package com.olipro.utils;

import com.google.gson.GsonBuilder;
import com.olipro.utils.cobertura.*;
import com.olipro.utils.llvm.*;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.*;
import java.util.function.Function;

public class Coverify {

    private static class IntermediateData {
        final Map<Long, List<LLVMJSONSegment>> segments;
        final LLVMJSONSummary summary;

        IntermediateData(Map<Long, List<LLVMJSONSegment>> segments, LLVMJSONSummary summary) {
            this.segments = segments;
            this.summary = summary;
        }
    }

    private static class SummaryData {
        double regionRate;
        long regionsCovered = 0;
        long regionsTotal = 0;
        double lineRate;
        long linesCovered = 0;
        long linesTotal = 0;

        void computeRates() {
            regionRate = regionsCovered == regionsTotal ? 100 : (double)regionsCovered / (double) regionsTotal;
            lineRate = linesCovered == linesTotal ? 100 : (double)linesCovered / (double) linesTotal;
        }
    }

    private LLVMJSONRoot json;
    private Map<String, IntermediateData> tree = new TreeMap<>();
    private CoberturaRoot xml;
    private Marshaller jaxb = CoberturaRoot.getJAXBMarshaller();
    private OutputStream out;

    Coverify(InputStreamReader file, OutputStream out) throws Exception {
        this.out = out;
        GsonBuilder gsonBuilder = LLVMGsonBuilder.get();
        jaxb.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        json = gsonBuilder.create().fromJson(file, LLVMJSONRoot.class);
        if (!"2.0.0".equals(json.version))
            System.err.println("Warning: LLVM version has changed. Parsing may fail.");
        parseToXML();
    }

    private void parseToXML() throws JAXBException {
        populateFileSegmentInfo();
        populateTotals();
        populatePackages();
        jaxb.marshal(xml, out);
    }

    private void populateFileSegmentInfo() {
        for (var data : json.data) {
            for (var file : data.files) {
                tree.putIfAbsent(file.filename, new IntermediateData(new LinkedHashMap<>(), file.summary));
                var segMap = tree.get(file.filename).segments;
                for (var segment : file.segments) {
                    if (segment.isRegionEntry)
                        continue;
                    if (!segment.hasCount)
                        segment.count = 1;
                    segMap.putIfAbsent(segment.line, new ArrayList<>());
                    segMap.get(segment.line).add(segment);
                }
            }
        }
    }

    private void populateTotals() {
        var sum = new SummaryData();
        for (var data : json.data) {
            sum.linesCovered += data.totals.lines.covered;
            sum.linesTotal += data.totals.lines.count;
            sum.regionsCovered += data.totals.regions.covered;
            sum.regionsTotal += data.totals.regions.count;
        }
        sum.computeRates();
        xml = new CoberturaRoot(sum.regionRate, sum.regionsCovered, sum.regionsTotal, 0.0, sum.lineRate, sum.linesCovered, sum.linesTotal, System.currentTimeMillis() / 1000);
    }

    private void populatePackages() {
        final var packages = new HashMap<String, HashMap<String, CoberturaClass>>();
        tree.forEach((filePath, v) -> {
            var path = new File(filePath);
            var strPath = path.getParent() == null ? "." : path.getParent();
            packages.putIfAbsent(strPath, new HashMap<>());
            var pkg = packages.get(strPath);
            processClassForPackage(path.getName(), pkg, v);
        });
        finaliseXmlTreeWithPackages(packages);
    }

    private void finaliseXmlTreeWithPackages(HashMap<String, HashMap<String, CoberturaClass>> packages) {
        var srcList = xml.getSources();
        var pkgList = xml.getPackages();
        packages.forEach((srcPath, files) -> {
            srcList.add(srcPath);
            var regionRate = calcRate(files, CoberturaClass::getBranchRate);
            var lineRate = calcRate(files, CoberturaClass::getLineRate);
            var pkg = new CoberturaPackage(regionRate,0, lineRate, srcPath);
            pkg.getClasses().addAll(files.values());
            pkgList.add(pkg);
        });
    }

    private static double calcRate(HashMap<String, CoberturaClass> classes, Function<CoberturaClass, Double> clbk) {
        var items = classes.values();
        double rateTotal = 0;
        for (var cls : items)
            rateTotal += clbk.apply(cls);
        return rateTotal / items.size();
    }

    private static void processClassForPackage(String file, HashMap<String, CoberturaClass> pkg, IntermediateData intData) {
        var sum = new SummaryData();
        sum.linesCovered = intData.summary.lines.covered;
        sum.linesTotal = intData.summary.lines.count;
        sum.regionsCovered = intData.summary.regions.covered;
        sum.regionsTotal = intData.summary.regions.count;
        sum.computeRates();
        if (pkg.putIfAbsent(file, new CoberturaClass(sum.regionRate, 0, file, sum.lineRate, file.replace('.', '_'))) != null)
            throw new IllegalStateException("class already populated. This should not happen");
        processSegmentsForClass(pkg.get(file), intData);
    }

    private static void processSegmentsForClass(CoberturaClass cls, IntermediateData intData) {
        intData.segments.forEach((lineNo, segments) -> {
            if (segments.size() == 1)
                addSingleLineEntry(cls, segments.get(0));
            else
                addBranchingLineEntry(cls, segments);
        });
    }

    private static void addSingleLineEntry(CoberturaClass cls, LLVMJSONSegment segment) {
        cls.getLines().add(new CoberturaLine(false, segment.count, 0, 0, segment.line));
    }

    private static void addBranchingLineEntry(CoberturaClass cls, List<LLVMJSONSegment> segments) {
        final var lineNo = segments.get(0).line;
        int coveredCount = 0;
        int uncoveredCount = 0;
        long hitsMax = 0;
        for (var segment : segments) {
            if (segment.count == 0)
                ++uncoveredCount;
            else
                ++coveredCount;
            hitsMax = Math.max(hitsMax, segment.count);
        }
        final var total = coveredCount + uncoveredCount;
        final int covPercent = coveredCount == total ?
                100 : (int)Math.round(((double)coveredCount / (double)total) * 100);
        final var lines = cls.getLines();
        final var conds = new CoberturaConditions();
        final var condList = conds.getConditionList();
        condList.add(new CoberturaCondition(covPercent, lineNo));
        lines.add(new CoberturaLine(true, hitsMax, coveredCount, total, lineNo));
        lines.add(conds);
    }

    public static void main(String[] args) throws Exception {
        try (var file = new FileReader(args[0])) {
            new Coverify(file, System.out);
        }
    }
}

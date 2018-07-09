package com.olipro.utils.cobertura;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "coverage")
public class CoberturaRoot {
    private static final String version = "LLVMToCobertura v0.1.0";
    private static final String DTD = "\n<!DOCTYPE coverage SYSTEM 'http://cobertura.sourceforge.net/xml/coverage-04.dtd'>\n";
    private double branchRate;
    private long branchesCovered;
    private long branchesValid;
    private double complexity;
    private double lineRate;
    private long linesCovered;
    private long linesValid;
    private long timestamp;
    private List<String> sources;
    private List<CoberturaPackage> packages;

    static {
        System.setProperty("com.sun.xml.bind.v2.bytecode.ClassTailor.noOptimize", "true");
    }

    public static Marshaller getJAXBMarshaller() throws JAXBException {
        var ret = JAXBContext.newInstance(CoberturaRoot.class).createMarshaller();
        ret.setProperty("com.sun.xml.bind.xmlHeaders", DTD);
        return ret;
    }

    private CoberturaRoot() {}

    public CoberturaRoot(double branchRate, long branchesCovered, long branchesValid, double complexity, double lineRate, long linesCovered, long linesValid, long timestamp) {
        this.branchRate = branchRate;
        this.branchesCovered = branchesCovered;
        this.branchesValid = branchesValid;
        this.complexity = complexity;
        this.lineRate = lineRate;
        this.linesCovered = linesCovered;
        this.linesValid = linesValid;
        this.timestamp = timestamp;
        this.sources = new ArrayList<>();
        this.packages = new ArrayList<>();
    }

    @XmlAttribute(name = "branch-rate")
    public double getBranchRate() {
        return branchRate;
    }

    @XmlAttribute(name = "branches-covered")
    public long getBranchesCovered() {
        return branchesCovered;
    }

    @XmlAttribute(name = "branches-valid")
    public long getBranchesValid() {
        return branchesValid;
    }

    @XmlAttribute(name = "complexity")
    public double getComplexity() {
        return complexity;
    }

    @XmlAttribute(name = "line-rate")
    public double getLineRate() {
        return lineRate;
    }

    @XmlAttribute(name = "lines-covered")
    public long getLinesCovered() {
        return linesCovered;
    }

    @XmlAttribute(name = "lines-valid")
    public long getLinesValid() {
        return linesValid;
    }

    @XmlAttribute(name = "timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    @XmlElementWrapper(name = "sources")
    @XmlElement(name = "source")
    public List<String> getSources() {
        return sources;
    }

    @XmlElementWrapper(name = "packages")
    @XmlElement(name = "package")
    public List<CoberturaPackage> getPackages() {
        return packages;
    }

    @XmlAttribute(name = "version")
    public String getVersion() {
        return version;
    }

    public void setBranchRate(double branchRate) {
        this.branchRate = branchRate;
    }

    public void setBranchesCovered(long branchesCovered) {
        this.branchesCovered = branchesCovered;
    }

    public void setBranchesValid(long branchesValid) {
        this.branchesValid = branchesValid;
    }

    public void setComplexity(double complexity) {
        this.complexity = complexity;
    }

    public void setLineRate(double lineRate) {
        this.lineRate = lineRate;
    }

    public void setLinesCovered(long linesCovered) {
        this.linesCovered = linesCovered;
    }

    public void setLinesValid(long linesValid) {
        this.linesValid = linesValid;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

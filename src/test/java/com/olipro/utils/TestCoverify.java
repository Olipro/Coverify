package com.olipro.utils;

import com.olipro.utils.cobertura.CoberturaClass;
import com.olipro.utils.llvm.LLVMJSONSummary;
import com.olipro.utils.llvm.LLVMJSONSummaryData;
import org.testng.annotations.Test;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import static org.testng.Assert.*;

public class TestCoverify {
    private static final String json = "{\"version\":\"2.0.0\",\"type\":\"llvm.coverage.json.export\"," +
            "\"data\":[{\"files\":[{\"filename\":\"/home/olipro/covtest.cpp\",\"segments\":[[6,22,2,1,1],[8,2,0,0,0]," +
            "[10,39,4,1,1],[11,6,4,1,1],[11,22,4,1,0],[11,23,1,1,0],[12,3,1,1,1],[12,11,3,1,0],[13,7,3,1,1],[13,11,3," +
            "1,1],[13,27,3,1,0],[13,28,1,1,0],[13,29,1,1,1],[17,3,2,1,0],[17,9,2,1,1],[17,13,2,1,1],[17,29,2,1,0]," +
            "[17,30,1,1,0],[17,31,1,1,1],[21,3,1,1,0],[21,9,1,1,1],[21,13,1,1,1],[21,29,1,1,0],[21,30,0,1,0],[21,31," +
            "0,1,1],[24,3,1,1,0],[25,2,1,1,1],[26,2,0,0,0],[28,12,1,1,1],[34,2,0,0,0]],\"expansions\":[]," +
            "\"summary\":{\"lines\":{\"count\":27,\"covered\":24,\"percent\":88},\"functions\":{\"count\":3," +
            "\"covered\":3,\"percent\":100},\"instantiations\":{\"count\":4,\"covered\":4,\"percent\":100}," +
            "\"regions\":{\"count\":15,\"covered\":14,\"notcovered\":1,\"percent\":93}}}]," +
            "\"functions\":[{\"name\":\"_Z16partiallyCoveredi\",\"count\":4,\"regions\":[[10,39,26,2,4,0,0,0],[11,6," +
            "11,22,4,0,0,0],[11,23,12,3,1,0,0,3],[12,3,12,11,1,0,0,0],[12,11,12,12,3,0,0,3],[12,12,13,7,3,0,0,3],[13," +
            "7,24,3,3,0,0,0],[13,11,13,27,3,0,0,0],[13,28,13,29,1,0,0,3],[13,29,17,3,1,0,0,0],[17,3,17,9,2,0,0,3]," +
            "[17,9,24,3,2,0,0,0],[17,13,17,29,2,0,0,0],[17,30,17,31,1,0,0,3],[17,31,21,3,1,0,0,0],[21,3,21,9,1,0,0," +
            "3],[21,9,24,3,1,0,0,0],[21,13,21,29,1,0,0,0],[21,30,21,31,0,0,0,3],[21,31,24,3,0,0,0,0],[24,3,25,2,1,0," +
            "0,3],[25,2,26,2,1,0,0,0]],\"filenames\":[\"/home/olipro/covtest.cpp\"]},{\"name\":\"main\",\"count\":1," +
            "\"regions\":[[28,12,34,2,1,0,0,0]],\"filenames\":[\"/home/olipro/covtest.cpp\"]}," +
            "{\"name\":\"_Z11instantiateINSt3__16vectorIiNS0_9allocatorIiEEEEEiT_\",\"count\":1,\"regions\":[[6,22,8," +
            "2,1,0,0,0]],\"filenames\":[\"/home/olipro/covtest.cpp\"]}," +
            "{\"name\":\"_Z11instantiateINSt3__14listIiNS0_9allocatorIiEEEEEiT_\",\"count\":1,\"regions\":[[6,22,8,2," +
            "1,0,0,0]],\"filenames\":[\"/home/olipro/covtest.cpp\"]}],\"totals\":{\"lines\":{\"count\":27," +
            "\"covered\":24,\"percent\":88},\"functions\":{\"count\":3,\"covered\":3,\"percent\":100}," +
            "\"instantiations\":{\"count\":4,\"covered\":4,\"percent\":100},\"regions\":{\"count\":15,\"covered\":14," +
            "\"notcovered\":1,\"percent\":93}}}]}";

    private static final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<!DOCTYPE coverage SYSTEM 'http://cobertura.sourceforge.net/xml/coverage-04.dtd'>\n" +
            "\n" +
            "<coverage branch-rate=\"0.9333333333333333\" branches-covered=\"14\" branches-valid=\"15\" complexity=\"0.0\" line-rate=\"0.8888888888888888\" lines-covered=\"24\" lines-valid=\"27\" timestamp=\"0\" version=\"LLVMToCobertura v0.1.0\">\n" +
            "    <packages>\n" +
            "        <package branch-rate=\"0.9333333333333333\" complexity=\"0.0\" line-rate=\"0.8888888888888888\" name=\"\\home\\olipro\">\n" +
            "            <classes>\n" +
            "                <class branch-rate=\"0.9333333333333333\" complexity=\"0.0\" filename=\"covtest.cpp\" line-rate=\"0.8888888888888888\" name=\"covtest_cpp\">\n" +
            "                    <lines>\n" +
            "                        <line branch=\"false\" hits=\"1\" number=\"8\"/>\n" +
            "                        <line branch=\"true\" condition-coverage=\"100% (2/2)\" hits=\"4\" number=\"11\"/>\n" +
            "                        <conditions>\n" +
            "                            <condition number=\"11\" coverage=\"100%\" type=\"jump\"/>\n" +
            "                        </conditions>\n" +
            "                        <line branch=\"false\" hits=\"3\" number=\"12\"/>\n" +
            "                        <line branch=\"true\" condition-coverage=\"100% (2/2)\" hits=\"3\" number=\"13\"/>\n" +
            "                        <conditions>\n" +
            "                            <condition number=\"13\" coverage=\"100%\" type=\"jump\"/>\n" +
            "                        </conditions>\n" +
            "                        <line branch=\"true\" condition-coverage=\"100% (3/3)\" hits=\"2\" number=\"17\"/>\n" +
            "                        <conditions>\n" +
            "                            <condition number=\"17\" coverage=\"100%\" type=\"jump\"/>\n" +
            "                        </conditions>\n" +
            "                        <line branch=\"true\" condition-coverage=\"67% (2/3)\" hits=\"1\" number=\"21\"/>\n" +
            "                        <conditions>\n" +
            "                            <condition number=\"21\" coverage=\"67%\" type=\"jump\"/>\n" +
            "                        </conditions>\n" +
            "                        <line branch=\"false\" hits=\"1\" number=\"24\"/>\n" +
            "                        <line branch=\"false\" hits=\"1\" number=\"26\"/>\n" +
            "                        <line branch=\"false\" hits=\"1\" number=\"34\"/>\n" +
            "                    </lines>\n" +
            "                    <methods/>\n" +
            "                </class>\n" +
            "            </classes>\n" +
            "        </package>\n" +
            "    </packages>\n" +
            "    <sources>\n" +
            "        <source>\\home\\olipro</source>\n" +
            "    </sources>\n" +
            "</coverage>\n";

    private static final String json_fullCoverage = "{\"version\":\"2.0.1\",\"type\":\"llvm.coverage.json.export\"," +
            "\"data\":[{\"files\":[{\"filename\":\"/home/olipro/covtest.cpp\",\"segments\":[[6,22,2,1,1],[8,2,0,0,0]," +
            "[10,39,5,1,1],[11,6,5,1,1],[11,22,5,1,0],[11,23,1,1,0],[12,3,1,1,1],[12,11,4,1,0],[13,7,4,1,1],[13,11,4," +
            "1,1],[13,27,4,1,0],[13,28,1,1,0],[13,29,1,1,1],[17,3,3,1,0],[17,9,3,1,1],[17,13,3,1,1],[17,29,3,1,0]," +
            "[17,30,1,1,0],[17,31,1,1,1],[21,3,2,1,0],[21,9,2,1,1],[21,13,2,1,1],[21,29,2,1,0],[21,30,1,1,0],[21,31," +
            "1,1,1],[24,3,1,1,0],[25,2,1,1,1],[26,2,0,0,0],[28,12,1,1,1],[36,2,0,0,0]],\"expansions\":[]," +
            "\"summary\":{\"lines\":{\"count\":29,\"covered\":29,\"percent\":100},\"functions\":{\"count\":3," +
            "\"covered\":3,\"percent\":100},\"instantiations\":{\"count\":4,\"covered\":4,\"percent\":100}," +
            "\"regions\":{\"count\":15,\"covered\":15,\"notcovered\":0,\"percent\":100}}}]," +
            "\"functions\":[{\"name\":\"_Z16partiallyCoveredi\",\"count\":5,\"regions\":[[10,39,26,2,5,0,0,0],[11,6," +
            "11,22,5,0,0,0],[11,23,12,3,1,0,0,3],[12,3,12,11,1,0,0,0],[12,11,12,12,4,0,0,3],[12,12,13,7,4,0,0,3],[13," +
            "7,24,3,4,0,0,0],[13,11,13,27,4,0,0,0],[13,28,13,29,1,0,0,3],[13,29,17,3,1,0,0,0],[17,3,17,9,3,0,0,3]," +
            "[17,9,24,3,3,0,0,0],[17,13,17,29,3,0,0,0],[17,30,17,31,1,0,0,3],[17,31,21,3,1,0,0,0],[21,3,21,9,2,0,0," +
            "3],[21,9,24,3,2,0,0,0],[21,13,21,29,2,0,0,0],[21,30,21,31,1,0,0,3],[21,31,24,3,1,0,0,0],[24,3,25,2,1,0," +
            "0,3],[25,2,26,2,1,0,0,0]],\"filenames\":[\"/home/olipro/covtest.cpp\"]},{\"name\":\"main\",\"count\":1," +
            "\"regions\":[[28,12,36,2,1,0,0,0]],\"filenames\":[\"/home/olipro/covtest.cpp\"]}," +
            "{\"name\":\"_Z11instantiateINSt3__16vectorIiNS0_9allocatorIiEEEEEiT_\",\"count\":1,\"regions\":[[6,22,8," +
            "2,1,0,0,0]],\"filenames\":[\"/home/olipro/covtest.cpp\"]}," +
            "{\"name\":\"_Z11instantiateINSt3__14listIiNS0_9allocatorIiEEEEEiT_\",\"count\":1,\"regions\":[[6,22,8,2," +
            "1,0,0,0]],\"filenames\":[\"/home/olipro/covtest.cpp\"]}],\"totals\":{\"lines\":{\"count\":29," +
            "\"covered\":29,\"percent\":100},\"functions\":{\"count\":3,\"covered\":3,\"percent\":100}," +
            "\"instantiations\":{\"count\":4,\"covered\":4,\"percent\":100},\"regions\":{\"count\":15,\"covered\":15," +
            "\"notcovered\":0,\"percent\":100}}}]}";

    private static final String xml_fullCoverage = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<!DOCTYPE coverage SYSTEM 'http://cobertura.sourceforge.net/xml/coverage-04.dtd'>\n" +
            "\n" +
            "<coverage branch-rate=\"100.0\" branches-covered=\"15\" branches-valid=\"15\" complexity=\"0.0\" line-rate=\"100.0\" lines-covered=\"29\" lines-valid=\"29\" timestamp=\"0\" version=\"LLVMToCobertura v0.1.0\">\n" +
            "    <packages>\n" +
            "        <package branch-rate=\"100.0\" complexity=\"0.0\" line-rate=\"100.0\" name=\"\\home\\olipro\">\n" +
            "            <classes>\n" +
            "                <class branch-rate=\"100.0\" complexity=\"0.0\" filename=\"covtest.cpp\" line-rate=\"100.0\" name=\"covtest_cpp\">\n" +
            "                    <lines>\n" +
            "                        <line branch=\"false\" hits=\"1\" number=\"8\"/>\n" +
            "                        <line branch=\"true\" condition-coverage=\"100% (2/2)\" hits=\"5\" number=\"11\"/>\n" +
            "                        <conditions>\n" +
            "                            <condition number=\"11\" coverage=\"100%\" type=\"jump\"/>\n" +
            "                        </conditions>\n" +
            "                        <line branch=\"false\" hits=\"4\" number=\"12\"/>\n" +
            "                        <line branch=\"true\" condition-coverage=\"100% (2/2)\" hits=\"4\" number=\"13\"/>\n" +
            "                        <conditions>\n" +
            "                            <condition number=\"13\" coverage=\"100%\" type=\"jump\"/>\n" +
            "                        </conditions>\n" +
            "                        <line branch=\"true\" condition-coverage=\"100% (3/3)\" hits=\"3\" number=\"17\"/>\n" +
            "                        <conditions>\n" +
            "                            <condition number=\"17\" coverage=\"100%\" type=\"jump\"/>\n" +
            "                        </conditions>\n" +
            "                        <line branch=\"true\" condition-coverage=\"100% (3/3)\" hits=\"2\" number=\"21\"/>\n" +
            "                        <conditions>\n" +
            "                            <condition number=\"21\" coverage=\"100%\" type=\"jump\"/>\n" +
            "                        </conditions>\n" +
            "                        <line branch=\"false\" hits=\"1\" number=\"24\"/>\n" +
            "                        <line branch=\"false\" hits=\"1\" number=\"26\"/>\n" +
            "                        <line branch=\"false\" hits=\"1\" number=\"36\"/>\n" +
            "                    </lines>\n" +
            "                    <methods/>\n" +
            "                </class>\n" +
            "            </classes>\n" +
            "        </package>\n" +
            "    </packages>\n" +
            "    <sources>\n" +
            "        <source>\\home\\olipro</source>\n" +
            "    </sources>\n" +
            "</coverage>\n";

    @Test
    public void testProcessClassForPackageThrowsIfClassAlreadyPopulated() throws Exception {
        Constructor idata = null;
        Class c = null;
        for (var cls : Coverify.class.getDeclaredClasses())
            if ("IntermediateData".equals(cls.getSimpleName())) {
                c = cls;
                idata = cls.getDeclaredConstructors()[0];
                break;
            }
        assertNotNull(idata);
        idata.setAccessible(true);
        var data = new LLVMJSONSummary();
        data.lines = new LLVMJSONSummaryData();
        data.regions = new LLVMJSONSummaryData();
        var idInst = idata.newInstance(null, data);
        var method = Coverify.class.getDeclaredMethod("processClassForPackage", String.class, HashMap.class, c);
        method.setAccessible(true);
        var incorrectlyFilledMap = new HashMap<String, CoberturaClass>();
        incorrectlyFilledMap.put("file", new CoberturaClass(0.0, 0.0, "file", 0.0, "file"));
        try {
            method.invoke(null, "file", incorrectlyFilledMap, idInst);
            fail("Exception should have been thrown on the line above");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof IllegalStateException);
        }
    }

    @Test
    public void TestCoverifySerializesJSONToExpectedXML() throws Exception {
        var strm = new InputStreamReader(new ByteArrayInputStream(json.getBytes("UTF-8")));
        var out = new ByteArrayOutputStream();
        new Coverify(strm, out);
        var str = new String(out.toByteArray(), "UTF-8");
        str = str.replaceAll("timestamp=\"[0-9]+\"", "timestamp=\"0\"");
        assertEquals(str, xml);
    }

    @Test
    public void TestCoverifySerializesFullyCoveredJSONToCorrectXML() throws Exception {
        var strm = new InputStreamReader(new ByteArrayInputStream(json_fullCoverage.getBytes("UTF-8")));
        var out = new ByteArrayOutputStream();
        try (var buf = new ByteArrayOutputStream()) {
            var oldErr = System.err;
            try (var stderr = new PrintStream(buf, false, "UTF-8")) {
                System.setErr(stderr);
                new Coverify(strm, out);
            }
            System.setErr(oldErr);
            assertTrue(buf.toString("UTF-8")
                    .contains("Warning: LLVM version has changed. Parsing may fail."));
        }
        var str = new String(out.toByteArray(), "UTF-8");
        str = str.replaceAll("timestamp=\"[0-9]+\"", "timestamp=\"0\"");
        assertEquals(str, xml_fullCoverage);
    }

    @Test
    public void testMainFunctionsCorrectly() throws Exception {
        try(var file = new FileWriter("jsontmp.json", false)) {
            file.write(json);
        }
        try (var buf = new ByteArrayOutputStream()) {
            var oldOut = System.out;
            try (var strm = new PrintStream(buf, false, "UTF-8")) {
                System.setOut(strm);
                Coverify.main(new String[]{"jsontmp.json"});
            }
            System.setOut(oldOut);
            var str = buf.toString("UTF-8")
                    .replaceAll("timestamp=\"[0-9]+\"", "timestamp=\"0\"");
            assertEquals(str, xml);
        } finally {
            new File("jsontmp.json").delete();
        }
    }
}
package com.olipro.utils.llvm;

import com.google.gson.GsonBuilder;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class TestLLVMJSON {
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

    @Test
    public void testJsonIsSerializedToCorrectObjects() {
        var gson = new GsonBuilder();
        //TODO: refactor this into LLVMJSONRoot.
        gson.registerTypeAdapter(LLVMJSONSegment.class, new LLVMJSONSegment.LLVMJSONSegmentAdapter());
        gson.registerTypeAdapter(LLVMJSONRegion.class, new LLVMJSONRegion.LLVMJSONRegionAdapter());
        var obj = gson.create().fromJson(this.json, LLVMJSONRoot.class);
        assertEquals(obj.version, "2.0.0");
        assertEquals(obj.type, "llvm.coverage.json.export");
        assertEquals(obj.data.length, 1);
        assertEquals(obj.data[0].files.length , 1);
        assertEquals(obj.data[0].functions.length, 4);

        //Totals
        var totals = obj.data[0].totals;
        assertEquals(totals.lines.count, 27);
        assertEquals(totals.lines.covered, 24);
        assertEquals(totals.lines.percent, 88.0);
        assertEquals(totals.functions.count, 3);
        assertEquals(totals.functions.covered, 3);
        assertEquals(totals.functions.percent, 100.0);
        assertEquals(totals.instantiations.count, 4);
        assertEquals(totals.instantiations.covered, 4);
        assertEquals(totals.instantiations.percent, 100.0);
        assertEquals(totals.regions.count, 15);
        assertEquals(totals.regions.covered, 14);
        assertEquals(totals.regions.percent, 93.0);

        //File
        var file = obj.data[0].files[0];
        assertEquals(file.filename, "/home/olipro/covtest.cpp");
        assertEquals(file.segments.length, 30);
        assertEquals(file.segments[4].line, 11);
        assertEquals(file.segments[4].column, 22);
        assertEquals(file.segments[4].count, 4);
        assertTrue(file.segments[4].hasCount);
        assertFalse(file.segments[4].isRegionEntry);
        assertEquals(file.segments[21].line, 21);
        assertEquals(file.segments[21].column, 13);
        assertEquals(file.segments[21].count, 1);
        assertTrue(file.segments[21].hasCount);
        assertTrue(file.segments[21].isRegionEntry);

        assertEquals(file.expansions.length, 0);

        //Function
        var func = obj.data[0].functions[0];
        assertEquals(func.name, "_Z16partiallyCoveredi");
        assertEquals(func.filenames.length, 1);
        assertEquals(func.filenames[0], "/home/olipro/covtest.cpp");
        assertEquals(func.count, 4);
        assertEquals(func.regions.length, 22);
        assertEquals(func.regions[7].lineStart, 13);
        assertEquals(func.regions[7].columnStart, 11);
        assertEquals(func.regions[7].lineEnd, 13);
        assertEquals(func.regions[7].columnEnd, 27);
        assertEquals(func.regions[7].executionCount, 3);
        assertEquals(func.regions[7].fileId, 0);
        assertEquals(func.regions[7].expandedFileId, 0);
        assertEquals(func.regions[7].kind, 0);
    }
}
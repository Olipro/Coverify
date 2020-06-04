package com.olipro.utils.llvm;

import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

import static org.testng.Assert.*;

public class TestLLVMJSON {

	private InputStreamReader getJson(String fileName) throws FileNotFoundException {
		var url = getClass().getClassLoader().getResource(fileName);
		return new FileReader(url.getPath());
	}

	private void runValidation(LLVMJSONRoot obj) {
		assertEquals(obj.version, "2.0.0");
		assertEquals(obj.type, "llvm.coverage.json.export");
		assertEquals(obj.data.length, 1);
		assertEquals(obj.data[0].files.length, 1);
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

	@Test
	public void testJsonIsSerializedToCorrectObjects() throws FileNotFoundException {
		var gsonBuilder = LLVMGsonBuilder.get();
		var testJson = getJson("coverage.json");
		var obj = gsonBuilder.create().fromJson(testJson, LLVMJSONRoot.class);
	}

	@Test
	public void testJsonIsSerializedToCorrectObjectsVersion8x() throws FileNotFoundException {
		var gsonBuilder = LLVMGsonBuilder.get();
		var testJson = getJson("coverage_llvm_8x.json");
		var obj = gsonBuilder.create().fromJson(testJson, LLVMJSONRoot.class);
	}
}
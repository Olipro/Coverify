package com.olipro.utils.llvm;

import com.google.gson.GsonBuilder;

public class LLVMGsonBuilder {
	public static GsonBuilder get() {
		var gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(LLVMJSONSegment.class, new LLVMJSONSegment.LLVMJSONSegmentAdapter());
		gsonBuilder.registerTypeAdapter(LLVMJSONRegion.class, new LLVMJSONRegion.LLVMJSONRegionAdapter());
		return gsonBuilder;
	}
}

package com.olipro.utils.llvm;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class LLVMJSONRegion {
    public long lineStart;
    public long columnStart;
    public long lineEnd;
    public long columnEnd;
    public long executionCount;
    public long fileId;
    public long expandedFileId;
    public int kind;

    public static class LLVMJSONRegionAdapter implements JsonDeserializer<LLVMJSONRegion> {
        @Override
        public LLVMJSONRegion deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            var elems = json.getAsJsonArray();
            var ret = new LLVMJSONRegion();
            ret.lineStart = elems.get(0).getAsLong();
            ret.columnStart = elems.get(1).getAsLong();
            ret.lineEnd = elems.get(2).getAsLong();
            ret.columnEnd = elems.get(3).getAsLong();
            ret.executionCount = elems.get(4).getAsLong();
            ret.fileId = elems.get(5).getAsLong();
            ret.expandedFileId = elems.get(6).getAsLong();
            ret.kind = elems.get(7).getAsInt();
            return ret;
        }
    }
}

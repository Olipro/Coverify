package com.olipro.utils.llvm;

import com.google.gson.*;

import java.lang.reflect.Type;

public class LLVMJSONSegment {
    public long line;
    public int column;
    public long count;
    public boolean hasCount;
    public boolean isRegionEntry;

    public static class LLVMJSONSegmentAdapter implements JsonDeserializer<LLVMJSONSegment> {
        @Override
        public LLVMJSONSegment deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            var elems = json.getAsJsonArray();
            var ret = new LLVMJSONSegment();
            ret.line = elems.get(0).getAsLong();
            ret.column = elems.get(1).getAsInt();
            ret.count = elems.get(2).getAsInt();
            ret.hasCount = elems.get(3).getAsInt() != 0;
            ret.isRegionEntry = elems.get(4).getAsInt() != 0;

            return ret;
        }
    }
}

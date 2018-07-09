package com.olipro.utils.cobertura;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "lines")
class CoberturaLines {
    private List<LinesSubType> lineList = new ArrayList<>();

    @XmlAnyElement
    @XmlElementRefs({
            @XmlElementRef(name = "conditions", type = CoberturaConditions.class),
            @XmlElementRef(name = "line", type = CoberturaLine.class)
    })
    public List<LinesSubType> getLineList() {
        return lineList;
    }
}

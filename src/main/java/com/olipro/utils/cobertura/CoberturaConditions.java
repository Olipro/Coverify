package com.olipro.utils.cobertura;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "conditions")
@XmlAccessorType(XmlAccessType.NONE)
public class CoberturaConditions implements LinesSubType {
    private List<CoberturaCondition> conditionList = new ArrayList<>();

    @XmlElement(name = "condition")
    public List<CoberturaCondition> getConditionList() {
        return conditionList;
    }
}
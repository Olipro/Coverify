package com.olipro.utils.cobertura;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class CoberturaCondition {
    private transient int coverage;
    private long number;
    private String type;

    private CoberturaCondition() {}

    public CoberturaCondition(int coverage, long number, String type) {
        this.coverage = coverage;
        this.number = number;
        this.type = type;
    }

    public CoberturaCondition(int coverage, long number) {
        this(coverage, number, "jump");
    }

    @XmlAttribute(name = "coverage")
    public String getStrCoverage() {
        return Integer.toString(coverage) + "%";
    }

    public int getCoverage() {
        return coverage;
    }

    public void setCoverage(int coverage) {
        if (coverage > 100 || coverage < 0)
            throw new IndexOutOfBoundsException("Coverage must be between 0-100");
        this.coverage = coverage;
    }

    @XmlAttribute(name = "number")
    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    @XmlAttribute(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
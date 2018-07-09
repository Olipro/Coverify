package com.olipro.utils.cobertura;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "line")
@XmlAccessorType(XmlAccessType.NONE)
public class CoberturaLine implements LinesSubType {
    private boolean branch = false;
    private long hits = 0;
    private transient int coveredConditions;
    private transient int totalConditions;
    private long number = 0;

    private CoberturaLine() {}

    public CoberturaLine(boolean branch, long hits, int coveredConditions, int totalConditions, long number) {
        this.branch = branch;
        this.hits = hits;
        this.coveredConditions = coveredConditions;
        this.totalConditions = totalConditions;
        this.number = number;
    }

    @XmlAttribute(name = "branch")
    public boolean isBranch() {
        return branch;
    }

    public void setBranch(boolean branch) {
        this.branch = branch;
    }

    @XmlAttribute(name = "hits")
    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }

    public int getCoveredConditions() {
        return coveredConditions;
    }

    public void setCoveredConditions(int coveredConditions) {
        this.coveredConditions = coveredConditions;
    }

    public int getTotalConditions() {
        return totalConditions;
    }

    public void setTotalConditions(int totalConditions) {
        this.totalConditions = totalConditions;
    }

    @XmlAttribute(name = "condition-coverage")
    public String getConditionCoverage() {
        if (totalConditions == 0)
            return null;
        var percent = Math.round(((double) coveredConditions / (double) totalConditions) * 100);
        if (percent == 100 && coveredConditions < totalConditions)
            percent = 99;
        return Long.toString(percent) + "% (" + coveredConditions + "/" + totalConditions + ")";
    }

    @XmlAttribute(name = "number")
    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}

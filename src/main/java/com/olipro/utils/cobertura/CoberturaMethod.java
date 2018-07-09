package com.olipro.utils.cobertura;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "method")
@XmlAccessorType(XmlAccessType.NONE)
public class CoberturaMethod {
    private String name;
    private String signature;
    private double lineRate;
    private double branchRate;
    private CoberturaLines lines;

    private CoberturaMethod() {}

    public CoberturaMethod(String name, String signature, double lineRate, double branchRate) {
        this.name = name;
        this.signature = signature;
        this.lineRate = lineRate;
        this.branchRate = branchRate;
        this.lines = new CoberturaLines();
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "signature")
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @XmlAttribute(name = "line-rate")
    public double getLineRate() {
        return lineRate;
    }

    public void setLineRate(double lineRate) {
        this.lineRate = lineRate;
    }

    @XmlAttribute(name = "branch-rate")
    public double getBranchRate() {
        return branchRate;
    }

    public void setBranchRate(double branchRate) {
        this.branchRate = branchRate;
    }

    @XmlElementRef(name = "lines")
    public CoberturaLines getLines() {
        return lines;
    }
}

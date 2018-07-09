package com.olipro.utils.cobertura;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "class")
@XmlAccessorType(XmlAccessType.NONE)
public class CoberturaClass {
    private double branchRate;
    private double complexity;
    private String filename = "";
    private double lineRate;
    private String name = "";
    private List<CoberturaMethod> methods;
    private CoberturaLines lines;

    private CoberturaClass() {}

    public CoberturaClass(double branchRate, double complexity, String filename, double lineRate, String name) {
        this.branchRate = branchRate;
        this.complexity = complexity;
        this.filename = filename;
        this.lineRate = lineRate;
        this.name = name;
        this.methods = new ArrayList<>();
        this.lines = new CoberturaLines();
    }

    @XmlAttribute(name = "branch-rate")
    public double getBranchRate() {
        return branchRate;
    }

    public void setBranchRate(double branchRate) {
        this.branchRate = branchRate;
    }

    @XmlAttribute(name = "complexity")
    public double getComplexity() {
        return complexity;
    }

    public void setComplexity(double complexity) {
        this.complexity = complexity;
    }

    @XmlAttribute(name = "filename")
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @XmlAttribute(name = "line-rate")
    public double getLineRate() {
        return lineRate;
    }

    public void setLineRate(double lineRate) {
        this.lineRate = lineRate;
    }

    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "methods")
    @XmlElement(name = "method")
    public List<CoberturaMethod> getMethods() {
        return methods;
    }

    public List<LinesSubType> getLines() {
        return lines.getLineList();
    }

    @XmlElementRef(name = "lines")
    private CoberturaLines getCoberturaLines() {
        return lines;
    }
}

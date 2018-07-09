package com.olipro.utils.cobertura;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "package")
public class CoberturaPackage {
    private double branchRate;
    private double complexity;
    private double lineRate;
    private String name = "";
    private List<CoberturaClass> classes;

    private CoberturaPackage() {}

    public CoberturaPackage(double branchRate, double complexity, double lineRate, String name) {
        this.branchRate = branchRate;
        this.complexity = complexity;
        this.lineRate = lineRate;
        this.name = name;
        this.classes = new ArrayList<>();
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

    @XmlElementWrapper(name = "classes")
    @XmlElement(name = "class")
    public List<CoberturaClass> getClasses() {
        return classes;
    }
}

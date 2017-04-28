package org.nhsrc.model;

import java.io.Serializable;
import java.util.List;

public class Checklist implements Serializable {
    private String name;
    private String uuid;
    private String department;
    private String assessmentTool;
    private List<String> areasOfConcern;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAssessmentTool() {
        return assessmentTool;
    }

    public void setAssessmentTool(String assessmentTool) {
        this.assessmentTool = assessmentTool;
    }

    public List<String> getAreasOfConcern() {
        return areasOfConcern;
    }

    public void setAreasOfConcern(List<String> areasOfConcern) {
        this.areasOfConcern = areasOfConcern;
    }
}

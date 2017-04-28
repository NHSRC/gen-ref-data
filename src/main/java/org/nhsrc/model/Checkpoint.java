package org.nhsrc.model;

import java.io.Serializable;
import java.util.List;

public class Checkpoint implements Serializable {
    private String name;
    private String uuid;
    private String meansOfVerification = "";
    private String measurableElement;
    private String checklist;
    private Boolean isDefault;
    private String state = "";
    private Boolean amObservation;
    private Boolean amStaffInterview;
    private Boolean amPatientInterview;
    private Boolean amRecordReview;

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

    public String getMeansOfVerification() {
        return meansOfVerification;
    }

    public void setMeansOfVerification(String meansOfVerification) {
        this.meansOfVerification = meansOfVerification;
    }

    public String getMeasurableElement() {
        return measurableElement;
    }

    public void setMeasurableElement(String measurableElement) {
        this.measurableElement = measurableElement;
    }

    public String getChecklist() {
        return checklist;
    }

    public void setChecklist(String checklist) {
        this.checklist = checklist;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getAmObservation() {
        return amObservation;
    }

    public void setAmObservation(Boolean amObservation) {
        this.amObservation = amObservation;
    }

    public Boolean getAmStaffInterview() {
        return amStaffInterview;
    }

    public void setAmStaffInterview(Boolean amStaffInterview) {
        this.amStaffInterview = amStaffInterview;
    }

    public Boolean getAmPatientInterview() {
        return amPatientInterview;
    }

    public void setAmPatientInterview(Boolean amPatientInterview) {
        this.amPatientInterview = amPatientInterview;
    }

    public Boolean getAmRecordReview() {
        return amRecordReview;
    }

    public void setAmRecordReview(Boolean amRecordReview) {
        this.amRecordReview = amRecordReview;
    }

    public Boolean isOfType(List<String> content) {
        return null;
    }

}

package org.nhsrc;

import java.util.ArrayList;
import java.util.List;

public class Standard {
    private String name;
    private String uuid;
    private String reference;
    private List<MeasurableElement> measurableElements;

    public Standard() {
        this.measurableElements = new ArrayList<MeasurableElement>();
    }

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<MeasurableElement> getMeasurableElements() {
        return measurableElements;
    }

    public void setMeasurableElements(List<MeasurableElement> measurableElements) {
        this.measurableElements = measurableElements;
    }

    public void addMeasurableElement(MeasurableElement measurableElement) {
        this.measurableElements.add(measurableElement);
    }

    @Override
    public String toString() {
        return "Standard{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }
}

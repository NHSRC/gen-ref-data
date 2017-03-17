package org.nhsrc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AreaOfConcern  implements Serializable{
    private String name;
    private String uuid;
    private String reference;
    private List<Standard> standards;

    public AreaOfConcern() {
        this.standards = new ArrayList<Standard>();
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

    public List<Standard> getStandards() {
        return standards;
    }

    public void setStandards(List<Standard> standards) {
        this.standards = standards;
    }

    public void addStandard(Standard standard) {
        this.standards.add(standard);
    }

    @Override
    public String toString() {
        return "AreaOfConcern{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", reference='" + reference + '\'' +
                ", standards='" + standards + '\'' +
                '}';
    }
}

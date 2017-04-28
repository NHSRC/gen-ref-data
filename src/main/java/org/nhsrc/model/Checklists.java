package org.nhsrc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Checklists implements Serializable {
    private List<Checklist> checklists;

    public Checklists() {
        this.checklists = new ArrayList<>();
    }

    public void add(Checklist checklist) {
        this.checklists.add(checklist);
    }

    public Checklists addAll(Checklists checklists) {
        this.checklists.addAll(checklists.getChecklists());
        return this;
    }

    public List<Checklist> getChecklists() {
        return checklists;
    }
}

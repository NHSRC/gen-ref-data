package org.nhsrc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AreasOfConcern implements Serializable{
    private List<AreaOfConcern> areasOfConcern;

    public AreasOfConcern() {
        this.areasOfConcern = new ArrayList<AreaOfConcern>();
    }

    public List<AreaOfConcern> getAreasOfConcern() {
        return areasOfConcern;
    }

    public void setAreasOfConcern(List<AreaOfConcern> areasOfConcern) {
        this.areasOfConcern = areasOfConcern;
    }

    public void addAreasOfConcern(AreaOfConcern areaOfConcern) {
        this.areasOfConcern.add(areaOfConcern);
    }

    public boolean isEmpty() {
        return this.areasOfConcern.isEmpty();
    }

    @Override
    public String toString() {
        return "AreasOfConcern{" +
                "areasOfConcern=" + areasOfConcern +
                '}';
    }
}

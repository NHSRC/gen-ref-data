package org.nhsrc;

import java.util.ArrayList;
import java.util.List;

public class AreasOfConcern {
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

    @Override
    public String toString() {
        return "AreasOfConcern{" +
                "areasOfConcern=" + areasOfConcern +
                '}';
    }
}

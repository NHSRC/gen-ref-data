package org.nhsrc;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Create {

    private final AreasOfConcern areasOfConcern;
    private Standard currStandard;
    private MeasurableElement currME;
    private AreaOfConcern currAOC;
    private final Checkpoints checkpoints;

    public Create(AreasOfConcern areasOfConcern, Checkpoints checkpoints) {
        this.areasOfConcern = areasOfConcern;
        this.checkpoints = checkpoints;
        currAOC = null;
        currStandard = null;
        currME = null;
    }

    public void create(Row currentRow, boolean empty) {

        Iterator<Cell> rowIterator = currentRow.iterator();
        List<String> cells = new ArrayList<String>();
        while (rowIterator.hasNext()) {
            Cell cell = rowIterator.next();
            if (cell.getCellTypeEnum().equals(CellType.STRING)) {
                cells.add(cell.getStringCellValue());
            }
        }
        boolean emptyAreasOfConcern = empty;
        System.out.println(emptyAreasOfConcern);
        if (!cells.isEmpty()) {

            if (cells.get(0).startsWith("Area of Concern - ")) {
                if (!emptyAreasOfConcern) {
                    String aocRefName = cells.get(0).replace("Area of Concern - ", "");
                    String name = aocRefName.substring(2).trim();
                    currAOC = areasOfConcern.getAreasOfConcern().stream()
                            .filter((aoc) -> aoc.getName().equals(name))
                            .findFirst()
                            .get();
                } else {
                    aoc(cells);
                }

            } else if (cells.get(0).startsWith("Standard ")) {
                if (!emptyAreasOfConcern) {
                    String name = cells.get(1).trim();
                    currStandard = currAOC.getStandards().stream()
                            .filter((std) -> std.getName()
                                    .equals(name))
                            .findFirst()
                            .get();
                } else {
                    standard(cells);
                }

            } else if (cells.get(0).startsWith("ME")) {
                if (!emptyAreasOfConcern) {
                    String name = cells.get(1).trim();
                    currME = currStandard.getMeasurableElements().stream().filter((me) -> me.getName().equals(name)).findFirst().get();
                    if ((cells.size() > 2 && cells.get(2).trim().length() > 0)) {
                        checkpoint(cells.subList(2, cells.size()));
                    }

                } else {
                    me(cells);
                }

            } else {
                checkpoint(cells);
            }
        }

    }

    private void checkpoint(List<String> cells) {
        try {
            Checkpoint checkpoint = new Checkpoint();
            checkpoint.setUuid(UUID.randomUUID().toString());
            checkpoint.setName(cells.get(0));
            String am = cells.get(1);
            checkpoint.setAmObservation(am.contains("OB"));
            checkpoint.setAmPatientInterview(am.contains("PI"));
            checkpoint.setAmRecordReview(am.contains("RR"));
            checkpoint.setAmStaffInterview(am.contains("SI"));
            checkpoint.setDefault(true);
            checkpoint.setMeasurableElement(currME.getUuid());
            checkpoint.setChecklist("785dfe09-9c0f-4656-b620-dc7fe9659a54");
            if (cells.size() > 2) {
                checkpoint.setMeansOfVerification(cells.get(2));
            }
            checkpoints.addCheckpoint(checkpoint);
        } catch (Exception ignored) {
        }

    }

    private void me(List<String> cells) {
        MeasurableElement me = new MeasurableElement();
        String ref = cells.get(0).replace("ME", "").trim();
        String name = cells.get(1).trim();
        me.setName(name);
        me.setReference(ref);
        me.setUuid(UUID.randomUUID().toString());
        if (cells.size() > 2 && cells.get(2).trim().length() > 0) {
            checkpoint(cells.subList(2, cells.size()));
        }
        currStandard.addMeasurableElement(me);
        currME = me;
    }

    private void standard(List<String> cells) {
        Standard standard = new Standard();
        String ref = cells.get(0).replace("Standard", "").trim();
        String name = cells.get(1).trim();
        standard.setUuid(UUID.randomUUID().toString());
        standard.setReference(ref);
        standard.setName(name);
        currAOC.addStandard(standard);
        currStandard = standard;
    }

    private void aoc(List<String> cells) {
        AreaOfConcern aoc = new AreaOfConcern();
        String aocRefName = cells.get(0).replace("Area of Concern - ", "");
        String ref = aocRefName.substring(0, 1);
        String name = aocRefName.substring(2).trim();
        aoc.setName(name);
        aoc.setReference(ref);
        aoc.setUuid(UUID.randomUUID().toString());
        areasOfConcern.addAreasOfConcern(aoc);
        currAOC = aoc;
    }
}

package org.nhsrc;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.nhsrc.model.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Create {

    private final AreasOfConcern areasOfConcern;
    private final Pattern standardPattern;
    private final Pattern aocPattern;
    private Standard currStandard;
    private MeasurableElement currME;
    private AreaOfConcern currAOC;
    private final Checkpoints checkpoints;
    private final String checklistUUID;
    private final Pattern mePattern;

    public Create(AreasOfConcern areasOfConcern, Checkpoints checkpoints, String checklistUUID) {
        this.areasOfConcern = areasOfConcern;
        this.checkpoints = checkpoints;
        currAOC = null;
        currStandard = null;
        currME = null;
        this.checklistUUID = checklistUUID;
        mePattern = Pattern.compile("^([a-zA-Z][0-9]+\\.[0-9]+)(.*)");
        standardPattern = Pattern.compile("^([a-zA-Z][0-9]+)(.*)");
        aocPattern = Pattern.compile("^([a-zA-Z])(.*)");
    }

    private String getCleanedRef(List<String> cells, Pattern pattern, String replacement) {
        String ref = cells.get(0).replace(replacement, "").trim();
        ref = ref.replaceAll("\\s", "");
        Matcher matcher = pattern.matcher(ref);
        String cleanedRef = ref;
        if (matcher.find()) {
            cleanedRef = matcher.group(1);
        }
        return cleanedRef;
    }

    private String getAOCRef(List<String> cells) {
        return getCleanedRef(cells, aocPattern, "Area of Concern - ");
    }

    private String getMERef(List<String> cells) {
        return getCleanedRef(cells, mePattern, "ME");
    }

    private String getStandardRef(List<String> cells) {
        return getCleanedRef(cells, standardPattern, "Standard");
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
        if (!cells.isEmpty()) {

            if (cells.get(0).startsWith("Area of Concern - ")) {
                if (!emptyAreasOfConcern) {
                    String aocRefName = cells.get(0).replace("Area of Concern - ", "");
                    String ref = aocRefName.substring(0, 1);
                    String name = aocRefName.substring(2).trim();
                    currAOC = areasOfConcern.getAreasOfConcern().stream()
                            .filter((aoc) -> aoc.getReference().equalsIgnoreCase(ref))
                            .findFirst()
                            .get();
                } else {
                    aoc(cells);
                }

            } else if (cells.get(0).startsWith("Standard ") && cells.get(0).length() <= 15) {
                if (!emptyAreasOfConcern) {
                    String ref = getStandardRef(cells);
                    try {
                        currStandard = currAOC.getStandards().stream()
                                .filter((std) -> std.getReference()
                                        .equalsIgnoreCase(ref))
                                .findFirst()
                                .get();
                    } catch (NoSuchElementException e) {
                        standard(cells);
                    }
                } else {
                    standard(cells);
                }

            } else if (cells.get(0).startsWith("ME")) {
                if (!emptyAreasOfConcern) {
                    String finalCleanedRef = getMERef(cells);
                    try {
                        currME = currStandard.getMeasurableElements().stream()
                                .filter((me) -> me.getReference().equalsIgnoreCase(finalCleanedRef))
                                .findFirst().get();
                        if ((cells.size() > 2 && cells.get(2).trim().length() > 0)) {
                            checkpoint(cells.subList(2, cells.size()));
                        }
                    } catch (NoSuchElementException e) {
                        me(cells);
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
            checkpoint.setAmObservation(am.toLowerCase().contains("ob"));
            checkpoint.setAmPatientInterview(am.toLowerCase().contains("pi"));
            checkpoint.setAmRecordReview(am.toLowerCase().contains("rr"));
            checkpoint.setAmStaffInterview(am.toLowerCase().contains("si"));
            checkpoint.setDefault(true);
            checkpoint.setMeasurableElement(currME.getUuid());
            checkpoint.setChecklist(checklistUUID);
            if (cells.size() > 2) {
                checkpoint.setMeansOfVerification(cells.get(2));
            }
            checkpoints.addCheckpoint(checkpoint);
        } catch (Exception ignored) {
        }

    }

    private void me(List<String> cells) {
        MeasurableElement me = new MeasurableElement();
//        String ref = cells.get(0).replace("ME", "").trim();
        String ref = getMERef(cells);
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
        String ref = getStandardRef(cells);
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

package org.nhsrc;

import com.google.gson.Gson;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.nhsrc.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Collectors;

public class Main {

    private static AreasOfConcern areasOfConcern = new AreasOfConcern();
    private static Checkpoints checkpoints = new Checkpoints();
    private static Departments departments = new Departments();


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String dirPath = args[0];
        String assessmentTool = args[1];
        File[] files = new File(dirPath).listFiles((dir, name) -> name.endsWith(".xlsx") && !name.startsWith("~$"));
        Checklists checklists = new Checklists();
        for (File file : files) {
            String checklistUUID = UUID.randomUUID().toString();
            System.out.println("Checklist UUID - " + checklistUUID);
            System.out.println(file.getName());
            Checklist checklist = fileImport(file.getName(), dirPath, checklistUUID, assessmentTool);
            checklists.add(checklist);
        }
        Gson gson = new Gson();
        Files.write(Paths.get(dirPath + "/" + "areasOfConcern.json"), gson.toJson(Main.areasOfConcern.getAreasOfConcern()).getBytes());
        Files.write(Paths.get(dirPath + "/" + "checkpoints.json"), gson.toJson(Main.checkpoints.getCheckpoints()).getBytes());
        Files.write(Paths.get(dirPath + "/" + "checklists.json"), gson.toJson(checklists.getChecklists()).getBytes());
        Files.write(Paths.get(dirPath + "/" + "departments.json"), gson.toJson(departments.getDepartments()).getBytes());
    }

    private static Checklist fileImport(String inputFileName, String dirPath, String checklistUUID, String assessmentTool) throws IOException, ClassNotFoundException {

        FileInputStream inputStream = new FileInputStream(
                new File(dirPath + "/" + inputFileName));
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        boolean empty = areasOfConcern.isEmpty();
        Checklist checklist = new Checklist();
        Create create = new Create(areasOfConcern, checkpoints, checklistUUID);
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            create.create(currentRow, empty);
        }
        for (AreaOfConcern areaOfConcern : areasOfConcern.getAreasOfConcern()) {
            System.out.println("\"" + areaOfConcern.getUuid() + "\",");
        }
        checklist.setAreasOfConcern(
                areasOfConcern
                        .getAreasOfConcern()
                        .stream()
                        .map(AreaOfConcern::getUuid)
                        .collect(Collectors.toList()));
        checklist.setAssessmentTool(assessmentTool);
        checklist.setUuid(checklistUUID);
        Department department = Main.makeDepartment(sheet.getSheetName());
        checklist.setDepartment(department.getUuid());
        checklist.setName(department.getName());
        workbook.close();
        inputStream.close();
        return checklist;
    }

    private static Department makeDepartment(String name) {
        Department department = new Department();
        department.setUuid(UUID.randomUUID().toString());
        department.setName(name);
        departments.addDepartment(department);
        return department;
    }
}

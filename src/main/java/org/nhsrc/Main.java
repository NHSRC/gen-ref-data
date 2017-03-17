package org.nhsrc;

import com.google.gson.Gson;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream(
                new File("/Users/mihir/projects/nhsrc/docs/NQAS_DH_LABOUR_ROOM_CLEAN.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        AreasOfConcern areasOfConcern = new AreasOfConcern();
        Checkpoints checkpoints = new Checkpoints();
        Create create = new Create(areasOfConcern, checkpoints);
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            create.create(currentRow);
        }
        Gson gson = new Gson();
        Files.write(Paths.get("/Users/mihir/projects/nhsrc/docs/NQAS_DH_LABOUR_ROOM_CLEAN.json"), gson.toJson(areasOfConcern.getAreasOfConcern()).getBytes());
        Files.write(Paths.get("/Users/mihir/projects/nhsrc/docs/NQAS_DH_LABOUR_ROOM_CLEAN_CHECKPOINTS.json"), gson.toJson(checkpoints.getCheckpoints()).getBytes());
        for (AreaOfConcern areaOfConcern : areasOfConcern.getAreasOfConcern()) {
            System.out.println("\"" + areaOfConcern.getUuid() + "\",");
        }
        workbook.close();
        inputStream.close();
    }
}

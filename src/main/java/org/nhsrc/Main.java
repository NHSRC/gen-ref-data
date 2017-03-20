package org.nhsrc;

import com.google.gson.Gson;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class Main {


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String dirPath = args[0];
        String inputFileName = args[1];
        String outputFileName = inputFileName.replace(".xlsx", ".json");
        String outputCheckpointFileName = inputFileName.replace(".xlsx", "_checkpoints.json");
        FileInputStream inputStream = new FileInputStream(
                new File(dirPath + "/" + inputFileName));
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        String output = dirPath + "/aoc.ser";
        AreasOfConcern areasOfConcern = readAreasOfConcern(output);
        boolean empty = areasOfConcern.isEmpty();
        Checkpoints checkpoints = new Checkpoints();
        Create create = new Create(areasOfConcern, checkpoints);
        while (iterator.hasNext()) {
            Row currentRow = iterator.next();
            create.create(currentRow, empty);
        }
        Gson gson = new Gson();
        writeAOCs(output, areasOfConcern);
        Files.write(Paths.get(dirPath + "/" + outputFileName), gson.toJson(areasOfConcern.getAreasOfConcern()).getBytes());
        Files.write(Paths.get(dirPath + "/" + outputCheckpointFileName), gson.toJson(checkpoints.getCheckpoints()).getBytes());
        for (AreaOfConcern areaOfConcern : areasOfConcern.getAreasOfConcern()) {
            System.out.println("\"" + areaOfConcern.getUuid() + "\",");
        }
        workbook.close();
        inputStream.close();
    }

    private static AreasOfConcern readAreasOfConcern(String s) throws ClassNotFoundException, IOException {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(new File(s)));
            return (AreasOfConcern) objectInputStream.readObject();
        } catch (IOException e) {
            return new AreasOfConcern();
        } finally {
            try {
                objectInputStream.close();
            } catch (Exception e) {

            }

        }
    }

    private static void writeAOCs(String s, Object o) throws IOException {
        FileOutputStream fos = new FileOutputStream(s);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(o);
        oos.close();
    }
}

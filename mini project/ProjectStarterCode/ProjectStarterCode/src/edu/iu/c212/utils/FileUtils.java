package edu.iu.c212.utils;

import edu.iu.c212.models.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    private static String resource_directory = System.getProperty("user.dir") + "/ProjectStarterCode/src/edu/iu/c212/resources";
    private static File inputFile = new File(resource_directory + "/input.txt");
    private static File outputFile = new File(resource_directory + "/output.txt");
    private static File inventoryFile = new File(resource_directory + "/inventory.txt");
    private static File staffFile = new File(resource_directory + "/staff.txt");
    private static File staffAvailabilityFile = new File(resource_directory + "/staff_availability_IN.txt");
    private static File shiftSchedulesFile = new File(resource_directory + "/shift_schedules_IN.txt");
    private static File storeScheduleFile = new File(resource_directory + "/store_schedule_OUT.txt");

    public static List<Item> readInventoryFromFile() throws IOException {
        System.out.println(inventoryFile.toURI().getPath() + "\n" + inventoryFile.exists());
        System.out.println(System.getProperty("user.dir"));
        // depending on your OS, toURI() may need to be used when working with paths
        return null;
    }

    public List<Staff> readStaffFromFile() throws IOException {
        System.out.println(staffFile.toURI().getPath() + "\n" + staffFile.exists());
        System.out.println(System.getProperty("user.dir"));
        return null;
    }

    public static void writeInventoryToFile(List<Item> items){
        List<String> lines = new ArrayList<>();
        for (Item item : items) {
            String line = item.getName() + "," + item.getPrice() + "," + item.getQuantity() + "," + item.getAisle();
            lines.add(line);
        }
        writeLineToOutputFile("inventory.txt");

    }

    public static void writeStaffToFile(List<Staff> employees) {
        // TODO
    }

    public static List<String> readCommandsFromFile() throws IOException {
        // TODO
        return null;
    }

    public static void writeStoreScheduleToFile(List<String> lines) {
        // TODO
    }

    public static void writeLineToOutputFile(String line) {
        // TODO
    }

}

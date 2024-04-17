import edu.iu.c212.models.Item;
import edu.iu.c212.models.Staff;
import edu.iu.c212.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static edu.iu.c212.utils.FileUtils.readInventoryFromFile;

public class Store implements IStore {
    //should also call takeAction() and run through the input file
    private List<Item> item;
    private List<Staff> staff;
    public Store(){
        try{
            item = readInventoryFromFile();
        } catch (Exception e){
            System.out.println("unable to load files");
        }
    }

    public void saveItemsFromFile(){
        try {
            FileUtils.writeInventoryToFile(item);
        } catch (Exception e) {
            System.err.println("Error saving items to file: " + e.getMessage());
            System.exit(1);
        }

    }
    public void saveStaffFromFile(){

    }
    public String findCost(String itemName){
        int index = -1;
        if(item.contains(itemName)){
            index = item.indexOf(itemName);
        }
        return itemName + " $" + item.get(index).getPrice();
    }

    //@Override
    public List<Item> getItemsfromFile(){
        List<Item> item = new ArrayList<>();
        try{
            return FileUtils.readInventoryFromFile();
        } catch(Exception e){
            System.err.println("Error reading items from file: " + e.getMessage());
            System.exit(1);
        }
        return null;
        /*try {
            List<String> lines = FileUtils.readInventoryFromFile("inventory.txt");
            for (String line : lines){
                String[] parts = line.split(",");
                String itemName = parts[0];
                int itemQuantity = Integer.parseInt(parts[1]);
                double itemCost = Double.parseDouble(parts[2]);
                int itemAisle = Integer.parseInt(parts[3]);
                Item item = new Item(itemName, itemQuantity, itemCost, itemAisle);
                items.add(item);
            }
        } catch (IOException e){
            System.err.println("Error reading from file: " + e.getMessage());
            System.exit(1);
        }*/
    }

    //@Override
    public List<Staff> getStaffFromFile(){
        List<Staff> staff = new ArrayList<>();
        /*try {
            List<String> lines = FileUtils.readStaffFromFile("staff_availability_IN.txt");
            for(String line : lines){
                String[] parts = line.split("");
            }
        }*/
        return staff;
    }
    void takeAction(){
        //This loads in the inventory and staff information, reads from the input file and takes
        //the correct actions, then finally asks the user to hit Enter to end the program when
        //they’re finished.

        //Commands can be interpreted within Store’s takeAction() method. Use the method from
        //FileUtils you write to get a List of String s, where each String is one line/command from
        //the input. Then, for each String, determine which command it is attempting to use and its
        //arguments and call the appropriate method in your code
    }

}

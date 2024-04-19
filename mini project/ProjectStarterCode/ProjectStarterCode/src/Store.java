package edu.iu.c212;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.Staff;
import edu.iu.c212.programs.SawPrimePlanks;
import edu.iu.c212.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static edu.iu.c212.utils.FileUtils.readCommandsFromFile;
import static edu.iu.c212.utils.FileUtils.readInventoryFromFile;

public class Store implements IStore {
    //should also call takeAction() and run through the input file
    //concretely implements IStore
    //implement IStore methods
    private List<Item> items;

    public Store(){
        takeAction();
    }

    public void saveItemsFromFile() {
        try {
            FileUtils.writeInventoryToFile(this.items);
        }catch (Exception e){
            System.exit(0);
        }
    }
    public void saveStaffFromFile(){

    }
    public String findCost(String itemName){
        //System.out.println("Finding cost for item="+ itemName);
        String response = "";
        for(Item item : this.items){
            //if(item.getName().equalsIgnoreCase("'"+itemName+"'")) {
            if(item.getName().equalsIgnoreCase(itemName)) {
                response += item.getPrice();
                break;
            }
        }
        return response;
    }

    @Override
    public List<Item> getItemsFromFile(){
        List<Item> items = new ArrayList<>();
        return items;
    }

    @Override
    public List<Staff> getStaffFromFile(){
        List<Staff> staff = new ArrayList<>();
        return staff;
    }
    @Override
    public void takeAction(){
        //This loads in the inventory and staff information, reads from the input file and takes
        //the correct actions, then finally asks the user to hit Enter to end the program when
        //they’re finished.

        //Commands can be interpreted within Store’s takeAction() method. Use the method from
        //FileUtils you write to get a List of String s, where each String is one line/command from
        //the input. Then, for each String, determine which command it is attempting to use and its
        //arguments and call the appropriate method in your code
        try{
            this.items = FileUtils.readInventoryFromFile(); //need to do the same for staff
            System.out.println("Completing loading inventory into the memory...");

            List<String> commands = FileUtils.readCommandsFromFile();
            for(String command : commands){
                //System.out.println("COMMAND=="+command);
                boolean result = takeActionHelper(command);
            }

        } catch (IOException e){
            System.out.println("Problem occurred...");
            System.exit(0);
        }
    }

    private boolean takeActionHelper(String cmd) throws IOException{
        boolean r = false;
        int index = cmd.indexOf(" ");
        index = (index==-1 ? cmd.length() : index);
        String c = cmd.substring(0,index);
        //System.out.println("cmd="+cmd);
        switch (c) {
            case "ADD":
                //executing ADD item
                System.out.println("Executing ADD command.");
                String regex_ADD = "ADD\\s+‘([^‘’]+)’\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)";
                Pattern pattern_ADD = Pattern.compile(regex_ADD);
                Matcher matcher_ADD = pattern_ADD.matcher(cmd);
                if(matcher_ADD.matches()){
                    String name = matcher_ADD.group(1);
                    double price = Double.parseDouble(matcher_ADD.group(2));
                    int qty = Integer.parseInt(matcher_ADD.group(3));
                    int aisleNum = Integer.parseInt(matcher_ADD.group(4));
                    Item item = new Item("'"+name+"'", price, qty, aisleNum);
                    //System.out.println("ADDing item... "+item.toString());
                    this.items.add(item);   //Add to the memory
                    saveItemsFromFile();    //Save Items to the Inventory File
                    FileUtils.writeLineToOutputFile(name+" was added to inventory");
                }
                break;
            case "COST":
                // executing cost
                System.out.println("Executing COST command.");
                String response = "";
                String regex_COST = "^COST\\s+‘([^’]+)’$";
                Pattern pattern_COST = Pattern.compile(regex_COST);
                Matcher matcher_COST = pattern_COST.matcher(cmd);
                if(matcher_COST.matches()){
                    String itemName = matcher_COST.group(1);
                    //System.out.println("Item Name="+itemName);
                    String resp = findCost("'"+itemName+"'");
                    double ret = Double.parseDouble(resp);
                    FileUtils.writeLineToOutputFile(itemName+": $"+String.format("%.0f",ret));
                }
                break;
            case "EXIT":
                //execute exit
                FileUtils.writeLineToOutputFile("Thank you for visiting High's Hardware and Gardening!");
                System.out.println("Press enter to continue...");
                try {
                    System.in.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
                break;
            case "FIND":
                //execute find
                System.out.println("Executing FIND command. ");
                String itemName="";
                String res_find = "";
                String regex_FIND = "FIND\\s+‘([^‘’]+)’";
                Pattern pattern_FIND = Pattern.compile(regex_FIND);
                Matcher matcher_FIND = pattern_FIND.matcher(cmd);
                if(matcher_FIND.matches()){
                    itemName = matcher_FIND.group(1);
                    //System.out.println("FIND..... item name="+itemName);
                    for(Item item : this.items){
                        //if(item.getName().equalsIgnoreCase("'"+itemName+"'")) {
                        if(item.getName().equalsIgnoreCase(itemName)) {
                            res_find = item.getQuantity()+" "+item.getName()+" are available in "+item.getAisle();
                            break;
                        }
                    }
                    if(res_find=="")
                        res_find="ERROR: "+itemName + " cannot be found";
                    FileUtils.writeLineToOutputFile(res_find);
                }
                break;
            case "FIRE":
                //execute fire
            case "HIRE":
                // execute hire
            case "PROMOTE":
                // execute promote
            case "SAW":
                //execute saw
                System.out.println("Executing SAW command.");
                List<Item> itemsToRemove = new ArrayList<>();
                List<Integer> lst = new ArrayList<>();
                boolean found = false;
                for(Item item : this.items){
                    //if(item.getName().equalsIgnoreCase("'"+itemName+"'")) {
                    //System.out.println("Item Name="+item.getName()+ " try to sell="+name);
                    String pname = item.getName();
                    boolean ind = pname.startsWith("Plank-");
                    if(ind) {
                        int plen = Integer.parseInt(pname.substring(pname.indexOf('-')));
                        List<Integer> ls = SawPrimePlanks.getPlankLengths(plen);
                        int length = ls.get(0);
                        String new_name = "Plank-" + length;
                        double new_price = length * length;
                        int new_quantity = ls.size() * item.getQuantity();
                        int new_aisle = item.getAisle();
                        Item it = new Item("'" + new_name + "'", new_price, new_quantity, new_aisle);
                        this.items.add(it);
                        saveItemsFromFile();
                        found = true;
                        itemsToRemove.add(item);
                        this.items.removeAll(itemsToRemove);
                        break;
                    }if(found){
                        saveItemsFromFile();
                        FileUtils.writeLineToOutputFile("Planks sawed.");
                    }
                }
                break;

            case "SCHEDULE":
                //execute schedule
            case "SELL":
                // execute sell
                System.out.println("Executing SELL command.");
                String res_sell = "";
                String regex_SELL = "SELL\\s+‘([^‘’]+)’\\s+(\\d+)";
                Pattern pattern_SELL = Pattern.compile(regex_SELL);
                Matcher matcher_SELL = pattern_SELL.matcher(cmd);
                if(matcher_SELL.matches()) {
                    String name = matcher_SELL.group(1);
                    String qty = matcher_SELL.group(2);
                    int sold = Integer.parseInt(qty);
                    //System.out.println("Trying to sell--"+name+ " qty="+qty);
                    for(Item item : this.items){
                        //if(item.getName().equalsIgnoreCase("'"+itemName+"'")) {
                        //System.out.println("Item Name="+item.getName()+ " try to sell="+name);
                        if(item.getName().equalsIgnoreCase("'"+name+"'")) {
                            if (item.getQuantity() >= sold) {
                                int avl = item.getQuantity();
                                item.setQuantity(avl - sold);
                                res_sell = qty+ " "+ name+ " was sold";
                            }
                            break;
                        }
                    }
                    if(res_sell=="")
                        res_sell="ERROR: "+name + " could not be sold";
                    else
                        saveItemsFromFile();    //Save Items to the Inventory File
                    FileUtils.writeLineToOutputFile(res_sell);
                }
                break;
            case "QUANTITY":
                // execute quantity
                System.out.println("Executing QUANTITY command. ");
                String res_qty = "";
                String regex_QTY = "QUANTITY\\s+‘([^‘’]+)’";
                Pattern pattern_QTY = Pattern.compile(regex_QTY);
                Matcher matcher_QTY = pattern_QTY.matcher(cmd);

                if(matcher_QTY.matches()){
                    String name_qty = matcher_QTY.group(1);
                    for(Item item : this.items){
                        //if(item.getName().equalsIgnoreCase("'"+itemName+"'")) {
                        if(item.getName().equalsIgnoreCase("'"+name_qty+"'")) {
                            res_qty = String.valueOf(item.getQuantity());
                            break;
                        }
                    }
                    if(res_qty=="")
                        res_qty="ERROR: "+name_qty + " cannot be found";
                    FileUtils.writeLineToOutputFile(res_qty);
                }
                break;
            default:
                //default
                System.out.println("Please enter a valid command...");
        }
        return r;
    }

}

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static edu.iu.c212.programs.SawPrimePlanks.sawPlank;

public class StoreMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("in main");
        boolean run = true;
        Store myStore = new Store();
        while (run) {
            System.out.print("Enter a command (type 'EXIT' to quit): ");
            String input = scanner.nextLine();
            System.out.println("input = "+ input);
            String[] in = input.split("\\s+");
            System.out.println(in[0] + "-" + in[1] + "-" + in[2] + "-" + in[3]);

            // Process the input
            if (in.length >= 0) {
                run = executeCommand(in);
            }
        }
        // Close the scanner
        //scanner.close();

    }

    public static Boolean executeCommand(String[] args) {
        String cmd = args[0];
        boolean r = true;
        switch (cmd) {
            case "ADD":
                //executing ADD
                break;

            case "COST":
                // executing cost
                System.out.println("command length=" + cmd.length());
                break;
            case "EXIT":
                //execute exit
                if (cmd.equalsIgnoreCase("EXIT")) {
                    System.out.println("Press enter to continue...");
                    r = false;
                    break; // Exit the loop
                }

            case "FIND":
                //execute find
            case "FIRE":
                //execute fire
            case "HIRE":
                // execute hire
            case "PROMOTE":
                // execute promote
            case "SAW":
                //execute saw
            case "SCHEDULE":
                //execute schedule
            case "SELL":
                // execute sell
            case "QUANTITY":
                // execute quantity
            default:
                //default
                System.out.println("Please enter a valid command...");
        }
        return r;
    }
}

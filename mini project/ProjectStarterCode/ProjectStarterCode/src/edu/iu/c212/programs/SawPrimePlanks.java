package edu.iu.c212.programs;

import java.util.ArrayList;
import java.util.List;

public class SawPrimePlanks{

    public static boolean isPrime(int number) {
        //Base cases
        if (number <= 1) return false;
        if (number <= 3) return true;
        // checking divisibility by 2 and 3
        if (number % 2 == 0 || number % 3 == 0) return false;
        //check divisibility for all numbers that are in the form 6k +- 1
        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) return false;
        }
        return true;
    }

    public static List<Integer> getPlankLengths(int longPlankLength){
        List<Integer> result = new ArrayList<>();
        //if the long plank length itself is prime, return it
        if (isPrime(longPlankLength)) {
            result.add(longPlankLength);
        } else {
            //find the smallest prime factor and saw recursively
            for (int i = 2; i <= longPlankLength / 2; i++) {
                if (longPlankLength % i == 0 && isPrime(i)) {
                    int count = longPlankLength / i;
                    List<Integer> subResults = getPlankLengths(i);
                    // multiply prime plank lengths to get the composite length
                    for (Integer length : subResults) {
                        for (int j = 0; j < count; j++) {
                            result.add(length);
                        }
                    }
                    break; // when the first valid prime is found, break to ensure the longest planks.
                }
            }
        }
        return result;

    }
    public static int sawPlank(int plankLength){
        List<Integer> result = new ArrayList<>();

        // If the plank length is already prime, add it directly to the result
        if (isPrime(plankLength)) {
            result.add(plankLength);
            return (int) result;
        }

        // Try dividing the plank by each increasing integer (starting from 2) to find prime lengths
        for (int factor = 2; factor <= plankLength; factor++) {
            if (plankLength % factor == 0 && isPrime(factor)) { // Check if the factor is prime and a divisor
                int count = plankLength / factor; // How many planks of this size?
                List<Integer> subPlanks = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    subPlanks.addAll(sawPlank(factor)); // Recursively saw each of these sub-planks
                }
                result.addAll(subPlanks);
                break; // Stop after successful division by the smallest prime factor
            }
        }
        return new ArrayList<>();
    }
}

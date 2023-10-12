package com.example.demo;

import java.util.*;
import java.io.*;

public class UnitConverter<T> {
    private ArrayList<Unit<T>> units = new ArrayList<>();
    private ArrayList<String> history = new ArrayList<>();

    public void addUnit(Unit<T> unit) {
        units.add(unit);
    }

    public void sortUnits() {
        // Sorts the list of units using insertion sort.
        //Insertion sort is well-suited for small lists like this one because of its
        //simplicity and lower overhead compared to more complex sorting algorithms
        for (int i = 1; i < units.size(); i++) {
            Unit<T> currentUnit = units.get(i);
            int j = i - 1;

            while (j >= 0 && units.get(j).getName().compareTo(currentUnit.getName()) > 0) {
                units.set(j + 1, units.get(j));
                j--;
            }

            units.set(j + 1, currentUnit);
        }
    }


    public T convert(T value, String fromUnitName, String toUnitName) {
        Unit<T> fromUnit = findUnitByName(fromUnitName);
        Unit<T> toUnit = findUnitByName(toUnitName);

        if (fromUnit != null && toUnit != null) {
            T result = toUnit.convertToUnit(value, fromUnit);
            if (result != null) {
                history.add(value + " " + fromUnitName + " to " + result + " " + toUnitName);
                return result;
            }
        }
        return null;
    }

    private Unit<T> findUnitByName(String name) {
        for (Unit<T> unit : units) {
            if (unit.getName().equalsIgnoreCase(name)) {
                return unit;
            }
        }
        return null;
    }

    public void saveHistoryToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            for (String entry : history) {
                writer.write(entry);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving history to file: " + e.getMessage());
        }
    }

    public List<String> getHistory() {
        return history;
    }
    public void readHistoryFromFile(String fileName) {
        history.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading history from file: " + e.getMessage());
        }
        Collections.sort(history); // Sort the history list
    }

    public static void main(String[] args) {
        UnitConverter<Double> converter = new UnitConverter<>();
        Scanner scanner = new Scanner(System.in);

        converter.addUnit(new Foot());
        converter.addUnit(new Inch());
        converter.addUnit(new Meter());
        converter.addUnit(new Yard());

        boolean stop = false;

        while (!stop) {
            // Get value to convert from user input
            System.out.print("Enter a value to convert (or type 'stop' to stop the program): ");
            String input = scanner.nextLine();
            if (input.equals("stop")) {
                stop = true;
            } else {
                double value = Double.parseDouble(input);

                // Get original unit from user
                System.out.print("Enter the original unit (Inch, Foot, Meter, Yard): ");
                String fromUnit = scanner.nextLine();

                // Get the new unit from user
                System.out.print("Enter the new unit (Inch, Foot, Meter, Yard): ");
                String toUnit = scanner.nextLine();

                Double result = converter.convert(value, fromUnit, toUnit);

                if (result != null) {
                    System.out.println(value + " " + fromUnit + " is equal to " + result + " " + toUnit);
                } else {
                    System.out.println("Invalid units or conversion");
                }
            }
        }

        // Save history to the file
        converter.saveHistoryToFile("UnitHistory.txt");

        // Call the sortUnits method before displaying previous conversions
        converter.sortUnits();

        // Read and display previous conversions
        System.out.println("Previous Conversions:");
        converter.readHistoryFromFile("UnitHistory.txt");

        for (String entry : converter.getHistory()) {
            System.out.println(entry);
        }
    }
}
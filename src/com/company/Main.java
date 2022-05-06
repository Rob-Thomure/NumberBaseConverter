package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            String[] input = scanner.nextLine().trim().split("\\s+");
            if (!input[0].equals("/exit")) {
                int sourceBase = Integer.parseInt(input[0]);
                int targetBase = Integer.parseInt(input[1]);
                boolean goBack = false;
                while (!goBack) {
                    scanner = new Scanner(System.in);
                    System.out.printf("Enter number in base %d to convert to base %d (To go back type /back) ",
                            sourceBase, targetBase);
                    String number = scanner.nextLine().trim();
                    if (!number.equals("/back")) {
                        String bigDecimal = Converters.convertNumber(number, sourceBase, targetBase);
                        System.out.printf("Conversion result: %s%n%n", bigDecimal);
                    } else {
                        goBack = true;
                    }
                }
            } else {
                exit = true;
            }
        }
    }
}

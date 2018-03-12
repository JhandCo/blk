package com.example.jamarkushodge.blk;


import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by jamarkushodge on 2/17/18.
 */

public class readfromJson {

    public String loadData(String filename) {

        Scanner scanner = new Scanner(filename);
        String field;
        Figure figure;
        String fields = " " ;

        while (scanner.hasNextLine()) {
            field = scanner.nextLine();
            fields += field;
        }

        scanner.close();
        return fields;
    }
}
package main.java;

import java.io.File;

public class Main {
    final static File logFolderForJar = new File("logging");
    
    public static void main(String[] args) {
        
        logFolderForJar.mkdir();
        System.setProperty("java.util.logging.SimpleFormatter.format","%1$tF %1$tT: %5$s %n");   //setting format properties for the time and date for the logging
        InputOutputManager inputOutputManager = new InputOutputManager();
        inputOutputManager.inputAndOutput();        
    }
}

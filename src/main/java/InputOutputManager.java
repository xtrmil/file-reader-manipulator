package main.java;

import java.util.Scanner;

public class InputOutputManager {
    
    FileManager fileManager = new FileManager();
    
    public void inputAndOutput() {

        Scanner scan = new Scanner(System.in);
        System.out.println();
        System.out.println("[Input 1] List all files in the resources folder.");
        System.out.println("[Input 2] List all files of a certain type in resources folder.");
        System.out.println("[Input 3] Show the name of the txt file.");
        System.out.println("[Input 4] Show how big the txt file is.");
        System.out.println("[Input 5] Show how many lines the txt file has.");
        System.out.println("[Input 6] Search for a specific word in the text file.");
        System.out.println("[Input 7] Exit application.");
       
        if(!scan.hasNextInt()){
            System.out.println();
            System.out.println("!!!!!Invalid input, please input an Integer in the range 1-7!!!!");
            inputAndOutput();
        }
            int option = scan.nextInt();

        switch (option) {

            case 1:
                fileManager.listFiles("all");
                inputAndOutput();
                break;

            case 2:
                System.out.println("Enter what typ of file you want to list:");
                scan.nextLine();
                String fileType = scan.nextLine();
                while (fileType == null) {

                }
                fileManager.listFiles(fileType);
                inputAndOutput();
                break;

            case 3:
                fileManager.nameOfTextFile();
                inputAndOutput();
                break;

            case 4:
                fileManager.sizeOfFile();
                inputAndOutput();

            case 5:
                fileManager.numberOfLinesInFile();
                inputAndOutput();
                break;

            case 6:
                System.out.println("Input a word you want to search for:");

                scan.nextLine();
                String searchWord = scan.nextLine();
                while (searchWord == null) {

                }
                fileManager.searchTextFile(searchWord);
                inputAndOutput();
                break;
            
            case 7:
                System.exit(0);
                break;
            
            default:
                System.out.println("!!!!invalid option, please input an integer between 1-7!!!!");
                inputAndOutput();
                break;
        }
        scan.close();
    }
}

package main.java;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.net.URL;
import java.security.CodeSource;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileManager {

    private long startTime, endTime;
    private String localDir = System.getProperty("user.dir");
    private File file = new File(localDir + "\\src\\main\\resources\\Dracula.txt");
    private String fileName = "main/resources/Dracula.txt";
    private String resourceFolderPath = "main/resources/";
    private int fileSize;
    ClassLoader classLoader = getClass().getClassLoader();

    protected void listFiles(String filter) {
        int nrOfFoundFiles = 0;
        try{ CodeSource source = FileManager.class.getProtectionDomain().getCodeSource();
            
            if (source != null) {
                
                URL jar = source.getLocation();
                ZipInputStream zip = new ZipInputStream(jar.openStream());     //Used to search through the jar file for resourcefolder 
                while(true) {
                    
                    ZipEntry e = zip.getNextEntry();
                    if (e == null)
                    break;
                    String name = e.getName();

                    if(filter.equals("all")){                                   //condition for listing all files
                    
                        if (name.startsWith(resourceFolderPath)) {              //condition for only listing files in resourcefolder
                            System.out.println(name.substring(resourceFolderPath.length(),name.length()));      // Substring is used to exclude the path from the print
                            nrOfFoundFiles++;
                        }

                    }else{

                        if(name.endsWith(filter)){                              // Condition for listing certaing filtypes
                            System.out.println(name.substring(resourceFolderPath.length(),name.length()));
                        
                            nrOfFoundFiles++;
                        }
                    }
                }
            } 
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        if(nrOfFoundFiles == 0 ){
            System.out.println("No " + filter + " files where found.");
        }
    }

    protected void nameOfTextFile(){
        startTimer();
        String fileName = file.getName();   // this line is added to have something to measure excecutiontime on instead of passing file.getName() straight to the log =)
        System.out.println(fileName);
        endTimer();
        LogManager.writeToLog("The name of the txt file is: "+ fileName + " :: Operation execution time: " + executionTime()+" ms");
    }

    protected void sizeOfFile(){
        
        startTimer();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName);) {
            
            int data = inputStream.read();
            int byteCount = 0;
    
            while(data != -1) {

                byteCount++;
    
                data = inputStream.read();
            }
            fileSize = byteCount;
            
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        endTimer();
        LogManager.writeToLog("The file is " + fileSize + " bytes, " + fileSize / 1024.0 + " kilobytes " + " :: Operation execution time: " + executionTime()+" ms");
    }

    protected void numberOfLinesInFile(){

        startTimer();
        
        try(InputStream inputStream = classLoader.getResourceAsStream(fileName);){
            InputStream initialStream = new ByteArrayInputStream(new byte[] { 0, 1, 2 });
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int read;
            byte[] data = new byte[1024];
            while ((read = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, read);
            }
         
            buffer.flush();
            byte[] byteArray = buffer.toByteArray();
            initialStream.read(byteArray);
            String fileData = new String(byteArray);
            String[] stringArray = fileData.split("\r\n");

        endTimer();
        
        LogManager.writeToLog("Number of lines in the file are: " + stringArray.length + " :: Operation execution time: "+ executionTime()+" ms");
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    protected void searchTextFile(String searchWord) {
        
        startTimer();
        int count;
        try(InputStream inputStream = classLoader.getResourceAsStream(fileName);){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StreamTokenizer tokenizer = new StreamTokenizer(bufferedReader);  
            tokenizer.wordChars('!', '-');
            count=0;           
            while(tokenizer.nextToken() != StreamTokenizer.TT_EOF)
            {
                if(tokenizer.ttype == StreamTokenizer.TT_WORD
                        && (tokenizer.sval.contains(searchWord)                                                              
                        || (tokenizer.sval.contains(searchWord.toLowerCase()))                                              // Conditions to not miss any words if input differs
                        || tokenizer.sval.contains(searchWord.toUpperCase())                                                // from how the word is written in the file(Upper/Lower cases)
                        || tokenizer.sval.contains(searchWord.substring(0, 1).toUpperCase() + searchWord.substring(1)))){   //
                    count++;
                }
            }
            endTimer();
            LogManager.writeToLog("The word: " + searchWord + " is found " + count + " times" + " :: Operation execution time: "+ executionTime()+" ms");
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void startTimer() {
        startTime = System.nanoTime();
    }

    private void endTimer() {
        endTime = System.nanoTime();
    }

    private long executionTime() {

        return (endTime - startTime) / 1000000;
    }

}

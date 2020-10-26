package main.java;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogManager{
    final static Logger logger = Logger.getLogger(LogManager.class.getName());
    protected static FileHandler fileHandler;   
    
    static {
        try{
            fileHandler = new FileHandler("logging/infolog.log", true);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter); 

        }catch (IOException | SecurityException e){

        }
    }

    protected static void writeToLog(String logPost){

        logger.info(logPost);
    }
}

package game;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles I/O for text files
 *
 * @author Matthew Ritchie
 * @version 1.0
 */
public class FileHandler {

    private Scanner scanner;
    private final InputStream fileStream;

    /**
     * Initializes file handler to new file path
     * @param filePath path of file to assign to handler
     */
    public FileHandler(String filePath) {
        fileStream = getClass().getResourceAsStream(filePath);
        assert fileStream != null;
    }

    /**
     * Reads lines from text file to ArrayList
     * @return returns ArrayList with lines from text file
     */
    public ArrayList<String> readLines() {
        scanner = new Scanner(fileStream);
        ArrayList<String> list = new ArrayList<>();

        while(scanner.hasNextLine()) {
            list.add(scanner.nextLine());
        }

        scanner.close();
        return list;
    }

}

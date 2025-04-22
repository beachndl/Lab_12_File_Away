import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;

public class FileInspector {
    public static void main(String[] args) {
        // JFileChooser + variable declaration
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";

        // Variables to track line, word, and character count
        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;

        // Try mechanism
        try {
            // Use the toolkit to get the current working directory
            File workingDirectory = new File(System.getProperty("user.dir"));

            // Set the current directory into the src folder within the project
            File srcDirectory = new File(workingDirectory.getPath() + File.separator + "src");
            chooser.setCurrentDirectory(srcDirectory);

            // Display file choose dialog
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                // Print basic information
                System.out.println("\nReading File: " + selectedFile.getName());
                System.out.println("-------------------------\n");

                // Create BufferedReader
                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                // Read file line by line
                while (reader.ready()) {
                    rec = reader.readLine();
                    lineCount++;

                    // Count words using split
                    // Using \\s+ instead of " " in case of multiple spaces or other whitespace characters
                    // https://stackoverflow.com/questions/15625629/regex-expressions-in-java-s-vs-s
                    String[] words = rec.split("\\s+");
                    wordCount += words.length;

                    // Count characters
                    charCount += rec.length();

                    // Print to screen
                    System.out.printf("Line %2d: %s\n", lineCount, rec);
                }

                // Close the file
                reader.close();

                // Print summary
                System.out.println("\n-------------------------");
                System.out.println("FILE SUMMARY REPORT");
                System.out.println("-------------------------");
                System.out.println("File name: " + selectedFile.getName());
                System.out.println("Number of lines: " + lineCount);
                System.out.println("Number of words: " + wordCount);
                System.out.println("Number of characters: " + charCount);
                System.out.println("-------------------------");

            } else {
                System.out.println("No file selected! Exiting program.");
                System.out.println("Run the program again and select a file.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IO Exception occurred!");
            e.printStackTrace();
        }
    }
}
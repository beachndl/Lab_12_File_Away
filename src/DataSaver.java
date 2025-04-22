import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class DataSaver {
    public static void main(String[] args) {
        // Scanner + variable declaration
        Scanner in = new Scanner(System.in);
        ArrayList<String> records = new ArrayList<>();
        boolean done = false;

        // ID counter starting at 1
        int idCounter = 1;

        // Using prettyHeader method from SafeInput.java to create the header
        SafeInput.prettyHeader("Data Saver Program");

        // Loop to enter records
        while (!done) {
            // Print basic information
            System.out.println("\n\nEntering Record #" + idCounter);

            // Using getNonZeroLenString method from SafeInput.java to get user's first + last name
            String firstName = SafeInput.getNonZeroLenString(in, "Enter First Name");
            String lastName = SafeInput.getNonZeroLenString(in, "Enter Last Name");

            // ID Number (a zero replaced string of 6 digits 000001, 000002, etc.)
            // https://stackoverflow.com/questions/43807479/how-can-i-create-string-with-number-in-java
            String idNum = String.format("%06d", idCounter);

            // Using getRegExString method from SafeInput.java to get user's email
            String email = SafeInput.getRegExString(in, "Enter Email", "^[A-Za-z0-9+_.-]+@(.+)$");

            // Using getRangedInt method from SafeInput.java to get user's birth year
            // Limited between 1000 - 2025 (must be a four-digit number, up to the current year)
            int yearOfBirth = SafeInput.getRangedInt(in, "Enter Year of Birth", 1000, 2025);

            // Create CVS record
            String record = firstName + ", " + lastName + ", " + idNum + ", " + email + ", " + yearOfBirth;

            // Add record to ArrayList
            records.add(record);

            // Increment ID counter for next record
            idCounter++;

            // Print basic information
            System.out.println("\rRecord saved.");

            // Ask user for another record
            // Using getNonZeroLenString method from SafeInput.java to get user's input
            String userInput = SafeInput.getNonZeroLenString(in, "Do you want to add another record? (Y/N)");
            if (userInput.equalsIgnoreCase("N")) {
                done = true;
            }
        }

        // Save records to file
        if (!records.isEmpty()) {
            // Using getNonZeroLenString method from SafeInput.java to get file name from user
            String fileName = SafeInput.getNonZeroLenString(in, "Enter file name (without extension)");

            // Add .cvs extension
            fileName += ".csv";

            try {
                // Use the toolkit to get the current working directory
                File workingDirectory = new File(System.getProperty("user.dir"));

                // Set file path to src directory
                File srcDirectory = new File(workingDirectory.getPath() + File.separator + "src");
                Path filePath = Paths.get(srcDirectory.getPath() + File.separator + fileName);

                // Create BufferedWriter
                OutputStream out = new BufferedOutputStream(Files.newOutputStream(filePath, CREATE));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

                // Write each record to the file
                for (String rec : records) {
                    writer.write(rec, 0, rec.length());
                    writer.newLine();
                }

                // Close the file
                writer.close();

                // Print basic information
                System.out.println("\nData successfully saved to " + fileName + " in the src directory.");

            } catch (IOException e) {
                System.out.println("Error writing to file!");
                e.printStackTrace();
            }
        } else {
            System.out.println("\nNo records to save. Program ending.");
        }
    }
}
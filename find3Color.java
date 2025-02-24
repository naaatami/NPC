// javac find3Color.java
// java find3Color graphs2025.txt
// Natalie Simova, Bennett Beltran, Shane Burke

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class find3Color {
    public static void main(String[] args) {
        if (args.length == 0)
        {
            System.out.println("ERROR: Please provide the text file you'd like to use.");
            return;
        }
        String graphFile = args[0]; 
        System.out.println("** Find find3Color in " + graphFile);
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(graphFile));
            String line = reader.readLine();
         
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
    
}


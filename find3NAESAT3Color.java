// javac find3NAESAT3Color.java
// java find3NAESAT3Color cnfs2025.txt
// Natalie Simova, Bennett Beltran, Shane Burke

public class find3NAESAT3Color {
    public static void main(String[] args) {
        if (args.length == 0)
        {
            System.out.println("ERROR: Please provide the text file you'd like to use.");
            return;
        }
        String cnfFile = args[0]; 
        System.out.println("** Find find3NAESAT3Color in " + cnfFile);
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(cnfFile));
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


import java.io.*;

class ComputationalBiology {
    private final static String INPUT_FILE = "INPUT.TXT";
    private final static String OUTPUT_FILE = "OUTPUT.TXT";
    private String answer;

    public static void main(String [] args) {
        ComputationalBiology program = new ComputationalBiology();
        program.readFileAndCalculate();
        program.writeToFile();
    }

    public void readFileAndCalculate(){
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
                String s = br.readLine();
                String t  = br.readLine();
                br.close();
                int matches = 0;
                for (int j = 0; j < t.length(); ++j) {
                    if (matches == s.length())
                        break;
                    else if(s.charAt(matches) == t.charAt(j) && matches < s.length()) {
                        ++matches;
                    }
                }
                answer = matches == s.length() ? "YES" : "NO";
            }
        } catch (FileNotFoundException e) {
            System.err.println("File " + INPUT_FILE + " not found!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeToFile() {
        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
                bw.write(answer);
                bw.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File " + OUTPUT_FILE + " not found!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task1 {
    private final static String INPUT_FILE = "INPUT.TXT";

    public static void main(String [] args) {
        Task1 task = new Task1();
        task.filterTextInformation();
    }

    public void filterTextInformation() {
        System.out.println("Enter arguments, please.");
        Scanner scanner = new Scanner(System.in);
        String[] arguments = scanner.nextLine().toString().split(" ");
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
                String in;
                while (br.ready()) {
                    in = br.readLine();
                    String[] words = in.split(" ");
                    boolean coincidence = false;
                    for (String args: arguments) {
                        if (coincidence == true) break;
                        for (String word : words) {
                            Pattern pattern = Pattern.compile(args);
                            String cleanWord = word.replace(";", "");
                            Matcher matcher = pattern.matcher(cleanWord);
                            if(matcher.find() && matcher.group().equals(cleanWord)) {
                                System.out.println(in);
                                coincidence = true;
                                break;
                            }
                        }
                    }
                }
            }
            catch (FileNotFoundException e) {
                System.err.println("File " + INPUT_FILE + " not found!");
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

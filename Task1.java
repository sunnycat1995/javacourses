import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task1 {
    private final static String INPUT_FILE = "INPUT.TXT";
    private Set<String> set = new HashSet<>();

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
                    for (String args: arguments) {
                        for (String word : words) {
                            Pattern pattern = Pattern.compile(args);
                            Matcher matcher = pattern.matcher(word.replace(";", ""));
                            if(matcher.find() && matcher.group().equals(word.replace(";", ""))) {
                                set.add(in);
                                break;
                            }
                        }
                    }
                }
                for (String entity: set) {
                    System.out.println(entity);
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

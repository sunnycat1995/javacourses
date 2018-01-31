import java.io.*;
import java.util.HashSet;
import java.util.Set;

class NumbersWithoutIdenticalDigits {
    private final static String INPUT_FILE = "INPUT.TXT";
    private final static String OUTPUT_FILE = "OUTPUT.TXT";
    private int inputNumber;
    private int outputNumber = 0;

    public static void main(String [] args) {
        NumbersWithoutIdenticalDigits program = new NumbersWithoutIdenticalDigits();
        program.readFile();
        program.findNumber();
        program.writeToFile();
    }

    public void readFile(){
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
                inputNumber = Integer.parseInt(br.readLine());
                br.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File " + INPUT_FILE + " not found!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findNumber() {
        int counter = 0;
        while (counter < inputNumber) {
            ++outputNumber;
            Set<Integer> set = new HashSet<>();
            int number = outputNumber;
            int numberLength = 0;
            while (number != 0) {
                set.add(number % 10);
                number = number / 10;
                ++numberLength;
            }
            if (numberLength == set.size())
                ++counter;
        }
    }

    public void writeToFile() {
        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
                bw.write(outputNumber + "");
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

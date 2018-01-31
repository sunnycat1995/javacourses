import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class SumModule {
    private final static String INPUT_FILE = "INPUT.TXT";
    private final static String OUTPUT_FILE = "OUTPUT.TXT";
    private final static ArrayList<Sequence> inputList = new ArrayList<>();
    private final static ArrayList<Sequence> outputListLeft = new ArrayList<>();
    private final static ArrayList<Sequence> outputListRight = new ArrayList<>();
    private static int leftSum = 0;
    private static int rightSum = 0;

    public static void main(String [] args) {
        SumModule sumModule = new SumModule();
        sumModule.readFile();
        ArrayList<Sequence> resultList = sumModule.calculateMaxSumModule();
        sumModule.writeToFile(resultList);
    }

    public void readFile(){
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
                String in;
                StringTokenizer st;
                br.readLine();
                while (br.ready()){
                    in = br.readLine();
                    int i = 1;
                    st = new StringTokenizer(in);
                    while (st.hasMoreTokens()) {
                        Sequence seq = new Sequence();
                        seq.setElement(Integer.parseInt(st.nextToken()));
                        seq.setIndex(i);
                        inputList.add(seq);
                        ++i;
                    }
                }
                Collections.sort(inputList);
                br.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File " + INPUT_FILE + " not found!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Sequence> calculateMaxSumModule() {
        for (Sequence seq: inputList) {
            if (Math.abs(leftSum) < Math.abs(leftSum + seq.getElement())) {
                leftSum += seq.getElement();
                outputListLeft.add(seq);
            }
            else break;
        }
        for (int i = inputList.size()-1; i >= 0; --i) {
            if (Math.abs(rightSum) < Math.abs(rightSum + inputList.get(i).getElement()) || Math.abs(rightSum) < Math.abs(leftSum)) {
                rightSum += inputList.get(i).getElement();
                outputListRight.add(inputList.get(i));
            }
            else break;
        }
        if (Math.abs(rightSum) < Math.abs(leftSum))
            return outputListLeft;
        return outputListRight;
    }

    public void writeToFile(ArrayList<Sequence> list) {
        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
                bw.write(list.size()+ "\n");
                for (int i = 0; i < list.size()-1; ++i) {
                    bw.write(list.get(i).getIndex() + " ");
                }
                bw.write(list.get(list.size()-1).getIndex() + "");
                bw.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File " + OUTPUT_FILE + " not found!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class Sequence implements Comparable<Sequence>{
        private Integer element;
        private Integer index;

        public Sequence() {}

        public int compareTo(Sequence s2){
            return s2.element.compareTo(element);
        }

        public Integer getElement() {
            return element;
        }

        public void setElement(Integer element) {
            this.element = element;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
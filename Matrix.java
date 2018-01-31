import java.io.*;
import java.util.StringTokenizer;

class Matrix {
    private final static String INPUT_FILE = "INPUT.TXT";
    private final static String OUTPUT_FILE = "OUTPUT.TXT";
    private static int m;
    private static int n;
    private static int a;
    private static int b;
    private static int p;
    private int answer;

    public static void main(String [] args) {
        Matrix program = new Matrix();
        program.readFileAndCalculate();
        program.writeToFile();
    }

    public void readFileAndCalculate(){
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
                setParameters(br);
                int[][] A = new int[n][n];
                int[][] B = new int[n][n];
                setMatrix(br, A);
                StringTokenizer st;
                for (int i = 1; i < m; ++i) {
                    setMatrix(br, B);
                    A = matrixMultiplication(A, B);
                }
                br.close();
                answer = A[a-1][b-1];
            }
        } catch (FileNotFoundException e) {
            System.err.println("File " + INPUT_FILE + " not found!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMatrix(BufferedReader br, int[][] a) throws IOException {
        br.readLine();
        StringTokenizer st;
        for (int j = 0; j < n; ++j) {
            String in = br.readLine();
            st = new StringTokenizer(in);
            for (int k = 0; k < n; ++k) {
                a[j][k] = Integer.parseInt(st.nextToken());
            }
        }
    }

    public void setParameters(BufferedReader br) throws IOException {
        String firstLine = br.readLine();
        StringTokenizer st = new StringTokenizer(firstLine);
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        String secondLine = br.readLine();
        st = new StringTokenizer(secondLine);
        a = Integer.parseInt(st.nextToken());
        b = Integer.parseInt(st.nextToken());
        p = Integer.parseInt(br.readLine());
    }

    public void writeToFile() {
        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
                bw.write(answer + "");
                bw.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File " + OUTPUT_FILE + " not found!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[][] matrixMultiplication(int[][] A, int[][] B) {
        int[][] C = new int[n][n];
        for(int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                C[i][j] = 0;
                for (int k = 0; k < n; ++k)
                    C[i][j] += A[i][k] * B[k][j];
                C[i][j] %= p;
            }
        }
        return C;
    }
}

import java.util.*;
import java.math.BigDecimal;

//expression = '2 ^ 5 * 2'
//expression = '-2.6 * ( 3 ^ 2 + 5 ^ 2 )'
//expression = '( 6 + 9 - 5 ) / ( 8 + 1 * 2 ) + 7'
//expression = '( 16 + 9 - 5 )'
//expression = '1 + 2 * ( 1 + 2 * ( 1 + 2 * ( -1 + 2 ) ) )'
//expression = '2 + 2 * 2'
//expression = '5 + 1 / 0'
//expression = '( 4 + 3 ) * 2 ^ -2'
// ( 17 ^ 4 + 5 * 974 ^ 33 + 2.24 * 4.75 )^0
// 4 2 * 3
// 4a * 5

public class Task2 {

    private Map<String, Integer> mathOperatorsPriority = new HashMap<>();
    private  Stack<String> stack = new Stack<>();

    public static void main(String [] args) {
        Task2 task = new Task2();
        task.setMathematicalSymbolsPriority();
        task.calculator();
    }

    public void setMathematicalSymbolsPriority() {
        mathOperatorsPriority.put("+", 1);
        mathOperatorsPriority.put("-", 1);
        mathOperatorsPriority.put("*", 2);
        mathOperatorsPriority.put("/", 2);
        mathOperatorsPriority.put("^", 3);
    }

    public void calculator() {
        while(true) {
            System.out.println("Enter arithmetic expression, please or type 'exit' for exit this program. ");
            Scanner in = new Scanner(System.in);
            String expression = in.nextLine();
            if(expression.toLowerCase().contains("exit")) {
                System.out.println("Bye!");
                break;
            }
            String prettyExpression = makePrettyExpression(expression);
            ArrayList<String> reversePolishNotation = convertToReversePolishNotation(prettyExpression);
            computation(reversePolishNotation);
            System.out.println(stack.pop());
        }
    }

    public boolean isMathematicalSymbol(String c) {
        return "+-*/^".contains(c);
    }

    public BigDecimal operation(String sign, BigDecimal first, BigDecimal second) {
        if (sign.equals("+"))
            return first.add(second);
        else if (sign.equals("-"))
            return first.subtract(second);
        else if (sign.equals("*"))
            return first.multiply(second);
        else if (sign.equals("/")) {
            if (second.intValue() == 0)
                throw new ArithmeticException("You can't divide by zero.");
            return first.divide(second);
        }
        else if (sign.equals("^"))
            return second.signum() >= 0 ? first.pow(second.intValue()) : (BigDecimal.valueOf(1.0).divide(first)).pow(-second.intValue());
        return BigDecimal.valueOf(0);
    }

    public String makePrettyExpression(String expression) {
        StringBuilder prettyExpression = new StringBuilder();
        for(int i = 0; i < expression.length(); ++i) {
            char c = expression.charAt(i);
            if (!(String.valueOf(c).matches("\\d")
                    || isMathematicalSymbol(String.valueOf(c))
                    || "() .,".contains(String.valueOf(c))))
                throw new InputMismatchException("Illegal character '" + c + "' in expression");
            if (c == '-' && String.valueOf(expression.charAt(i+1)).matches("\\d")) {
                prettyExpression.append(c);
            }
            else if (c == '(' ||  c == ')' || isMathematicalSymbol(String.valueOf(c))) {
                prettyExpression.append(" " + c + " ");
            }
            else
                prettyExpression.append(c);
        }
        return prettyExpression.toString();
    }

    public ArrayList<String> convertToReversePolishNotation(String s) {
        ArrayList<String> reversePolishNotationList = new ArrayList<>();
        String[] tokens = s.split(" ");
        int tokensLength = tokens.length;
        int counter = 0, sensitivity = 0;
        for (String token: tokens) {
            counter += 1;
            if (token.equals(" ") || token.equals(""))
                continue;
            if (token.replace(".", "").replaceAll(",", "").matches("-?\\d+")) {
                reversePolishNotationList.add(token.replace(",", "."));
                sensitivity += 1;
            }
            else {
                if (token.equals("(")) {
                    stack.push("(");
                }
                else if (token.equals(")")) {
                    String openingBracket = stack.pop();
                    while (!openingBracket.equals("(")) {
                        reversePolishNotationList.add(openingBracket);
                        openingBracket = stack.pop();
                    }
                }
                else if (isMathematicalSymbol(token)) {
                    sensitivity -= 1;
                    if (stack.size() > 0) {
                        if (!stack.peek().equals("(") && !stack.peek().equals(")")) {
                            if (mathOperatorsPriority.get(token) <= mathOperatorsPriority.get(stack.peek())) {
                                reversePolishNotationList.add(stack.pop());
                            }
                        }
                    }
                    stack.push(token);
                }
            }
            if (sensitivity < 0 || sensitivity > 1)
                throw new InputMismatchException("Incorrect arithmetic expression near '" + token + "'");
            if (counter == tokensLength) {
                while (stack.size() > 0) {
                    reversePolishNotationList.add(stack.pop());
                }
            }
        }
        return reversePolishNotationList;
    }

    public void computation(ArrayList<String> elementsList) {
        for (int i = 0; i < elementsList.size(); ++i) {
            if (isMathematicalSymbol(elementsList.get(i))) {
                BigDecimal second =  BigDecimal.valueOf(Double.parseDouble(stack.pop()));
                BigDecimal first = BigDecimal.valueOf(Double.parseDouble(stack.pop()));
                BigDecimal result = operation(elementsList.get(i), first, second);
                stack.push(result.toString());
            }
            else
                stack.push(elementsList.get(i));
        }
    }
}
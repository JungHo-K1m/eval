import java.util.Scanner;
import java.util.Stack;

public class makeEval {
    public static double eval(final String str){
        char[] token = str.toCharArray();
        String oper = "+-*/";

        Stack<Integer> values = new Stack<Integer>();
        Stack<Character> operations = new Stack<Character>();

        for(int i=0; i < str.length(); i++){
            if(token[i] == ' ') continue;

            if(Character.isDigit(token[i])){
                StringBuffer valueBuffer = new StringBuffer();

                while(i < token.length && Character.isDigit(token[i]))
                    valueBuffer.append(token[i++]);

                values.push(Integer.parseInt(valueBuffer.toString()));

                i--;
            }

            else if(token[i] == '(')
                operations.push(token[i]);

            else if(token[i] == ')'){
                while (operations.peek() != '(')
                    values.push(applyOp(operations.pop(), values.pop(), values.pop()));
                operations.pop();
            }

            else if(oper.indexOf(token[i]) != -1){
                while (!operations.empty() && hasPrecedence(token[i], operations.peek()))
                    values.push(applyOp(operations.pop(), values.pop(), values.pop()));
                operations.push(token[i]);
            }
        }
        while (!operations.empty())
            values.push(applyOp(operations.pop(), values.pop(), values.pop()));


        return values.pop();
    }

    public static boolean hasPrecedence(
            char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        return true;
    }

    public static int applyOp(char op, int b, int a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return a / b;
        }
        return 0;
    }


    public static void main(String[] args){
        System.out.println("입력 : ");
        Scanner scanner = new Scanner(System.in);

        String str = scanner.next();
        System.out.println("결과 : " + eval(str));

    }
}

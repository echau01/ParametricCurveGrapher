package curve;

import java.util.*;

/**
 * This class contains static methods for evaluating mathematical expressions. The
 * class cannot be instantiated.
 */
public final class EquationParser {
    private static Map<String, Integer> operatorToPrecedence = new HashMap<String, Integer>();
    private static Map<String, Boolean> operatorIsRightAssociativeMap = new HashMap<String, Boolean>();

    /**
     * The token used to represent the unary minus operator when a
     * mathematical expression is parsed.
     */
    public static final String UNARY_MINUS_TOKEN = "$";

    private EquationParser() {}

    // Initialize HashMaps
    static {
        operatorToPrecedence.put("+", 1);
        operatorToPrecedence.put("-", 1);
        operatorToPrecedence.put("*", 2);
        operatorToPrecedence.put("/", 2);
        operatorToPrecedence.put("^", 3);

        operatorIsRightAssociativeMap.put("+", false);
        operatorIsRightAssociativeMap.put("-", false);
        operatorIsRightAssociativeMap.put("*", false);
        operatorIsRightAssociativeMap.put("/", false);
        operatorIsRightAssociativeMap.put("^", true);
    }

    /**
     * Evaluates the given expression, with the given t value being
     * substituted for every occurrence of t in the expression. The
     * expression must be valid and written in infix notation. The
     * expression can include numbers and the following symbols: t,
     * +, -, *, /, ^, (, ), sin, cos, tan. The plus (+) symbol cannot be
     * used as a unary operator, but the minus (-) symbol can be unary.
     * Any whitespace in the expression is ignored.
     *
     * @throws IllegalArgumentException if the expression is invalid.
     */
    public static double evaluate(String expression, double t) {
        return evaluate(infixToPostfix(expression), t);
    }

    /**
     * Evaluates the expression represented by the given queue of tokens,
     * with the given t value being substituted for every occurrence of t
     * in the queue. The tokens in the queue, if read from front to back,
     * must form a valid expression written in postfix notation. The
     * tokens may be real numbers or the following symbols: t, +, -, *, /, ^,
     * (, ), sin, cos, tan. Unary plus is NOT allowed (but unary minus is
     * allowed).
     *
     * This method does not change the input Queue, as a copy of the Queue is
     * created at the beginning.
     *
     * @return the result of evaluating the postfix expression contained in the Queue
     *
     * @throws IllegalArgumentException if the postfix expression
     * contained in the given Queue is invalid.
     */
    public static double evaluate(Queue<String> postfixQueue, double t) {
        Queue<String> postfixQueueCopy = new LinkedList<String>(postfixQueue);

        Stack<Double> operands = new Stack<Double>();

        while (!postfixQueueCopy.isEmpty()) {
            String currentToken = postfixQueueCopy.poll();
            try {
                switch (currentToken) {
                    case "+": {
                        Double secondOperand = operands.pop();
                        Double firstOperand = operands.pop();
                        operands.push(firstOperand + secondOperand);
                        break;
                    }
                    case "-": {
                        Double secondOperand = operands.pop();
                        Double firstOperand = operands.pop();
                        operands.push(firstOperand - secondOperand);
                        break;
                    }
                    case "*": {
                        Double secondOperand = operands.pop();
                        Double firstOperand = operands.pop();
                        operands.push(firstOperand * secondOperand);
                        break;
                    }
                    case "/": {
                        Double secondOperand = operands.pop();
                        Double firstOperand = operands.pop();
                        operands.push(firstOperand / secondOperand);
                        break;
                    }
                    case "^": {
                        Double secondOperand = operands.pop();
                        Double firstOperand = operands.pop();
                        operands.push(Math.pow(firstOperand, secondOperand));
                        break;
                    }
                    case UNARY_MINUS_TOKEN:
                        operands.push(-1 * operands.pop());
                        break;
                    case "sin":
                        operands.push(Math.sin(operands.pop()));
                        break;
                    case "cos":
                        operands.push(Math.cos(operands.pop()));
                        break;
                    case "tan":
                        operands.push(Math.tan(operands.pop()));
                        break;
                    case "t":
                        operands.push(t);
                        break;
                    default:
                        operands.push(Double.parseDouble(currentToken));
                        break;
                }
            } catch (EmptyStackException | NumberFormatException e) {
                throw new IllegalArgumentException("Given postfix expression is invalid");
            }
        }

        if (operands.size() != 1) {
            throw new IllegalArgumentException("Given postfix expression is invalid");
        }

        return operands.pop();
    }

    /**
     * Converts the given infix expression to postfix notation. The given
     * expression must be valid and must be written in infix notation.
     * The expression may contain real numbers, whitespace around operators
     * and trigonometric functions, and the following symbols: t, +, -, *, /,
     * ^, (, ), sin, cos, tan. Any whitespace in the expression is ignored.
     *
     * @return a queue of the tokens in the given infix expression converted to
     * postfix notation
     *
     * @throws IllegalArgumentException if expression is not valid
     */
    public static Queue<String> infixToPostfix(String expression) throws IllegalArgumentException {
        Queue<String> output = new LinkedList<String>();

        // Stores operators, brackets, and trigonometric functions
        Stack<String> operatorBracketFunctionStack = new Stack<String>();

        List<String> tokens = tokenize(expression);

        /* Implementation of the Shunting-Yard algorithm adapted from
         * https://en.wikipedia.org/wiki/Shunting-yard_algorithm
         */
        for (int i = 0; i < tokens.size(); i++) {
            String currentToken = tokens.get(i);
            if (currentToken.matches("[+\\-*/]")) {
                if (!operatorBracketFunctionStack.isEmpty()) {
                    int precedence = operatorToPrecedence.get(currentToken);
                    String nextStackSymbol = operatorBracketFunctionStack.peek();
                    while ((nextStackSymbol.matches("sin|cos|tan")
                            || nextStackSymbol.equals(UNARY_MINUS_TOKEN))
                            || (nextStackSymbol.matches("[+\\-*/^]")
                            && (operatorToPrecedence.get(nextStackSymbol) > precedence
                            || (operatorToPrecedence.get(nextStackSymbol) == precedence
                            && !operatorIsRightAssociativeMap.get(nextStackSymbol))))) {
                        output.add(operatorBracketFunctionStack.pop());

                        if (operatorBracketFunctionStack.isEmpty()) {
                            break;
                        } else {
                            nextStackSymbol = operatorBracketFunctionStack.peek();
                        }
                    }
                }

                operatorBracketFunctionStack.push(currentToken);
            } else if (currentToken.matches("sin|cos|tan|\\(|\\^")
                        || currentToken.equals(UNARY_MINUS_TOKEN)) {
                operatorBracketFunctionStack.push(currentToken);
            } else if (currentToken.equals(")")) {
                while (!operatorBracketFunctionStack.peek().equals("(")) {
                    output.add(operatorBracketFunctionStack.pop());
                }

                // Note: when the while loop terminates, the token at the
                // top of the stack must be a left parenthesis.

                operatorBracketFunctionStack.pop();
            } else {
                // At this point, currentToken is either a number or "t".
                output.add(currentToken);
            }
        }

        int stackSize = operatorBracketFunctionStack.size();
        for (int i = 0; i < stackSize; i++) {
            output.add(operatorBracketFunctionStack.pop());
        }

        return output;
    }

    /**
     * Produce a list of the tokens in the given infix expression as seen
     * from left to right. A unary minus symbol is represented in the
     * returned list as the symbol given by UNARY_MINUS_TOKEN. A "*" symbol
     * is inserted wherever there is implicit multiplication (e.g. "2t",
     * "-4sin(6)"). For example, the expression "5 + 2tan(-t - 9)" would be
     * tokenized as the list ["5", "+", "2", "*", "tan", "(", UNARY_MINUS_TOKEN,
     * "t", "-", "9", ")"]. Any whitespace in the expression is ignored, so
     * an expression like "3 7" is interpreted as "37".
     *
     * @return a list of the tokens in the given infix expression
     *
     * @throws IllegalArgumentException if the given expression is invalid.
     */
    public static List<String> tokenize(String expression) throws IllegalArgumentException {
        List<String> tokens = new ArrayList<String>(expression.length());

        // This stack stores left parentheses to check if all brackets are balanced.
        Stack<Character> bracketStack = new Stack<Character>();

        // functionName is used to store the name of a function as we read it
        StringBuilder functionName = new StringBuilder();

        // operand is used to store "t" or the string representation of a number
        // as we read it
        StringBuilder operand = new StringBuilder();

        // true if the current operand has a decimal point
        boolean operandHasDecimalPoint = false;

        for (int i = 0; i < expression.length(); i++) {
            String currentChar = expression.substring(i, i + 1);
            if (!currentChar.equals(" ")) {
                if (functionName.length() == 0) {
                    if (currentChar.matches("\\d")) {
                        if (!tokens.isEmpty() && tokens.get(tokens.size() - 1).equals(")")) {
                            tokens.add("*");
                            operand.setLength(0);
                            operandHasDecimalPoint = false;
                        } else if (operand.toString().equals("t")) {
                            tokens.add("t");
                            tokens.add("*");
                            operand.setLength(0);
                            operandHasDecimalPoint = false;
                        }
                        operand.append(currentChar);
                    } else if (currentChar.matches("[+*/^]")) {
                        if (operand.length() == 0) {
                            if (tokens.isEmpty() || !tokens.get(tokens.size() - 1).equals(")")) {
                                throw new IllegalArgumentException("Given expression is invalid.");
                            }
                        } else {
                            if (operand.substring(operand.length() - 1).equals(".")) {
                                throw new IllegalArgumentException("Given expression contains misplaced decimal point.");
                            }
                            tokens.add(operand.toString());
                        }

                        tokens.add(currentChar);
                        operand.setLength(0);
                        operandHasDecimalPoint = false;
                    } else if (currentChar.equals("(")) {
                        if (!tokens.isEmpty() && tokens.get(tokens.size() - 1).matches("[t)]")) {
                            tokens.add("*");
                        } else if (operand.length() != 0) {
                            if (operand.substring(operand.length() - 1).equals(".")) {
                                throw new IllegalArgumentException("Given expression contains misplaced decimal point.");
                            }
                            tokens.add(operand.toString());
                            tokens.add("*");
                        }
                        tokens.add(currentChar);
                        operand.setLength(0);
                        operandHasDecimalPoint = false;
                        bracketStack.push('(');
                    } else if (currentChar.equals(")")) {
                        try {
                            bracketStack.pop();
                        } catch (EmptyStackException e) {
                            throw new IllegalArgumentException("Given expression has mismatched parentheses.");
                        }

                        if (operand.length() != 0) {
                            if (operand.substring(operand.length() - 1).equals(".")) {
                                throw new IllegalArgumentException("Given expression contains misplaced decimal point.");
                            }
                            tokens.add(operand.toString());
                        }

                        // Catch invalid expressions that contain "()".
                        if (tokens.get(tokens.size() - 1).equals("(")) {
                            throw new IllegalArgumentException("Given expression is invalid.");
                        }

                        tokens.add(currentChar);
                        operand.setLength(0);
                        operandHasDecimalPoint = false;
                    } else if (currentChar.equals("-")) {
                        /* The algorithm to distinguish unary minus from binary minus is based on
                         * the discussion at http://wcipeg.com/wiki/Shunting_yard_algorithm#Extensions
                         */
                        if (tokens.isEmpty() && operand.length() == 0) {
                            // This means the "-" character is the first non-whitespace character
                            // in the expression.
                            tokens.add(UNARY_MINUS_TOKEN);
                        } else {
                            if (operand.length() != 0) {
                                if (operand.substring(operand.length() - 1).equals(".")) {
                                    throw new IllegalArgumentException("Given expression contains misplaced decimal point.");
                                }
                                tokens.add(operand.toString());
                            }

                            String previousToken = tokens.get(tokens.size() - 1);
                            if (previousToken.matches("[+\\-*/^(]|sin|cos|tan")) {
                                tokens.add(UNARY_MINUS_TOKEN);
                            } else {
                                tokens.add("-");
                            }
                        }
                        operand.setLength(0);
                        operandHasDecimalPoint = false;
                    } else if (currentChar.equals("t")) {
                        if (!tokens.isEmpty() && tokens.get(tokens.size() - 1).matches("[t)]")) {
                            tokens.add("*");
                        } else if (operand.length() != 0) {
                            if (operand.substring(operand.length() - 1).equals(".")) {
                                throw new IllegalArgumentException("Given expression contains misplaced decimal point.");
                            }
                            tokens.add(operand.toString());
                            tokens.add("*");
                            operand.setLength(0);
                            operandHasDecimalPoint = false;
                        }

                        if (i == expression.length() - 1) {
                            operand.append("t");
                        } else if (expression.substring(i + 1, i + 2).equals("a")) {
                            // Here, the "t" is part of "tan".
                            functionName.append("t");
                        } else {
                            operand.append("t");
                        }
                    } else if (currentChar.equals(".")) {
                        // Expressions like "2.5.6" or "2..." are invalid.
                        if (operandHasDecimalPoint) {
                            throw new IllegalArgumentException("Given expression contains misplaced decimal point.");
                        }

                        operandHasDecimalPoint = true;

                        // Expressions like "t.3" and "cos(5).40" are invalid.
                        if (operand.toString().equals("t")
                                || (!tokens.isEmpty() && tokens.get(tokens.size() - 1).equals(")"))) {
                            throw new IllegalArgumentException("Given expression is invalid.");
                        }

                        operand.append(".");
                    } else {
                        // Any valid expression that makes it to this else block
                        // has currentChar equal to one of the first letters of "sin",
                        // or "cos". If the expression is invalid, it will be caught in
                        // a few more iterations.
                        if (!tokens.isEmpty() && tokens.get(tokens.size() - 1).equals(")")) {
                            tokens.add("*");
                        } else if (operand.length() != 0) {
                            if (operand.substring(operand.length() - 1).equals(".")) {
                                throw new IllegalArgumentException("Given expression contains misplaced decimal point.");
                            }
                            tokens.add(operand.toString());
                            tokens.add("*");
                            operand.setLength(0);
                            operandHasDecimalPoint = false;
                        }
                        functionName.append(currentChar);
                    }
                } else {
                    // If this code runs, and the given expression is valid, then we must be somewhere
                    // inside the substrings "sin", "cos", or "tan". Thus, we simply let the program
                    // loop through the expression, appending characters (one-length strings) to
                    // functionName until its length is 3. If, at that point, the String represented
                    // by functionName is not "sin" or "cos" or "tan", then the expression is invalid.
                    functionName.append(currentChar);
                    if (functionName.length() == 3) {
                        String function = functionName.toString();
                        if (function.matches("sin|cos|tan")) {
                            tokens.add(function);
                            functionName.setLength(0);
                        } else {
                            throw new IllegalArgumentException("Given expression contains invalid function.");
                        }
                    }
                }
            }
        }

        if (operand.length() != 0) {
            if (operand.substring(operand.length() - 1).equals(".")) {
                throw new IllegalArgumentException("Given expression contains misplaced decimal point.");
            }
            tokens.add(operand.toString());
        }

        // Final error checking
        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("Given expression is invalid.");
        } else if (!bracketStack.isEmpty()) {
            throw new IllegalArgumentException("Given expression contains mismatched parentheses.");
        } else {
            String lastToken = tokens.get(tokens.size() - 1);
            if (lastToken.matches("[+\\-*/^]|sin|cos|tan")
                    || lastToken.equals(UNARY_MINUS_TOKEN)) {
                throw new IllegalArgumentException("Given expression is invalid.");
            }
        }

        return tokens;
    }
}

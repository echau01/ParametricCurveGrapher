import main.EquationParser;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class EquationParserTest {
    @Test
    public void evaluateValidInfixExpressions() {
        String expression1 = "4.5";
        String expression2 = "-4 + t^2";
        String expression3 = "5sin(t^3 * cos(0)) - 10t";
        String expression4 = "tan(-t - 1)^2";   // interpreted as tan((-t - 1)^2)
        String expression5 = "(tan(-t - 1))^2";
        String expression6 = "-5^2t";   // interpreted as (-5^2) * t
        String expression7 = "0.8^t^2"; // interpreted as 0.8^(t^2)
        String expression8 = "0 / 0";
        String expression9 = "sin(t) * (" + Math.E + "^cos(t) - 2cos(4t) - (sin(t / 12))^5)";
        String expression10 = "(3)(4t)";
        String expression11 = "sin(t)(cos(t))"; // interpreted as (sin(t)) * (cos(t))
        String expression12 = "(3)4t";
        String expression13 = "(3)t(5)6";

        double t1 = -304.46;
        double t2 = 3.4594;
        double t3 = 5.59542;
        double t4 = -0.45813946938419;
        double t5 = Math.sin(89);
        double t6 = 554.23745;
        double t7 = Math.E;
        double t8 = Math.cos(384);
        double t9 = 2 * Math.PI;
        double t10 = 3.572;
        double t11 = -Math.E * Math.PI;
        double t12 = 2.5;
        double t13 = 4.12;

        double expectedResult1 = 4.5;
        double expectedResult2 = -4 + Math.pow(t2, 2);
        double expectedResult3 = 5 * Math.sin(Math.pow(t3, 3) * Math.cos(0)) - 10 * t3;
        double expectedResult4 = Math.tan(Math.pow(-t4 - 1, 2));
        double expectedResult5 = Math.pow(Math.tan(-t5 - 1), 2);
        double expectedResult6 = -Math.pow(5, 2) * t6;
        double expectedResult7 = Math.pow(0.8, Math.pow(t7, 2));
        double expectedResult8 = Double.NaN;
        double expectedResult9 = Math.sin(t9) * (Math.pow(Math.E, Math.cos(t9))
                - 2 * Math.cos(4 * t9) - Math.pow(Math.sin(t9 / 12), 5));
        double expectedResult10 = (3) * (4 * t10);
        double expectedResult11 = (Math.sin(t11)) * (Math.cos(t11));
        double expectedResult12 = 3 * 4 * t12;
        double expectedResult13 = 3 * t13 * 5 * 6;

        assertEquals(expectedResult1, EquationParser.evaluate(expression1, t1));
        assertEquals(expectedResult2, EquationParser.evaluate(expression2, t2));
        assertEquals(expectedResult3, EquationParser.evaluate(expression3, t3));
        assertEquals(expectedResult4, EquationParser.evaluate(expression4, t4));
        assertEquals(expectedResult5, EquationParser.evaluate(expression5, t5));
        assertEquals(expectedResult6, EquationParser.evaluate(expression6, t6));
        assertEquals(expectedResult7, EquationParser.evaluate(expression7, t7));
        assertEquals(expectedResult8, EquationParser.evaluate(expression8, t8));
        assertEquals(expectedResult9, EquationParser.evaluate(expression9, t9));
        assertEquals(expectedResult10, EquationParser.evaluate(expression10, t10));
        assertEquals(expectedResult11, EquationParser.evaluate(expression11, t11));
        assertEquals(expectedResult12, EquationParser.evaluate(expression12, t12));
        assertEquals(expectedResult13, EquationParser.evaluate(expression13, t13));
    }

    @Test
    public void evaluateInvalidInfixExpressions() {
        String invalidExpression1 = "";
        String invalidExpression2 = "(";
        String invalidExpression3 = ")";
        String invalidExpression4 = "+";
        String invalidExpression5 = "-";
        String invalidExpression6 = "*";
        String invalidExpression7 = "/";
        String invalidExpression8 = "sin";
        String invalidExpression9 = "cos";
        String invalidExpression10 = "tan";
        String invalidExpression11 = "()";
        String invalidExpression12 = "sin^2(5)";
        String invalidExpression13 = "    ";
        String invalidExpression14 = "cos(3t).67";
        String invalidExpression15 = "t.0";
        String invalidExpression16 = "2.t";
        String invalidExpression17 = "3. * 4";
        String invalidExpression18 = "2.5.7";
        String invalidExpression19 = "2..5";
        String invalidExpression20 = "3 + 2.";
        String invalidExpression21 = "3^-*.4";
        String invalidExpression22 = "arctan(1)";
        String invalidExpression23 = "3 + 4 --";
        String invalidExpression24 = "3 + 4 ^ ";
        String invalidExpression25 = "3 + 5cos(3 - (6t^2)"; // mismatched parentheses
        String invalidExpression26 = "4.";
        String invalidExpression27 = "4.(";
        String invalidExpression28 = "tan(4).6";
        String invalidExpression29 = "359. - 8";
        String invalidExpression30 = "6.cos(-1)";
        String invalidExpression31 = "cos(1.)";

        // Dummy t variable
        double t = Math.cos(1);

        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression1, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression2, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression3, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression4, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression5, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression6, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression7, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression8, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression9, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression10, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression11, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression12, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression13, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression14, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression15, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression16, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression17, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression18, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression19, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression20, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression21, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression22, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression23, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression24, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression25, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression26, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression27, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression28, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression29, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression30, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidExpression31, t));
    }

    @Test
    public void evaluateValidPostfixQueues() {
        String[] validPostfixQueueAsArray1 = {"2"};
        String[] validPostfixQueueAsArray2 = {"3.14159", EquationParser.UNARY_MINUS_TOKEN, "43",
                "57", "*", "+", "0.3", "80", "t", "*", "4", "+", "t", "2", "/", "^", "*", "-"};
        String[] validPostfixQueueAsArray3 = {"4", "3", EquationParser.UNARY_MINUS_TOKEN, "2", "^",
                "4", "t", "*", "sin", "*", "-", "3.1", "t", "/", "5", "t", "2", "^", "+", "tan",
                "+", "t", "sin", "^", EquationParser.UNARY_MINUS_TOKEN, "/"};

        Queue<String> validPostfixQueue1 = new LinkedList<String>(Arrays.asList(validPostfixQueueAsArray1));
        Queue<String> validPostfixQueue2 = new LinkedList<String>(Arrays.asList(validPostfixQueueAsArray2));
        Queue<String> validPostfixQueue3 = new LinkedList<String>(Arrays.asList(validPostfixQueueAsArray3));

        int queueSize1 = validPostfixQueue1.size();
        int queueSize2 = validPostfixQueue2.size();
        int queueSize3 = validPostfixQueue3.size();

        double t1 = -3.738;
        double t2 = Math.cos(1);
        double t3 = Math.sin(1);

        double expectedResult1 = 2;
        double expectedResult2 = -3.14159 + 43 * 57 - 0.3 * Math.pow(80 * t2 + 4, t2 / 2);
        double expectedResult3 = (4 - Math.pow(-3, 2) * Math.sin(4 * t3)) / -Math.pow((3.1 / t3
                + Math.tan(5 + Math.pow(t3, 2))), Math.sin(t3));

        assertEquals(expectedResult1, EquationParser.evaluate(validPostfixQueue1, t1));
        assertEquals(expectedResult2, EquationParser.evaluate(validPostfixQueue2, t2));
        assertEquals(expectedResult3, EquationParser.evaluate(validPostfixQueue3, t3));

        // The queues themselves should not have changed in size.
        assertEquals(queueSize1, validPostfixQueue1.size());
        assertEquals(queueSize2, validPostfixQueue2.size());
        assertEquals(queueSize3, validPostfixQueue3.size());
    }

    @Test
    public void evaluateInvalidPostfixQueues() {
        // Represents the invalid expression "3 + sin(5 + t^2) -"
        String[] invalidPostfixQueueAsArray1 = {"3", "5", "t", "2", "^", "+", "sin", "+", "-"};

        // The following queue is invalid because there is no operator to
        // combine 2^(-3) and 4.
        String[] invalidPostfixQueueAsArray2 = {"2", "3", EquationParser.UNARY_MINUS_TOKEN, "^", "4"};

        // The following queue is invalid because "ref" is not a function that is defined
        // in our program.
        String[] invalidPostfixQueueAsArray3 = {"3", "t", "ref", "+", "sin"};

        Queue<String> invalidPostfixQueue1 = new LinkedList<String>(Arrays.asList(invalidPostfixQueueAsArray1));
        Queue<String> invalidPostfixQueue2 = new LinkedList<String>(Arrays.asList(invalidPostfixQueueAsArray2));
        Queue<String> invalidPostfixQueue3 = new LinkedList<String>(Arrays.asList(invalidPostfixQueueAsArray3));

        // Dummy t variable
        double t = Math.cos(1);

        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidPostfixQueue1, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidPostfixQueue2, t));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.evaluate(invalidPostfixQueue3, t));
    }

    @Test
    public void infixToPostfixValidExpressions() {
        String validExpression1 = "2";
        String validExpression2 = "-3.14159 + 43 * 57 - 0.3(80t + 4)^(t / 2)";
        String validExpression3 = "(4 - (-3)^2 * sin(4t)) / -(3.1/t + tan(5 + t^2))^(sin(t))";

        String[] postfixQueueAsArray1 = {"2"};
        String[] postfixQueueAsArray2 = {"3.14159", EquationParser.UNARY_MINUS_TOKEN, "43",
                "57", "*", "+", "0.3", "80", "t", "*", "4", "+", "t", "2", "/", "^", "*", "-"};
        String[] postfixQueueAsArray3 = {"4", "3", EquationParser.UNARY_MINUS_TOKEN, "2", "^",
                "4", "t", "*", "sin", "*", "-", "3.1", "t", "/", "5", "t", "2", "^", "+", "tan",
                "+", "t", "sin", "^", EquationParser.UNARY_MINUS_TOKEN, "/"};

        Queue<String> expectedQueue1 = new LinkedList<String>(Arrays.asList(postfixQueueAsArray1));
        Queue<String> expectedQueue2 = new LinkedList<String>(Arrays.asList(postfixQueueAsArray2));
        Queue<String> expectedQueue3 = new LinkedList<String>(Arrays.asList(postfixQueueAsArray3));

        assertEquals(expectedQueue1, EquationParser.infixToPostfix(validExpression1));
        assertEquals(expectedQueue2, EquationParser.infixToPostfix(validExpression2));
        assertEquals(expectedQueue3, EquationParser.infixToPostfix(validExpression3));
    }

    @Test
    public void infixToPostfixInvalidExpressions() {
        String invalidExpression1 = "";
        String invalidExpression2 = "(";
        String invalidExpression3 = ")";
        String invalidExpression4 = "+";
        String invalidExpression5 = "-";
        String invalidExpression6 = "*";
        String invalidExpression7 = "/";
        String invalidExpression8 = "sin";
        String invalidExpression9 = "cos";
        String invalidExpression10 = "tan";
        String invalidExpression11 = "()";
        String invalidExpression12 = "sin^2(5)";
        String invalidExpression13 = "    ";
        String invalidExpression14 = "cos(3t).67";
        String invalidExpression15 = "t.0";
        String invalidExpression16 = "2.t";
        String invalidExpression17 = "3. * 4";
        String invalidExpression18 = "2.5.7";
        String invalidExpression19 = "2..5";
        String invalidExpression20 = "3 + 2.";
        String invalidExpression21 = "3^-*.4";
        String invalidExpression22 = "arctan(1)";
        String invalidExpression23 = "3 + 4 --";
        String invalidExpression24 = "3 + 4 ^ ";
        String invalidExpression25 = "3 + 5cos(3 - (6t^2)"; // mismatched parentheses
        String invalidExpression26 = "4.";
        String invalidExpression27 = "4.(";
        String invalidExpression28 = "tan(4).6";
        String invalidExpression29 = "359. - 8";
        String invalidExpression30 = "6.cos(-1)";
        String invalidExpression31 = "cos(1.)";

        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression1));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression2));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression3));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression4));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression5));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression6));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression7));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression8));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression9));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression10));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression11));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression12));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression13));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression14));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression15));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression16));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression17));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression18));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression19));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression20));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression21));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression22));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression23));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression24));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression25));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression26));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression27));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression28));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression29));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression30));
        assertThrows(IllegalArgumentException.class,
                () -> EquationParser.infixToPostfix(invalidExpression31));
    }

    @Test
    public void tokenizeValidExpressions() {
        String validExpression1 = "2";
        String validExpression2 = "-3.14159 + 43";
        String validExpression3 = "2t + sin(-5t^2cos(t))"; // note: t^2cos(t) = (t^2)(cos(t))
        String validExpression4 = "t^(2^(-tan(50))) + 3.45sin(-(-t))";
        String validExpression5 = "t/-(50+t)8";
        String validExpression6 = "9.35t^.56 * 35 - sin(2t - 0)7.45";
        String validExpression7 = "0002.43"; // trailing zeroes in front of numbers are allowed
        String validExpression8 = "2t3tt-4"; // implicit multiplication
        String validExpression9 = ".36(50+.34sin(t/-0.5))^-.2";
        String validExpression10 = "2.50 / -00446";
        String validExpression11 = "-(49.59602 * sin(56 cos(-40)))^(2 / 3)";
        String validExpression12 = "4--2"; // this is 4 - (-2)
        String validExpression13 = "4+-2"; // this is 4 + (-2)
        String validExpression14 = "(cos(t))^sin(2t)";
        String validExpression15 = "sin -2t + 2";   // this is sin(-2t) + 2
        String validExpression16 = "3 7";  //this is 37
        String validExpression17 = "(3)(4t)";
        String validExpression18 = "(3)4t";
        String validExpression19 = "(3)t";
        String validExpression20 = "4 + ttan(6)t";   // interpreted as 4 + t * (tan(6)) * t

        String[] expectedTokens1 = {"2"};
        String[] expectedTokens2 = {EquationParser.UNARY_MINUS_TOKEN, "3.14159", "+", "43"};
        String[] expectedTokens3 = {"2", "*", "t", "+", "sin", "(", EquationParser.UNARY_MINUS_TOKEN,
                "5", "*", "t", "^", "2", "*", "cos", "(", "t", ")", ")"};
        String[] expectedTokens4 = {"t", "^", "(", "2", "^", "(", EquationParser.UNARY_MINUS_TOKEN,
                "tan", "(", "50", ")", ")", ")", "+", "3.45", "*", "sin", "(",
                EquationParser.UNARY_MINUS_TOKEN, "(",
                EquationParser.UNARY_MINUS_TOKEN, "t", ")", ")"};
        String[] expectedTokens5 = {"t", "/", EquationParser.UNARY_MINUS_TOKEN, "(", "50", "+", "t",
                ")", "*", "8"};
        String[] expectedTokens6 = {"9.35", "*", "t", "^", ".56", "*", "35", "-", "sin", "(", "2",
                "*", "t", "-", "0", ")", "*", "7.45"};
        String[] expectedTokens7 = {"0002.43"};
        String[] expectedTokens8 = {"2", "*", "t", "*", "3", "*", "t", "*", "t", "-", "4"};
        String[] expectedTokens9 = {".36", "*", "(", "50", "+", ".34", "*", "sin", "(", "t", "/",
                EquationParser.UNARY_MINUS_TOKEN, "0.5", ")", ")", "^",
                EquationParser.UNARY_MINUS_TOKEN, ".2"};
        String[] expectedTokens10 = {"2.50", "/", EquationParser.UNARY_MINUS_TOKEN, "00446"};
        String[] expectedTokens11 = {EquationParser.UNARY_MINUS_TOKEN, "(", "49.59602", "*", "sin",
                "(", "56", "*", "cos", "(", EquationParser.UNARY_MINUS_TOKEN,
                "40", ")", ")", ")", "^", "(", "2", "/", "3", ")"};
        String[] expectedTokens12 = {"4", "-", EquationParser.UNARY_MINUS_TOKEN, "2"};
        String[] expectedTokens13 = {"4", "+", EquationParser.UNARY_MINUS_TOKEN, "2"};
        String[] expectedTokens14 = {"(", "cos", "(", "t", ")", ")", "^", "sin", "(", "2", "*", "t", ")"};
        String[] expectedTokens15 = {"sin", EquationParser.UNARY_MINUS_TOKEN, "2", "*", "t", "+", "2"};
        String[] expectedTokens16 = {"37"};
        String[] expectedTokens17 = {"(", "3", ")", "*", "(", "4", "*", "t", ")"};
        String[] expectedTokens18 = {"(", "3", ")", "*", "4", "*", "t"};
        String[] expectedTokens19 = {"(", "3", ")", "*", "t"};
        String[] expectedTokens20 = {"4", "+", "t", "*", "tan", "(", "6", ")", "*", "t"};

        assertEquals(Arrays.asList(expectedTokens1), EquationParser.tokenize(validExpression1));
        assertEquals(Arrays.asList(expectedTokens2), EquationParser.tokenize(validExpression2));
        assertEquals(Arrays.asList(expectedTokens3), EquationParser.tokenize(validExpression3));
        assertEquals(Arrays.asList(expectedTokens4), EquationParser.tokenize(validExpression4));
        assertEquals(Arrays.asList(expectedTokens5), EquationParser.tokenize(validExpression5));
        assertEquals(Arrays.asList(expectedTokens6), EquationParser.tokenize(validExpression6));
        assertEquals(Arrays.asList(expectedTokens7), EquationParser.tokenize(validExpression7));
        assertEquals(Arrays.asList(expectedTokens8), EquationParser.tokenize(validExpression8));
        assertEquals(Arrays.asList(expectedTokens9), EquationParser.tokenize(validExpression9));
        assertEquals(Arrays.asList(expectedTokens10), EquationParser.tokenize(validExpression10));
        assertEquals(Arrays.asList(expectedTokens11), EquationParser.tokenize(validExpression11));
        assertEquals(Arrays.asList(expectedTokens12), EquationParser.tokenize(validExpression12));
        assertEquals(Arrays.asList(expectedTokens13), EquationParser.tokenize(validExpression13));
        assertEquals(Arrays.asList(expectedTokens14), EquationParser.tokenize(validExpression14));
        assertEquals(Arrays.asList(expectedTokens15), EquationParser.tokenize(validExpression15));
        assertEquals(Arrays.asList(expectedTokens16), EquationParser.tokenize(validExpression16));
        assertEquals(Arrays.asList(expectedTokens17), EquationParser.tokenize(validExpression17));
        assertEquals(Arrays.asList(expectedTokens18), EquationParser.tokenize(validExpression18));
        assertEquals(Arrays.asList(expectedTokens19), EquationParser.tokenize(validExpression19));
        assertEquals(Arrays.asList(expectedTokens20), EquationParser.tokenize(validExpression20));
    }

    @Test
    public void tokenizeInvalidExpressions() {
        String invalidExpression1 = "";
        String invalidExpression2 = "(";
        String invalidExpression3 = ")";
        String invalidExpression4 = "+";
        String invalidExpression5 = "-";
        String invalidExpression6 = "*";
        String invalidExpression7 = "/";
        String invalidExpression8 = "sin";
        String invalidExpression9 = "cos";
        String invalidExpression10 = "tan";
        String invalidExpression11 = "()";
        String invalidExpression12 = "sin^2(5)";
        String invalidExpression13 = "    ";
        String invalidExpression14 = "cos(3t).67";
        String invalidExpression15 = "t.0";
        String invalidExpression16 = "2.t";
        String invalidExpression17 = "3. * 4";
        String invalidExpression18 = "2.5.7";
        String invalidExpression19 = "2..5";
        String invalidExpression20 = "3 + 2.";
        String invalidExpression21 = "3^-*.4";
        String invalidExpression22 = "arctan(1)";
        String invalidExpression23 = "3 + 4 --";
        String invalidExpression24 = "3 + 4 ^ ";
        String invalidExpression25 = "3 + 5cos(3 - (6t^2)"; // mismatched parentheses
        String invalidExpression26 = "4.";
        String invalidExpression27 = "4.(";
        String invalidExpression28 = "tan(4).6";
        String invalidExpression29 = "359. - 8";
        String invalidExpression30 = "6.cos(-1)";
        String invalidExpression31 = "cos(1.)";

        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression1));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression2));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression3));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression4));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression5));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression6));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression7));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression8));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression9));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression10));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression11));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression12));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression13));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression14));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression15));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression16));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression17));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression18));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression19));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression20));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression21));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression22));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression23));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression24));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression25));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression26));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression27));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression28));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression29));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression30));
        assertThrows(IllegalArgumentException.class, () -> EquationParser.tokenize(invalidExpression31));
    }
}
import main.EquationParser;
import org.junit.jupiter.api.*;

import java.util.Arrays;

public class EquationParserTest {
    private EquationParser instance = EquationParser.getInstance();

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void evaluate() {
    }

    @Test
    public void postfix() {
    }

    @Test
    public void tokenize() {
        /* Invalid expressions should throw IllegalArgumentException */

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

        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression2));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression3));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression4));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression6));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression7));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression8));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression9));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression10));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression11));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression12));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression13));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression14));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression15));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression16));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression17));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression18));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression19));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression20));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression21));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression22));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression23));
        Assertions.assertThrows(IllegalArgumentException.class, () -> instance.tokenize(invalidExpression24));

        /* Tests for valid expressions */

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

        String[] tokenizedExpression1 = {"2"};
        String[] tokenizedExpression2 = {EquationParser.UNARY_MINUS_TOKEN, "3.14159", "+", "43"};
        String[] tokenizedExpression3 = {"2", "*", "t", "+", "sin", "(", EquationParser.UNARY_MINUS_TOKEN,
                                            "5", "*", "t", "^", "2", "*", "cos", "(", "t", ")", ")"};
        String[] tokenizedExpression4 = {"t", "^", "(", "2", "^", "(", EquationParser.UNARY_MINUS_TOKEN,
                                            "tan", "(", "50", ")", ")", ")", "+", "3.45", "*", "sin", "(",
                                            EquationParser.UNARY_MINUS_TOKEN, "(",
                                            EquationParser.UNARY_MINUS_TOKEN, "t", ")", ")"};
        String[] tokenizedExpression5 = {"t", "/", EquationParser.UNARY_MINUS_TOKEN, "(", "50", "+", "t",
                                            ")", "*", "8"};
        String[] tokenizedExpression6 = {"9.35", "*", "t", "^", ".56", "*", "35", "-", "sin", "(", "2",
                                            "*", "t", "-", "0", ")", "*", "7.45"};
        String[] tokenizedExpression7 = {"0002.43"};
        String[] tokenizedExpression8 = {"2", "*", "t", "*", "3", "*", "t", "*", "t", "-", "4"};
        String[] tokenizedExpression9 = {".36", "*", "(", "50", "+", ".34", "*", "sin", "(", "t", "/",
                                            EquationParser.UNARY_MINUS_TOKEN, "0.5", ")", ")", "^",
                                            EquationParser.UNARY_MINUS_TOKEN, ".2"};
        String[] tokenizedExpression10 = {"2.50", "/", EquationParser.UNARY_MINUS_TOKEN, "00446"};
        String[] tokenizedExpression11 = {EquationParser.UNARY_MINUS_TOKEN, "(", "49.59602", "*", "sin",
                                            "(", "56", "*", "cos", "(", EquationParser.UNARY_MINUS_TOKEN,
                                            "40", ")", ")", ")", "^", "(", "2", "/", "3", ")"};
        String[] tokenizedExpression12 = {"4", "-", EquationParser.UNARY_MINUS_TOKEN, "2"};
        String[] tokenizedExpression13 = {"4", "+", EquationParser.UNARY_MINUS_TOKEN, "2"};

        Assertions.assertEquals(Arrays.asList(tokenizedExpression1), instance.tokenize(validExpression1));
        Assertions.assertEquals(Arrays.asList(tokenizedExpression2), instance.tokenize(validExpression2));
        Assertions.assertEquals(Arrays.asList(tokenizedExpression3), instance.tokenize(validExpression3));
        Assertions.assertEquals(Arrays.asList(tokenizedExpression4), instance.tokenize(validExpression4));
        Assertions.assertEquals(Arrays.asList(tokenizedExpression5), instance.tokenize(validExpression5));
        Assertions.assertEquals(Arrays.asList(tokenizedExpression6), instance.tokenize(validExpression6));
        Assertions.assertEquals(Arrays.asList(tokenizedExpression7), instance.tokenize(validExpression7));
        Assertions.assertEquals(Arrays.asList(tokenizedExpression8), instance.tokenize(validExpression8));
        Assertions.assertEquals(Arrays.asList(tokenizedExpression9), instance.tokenize(validExpression9));
        Assertions.assertEquals(Arrays.asList(tokenizedExpression10), instance.tokenize(validExpression10));
        Assertions.assertEquals(Arrays.asList(tokenizedExpression11), instance.tokenize(validExpression11));
        Assertions.assertEquals(Arrays.asList(tokenizedExpression12), instance.tokenize(validExpression12));
        Assertions.assertEquals(Arrays.asList(tokenizedExpression13), instance.tokenize(validExpression13));
    }
}
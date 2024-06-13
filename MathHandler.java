public class MathHandler {

    public static int evaluate(int operand1, int operand2, char operator) {
        // No modifications needed here!!!
        int result;
        switch (operator) {
            case '+':
                result = add(operand1, operand2);
                break;
            case '-':
                result = subtract(operand1, operand2);
                break;
            case 'x':
                result = multiply(operand1, operand2);
                break;
            case '÷':
                result = divide(operand1, operand2);
                break;
            default:
                result = 0;
        }
        return result;
    }

    public static double evaluate(double operand1, double operand2, char operator) {
        double result;
        switch (operator) {
            case '+':
                result = add(operand1, operand2);
                break;
            case '-':
                result = subtract(operand1, operand2);
                break;
            case 'x':
                result = multiply(operand1, operand2);
                break;
            case '÷':
                result = divide(operand1, operand2);
                break;
            default:
                result = 0;
        }
        return result;
    }

    public static double evaluate(double operand, char operator) {
        // No modifications needed here!!!
        double result;
        switch (operator) {
            case 's':
                result = sin(operand);
                break;
            case 't':
                result = tan(operand);
                break;
            case 'c':
                result = cos(operand);
                break;
            case 'e':
                result = exp(operand);
                break;
            default:
                result = 0;
        }
        return result;
    }

    public static int add(int operand1, int operand2) {
        return operand1 + operand2;
    }

    public static double add(double operand1, double operand2) {
        return operand1 + operand2;
    }

    public static int subtract(int operand1, int operand2) {
        return operand1 - operand2;
    }

    public static double subtract(double operand1, double operand2) {
        return operand1 - operand2;
    }

    public static int divide(int operand1, int operand2) {
        return operand1 / operand2;
    }

    public static double divide(double operand1, double operand2) {
        return operand1 / operand2;
    }

    public static int multiply(int operand1, int operand2) {
        return operand1 * operand2;
    }

    public static double multiply(double operand1, double operand2) {
        return operand1 * operand2;
    }

    public static double sin(double operand) {
        return Math.sin(Math.toRadians(operand));
    }

    public static double cos(double operand) {
        return Math.cos(Math.toRadians(operand));
    }

    public static double tan(double operand) {
        return Math.tan(Math.toRadians(operand));
    }

    public static double exp(double operand) {
        return Math.exp(operand);
    }
}

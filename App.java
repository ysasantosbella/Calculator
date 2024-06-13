import acm.graphics.GObject;
import acm.program.*;
import java.awt.event.MouseEvent;

public class App extends GraphicsProgram {

    private static final double CANVAS_WIDTH = 540;    // Calculator width
    private static final double CANVAS_HEIGHT = 740;   // Calculator height
    private CalculatorLayout calculatorLayout;         // Instantiate the Layout Object

    private char opBuffer;                             // Stores the operator
    private double operand1;                           // Stores the operand digits
    private String result;                             // Stores the results

    private boolean isFirstOp;                         // Checks if first operator
    private boolean isPriorEquals;                     // Checks if it is prior to equal sign
    private boolean isFirstPoint;                      // Checks if first decimal point
    private boolean isDeletable;                       // Checks if it is deletable

    public void run() {
        setTitle("LBYCPEI Calculator");
        setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        calculatorLayout = new CalculatorLayout(getHeight());
        add(calculatorLayout);
        initBooleans();
        addMouseListeners();                             // Adds the program as both a MouseListener and MouseMotionListener to the canvas.
    }

    public void mouseClicked(MouseEvent e) {
        // This method runs everytime you click the mouse, thus it enables you to access the mouse events

        GObject element = calculatorLayout.getElementAt(e.getX(), e.getY()); // getElementAt() Returns the topmost graphical object that contains the point (x, y), or null if no such object exists.

        if (element instanceof MyButton) {
            String input = ((MyButton) element).getText();    // Gets the text associated with the button. e.g. C, CE, ⌫, ±, 0, 1,...,9, etc.

            // I. Handle special cases: Clear Element, Clear All, and  Delete
            if (input.equals("CE ")) {
                calculatorLayout.clearMainDisplay();
                calculatorLayout.clearMemoElement(opBuffer);
                System.out.println("Clear Element");
                return;
            }
            if (input.equals("C")) {
                calculatorLayout.clearMainDisplay();
                calculatorLayout.clearMemoDisplay();
                initBooleans();
                System.out.println("Clear Called");
                return;
            }
            if (input.equals("⌫") && isDeletable) {   // ⌫ symbolizes delete
                calculatorLayout.deleteOneCharacter();
                System.out.println("Delete Called");
                return;
            }

            // II. Handle arithmetic symbols and operations
            char symbol = input.charAt(0); // Aiming for 0,1,2,3,4,5,6,7,8,9,.,±,=,+,-,x,÷,s,c,t,e

            if (symbol == '±' && isDeletable) {
                calculatorLayout.negateElement(opBuffer);
                System.out.println("Negation Called");
                return;
            }

            if ((symbol >= '0' && symbol <= '9') || symbol == '.') {
                isDeletable = true;
                if (symbol == '.') {
                    if (!isFirstPoint) {
                        return;
                    } else {
                        isFirstPoint = false;
                    }
                }
                if (isPriorEquals) {
                    calculatorLayout.clearMainDisplay();
                    isPriorEquals = false;
                    System.out.println("Digit: Prior Equals");
                }
                calculatorLayout.setMemoDisplay(symbol);
                calculatorLayout.setMainDisplay(symbol);
                System.out.println("Digit: Prior Not Equals");
                return;
            }

            double operand2;
            if (isOperator(symbol)) {
                if (isFirstOp && !isPriorEquals) {
                    operand1 = Double.parseDouble(calculatorLayout.getMainDisplay());
                    calculatorLayout.setMemoDisplay(symbol);
                    opBuffer = symbol;
                    isFirstOp = false;
                    System.out.println("Operator: First Operation and Not prior equals");
                } else if (isPriorEquals) {
                    calculatorLayout.setMemoDisplay(result + symbol);
                    opBuffer = symbol;
                    isFirstOp = false;
                    System.out.println("Operator: Prior equals!");
                } else {
                    operand2 = Double.parseDouble(calculatorLayout.getMainDisplay());
                    operand1 = MathHandler.evaluate(operand1, operand2, opBuffer);
                    result = "" + operand1;
                    result = result.contains(".") ? result.replaceAll("0*$", "").replaceAll("\\.$", "") : result;
                    calculatorLayout.setMainDisplay(result);
                    calculatorLayout.setMemoDisplay(symbol);
                    isDeletable = false;
                    System.out.println("Operator: Second operator");
                }
                calculatorLayout.clearNumBuffer();
                isFirstPoint = true;
            } else if (isTrig(symbol) || symbol == 'e') {
                operand1 = Double.parseDouble(calculatorLayout.getMainDisplay());
                operand1 = MathHandler.evaluate(operand1, symbol);
                result = "" + operand1;
                result = result.contains(".") ? result.replaceAll("0*$", "").replaceAll("\\.$", "") : result;
                calculatorLayout.setMemoDisplay(symbol);
                calculatorLayout.setMainDisplay(result);
                initBooleans();
                System.out.println("Function: " + symbol + " evaluated");
            } else if (symbol == '=') {
                operand2 = Double.parseDouble(calculatorLayout.getMainDisplay());
                operand1 = MathHandler.evaluate(operand1, operand2, opBuffer);
                operand1 = MathHandler.evaluate(operand1,opBuffer);
                result = "" + operand1;
                result = result.contains(".") ? result.replaceAll("0*$", "").replaceAll("\\.$", "") : result;
                calculatorLayout.setMainDisplay(result);
                calculatorLayout.clearMemoDisplay();
                initBooleans();
                System.out.println("Equals: evaluated");
                System.out.println("operand1 = " + operand1);
                System.out.println("operand2 = " + operand2);
            }
        }
    }

    private void initBooleans() {
        isFirstOp = true;
        isPriorEquals = true;
        isDeletable = false;
        isFirstPoint = true;
    }

    private boolean isOperator(char symbol) {
        return (symbol == '+' || symbol == '-' || symbol == 'x' || symbol == '÷');
    }

    private boolean isTrig(char symbol) {
        return (symbol == 's' || symbol == 'c' || symbol == 't' || symbol == 'e');
    }

    // Solves java.lang.NoClassDefFoundError
    public static void main(String[] args) {
        (new App()).start(args);
    }
}

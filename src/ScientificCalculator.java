import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class ScientificCalculator extends JFrame implements ActionListener {
    JTextField display;
    DecimalFormat df = new DecimalFormat("#.########");

    public ScientificCalculator() {
        setTitle("Scientific Calculator");
        setSize(450, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        // Buttons layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 5, 5, 5));

        String[] buttons = {
                "7", "8", "9", "÷", "sqrt",
                "4", "5", "6", "×", "x^y",
                "1", "2", "3", "−", "log",
                "0", ".", "=", "+", "C",
                "sin", "cos", "tan", "π", "e",
                "←", "(", ")", "%", "±"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        try {
            // Numbers and decimal
            if (cmd.matches("\\d") || cmd.equals(".")) {
                display.setText(display.getText() + cmd);
            }
            // Operators (show on screen instead of clearing)
            else if (cmd.equals("+") || cmd.equals("−") || cmd.equals("×") || cmd.equals("÷") || cmd.equals("%") || cmd.equals("x^y")) {
                if (!display.getText().isEmpty() && !display.getText().endsWith(" ")) {
                    display.setText(display.getText() + " " + cmd + " ");
                }
            }
            // Equals: parse and calculate
            else if (cmd.equals("=")) {
                String[] parts = display.getText().split(" ");
                if (parts.length < 3) return;

                double num1 = Double.parseDouble(parts[0]);
                String operator = parts[1];
                double num2 = Double.parseDouble(parts[2]);
                double result = 0;

                switch (operator) {
                    case "+": result = num1 + num2; break;
                    case "−": result = num1 - num2; break;
                    case "×": result = num1 * num2; break;
                    case "÷": result = num2 != 0 ? num1 / num2 : Double.NaN; break;
                    case "%": result = num1 % num2; break;
                    case "x^y": result = Math.pow(num1, num2); break;
                }
                display.setText(df.format(result));
            }
            // Scientific functions
            else if (cmd.equals("sqrt")) {
                double val = Double.parseDouble(display.getText());
                display.setText(df.format(Math.sqrt(val)));
            } else if (cmd.equals("log")) {
                double val = Double.parseDouble(display.getText());
                display.setText(df.format(Math.log10(val)));
            } else if (cmd.equals("sin")) {
                double val = Double.parseDouble(display.getText());
                display.setText(df.format(Math.sin(Math.toRadians(val))));
            } else if (cmd.equals("cos")) {
                double val = Double.parseDouble(display.getText());
                display.setText(df.format(Math.cos(Math.toRadians(val))));
            } else if (cmd.equals("tan")) {
                double val = Double.parseDouble(display.getText());
                display.setText(df.format(Math.tan(Math.toRadians(val))));
            } else if (cmd.equals("π")) {
                display.setText(String.valueOf(Math.PI));
            } else if (cmd.equals("e")) {
                display.setText(String.valueOf(Math.E));
            }
            // Clear
            else if (cmd.equals("C")) {
                display.setText("");
            }
            // Backspace
            else if (cmd.equals("←")) {
                String text = display.getText();
                if (!text.isEmpty()) {
                    display.setText(text.substring(0, text.length() - 1));
                }
            }
            // Plus/minus toggle
            else if (cmd.equals("±")) {
                if (!display.getText().isEmpty()) {
                    double val = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(-val));
                }
            }
        } catch (Exception ex) {
            display.setText("Error");
        }
    }

    public static void main(String[] args) {
        new ScientificCalculator();
    }
}

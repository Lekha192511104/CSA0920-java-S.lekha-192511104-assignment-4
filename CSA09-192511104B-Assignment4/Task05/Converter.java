import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Converter extends JFrame {
    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> target =
        new JComboBox<>(new String[]{"USD", "EUR"});
    private final JLabel result = new JLabel(" ");

    private static double rate(String currency) {
        return currency.equals("USD") ? 0.012 : 0.011;
    }
    public Converter() {
        super("Currency Converter");
        setLayout(new FlowLayout());
        JButton convertButton = new JButton("Convert");
        add(new JLabel("INR")); add(amount); add(target);
        add(convertButton); add(result);
        ActionListener handler = e -> convert();
        convertButton.addActionListener(handler);
        amount.addActionListener(handler);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack(); setLocationRelativeTo(null);
    }
    private void convert() {
        try {
            double inr = Double.parseDouble(amount.getText().trim());
            if (!Double.isFinite(inr) || inr < 0) throw new NumberFormatException();
            String currency = (String) target.getSelectedItem();
            String output = String.format("%s %.2f", currency, inr * rate(currency));
            result.setText(output);
            JOptionPane.showMessageDialog(this, output, "Conversion Result",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            result.setText("Invalid amount");
            JOptionPane.showMessageDialog(this, "Invalid amount", "Input Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Converter().setVisible(true));
    }
}

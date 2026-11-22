import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

public class ConverterNamedInner extends JFrame {
    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> target = new JComboBox<>(new String[]{"USD", "EUR"});
    private final JLabel result = new JLabel(" ");
    public ConverterNamedInner() {
        super("Converter - Named Inner Class");
        setLayout(new FlowLayout());
        JButton button = new JButton("Convert");
        add(new JLabel("INR")); add(amount); add(target); add(button); add(result);
        ConvertHandler handler = new ConvertHandler();
        button.addActionListener(handler); amount.addActionListener(handler);
        setDefaultCloseOperation(EXIT_ON_CLOSE); pack(); setLocationRelativeTo(null);
    }
    private class ConvertHandler implements ActionListener {
        @Override public void actionPerformed(ActionEvent e) { convert(); }
    }
    private void convert() {
        try {
            double v = Double.parseDouble(amount.getText().trim());
            if (!Double.isFinite(v) || v < 0) throw new NumberFormatException();
            String c = (String)target.getSelectedItem();
            String s = String.format("%s %.2f", c, v * (c.equals("USD") ? .012 : .011));
            result.setText(s); JOptionPane.showMessageDialog(this, s);
        } catch (NumberFormatException ex) { result.setText("Invalid amount"); }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConverterNamedInner().setVisible(true));
    }
}

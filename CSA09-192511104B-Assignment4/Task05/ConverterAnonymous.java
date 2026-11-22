import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

public class ConverterAnonymous extends JFrame {
    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> target = new JComboBox<>(new String[]{"USD", "EUR"});
    private final JLabel result = new JLabel(" ");
    public ConverterAnonymous() {
        super("Converter - Anonymous Inner Class");
        setLayout(new FlowLayout());
        JButton button = new JButton("Convert");
        add(new JLabel("INR")); add(amount); add(target); add(button); add(result);
        ActionListener handler = new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { convert(); }
        };
        button.addActionListener(handler); amount.addActionListener(handler);
        setDefaultCloseOperation(EXIT_ON_CLOSE); pack(); setLocationRelativeTo(null);
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
        SwingUtilities.invokeLater(() -> new ConverterAnonymous().setVisible(true));
    }
}

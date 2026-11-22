import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

public class ConverterFrameListener extends JFrame implements ActionListener {
    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> target = new JComboBox<>(new String[]{"USD", "EUR"});
    private final JLabel result = new JLabel(" ");
    private final JButton convertButton = new JButton("Convert");
    public ConverterFrameListener() {
        super("Converter - Frame implements ActionListener");
        setLayout(new FlowLayout());
        add(new JLabel("INR")); add(amount); add(target); add(convertButton); add(result);
        convertButton.addActionListener(this); amount.addActionListener(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE); pack(); setLocationRelativeTo(null);
    }
    @Override public void actionPerformed(ActionEvent e) { convert(); }
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
        SwingUtilities.invokeLater(() -> new ConverterFrameListener().setVisible(true));
    }
}

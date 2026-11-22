import java.awt.*;
import java.awt.event.*;

public class FeeWindow extends Frame {
    private final TextField regNo = new TextField(15);
    private final TextField amount = new TextField(10);
    private final Label status = new Label("Enter fee details");
    private final FeeService service = new FeeService();

    public FeeWindow() {
        super("Student Fee Payment");
        setLayout(new GridLayout(4, 2, 5, 5));
        Button pay = new Button("Pay");
        Button clear = new Button("Clear");
        Button receipt = new Button("Receipt");
        add(new Label("Registration No:")); add(regNo);
        add(new Label("Amount:")); add(amount);
        add(pay); add(clear);
        add(receipt); add(status);
        pay.addActionListener(e -> handlePay());
        clear.addActionListener(e -> {
            regNo.setText(""); amount.setText(""); status.setText("Cleared");
        });
        receipt.addActionListener(e ->
            status.setText("Receipt is available after payment."));
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) { dispose(); }
        });
        pack();
        setLocationRelativeTo(null);
    }
    private void handlePay() {
        try {
            double value = Double.parseDouble(amount.getText().trim());
            status.setText(service.pay(regNo.getText(), value));
        } catch (NumberFormatException ex) {
            status.setText("Enter a valid numeric amount");
        } catch (IllegalArgumentException ex) {
            status.setText(ex.getMessage());
        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new FeeWindow().setVisible(true));
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;

public class EventRegistrationFrame extends JFrame {
    private final JTextField regNo = new JTextField(12), name = new JTextField(12),
        event = new JTextField(12), email = new JTextField(16);
    private final JLabel status = new JLabel("Ready");
    private final DefaultTableModel model = new DefaultTableModel(
        new Object[]{"Register No", "Student Name", "Event", "Email"}, 0);
    private final RegistrationService service = new RegistrationService();
    private final int pageSize = 50;
    private int offset = 0;

    public EventRegistrationFrame() {
        super("Campus Event Registration");
        setLayout(new BorderLayout(5, 5));
        JMenu fileMenu = new JMenu("Registration");
        JMenuItem refreshItem = new JMenuItem("Refresh");
        JMenuItem exportItem = new JMenuItem("Export CSV");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(refreshItem); fileMenu.add(exportItem); fileMenu.add(exitItem);
        JMenuBar bar = new JMenuBar(); bar.add(fileMenu); setJMenuBar(bar);

        JPanel form = new JPanel(new GridLayout(2, 4, 5, 5));
        form.add(new JLabel("Register No")); form.add(regNo);
        form.add(new JLabel("Student Name")); form.add(name);
        form.add(new JLabel("Event Name")); form.add(event);
        form.add(new JLabel("Email")); form.add(email);
        add(form, BorderLayout.NORTH);
        JTable table = new JTable(model); add(new JScrollPane(table), BorderLayout.CENTER);

        JButton save = new JButton("Register"), previous = new JButton("Previous"),
            next = new JButton("Next");
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.add(save); actions.add(previous); actions.add(next); actions.add(status);
        add(actions, BorderLayout.SOUTH);
        save.addActionListener(e -> saveRegistration());
        refreshItem.addActionListener(e -> loadPage());
        previous.addActionListener(e -> { offset = Math.max(0, offset-pageSize); loadPage(); });
        next.addActionListener(e -> { offset += pageSize; loadPage(); });
        exportItem.addActionListener(e -> exportCsv());
        exitItem.addActionListener(e -> dispose());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 500); setLocationRelativeTo(null); loadPage();
    }
    private Registration readForm() {
        return new Registration(regNo.getText().trim(), name.getText().trim(),
            event.getText().trim(), email.getText().trim());
    }
    private void saveRegistration() {
        Registration registration = readForm(); status.setText("Saving...");
        new SwingWorker<Void, Void>() {
            @Override protected Void doInBackground() throws Exception {
                service.register(registration); return null;
            }
            @Override protected void done() {
                try {
                    get(); status.setText("Registration successful");
                    JOptionPane.showMessageDialog(EventRegistrationFrame.this, "Registration saved.");
                    loadPage();
                } catch (Exception ex) {
                    status.setText("Registration failed");
                    Throwable cause = ex.getCause() == null ? ex : ex.getCause();
                    JOptionPane.showMessageDialog(EventRegistrationFrame.this,
                        "Could not register: " + cause.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }
    private void loadPage() {
        status.setText("Loading...");
        new SwingWorker<List<Registration>, Void>() {
            @Override protected List<Registration> doInBackground() throws Exception {
                return service.getPage(pageSize, offset);
            }
            @Override protected void done() {
                try {
                    List<Registration> rows = get(); model.setRowCount(0);
                    for (Registration r : rows) model.addRow(new Object[]{
                        r.getRegNo(), r.getName(), r.getEvent(), r.getEmail()});
                    status.setText("Loaded " + rows.size() + " row(s)");
                } catch (Exception ex) {
                    status.setText("Load failed");
                    Throwable cause = ex.getCause() == null ? ex : ex.getCause();
                    JOptionPane.showMessageDialog(EventRegistrationFrame.this,
                        "Could not load data: " + cause.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }
    private void exportCsv() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("registrations.csv"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        try (PrintWriter out = new PrintWriter(new FileWriter(chooser.getSelectedFile()))) {
            out.println("Register No,Student Name,Event,Email");
            for (int i=0; i<model.getRowCount(); i++)
                out.println(csv(model.getValueAt(i,0))+","+csv(model.getValueAt(i,1))+","+
                    csv(model.getValueAt(i,2))+","+csv(model.getValueAt(i,3)));
            status.setText("CSV exported");
        } catch (IOException ex) {
            status.setText("Export failed");
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private String csv(Object value) {
        return "\"" + String.valueOf(value).replace("\"", "\"\"") + "\"";
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EventRegistrationFrame().setVisible(true));
    }
}

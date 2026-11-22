import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class LibrarySearch extends JFrame {
    private final JTextField query = new JTextField(20);
    private final JComboBox<String> category =
        new JComboBox<>(new String[]{"Title", "Author", "Subject"});
    private final DefaultTableModel model = new DefaultTableModel(
        new Object[]{"Title", "Author", "Availability"}, 0);
    private final JLabel status = new JLabel("Ready");

    public LibrarySearch() {
        super("Campus Library Search");
        setLayout(new BorderLayout(5, 5));
        JLabel title = new JLabel("Campus Library Search");
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.add(title); add(titlePanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton search = new JButton("Search");
        searchPanel.add(new JLabel("Search:")); searchPanel.add(query);
        searchPanel.add(category); searchPanel.add(search);

        JTable results = new JTable(model);
        JPanel center = new JPanel(new BorderLayout());
        center.add(searchPanel, BorderLayout.NORTH);
        center.add(new JScrollPane(results), BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        JButton prev = new JButton("Prev"), next = new JButton("Next"),
                reserve = new JButton("Reserve");
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(prev); buttons.add(next); buttons.add(reserve);
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(buttons, BorderLayout.CENTER);
        bottom.add(status, BorderLayout.SOUTH);
        add(bottom, BorderLayout.SOUTH);

        search.addActionListener(e -> {
            model.setRowCount(0);
            String text = query.getText().trim();
            if (text.isEmpty()) status.setText("Enter a search term");
            else {
                model.addRow(new Object[]{text, "Sample Author", "Available"});
                status.setText("Search completed");
            }
        });
        prev.addActionListener(e -> status.setText("Previous page"));
        next.addActionListener(e -> status.setText("Next page"));
        reserve.addActionListener(e -> status.setText(
            results.getSelectedRow() < 0 ? "Select a result to reserve"
                                        : "Reservation request selected"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(450, 300));
        setSize(700, 450); setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarySearch().setVisible(true));
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AppointmentDesk extends JFrame {
    private final JTextField patientId = new JTextField(15);
    private final JComboBox<String> department = new JComboBox<>(
        new String[]{"Cardiology", "Orthopedics", "Neurology"});
    private final JComboBox<String> doctor = new JComboBox<>();
    private final JComboBox<String> slot = new JComboBox<>(
        new String[]{"09:00 AM", "10:00 AM", "11:00 AM"});
    private final JLabel status = new JLabel("Ready");
    private final Map<String, String[]> doctors = new LinkedHashMap<>();

    public AppointmentDesk() {
        super("Hospital Outpatient Appointment Desk");
        doctors.put("Cardiology", new String[]{"Dr. Arun", "Dr. Meena"});
        doctors.put("Orthopedics", new String[]{"Dr. Kumar", "Dr. Priya"});
        doctors.put("Neurology", new String[]{"Dr. Ravi", "Dr. Nisha"});
        setLayout(new BorderLayout(8, 8));
        JPanel fields = new JPanel(new GridLayout(4, 2, 8, 8));
        fields.add(new JLabel("Patient ID")); fields.add(patientId);
        fields.add(new JLabel("Department")); fields.add(department);
        fields.add(new JLabel("Doctor")); fields.add(doctor);
        fields.add(new JLabel("Time Slot")); fields.add(slot);
        add(fields, BorderLayout.CENTER);
        JButton book = new JButton("Book Appointment");
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.add(book); bottom.add(status); add(bottom, BorderLayout.SOUTH);
        department.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) refreshDoctors();
        });
        book.addActionListener(e -> bookAppointment());
        patientId.addActionListener(e -> bookAppointment());
        refreshDoctors();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 260); setLocationRelativeTo(null);
    }
    private void refreshDoctors() {
        doctor.removeAllItems();
        String selected = (String) department.getSelectedItem();
        if (selected != null)
            for (String name : doctors.get(selected)) doctor.addItem(name);
    }
    private void bookAppointment() {
        String id = patientId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Patient ID is required");
            return;
        }
        String doc = (String) doctor.getSelectedItem();
        String time = (String) slot.getSelectedItem();
        if (doc == null || time == null) {
            JOptionPane.showMessageDialog(this, "Select doctor and time slot");
            return;
        }
        status.setText("Appointment booked: " + id + " / " + doc + " / " + time);
        JOptionPane.showMessageDialog(this, "Appointment booked");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppointmentDesk().setVisible(true));
    }
}

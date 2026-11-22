import java.sql.*;
import java.util.*;

public class RegistrationRepository {
    public void insert(Registration r) throws SQLException {
        String sql = "INSERT INTO registrations "
            + "(student_reg_no, student_name, event_name, email) VALUES (?, ?, ?, ?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, r.getRegNo()); ps.setString(2, r.getName());
            ps.setString(3, r.getEvent()); ps.setString(4, r.getEmail());
            ps.executeUpdate();
        }
    }
    public List<Registration> findPage(int limit, int offset) throws SQLException {
        List<Registration> list = new ArrayList<>();
        String sql = "SELECT student_reg_no, student_name, event_name, email "
                   + "FROM registrations ORDER BY id LIMIT ? OFFSET ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, limit); ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(new Registration(
                    rs.getString("student_reg_no"), rs.getString("student_name"),
                    rs.getString("event_name"), rs.getString("email")));
            }
        }
        return list;
    }
}

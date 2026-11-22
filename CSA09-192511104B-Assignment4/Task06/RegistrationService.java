import java.sql.SQLException;
import java.util.List;

public class RegistrationService {
    private final RegistrationRepository repo = new RegistrationRepository();
    public void register(Registration r) throws SQLException {
        if (r.getRegNo().trim().isEmpty() || r.getName().trim().isEmpty()
            || r.getEvent().trim().isEmpty() || r.getEmail().trim().isEmpty())
            throw new IllegalArgumentException("All fields are required");
        if (!r.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))
            throw new IllegalArgumentException("Enter a valid email address");
        repo.insert(r);
    }
    public List<Registration> getPage(int limit, int offset) throws SQLException {
        return repo.findPage(limit, offset);
    }
}

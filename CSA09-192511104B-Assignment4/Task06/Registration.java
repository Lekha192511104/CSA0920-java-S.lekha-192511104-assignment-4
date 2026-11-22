public class Registration {
    private final String regNo, name, event, email;
    public Registration(String regNo, String name, String event, String email) {
        this.regNo = regNo; this.name = name; this.event = event; this.email = email;
    }
    public String getRegNo() { return regNo; }
    public String getName() { return name; }
    public String getEvent() { return event; }
    public String getEmail() { return email; }
}

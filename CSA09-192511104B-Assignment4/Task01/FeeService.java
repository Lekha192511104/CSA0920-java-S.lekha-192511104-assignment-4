public class FeeService {
    public String pay(String regNo, double amount) {
        if (regNo == null || regNo.trim().isEmpty())
            throw new IllegalArgumentException("Registration number is required");
        if (!Double.isFinite(amount) || amount <= 0)
            throw new IllegalArgumentException("Amount must be positive");
        return "Payment request accepted for " + regNo.trim()
                + " : Rs. " + String.format("%.2f", amount);
    }
}

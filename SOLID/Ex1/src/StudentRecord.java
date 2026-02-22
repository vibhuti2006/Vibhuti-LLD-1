import java.util.*;

public class StudentRecord {
    public final String id;
    public final String name;
    public final String email;
    public final String phone;
    public final String program;

    public StudentRecord(String id, String name, String email, String phone, String program) {
        this.id = id; this.name = name; this.email = email; this.phone = phone; this.program = program;
    }

    @Override
    public String toString() {
        return "StudentRecord{id='" + id + "', name='" + name + "', email='" + email + "', phone='" + phone + "', program='" + program + "'}";
    }
}

class StudentValidator {
    public static List<String> validate(String name, String email, String phone, String program) {
        List<String> errors = new ArrayList<>();
        if (name.isBlank()) errors.add("name is required");
        if (email.isBlank() || !email.contains("@")) errors.add("email is invalid");
        if (phone.isBlank() || !phone.chars().allMatch(Character::isDigit)) errors.add("phone is invalid");
        if (!(program.equals("CSE") || program.equals("AI") || program.equals("SWE"))) errors.add("program is invalid");
        return errors;
    }
}

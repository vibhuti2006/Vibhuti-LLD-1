import java.util.*;

public class OnboardingService {
    private final StudentStore store;

    public OnboardingService(StudentStore store) { this.store = store; }

    public void registerFromRawInput(String raw) {
        TextTable.printInput(raw);

        Map<String, String> kv = ConsoleInput.parse(raw);

        String name = kv.getOrDefault("name", "");
        String email = kv.getOrDefault("email", "");
        String phone = kv.getOrDefault("phone", "");
        String program = kv.getOrDefault("program", "");

        List<String> errors = StudentValidator.validate(name, email, phone, program);
        if (!errors.isEmpty()) {
            TextTable.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(store.count());
        StudentRecord rec = new StudentRecord(id, name, email, phone, program);

        store.save(rec);

        TextTable.printConfirmation(id, rec, store.count());
    }
}

import java.util.*;

interface StudentStore {
    void save(StudentRecord r);
    int count();
    List<StudentRecord> all();
}

public class FakeDb implements StudentStore {
    private final List<StudentRecord> rows = new ArrayList<>();

    public void save(StudentRecord r) { rows.add(r); }
    public int count() { return rows.size(); }
    public List<StudentRecord> all() { return Collections.unmodifiableList(rows); }
}

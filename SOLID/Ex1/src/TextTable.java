import java.util.*;

public class TextTable {
    public static String render3(StudentStore store) {
        StringBuilder sb = new StringBuilder();
        sb.append("| ID             | NAME | PROGRAM |\n");
        for (StudentRecord r : store.all()) {
            sb.append(String.format("| %-14s | %-4s | %-7s |\n", r.id, r.name, r.program));
        }
        return sb.toString();
    }

    public static void printInput(String raw) {
        System.out.println("INPUT: " + raw);
    }

    public static void printErrors(List<String> errors) {
        System.out.println("ERROR: cannot register");
        for (String e : errors) System.out.println("- " + e);
    }

    public static void printConfirmation(String id, StudentRecord rec, int totalCount) {
        System.out.println("OK: created student " + id);
        System.out.println("Saved. Total students: " + totalCount);
        System.out.println("CONFIRMATION:");
        System.out.println(rec);
    }
}

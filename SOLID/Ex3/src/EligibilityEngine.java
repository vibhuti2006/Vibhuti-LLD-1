import java.util.*;

public class EligibilityEngine {
    private final FakeEligibilityStore store;
    private final List<RuleInput> rules;

    public EligibilityEngine(FakeEligibilityStore store) {
        this.store = store;
        this.rules = List.of(
            new DisciplinaryRule(),
            new CgrRule(),
            new AttendanceRule(),
            new CreditsRule()
        );
    }

    public void runAndPrint(StudentProfile s) {
        ReportPrinter p = new ReportPrinter();
        EligibilityEngineResult r = evaluate(s);
        p.print(s, r);
        store.save(s.rollNo, r.status);
    }

    public EligibilityEngineResult evaluate(StudentProfile s) {
        List<String> reasons = new ArrayList<>();
        String status = "ELIGIBLE";

        for (RuleInput rule : rules) {
            String reason = rule.check(s);
            if (reason != null) {
                status = "NOT_ELIGIBLE";
                reasons.add(reason);
                break;
            }
        }

        return new EligibilityEngineResult(status, reasons);
    }
}

class EligibilityEngineResult {
    public final String status;
    public final List<String> reasons;
    public EligibilityEngineResult(String status, List<String> reasons) {
        this.status = status;
        this.reasons = reasons;
    }
}

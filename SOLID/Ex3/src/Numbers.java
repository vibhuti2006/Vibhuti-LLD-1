
class DisciplinaryRule implements RuleInput {
    public String check(StudentProfile s) {
        if (s.disciplinaryFlag != LegacyFlags.NONE) return "disciplinary flag present";
        return null;
    }
}

class CgrRule implements RuleInput {
    public String check(StudentProfile s) {
        if (s.cgr < 8.0) return "CGR below 8.0";
        return null;
    }
}

class AttendanceRule implements RuleInput {
    public String check(StudentProfile s) {
        if (s.attendancePct < 75) return "attendance below 75";
        return null;
    }
}

class CreditsRule implements RuleInput {
    public String check(StudentProfile s) {
        if (s.earnedCredits < 20) return "credits below 20";
        return null;
    }
}

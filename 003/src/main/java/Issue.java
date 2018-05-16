import java.util.Objects;

public class Issue {
    private static final int TRUNCATED_LENGTH = 200;
    private final String summary;
    private final String description;

    public Issue(String summary, String description) {
        this.summary = summary;
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return Objects.equals(summary, issue.summary) &&
                Objects.equals(description, issue.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(summary, description);
    }

    public Issue preview() {
        String summary = this.summary;
        String description = this.description;
        if (summary.length() > TRUNCATED_LENGTH) {
            summary = summary.substring(0, TRUNCATED_LENGTH);
        }
        if (description.length() > TRUNCATED_LENGTH) {
            description = description.substring(0, TRUNCATED_LENGTH);
        }
        description = description.replaceAll("\\s+", " ");
        return new Issue(summary, description);
    }
}

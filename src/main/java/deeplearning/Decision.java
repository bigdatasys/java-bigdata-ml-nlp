package deeplearning;

public class Decision {
    private Double topic1Decision;
    private Double topic2Decision;
    private Double topic3Decision;
    private Double topic4Decision;

    private String decisionClass;

    public Decision(Double topic1Decision, Double topic2Decision, Double topic3Decision, Double topic4Decision) {
        this.topic1Decision = topic1Decision;
        this.topic2Decision = topic2Decision;
        this.topic3Decision = topic3Decision;
        this.topic4Decision = topic4Decision;
    }

    public Double getTopic1Decision() {
        return topic1Decision;
    }

    public void setTopic1Decision(Double topic1Decision) {
        this.topic1Decision = topic1Decision;
    }

    public Double getTopic2Decision() {
        return topic2Decision;
    }

    public void setTopic2Decision(Double topic2Decision) {
        this.topic2Decision = topic2Decision;
    }

    public Double getTopic3Decision() {
        return topic3Decision;
    }

    public void setTopic3Decision(Double topic3Decision) {
        this.topic3Decision = topic3Decision;
    }

    public Double getTopic4Decision() {
        return topic4Decision;
    }

    public void setTopic4Decision(Double topic4Decision) {
        this.topic4Decision = topic4Decision;
    }

    public String getDecisionClass() {
        return decisionClass;
    }

    public void setDecisionClass(String decisionClass) {
        this.decisionClass = decisionClass;
    }

    @Override
    public String toString() {
        return String.format(
                "Decision class = %s, Data[ Decision about topic#1 = %.1f, Decision about topic#2 = %.1f, Decision about topic#3 = %.1f, Decision about topic#4 = %.1f ]",
                decisionClass, topic1Decision, topic2Decision, topic3Decision, topic4Decision);
    }

}

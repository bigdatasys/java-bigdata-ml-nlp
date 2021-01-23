package deeplearning;

import java.io.IOException;

public class DeepLearningApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        DecisionClassifier classifier = new DecisionClassifier();
        classifier.classify("decision.csv", "decision-test.csv");
    }
}

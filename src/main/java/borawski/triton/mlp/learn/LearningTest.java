package borawski.triton.mlp.learn;

import borawski.triton.mlp.LinearClassifier;

/**
 * Created by Ethan on 5/14/2018.
 */
public class LearningTest {

    public static void main(String[] args) {
        double[] thetaVector = new double[] {1.004579, 5.286822};
        LinearClassifier classifier = new LinearClassifier(thetaVector);

        double[] featureVector = new double[] {1.0, 1330.0};
        double prediction = classifier.apply(featureVector);

        System.out.println("Predicted: " + prediction);
    }

}

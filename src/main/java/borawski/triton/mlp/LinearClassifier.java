package borawski.triton.mlp;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ethan on 5/13/2018.
 */
public class LinearClassifier {

    private final double[] thetaVector;

    public LinearClassifier(double[] thetaVector) {
        this.thetaVector = Arrays.copyOf(thetaVector, thetaVector.length);
    }

    public double apply(double[] featureVector) {
        assert featureVector[0] == 1.0;

        double prediction = 0;
        for(int i = 0; i < thetaVector.length; i++) {
            prediction += thetaVector[i] * featureVector[i];
        }

        return prediction;
    }

    public double[] getThetas() {
        return Arrays.copyOf(thetaVector, thetaVector.length);
    }

    public LinearClassifier train(LinearClassifier targetFunction,
                                  List<double[]> dataset,
                                  List<double[]> labels,
                                  double alpha) {

        int m = dataset.size();
        double[] thetaVector = targetFunction.getThetas();
        double[] newThetaVector = new double[thetaVector.length];

        for(int j = 0; j < thetaVector.length; j++) {
            double sumErrors = 0;
            for(int i = 0; i < m; i++) {
                double[] featureVector = dataset.get(i);
                double error = targetFunction.apply(featureVector) - labels.get(i)[j];
                sumErrors += error * featureVector[j];
            }

            double gradient = (1.0/m) * sumErrors;
            newThetaVector[j] = thetaVector[j] - alpha * gradient;
        }

        return new LinearClassifier(newThetaVector);
    }

}

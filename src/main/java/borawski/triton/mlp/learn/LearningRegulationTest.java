package borawski.triton.mlp.learn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 5/14/2018.
 */
public class LearningRegulationTest {

    public static void main(String[] args) {
        List<double[]> dataset = new ArrayList<>();
        dataset.add(new double[] {1.0, 90.0, 8100.0});
        dataset.add(new double[] {1.0, 101.0, 10201.0});
        dataset.add(new double[] {1.0, 103.0, 10609.0});

        List<Double> labels = new ArrayList<>();
        labels.add(249.0);
        labels.add(338.0);
        labels.add(304.0);

        List<double[]> scaledDataset = new ArrayList<>();
        double nSum;
        double nAvg;
        dataset.forEach((data) -> {
            double sum = 0;
            for(int i = 0; i < data.length; i++) {
                sum = sum + data[i];
            }
            double avg = sum / data.length;


        });
    }

}

package Classify;

import junit.framework.TestCase;

import java.io.File;

/**
 * Created by supc on 2018/5/1 0001.
 */
public class ClassifierNeuralNetworkTest extends TestCase {
    public void testCreateClassifier() throws Exception {
        File file = new File("TrainData.arff");
        File testFiel = new File("test.arff");
        ClassifierNeuralNetwork nn = new ClassifierNeuralNetwork();
        nn.createClassifier(file, "");
        nn.loadClassifier("NeuralNetwork.model");
        nn.insClassify(testFiel, "d:\\");
    }

}
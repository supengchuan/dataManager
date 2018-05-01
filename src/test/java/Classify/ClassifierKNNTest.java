package Classify;

import junit.framework.TestCase;

import java.io.File;

/**
 * Created by supc on 2018/5/1 0001.
 */
public class ClassifierKNNTest extends TestCase {
    public void testCreateClassifier() throws Exception {
        File file = new File("TrainData.arff");
        ClassifierKNN knn  = new ClassifierKNN();
        knn.createClassifier(file, "");
        knn.loadClassifier("KNN.model");
        File testFiel = new File("test.arff");
        knn.insClassify(testFiel, "d:\\");
    }

}
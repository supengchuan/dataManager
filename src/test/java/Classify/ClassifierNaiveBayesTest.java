package Classify;

import junit.framework.TestCase;

import java.io.File;

/**
 * Created by supc on 2018/5/1 0001.
 */
public class ClassifierNaiveBayesTest extends TestCase {
    public void testCreateClassifier() throws Exception {
        File file = new File("TrainData.arff");
        File testFiel = new File("test.arff");
        ClassifierNaiveBayes bayes = new ClassifierNaiveBayes();
        bayes.createClassifier(file, "");
        bayes.loadClassifier("NaiveBayes.model");
        bayes.insClassify(testFiel, "d:\\");
    }

}
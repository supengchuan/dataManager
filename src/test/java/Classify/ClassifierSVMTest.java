package Classify;

import junit.framework.TestCase;

import java.io.File;

/**
 * Created by supc on 2018/5/1 0001.
 */
public class ClassifierSVMTest extends TestCase {
    public void testCreateClassifier() throws Exception {
        File file = new File("TrainData.arff");
        File testFiel = new File("test.arff");
        ClassifierSVM svm = new ClassifierSVM();
        svm.createClassifier(file, "");
        svm.loadClassifier("Logistic.model");
        svm.insClassify(testFiel, "d:\\");
    }

}
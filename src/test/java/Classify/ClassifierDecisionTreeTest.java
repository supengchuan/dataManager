package Classify;

import junit.framework.TestCase;
import weka.core.TechnicalInformation;
import weka.core.converters.ArffLoader;

import java.io.File;

/**
 * Created by supc on 2018/4/30 0030.
 */
public class ClassifierDecisionTreeTest extends TestCase {
    public void testCreateClassifier() throws Exception {
        File file= new File("TrainData.arff");
        ClassifierDecisionTree classifier = new ClassifierDecisionTree();
        classifier.createClassifier(file, "");
        classifier.loadClassifier("DecisionTree.model");
        File testFiel = new File("test.arff");
        classifier.insClassify(testFiel, "d:\\");
    }

}
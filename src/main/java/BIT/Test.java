package BIT;

import Classify.*;
import Score.OneShot;
import org.apache.log4j.Logger;
import weka.core.Instances;

import java.io.File;

/**
 * Created by supc on 2017/12/20 0020.
 */
public class Test {

    private static final Logger logger = Logger.getLogger(Test.class);
    public static void main(String[] args) {

       /* //测试指标权重分析
        File file = new File("TrainData.arff");
        List<TargetInstance> res = WeightSort.sortWeight(file);

        System.out.println("指标名称 - 合格方差 - 不合格方差 - 方差距离");
        for (TargetInstance a : res) {
            System.out.println(a.getName() + " - " + a.getQualifiedVariance() + " - " + a.getDisQualifiedVariance() + " - " + a.getDistanceVariance());
        }

        //测试分类器
        //DemoClassify myClassify = new DemoClassify();
        ClassifierKNN myClassify = new ClassifierKNN();
        myClassify.createClassifier(file, "");
        myClassify.loadClassifier("NaiveBayes.model");

        Instances curve = myClassify.evalClassifier(file, "d:\\");



        // System.out.println("The error rate is:" + errorRate);
        File testFile = new File("test.arff");
        Instances result = myClassify.insClassify(testFile, "d:\\");


        System.out.println();*/
       // File file = new File("TrainData.arff");

       // File testFile = new File("test.arff");
       // DemoClassify myClassify = new DemoClassify();
       // myClassify.createClassifier(file, "d:\\");

       /* Instances res;
        //KNN分类器的各项功能
        ClassifierKNN knn = new ClassifierKNN();
        knn.createClassifier(file, "");
        knn.evalClassifier(file, "d:\\");
        knn.loadClassifier("KNN.model");
        knn.insClassify(testFile, "d:\\");
        //决策树分类器的各项功能
        ClassifierDecisionTree dtree = new ClassifierDecisionTree();
        dtree.createClassifier(file, "");
        dtree.evalClassifier(file, "d:\\");
        dtree.loadClassifier("DecisionTree.model");
        dtree.insClassify(testFile, "d:\\");
        //贝叶斯分类器的各项功能
        ClassifierNaiveBayes bayes = new ClassifierNaiveBayes();
        bayes.createClassifier(file, "");
        bayes.evalClassifier(file, "d:\\");
        bayes.loadClassifier("NaiveBayes.model");
        res = bayes.insClassify(testFile, "d:\\");
        //逻辑回归分类器的各项功能
        ClassifierLogistic logistic = new ClassifierLogistic();
        logistic.createClassifier(file,"");
        logistic.evalClassifier(file,"d:\\");
        logistic.loadClassifier("Logistic.model");
        logistic.insClassify(testFile,"d:\\");
        //svm分类器的各项功能
        ClassifierSVM svm = new ClassifierSVM();
        svm.createClassifier(file,"");
        svm.evalClassifier(file,"d:\\");
        svm.loadClassifier("SVM.model");
        svm.insClassify(testFile,"d:\\");
        //神经网络分类器的各项功能
        ClassifierNeuralNetwork nn = new ClassifierNeuralNetwork();
        nn.createClassifier(file,"");
        nn.evalClassifier(file,"d:\\");
        nn.loadClassifier("NeuralNetworkBayes.model");
        nn.insClassify(testFile,"d:\\");
*/


        /*String[] describe = {"极好", "好", "一般", "差", "极差"};
        double[] score = {100, 90, 80, 70, 60};
        String[] dataSet = {"好", "极好", "好", "一般", "好", "一般", "差", "极差", "差", "差", "极差"};

        double[] res = DescribeType.m_DescribeType(describe, score, dataSet);*/

/*        //AHP
        //添加节点参数
        List<NodeAHP> listNode = new ArrayList<NodeAHP>();
        NodeAHP A = new NodeAHP(1, "A");
        listNode.add(A);

        NodeAHP B1 = new NodeAHP(2, "B1");
        NodeAHP B2 = new NodeAHP(2, "B2");
        NodeAHP B3 = new NodeAHP(2, "B3");
        listNode.add(B1);
        listNode.add(B2);
        listNode.add(B3);

        NodeAHP C1 = new NodeAHP(3, "C1");
        NodeAHP C2 = new NodeAHP(3, "C2");
        NodeAHP C3 = new NodeAHP(3, "C3");
        NodeAHP C4 = new NodeAHP(3, "C4");
        NodeAHP C5 = new NodeAHP(3, "C5");
        listNode.add(C1);
        listNode.add(C2);
        listNode.add(C3);
        listNode.add(C4);
        listNode.add(C5);
        //添加关系矩阵
        List<RelationMatrix> listRM = new ArrayList<RelationMatrix>();
        List<String> listNameB2A = new ArrayList<String>();
        listNameB2A.add("B1");
        listNameB2A.add("B2");
        listNameB2A.add("B3");
        double[][] matrixB2A = {{1, (double) 1 / 5, (double) 1 / 3}, {5, 1, 3}, {3, (double) 1 / 3, 1}};
        RelationMatrix B2A = new RelationMatrix("A", listNameB2A, matrixB2A);
        listRM.add(B2A);

        List<String> listNameC2B1 = new ArrayList<String>();
        listNameC2B1.add("C1");
        listNameC2B1.add("C2");
        listNameC2B1.add("C3");
        listNameC2B1.add("C4");
        listNameC2B1.add("C5");
        double[][] matrixC2B1 = {{1, 3, 5, 4, 7},
                {(double) 1 / 3, 1, 3, 2, 5},
                {(double) 1 / 5, (double) 1 / 3, 1, (double) 1 / 2, 2},
                {(double) 1 / 4, (double) 1 / 2, 2, 1, 3},
                {(double) 1 / 7, (double) 1 / 5, (double) 1 / 2, (double) 1 / 3, 1}};
        RelationMatrix C2B1 = new RelationMatrix("B1", listNameC2B1, matrixC2B1);
        listRM.add(C2B1);

        List<String> listNameC2B2 = new ArrayList<String>();
        listNameC2B2.add("C2");
        listNameC2B2.add("C3");
        listNameC2B2.add("C4");
        listNameC2B2.add("C5");
        double[][] matrixC2B2 = {{1, (double) 1 / 7, (double) 1 / 3, (double) 1 / 5},
                {7, 1, 5, 3},
                {3, (double) 1 / 5, 1, (double) 1 / 3},
                {5, (double) 1 / 3, 3, 1}};
        RelationMatrix C2B2 = new RelationMatrix("B2", listNameC2B2, matrixC2B2);
        listRM.add(C2B2);

        List<String> listNameC2B3 = new ArrayList<String>();
        listNameC2B3.add("C1");
        listNameC2B3.add("C2");
        listNameC2B3.add("C3");
        listNameC2B3.add("C4");

        double[][] matrixC2B3 = {{1, 1, 3, 3},
                {1, 1, 3, 3},
                {(double) 1 / 3, (double) 1 / 3, 1, 1},
                {(double) 1 / 3, (double) 1 / 3, 1, 1}};
        RelationMatrix C2B3 = new RelationMatrix("B3", listNameC2B3, matrixC2B3);
        listRM.add(C2B3);

        AHP myAHP = new AHP(3, listNode, listRM);

        List<WeightNodeAHP> res = myAHP.m_AHP();
        for (int i = 0; i < res.size(); i++) {
            System.out.println(res.get(i).getName() + "  " + res.get(i).getWeight());
        }*/

/*
        double[][] X = {{165, 166, 166, 165, 165},
                {167, 163, 169, 161, 169},
                {167, 164, 163, 163, 166},
                {166, 163, 164, 164, 167},
                {169, 170, 160, 166, 166},
                {160, 169, 160, 160, 165},
                {164, 163, 167, 170, 161},
                {166, 163, 168, 165, 161},
                {162, 161, 165, 161, 160},
                {161, 161, 160, 169, 169},
                {160, 167, 168, 167, 166},
                {164, 160, 167, 169, 161},
                {164, 164, 166, 165, 168},
                {160, 170, 165, 166, 169},
                {172, 172, 172, 172, 172}
        };
        double[] y = {1, 1, -1, 1, 1, -1, 1, 1, 1, -1, 1, 1, -1, 1, -1, -1};

        SVMCoefficient svm = new SVMCoefficient();
        double [] res = svm.m_getCoefficient(X,y);
        System.out.println();*/



        //测试分类器
        // DemoClassify myClassify = new DemoClassify();
        // myClassify.createClassifier(file, "d:\\");

        //myClassify.loadClassifier("NaiveBayes.model");
        // myClassify.evalClassifier(file, "d:\\");

        //ClassifierNeuralNetwork nn = new ClassifierNeuralNetwork();
       // nn.createClassifier(file, "d:\\");

        logger.debug("this is debug message!");
        logger.info("this is information!");
        logger.error("this is error message!");
    }
}

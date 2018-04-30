package BIT;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by supc on 2017/12/25 0025.
 */
public class DemoClassify {
    private Classifier cl;
    private boolean clExist;

    public DemoClassify() {
        this.setClExist(true);
    }

    //need a arff file
    public void createClassifier(File fileName, String modelPath) {
        Instances insTrain = null;
        try {
            ArffLoader loader = new ArffLoader();
            loader.setFile(fileName);

            insTrain = loader.getDataSet();
            insTrain.setClassIndex(insTrain.numAttributes() - 1);

            this.cl = new NaiveBayes();
            this.cl.buildClassifier(insTrain);

            SerializationHelper.write(modelPath + "NaiveBayes.model", cl);
            this.clExist = true;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //将评估的结果写到指定的文件中，返回评估的错误率
    //public double evalClassifier(File fileName, String resPath) {
    public Instances evalClassifier(File fileName, String resPath) {
        Evaluation eval = null;
        FileWriter fw = null;
        PrintWriter out = null;
        Instances curve = null;
        try {
            ArffLoader loader = new ArffLoader();
            loader.setFile(fileName);
            Instances insEval = loader.getDataSet();
            insEval.setClassIndex(insEval.numAttributes() - 1);


            eval = new Evaluation(insEval);
            Classifier cls = new NaiveBayes();
            eval.crossValidateModel(cls, insEval, 10, new Random(1));
            //System.out.println("the error rate: " + eval.errorRate());


            ThresholdCurve tc = new ThresholdCurve();
            int classIndex = 0;
            curve = tc.getCurve(eval.predictions(), classIndex);


            DecimalFormat df = new DecimalFormat("######0.0000");
            File fileRes = new File(resPath + "EvaluationResult.txt");
            fw = new FileWriter(fileRes);
            out = new PrintWriter(fw);

            out.println("The test error rate is:" + eval.errorRate());

            for (int j = 0; j < curve.numAttributes() - 1; j++) {
                out.print(curve.attribute(j).name());
                out.print(", ");
            }
            out.println(curve.attribute(curve.numAttributes() - 1).name());


            //double [] output = new double[curve.numAttributes()];
            for (int i = 0; i < curve.numInstances(); i++) {
                for (int j = 0; j < curve.numAttributes() - 1; j++) {
                    out.print(df.format(curve.instance(i).value(j)) + ", ");
                }
                out.println(df.format(curve.instance(i).value(curve.numAttributes() - 1)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return curve;
        }

    }

    //need a arff file
    public Instances insClassify(File fileName, String resPath) {
        try {
            if (!isClExist()) {
                throw new Exception("You must build classifier before classify data!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileWriter fw = null;
        PrintWriter out = null;
        Instances insTest = null;
        try {
            //load the data need to classify;
            ArffLoader loader = new ArffLoader();
            loader.setFile(fileName);
            insTest = loader.getDataSet();
            insTest.setClassIndex(insTest.numAttributes() - 1);


            fw = new FileWriter(resPath + "ClassifyResult.txt");
            out = new PrintWriter(fw);

            //System.out.println("# - actual -predicted  - error - distribution");
            //文件头
            for (int j = 0; j < insTest.numAttributes() - 1; j++) {
                out.print(insTest.attribute(j).name());
                out.print(", ");
            }
            out.println(insTest.attribute(insTest.numAttributes() - 1).name());

            //分类数据
            for (int i = 0; i < insTest.numInstances(); i++) {
                double pred = this.cl.classifyInstance(insTest.instance(i));
                double[] dist = cl.distributionForInstance(insTest.instance(i));

                for (int j = 0; j < insTest.numAttributes() - 1; j++) {
                    out.print(insTest.instance(i).value(j) + ", ");
                }
                insTest.instance(i).setValue(insTest.numAttributes() -1, insTest.classAttribute().value((int) pred));

                out.println(insTest.classAttribute().value((int) pred));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return insTest;
        }

    }

    public void loadClassifier(String filePathAndName) {
        try {
            this.cl = (Classifier) weka.core.SerializationHelper.read(filePathAndName);
            this.setClExist(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Classifier getCl() {
        return cl;
    }

    public void setCl(Classifier cl) {
        this.cl = cl;
    }

    public boolean isClExist() {
        return clExist;
    }

    public void setClExist(boolean clExist) {
        this.clExist = clExist;
    }
}

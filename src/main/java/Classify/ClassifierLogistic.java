package Classify;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.log4j.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by supc on 2018/1/16 0016.
 */
public class ClassifierLogistic {
    //Logistic
    private Classifier cl;
    private boolean clExist;

    private static final Logger log = Logger.getLogger(ClassifierLogistic.class);
    public ClassifierLogistic() {
        log.info("Logistic分类器对象被创建");
        this.setClExist(false);
    }

    public void createClassifier(File fileName, String modelPath) {
        log.info("创建Logistic分类器模型");
        Instances insTrain;
        try {
            ArffLoader loader = new ArffLoader();
            loader.setFile(fileName);
            insTrain = loader.getDataSet();
            insTrain.setClassIndex(insTrain.numAttributes() - 1);

            this.cl = new Logistic();
            this.cl.buildClassifier(insTrain);

            SerializationHelper.write(modelPath + "Logistic.model", this.cl);
            this.setClExist(true);

        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadClassifier(String filePathAndName) {
        log.info("加载Logistic分类器模型");
        try {
            this.cl = (Classifier) weka.core.SerializationHelper.read(filePathAndName);
            this.setClExist(true);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }


    public Instances evalClassifier(File fileName, String resPath) {
        log.info("评估Logistic分类器模型");
        Instances res = null;
        try {
            ArffLoader loader = new ArffLoader();
            loader.setFile(fileName);
            Instances insEval = loader.getDataSet();
            insEval.setClassIndex(insEval.numAttributes() - 1);

            Evaluation eval = new Evaluation(insEval);
            Classifier cls = new Logistic();
            eval.crossValidateModel(cls, insEval, 10, new Random(1));

            ThresholdCurve tc = new ThresholdCurve();
            int classIndex = 0;
            res = tc.getCurve(eval.predictions(), classIndex);

            //output result to file
            String fileRes = resPath + "LogisticEvaluation.txt";
            OutputToFile.outputEvaluaiton(fileRes, res, eval);
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public Instances insClassify(File fileName, String resPath) {
        log.info("使用Logistic分类器模型进行分类");
        try {
            if (!isClExist()) {
                log.error("Must be build classifier before classify data!");
                throw new Exception("Must be build classifier before classify data!");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        Instances insRes = null;

        try {
            //load the data need to classify;
            ArffLoader loader = new ArffLoader();
            loader.setFile(fileName);
            insRes = loader.getDataSet();
            insRes.setClassIndex(insRes.numAttributes() - 1);

            //begin classify data
            for (int i = 0; i < insRes.numInstances(); i++) {
                double pred = this.cl.classifyInstance(insRes.instance(i));
                double[] dist = cl.distributionForInstance(insRes.instance(i));
                insRes.instance(i).setValue(insRes.numAttributes() - 1, insRes.classAttribute().value((int) pred));
            }

            //print the result of classify to file;
            String outputFilePath = resPath + "LogisticClassifyResult.txt";
            OutputToFile.outputClassifyRes(outputFilePath, insRes);

        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        return insRes;
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

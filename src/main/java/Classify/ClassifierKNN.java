package Classify;

import org.apache.log4j.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by supc on 2018/1/16 0016.
 */
public class ClassifierKNN {

    private static final Logger log = Logger.getLogger(ClassifierKNN.class);
    private Classifier cl;
    private boolean clExist;

    public ClassifierKNN() {
        log.info("KNN分类器对象被创建");
        setClExist(false);
    }

    public void createClassifier(File fileName, String modelPath) {
        Instances insTrain = null;
        try {
            ArffLoader loader = new ArffLoader();
            loader.setFile(fileName);
            insTrain = loader.getDataSet();
            insTrain.setClassIndex(insTrain.numAttributes() - 1);

            this.cl = new IBk();
            this.cl.buildClassifier(insTrain);

            SerializationHelper.write(modelPath + "KNN.model", cl);
            setClExist(true);

        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }


        Classifier cls = new IBk();
    }

    public void loadClassifier(String filePathAndName) {
        try {
            this.cl = (Classifier)weka.core.SerializationHelper.read(filePathAndName);
            setClExist(true);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public Instances evalClassifier(File fileName, String resPath) {
        Instances res = null;
        try {
            ArffLoader loader = new ArffLoader();
            loader.setFile(fileName);
            Instances insEval = loader.getDataSet();
            insEval.setClassIndex(insEval.numAttributes()-1);

            Evaluation eval = new Evaluation(insEval);
            Classifier cls = new IBk();
            eval.crossValidateModel(cls, insEval, 10, new Random(1));

            ThresholdCurve tc= new ThresholdCurve();
            int classIndex = 0;
            res = tc.getCurve(eval.predictions(), classIndex);

            String fileRes = resPath + "KNNEvaluation.txt";
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
        try {
            if(!isClExist()) {
                log.error("Must be build classifier before classify data!");
                throw new Exception("Must be build classifier before classify data!");
            }
        }catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        Instances insRes = null;
        try {
            ArffLoader loader = new ArffLoader();
            loader.setFile(fileName);
            insRes = loader.getDataSet();
            insRes.setClassIndex(insRes.numAttributes()-1);

            for(int i = 0; i < insRes.numInstances(); i++) {
                double pred = this.cl.classifyInstance(insRes.instance(i));
                double[] dis = cl.distributionForInstance(insRes.instance(i));
                insRes.instance(i).setValue(insRes.numAttributes()-1, insRes.classAttribute().value((int)pred));
            }

            String outputFilePath = resPath+"KNNClassifyResult.txt";
            OutputToFile.outputClassifyRes(outputFilePath, insRes);
        }catch (IOException e) {
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

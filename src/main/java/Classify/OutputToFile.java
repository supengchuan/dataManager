package Classify;

import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 * Created by supc on 2018/1/17 0017.
 */
public class OutputToFile {
    public static void outputClassifyRes(String filePath, Instances ins) {
        FileWriter fw = null;
        PrintWriter out = null;
        try {
            fw = new FileWriter(filePath);
            out = new PrintWriter(fw);

            //header
            for (int j = 0; j < ins.numAttributes() - 1; j++) {
                out.print(ins.attribute(j).name());
                out.print(", ");
            }
            out.println(ins.attribute(ins.numAttributes() - 1).name());

            //print data
            for (int i = 0; i < ins.numInstances(); i++) {
                for (int j = 0; j < ins.numAttributes() - 1; j++) {
                    out.print(ins.instance(i).value(j) + ", ");
                }
                out.println(ins.classAttribute().value((int) ins.instance(i).value(ins.numAttributes() - 1)));

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void outputEvaluaiton(String filePath, Instances ins, Evaluation eval) {
        FileWriter fw = null;
        PrintWriter out = null;
        try {
            fw = new FileWriter(filePath);
            out = new PrintWriter(fw);

            DecimalFormat df = new DecimalFormat("######0.0000");
            out.println("The test error rate is: " + eval.errorRate());

            for (int j = 0; j < ins.numAttributes() - 1; j++) {
                out.print(ins.attribute(j).name());
                out.print(", ");
            }
            out.println(ins.attribute(ins.numAttributes() - 1).name());

            for (int i = 0; i < ins.numInstances(); i++) {
                for (int j = 0; j < ins.numAttributes() - 1; j++) {
                    out.print(df.format(ins.instance(i).value(j)) + ", ");
                }
                out.println(df.format(ins.instance(i).value(ins.numAttributes() - 1)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

}

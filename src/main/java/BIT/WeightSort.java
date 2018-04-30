package BIT;

import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by supc on 2017/12/20 0020.
 * 计算每个指标的权重，然后按照权重大小的从大到小返回指标名称
 */
public class WeightSort {

    private static Instances nomalizeData(Instances ins) {
        double maxData[] = new double[ins.numAttributes() - 1];
        for (int i = 0; i < maxData.length; i++) {
            maxData[i] = 0.0;
        }

        try {
            for (int i = 0; i < ins.numInstances(); i++) {
                for (int j = 0; j < ins.numAttributes() - 1; j++) {
                    if(maxData[j] < ins.instance(i).value(ins.attribute(j))) {
                        maxData[j] = ins.instance(i).value(ins.attribute(j));
                    }
                }
            }

            for (int i = 0 ; i < ins.numInstances(); i++) {
                for (int j = 0; j < ins.numAttributes() -1; j++) {
                    ins.instance(i).setValue(j, ins.instance(i).value(ins.attribute(j))/maxData[j] );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return  ins;
        }
    }


    private static double[] mathExpectation(Instances ins, String category) {

        double[] res = new double[ins.numAttributes() - 1];
        int count = 0;

        try {
            for (int i = 0; i < ins.numInstances(); i++) {
                for (int j = 0; j < ins.numAttributes() - 1; j++) {
                    if (category.equals(ins.instance(i).toString(ins.classIndex()))) {
                        res[j] += ins.instance(i).value(ins.attribute(j));
                        count++;
                    }
                }
            }

            for (int j = 0; j < ins.numAttributes() - 1; j++) {
                res[j] /= count;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return res;
        }
    }


    private static double[] mathVariance(Instances ins, String category, double[] mathException) {
        double[] res = new double[ins.numAttributes() - 1];
        int count = 0;
        try {
            for (int i = 0; i < ins.numInstances(); i++) {
                if (category.equals(ins.instance(i).toString(ins.classIndex()))) {
                    for (int j = 0; j < ins.numAttributes() - 1; j++) {
                        res[j] += Math.pow(ins.instance(i).value(ins.attribute(j)) - mathException[j], 2);
                        count++;
                    }
                }
            }

            for (int j = 0; j < ins.numAttributes() - 1; j++) {
                res[j] /= count;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return res;
        }
    }


    public static List<TargetInstance> sortWeight(File inputFile) {

        List<TargetInstance> targetInstanceList = new ArrayList<TargetInstance>();
        try {
            //加载arff文件，存入到instance对象中。
            ArffLoader loader = new ArffLoader();
            loader.setFile(inputFile);
            Instances ins = loader.getDataSet();
            ins.setClassIndex(ins.numAttributes() - 1);

            ins = nomalizeData(ins);

            double[] qualifiedExcpt = mathExpectation(ins, "合格");
            double[] disQualifiedExcpt = mathExpectation(ins, "不合格");

            double[] qualifiedVariance = mathVariance(ins, "合格", qualifiedExcpt);
            double[] disQualifiedVariance = mathVariance(ins, "不合格", disQualifiedExcpt);

            for (int j = 0; j < ins.numAttributes() - 1; j++) {
                TargetInstance ti = new TargetInstance();

                ti.setName(ins.attribute(j).name());
                ti.setQualifiedVariance(qualifiedVariance[j]);
                ti.setDisQualifiedVariance(disQualifiedVariance[j]);
                ti.setDistanceVariance(Math.abs(qualifiedVariance[j] - disQualifiedVariance[j]));

                targetInstanceList.add(ti);

            }

            Collections.sort(targetInstanceList);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return targetInstanceList;
    }
}

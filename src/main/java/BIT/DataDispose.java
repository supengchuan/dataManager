package BIT;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by supc on 2017/12/18 0018.
 */
public class DataDispose {
    public static void main(String [] args) {
        //System.out.println("Hello World");
        File file = new File("D:\\Project\\质量评估项目\\TrainData.xls");
        FileWriter fw = null;
        PrintWriter out = null;

        try {
            String fileName = file.getName().replace(".xls", "");
            fw = new FileWriter(file.getParent() + "/" + fileName + ".arff");
            out = new PrintWriter(fw);

            InputStream is = new FileInputStream(file.getAbsolutePath());

            Workbook wb = Workbook.getWorkbook(is);

            int sheet_size = wb.getNumberOfSheets();

            for(int index = 0; index < sheet_size; index++) {
                Sheet sheet = wb.getSheet(index);

                for (int i = 1; i < sheet.getRows(); i++) {

                    /*for (int j = 0; j < sheet.getColumns() -1; j++) {
                        String ceffInfo = sheet.getCell(j, i).getContents();
                        out.print(ceffInfo+",");
                    }
                    String lastColumn = sheet.getCell(sheet.getColumns() -1, i).getContents();
                    out.print(lastColumn + "\n");*/


                    String [] input = new String[sheet.getColumns()];
                    for(int j = 0; j < sheet.getColumns(); j++) {
                        input[j] = sheet.getCell(j ,i).getContents();
                    }
                    Random random = new Random();
                    DecimalFormat df = new DecimalFormat("######0.00");
                    if(input[6].equals("不合格")) {
                        double mZ03 =60+((random.nextInt(100)%2)==0?1:-1 )*(random.nextDouble()*3);
                        input[3] = df.format(mZ03);

                        double bridge = 1.05 + ((random.nextInt(100)%2)==0?1:-1 )*(random.nextDouble()/10);
                        input[4] =  df.format(bridge);

                        double electric = 500 + (random.nextInt(100)%2 == 0 ? 1: -1)*(random.nextDouble()*20);
                        input[5] = df.format(electric);
                    } else if (input[6].equals("合格")){
                        double mZ03 =60+((random.nextInt(100)%2)==0?1:-1 )*(random.nextDouble()+2);
                        input[3] =  df.format(mZ03);

                        double bridge = 1.05 + ((random.nextInt(100)%2)==0?1:-1 )*(random.nextDouble()*3/20);
                        input[4] = df.format(bridge);

                        double electric = 500 + (random.nextInt(100)%2 == 0 ? 1: -1)*(random.nextDouble()*25);
                        input[5] = df.format(electric);
                    }

                    for(int j = 0; j < 6; j++){
                        out.print(input[j]+",");
                    }
                    out.println(input[6]);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                fw.close();
                out.flush();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

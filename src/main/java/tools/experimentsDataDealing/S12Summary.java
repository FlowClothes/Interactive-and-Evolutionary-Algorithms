package tools.experimentsDataDealing;

import tools.experimentsDataDealing.steps.D1FillUnbalancedata;
import tools.experimentsDataDealing.steps.D2AverageFormular2Cell;
import tools.experimentsDataDealing.steps.D4SummaryData;
import tools.experimentsDataDealing.steps.D5AlignData4PS;

/**
 * @author 郝国生 HAO Guo-Sheng
 */
public class S12Summary {

    String commFileName = "\\PS_";
   

    private void dataDeal(String filePath, String extensionFileName) {
        System.out.println("Step 1 begin");
        ExperimentSetup es=new ExperimentSetup();
        D1FillUnbalancedata fu = new D1FillUnbalancedata();
        for (int i = 0; i < es.getPs().length; i++) {
            fu.completeData(filePath + commFileName +  es.getPs()[i] + extensionFileName,  es.getPs()[i]);
        }
//        System.gc();
        System.out.println("Step 2 begin");
        D2AverageFormular2Cell afc = new D2AverageFormular2Cell();
        String filename = filePath;
        for (int i = 0; i <  es.getPs().length; i++) {
            afc.putFormular2Cell(filename + commFileName + es.getPs()[i] + extensionFileName,  es.getPs()[i]);
        }
        //在D2中直接求值，而不是插入公式的办法，所以D3求公式值及拷贝的过程就不用了
// //       System.gc();
//        System.out.println("Step 3 begin");
//        D3EvaluateFormula ef = new D3EvaluateFormula();
//        ef.evaluateFormula(filePath, commFileName, extensionFileName);
////        System.gc();
        System.out.println("Step 4 begin");
        D4SummaryData sd = new D4SummaryData();
        sd.summaryData(filePath, commFileName, extensionFileName);
        //       System.gc();
        System.out.println("Step 5 begin");
        D5AlignData4PS adps = new D5AlignData4PS();
        adps.alignData4PS(filePath);
    }

    public static void main(String[] args) {
        ExperimentSetup es=new ExperimentSetup();
        es.init();
        String filePathPart1 = "E:\\backupFile\\Fangcloud of JSNUV2\\个人文件\\study\\papers\\experimentsData\\EAs\\ExperimentsDataNew\\";

        S12Summary sdd = new S12Summary();
        
        for (int i = 34; i < 35; i++) {
            //for (int i = 11; i <=11; i++) {
            String filePathPart2 = "F" + i + "\\D30\\";
            System.out.println("F" + i + "==============================================");
            for (int j = 2; j < es.getKnowledgeName().length; j++) {
                System.out.println("F" + i + "--------" + es.getKnowledgeName()[j]);
                sdd.dataDeal(filePathPart1 + filePathPart2 + es.getKnowledgeName()[j], "_" + es.functionDimension.get(i) + "D.xlsx");
            }
        }
    }
   
}

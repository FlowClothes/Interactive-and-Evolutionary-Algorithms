package tools.experimentsDataDealing;

import java.util.HashMap;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class ExperimentSetup {

    private final String[] knowledgeName = new String[]{"SimpleGA", "NoRepeatOnSmaePopulationKnowledge", "NoReappearOnThisAndLastPopulationKnowledge", "NoReappearOnSearchSpaceHashMapAndPopulation1"};
    private final Integer[] ps = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200, 210, 220, 230, 240, 250, 260, 270, 280, 290, 300, 310, 320, 330, 340,
        350};

    public String[] getKnowledgeName() {
        return knowledgeName;
    }

    public Integer[] getPs() {
        return ps;
    }
     public HashMap<Integer, Integer> functionDimension = new HashMap<>();

    public void init() {
        //2020-08-02第一组实验数据处理
        functionDimension.put(2, 2);
        functionDimension.put(3, 5);
        functionDimension.put(4, 30);
        functionDimension.put(5, 2);
        functionDimension.put(6, 2);
        functionDimension.put(7, 2);
        functionDimension.put(8, 2);
        functionDimension.put(9, 2);
        functionDimension.put(10, 2);
        functionDimension.put(11, 30);
        functionDimension.put(12, 30);
        functionDimension.put(13, 30);
        functionDimension.put(14, 30);
        functionDimension.put(15, 30);
        functionDimension.put(16, 30);
        functionDimension.put(17, 30);
        functionDimension.put(18, 30);
        functionDimension.put(19, 30);
        functionDimension.put(20, 30);
        functionDimension.put(21, 30);
        functionDimension.put(22, 30);
        functionDimension.put(23, 30);
        functionDimension.put(24, 30);
        functionDimension.put(25, 30);
        functionDimension.put(26, 30);
        functionDimension.put(27, 30);
        functionDimension.put(28, 30);
        functionDimension.put(29, 30);
        functionDimension.put(30, 30);
        functionDimension.put(31, 30);
        functionDimension.put(32, 30);
        functionDimension.put(33, 30);
        functionDimension.put(34, 30);

    }
}

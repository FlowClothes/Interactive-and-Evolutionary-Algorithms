package core.algorithm.operator.Selection;

import core.algorithm.operator.Selection.triangle.SinSelection;
import core.algorithm.operator.Selection.triangle.TanSelection;

/**
 * @author 郝国生 HAO Guo-Sheng
 */
public class FactorySelection {

    public static int selectionType;//选择算子类型
    public static String selectionName;

    public static String getName(int type) {
        return selectionName;
    }

    public static Selection getSelection(int inputSelectionType) {
        selectionType = inputSelectionType;
        Selection result = null;
        switch (selectionType) {
            case 0:
                selectionName = "Sine Function Based Selection Operator";
                result = new SinSelection();
                break;
            case 1:
                selectionName = "Tangent Function Based Selection Operator";
                result = new TanSelection();
                break;

            case 2:
                selectionName = "Rank Seletion Operator";
                result = new RankSelection();
                break;
            case 3:
                selectionName = "Stochastic Tournament Seletion Operator";
                result = new StochasticTournamentModel();
                break;
            case 4:
                selectionName = "Roulette Wheel Selection Operator";
                result = new RouletteWheelSelection();
                break;
            case 5:
                selectionName = "Immunity Selection Operator";
                result = new ImmunitySelection();
                break;
        }
        return result;
    }

}

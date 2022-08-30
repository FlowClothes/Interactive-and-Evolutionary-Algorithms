package core.tools.DesignPatters;

import javax.swing.JComboBox;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class ColleagueJComboBox extends JComboBox implements Colleague {

    static int i = 0;
    private Mediator mediator;
    private int Colleaguenum;

    public ColleagueJComboBox(String[] items, int ColleagueNum) {
        super(items);
        this.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
//                if (evt.getStateChange() == ItemEvent.SELECTED) {
//                    selectedIndex = getSelectedIndex();
//                }
            }
        });
        this.Colleaguenum = ColleagueNum;
    }

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public void StatusAdjusted(int status) {
    }

    public void addEventListener() {
    }
}

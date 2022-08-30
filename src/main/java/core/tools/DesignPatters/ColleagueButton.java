package core.tools.DesignPatters;

/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

public class ColleagueButton extends JButton implements Colleague, ActionListener {
    private Mediator mediator;
    private int ColleagueNum;

    public ColleagueButton() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public ColleagueButton(String caption, int ColleagueIndex) {
        super(caption);
        addActionListener(this);
        this.ColleagueNum = ColleagueIndex;
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }
    //How can my status should be changed,if something else needed,handle it in my Mediator

    @Override
    public void StatusAdjusted(int status) {
    }

    @Override
    public void addEventListener() {
        

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.mediator.colleagueInformationHandled(ColleagueNum);
        } catch (IOException ex) {
            Logger.getLogger(ColleagueButton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void jbInit() throws Exception {
    }
}

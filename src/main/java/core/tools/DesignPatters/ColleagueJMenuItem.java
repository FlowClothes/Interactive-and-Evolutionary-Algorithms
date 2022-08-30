package core.tools.DesignPatters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import core.problem.IGA.Graph.Compose.Compose;
import core.problem.Problem;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class ColleagueJMenuItem extends JMenuItem implements Colleague, ActionListener {

    private Mediator mediator;
    private int ColleagueNum;

    public ColleagueJMenuItem(String text, int mnemonic, int ColleagueNum) {
        super(text);
        this.ColleagueNum = ColleagueNum;
        init(mnemonic);
    }
    
    private void init(int mnemonic) {
        this.setMnemonic(mnemonic);
        addEventListener();
    }

    public ColleagueJMenuItem(String text) {
        super(text);
        addActionListener((ActionEvent e) -> {
            new Compose();
        });
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
        this.addActionListener(this);

    }
    Problem problem;

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.mediator.colleagueInformationHandled(ColleagueNum);
        } catch (IOException ex) {
            Logger.getLogger(ColleagueJMenuItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

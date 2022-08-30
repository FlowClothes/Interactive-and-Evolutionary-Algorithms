package core.algorithm.IEC.Render.FitnessAssignMethods;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class TextSlider extends JPanel {

    JTextField text = new JTextField(10);
    JSlider sl = new JSlider(JSlider.HORIZONTAL, 0, 1000, 500);

    public TextSlider() {
        text.addKeyListener(
                new KeyAdapter() {
            @Override
                    public void keyReleased(KeyEvent e) {
                        sl.setValue(Integer.parseInt(text.getText()));
                    }
                });
        sl.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                text.setText(Integer.toString(sl.getValue()));
            }
        });
        text.setText("500");
        this.setLayout(new BorderLayout());
        this.add(text, BorderLayout.CENTER);
        this.add(sl, BorderLayout.SOUTH);
    }

    public int getValue() {
        return sl.getValue();
    }
    public void setValue(int value){
        sl.setValue(value);
    }
    public void setDefaultValue(){
        this.setValue(500);
    }
}

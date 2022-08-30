package core.problem.IGA.Graph.Face;

import core.controllers.ControlParameters;
import core.GUI.LabelName;
import core.algorithm.Algorithm;
import core.algorithm.IEC.Panel.IECPanel;
import core.problem.IGA.Graph.Face.Base.FaceControlParameters;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class PreFaceOptimization extends JPanel {

    private JLabel face, delt, instruction = new JLabel(LabelName.delterValueInPreFaceOptimization);
    private Icon icon, iconNormalization;
    private String[] facePart;
    private JComboBox facePartSelection = new JComboBox();
    private JPanel checkPane = new JPanel(), leftPane = new JPanel(new BorderLayout()), rightPane = new JPanel(), me;
    private JCheckBox userRecognitionBased;//如果使用用户认知，则为4
    private String imgname = ControlParameters.resource + "img";
    private JButton begin = new JButton();
    private JScrollPane js = new JScrollPane();
    private JTextField deltValue = new JTextField();
    private ComboBoxWithIcon cbi = new ComboBoxWithIcon();
    private Algorithm algorithm;

    public PreFaceOptimization(Algorithm algorithm) {
        super();
        me = this;
        this.algorithm = algorithm;
        init();
    }

    private void init() {
        facePart = LabelName.partOfFace;
        userRecognitionBased = new JCheckBox(LabelName.userPreferenceInPreFaceOptimization);
        begin.setText(LabelName.beginButtonInPreFaceOptimization);
        cbi.setIconStrings(FaceControlParameters.faceName);
        icon = new ImageIcon(imgname.trim() + "/" + FaceControlParameters.whichFace + "Large.gif");
        iconNormalization = new ImageIcon(ControlParameters.resource + "img/normalization.gif");
        face = new JLabel(icon);
        delt = new JLabel(iconNormalization);
        delt.setVisible(false);
        deltValue.setText("0.5");
        deltValue.setVisible(false);
        JPanel deltP = new JPanel();
        deltP.setLayout(new BorderLayout());
        deltP.add(deltValue, BorderLayout.NORTH);
        deltP.add(delt, BorderLayout.CENTER);
        instruction.setVisible(false);
        deltP.add(instruction, BorderLayout.SOUTH);
        leftPane.add(face, BorderLayout.CENTER);
        for (int i = 0; i < facePart.length; i++) {
            facePartSelection.addItem(facePart[i]);
        }
        facePartSelection.setSelectedIndex(facePart.length - 1);
        JPanel faceP = new JPanel();
        faceP.setLayout(new BorderLayout());
        faceP.add(facePartSelection, BorderLayout.SOUTH);
        faceP.add(cbi, BorderLayout.NORTH);
        checkPane.setLayout(new BorderLayout());
        JPanel temP = new JPanel();
        temP.setLayout(new GridLayout(2, 1));
        temP.add(userRecognitionBased);
        checkPane.add(temP, BorderLayout.NORTH);
        checkPane.add(deltP, BorderLayout.CENTER);
        rightPane.setLayout(new BorderLayout());
        rightPane.add(faceP, BorderLayout.NORTH);
        rightPane.add(checkPane, BorderLayout.CENTER);
        begin.setBackground(Color.red);
        rightPane.add(begin, BorderLayout.SOUTH);
        setLayout(new GridLayout(1, 2));
        add(leftPane);
        add(rightPane);
        setActionListener();
    }

    private void setActionListener() {
        facePartSelection.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                icon = new ImageIcon(imgname.trim() + "/" + FaceControlParameters.whichFace + facePartSelection.getSelectedIndex() + ".gif");
                face.setIcon(icon);
                validate();
            }
        });
        begin.addActionListener(new ActionListener() {//按下了开始进化按钮

            @Override
            public void actionPerformed(ActionEvent e) {
                if (userRecognitionBased.isSelected()) {
                    ControlParameters.deltValue = Float.parseFloat(deltValue.getText());
                } 
                removeAll();
                setLayout(new GridLayout(1, 1));
                add(js);
                FaceControlParameters.part = facePartSelection.getSelectedIndex();//指定进化的部分，直接对FaceIndividual产生影响
                //生成人脸的个体
                IECPanel iGAPanel = new IECPanel(me);
                iGAPanel.createColleagues();
                js.getViewport().add(iGAPanel);
                validate();
            }
        });
        userRecognitionBased.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (userRecognitionBased.isSelected()) {
                    delt.setVisible(true);
                    deltValue.setVisible(true);
                    instruction.setVisible(true);
                } else {
                    delt.setVisible(false);
                    deltValue.setVisible(false);
                    instruction.setVisible(false);
                }
                validate();
            }
        });
    }

    class ComboBoxWithIcon extends JPanel {

        private ImageIcon[] images;
        private String[] iconStrings;
        private JComboBox petList;
        private ComboBoxRenderer renderer;

        public ComboBoxWithIcon() {
        }

        public void init() {
            //Load   the   pet   images   and   create   an   array   of   indexes.
            images = new ImageIcon[iconStrings.length];
            Integer[] intArray = new Integer[iconStrings.length];
            for (int i = 0; i < iconStrings.length; i++) {
                intArray[i] = new Integer(i);
                images[i] = createImageIcon(ControlParameters.resource + "img/" + iconStrings[i] + ".gif");
                if (images[i] != null) {
                    images[i].setDescription(iconStrings[i]);
                }
            }
            //Create   the   combo   box.
            petList = new JComboBox(intArray);
            petList.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    FaceControlParameters.whichFace = iconStrings[petList.getSelectedIndex()];
                    setIcon();
                }
            });
            renderer = new ComboBoxRenderer();
            renderer.setPreferredSize(new Dimension(200, 130));
            petList.setRenderer(renderer);
            petList.setMaximumRowCount(iconStrings.length);

            //Lay   out   the   demo.
            setLayout(new BorderLayout());
            add(petList, BorderLayout.PAGE_START);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        }

        /**
         * Returns an ImageIcon, or null if the path was invalid.
         */
        protected ImageIcon createImageIcon(String path) {
            if (path != null) {
                return new ImageIcon(path);
            } else {
                System.err.println("Couldn't   find   file:   " + path);
                return null;
            }
        }

        /**
         * @return the images
         */
        public ImageIcon[] getImages() {
            return images;
        }

        public String[] getIconStrings() {
            return iconStrings;
        }

        public void setIconStrings(String[] iconStrings) {
            if (null == this.iconStrings || this.iconStrings.length == 0) {
                this.iconStrings = new String[iconStrings.length];
            }
            System.arraycopy(iconStrings, 0, this.iconStrings, 0, iconStrings.length);
            this.init();
        }

        class ComboBoxRenderer extends JLabel implements ListCellRenderer {

            private Font uhOhFont;
            private int selectedIndex = 0;

            public ComboBoxRenderer() {
                setOpaque(true);
                setHorizontalAlignment(CENTER);
                setVerticalAlignment(CENTER);
            }

            /*
             *   This   method   finds   the   image   and   text   corresponding
             *   to   the   selected   value   and   returns   the   label,   set   up
             *   to   display   the   text   and   image.
             */
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                //Get   the   selected   index.   (The   index   param   isn't always   valid,   so   just   use   the   value.)
                selectedIndex = ((Integer) value).intValue();
                if (isSelected) {
                    setBackground(list.getSelectionBackground());
                    setForeground(list.getSelectionForeground());
                } else {
                    setBackground(list.getBackground());
                    setForeground(list.getForeground());
                }

                //Set   the   icon   and   text.     If   icon   was   null,   say   so.
                ImageIcon icon = getImages()[selectedIndex];
                String iconName = getIconStrings()[selectedIndex];
                setIcon(icon);
                if (icon != null) {
                    setText(iconName);
                    setFont(list.getFont());
                } else {
                    setUhOhText(iconName + "   (no   image   available)",
                            list.getFont());
                }

                return this;
            }

            //Set   the   font   and   text   when   no   image   was   found.
            protected void setUhOhText(String uhOhText, Font normalFont) {
                if (uhOhFont == null) {   //lazily   create   this   font
                    uhOhFont = normalFont.deriveFont(Font.ITALIC);
                }
                setFont(uhOhFont);
                setText(uhOhText);
            }
        }

        private void setIcon() {
            icon = new ImageIcon(imgname.trim() + "/" + FaceControlParameters.whichFace + "Large.gif");
            face.setIcon(icon);
            validate();
        }
    }
}

package core.GUI;

import core.problem.FactoryProblems;
import core.tools.DesignPatters.ColleagueJMenuItem;
import core.tools.DesignPatters.Mediator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class MyMenuBar extends JMenuBar {

    private List<ColleagueJMenuItem> menuItemsList = new LinkedList<>();
    //ColleagueJMenuItem f0 = ;

    public MyMenuBar(Mediator med) {
        //第一步完成优化问题注册
        FactoryProblems.registerProblems();
        for (Entry<Integer, String> entry : FactoryProblems.problemsIndex.entrySet()) {
            menuItemsList.add(new ColleagueJMenuItem(entry.getValue(), (char) (entry.getKey().intValue()), entry.getKey()));
        }
        menuItemsList.forEach((cj) -> {
            cj.setMediator(med);
        });
        //第二步完成优化问题归类及其他菜单处理
        initMenues(med);
    }

    private void initMenues(Mediator med) {
        //TGA
        JMenu tga = new JMenu(LabelName.tga);
        tga.setMnemonic('T');
        //IGA
        JMenu iga = new JMenu(LabelName.iga);
        //IGA--Music
        JMenu igaAidMusic = new JMenu(LabelName.igaAidMusic);
        iga.setMnemonic('I');
        igaAidMusic.setMnemonic('C');

        for (ColleagueJMenuItem jitem : menuItemsList) {
            if (jitem.getMnemonic() <= 960 && jitem.getMnemonic() > 950) {//音乐优化
                igaAidMusic.add(jitem);
            } else if (jitem.getMnemonic() > 900) {
                iga.add(jitem);
            } else {
                tga.add(jitem);
            }
        }
        
        JMenu file = new JMenu(LabelName.file);
        file.setMnemonic('F');
        file.add(tga);
        iga.add(igaAidMusic);
        file.add(iga);
        file.addSeparator();

        JMenu language = new JMenu(LabelName.languageMenu);
        language.setMnemonic('L');
        ColleagueJMenuItem english = new ColleagueJMenuItem(LabelName.English, 'N', LabelName.EnglishIndex);
        english.setMediator(med);
        language.add(english);
        ColleagueJMenuItem chinese = new ColleagueJMenuItem(LabelName.Chinese, 'C', LabelName.ChineseIndex);
        chinese.setMediator(med);
        language.add(chinese);

        ColleagueJMenuItem exit = new ColleagueJMenuItem(LabelName.exit, 'x', LabelName.exitIndex);//-1
        exit.setMediator(med);
        file.add(exit);//exit
//
        this.add(file);
        this.add(language);
        
        JMenu run = new JMenu(LabelName.run);
        run.setMnemonic('R');
        JMenu help = new JMenu(LabelName.help);
        help.setMnemonic('H');
        this.add(run);
        this.add(help);
    }
}

package core.tools.DesignPatters;

import java.io.IOException;
import core.problem.Problem;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public interface Mediator {

    public void createColleagues();

    public void colleagueInformationHandled(int ColleaguIndex) throws IOException;

}

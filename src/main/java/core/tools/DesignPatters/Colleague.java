package core.tools.DesignPatters ;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public interface Colleague {
  public void setMediator(Mediator mediator);
  public  void StatusAdjusted(int status);
  public void addEventListener();
}

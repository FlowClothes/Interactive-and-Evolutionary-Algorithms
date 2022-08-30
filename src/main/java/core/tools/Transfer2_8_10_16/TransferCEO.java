package core.tools.Transfer2_8_10_16;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class TransferCEO extends BaseTransfer{
    public TransferCEO(String genecode,double xmax,double xmin,double maxCodeValue,String codescope){  
        super();
        FactoryTransfer ft=new FactoryTransfer();
        this.setX(ft.geneToPheno(
                genecode, 
                xmax, 
                xmin,
                maxCodeValue,codescope));
    }
}

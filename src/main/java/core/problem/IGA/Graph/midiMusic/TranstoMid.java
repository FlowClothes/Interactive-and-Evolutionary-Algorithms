package core.problem.IGA.Graph.midiMusic;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class TranstoMid {

    public TranstoMid(){
    }

    public int trans(int orign)
    {
       // int mid;
        if(orign>=0&&orign<=48)  orign=48;
        if(orign==49||orign==50) orign=50;
        if(orign==51||orign==51) orign=52;
        if(orign==53)            orign=53;
        if(orign==54||orign==55) orign=55;
        if(orign==56||orign==57) orign=57;
        if(orign==58||orign==59) orign=59;
        if(orign==60)            orign=60;
        if(orign==61||orign==62) orign=62;
        if(orign==63||orign==64) orign=64;
        if(orign==65)            orign=65;
        if(orign==66||orign==67) orign=67;
        if(orign==68||orign==69) orign=69;
        if(orign==70||orign==71) orign=71;
        if(orign==72)            orign=72;
        if(orign==73||orign==74) orign=74;
        if(orign==75||orign==76) orign=76;
        if(orign==77)            orign=77;
        if(orign==78||orign==79) orign=79;
        if(orign==80||orign==81) orign=81;
        if(orign>=82&&orign<=127)  orign=83;
        return orign;
    }
     public int time(int MusicTime)
     {
         if(MusicTime==0) MusicTime=100;
         if(MusicTime==1) MusicTime=200;
         if(MusicTime==2) MusicTime=400;
         if(MusicTime==3) MusicTime=600;
         if(MusicTime==4) MusicTime=800;
         if(MusicTime==5) MusicTime=1200;
         if(MusicTime==6) MusicTime=1200;
         if(MusicTime==7) MusicTime=1200;
         return MusicTime;
     }

}

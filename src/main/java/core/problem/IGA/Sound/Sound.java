package core.problem.IGA.Sound;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.*;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class Sound {

    Synthesizer synthesizer;
    Instrument[] instruments;
    MidiChannel[] channels;
    public static int NOTESIZE=100;
    int channelNum = 3;
    int instr = 9;//该参数表示音乐的音品0表示钢琴声
    int[][] noteCode = new int[NOTESIZE][3];//noteCode[i][0]中存储的是音节，noteCode[i][1]存储的是控制声音大小的参数，noteCode[i][2]中存储的是该音节持续的时间

    public void setNoteCode(int[][] code) {
        for (int i = 0; i < noteCode.length; i++) {
            for (int j = 0; j < noteCode[0].length; j++) {
                noteCode[i][j] = code[i][j];
            }
        }
    }

    public void playChannel(MidiChannel channel) {
        for (int i = 0; i < noteCode.length; i++) {
            channel.noteOn(noteCode[i][0], noteCode[i][1]);
            try {
                Thread.sleep(noteCode[i][2]);
            } catch (InterruptedException e) {
            }
        }
        for (int i = 0; i < noteCode.length; i++) {
            channel.noteOff(noteCode[i][0]);
        }
    }

    public Sound() {
    }

    public void open() {
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
        } catch (MidiUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }

        instruments = synthesizer.getDefaultSoundbank().getInstruments();
        channels = synthesizer.getChannels();
        String name = instruments[instr].getName();
        if (name.endsWith("\n")) {
            name = name.trim();
        }
        //System.out.println("Soundbank instrument " + instr + ": " + name);
        synthesizer.loadInstrument(instruments[instr]);
        channels[channelNum].programChange(instr);
        //channels[1]
        playChannel(channels[channelNum]);

    }

//
//    public static void main(String[] args) throws MidiUnavailableException {
//        Sound kone = new Sound();
//        int[][] tem=new int[NOTESIZE][3];
//        int a=1;
//        for (int i = 0; i < tem.length; i++) {
//                tem[i][0] = a + i;
//                tem[i][1]=60;
//                tem[i][2]=100;
//        }
//        kone.setNoteCode(tem);
//        kone.open();
//    }
}



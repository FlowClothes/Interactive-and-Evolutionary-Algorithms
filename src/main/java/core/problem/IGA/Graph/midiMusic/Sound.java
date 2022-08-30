package core.problem.IGA.Graph.midiMusic;

import core.controllers.ControlParameters;
import javax.sound.midi.Instrument;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class Sound {
    Synthesizer synthesizer;
    Instrument[] instruments;
    MidiChannel[] channels;
  //  public static int NOTESIZE = 300;
    int channelNum = 3;
    int instr = 0;//0表示钢琴
    int[][] noteCode ;//
   public int n;
    public void  setNoteCode(int[][] code,int num) {
        for (int i = 0; i < num; i++) {
            System.arraycopy(code[i], 0, noteCode[i], 0, 3);
        }
       n=num;
    }
   public void playChannel(MidiChannel channel ) {
        for (int i = 0; i < n; i++) {
            channel.noteOn(noteCode[i][0],noteCode[i][1]);
            try {
                Thread.sleep(noteCode[i][2]);
            } catch (InterruptedException e) {
            }
        }
       for (int i = 0; i < n; i++) {
             channel.noteOff(noteCode[i][0],92);
             System.out.println(noteCode[i][0]);
             System.out.println(noteCode[i][2]);
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
        System.out.println("Soundbank instrument " + instr + ": " + name);
        synthesizer.loadInstrument(instruments[instr]);
        channels[channelNum].programChange(instr);
        //channels[1]
        playChannel(channels[channelNum]);
        System.out.println(n);
    }

    public void setNoteCode(int[][] tem, int i, int track) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

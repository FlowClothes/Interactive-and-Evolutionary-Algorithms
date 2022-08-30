package core.problem.IGA.Graph.Compose;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class MakeMidi {

    Sequencer sequencer;
    Sequence sequence;
    Track track;
    int tracklength;
    public static int NOTESIZE = 100;
    int[][] noteCode = new int[NOTESIZE][32];

    public MakeMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
        } catch (MidiUnavailableException | InvalidMidiDataException ex) {
            Logger.getLogger(MakeMidi.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setnoteCode(int[][] tem, int length) {
        for (int i = 0; i < NOTESIZE; i++) {
            System.arraycopy(tem[i], 0, noteCode[i], 0, length+2);
//            for (int j = 0; j <= length + 1; j++) {
//                noteCode[i][j] = tem[i][j];
//            }
        }
        tracklength = length;
    }

    public void buildTrackAndStart() {
        int[] trackList = null;
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i <= tracklength; i = i + 2) {
            System.out.println("BIANMA");
            // trackList = new int[NOTESIZE];
            int length = 1;
            for (int j = 1; j < NOTESIZE; j++) {
                if (noteCode[j][i] != 0) {
                    length++;
                } else {
                    break;
                }
            }
            int[] trackTime = new int[length];
            trackList = new int[length];
            for (int j = 0; j < length; j++) {
                trackList[j] = noteCode[j][i];
                trackTime[j] = noteCode[j][i + 1];
                System.out.println(trackList[j]);
                System.out.println(trackTime[j]);
            }

            makeTracks(trackList, trackTime, length);
            track.add(makeEvent(176, trackList[0], 127, 0, length));//176表示CONTROL_CHANGE
        }// close outer loop

        track.add(makeEvent(192, 0, 1, 0, 15));//192表示programChange

        try {
            sequencer.setSequence(sequence);
            // sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            // sequencer.setTempoInBPM(60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeTracks(int[] list, int[] listTmie, int length) {
        int channel = list[0] - 1;
        for (int i = 1; i < length; i++) {
            int key = list[i];
            if (key != 0) {
                track.add(makeEvent(144, channel, key, 92, i));//144表示noteon
                if (listTmie[i] == 200) {
                    sequencer.setTempoInBPM(60);
                } else if (listTmie[i] == 400) {
                    sequencer.setTempoInBPM(30);
                } else {
                    sequencer.setTempoInBPM(15);
                }
//    sequencer.setTempoInBPM(20);
                track.add(makeEvent(128, channel, key, 92, i + 1));//128表示noteoff
            }
        }
    }

    private MidiEvent makeEvent(int command, int chanel, int one, int two, int ticks) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(command, chanel, one, two);
            event = new MidiEvent(a, ticks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

}

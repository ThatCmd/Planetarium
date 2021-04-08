/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetarium.contents.system.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author TTT
 */
public class AudioPlayer {

    public void playAudioJoined(String p) {
        new Thread() {
            @Override
            public void run() {
                try {
                    InputStream s = getClass().getResourceAsStream(p);
                    AudioInputStream stream = AudioSystem.getAudioInputStream(s);
                    Clip clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, stream.getFormat()));
                    clip.open(stream);
                    clip.start();
                    Thread.sleep(clip.getMicrosecondLength() / 1000);
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException | InterruptedException ex) {
                    Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();

    }

}

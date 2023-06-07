package Modele;


import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainMeteoTest {
    public static void main(String[] args) {
        try {
            Clip audio = AudioSystem.getClip();
            audio.open(AudioSystem.getAudioInputStream(new FileInputStream("./src/son/Cybersdf-Dolling.wav")));
            audio.start();
            audio.loop(Clip.LOOP_CONTINUOUSLY);
            //audio.stop();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

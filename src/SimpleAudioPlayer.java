import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SimpleAudioPlayer implements AudioPlayer {
    Clip clip;

    boolean status;

    AudioInputStream audioInputStream;

    private String fileName;

    public SimpleAudioPlayer() {
        this.status = true;

    }

    public SimpleAudioPlayer(String name) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        fileName = name;
        this.status = true;

        audioInputStream = AudioSystem.getAudioInputStream(new File("src\\MagicCards\\Audio\\"+fileName+".wav").getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
    }

    @Override
    public void setFileName(String fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        audioInputStream = AudioSystem.getAudioInputStream(new File("src\\Audio\\"+fileName+".wav").getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);

        this.fileName = fileName;
    }

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean getStatus() {
        return status;
    }
    @Override
    public void play() {
        if (status){
            clip.start();
        }
    }
}


package placyk;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio extends Thread
{
    private Clip theme;
   
    public Audio()
    {
    	try
        {
        	File themeFile = new File("theme.wav");
            if (themeFile.exists())
            {
            	AudioInputStream themeStream = AudioSystem.getAudioInputStream(themeFile);
            
                theme = AudioSystem.getClip();
                theme.open(themeStream);
            }
        } 
        catch (UnsupportedAudioFileException e){}
        catch (IOException e){}
        catch (LineUnavailableException e){}
    }
    
    public void run(){ theme.loop(Clip.LOOP_CONTINUOUSLY); }
}
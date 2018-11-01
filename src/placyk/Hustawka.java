package placyk;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Hustawka extends Thread
{
	private PFrame frame;
	private Semaphore semBench, semBench2, semBench3;
	private Random rand;
	private boolean place[];
	private int randomMoves, pictureFrame;
	
	public Hustawka(PFrame frame)
	{
		semBench = new Semaphore(3);
		semBench2 = new Semaphore(0);
		semBench3 = new Semaphore(0);
		place = new boolean[3];
		place[0] = true;
		place[1] = true;
		place[2] = true;
		rand = new Random();
		randomMoves = rand.nextInt(5) + 3;
		
		this.frame = frame;
		pictureFrame = 0;
	}
	
	public void sway(PRunnable runnable) throws InterruptedException
	{
		semBench.acquire();
		
		if(place[0] == true)
		{
			place[0] = false;
			while( runnable.go(335, 186) == false );
			runnable.changeSpeed(65);
			semBench3.release();
			semBench2.acquire();
			for(int i = 0; i < randomMoves; i++)
			{
				while( runnable.move(315, 186) == false );
				while( runnable.move(355, 186) == false );
			}
			place[0] = true;
			runnable.changeSpeed();
			semBench.release();
			while( runnable.go(335, 80) == false );
		}
		
		else if(place[1] == true)
		{
			place[1] = false;
			while( runnable.go(335, 206) == false );
			runnable.changeSpeed(65);
			semBench3.release();
			semBench2.acquire();
			for(int i = 0; i < randomMoves; i++)
			{
				while( runnable.move(315, 206) == false );
				while( runnable.move(355, 206) == false );
			}
			place[1] = true;
			runnable.changeSpeed();
			semBench.release();
			while( runnable.go(260, 206) == false );
		}
		
		else
		{
			place[2] = false;
			while( runnable.go(335, 226) == false );
			runnable.changeSpeed(65);
			semBench3.release();
			semBench2.acquire();
			for(int i = 0; i < randomMoves; i++)
			{
				while( runnable.move(315, 226) == false );
				while( runnable.move(355, 226) == false );
			}
			place[2] = true;
			runnable.changeSpeed();
			semBench.release();
			while( runnable.go(335, 330) == false );
		}
	}
	
	public void run()
	{
		while(true)
		{	
			try {semBench3.acquire(3);} catch(InterruptedException e1){}
			semBench2.release(3);
			while( place[0] == false && place[1] == false && place[2] == false )
			{
				pictureFrame++;
				pictureFrame = pictureFrame%21;
				frame.repaint();
				try{Thread.sleep(250);} catch (InterruptedException e){}
			}
			pictureFrame = 0;
			randomMoves = rand.nextInt(5) + 3;
		}
	}
	
	public int getPictureFrame(){ return pictureFrame ;}
}
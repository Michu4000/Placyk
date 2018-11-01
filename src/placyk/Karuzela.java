package placyk;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Karuzela extends Thread
{
	private PFrame frame;
	private Semaphore semCarousel, semCarousel2, semCarousel3;
	private Random rand;
	private boolean place[];
	private int randomMoves, pictureFrame;
	
	public Karuzela(PFrame frame)
	{
		semCarousel = new Semaphore(4);
		semCarousel2 = new Semaphore(0);
		semCarousel3 = new Semaphore(0);
		place = new boolean[4];
		place[0] = true;
		place[1] = true;
		place[2] = true;
		place[3] = true;
		rand = new Random();
		randomMoves = rand.nextInt(7) + 5;
		
		this.frame = frame;
		pictureFrame = 0;
	}
	
	public void spin(PRunnable runnable) throws InterruptedException
	{
		semCarousel.acquire();
		
		if(place[0] == true)
		{
			place[0] = false;
			while( runnable.go(104, 460) == false );
			runnable.changeSpeed(17);
			semCarousel3.release();
			semCarousel2.acquire();
			for(int i = 0; i < randomMoves; i++)
			{
				while( runnable.move(117, 460) == false );
				while( runnable.move(131, 485) == false );
				while( runnable.move(117, 511) == false );
				while( runnable.move(104, 511) == false );
				while( runnable.move(91, 511) == false );
				while( runnable.move(79, 485) == false );
				while( runnable.move(91, 460) == false );
				while( runnable.move(104, 460) == false );
			}
			place[0] = true;
			runnable.changeSpeed();
			semCarousel.release();
			while(runnable.go(104, 380) == false);
		}
		
		else if(place[1] == true)
		{
			place[1] = false;
			while( runnable.go(131, 485) == false );
			runnable.changeSpeed(17);
			semCarousel3.release();
			semCarousel2.acquire();
			for(int i = 0; i < randomMoves; i++)
			{
				while( runnable.move(117, 511) == false );
				while( runnable.move(104, 511) == false );
				while( runnable.move(91, 511) == false );
				while( runnable.move(79, 485) == false );
				while( runnable.move(91, 460) == false );
				while( runnable.move(104, 460) == false );
				while( runnable.move(117, 460) == false );
				while( runnable.move(131, 485) == false );
			}
			place[1] = true;
			runnable.changeSpeed();
			semCarousel.release();
			while(runnable.go(221, 485) == false);
		}
		
		else if(place[2] == true)
		{
			place[2] = false;
			while( runnable.go(104, 511) == false );
			runnable.changeSpeed(17);
			semCarousel3.release();
			semCarousel2.acquire();
			for(int i = 0; i < randomMoves; i++)
			{
				while( runnable.move(91, 511) == false );
				while( runnable.move(79, 485) == false );
				while( runnable.move(91, 460) == false );
				while( runnable.move(104, 460) == false );
				while( runnable.move(117, 460) == false );
				while( runnable.move(131, 485) == false );
				while( runnable.move(117, 511) == false );
				while( runnable.move(104, 511) == false );
			}
			place[2] = true;
			runnable.changeSpeed();
			semCarousel.release();
			while( runnable.go(104, 581) == false );
			while( runnable.go(201, 581) == false );
		}
		
		else
		{
			place[3] = false;
			while( runnable.go(79, 485) == false );
			runnable.changeSpeed(17);
			semCarousel3.release();
			semCarousel2.acquire();
			for(int i = 0; i < randomMoves; i++)
			{
				while( runnable.move(91, 460) == false );
				while( runnable.move(104, 460) == false );
				while( runnable.move(117, 460) == false );
				while( runnable.move(131, 485) == false );
				while( runnable.move(117, 511) == false );
				while( runnable.move(104, 511) == false );
				while( runnable.move(91, 511) == false );
				while( runnable.move(79, 485) == false );
			}
			place[3] = true;
			runnable.changeSpeed();
			semCarousel.release();
			while( runnable.go(50, 485) == false );
			while( runnable.go(50, 410) == false );
		}
	}
	
	public void run()
	{
		while(true)
		{	
			try{ semCarousel3.acquire(4) ;} catch (InterruptedException e1){}
			semCarousel2.release(4);
			while(place[0] == false && place[1] == false && place[2] == false && place[3] == false)
			{
				pictureFrame++;
				pictureFrame = pictureFrame%9;
				frame.repaint();
				try{ Thread.sleep(145); } catch (InterruptedException e){}
			}
			pictureFrame = 0;
			randomMoves = rand.nextInt(7) + 5;
		}
	}
	
	public int getPictureFrame(){ return pictureFrame ;}
}
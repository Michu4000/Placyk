package playgrnd;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Carousel extends Thread
{
	private Playground frame;
	private Semaphore semCarousel, semCarousel2, semCarousel3;
	private Random rand;
	private boolean place[];
	private int randomMoves, pictureFrame;
	
	public Carousel(Playground frame)
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
	
	public void spin(Kid kid) throws InterruptedException
	{
		semCarousel.acquire();
		
		if(place[0] == true)
		{
			place[0] = false;
			while( kid.go(104, 460) == false );
			kid.changeSpeed(17);
			semCarousel3.release();
			semCarousel2.acquire();
			for(int i = 0; i < randomMoves; i++)
			{
				while( kid.move(117, 460) == false );
				while( kid.move(131, 485) == false );
				while( kid.move(117, 511) == false );
				while( kid.move(104, 511) == false );
				while( kid.move(91, 511) == false );
				while( kid.move(79, 485) == false );
				while( kid.move(91, 460) == false );
				while( kid.move(104, 460) == false );
			}
			place[0] = true;
			kid.changeSpeed();
			semCarousel.release();
			while(kid.go(104, 380) == false);
		}
		
		else if(place[1] == true)
		{
			place[1] = false;
			while( kid.go(131, 485) == false );
			kid.changeSpeed(17);
			semCarousel3.release();
			semCarousel2.acquire();
			for(int i = 0; i < randomMoves; i++)
			{
				while( kid.move(117, 511) == false );
				while( kid.move(104, 511) == false );
				while( kid.move(91, 511) == false );
				while( kid.move(79, 485) == false );
				while( kid.move(91, 460) == false );
				while( kid.move(104, 460) == false );
				while( kid.move(117, 460) == false );
				while( kid.move(131, 485) == false );
			}
			place[1] = true;
			kid.changeSpeed();
			semCarousel.release();
			while(kid.go(221, 485) == false);
		}
		
		else if(place[2] == true)
		{
			place[2] = false;
			while( kid.go(104, 511) == false );
			kid.changeSpeed(17);
			semCarousel3.release();
			semCarousel2.acquire();
			for(int i = 0; i < randomMoves; i++)
			{
				while( kid.move(91, 511) == false );
				while( kid.move(79, 485) == false );
				while( kid.move(91, 460) == false );
				while( kid.move(104, 460) == false );
				while( kid.move(117, 460) == false );
				while( kid.move(131, 485) == false );
				while( kid.move(117, 511) == false );
				while( kid.move(104, 511) == false );
			}
			place[2] = true;
			kid.changeSpeed();
			semCarousel.release();
			while( kid.go(104, 581) == false );
			while( kid.go(201, 581) == false );
		}
		
		else
		{
			place[3] = false;
			while( kid.go(79, 485) == false );
			kid.changeSpeed(17);
			semCarousel3.release();
			semCarousel2.acquire();
			for(int i = 0; i < randomMoves; i++)
			{
				while( kid.move(91, 460) == false );
				while( kid.move(104, 460) == false );
				while( kid.move(117, 460) == false );
				while( kid.move(131, 485) == false );
				while( kid.move(117, 511) == false );
				while( kid.move(104, 511) == false );
				while( kid.move(91, 511) == false );
				while( kid.move(79, 485) == false );
			}
			place[3] = true;
			kid.changeSpeed();
			semCarousel.release();
			while( kid.go(50, 485) == false );
			while( kid.go(50, 410) == false );
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
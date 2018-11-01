package playgrnd;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Bench
{
	private Semaphore semBench;
	private Random rand;
	private boolean place[];
	
	public Bench()
	{
		semBench = new Semaphore(2);
		rand = new Random();
		place = new boolean[2];
		place[0] = true;
		place[1] = true;
	}
	
	public void sit(Kid kid) throws InterruptedException
	{
		semBench.acquire();
		
		if(place[0] == true)
		{
			place[0] = false;
			while( kid.go(295, 365) == false );
			kid.wait(rand.nextInt(32000) + 8000);
			place[0] = true;
			semBench.release();
			while( kid.go(350, 365) == false );
		}
		
		else
		{
			place[1] = false;
			while( kid.go(295, 342) == false );
			kid.wait(rand.nextInt(32000) + 8000);
			place[1] = true;
			semBench.release();
			while( kid.go(350, 342) == false );
		}
	}
}
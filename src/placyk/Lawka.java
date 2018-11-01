package placyk;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Lawka
{
	private Semaphore semBench;
	private Random rand;
	private boolean place[];
	
	public Lawka()
	{
		semBench = new Semaphore(2);
		rand = new Random();
		place = new boolean[2];
		place[0] = true;
		place[1] = true;
	}
	
	public void sit(PRunnable runnable) throws InterruptedException
	{
		semBench.acquire();
		
		if(place[0] == true)
		{
			place[0] = false;
			while( runnable.go(295, 365) == false );
			runnable.wait(rand.nextInt(32000) + 8000);
			place[0] = true;
			semBench.release();
			while( runnable.go(350, 365) == false );
		}
		
		else
		{
			place[1] = false;
			while( runnable.go(295, 342) == false );
			runnable.wait(rand.nextInt(32000) + 8000);
			place[1] = true;
			semBench.release();
			while( runnable.go(350, 342) == false );
		}
	}
}
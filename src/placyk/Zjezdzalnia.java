package placyk;

import java.util.concurrent.Semaphore;

public class Zjezdzalnia
{
	private Semaphore semSlide;
	
	public Zjezdzalnia(){ semSlide = new Semaphore(1); }
	
	public void ride(PRunnable runnable) throws InterruptedException
	{
		semSlide.acquire();
		while( runnable.go(301, 520) == false );
		runnable.slower();
		while( runnable.go(313, 449) == false );
		semSlide.release();
		runnable.wait(500);
		runnable.faster();
		while( runnable.go(420, 520) == false );
		runnable.changeSpeed();
		while( runnable.go(420, 560) == false );
		while( runnable.go(290, 560) == false );
	}
}
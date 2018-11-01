package playgrnd;

import java.util.concurrent.Semaphore;

public class Slide
{
	private Semaphore semSlide;
	
	public Slide(){ semSlide = new Semaphore(1); }
	
	public void ride(Kid kid) throws InterruptedException
	{
		semSlide.acquire();
		while( kid.go(301, 520) == false );
		kid.slower();
		while( kid.go(313, 449) == false );
		semSlide.release();
		kid.wait(500);
		kid.faster();
		while( kid.go(420, 520) == false );
		kid.changeSpeed();
		while( kid.go(420, 560) == false );
		while( kid.go(290, 560) == false );
	}
}
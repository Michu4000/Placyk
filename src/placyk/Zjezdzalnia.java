package placyk;

import java.util.concurrent.Semaphore;

public class Zjezdzalnia
{
	private Semaphore sem_z;
	
	public Zjezdzalnia()
	{
		sem_z = new Semaphore(1);
	}
	
	public void zjedz(PRunnable r) throws InterruptedException
	{
		sem_z.acquire();
		while( r.go(301, 520) == false );
		r.slower();
		while( r.go(313, 449) == false );
		sem_z.release();
		r.wait(500);
		r.faster();
		while( r.go(420, 520) == false );
		r.change_tempo();
		while( r.go(420, 560) == false );
		while( r.go(290, 560) == false );
	}
}

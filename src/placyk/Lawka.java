package placyk;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Lawka
{
	private Semaphore sem_l;
	private Random rnd;
	private boolean tab[];
	
	public Lawka()
	{
		sem_l = new Semaphore(2);
		rnd = new Random();
		tab = new boolean[2];
		tab[0] = true;
		tab[1] = true;
	}
	
	public void usiadz(PRunnable r) throws InterruptedException
	{
		sem_l.acquire();
		
		if(tab[0]==true)
		{
			tab[0]=false;
			while( r.go(295, 365) == false );
			r.wait(rnd.nextInt(32000)+8000);
			tab[0]=true;
			sem_l.release();
			while( r.go(350, 365) == false );
		}
		
		else
		{
			tab[1]=false;
			while( r.go(295, 342) == false );
			r.wait(rnd.nextInt(32000)+8000);
			tab[1]=true;
			sem_l.release();
			while( r.go(350, 342) == false );
		}
	}
}

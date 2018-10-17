package placyk;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Hustawka extends Thread
{
	private Semaphore sem_h, sem_h2, sem_h3;
	private boolean tab[];
	private int ile;
	private Random rnd;
	
	private int klatka;
	private PFrame frame;
	
	public Hustawka(PFrame frame)
	{
		sem_h = new Semaphore(3);
		sem_h2 = new Semaphore(0);
		sem_h3 = new Semaphore(0);
		tab = new boolean[3];
		tab[0] = true;
		tab[1] = true;
		tab[2] = true;
		rnd = new Random();
		ile = rnd.nextInt(5)+3;
		
		this.frame = frame;
		klatka=0;
	}
	
	public void bujaj(PRunnable r) throws InterruptedException
	{
		sem_h.acquire();
		
		if(tab[0]==true)
		{
			tab[0]=false;
			while( r.go(335, 186) == false );
			r.change_tempo(65);
			sem_h3.release();
			sem_h2.acquire();
			for(int i=0; i<ile; i++)
			{
				while(r.move(315, 186) == false);
				while(r.move(355, 186) == false);
			}
			tab[0]=true;
			r.change_tempo();
			sem_h.release();
			while( r.go(335, 80) == false );
		}
		
		else if(tab[1]==true)
		{
			tab[1]=false;
			while( r.go(335, 206) == false );
			r.change_tempo(65);
			sem_h3.release();
			sem_h2.acquire();
			for(int i=0; i<ile; i++)
			{
				while(r.move(315, 206) == false);
				while(r.move(355, 206) == false);
			}
			tab[1]=true;
			r.change_tempo();
			sem_h.release();
			while( r.go(260, 206) == false );
		}
		
		else
		{
			tab[2]=false;
			while( r.go(335, 226) == false );
			r.change_tempo(65);
			sem_h3.release();
			sem_h2.acquire();
			for(int i=0; i<ile; i++)
			{
				while(r.move(315, 226) == false);
				while(r.move(355, 226) == false);
			}
			tab[2]=true;
			r.change_tempo();
			sem_h.release();
			while( r.go(335, 330) == false );
		}
	}
	
	public void run()
	{
		while(true)
		{	
			try {sem_h3.acquire(3);} catch(InterruptedException e1){}
			sem_h2.release(3);
			while(tab[0]==false && tab[1]==false && tab[2]==false)
			{
				klatka++;
				klatka = klatka%21;
				frame.repaint();
				try{Thread.sleep(250);}catch (InterruptedException e){}
			}
			klatka=0;
			ile = rnd.nextInt(5)+3;
		}
	}
	
	public int getKlatka()
	{
		return klatka;
	}
}

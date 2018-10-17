package placyk;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Karuzela extends Thread
{
	private Semaphore sem_k, sem_k2, sem_k3;
	private boolean tab[];
	private int ile;
	private Random rnd;
	
	private int klatka;
	private PFrame frame;
	
	public Karuzela(PFrame frame)
	{
		sem_k = new Semaphore(4);
		sem_k2 = new Semaphore(0);
		sem_k3 = new Semaphore(0);
		tab = new boolean[4];
		tab[0] = true;
		tab[1] = true;
		tab[2] = true;
		tab[3] = true;
		rnd = new Random();
		ile = rnd.nextInt(7)+5;
		
		this.frame = frame;
		klatka=0;
	}
	
	public void krec(PRunnable r) throws InterruptedException
	{
		sem_k.acquire();
		
		if(tab[0]==true)
		{
			tab[0]=false;
			while( r.go(104, 460) == false );
			r.change_tempo(17);
			sem_k3.release();
			sem_k2.acquire();
			for(int i=0; i<ile; i++)
			{
				while(r.move(117, 460) == false);//
				while(r.move(131, 485) == false);
				while(r.move(117, 511) == false);//
				while(r.move(104, 511) == false);
				while(r.move(91, 511) == false);//
				while(r.move(79, 485) == false);
				while(r.move(91, 460) == false);//
				while(r.move(104, 460) == false);
			}
			tab[0]=true;
			r.change_tempo();
			sem_k.release();
			while(r.go(104, 380) == false);
		}
		
		else if(tab[1]==true)
		{
			tab[1]=false;
			while( r.go(131, 485) == false );
			r.change_tempo(17);
			sem_k3.release();
			sem_k2.acquire();
			for(int i=0; i<ile; i++)
			{
				while(r.move(117, 511) == false);//
				while(r.move(104, 511) == false);
				while(r.move(91, 511) == false);//
				while(r.move(79, 485) == false);
				while(r.move(91, 460) == false);//
				while(r.move(104, 460) == false);
				while(r.move(117, 460) == false);//
				while(r.move(131, 485) == false);
			}
			tab[1]=true;
			r.change_tempo();
			sem_k.release();
			while(r.go(221, 485) == false);
		}
		
		else if(tab[2]==true)
		{
			tab[2]=false;
			while( r.go(104, 511) == false );
			r.change_tempo(17);
			sem_k3.release();
			sem_k2.acquire();
			for(int i=0; i<ile; i++)
			{
				while(r.move(91, 511) == false);//
				while(r.move(79, 485) == false);
				while(r.move(91, 460) == false);//
				while(r.move(104, 460) == false);
				while(r.move(117, 460) == false);//
				while(r.move(131, 485) == false);
				while(r.move(117, 511) == false);//
				while(r.move(104, 511) == false);
			}
			tab[2]=true;
			r.change_tempo();
			sem_k.release();
			while(r.go(104, 581) == false);
			while(r.go(201, 581) == false);
		}
		
		else
		{
			tab[3]=false;
			while( r.go(79, 485) == false );
			r.change_tempo(17);
			sem_k3.release();
			sem_k2.acquire();
			for(int i=0; i<ile; i++)
			{
				while(r.move(91, 460) == false);//
				while(r.move(104, 460) == false);
				while(r.move(117, 460) == false);//
				while(r.move(131, 485) == false);
				while(r.move(117, 511) == false);//
				while(r.move(104, 511) == false);
				while(r.move(91, 511) == false);//
				while(r.move(79, 485) == false);
			}
			tab[3]=true;
			r.change_tempo();
			sem_k.release();
			while(r.go(50, 485) == false);
			while(r.go(50, 410) == false);
		}
	}
	
	public void run()
	{
		while(true)
		{	
			try {sem_k3.acquire(4);} catch(InterruptedException e1){}
			sem_k2.release(4);
			while(tab[0]==false && tab[1]==false && tab[2]==false && tab[3]==false)
			{
				klatka++;
				klatka = klatka%9;
				frame.repaint();
				try{Thread.sleep(145);}catch (InterruptedException e){}
			}
			klatka=0;
			ile = rnd.nextInt(7)+5;
		}
	}
	
	public int getKlatka()
	{
		return klatka;
	}
}

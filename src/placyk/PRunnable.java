package placyk;
import java.awt.Color;
import java.util.Random;

public class PRunnable implements Runnable
{
	private PFrame frame;
	private Color k;
	private int size;
	private int n;
	private int m;	//nr porzadkowy watku
	
	private int x;
	private int y;
	private int tempo;
	//wsp celu
	private int cx;
	private int cy;
	
	private Random rnd = new Random();
	
	public int getX(){return x;}
	
	public int getY(){return y;}
	
	public Color getC(){return k;}
	
	public void faster(){tempo=(tempo)/5;}
	
	public void slower(){tempo=(3*tempo)/2;}
	
	public void change_tempo(){tempo = rnd.nextInt(30)+20;}
	
	public void change_tempo(int a){tempo = a;}
	
	public PRunnable(PFrame frame, int x, int y, int size, int n, int m)
	{
		this.frame = frame;
		this.size = size;
		this.n = n;
		this.m = m;
		this.x = x;
		this.y = y;
		this.k = new Color(rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256));
		tempo = rnd.nextInt(30)+20;
		this.cx = x;
		this.cy = y;
	}
	
	private boolean checkCollisions()
	{
		//flagi kolizji w kierunkach:
		boolean l=false, p=false, g=false, d=false;
		
		//kolizje z przeszkodami:
		//karuzela = (112, 492)x85
		//hustawka = (343, 221)x142
		//zjezdzalnia = (363, 494)x125
		//lawka = (299, 361)x49
			
		if( Math.abs(x - 112) <= (size/2 + 85/2) && Math.abs(y - 492) <= (size/2 + 85/2) )
		{
			if( (x - 112) > 0 && (x - 112) <= (size/2 + 85/2) )
				l=true;
				
			if( (112 - x) > 0 && (112 - x) <= (size/2 + 85/2) )
				p=true;
				
			if( (y - 492) > 0 && (y - 492) <= (size/2 + 85/2) )
				d=true;
				
			if( (492 - y) > 0 && (492 - y) <= (size/2 + 85/2) )
				g=true;
			
			if ( rnd.nextInt(4)==1 )
			{
				while( checkCollisions() == false );
				try{frame.k.krec(this);}catch (InterruptedException e){}
			}
		}
			
		else if( Math.abs(x - 343) <= (size/2 + 142/2) && Math.abs(y - 221) <= (size/2 + 142/2) )
		{
			if( (x - 343) > 0 && (x - 343) <= (size/2 + 142/2) )
				l=true;
				
			if( (343 - x) > 0 && (343 - x) <= (size/2 + 142/2) )
				p=true;
				
			if( (y - 221) > 0 && (y - 221) <= (size/2 + 142/2) )
				d=true;
				
			if( (221 - y) > 0 && (221 - y) <= (size/2 + 142/2) )
				g=true;
			
			if ( rnd.nextInt(50)==1 )
			{
				while( checkCollisions() == false );
				try{frame.h.bujaj(this);}catch (InterruptedException e){}
			}
		}
			
		else if( Math.abs(x - 363) <= (size/2 + 125/2) && Math.abs(y - 494) <= (size/2 + 125/2) )
		{
			if( (x - 363) > 0 && (x - 363) <= (size/2 + 125/2) )
				l=true;
				
			if( (363 - x) > 0 && (363 - x) <= (size/2 + 125/2) )
				p=true;
				
			if( (y - 494) > 0 && (y - 494) <= (size/2 + 125/2) )
				d=true;
				
			if( (494 - y) > 0 && (494 - y) <= (size/2 + 125/2) )
				g=true;

			if ( rnd.nextInt(15)==1 )
			{
				while( checkCollisions() == false );
				try{frame.z.zjedz(this);}catch (InterruptedException e){}
			}
		}
			
		else if( Math.abs(x - 299) <= (size/2 + 49/2) && Math.abs(y - 361) <= (size/2 + 49/2) )
		{
			if( (x - 299) > 0 && (x - 299) <= (size/2 + 49/2) )
				l=true;
				
			if( (299 - x) > 0 && (299 - x) <= (size/2 + 49/2) )
				p=true;
				
			if( (y - 361) > 0 && (y - 361) <= (size/2 + 49/2) )
				d=true;
				
			if( (361 - y) > 0 && (361 - y) <= (size/2 + 49/2) )
				g=true;
			
			if ( rnd.nextInt(50)==1 )
			{
				while( checkCollisions() == false );
				try{frame.l.usiadz(this);}catch (InterruptedException e){}
			}
		}
			
		//kolizje z innymi ludzikami
		for(int i=0;i<n;i++)
		{
			if(i==m)
				continue;
			
			if( Math.abs(x - frame.r[i].getX() ) <= size && Math.abs(y - frame.r[i].getY() ) <= size )
			{
				if( (x - frame.r[i].getX() ) > 0 && (x - frame.r[i].getX() ) <= size )
					l=true;
					
				if( (frame.r[i].getX() - x) > 0 && (frame.r[i].getX() - x) <= size )
					p=true;
					
				if( (y - frame.r[i].getY() ) > 0 && (y - frame.r[i].getY() ) <= size )
					d=true;
					
				if( (frame.r[i].getY() - y) > 0 && (frame.r[i].getY() - y) <= size )
					g=true;
			}
		}
		
		return avoidCollision(l,p,g,d);
	}
	
	public boolean avoidCollision(boolean l, boolean p, boolean g, boolean d)
	{
		if(l==false && p==false && g==false && d==false)//brak
		{
			return true;
		}
		
		if(l==false && p==true && g==true && d==true)//w lewo
		{
			x--;
			frame.repaint();
			wait(tempo);
			return false;
		}
			
		if(l==true && p==false && g==true && d==true)//w prawo
		{
			x++;
			frame.repaint();
			wait(tempo);
			return false;
		}	
				
		if(l==true && p==true && g==false && d==true)//w gore
		{
			y++;
			frame.repaint();
			wait(tempo);
			return false;
		}	
			
		if(l==true && p==true && g==true && d==false)//w dol
		{
			y--;
			frame.repaint();
			wait(tempo);
			return false;
		}	
			
		if(l==false && p==false && g==true && d==true)//w lewo albo prawo
		{
			if(rnd.nextBoolean()==true)
				x--;
			else
				x++;
			frame.repaint();
			wait(tempo);
			return false;
		}	
			
		if(l==true && p==true && g==false && d==false)//w gore lub w dol
		{
			if(rnd.nextBoolean()==true)
				y--;
			else
				y++;
			frame.repaint();
			wait(tempo);
			return false;
		}	
			
		if(l==false && p==false && g==false && d==true)//w lewo albo prawo lub w gore
		{
			switch(rnd.nextInt(3))
			{
			case 0:
				x--;
				break;
			
			case 1:
				x++;
				break;
			
			case 2:
				y++;
				break;
			}
			frame.repaint();
			wait(tempo);
			return false;
		}	
			
		if(l==false && p==false && g==true && d==false)//w lewo albo prawo lub w dol
		{
			switch(rnd.nextInt(3))
			{
			case 0:
				x--;
				break;
			
			case 1:
				x++;
				break;
			
			case 2:
				y--;
				break;
			}
			frame.repaint();
			wait(tempo);
			return false;
		}	
				
		if(l==false && p==true && g==false && d==false)//w gore lub w dol lub w lewo
		{
			switch(rnd.nextInt(3))
			{
			case 0:
				y++;
				break;
			
			case 1:
				y--;
				break;
			
			case 2:
				x--;
				break;
			}
			frame.repaint();
			wait(tempo);
			return false;
		}	
		
		if(l==true && p==false && g==false && d==false)//w gore lub w dol lub w prawo
		{
			switch(rnd.nextInt(3))
			{
			case 0:
				y++;
				break;
			
			case 1:
				y--;
				break;
			
			case 2:
				x++;
				break;
			}
			frame.repaint();
			wait(tempo);
			return false;
		}	
		
		if(l==true && p==true && g==true && d==true)//nigdzie
		{
			wait(tempo);
			return false;
		}	
			
		if(l==true && p==false && g==true && d==false)//w prawo lub w dol
		{
			if(rnd.nextBoolean()==true)
				x++;
			else
				y--;
			frame.repaint();
			wait(tempo);
			return false;
		}	
		
		if(l==false && p==true && g==false && d==true)//w lewo lub w gore
		{
			if(rnd.nextBoolean()==true)
				x--;
			else
				y++;
			frame.repaint();
			wait(tempo);
			return false;
		}	
			
		if(l==false && p==true && g==true && d==false)//w lewo lub w dol
		{
			if(rnd.nextBoolean()==true)
				x--;
			else
				y--;
			frame.repaint();
			wait(tempo);
			return false;
		}	
			
		if(l==true && p==false && g==false && d==true)//w prawo lub w gore
		{
			if(rnd.nextBoolean()==true)
				y++;
			else
				x++;
			frame.repaint();
			wait(tempo);
			return false;
		}
		
		return true;
	}
	
	public boolean go(int xx, int yy)
	{
		if(x==xx && y==yy)
			return true;
		
		if(x>xx)
		{
			if(rnd.nextBoolean()==true)
				x--;
		}
			
		if(x<xx)
		{
			if(rnd.nextBoolean()==true)
				x++;
		}
			
		if(y>yy)
		{
			if(rnd.nextBoolean()==true)
				y--;
		}
			
		if(y<yy)
		{
			if(rnd.nextBoolean()==true)
				y++;
		}
		
		frame.repaint();
		wait(tempo);
		return false;
	}
	
	public boolean move(int xx, int yy)
	{
		if(x==xx && y==yy)
			return true;
		
		if(x>xx)
			x--;
			
		if(x<xx)
			x++;
			
		if(y>yy)
			y--;
			
		if(y<yy)
			y++;
		
		frame.repaint();
		wait(tempo);
		return false;
	}
	
	public void wait(int t)
	{
		try{Thread.sleep(t);}catch (InterruptedException e){}
	}
	
	@Override
	public void run()
	{
		while(true)
		//if(free==true)
		{
			while(true)
			{
				if( checkCollisions()==true )
					break;
				else
				{
					cx=rnd.nextInt(390)+40;
					cy=rnd.nextInt(520)+50;
					change_tempo();
				}	
			}
			
			if(go(cx, cy) == true)
			{
				cx=rnd.nextInt(390)+40;
				cy=rnd.nextInt(520)+50;
				change_tempo();
			}
		}
	}
}
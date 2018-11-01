package playgrnd;

import java.awt.Color;
import java.util.Random;

public class Kid implements Runnable
{
	private Playground frame;
	private Color color;
	private int size, numberOfThreads, threadNumber, x, y, speed;
	private int destX, destY; //destination coords
	private Random rand = new Random();
	
	public int getX(){ return x; }
	
	public int getY(){ return y; }
	
	public Color getColor(){ return color; }
	
	public void faster(){ speed /= 5; }
	
	public void slower(){ speed = (3*speed)/2; }
	
	public void changeSpeed(){ speed = rand.nextInt(30) + 20; }
	
	public void changeSpeed(int a){ speed = a; }
	
	public Kid(Playground frame, int x, int y, int size, int numberOfThreads, int threadNumber)
	{
		this.frame = frame;
		this.size = size;
		this.numberOfThreads = numberOfThreads;
		this.threadNumber = threadNumber;
		this.x = x;
		this.y = y;
		this.color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
		this.speed = rand.nextInt(30) + 20;
		this.destX = x;
		this.destY = y;
	}
	
	private boolean checkCollisions()
	{
		//collision flags in directions:
		boolean leftCollision = false, rightCollision = false, topCollision = false, bottomCollision = false;
		
		//collisions with obstacles:
		//carousel = (112, 492)x85
		//swing = (343, 221)x142
		//slide = (363, 494)x125
		//bench = (299, 361)x49
			
		if( Math.abs(x - 112) <= (size/2 + 85/2) && Math.abs(y - 492) <= (size/2 + 85/2) )
		{
			if( (x - 112) > 0 && (x - 112) <= (size/2 + 85/2) )
				leftCollision = true;
				
			if( (112 - x) > 0 && (112 - x) <= (size/2 + 85/2) )
				rightCollision = true;
				
			if( (y - 492) > 0 && (y - 492) <= (size/2 + 85/2) )
				bottomCollision = true;
				
			if( (492 - y) > 0 && (492 - y) <= (size/2 + 85/2) )
				topCollision = true;
			
			if ( rand.nextInt(4) == 1 )
			{
				while( checkCollisions() == false );
				try{ frame.carousel.spin(this) ;} catch (InterruptedException e){}
			}
		}
			
		else if( Math.abs(x - 343) <= (size/2 + 142/2) && Math.abs(y - 221) <= (size/2 + 142/2) )
		{
			if( (x - 343) > 0 && (x - 343) <= (size/2 + 142/2) )
				leftCollision = true;
				
			if( (343 - x) > 0 && (343 - x) <= (size/2 + 142/2) )
				rightCollision = true;
				
			if( (y - 221) > 0 && (y - 221) <= (size/2 + 142/2) )
				bottomCollision = true;
				
			if( (221 - y) > 0 && (221 - y) <= (size/2 + 142/2) )
				topCollision = true;
			
			if ( rand.nextInt(50) == 1 )
			{
				while( checkCollisions() == false );
				try{ frame.swing.sway(this); } catch (InterruptedException e){}
			}
		}
			
		else if( Math.abs(x - 363) <= (size/2 + 125/2) && Math.abs(y - 494) <= (size/2 + 125/2) )
		{
			if( (x - 363) > 0 && (x - 363) <= (size/2 + 125/2) )
				leftCollision = true;
				
			if( (363 - x) > 0 && (363 - x) <= (size/2 + 125/2) )
				rightCollision = true;
				
			if( (y - 494) > 0 && (y - 494) <= (size/2 + 125/2) )
				bottomCollision = true;
				
			if( (494 - y) > 0 && (494 - y) <= (size/2 + 125/2) )
				topCollision = true;

			if ( rand.nextInt(15) == 1 )
			{
				while( checkCollisions() == false );
				try{ frame.slide.ride(this); } catch (InterruptedException e){}
			}
		}
			
		else if( Math.abs(x - 299) <= (size/2 + 49/2) && Math.abs(y - 361) <= (size/2 + 49/2) )
		{
			if( (x - 299) > 0 && (x - 299) <= (size/2 + 49/2) )
				leftCollision = true;
				
			if( (299 - x) > 0 && (299 - x) <= (size/2 + 49/2) )
				rightCollision = true;
				
			if( (y - 361) > 0 && (y - 361) <= (size/2 + 49/2) )
				bottomCollision = true;
				
			if( (361 - y) > 0 && (361 - y) <= (size/2 + 49/2) )
				topCollision = true;
			
			if ( rand.nextInt(50) == 1 )
			{
				while( checkCollisions() == false );
				try{ frame.bench.sit(this); } catch (InterruptedException e){}
			}
		}
			
		//collisions with other kids
		for(int i = 0; i < numberOfThreads; i++)
		{
			if(i == threadNumber)
				continue;
			
			if( Math.abs(x - frame.kid[i].getX() ) <= size && Math.abs(y - frame.kid[i].getY() ) <= size )
			{
				if( (x - frame.kid[i].getX() ) > 0 && (x - frame.kid[i].getX() ) <= size )
					leftCollision = true;
					
				if( (frame.kid[i].getX() - x) > 0 && (frame.kid[i].getX() - x) <= size )
					rightCollision = true;
					
				if( (y - frame.kid[i].getY() ) > 0 && (y - frame.kid[i].getY() ) <= size )
					bottomCollision = true;
					
				if( (frame.kid[i].getY() - y) > 0 && (frame.kid[i].getY() - y) <= size )
					topCollision = true;
			}
		}
		
		return avoidCollision(leftCollision, rightCollision, topCollision, bottomCollision);
	}
	
	public boolean avoidCollision(boolean leftCollision, boolean rightCollision, boolean topCollision, boolean bottomCollision)
	{
		if(leftCollision == false && rightCollision == false && topCollision == false && bottomCollision == false) //none
		{
			return true;
		}
		
		if(leftCollision == false && rightCollision == true && topCollision == true && bottomCollision == true) //to left
		{
			x--;
			frame.repaint();
			wait(speed);
			return false;
		}
			
		if(leftCollision == true && rightCollision == false && topCollision == true && bottomCollision == true) //to right
		{
			x++;
			frame.repaint();
			wait(speed);
			return false;
		}	
				
		if(leftCollision == true && rightCollision == true && topCollision == false && bottomCollision == true) //to top
		{
			y++;
			frame.repaint();
			wait(speed);
			return false;
		}	
			
		if(leftCollision == true && rightCollision == true && topCollision == true && bottomCollision == false) //to bottom
		{
			y--;
			frame.repaint();
			wait(speed);
			return false;
		}	
			
		if(leftCollision == false && rightCollision == false && topCollision == true && bottomCollision == true) //to left or right
		{
			if(rand.nextBoolean() == true)
				x--;
			else
				x++;
			frame.repaint();
			wait(speed);
			return false;
		}	
			
		if(leftCollision == true && rightCollision == true && topCollision == false && bottomCollision == false) //to top or bottom
		{
			if(rand.nextBoolean() == true)
				y--;
			else
				y++;
			frame.repaint();
			wait(speed);
			return false;
		}	
			
		if(leftCollision == false && rightCollision == false && topCollision == false && bottomCollision == true) //to left or right or top
		{
			switch(rand.nextInt(3))
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
			wait(speed);
			return false;
		}	
			
		if(leftCollision == false && rightCollision == false && topCollision == true && bottomCollision == false) //to left or right or bottom
		{
			switch(rand.nextInt(3))
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
			wait(speed);
			return false;
		}	
				
		if(leftCollision == false && rightCollision == true && topCollision == false && bottomCollision == false) //to top or bottom or left
		{
			switch(rand.nextInt(3))
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
			wait(speed);
			return false;
		}	
		
		if(leftCollision == true && rightCollision == false && topCollision == false && bottomCollision == false) //to top or bottom or right
		{
			switch(rand.nextInt(3))
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
			wait(speed);
			return false;
		}	
		
		if(leftCollision == true && rightCollision == true && topCollision == true && bottomCollision == true) //none
		{
			wait(speed);
			return false;
		}	
			
		if(leftCollision == true && rightCollision == false && topCollision == true && bottomCollision == false) //to right or bottom
		{
			if(rand.nextBoolean() == true)
				x++;
			else
				y--;
			frame.repaint();
			wait(speed);
			return false;
		}	
		
		if(leftCollision == false && rightCollision == true && topCollision == false && bottomCollision == true) //to left or top
		{
			if(rand.nextBoolean() == true)
				x--;
			else
				y++;
			frame.repaint();
			wait(speed);
			return false;
		}	
			
		if(leftCollision == false && rightCollision == true && topCollision == true && bottomCollision == false) //to left or bottom
		{
			if(rand.nextBoolean() == true)
				x--;
			else
				y--;
			frame.repaint();
			wait(speed);
			return false;
		}	
			
		if(leftCollision == true && rightCollision == false && topCollision == false && bottomCollision == true) //to right or top
		{
			if(rand.nextBoolean() == true)
				y++;
			else
				x++;
			frame.repaint();
			wait(speed);
			return false;
		}
		
		return true;
	}
	
	public boolean go(int xx, int yy)
	{
		if(x == xx && y == yy)
			return true;
		
		if(x>xx)
		{
			if(rand.nextBoolean() == true)
				x--;
		}
			
		if(x<xx)
		{
			if(rand.nextBoolean() == true)
				x++;
		}
			
		if(y>yy)
		{
			if(rand.nextBoolean() == true)
				y--;
		}
			
		if(y<yy)
		{
			if(rand.nextBoolean() == true)
				y++;
		}
		
		frame.repaint();
		wait(speed);
		return false;
	}
	
	public boolean move(int xx, int yy)
	{
		if(x == xx && y == yy)
			return true;
		
		if(x > xx)
			x--;
			
		if(x < xx)
			x++;
			
		if(y > yy)
			y--;
			
		if(y < yy)
			y++;
		
		frame.repaint();
		wait(speed);
		return false;
	}
	
	public void wait(int t){ try{ Thread.sleep(t); } catch (InterruptedException e){} }
	
	@Override
	public void run()
	{
		while(true)
		{
			while(true)
			{
				if( checkCollisions() == true )
					break;
				else
				{
					destX = rand.nextInt(390) + 40;
					destY = rand.nextInt(520) + 50;
					changeSpeed();
				}	
			}
			
			if(go(destX, destY) == true)
			{
				destX = rand.nextInt(390) + 40;
				destY = rand.nextInt(520) + 50;
				changeSpeed();
			}
		}
	}
}
package playgrnd;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

//main application class
public class Playground extends JFrame
{
	public Kid kid[];
	public Thread thread[];
	public Carousel carousel;
	public Swing swing;
	public Bench bench;
	public Slide slide;
	public Audio audio;
	public int size, kidsNumber;
	
	//constructor
	public Playground()
	{
		add(new ImageComponent());
		pack();
		
		size = 17; //size of kid
		kidsNumber = 20;
		kid = new Kid[kidsNumber];
		thread = new Thread[kidsNumber];
		Random rand = new Random();

		for(int i = 0; i < kidsNumber; i++)
		{
			kid[i] = new Kid(this, rand.nextInt(390) + 40, rand.nextInt(520) + 50, size, kidsNumber, i);
			thread[i] = new Thread(kid[i]);
		}
		
		carousel = new Carousel(this);
		swing = new Swing(this);
		bench = new Bench();
		slide = new Slide();
		audio = new Audio();
		
		carousel.start();
		swing.start();
		audio.start();

		for(int i = 0; i < kidsNumber; i++)
			thread[i].start();
	}
	
	//entry point of the program
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				Playground frame = new Playground();
				frame.setTitle("Playground");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationByPlatform(true);
				frame.setResizable(false);
				frame.setVisible(true);
			}
		});
	}
	
	//component on which everything is painted
	class ImageComponent extends JComponent
	{
		//picture width
		private final int WIDTH = 470;
		//picture height
		private final int HEIGHT = 630;
		
		Graphics2D g2;
		Image image;
		Image[] carouselImg;
		Image[] swingImg;
		
		public ImageComponent()
		{
			image = new ImageIcon("img\\Playground.png").getImage();
			
			carouselImg = new Image[9];
			carouselImg[0] = new ImageIcon("img\\Carousel1.png").getImage();
			carouselImg[1] = new ImageIcon("img\\Carousel2.png").getImage();
			carouselImg[2] = new ImageIcon("img\\Carousel3.png").getImage();
			carouselImg[3] = new ImageIcon("img\\Carousel4.png").getImage();
			carouselImg[4] = new ImageIcon("img\\Carousel5.png").getImage();
			carouselImg[5] = new ImageIcon("img\\Carousel6.png").getImage();
			carouselImg[6] = new ImageIcon("img\\Carousel7.png").getImage();
			carouselImg[7] = new ImageIcon("img\\Carousel8.png").getImage();
			carouselImg[8] = new ImageIcon("img\\Carousel9.png").getImage();
			
			swingImg = new Image[21];
			swingImg[0] = new ImageIcon("img\\Swing1.png").getImage();
			swingImg[1] = new ImageIcon("img\\Swing2.png").getImage();
			swingImg[2] = new ImageIcon("img\\Swing3.png").getImage();
			swingImg[3] = new ImageIcon("img\\Swing4.png").getImage();
			swingImg[4] = new ImageIcon("img\\Swing5.png").getImage();
			swingImg[5] = new ImageIcon("img\\Swing6.png").getImage();
			swingImg[6] = new ImageIcon("img\\Swing7.png").getImage();
			swingImg[7] = new ImageIcon("img\\Swing8.png").getImage();
			swingImg[8] = new ImageIcon("img\\Swing9.png").getImage();
			swingImg[9] = new ImageIcon("img\\Swing10.png").getImage();
			swingImg[10] = new ImageIcon("img\\Swing11.png").getImage();
			swingImg[11] = new ImageIcon("img\\Swing12.png").getImage();
			swingImg[12] = new ImageIcon("img\\Swing13.png").getImage();
			swingImg[13] = new ImageIcon("img\\Swing14.png").getImage();
			swingImg[14] = new ImageIcon("img\\Swing15.png").getImage();
			swingImg[15] = new ImageIcon("img\\Swing16.png").getImage();
			swingImg[16] = new ImageIcon("img\\Swing17.png").getImage();
			swingImg[17] = new ImageIcon("img\\Swing18.png").getImage();
			swingImg[18] = new ImageIcon("img\\Swing19.png").getImage();
			swingImg[19] = new ImageIcon("img\\Swing20.png").getImage();
			swingImg[20] = new ImageIcon("img\\Swing21.png").getImage();
		}
		
		//paint component
		public void paintComponent(Graphics g)
		{	
			g2 = (Graphics2D) g;
			
			//picture
			g2.drawImage(image, 0, 0, null);
			
			g2.drawImage(carouselImg[carousel.getPictureFrame()], 70, 450, null);
			g2.drawImage(swingImg[swing.getPictureFrame()], 300, 150, null);
			
			//signature
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("Century", Font.BOLD, 12));
			g2.drawString("Michal K", WIDTH-60, HEIGHT+5);
			
			//kids
			for(int i = 0; i < kidsNumber; i++)
			{
				g2.setColor(Color.BLACK);
				g2.fill(new Ellipse2D.Double(kid[i].getX(), kid[i].getY(), size, size));
				
				g2.setColor(kid[i].getColor());
				g2.fill(new Ellipse2D.Double(kid[i].getX(), kid[i].getY(), size-2, size-2));
			}
		}
		
		//give dimensions of component
		public Dimension getPreferredSize(){ return new Dimension(WIDTH, HEIGHT) ;}
	}
}
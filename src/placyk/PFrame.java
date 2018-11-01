package placyk;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

//main application class
public class PFrame extends JFrame
{
	public PRunnable runnable[];
	public Thread thread[];
	public Karuzela carousel;
	public Hustawka swing;
	public Lawka bench;
	public Zjezdzalnia slide;
	public Audio audio;
	public int size, kidsNumber;
	
	//constructor
	public PFrame()
	{
		add(new ImageComponent());
		pack();
		
		size = 17; //size of kid
		kidsNumber = 20;
		runnable = new PRunnable[kidsNumber];
		thread = new Thread[kidsNumber];
		Random rand = new Random();

		for(int i = 0; i < kidsNumber; i++)
		{
			runnable[i] = new PRunnable(this, rand.nextInt(390) + 40, rand.nextInt(520) + 50, size, kidsNumber, i);
			thread[i] = new Thread(runnable[i]);
		}
		
		carousel = new Karuzela(this);
		swing = new Hustawka(this);
		bench = new Lawka();
		slide = new Zjezdzalnia();
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
				PFrame frame = new PFrame();
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
			image = new ImageIcon("Playground.png").getImage();
			
			carouselImg = new Image[9];
			carouselImg[0] = new ImageIcon("Carousel1.png").getImage();
			carouselImg[1] = new ImageIcon("Carousel2.png").getImage();
			carouselImg[2] = new ImageIcon("Carousel3.png").getImage();
			carouselImg[3] = new ImageIcon("Carousel4.png").getImage();
			carouselImg[4] = new ImageIcon("Carousel5.png").getImage();
			carouselImg[5] = new ImageIcon("Carousel6.png").getImage();
			carouselImg[6] = new ImageIcon("Carousel7.png").getImage();
			carouselImg[7] = new ImageIcon("Carousel8.png").getImage();
			carouselImg[8] = new ImageIcon("Carousel9.png").getImage();
			
			swingImg = new Image[21];
			swingImg[0] = new ImageIcon("Swing1.png").getImage();
			swingImg[1] = new ImageIcon("Swing2.png").getImage();
			swingImg[2] = new ImageIcon("Swing3.png").getImage();
			swingImg[3] = new ImageIcon("Swing4.png").getImage();
			swingImg[4] = new ImageIcon("Swing5.png").getImage();
			swingImg[5] = new ImageIcon("Swing6.png").getImage();
			swingImg[6] = new ImageIcon("Swing7.png").getImage();
			swingImg[7] = new ImageIcon("Swing8.png").getImage();
			swingImg[8] = new ImageIcon("Swing9.png").getImage();
			swingImg[9] = new ImageIcon("Swing10.png").getImage();
			swingImg[10] = new ImageIcon("Swing11.png").getImage();
			swingImg[11] = new ImageIcon("Swing12.png").getImage();
			swingImg[12] = new ImageIcon("Swing13.png").getImage();
			swingImg[13] = new ImageIcon("Swing14.png").getImage();
			swingImg[14] = new ImageIcon("Swing15.png").getImage();
			swingImg[15] = new ImageIcon("Swing16.png").getImage();
			swingImg[16] = new ImageIcon("Swing17.png").getImage();
			swingImg[17] = new ImageIcon("Swing18.png").getImage();
			swingImg[18] = new ImageIcon("Swing19.png").getImage();
			swingImg[19] = new ImageIcon("Swing20.png").getImage();
			swingImg[20] = new ImageIcon("Swing21.png").getImage();
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
				g2.fill(new Ellipse2D.Double(runnable[i].getX(), runnable[i].getY(), size, size));
				
				g2.setColor(runnable[i].getColor());
				g2.fill(new Ellipse2D.Double(runnable[i].getX(), runnable[i].getY(), size-2, size-2));
			}
		}
		
		//give dimensions of component
		public Dimension getPreferredSize(){ return new Dimension(WIDTH, HEIGHT) ;}
	}
}
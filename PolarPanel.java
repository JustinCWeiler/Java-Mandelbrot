import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Complex plane will initially be [-2, 2] x i[-2,2]

public class PolarPanel extends JPanel
	implements MouseListener, MouseMotionListener
{
	private double maxX = 2;
	private double minX = -2;
	private double maxY = 2;
	private double minY = -2;

	private double tempMinX, tempMinY;

	// Max 1535
	private Color[] colors;

	private boolean first = true;

	private final int ITERATIONS = 2000;

	private Complex c = new Complex(0, 0);

	public PolarPanel()
	{
		addMouseListener(this);
		//addMouseMotionListener(this);
		initColors();
	}

	// Initialize the colors array
	private void initColors()
	{
		colors = new Color[1536];
		for(int i = 0; i < colors.length; i++)
		{
			colors[i] = convert(i);
		}
	}

	// Each color is assigned a number from 0 to 1535
	// This function maps those numbers to an actual color
	private Color convert(int c)
	{
		int k = c % 255;
		if(c < 256)
			return new Color(255, k, 0);
		if(c < 512)
			return new Color(255 - k, 255, 0);
		if(c < 768)
			return new Color(0, 255, k);
		if(c < 1024)
			return new Color(0, 255 - k, 255);
		if(c < 1280)
			return new Color(k, 0, 255);
		return new Color(255, 0, 255 - k);
	}

	private double convertX(int a)
	{
		int width = getWidth();

		double x = a - width/2;
		double mid = (minX + maxX)/2;
		return mid + x*(maxX - minX)/width;
	}

	private double convertY(int b)
	{
		int height = getHeight();

		double y = height/2 - b;
		double mid = (minY + maxY)/2;
		return mid + y*(maxY - minY)/height;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		// Set up width and height variables
		int width = getWidth();
		int height = getHeight();

		// Julia Set
		juliaSet(g, width, height, c);

		// Mandelbrot Set
		//mandelbrotSet(g, width, height);

		// TODO: the coords printing thing
	}

	private Color getColor(int i)
	{
		if(i == -1)
			return Color.BLACK;
		return colors[(int)((double)i/ITERATIONS * colors.length)];
	}

	private void juliaSet(Graphics g, int w, int h, Complex c)
	{
		for (int i = 0; i < w; i++)
		{
			for (int j = 0; j < h; j++)
			{
				double x = convertX(i);
				double y = convertY(j);
				double r = Math.sqrt(x*x + y*y);
				double theta = Math.atan2(y, x);
				Complex z = new Complex(r, theta);
				g.setColor(getColor(Complex.inSet(z, c, ITERATIONS)));
				g.drawRect(i, j, 1, 1);
			}
		}
	}

	private void mandelbrotSet(Graphics g, int w, int h)
	{
		for (int i = 0; i < w; i++)
		{
			for (int j = 0; j < h; j++)
			{
				double x = convertX(i);
				double y = convertY(j);
				double r = Math.sqrt(x*x + y*y);
				double theta = Math.atan2(y, x);
				Complex z = new Complex(r, theta);
				g.setColor(getColor(Complex.inSet(z, z, ITERATIONS)));
				g.drawRect(i, j, 1, 1);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			if (first)
			{
				tempMinX = convertX(e.getX());
				tempMinY = convertY(e.getY());
			}
			else
			{
				maxX = convertX(e.getX());
				maxY = convertY(e.getY());
				minX = tempMinX;
				minY = tempMinY;
				repaint();
			}
			first = !first;
		}
		else if(e.getButton() == MouseEvent.BUTTON3)
		{
			maxX = 2;
			minX = -2;
			maxY = 2;
			minY = -2;
			repaint();
		}
		else
		{
			repaint();
		}
	}

	public void mouseMoved(MouseEvent e)
	{
		// TODO: show coordinates on the screen
		// also future me: dont forget to uncomment the thing in the constructor
	}

	public void mouseDragged(MouseEvent e) {}

	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public static void main(String[] args)
	{
		// Set up the window and add the panel
		JFrame w = new JFrame("Julia Sets");
		PolarPanel jp = new PolarPanel();
		w.add(jp);
		w.setBounds(200, 200, 600, 600);
		w.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		w.setVisible(true);
	}
}

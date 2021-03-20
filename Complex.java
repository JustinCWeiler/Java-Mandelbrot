// Implements a complex number
public class Complex
{
	private double x, y;

	public Complex(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	// ------------------------------------- Julia/Mandelbrot Set Helpers ----------------------------------------------
	// Implements the function f(z) = z^2 + c
	public static Complex juliaFunction(Complex z, Complex c)
	{
		double[] nums = juliaHelper(z);
		return new Complex(nums[0] - nums[1] + c.x, nums[2] - nums[0] - nums[1] + c.y);
	}
	private static double[] juliaHelper(Complex z)
	{
		return new double[] {
				z.x*z.x,
				z.y*z.y,
				(z.x+z.y) * (z.x+z.y)
		};
	}

	// Tests if the complex number z is in the set defined by the point c
	// Returns -1 if in the set, else returns the number of iterations
	public static int inSet(Complex z, Complex c) { return inSet(z, c, 1000); }
	public static int inSet(Complex z, Complex c, int iterations)
	{
		for (int i = 0; i < iterations; i++)
		{
			double[] nums = juliaHelper(z);
			if (!(nums[0] + nums[1] <= 4))
				return i;
			z = new Complex(nums[0] - nums[1] + c.x, nums[2] - nums[0] - nums[1] + c.y);
		}
		return -1;
	}
	// -----------------------------------------------------------------------------------------------------------------

	public void print()
	{
		System.out.println(x + " + i" + y);
	}

	public double re() { return x; }
	public double im() { return y; }
	public double norm() { return Math.sqrt(normSquared()); }
	public double normSquared() { return x*x + y*y; }
	public Complex conjugate() { return new Complex(x, -y); }

	public static Complex add(Complex c, double a) { return add(c, new Complex(a, 0)); }
	public static Complex add(Complex c, double a, double b) { return add(c, new Complex(a, b)); }
	public static Complex add(Complex c1, Complex c2) { return new Complex(c1.x + c2.x, c1.y + c2.y); }

	public static Complex sub(Complex c, double a) { return sub(c, new Complex(a, 0)); }
	public static Complex sub(Complex c, double a, double b) { return sub(c, new Complex(a, b)); }
	public static Complex sub(Complex c1, Complex c2) { return new Complex(c1.x - c2.x, c1.y - c2.y); }

	public static Complex mult(Complex c, double a) { return new Complex(a*c.x, a*c.y); }
	public static Complex mult(Complex c, double a, double b) { return mult(c, new Complex(a, b)); }
	public static Complex mult(Complex c1, Complex c2) { return new Complex(c1.x*c2.x - c1.y*c2.y, c1.x*c2.y + c1.y*c2.x); }

	public static Complex div(Complex c, double a) { return new Complex(c.x/a, c.y/a); }
	public static Complex div(Complex c, double a, double b) { return div(c, new Complex(a, b)); }
	public static Complex div(Complex c1, Complex c2) { return div(mult(c1, c2.conjugate()), c2.normSquared()); }
}

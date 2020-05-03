import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;
/**позволяет получить первоначальную прямоугольную область на комплексной плоскости**/
	    public void getInitialRange(Rectangle2D.Double r)

		{
			r.x = -2;
			r.y = -1.5;
			r.width = 3;
			r.height = 3;
	    }
/** реализует вычисление фрактала Мандельброта при помощи итерационной формулы 𝑧𝑛= 𝑧𝑛−12+𝑐.**/
	    public int numIterations(double x, double y) {
			int count = 0;
			double re = 0;
			double im = 0;
			double z_n2 = 0;
			//составим формулу
			while (count < MAX_ITERATIONS && z_n2 < 4) {
			    count++;
			    
			    double nextRe = Math.pow(re, 2) - Math.pow(im, 2) + x;
			    double nextIm = 2 * re * im + y;

			    z_n2 = Math.pow(nextRe, 2) + Math.pow(nextIm, 2);

			    re = nextRe;
			    im = nextIm;	   
			}

			return count < MAX_ITERATIONS ? count : -1;
	    }
	    
	    public static String getString() {
			return "Mandelbrot";
    	}
}
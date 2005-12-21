package triangulacion;

import java.io.IOException;

/**
 * @author kat
 *
 */
public class triangulador {
	

	int pidePlanta(){
		System.out.println("Introduzca el número de planta");
		int c;
		try {
			c = System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
			c = 0;
		}
		return c;
	}
	
	/**
	 * @param a
	 * Muestra a por consola.
	 */

	static void muestra( int a){
		System.out.println(a);
	}
	
	/**
	 * @param antena a1, a2, a3, a4
	 */
	public static void main(antena a1, antena a2, antena a3, antena a4) {
		int x, y, z; // punto a hallar
		int a, b, c, d, e, f, g, h, i;
		int x1, y1, z1, d1;
		int x2, y2, z2, d2;
		int x3, y3, z3, d3;
		int x4, y4, z4, d4;
		x1 = a1.getX();
		y1 = a1.getY();
		z1 = a1.getZ();
		d1 = a1.getD();
		x2 = a2.getX();
		y2 = a2.getY();
		z2 = a2.getZ();
		d2 = a2.getD();
		x3 = a3.getX();
		y3 = a3.getY();
		z3 = a3.getZ();
		d3 = a3.getD();
		x4 = a4.getX();
		y4 = a4.getY();
		z4 = a4.getZ();
		d4 = a4.getD();
		a = (d1^2)-(d2^2)-(x1^2)+(x2^2)-(y1^2)+(y2^2)-(z1^2)+(z2^2);
		b = (d1^2)-(d3^2)-(x1^2)+(x3^2)-(y1^2)+(y3^2)-(z1^2)+(z3^2);
		c = (d1^2)-(d4^2)-(x1^2)+(x4^2)-(y1^2)+(y4^2)-(z1^2)+(z4^2);
		d = (x3-x1)/(x2-x1);
		e = (a*d -b)/2;
		f = (d*y2) + (d*y1) - y3 + y1;
		g = (d*z2) + (d*z1) - z3 + z1;
		h = (a-((2*(y2-y1)*e)/f))/(x2-x1); 
		h = h - (c-((2*(y4-y1)*e)/f))/(x4-x1);
		i = ((2*g*(y2-y1))/f)+(2*(z4-z1))-((2*g*(y4-y1))/f)-(2*(z4-z1)); // esta línea está mal
		z = i/h;
		y = e-(g*z)/f;
		x = (a - (2*y*(y2-y1)) - (2*z*(z2-z1)))/(2*(x2-x1));
		System.out.print(x);
		
	}

}

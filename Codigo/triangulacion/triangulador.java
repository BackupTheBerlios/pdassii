package triangulacion;

import java.io.IOException;
import java.lang.Math;

/**
 * @author kat
 *
 */
public class triangulador {
	
	int pidePlanta(){
		System.out.println("Introduzca el numero de planta");
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
	public static punto triangula (antena a1, antena a2, antena a3, antena a4) {
		double x, y, z; // punto a hallar 
		double xtemp, ytemp, ztemp; // punto a hallar sin redondeos
		double v1, v2, v3; // vector de translacion
		double x1, y1, z1, d1;
		double x2, y2, z2, d2;
		double x3, y3, z3, d3;
		double x4, y4, z4, d4;
		
		//obtencion las coordenadas del centro y el radio de las esferas
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
		
		//trasladamos los ejes, las distnacias no cambian
		v1 = x1;
		v2 = y1;
		v3 = z1;
		
		x1 = x1 - v1;
		y1 = y1 - v2;
		z1 = z1 - v3;
		x2 = x2 - v1;
		y2 = y2 - v2;
		z2 = z2 - v3;
		x3 = x3 - v1;
		y3 = y3 - v2;
		z3 = z3 - v3;
		
		//resolucion analitica
		System.out.println("Resultados sin transladar");
		xtemp = ( ( d2 * d2 ) - ( d1 * d1 ) - ( x2 * x2 ) ) / ( -2 * x2 );
		System.out.print("X: ");
		System.out.print(xtemp);
		x = Math.round( xtemp );
		System.out.print(" redondeado: ");
		System.out.println(x);
		ytemp = ( ( d3 * d3 ) - ( d2 * d2 ) - ( x3 * x3 ) + ( x2 * x2 ) + ( 2 * xtemp * x3 ) - ( 2 * xtemp * x2 ) - ( y3 * y3 ) ) / ( -2 * y3 );
		System.out.print("Y: ");
		System.out.print(ytemp); 
		y = Math.round( ytemp );
		System.out.print(" redondeado: ");
		System.out.println(y);
		ztemp = Math.sqrt( ( d1 * d1 ) - ( x* x ) - ( y * y ) ) ;
		System.out.print("Z: ");
		System.out.print(ztemp);
		z =  Math.round( ztemp );
		System.out.print(" redondeado: ");
		System.out.println(z);
	
		//reinstauracion de los ejes
		x = x + v1;
		y = y + v2;
		z = z + v3;
		System.out.println("Resultados");
		System.out.print("X: ");
		System.out.println(x);
		System.out.print("Y: ");
		System.out.println(y);
		System.out.print("Z: ");
		System.out.println(z);
		//comprobamos que es correcto en la cuarta antena
		if ( ( ( ( x4 - x ) * ( x4 - x) ) + ( ( y4 - y ) * ( y4 - y ) ) + ( ( z4 - z ) * ( z4 - z ) ) ) == ( d4 * d4 ) ){
			System.out.println("Esta bien");
		}
		else{
			System.out.println("Esta mal");
		}
		
		//devolvemos el punto a localizar
		punto p = new punto (x,y,z);
		return p;
		
	}
}

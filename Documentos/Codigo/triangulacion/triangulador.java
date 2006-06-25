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

	static void muestra(int a){
		System.out.println(a);
	}
	
	/**
	 * @param antena a1, a2, a3, a4
	 */
	public static punto triangula (antena a1, antena a2, antena a3, antena a4) {
		double x, y, z; // punto a hallar
		double xtemp, ytemp, ztemp; // punto a calcular para cáluclos temporales 
		double v1, v2, v3; // vector de translacion
		double x1, y1, z1, d1; // datos de a1
		double x2, y2, z2, d2; // datos de a2
		double x3, y3, z3, d3; // datos de a3
		double x4, y4, z4, d4; // datos de a4
		double b, a, d; // angulos de las rotaciones
		double x2temp, y2temp, z2temp, x3temp, y3temp, z3temp; // coordenadas temporales en la rotacion
		double aux; // para intercambiar posiciones
	
		// obtencion las coordenadas del centro y el radio de las esferas
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
		
		// comprobacion de que no estan alineadas: intercambiar la tercera y la cuarta;
		punto p1 = new punto (x1, y1, z1);
		punto p2 = new punto (x2, y2, z2);
		punto p3 = new punto (x3, y3, z3);

		if ( p1.alineado(p2) && p1.alineado(p3) ) {
			aux = x3;
			x3 = x4;
			x4 = aux;
			
			aux = y3;
			y3 = y4;
			y4 = aux;
			
			aux = z3;
			z3 = z4;
			z4 = aux;
			
			aux = d3;
			d3 = d4;
			d4 = aux;
		}
		
		// calculo del vector de translacion
		v1 = x1;
		v2 = y1;
		v3 = z1;
		
		// translacion del origen de coordenadas, las distancias no cambian
		x1 = x1 - v1;
		y1 = y1 - v2;
		z1 = z1 - v3;
		x2 = x2 - v1;
		y2 = y2 - v2;
		z2 = z2 - v3;
		x3 = x3 - v1;
		y3 = y3 - v2;
		z3 = z3 - v3;
		
		// mostramos los puntos (quitar luego)
		System.out.println("Translacion:");
		p1 = new punto (x1, y1, z1);
		p1.mostrar();
		p2 = new punto (x2, y2, z2);
		p2.mostrar();
		p3 = new punto (x3, y3, z3);
		p3.mostrar();
		
		// calculo del primer angulo de rotacion: alinear B
		if ( ( x2 == 0 ) && ( z2 == 0 ) ){
			b = 0;
		}
		else {
			b = Math.acos( x2 / Math.sqrt( ( x2 * x2 ) + ( z2 * z2 ) ) );
			if ( z2 < 0 ){
				b = -b;
			}
		}
		System.out.print("B: ");
		System.out.println(Math.toDegrees(b));

		// rotamos para alinear B en el plano XZ
		// usamos temporales para no usar los valores actualizados a mitad del calculo
		x2temp = ( x2 * Math.cos(b) ) + ( z2 * Math.sin(b) );
		z2temp = ( (-x2) * Math.sin(b) ) + ( z2 * Math.cos(b) );
		x3temp = ( x3 * Math.cos(b) ) + ( z3 * Math.sin(b) );
		z3temp = ( (-x3) * Math.sin(b) ) + ( z3 * Math.cos(b) );
		
		// actualizamos valores 
		x2 = x2temp;
		z2 = z2temp;
		x3 = x3temp;
		z3 = z3temp;
		
		// redondeamos para garantizar z2=0
		x2 =  Math.round( x2 );
		z2 =  Math.round( z2 );
		x3 =  Math.round( x3 );
		z3 =  Math.round( z3 );
		
		// mostramos los puntos (quitar luego)
		System.out.println("Rotacion 1 de B");
		p1 = new punto (x1, y1, z1);
		p1.mostrar();
		p2 = new punto (x2, y2, z2);
		p2.mostrar();
		p3 = new punto (x3, y3, z3);
		p3.mostrar();
		
		// calculo del segundo angulo de rotacion: alinear B
		if ( ( x2 == 0 ) && ( y2 == 0 ) ){
			a = 0;
		}
		else {
			a = Math.acos( x2 / Math.sqrt( ( x2 * x2 ) + ( y2 * y2 ) ) );
			if ( y2 < 0 ){
				a = -a;
			}
		}
		System.out.print("A: ");
		System.out.println(Math.toDegrees(a));
		
		
		// rotamos para alinear B en el plano XY
		// usamos temporales para no usar los valores actualizados a mitad del calculo
		x2temp = ( x2 * Math.cos(a) ) + ( y2 * Math.sin(a) );
		y2temp = ( (-x2) * Math.sin(a) ) + ( y2 * Math.cos(a) );
		x3temp = ( x3 * Math.cos(a) ) + ( y3 * Math.sin(a) );
		y3temp = ( (-x3) * Math.sin(a) ) + ( y3 * Math.cos(a) );
		
		// actualizamos los valores
		x2 = x2temp;
		y2 = y2temp;
		x3 = x3temp;
		y3 = y3temp;
		
		// redondeamos para garantizar y2=0
		x2 =  Math.round( x2 );
		y2 =  Math.round( y2 );
		x3 =  Math.round( x3 );
		y3 =  Math.round( y3 );
		
		// mostramos los puntos (quitar luego)
		System.out.println("Rotacion 2 de B");
		p1 = new punto (x1, y1, z1);
		p1.mostrar();
		p2 = new punto (x2, y2, z2);
		p2.mostrar();
		p3 = new punto (x3, y3, z3);
		p3.mostrar();
		
		
		// calculo del primer angulo de rotacion: alinear C
		if ( ( y3 == 0 ) && ( z3 == 0 ) ){
			d = 0;
		}
		else {
			d = Math.acos( y3 / Math.sqrt( ( y3 * y3 ) + ( z3 * z3 ) ) );
			if ( z3 < 0 ){
				d = -d;
			}
		}
		System.out.print("D: ");
		System.out.println(Math.toDegrees(d));

		// rotamos para alinear C en el plano YZ
		// usamos temporales para no usar los valores actualizados a mitad del calculo
		y3temp = ( y3 * Math.cos(d) ) + ( z3 * Math.sin(d) );
		z3temp = ( (-y3) * Math.sin(d) ) + ( z3 * Math.cos(d) );

		// actualizamos valores
		x3 = x3temp;
		z3 = z3temp;
		
		// redondeos para garantizar z3=0
		x3 =  Math.round( x3 );
		z3 =  Math.round( z3 );
		
		// mostramos los puntos (quitar luego)
		System.out.println("Rotacion 1 de C");
		p1 = new punto (x1, y1, z1);
		p1.mostrar();
		p2 = new punto (x2, y2, z2);
		p2.mostrar();
		p3 = new punto (x3, y3, z3);
		p3.mostrar();
		
		// resolucion analitica
			x = ( ( d2 * d2 ) - ( d1 * d1 ) - ( x2 * x2 ) ) / ( -2 * x2 );
			y = ( ( d3 * d3 ) - ( d2 * d2 ) - ( x3 * x3 ) + ( x2 * x2 ) + ( 2 * x * x3 ) - ( 2 * x * x2 ) - ( y3 * y3 ) ) /  ( -2 * y3 );
			z = Math.sqrt( ( d1 * d1 ) - ( x * x ) - ( y * y ) ) ;
		
		// dehacer la primera rotacion de C: girar en sentido contrario (-d)
		ytemp = ( y * Math.cos(-d) ) + ( z * Math.sin(-d) );
		ztemp = ( (-y) * Math.sin(-d) ) + ( z * Math.cos(-d) );
		y = ytemp;
		z = ztemp;
		
		//dehacer la segunda rotacion de B: girar en sentido contrario (-a)
		xtemp = ( x * Math.cos(-a) ) + ( y * Math.sin(-a) );
		ytemp = ( (-x) * Math.sin(-a) ) + ( y * Math.cos(-a) );
		x = xtemp;
		y = ytemp;
		
		//dehacer la primera rotacion de B: girar en sentido contrario (-b)
		xtemp = ( x * Math.cos(-b) ) + ( z * Math.sin(-b) );
		ztemp = ( (-x) * Math.sin(-b) ) + ( z * Math.cos(-b) );
		x = xtemp;
		z = ztemp;
		
		//reinstauracion de los ejes
		xtemp = x + v1;
		ytemp = y + v2;
		ztemp = z + v3;
		
		// redondeos
		x = Math.round( xtemp );
		y = Math.round( ytemp );
		z = Math.round( ztemp );
		
		// mostramos los puntos (quitar luego)
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

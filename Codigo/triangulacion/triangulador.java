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
		double b, a; //angulos de las rotaciones
		double x2temp, y2temp, z2temp, x3temp, y3temp, z3temp; // coordenasdas temporales en la rotacion
		//boolean xiguales,yiguales,ziguales;
	
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
		
		//si dos de las coodenadas son iguales en todos se resuelve de otra forma: pedir antena  
		//si una de las coordenadas es igual en todos se resuelve de otra forma: simplificar
		
		/*if ( ( x1 == x2) || ( x1 == x3 ) || ( x2 == x3 ) ){
			xiguales = true;
		}
		else{
			xiguales = false;
		}
		
		if ( ( y1 == y2) || ( y1 == y3 ) || ( y2 == y3 ) ){
			yiguales = true;
		}
		else{
			yiguales = false;
		}
		
		if ( ( z1 == z2) || ( z1 == z3 ) || ( z2 == z3 ) ){
			ziguales = true;
		}
		else{
			ziguales = false;
		}
		
		if ( ( xiguales && yiguales ) || ( xiguales && ziguales ) || ( yiguales && ziguales ) ){
			int c = pidePlanta();
			z= c;
		}*/
		
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
		
		/*punto p1 = new punto (x1, y1, z1);
		p1.mostrar();
		punto p2 = new punto (x2, y2, z2);
		p2.mostrar();
		punto p3 = new punto (x3, y3, z3);
		p3.mostrar();*/
		
		// calculo del primer angulo de rotacion
		b = Math.acos( x2 / Math.sqrt( ( x2 * x2 ) + ( z2 * z2 ) ) );
		//System.out.print("B: ");
		//System.out.println(Math.toDegrees(b));

		// rotamos para alinear en el plano XZ
		//x1 = ( x1 * Math.cos(-b) ) + ( z1 * Math.sin(-b) );
		//z1 = ( (-x1) * Math.sin(-b) ) + ( z1 * Math.cos(-b) );
		x2temp = ( x2 * Math.cos(b) ) + ( z2 * Math.sin(b) );
		z2temp = ( (-x2) * Math.sin(b) ) + ( z2 * Math.cos(b) );
		x3temp = ( x3 * Math.cos(b) ) + ( z3 * Math.sin(b) );
		z3temp = ( (-x3) * Math.sin(b) ) + ( z3 * Math.cos(b) );
		
		x2 = x2temp;
		z2 = z2temp;
		x3 = x3temp;
		z3 = z3temp;
		
		//El punto 1 sigue siendo el origen, no lo tenemos que redondear.
		//La coordenada y del punto 2 la hacemos 0, tampoco hay que redondearla.
		/*x2 =  Math.round( x2 );
		z2 =  Math.round( z2 );
		x3 =  Math.round( x3 );
		z3 =  Math.round( z3 );*/
		
		/*punto p1 = new punto (x1, y1, z1);
		p1.mostrar();
		punto p2 = new punto (x2, y2, z2);
		p2.mostrar();
		punto p3 = new punto (x3, y3, z3);
		p3.mostrar();*/
		
		
		// calculo del segundo angulo de rotacion
		a = Math.atan( y2 / Math.sqrt( ( x2 * x2 ) + ( z2 * z2 ) ) );
		System.out.print("A: ");
		System.out.println(Math.toDegrees(a));
		
		// rotamos para alinear en el plano XY
		x2temp = ( x2 * Math.cos(a) ) + ( y2 * Math.sin(a) );
		y2temp = ( (-x2) * Math.sin(a) ) + ( y2 * Math.cos(a) );
		x3temp = ( x3 * Math.cos(a) ) + ( y3 * Math.sin(a) );
		y3temp = ( (-x3) * Math.sin(a) ) + ( y3 * Math.cos(a) );
		
		x2 = x2temp;
		y2 = y2temp;
		x3 = x3temp;
		y3 = y3temp;
		
		// resolucion analitica
		xtemp = ( ( d2 * d2 ) - ( d1 * d1 ) - ( x2 * x2 ) ) / ( -2 * x2 );
		x = xtemp;
		ytemp = ( ( d3 * d3 ) - ( d2 * d2 ) - ( x3 * x3 ) + ( x2 * x2 ) + ( 2 * xtemp * x3 ) - ( 2 * xtemp * x2 ) - ( y3 * y3 ) ) /  ( -2 * y3 );
		y = ytemp;
		ztemp = Math.sqrt( ( d1 * d1 ) - ( xtemp * xtemp ) - ( ytemp * ytemp ) ) ;
		z =  ztemp;
		
		//dehacer la segunda rotacion: girar en sentido contrario (-a)
		xtemp = ( x * Math.cos(-a) ) + ( y * Math.sin(-a) );
		ytemp = ( (-x) * Math.sin(-a) ) + ( y * Math.cos(-a) );
		x = xtemp;
		y = ytemp;
		
		//dehacer la primera rotacion: girar en sentido contrario (-b)
		xtemp = ( x * Math.cos(-b) ) + ( z * Math.sin(-b) );
		ztemp = ( (-x) * Math.sin(-b) ) + ( z * Math.cos(-b) );
		x = xtemp;
		z = ztemp;
		
		//reinstauracion de los ejes
		x = x + v1;
		y = ytemp + v2;
		z = ztemp + v3;
		
		//redondeos
		x = Math.round( xtemp );
		y = Math.round( ytemp );
		z = Math.round( ztemp );
		
		
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

package Servidor.src;

import java.util.Vector;

//import Triangulacion.Punto;

/**
 * @author kat
 *
 */
public class Antena {
	Punto p;
	double d; //distancia al punto a localizar
	
	/**
	 * Constructor por defecto, crea una antena en el origen.
	 * La distancia al punto a localizar es 0.
	 */
	
	public Antena() {
		p = new Punto();
		d = 0;
	}
	
	/**
	 * Constructor completo, crea una antena en el punto 
	 *(x, y, z) que indicamos.
	 * La distancia al punto a localizar es d.
	 */
	public Antena(double x, double y, double z, double d) {
		Punto p = new Punto(x, y, z);
		this.p = p;
		this.d = d;
	}
	
	/**
	 * Accesores y mutadores para todos 
	 * los parametros de la clase.
	 */

	public double getD() {
		return d;
	}

	public void setD(double d) {
		this.d = d;
	}

	public double getX() {
		return p.getX();
	}

	public void setX(double x) {
		this.p.setX(x);
	}

	public double getY() {
		return p.getY();
	}

	public void setY(double y) {
		this.p.setY(y);
	}

	public double getZ() {
		return p.getZ();
	}

	public void setZ(double z) {
		this.p.setZ(z);
	}
	
	public Punto getP() {
		return p;
	}

	public void setP(Punto p) {
		this.p.setX(p.getX());
		this.p.setY(p.getY());
		this.p.setZ(p.getZ());
	}
	public static Vector listaAntenas(String a){
		String a1 = "192.168.0.11";
		String a2 = "192.168.0.12";
		String a3 = "192.168.0.13";
		String a4 = "192.168.0.14";
		String a5 = "192.168.0.15";
		String a6 = "192.168.0.16";
		Vector list = new Vector();
		
		if (a.equals("192.168.0.11")){
			list.add(a1);
			list.add(a2);
			list.add(a3);
			list.add(a4);
		}
		else if (a.equals("192.168.0.12")){
			list.add(a1);
			list.add(a2);
			list.add(a3);
			list.add(a4);
		}
		else if (a.equals("192.168.0.13")){
			list.add(a1);
			list.add(a2);
			list.add(a3);
			list.add(a4);
		}
		else if (a.equals("192.168.0.14")){
			list.add(a1);
			list.add(a2);
			list.add(a3);
			list.add(a4);
		}
		else if (a.equals("192.168.0.15")){
			list.add(a5);
			list.add(a6);
			list.add(a3);
			list.add(a4);
		}
		else if (a.equals("192.168.0.16")){
			list.add(a5);
			list.add(a6);
			list.add(a3);
			list.add(a4);
		}
		else {
			list.add(a5);
			list.add(a6);
			list.add(a3);
			list.add(a4);
		}
		return list;
	}
	
	public static Punto dameCoordenadas(String a){
		if (a.equals("a1"))
			return new Punto(0.5,5,1);
		else if (a.equals("a2"))
			return new Punto(6.5,5,1);
		else if (a.equals("a3"))
			return new Punto(14.5,5,1);
		else if (a.equals("a4"))
			return new Punto(0.5,8.5,1);
		else if (a.equals("a5"))
			return new Punto(6.5,8.5,1);
		else if (a.equals("a4"))
			return new Punto(14.5,8.5,1);
		else
			return new Punto(0,0,0);
	}
}


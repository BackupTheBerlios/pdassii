

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
}


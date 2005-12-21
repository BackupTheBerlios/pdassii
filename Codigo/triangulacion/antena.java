package triangulacion;

/**
 * @author kat
 *
 */
public class antena {
	int x; //coordenada en anchura
	int y; //coordenada en altura
	int z; //coordenada en profundidad
	int d; //distancia al punto a localizar
	
	/**
	 * Constructor por defecto, crea una antena en el origen.
	 * La distancia al punto a localizar es 0.
	 */
	
	public antena() {
		x = 0;
		y = 0;
		z = 0;
		d = 0;
	}
	
	/**
	 * Constructor completo, crea una antena en el punto 
	 *(x, y, z) que indicamos.
	 * La distancia al punto a localizar es d.
	 */
	public antena(int x, int y, int z, int d) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.d = d;
	}
	
	/**
	 * Accesores y mutadores para todos 
	 * los parámetros de la clase.
	 */

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}


	/**
	 * @param args
	 */
	//public static void main(String[] args) {
		// TODO Auto-generated method stub

	//}

}

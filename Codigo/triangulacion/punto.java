package triangulacion;

public class punto {
	
	double x; //coordenada en anchura
	double y; //coordenada en altura
	double z; //coordenada en profundidad

		
	/**
	 * Constructor por defecto, crea un punto en el origen.
	 */
		
		public punto() {
			x = 0;
			y = 0;
			z = 0;
		}
		
		/**
		 * Constructor completo, crea un punto con coordenadas
		 *(x, y, z) que indicamos.
		 */
		public punto (double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		/**
		 * Accesores y mutadores para todos 
		 * los parametros de la clase.
		 */

		public double getX() {
			return x;
		}

		public void setX(double x) {
			this.x = x;
		}

		public double getY() {
			return y;
		}

		public void setY(double y) {
			this.y = y;
		}

		public double getZ() {
			return z;
		}

		public void setZ(double z) {
			this.z = z;
		}

}

package Triangulacion;

public class Punto {
	
	double x; //coordenada en anchura
	double y; //coordenada en altura
	double z; //coordenada en profundidad

		
	/**
	 * Constructor por defecto, crea un punto en el origen.
	 */
		
		public Punto() {
			x = 0;
			y = 0;
			z = 0;
		}
		
		/**
		 * Constructor completo, crea un punto con coordenadas
		 *(x, y, z) que indicamos.
		 */
		public Punto (double x, double y, double z) {
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
		
		public void mostrar(){
			System.out.print("( ");
			System.out.print(this.x);
			System.out.print(", ");
			System.out.print(this.y);
			System.out.print(", ");
			System.out.print(this.z);
			System.out.println(" )");
		}
		
		public boolean alineado(Punto p){
			int a = 0;
			boolean b;
			if ( this.x == p.getX() ){
				a = a +1; 
			}
			
			if ( this.y == p.getY() ){
				a = a +1; 
			}
			
			if ( this.z == p.getZ() ){
				a = a +1; 
			}
			
			if ( a > 1 ){
				b = true;
			}
			else{
				b = false;
			}
			
			return b;
		}

}

import triangulacion.*;
public class PruebaTriangulacion {

	public static void main(String[] args) {
		antena a1 = new antena(1,0,0,1);
		antena a2 = new antena(0,1,0,1);
		antena a3 = new antena(3,3,0,2);
		antena a4 = new antena(4,0,0,2);
		triangulador.main(a1,a2,a3,a4);
		/* Hay que capturar y tratar las excepciones en
		 * el triangulador porqeu sino no funcioan cuando
		 * divides por cero, como en el ejemplo:
		 * h = h - (.../(x4-x1)) x4-x1 = 0
		 * 
		 */ 
	}

}

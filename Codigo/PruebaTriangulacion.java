import triangulacion.*;
public class PruebaTriangulacion {

	public static void main(String[] args) {
		
		/*antena a1 = new antena(0,2,1,2);
		antena a2 = new antena(2,2,0,3);
		antena a3 = new antena(2,0,1,2);
		antena a4 = new antena(0,0,0,0);*/

		antena a1 = new antena(20,20,0,28);
		antena a2 = new antena(0,10,10,14);
		antena a3 = new antena(30,0,20,42);
		antena a4 = new antena(0,0,0,0);		

		triangulador.triangula(a1,a2,a3,a4);
	}

}

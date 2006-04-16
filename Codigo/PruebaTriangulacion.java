import triangulacion.*;
public class PruebaTriangulacion {

	public static void main(String[] args) {
		
		antena a1 = new antena(0.5,0,0,0.5);
		antena a2 = new antena(1,0,1,1.4);
		antena a3 = new antena(0,0,1,1);
		antena a4 = new antena(0,0,0,0);
		
		/*antena a1 = new antena(25,10,10,18.027756377);
		antena a2 = new antena(40,10,10,10);
		antena a3 = new antena(25,20,10,15);
		antena a4 = new antena(40,20,10,0);*/
		

		triangulador.triangula(a1,a2,a3,a4);
	}

}

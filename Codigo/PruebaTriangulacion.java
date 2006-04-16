import triangulacion.*;
public class PruebaTriangulacion {

	public static void main(String[] args) {
		
		antena a1 = new antena(0,0,1,1);
		antena a2 = new antena(2,0,2,2.82);
		antena a3 = new antena(2,1,0,2.23);
		antena a4 = new antena(0,0,0,0);
		
		/*antena a1 = new antena(25,10,10,18.027756377);
		antena a2 = new antena(40,10,10,10);
		antena a3 = new antena(25,20,10,15);
		antena a4 = new antena(40,20,10,0);*/
		

		triangulador.triangula(a1,a2,a3,a4);
	}

}

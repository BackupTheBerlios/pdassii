import triangulacion.*;
import java.io.*;
import java.util.Vector;
import Servidor.src.*;

import Servidor.src.Antena;
public class PruebaTriangulacion {

	public static void main(String[] args) throws Exception{
		

		antena a1 = new antena(0,2,0,1.4);
		antena a2 = new antena(0,0,2,1.4);
		//antena a2 = new antena(1,1,-1,1.4);
		//antena a2 = new antena(1,-1,1,1.4);
		//antena a2 = new antena(1,-1,-1,1.4);
		//antena a2 = new antena(-1,1,1,1.4);
		//antena a2 = new antena(-1,1,-1,1.4);
		//antena a2 = new antena(-1,-1,1,1.4);
		//antena a2 = new antena(-1,-1,-1,1.4);
		
		antena a3 = new antena(-2,-1,-1,2.44);
		//antena a3 = new antena(1,1,-1,1.4);
		//antena a3 = new antena(1,-1,1,1.4);
		//antena a3 = new antena(1,-1,-1,1.4);
		//antena a3 = new antena(-1,1,1,1.4);
		//antena a3 = new antena(-1,1,-1,1.4);
		//antena a3 = new antena(-1,-1,1,1.4);
		//antena a3 = new antena(-1,-1,-1,1.4);
		
		antena a4 = new antena(0,0,0,0);
		
		/*antena a1 = new antena(15,0,15,18.02);
		antena a2 = new antena(20,20,0,28.72);
		antena a3 = new antena(0,10,10,11.18);
		antena a4 = new antena(0,0,0,5);*/
		
		/*antena a1 = new antena(25,10,10,18.027756377);
		antena a2 = new antena(40,10,10,10);
		antena a3 = new antena(25,20,10,15);
		antena a4 = new antena(40,20,10,0);*/
		

		triangulador.triangula(a1,a2,a3,a4);
		Vector lista = Antena.listaAntenas("192.168.0.11");
		String comando;
		String s;
		for (int i = 0; i <4; i ++){
		comando = "ping -c 1 ";
		System.out.println((String)lista.elementAt(i));
		comando = comando.concat((String)lista.elementAt(i));
		System.out.println((String)comando);
		Process p = Runtime.getRuntime().exec(comando);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while ((s = stdInput.readLine()) != null) {
            if (s.contains("time")){
            	String aux = s;
            	int aux2 = aux.indexOf("time ");
            	s = aux.substring(aux2);
            	s = s.replaceAll("time ", "");
            	s = s.replaceAll("ms", "");
            }
        }
		}
	}
	
}

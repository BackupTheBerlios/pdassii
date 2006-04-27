package pruebaXML_mysql;


import java.util.*; 
import org.apache.xmlrpc.*;
import java.io.*;

public class JavaServer {
	public JavaServer () {
        // Our handler is a regular Java object. It can have a
        // constructor and member variables in the ordinary fashion.
        // Public methods will be exposed to XML-RPC clients.
    }

    /*public Hashtable sumAndDifference (int x, int y) {
        Hashtable result = new Hashtable();
        result.put("sum", new Integer(x + y));
        result.put("difference", new Integer(x - y));
        return result;
    }*/
    
	public String leer(String archivo){
		String str;
		String salida = "";
		try{
			File f = new File(archivo); 
			BufferedReader in = new BufferedReader(new FileReader(f));
			while((str = in.readLine())!= null)
				salida += str+"\n";
			System.out.println(salida);
			in.close();
			return salida;
		}catch(IOException ioe){
			System.err.println(ioe);
			System.err.println("error en la lectura del dato");
			return "";
		}
	}
	
    public Integer imprimir(String nombrePaciente, int opcion){
     	/*
     	/*	0.buscar en la base de datos si esta el nombrePaciente
     	 	1. el servidor elige las antenas que se van a usar
     	 	2. se mide la distancia
     	 	3. se triangula
     	 	4. con el punto que se devuelve se crea un nodo y se llama a floyd y se localiza la impresora
     	 	5. llamar a imprimir ( llamada  a cups)
     	 	6. informar al usuario

     	 */
    	String info;//aqui guardo el resultado de la lectura del fichero que es lo que hay que mandar a imprimir
    	BaseDatos miBase = new BaseDatos("bdpersonal", "maria", "1829");
    	switch(opcion){
    		case 0: //imprimir un expediente
    			try{
    				String expediente = miBase.consultaExpediente("pruebaPaciente", nombrePaciente);
    				int tam = expediente.length();
    				expediente = expediente.substring(0, tam-1);
    				if (expediente == "")
    					return new Integer(-1);
    				else{
    					info = leer(expediente);
    					if (info == null)
    						return new Integer(-1);
    				}
    			}catch(Exception e){
    				return new Integer(-1);
    			}
    			break;
    		case 1: //imprimir los ultimos analisis
    			try{
    				String expediente = miBase.consultaAnalisis("pruebaPaciente", nombrePaciente);
    				int tam = expediente.length();
    				expediente = expediente.substring(0, tam-1);
    				if (expediente == "")
    					return new Integer(-1);
    				else{
    					info = leer(expediente);
    					if (info == null)
    						return new Integer(-1);
    				}
    			}catch(Exception e){
    				return new Integer(-1);
    			}
    			break;
    	}
    	return new Integer(1);
     	 
    }
    
    public String consultaExpediente(String nombrePaciente){
    	BaseDatos miBase = new BaseDatos("bdpersonal", "maria", "1829");
    	try{
			String expediente = miBase.consultaExpediente("pruebaPaciente", nombrePaciente);
			int tam = expediente.length();
			expediente = expediente.substring(0, tam-1);
			if (expediente == "")
				return expediente;
			else
				return leer(expediente);
		}catch(Exception e){
			return "";
		}
    }

    public static void main (String [] args) {
        try {
            //Mapa map = new Mapa();
            // Invoke me as <http://localhost:8080/RPC2>.
            WebServer server = new WebServer(8080);
            //server.addHandler("pruebaxml", new JavaServer());
            server.addHandler("bd", new BaseDatos("bdpersonal", "maria", "1829"));
            server.addHandler("servidor", new JavaServer());
            server.start();

        } catch (Exception exception) {
            System.err.println("JavaServer: " + exception.toString());
        }
    }
}

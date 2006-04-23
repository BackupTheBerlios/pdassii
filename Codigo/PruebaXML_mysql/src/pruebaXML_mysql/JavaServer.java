package pruebaXML_mysql;


import java.util.*; 
import org.apache.xmlrpc.*;


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
    	BaseDatos miBase = new BaseDatos("bdpersonal", "maria", "1829");
    	switch(opcion){
    		case 0: //imprimir un expediente
    			String expediente = miBase.consultaExpediente(nombrePaciente);
    			if(expediente != null)
    				return new Integer(1);
    			else
    				return new Integer(2);
    		case 1: //imprimir los ultimos analisis
    			//String expediente = miBase.consultaAnalisis(nombrePaciente);
    			break;
    	}
    	return new Integer(1);
     	 
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

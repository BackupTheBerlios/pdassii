import org.apache.xmlrpc.*;
import java.io.*;
import java.util.Vector;

public class JavaServer {
	
	
	/**
	 * 
	 * @author María Alonso López
	 * 
	 * constructora de la clase
	 *
	 */
	public JavaServer () {
        // Our handler is a regular Java object. It can have a
        // constructor and member variables in the ordinary fashion.
        // Public methods will be exposed to XML-RPC clients.
    }

	/**
	 * 
	 * @author María Alonso López
	 * @param
	 *
	 */
	public String responder (String s){
		String res = "He recibido el mensaje: "+s;
		System.out.println("estoy en responder");
		return res;
	}
    
	/**
	 * 
	 * @author María Alonso López
	 * 
	 * @param String archivo
	 * 
	 * lee y devuelve en un String el contenido de "archivo"
	 *
	 */
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
	
	
	public Vector antenas(String a){
		Vector v = Antena.listaAntenas(a);
		return v;
	}
	/**
	 * 
	 * @author María Alonso López & Jose David Soria Soler 
	 * 
	 * @param String nombrePaciente, int opcion
	 * 
	 * 		0.buscar en la base de datos si esta el nombrePaciente
     	 	1. el servidor elige las antenas que se van a usar
     	 	2. se mide la distancia
     	 	3. se triangula
     	 	4. con el punto que se devuelve se crea un nodo y se llama a floyd y se localiza la impresora
     	 	5. llamar a imprimir ( llamada  a cups)
     	 	6. informar al usuario
	 *
	 */
    public Integer imprimir(String nombrePaciente, int opcion, String ip){
    	String info;//aqui guardo el resultado de la lectura del fichero que es lo que hay que mandar a imprimir
    	BaseDatos miBase = new BaseDatos("Hospital", "root", "PD4ss11");
    	String ruta = "";
    	switch(opcion){
    		case 0: //imprimir un expediente
    			try{
    				String expediente = miBase.consultaExpediente("tablaPacientes", nombrePaciente);
    				int tam = expediente.length();
    				expediente = expediente.substring(0, tam-1);
    				if (expediente == "")
    					return new Integer(-1);
    				else{
    					info = leer(expediente);
    					if (info == null)
    						return new Integer(-1);
    				}
    				ruta = expediente;
    			}catch(Exception e){
    				return new Integer(-1);
    			}
   
    			break;
    		case 1: //imprimir los ultimos analisis
    			try{
    				String analisis = miBase.consultaAnalisis("tablaPacientes", nombrePaciente);
    				int tam = analisis.length();
    				analisis = analisis.substring(0, tam-1);
    				if (analisis == "")
    					return new Integer(-1);
    				else{
    					info = leer(analisis);
    					if (info == null)
    						return new Integer(-1);
    				}
    				ruta = analisis;
    				break;
    			}catch(Exception e){
    				return new Integer(-1);
    			}
    			
    	}
    	//triangulamos para obtener la posición en coordenadas desde la que se realiza la petición 
    	//Antena a1 = new Antena(0, 2, 0, 1.4);
    	//Antena a2 = new Antena(0, 0, 2, 1.4);
    	//Antena a3 = new Antena(-2, -1, -1, 2.44);
    	//Antena a4 = new Antena(0, 0, 0, 0);
    	String a1 = "";
    	String a2 = "";
    	String a3 = "";
    	String a4 = "";
    	int d1 = 0;
    	int d2 = 0;
    	int d3 = 0;
    	int d4 = 0;
    	Punto posicion = Triangulador.mide(a1, a2, a3, a4,d1,d2,d3,d4);
    	Mapa m = new Mapa();
    	m = m.cargarMapa();
    	Nodo n = m.buscaNodoImpresora(posicion);
    	int impresora = n.getnumImpresora();
    	//mandamos a la impresora escojida (variable impresora) el documento pedido (guardados en las variables expediente o analisis dependiendo de lo que tengamos que imprimir)
    	try{
            String aux = "lpr -P ";
            aux = aux.concat(new Integer(impresora).toString());
            aux = aux.concat(ruta);
    		Process p = Runtime.getRuntime().exec(aux);
            int exitVal = p.waitFor();
            System.out.println("Process exitValue: " + exitVal);
            }catch (Exception e){
                    System.out.println("Problem running lpr. " + e);
            }
    	//devolvemos al usuario el número de la impresora por la que se llevará a cabo su petición
    	return new Integer(impresora);
    }
    
    /**
     * 
     * @author María Alonso López
     * 
     * @param String nombrePaciente
     * 
     * atiende la petición de consultar un expediente de la base de datos 
     * (el de la persona "nombre paciente") desde el  PDA 
     *  
     *
     */
    public String consultaExpediente(String nombrePaciente){
    	BaseDatos miBase = new BaseDatos("bdpersonal", "maria", "1829");
    	try{
    		//hacemos la consulta a la base de datos
			String expediente = miBase.consultaExpediente("pruebaPaciente", nombrePaciente);
			int tam = expediente.length();
			expediente = expediente.substring(0, tam-1);
			//si no existe tal expediente se devuelve un String vacio
			if (expediente == "")
				return expediente;
			else
			//si no, lo leemos y lo devolvemos en un String 	
				return leer(expediente);
		}catch(Exception e){
			return "Este expediente no existe";
		}
    }

    /**
     * 
     * @author María Alonso López
     * @param String [] args
     *
     */
    public static void main (String [] args) {
        try {
            WebServer server = new WebServer(8080);
            //handlers:
            server.addHandler("bd", new BaseDatos("bdpersonal", "maria", "1829"));
            server.addHandler("servidor", new JavaServer());
            //iniciamos el servidor
            server.start();

        } catch (Exception exception) {
            System.err.println("JavaServer: " + exception.toString());
        }
    }
}


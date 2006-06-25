/*
 * Autor: María Alonso López
 * 
 * */


package Inicio;

import java.util.Vector;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
//import javax.microedition.xml.rpc.*;

//import org.apache.xmlrpc.XmlRpcException;
import org.kxmlrpc.*;
import org.kxmlrpc.XmlRpcClient;
import java.util.*;

public class Inicio extends MIDlet implements CommandListener{
	
	//atributos
	private Display display;
	private Command salir = new Command("Salir",Command.EXIT,1);//telefono rojo
	private Command select = new Command("Select",Command.OK,1);//boton esquina derecha
	private TextBox campoUsuario; //campo de texto para introducir el nombre de usuario
	private TextBox campoClave; //campo de texto para introducir la clave
	private boolean enter = false;


	public void startApp() {
//MÉTODO QUE LANZA LA EJECUCIÓN DEL MIDLET
		
		String aux1, aux2;
		display = Display.getDisplay (this);
		campoUsuario = new TextBox("Introduzca su nombre de usuario:","", 8, TextField.ANY);
		campoUsuario.addCommand(salir);
		campoUsuario.addCommand(select);
		campoUsuario.setCommandListener(this);
		campoClave = new TextBox("Introduzca su clave personal:","", 8, TextField.ANY );
		campoClave.addCommand(salir);
		campoClave.addCommand(select);
		campoClave.setCommandListener(this);
//		escribir en display
		while (!this.enter){
			display.setCurrent (campoUsuario);
		}
		enter = false;
		TextBox tb = (TextBox)display.getCurrent();
		aux1 = tb.getString();
		while (!this.enter)
			display.setCurrent (campoClave);
		enter = false;
		TextBox tb2 = (TextBox)display.getCurrent();
		aux2 = tb2.getString();
		//System.out.println("he añadido los parametros");
		org.kxmlrpc.XmlRpcClient xrc = new org.kxmlrpc.XmlRpcClient("http://localhost:8080"); 
		System.out.println("he creado un cliente");
		
		Vector params = new Vector();
		/*params.addElement(new Integer(5));
        params.addElement(new Integer(3));
        System.out.println("he añadido los parametros");*/

        // Call the server, and get our result.
        /*Hashtable result =
            (Hashtable) xrc.execute("pruebaxml.sumAndDifference", params);
        int sum = ((Integer) result.get("sum")).intValue();
        int difference = ((Integer) result.get("difference")).intValue();*/

		params.addElement(new String(aux1));
        params.addElement(new String(aux2));
        System.out.println("he añadido los parametros");
        try{
        	String clave = (String) xrc.execute("bd.consultarClave", params);
        	System.out.println("clave: "+clave);
        	/*Hashtable result =
                (Hashtable) xrc.execute("pruebaxml.sumAndDifference", params);
        	System.out.println("he vuelto de la llamada al servidor");
            int sum = ((Integer) result.get("sum")).intValue();
            System.out.println("sum: "+sum);
            int difference = ((Integer) result.get("difference")).intValue();
            System.out.println("difference: "+difference);*/
        } catch (Exception exception) {
            System.err.println("JavaClient: " + exception.getMessage());
        }
		
	}

	public void pauseApp() {
//SUSPENDE LAS ACCIONES EN SEGUNDO PLANO Y LIBERA RECURSOS MIENTRAS EL MIDLET NO ESTÁ ACTIVO
		
	}

	public void destroyApp(boolean arg0){
//DETIENE TODA ACTIVIDAD DEL MIDLET Y LIBERA LOS RECURSOS

	}

	public void commandAction(Command c, Displayable d){
		if (c.equals(salir)){
			//System.out.print("Estoy saliendo con el botón de salir");
			destroyApp(true);
		}
		if (c.equals(select)){
			//System.out.println("he seleccionado");
			enter = true;
		}
	}
	

}

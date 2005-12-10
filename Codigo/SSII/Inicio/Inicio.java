/*
 * Autor: María Alonso López
 * 
 * */


package Inicio;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
//import java.lang.System;

public class Inicio extends MIDlet implements CommandListener{
	
	//atributos
	private Display display;
	private Command salir = new Command("Salir",Command.EXIT,1);//telefono rojo
	private Command select = new Command("Select",Command.OK,1);//boton esquina derecha
	private TextBox campoUsuario; //campo de texto para introducir el nombre de usuario
	private TextBox campoClave; //campo de texto para introducir la clave
	private boolean enter = false;


	public void startApp(){
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
		//System.out.println(aux1);
		//System.out.println(aux2);
		
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

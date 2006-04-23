import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;

/*import org.kxmlrpc.*;
import org.kxmlrpc.XmlRpcClient;
import java.util.*;*/

public class pda extends MIDlet implements CommandListener{
	
//	atributos
	private Display display;
	private Command salir = new Command("Salir",Command.EXIT,1);//boton esquina izquierda
	private Command atras = new Command("Atrás",Command.EXIT,1);//boton esquina izquierda
	private Command consultar = new Command("Consultar",Command.OK,1);//boton esquina derecha
	private Command aceptar = new Command("Aceptar",Command.OK,1);//boton esquina derecha 
	private TextBox campoUsuario; //campo de texto para introducir el nombre de usuario
	private TextBox campoClave; //campo de texto para introducir la clave
	private boolean enter = false;
	private List menu;
	private TextBox nombrePaciente;
	private List opcionesImprimir;
	private int opcionImprimir = -1;
	private int estado = 0;
	/*estado = 0 es pantalla de inicio
	 * estado = 1 es pantalla de menu principal
	 * estado = 2 es pantalla de imprimir
	 * estado = 3 es quiero consultar un expediente
	//}
	 * */
	private String datoPaciente;
	private String tablaPersonal = "personal";
	private String tablaPacientes = "pacientes";
	
	


	public void startApp() {
//MÉTODO QUE LANZA LA EJECUCIÓN DEL MIDLET
		
		display = Display.getDisplay (this);
		boolean pi = pantallaInicio();
		while (!pi)
			pi = pantallaInicio();
		menuPrincipal();
	}

	public void pauseApp() {
//SUSPENDE LAS ACCIONES EN SEGUNDO PLANO Y LIBERA RECURSOS MIENTRAS EL MIDLET NO ESTÁ ACTIVO
		
	}

	public void destroyApp(boolean arg0){
//DETIENE TODA ACTIVIDAD DEL MIDLET Y LIBERA LOS RECURSOS

	}

	public void commandAction(Command c, Displayable d){
		/*
		if (c.equals(salir)){
			System.out.println("estoy saliendo con el boton de salir");
			this.destroyApp(true);
			this.notifyDestroyed();
		}
		else if (c.equals(select)){
			opcionImprimir = opcionesImprimir.getSelectedIndex();
			enter = true;
			System.out.println("le he dado a select");
		}
		else if(c.equals(aceptar)){
			enter = true;
			System.out.println("Estoy dando al boton de aceptar");
		}
		switch(estado){
		  case 1: //pantalla de menu principal
			  List lmp = (List)display.getCurrent();
			  // En función de la opción marcada llamamos al método
			  switch(lmp.getSelectedIndex()) {
			  	case 0://imprimir
			  		System.out.println("Quiero Imprimir");
			  		menuImprimir();
			  		break;
			  	case 1 ://consultar expediente
			  		System.out.println("quiero consultar un expediente");
			  		//datoPaciente = solicitaDatos();
			  		consultaBaseExpediente();
			  		//System.out.println("vuelvo de solicitar los datos");
			  		//consultaBaseImprimir();
			  		break;
			  	case 2 ://salir
			  		System.out.println("quiero salir");
			  		this.destroyApp(true);
			  		this.notifyDestroyed();
			  		break;
			  }
		  case 2: //pantalla de imprimir	  
			  System.out.println("estoy en la pantalla de imprimir");
			  // En función de la opción marcada llamamos al método
			  switch(opcionImprimir) {
			  	case 0://imprimir expediente
			  		//opcionImprimir = -1;
			  		System.out.println("Quiero Imprimir un expediente");
			  		datoPaciente = solicitaDatos();
			  		boolean b = consultaBaseImprimir();
			  		/*if(b){
			  			System.out.println("he vuelto a salvo");
			  			menuPrincipal();
			  		}
			  		else{
			  			System.out.println("he vuelto malito");
			  			menuPrincipal();
			  		}*/
			  		/*break;
			  	case 1 ://imprimir ultimos analisis
			  		System.out.println("quiero imprimir los ultimos analisis");
			  		datoPaciente = solicitaDatos();
			  		consultaBaseImprimir();
			  		break;
			  }
		}*/
		if (c.equals(menu.SELECT_COMMAND) && estado ==1){
			  // En función de la opción marcada llamamos al método
			 switch(menu.getSelectedIndex()) {
			  	case 0://imprimir
			  		System.out.println("Quiero Imprimir");
			  		menuImprimir();
			  		break;
			  	case 1 ://consultar expediente
			  		System.out.println("quiero consultar un expediente");
			  		estado = 3;
			  		solicitaDatos();
			  		break;
			  	case 2 ://salir
			  		System.out.println("quiero salir");
			  		this.destroyApp(true);
			  		this.notifyDestroyed();
			  		break;
			 }
		}
		else if(c.equals(opcionesImprimir.SELECT_COMMAND) && estado == 2){
			opcionImprimir = opcionesImprimir.getSelectedIndex();
			switch(opcionImprimir) {
		  	case 0://imprimir expediente
		  		System.out.println("Quiero Imprimir un expediente");
		  		solicitaDatos();
		  		break;
		  	case 1 ://imprimir ultimos analisis
		  		System.out.println("quiero imprimir los ultimos analisis");
		  		solicitaDatos();
		  		break;
		  }
		}
		else if (c.equals(salir)){
			System.out.println("estoy saliendo con el boton de salir");
			this.destroyApp(true);
			this.notifyDestroyed();
		}
		else if (c.equals(consultar)){
			//enter = true;
			System.out.println("le he dado a select");
			TextBox tb = (TextBox)display.getCurrent();
			datoPaciente = tb.getString();
			switch(estado){
				case 2: consultaBaseImprimir();
					    break;
				case 3: consultaBaseExpediente();
			}
			menuPrincipal();
		}
		else if(c.equals(aceptar)){
			enter = true;
			System.out.println("Estoy dando al boton de aceptar");
		}
		else if(c.equals(atras)){
			menuPrincipal();
		}
	}
	
	public boolean pantallaInicio (){
		
		String aux1, aux2;
		campoUsuario = new TextBox("Introduzca su nombre de usuario:","", 8, TextField.ANY);
		campoUsuario.addCommand(salir);
		campoUsuario.addCommand(aceptar);
		campoUsuario.setCommandListener(this);
		campoClave = new TextBox("Introduzca su clave personal:","", 8, TextField.PASSWORD);
		campoClave.addCommand(salir);
		campoClave.addCommand(aceptar);
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

		org.kxmlrpc.XmlRpcClient xrc = new org.kxmlrpc.XmlRpcClient("http://localhost:8080"); 
		
		Vector params = new Vector();
		params.addElement(tablaPersonal);
		params.addElement(new String(aux1));
        params.addElement(new String(aux2));
        try{
        	String respuestaLogIn = (String) xrc.execute("bd.login", params);
        	System.out.println(respuestaLogIn);
        	if(respuestaLogIn.equals("si"))
        		return true;
        	
        	else{
        		Alert aviso = new Alert("El nombre de usuario o la clave personal no son correctas");
                aviso.setType(AlertType.ERROR);
                aviso.setTitle("ERROR");
                aviso.setString("El nombre de usuario o la clave personal no son correctas");
                aviso.setTimeout(Alert.FOREVER);
                display.setCurrent(aviso, display.getCurrent());
                System.out.println("El nombre de usuario o la clave personal no son correctas");
                return false;
        	}
        } catch (Exception exception) {
            System.err.println("JavaClient: " + exception.getMessage());
            Alert aviso = new Alert("El nombre de usuario o la clave personal no son correctas");
            aviso.setType(AlertType.ERROR);
            aviso.setTitle("ERROR");
            aviso.setString("El nombre de usuario o la clave personal no son correctas");
            aviso.setTimeout(Alert.FOREVER);
            //aviso.setTimeout(900000000);
            display.setCurrent(aviso);
            System.out.println("El nombre de usuario o la clave personal no son correctas");
            return false;
        }
		
	}

	public void menuPrincipal(){
		estado = 1;
		//creamos la lista con las opciones del menú principal
		menu = new List("Menú Principal",List.IMPLICIT);
		//añadimos las opciones
		menu.append("Imprimir", null);
		menu.append("Consultar expediente", null);
		menu.append("Salir", null);
		//añadimos los botones
		//menu.addCommand(aceptar);
	    // Fijamos el receptor de eventos sobre la lista
	    menu.setCommandListener((CommandListener)this);
		display.setCurrent(menu);

	}
	
	public void menuImprimir(){
		
		estado = 2;
		opcionesImprimir = new List("Documento para imprimir", List.IMPLICIT);
		opcionesImprimir.append("Expediente", null);
		opcionesImprimir.append("Últimos análisis", null);
		opcionesImprimir.addCommand(atras);
		//opcionesImprimir.addCommand(select);
		opcionesImprimir.setCommandListener((CommandListener)this);
		//enter = false;
		//while(!enter)
		display.setCurrent(opcionesImprimir);
		//System.out.println("ahora tengo la lista en pantalla");
			
}

String solicitaDatos(){
	nombrePaciente = new TextBox("Introduzca el nombre del paciente:", "", 20, TextField.ANY);
	nombrePaciente.addCommand(salir);
	nombrePaciente.addCommand(consultar);
	nombrePaciente.setCommandListener((CommandListener)this);
	//escribir en display
	//enter = false;
	//while(!this.enter)
		display.setCurrent(nombrePaciente);
	//enter = false;
	TextBox tb = (TextBox)display.getCurrent();
	String aux1 = tb.getString();
	System.out.println(aux1);
	datoPaciente = aux1;
	return aux1;
}

boolean consultaBaseImprimir(){
	
	org.kxmlrpc.XmlRpcClient xrc = new org.kxmlrpc.XmlRpcClient("http://localhost:8080"); 
	
	Vector params = new Vector();
	System.out.println(datoPaciente);
	System.out.println(opcionImprimir);
	params.addElement(datoPaciente);
    params.addElement(new Integer(opcionImprimir));
    try{
    	Integer impresora = (Integer)xrc.execute("servidor.imprimir", params);
    	int numeroImpresora = impresora.intValue();
    	
    	if(numeroImpresora == 1){
    		Alert infoImpresora = new Alert("Información sobre la impresora");
            infoImpresora.setType(AlertType.INFO);
            infoImpresora.setTitle("Información de impresión");
            infoImpresora.setString("Sus documentos se imprimirán en la impresora número: "+numeroImpresora+" \n");
            infoImpresora.setTimeout(Alert.FOREVER);
            estado = 1;
            display.setCurrent(infoImpresora, menu);
            System.out.println("el estado es "+ estado);
            System.out.println("la impresora es "+ numeroImpresora);
            //menuPrincipal();
            return true;
    	}
    	else{
    		Alert errorImpresora = new Alert("Información sobre la impresora");
            errorImpresora.setType(AlertType.WARNING);
            errorImpresora.setTitle("Información de impresión");
            errorImpresora.setString("Sus documentos no se pueden imprimir en este momento.\nInténtelo más tarde.");
            errorImpresora.setTimeout(Alert.FOREVER);
            display.setCurrent(errorImpresora);
            return false;
    	}
    	
    	//menuPrincipal();
    } catch (Exception exception) {
        System.err.println("JavaClient: " + exception.getMessage());
        Alert aviso = new Alert("Error en la impresión");
        aviso.setType(AlertType.ERROR);
        aviso.setTitle("ERROR");
        //aviso.setString(exception.getMessage());
        aviso.setString("error en la consulta de los expedientes");
        aviso.setTimeout(Alert.FOREVER);
        //aviso.setTimeout(600000000);
        display.setCurrent(aviso/*, menu*/);
        //menuPrincipal();
        return false;
    }	


}

void consultaBaseExpediente(){
	
	org.kxmlrpc.XmlRpcClient xrc = new org.kxmlrpc.XmlRpcClient("http://localhost:8080"); 
	
	Vector params = new Vector();
	params.addElement(datoPaciente);
    try{
    	String expediente = (String)xrc.execute("bd.consultaExpediente", params);
    	Alert  alertaExpediente = new Alert("Consulta de expediente");
        alertaExpediente.setType(AlertType.INFO);
        alertaExpediente.setTitle("Expediente de "+ datoPaciente);
        alertaExpediente.setString(expediente+" \n");
        alertaExpediente.setTimeout(Alert.FOREVER);
        display.setCurrent(alertaExpediente, menu);
        System.out.println(expediente+" \n");
    	return;
    } catch (Exception exception) {
        System.err.println("JavaClient: " + exception.getMessage());
        Alert aviso = new Alert("Error en la consulta del expediente");
        aviso.setType(AlertType.ERROR);
        aviso.setTitle("ERROR");
        aviso.setString(exception.getMessage());
        aviso.setTimeout(Alert.FOREVER);
        display.setCurrent(aviso);
        menuPrincipal();
    }
	System.out.println("estoy en consultarBaseExpediente");
	return;
}

}


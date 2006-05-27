import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;

import java.util.Vector;



public class PdaMIDlet extends MIDlet {
	
	private PantallaInicio pi;
	private Display display;
	private TextBox otra;
	private Displayable sig;
	private Command salir = new Command("Salir",Command.EXIT,1);//boton esquina izquierda
	private Command aceptar = new Command("Aceptar",Command.OK,1);//boton esquina derecha
	private Command atras = new Command("Atrás",Command.EXIT,1);//boton esquina izquierda
	
	//Displayable sig;
	public void startApp() {
		//MÉTODO QUE LANZA LA EJECUCIÓN DEL MIDLET
		otra = new TextBox("Pantalla siguiente","", 8, TextField.ANY);
		pi = new PantallaInicio(sig);
		display = Display.getDisplay(this);
		display.setCurrent(pi);
	}

	public void pauseApp() {
		//SUSPENDE LAS ACCIONES EN SEGUNDO PLANO Y LIBERA RECURSOS MIENTRAS EL MIDLET NO ESTÁ ACTIVO
				
	}

	public void destroyApp(boolean arg0){
		//DETIENE TODA ACTIVIDAD DEL MIDLET Y LIBERA LOS RECURSOS

	}
	
	public void errorLogin(Displayable d){
		Alert aviso = new Alert("El nombre de usuario o la clave personal no son correctas");
        aviso.setType(AlertType.ERROR);
        aviso.setTitle("ERROR");
        aviso.setString("El nombre de usuario o la clave personal no son correctas");
        aviso.setTimeout(Alert.FOREVER);
        display.setCurrent( aviso);
        System.out.println("El nombre de usuario o la clave personal no son correctas");
		
	}
	
	public void errorConexion(Displayable d){
		Alert aviso = new Alert("Error en la conexion");
        aviso.setType(AlertType.ERROR);
        aviso.setTitle("ERROR");
        aviso.setString("Error en la conexion: inténtelo de nuevo");
        aviso.setTimeout(Alert.FOREVER);
        display.setCurrent( aviso);
        System.out.println("Error en la conexion");
	}
	
	public void pasarPantalla(Displayable d){
		display = Display.getDisplay(this);
		display.setCurrent(d);
	}
	
	public void infoImpresora(Displayable d, int imp){
		Alert infoImpresora = new Alert("Información sobre la impresora");
        infoImpresora.setType(AlertType.INFO);
        infoImpresora.setTitle("Información de impresión");
        infoImpresora.setString("Sus documentos se imprimirán en la impresora número: "+imp+" \n");
        infoImpresora.setTimeout(Alert.FOREVER);
        display.setCurrent(infoImpresora, d);
	}
	
	public void errorImpresora(Displayable d){
		Alert errorImpresora = new Alert("Información sobre la impresora");
        errorImpresora.setType(AlertType.WARNING);
        errorImpresora.setTitle("Información de impresión");
        errorImpresora.setString("Sus documentos no se pueden imprimir en este momento.\nInténtelo más tarde.");
        errorImpresora.setTimeout(Alert.FOREVER);
        display.setCurrent(errorImpresora);
	}
	
	public void mostrarExpediente(Displayable d, String exp, String np){
		Alert  alertaExpediente = new Alert("Consulta de expediente");
        alertaExpediente.setType(AlertType.INFO);
        alertaExpediente.setTitle("Expediente de "+ np);
        alertaExpediente.setString(exp+" \n");
        alertaExpediente.setTimeout(Alert.FOREVER);
        display.setCurrent(alertaExpediente, d);
	}
	
	public void errorExpediente(Displayable d){
		Alert aviso = new Alert("Error en la consulta del expediente");
        aviso.setType(AlertType.ERROR);
        aviso.setTitle("ERROR");
        aviso.setString("Error en la consulta del expediente");
        aviso.setTimeout(Alert.FOREVER);
        display.setCurrent(aviso, d);
	}

public class PantallaInicio extends Form implements CommandListener, Runnable{
	
	private TextField campoUsuario; //campo de texto para introducir el nombre de usuario
	private TextField campoClave; //campo de texto para introducir la clave
	private String clave;
	private String nombre;
	Displayable sig ;
	
	PantallaInicio(Displayable _sig){
		super("Inicio de sesión");
		sig = _sig;
		campoUsuario = new TextField("Introduzca su nombre de usuario:", "", 8, TextField.ANY);
		campoClave = new TextField("Introduzca su clave personal:","", 8, TextField.PASSWORD);
		this.append(campoUsuario);
		this.append(campoClave);
		this.addCommand(salir);
		this.addCommand(aceptar);
		this.setCommandListener(this);
		//this.setItemStateListener(listener);
	}
	
	public void commandAction(Command c, Displayable d){
		if (c == aceptar){
			nombre = campoUsuario.getString();
			clave = campoClave.getString();
			System.out.println(nombre);
			System.out.println(clave);
			Thread t = new Thread(this);
			t.start();
		}
		else if(c.equals(salir)){
			System.out.println("estoy saliendo con el boton de salir");
			destroyApp(true);
			notifyDestroyed();
		}
	}
	
	/*ItemStateListener listener = new ItemStateListener(){
		
		public void itemStateChanged(Item i){
			nombre = campoUsuario.getString();
			clave = campoClave.getString();
		}	
	};*/
	
	public void run(){
		try{
			org.kxmlrpc.XmlRpcClient xrc = new org.kxmlrpc.XmlRpcClient("http://localhost:8080"); 
			
			Vector params = new Vector();
			params.addElement("personal");
			params.addElement(new String(nombre));
	        params.addElement(new String(clave));
	        try{
	        	String respuestaLogIn = (String) xrc.execute("bd.login", params);
	        	System.out.println(respuestaLogIn);
	        	if(respuestaLogIn.equals("no"))
	        		errorLogin(sig);
	        	else
	        		pasarPantalla(new MenuPrincipal());
	        } catch (Exception exception) {
	            System.err.println("JavaClient: " + exception.getMessage());
	        	errorConexion(sig);
	        }
		}
		catch(Exception e){
			System.err.print("error del hilo");
		}
	}
	
}//fin de la clase PantallaInicio

public class MenuPrincipal extends List implements CommandListener{
	
	//private List menu;
	
	MenuPrincipal(){
		super("Menú Principal",List.IMPLICIT);
		//añadimos las opciones de la lista
		this.append("Imprimir", null);
		this.append("Consultar expediente", null);
		//añadimos un boton
		this.append("Salir", null);
	    // Fijamos el receptor de eventos sobre la lista
	    this.setCommandListener((CommandListener)this);
	}
	
	public void commandAction(Command c, Displayable d){
		if (c.equals(this.SELECT_COMMAND)){
			  // En función de la opción marcada llamamos al método
			 switch(this.getSelectedIndex()) {
			  	case 0://imprimir
			  		System.out.println("Quiero Imprimir");
			  		pasarPantalla(new MenuImprimir(this));
			  		break;
			  	case 1 ://consultar expediente
			  		System.out.println("quiero consultar un expediente");
			  		pasarPantalla(new ConsultaExpediente(this));
			  		break;
			  	case 2 ://salir
			  		System.out.println("quiero salir");
			  		destroyApp(true);
			  		notifyDestroyed();
			  		break;
			 }
		}
		else if(c.equals(salir)){
			System.out.println("estoy saliendo con el boton de salir");
			destroyApp(true);
			notifyDestroyed();
		}
	} 	
}//fin de la clase MenuPrincipal


public class MenuImprimir extends Form implements CommandListener, Runnable{
	
	TextField datosPaciente;
	ChoiceGroup documentoAImprimir;
	String nombrePaciente;
	int opcionImprimir;
	Displayable sig ;
	
	MenuImprimir(Displayable _sig){
		super("Documento para imprimir");
		sig = _sig;
		documentoAImprimir = new ChoiceGroup("Documento a imprimir", Choice.EXCLUSIVE);
		documentoAImprimir.append("Expediente", null);
		documentoAImprimir.append("Ultimos análisis", null);
		this.append(documentoAImprimir);
		datosPaciente = new TextField("Introduzca el nombre del paciente:", "", 20, TextField.ANY);
		this.append(datosPaciente);
		//añadimos botones
		this.addCommand(atras);
		this.addCommand(aceptar);
		this.setCommandListener((CommandListener)this);
	}
	
	
	
	public void run(){
		try{
			org.kxmlrpc.XmlRpcClient xrc = new org.kxmlrpc.XmlRpcClient("http://localhost:8080"); 
		
			Vector params = new Vector();
			params.addElement(nombrePaciente);
			params.addElement(new Integer(opcionImprimir));
			try{
				Integer impresora = (Integer)xrc.execute("servidor.imprimir", params);
				int numeroImpresora = impresora.intValue();
	    	
				if(numeroImpresora == 1){
					infoImpresora(new MenuPrincipal(), numeroImpresora);
				}
				else{
					errorImpresora(sig);
				}
			} catch (Exception exception) {
	        errorConexion(sig);
			}
		}catch(Exception e){
			System.err.print("error del hilo");
		}
	}
	public void commandAction(Command c, Displayable d){
		if(c.equals(atras)){
			pasarPantalla(new MenuPrincipal());
		}
		else if (c.equals(aceptar)){
			nombrePaciente = datosPaciente.getString();
			opcionImprimir = this.documentoAImprimir.getSelectedIndex();
			System.out.println(nombrePaciente+" "+opcionImprimir);
			Thread t = new Thread(this);
			t.start();
		}
	}
	
}//fin de la clase MenuImprimir


public class ConsultaExpediente extends Form implements CommandListener, Runnable{
	
	TextField datosPaciente;
	String nombrePaciente;
	Displayable sig ;
	
	ConsultaExpediente(Displayable _sig){
		super("Consulta de expediente");
		sig = _sig;
		datosPaciente = new TextField("Introduzca el nombre del paciente:", "", 20, TextField.ANY);
		this.append(datosPaciente);
		//añadimos botones
		this.addCommand(atras);
		this.addCommand(aceptar);
		this.setCommandListener((CommandListener)this);
	}
	
	public void run(){
		try{
			org.kxmlrpc.XmlRpcClient xrc = new org.kxmlrpc.XmlRpcClient("http://localhost:8080"); 
		
			Vector params = new Vector();
			params.addElement(nombrePaciente);
		
			try{
				String expediente = (String)xrc.execute("servidor.consultaExpediente", params);
				if (expediente.equals(""))
					errorExpediente(new MenuPrincipal());
				else
					mostrarExpediente(sig, expediente, nombrePaciente);
			} catch (Exception exception) {
				errorConexion(new MenuPrincipal());
			}
		}catch(Exception e){
			System.err.print("error del hilo");
		}
	}
	
	public void commandAction(Command c, Displayable d){
		if(c.equals(atras)){
			pasarPantalla(new MenuPrincipal());
		}
		else if (c.equals(aceptar)){
			nombrePaciente = datosPaciente.getString();
			System.out.println(nombrePaciente);
			Thread t = new Thread(this);
			t.start();
		}
	}
	
}//fin de la clase ConsultaExpediente

}//fin del MIDlet

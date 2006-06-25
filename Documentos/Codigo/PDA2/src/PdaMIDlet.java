import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;

import java.util.Vector;

/**
 * 
 * @author María Alonso López
 *
 */

public class PdaMIDlet extends MIDlet {
	
	//atributos
	private PantallaInicio pi;//primera pantalla
	//displays
	private Display display;
	private Displayable sig;
	//botones:
	private Command salir = new Command("Salir",Command.EXIT,1);//boton esquina izquierda
	private Command aceptar = new Command("Aceptar",Command.OK,1);//boton esquina derecha
	private Command atras = new Command("Atrás",Command.EXIT,1);//boton esquina izquierda
	
	/**
	 * MÉTODO QUE LANZA LA EJECUCIÓN DEL MIDLET
	 */
	public void startApp() {
		pi = new PantallaInicio(sig);//primera pantalla
		display = Display.getDisplay(this);
		display.setCurrent(pi);
	}

	public void pauseApp() {
		//SUSPENDE LAS ACCIONES EN SEGUNDO PLANO Y LIBERA RECURSOS MIENTRAS EL MIDLET NO ESTÁ ACTIVO
				
	}

	public void destroyApp(boolean arg0){
		//DETIENE TODA ACTIVIDAD DEL MIDLET Y LIBERA LOS RECURSOS

	}
	
	/**
	 * 
	 * @param Displayable d
	 * 
	 * muestra en la pantalla del PDA una alerta en caso de que haya un error en el login y pasa a la siguiente pantalla "d"
	 * 
	 */
	public void errorLogin(Displayable d){
		Alert aviso = new Alert("El nombre de usuario o la clave personal no son correctas");
        aviso.setType(AlertType.ERROR);
        aviso.setTitle("ERROR");
        aviso.setString("El nombre de usuario o la clave personal no son correctas");
        aviso.setTimeout(Alert.FOREVER);
        display.setCurrent( aviso);
        System.out.println("El nombre de usuario o la clave personal no son correctas");
		
	}
	
	/**
	 * 
	 * @param Displayable d
	 * 
	 * muestra en la pantalla del PDA una alerta en caso de que haya un error de conexión y pasa a la siguiente pantalla "d"
	 * 
	 */
	public void errorConexion(Displayable d){
		Alert aviso = new Alert("Error en la conexion");
        aviso.setType(AlertType.ERROR);
        aviso.setTitle("ERROR");
        aviso.setString("Error en la conexion: inténtelo de nuevo");
        aviso.setTimeout(Alert.FOREVER);
        display.setCurrent( aviso);
        System.out.println("Error en la conexion");
	}
	
	/**
	 * 
	 * @param Displayable d
	 * 
	 * pone la pantalla "d" en el display del PDA
	 * 
	 */
	public void pasarPantalla(Displayable d){
		display = Display.getDisplay(this);
		display.setCurrent(d);
	}
	
	
	/**
	 * 
	 * @param Displayable d, int imp
	 * 
	 * muestra en la pantalla del PDA una alerta en caso informando de la impresora por la que se vana a imprimir los documentos
	 * solicitados y pasa a la siguiente pantalla "d"
	 * 
	 */
	public void infoImpresora(Displayable d, int imp){
		Alert infoImpresora = new Alert("Información sobre la impresora");
        infoImpresora.setType(AlertType.INFO);
        infoImpresora.setTitle("Información de impresión");
        infoImpresora.setString("Sus documentos se imprimirán en la impresora número: "+imp+" \n");
        infoImpresora.setTimeout(Alert.FOREVER);
        display.setCurrent(infoImpresora, d);
	}
	
	/**
	 * 
	 * @param Displayable d
	 * 
	 * muestra en la pantalla del PDA una alerta en caso de que haya un error en el proceso de imprimir y pasa a la siguiente pantalla "d"
	 * 
	 */
	public void errorImpresora(Displayable d){
		Alert errorImpresora = new Alert("Información sobre la impresora");
        errorImpresora.setType(AlertType.WARNING);
        errorImpresora.setTitle("Información de impresión");
        errorImpresora.setString("Sus documentos no se pueden imprimir en este momento.\nInténtelo más tarde.");
        errorImpresora.setTimeout(Alert.FOREVER);
        display.setCurrent(errorImpresora);
	}
	
	/**
	 * 
	 * @param Displayable d
	 * @param String exp
	 * @param String np
	 * 
	 * muestra por pantalla del PDA el expediente "exp" de "np" y pasa a la siguiente pantalla "d"
	 * 
	 */
	public void mostrarExpediente(Displayable d, String exp, String np){
		Alert  alertaExpediente = new Alert("Consulta de expediente");
        alertaExpediente.setType(AlertType.INFO);
        alertaExpediente.setTitle("Expediente de "+ np);
        alertaExpediente.setString(exp+" \n");
        alertaExpediente.setTimeout(Alert.FOREVER);
        display.setCurrent(alertaExpediente, d);
	}
	
	/**
	 * 
	 * @param Displayable d
	 * 
	 * muestra en la pantalla del PDA una alerta en caso de que haya un error en la consulta de un expediente y pasa a la siguiente pantalla "d"
	 * 
	 */
	public void errorExpediente(Displayable d){
		Alert aviso = new Alert("Error en la consulta del expediente");
        aviso.setType(AlertType.ERROR);
        aviso.setTitle("ERROR");
        aviso.setString("Error en la consulta del expediente");
        aviso.setTimeout(Alert.FOREVER);
        display.setCurrent(aviso, d);
	}

	/**
	 * clase que implementa la pantalla inicial (login) del PDA 
	 */
public class PantallaInicio extends Form implements CommandListener, Runnable{
	
	//atributos
	private TextField campoUsuario; //campo de texto para introducir el nombre de usuario
	private TextField campoClave; //campo de texto para introducir la clave
	private String clave;//clave que ha introducido el usuario
	private String nombre;//nombre que ha introducido el usuario
	Displayable sig;//siguiente pantalla despues de esta del login
	
	/**
	 * 
	 * @param Displayable _sig
	 * 
	 * constructora parametrizada de la clase
	 */
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
	}
	
	/**
	 * método de control de los eventos que se generan en la pantalla del MIDlet
	 */
	public void commandAction(Command c, Displayable d){
		if (c == aceptar){
			//recogemos los datos proporcionados por el usuario
			nombre = campoUsuario.getString();
			clave = campoClave.getString();
			System.out.println(nombre);
			System.out.println(clave);
			//llamamos a un nuevo hilo para que realice la tarea de contrastar estos datos con la base de datos
			Thread t = new Thread(this);
			t.start();
		}
		else if(c.equals(salir)){//salimos de la aplicación
			System.out.println("estoy saliendo con el boton de salir");
			destroyApp(true);
			notifyDestroyed();
		}
	}
	
	/**
	 * método que realiza la tarea de contrastar los datos del usuario con la base de datos 
	 */
	public void run(){
		try{
			//creamos un cliente XML-RPC para llamar remotamente al método de login 
			org.kxmlrpc.XmlRpcClient xrc = new org.kxmlrpc.XmlRpcClient("http://localhost:8080"); 
			//vector con los parámetros que se le pasan a la función que llamamos remotamente
			Vector params = new Vector();
			params.addElement("personal");
			params.addElement(new String(nombre));
	        params.addElement(new String(clave));
	        try{
	        	//llamamos remotamente al login de la base de datos
	        	String respuestaLogIn = (String) xrc.execute("bd.login", params);
	        	System.out.println(respuestaLogIn);
	        	if(respuestaLogIn.equals("no"))//los datos no son correctos 
	        		errorLogin(sig);
	        	else//datos correctos
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

	/**
	 * clase que implementa el menú principal de la aplicación 
	 */
public class MenuPrincipal extends List implements CommandListener{
	
	/**
	 * 
	 * constructora no parametrizda
	 *
	 */
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
	
	/**
	 * método de control de los eventos que se generan en la pantalla del MIDlet
	 */
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
			  	case 2 ://salimos de la aplicación
			  		System.out.println("quiero salir");
			  		destroyApp(true);
			  		notifyDestroyed();
			  		break;
			 }
		}
		else if(c.equals(salir)){//salimos de la aplicación
			System.out.println("estoy saliendo con el boton de salir");
			destroyApp(true);
			notifyDestroyed();
		}
	} 	
}//fin de la clase MenuPrincipal


	/**
	 * clase que imprementa la pantalla de solicitud de impresión
	 */
public class MenuImprimir extends Form implements CommandListener, Runnable{
	
	//atributos
	TextField datosPaciente;//campo de texto para recoger el nombre del paciente del que se solicita un documento impreso
	ChoiceGroup documentoAImprimir;//lista de opciones de los documentos que se pueden imprimir
	String nombrePaciente;//nombre del paciente introducido por el usuario
	int opcionImprimir;//documento que queremos imprimir (opción de la lista)
	Displayable sig ;//siguiente pantalla después de esta en la que estamos
	
	/**
	 * 
	 * @param Displayable _sig
	 * 
	 * constructora parametrizada de la clase
	 * 
	 */
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
	
	/**
	 * método que implementa la petición de la impresión
	 */
	public void run(){
		try{
//			creamos un cliente XML-RPC para llamar remotamente al método que imprime
			org.kxmlrpc.XmlRpcClient xrc = new org.kxmlrpc.XmlRpcClient("http://localhost:8080"); 
//			vector con los parámetros que se le pasan a la función que llamamos remotamente
			Vector params = new Vector();
			params.addElement(nombrePaciente);
			params.addElement(new Integer(opcionImprimir));
			try{
				//hacemos la llamada remota
				Integer impresora = (Integer)xrc.execute("servidor.imprimir", params);
				int numeroImpresora = impresora.intValue();//obtenemos la impresora por la que se imprimiran los documentos
				if(numeroImpresora != -1){//no es posible imprimir
					infoImpresora(new MenuPrincipal(), numeroImpresora);
				}
				else{
					errorImpresora(sig);//informamos de la impresora por la que se realizará la petición
				}
			} catch (Exception exception) {
				errorConexion(sig);
			}
		}catch(Exception e){
			System.err.print("error del hilo");
		}
	}
	
	/**
	 * método de control de los eventos que se generan en la pantalla del MIDlet
	 */
	public void commandAction(Command c, Displayable d){
		if(c.equals(atras)){//pasar a la pantalla anterior
			pasarPantalla(new MenuPrincipal());
		}
		else if (c.equals(aceptar)){
			//recogemos los datos introducidos por el usuario
			nombrePaciente = datosPaciente.getString();
			opcionImprimir = this.documentoAImprimir.getSelectedIndex();
			System.out.println(nombrePaciente+" "+opcionImprimir);
			//encargamos a un nuevo hilo la tarea de mandar a imprimir el documento
			Thread t = new Thread(this);
			t.start();
		}
	}
	
}//fin de la clase MenuImprimir


/**
 * clase que implementa la pantalla de solicitud de consulta de expediente
 */
public class ConsultaExpediente extends Form implements CommandListener, Runnable{
	
	//atributos
	TextField datosPaciente;//campo de texto para recoger el nombre del paciente del que se solicita la consulta de expediente
	String nombrePaciente;//nombre del paciente introducido por el usuario
	Displayable sig ;//siguiente pantalla después de esta en la que estamos
	
	/**
	 * 
	 * @param Displayable _sig
	 * 
	 * constructora parametrizada de la clase
	 * 
	 */
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
	
	/**
	 * método que implementa la tarea de consultar el expediente en la base de datos
	 */
	public void run(){
		try{
			//creamos un cliente XML-RPC para llamar remotamente al método que consulta expedientes en la base de datos
			org.kxmlrpc.XmlRpcClient xrc = new org.kxmlrpc.XmlRpcClient("http://localhost:8080"); 
			//vector con los parámetros que se le pasan a la función que llamamos remotamente
			Vector params = new Vector();
			params.addElement(nombrePaciente);
			try{
//				hacemos la llamada remota
				String expediente = (String)xrc.execute("servidor.consultaExpediente", params);
				if (expediente.equals(""))//no existe el expediente
					errorExpediente(new MenuPrincipal());
				else//sino lo mostramos por pantalla
					mostrarExpediente(sig, expediente, nombrePaciente);
			} catch (Exception exception) {
				errorConexion(new MenuPrincipal());
			}
		}catch(Exception e){
			System.err.print("error del hilo");
		}
	}
	
	/**
	 * método de control de los eventos que se generan en la pantalla del MIDlet
	 */
	public void commandAction(Command c, Displayable d){
		if(c.equals(atras)){//pasar a la pantalla anterior
			pasarPantalla(new MenuPrincipal());
		}
		else if (c.equals(aceptar)){
//			recogemos los datos introducidos por el usuario
			nombrePaciente = datosPaciente.getString();
			System.out.println(nombrePaciente);
//			encargamos a un nuevo hilo la tarea de obtener el expediente de la base de datos
			Thread t = new Thread(this);
			t.start();
		}
	}
	
}//fin de la clase ConsultaExpediente

}//fin del MIDlet

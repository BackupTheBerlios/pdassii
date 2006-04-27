package pruebaXML_mysql;

//import ConsultaTablaException;
//import CreaTablaException;
//import EliminaTablaException;
//import EliminaValorTablaException;
//import InsertaValorTablaException;
//import ModificaValorTablaException;

import java.io.*;
//import java.util.*;
import java.sql.*;
import java.security.*;

//Clases de las posibles excepciones que se pueden dar en la base de datos

class CreaTablaException extends RuntimeException
{ /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CreaTablaException() { super(); }
	public CreaTablaException(String mensaje) { super(mensaje); }
}
class EliminaTablaException extends RuntimeException
{ /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EliminaTablaException() { super(); }
	public EliminaTablaException(String mensaje) { super(mensaje); }
}
class EliminaValorTablaException extends RuntimeException
{ 	
	private static final long serialVersionUID = 1L;
	public EliminaValorTablaException() { super(); }
	public EliminaValorTablaException(String mensaje) { super(mensaje); }
}
class ModificaValorTablaException extends RuntimeException
{ 
	private static final long serialVersionUID = 1L;
	public ModificaValorTablaException() { super(); }
	public ModificaValorTablaException(String mensaje) { super(mensaje); }
}
class InsertaValorTablaException extends RuntimeException
{ 
	private static final long serialVersionUID = 1L;
	public InsertaValorTablaException() { super(); }
	public InsertaValorTablaException(String mensaje) { super(mensaje); }
}
class ConsultaTablaException extends RuntimeException
{ 
	private static final long serialVersionUID = 1L;
	public ConsultaTablaException() { super(); }
	public ConsultaTablaException(String mensaje) { super(mensaje); }
}

//Clase que implementa la base de datos del hospital 

public class BaseDatos{

    /*
    La base de datos creada por consola sql:
       - mysql> create database baseClaves;
         Query OK, 1 row affected (0.00 sec)

       - privilegios concedidos por consola: grant all privileges on baseClaves.* to 'alumno'@'%' identified by 'alumno';
    */
    private String nombreBaseDatos = "bdpersonal";
    private String usuario = "maria";
    private String password = "1829";

    
    //Constructora
    public BaseDatos(String nBd, String usr, String pw){
	nombreBaseDatos = nBd;
	usuario = usr;
	password = pw;
    }
    
    
    //Método para codificar claves. Para ello utiliza un algoritmo el Java "SHA-1"
    public static String codificaClave(String clave){
	
	try {	
	    MessageDigest md = MessageDigest.getInstance("SHA-1");
	    byte [] cod = md.digest(clave.getBytes());
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < cod.length; i++) {
		//System.out.print(cod[i]+",");
		sb.append(Integer.toHexString((char)cod[i]));
		//sb.append(',');


	    }

	    //System.out.println(" String a codificar :"+clave+":");
	    //System.out.println(" Codificacion:"+sb.toString()+":"+cod.length);
	    return sb.toString();      
		
	} catch (NoSuchAlgorithmException e) {
	    //esto no va a pasar pq el SHA ya lo conocemos
	    System.out.println("Error codificando la clave: "+e.getMessage());
	    return null;
	}
	//return clave;
    }
    
    //Métodos generales para la base de datos
    
    private Connection abrirConexion() throws ClassNotFoundException
					      ,InstantiationException
					      ,IllegalAccessException
					      ,SQLException{
	String urlBD = "jdbc:mysql://localhost/"+nombreBaseDatos+"?user="+usuario+"&password="+password;
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	Connection con = DriverManager.getConnection(urlBD);
	
	return con;      
    }

    private void eliminarTabla(String nombreTabla){
        try {
  	  Connection con = abrirConexion();
  	  Statement stmt = con.createStatement();
  	  stmt.executeUpdate("DROP TABLE "+nombreTabla);
  	  stmt.close();
  	  con.close();
  	  
          } catch(Exception e) {
  	    //e.printStackTrace();
  	    throw new EliminaTablaException("Hubo un error eliminando la tabla: "+e.getMessage());
  	    //System.out.println("Hubo un error eliminando la tabla");
          }	
      }
    
//Tablas correspondientes a los médicos: tienen como entradas el nombre de usuario y la clave
    
    private void crearTablaMedicos(String nombreTabla){
      try {
	  Connection con = abrirConexion();
	  Statement stmt = con.createStatement();
	  /*
	  varchar(80) pq la codificacion me da 20 digitos hexadecimales, cada uno como maximo da 4 caracteres
	  */
	  stmt.executeUpdate("CREATE TABLE "+nombreTabla+"(USUARIO VARCHAR(32) NOT NULL, CLAVE VARCHAR(41), PRIMARY KEY (USUARIO))");
	  stmt.close();
	  con.close();
	  
        } catch(Exception e) {
	     //e.printStackTrace();
	    throw new CreaTablaException("Hubo un error creando la tabla: "+e.getMessage());
	    // System.out.println("Hubo un error creando la tabla");
        }	
    }
    
    private void eliminarValorTablaM(String nombreTabla, String usuario){
        try {
  	  Connection con = abrirConexion();
  	  Statement stmt = con.createStatement();
  	  stmt.executeUpdate("DELETE FROM "+nombreTabla+" WHERE USUARIO=\'"+usuario+"\'");
  	  stmt.close();
  	  con.close();
  	  
          } catch(Exception e) {
  	    throw new EliminaValorTablaException("Hubo un error eliminando una entrada de la tabla: "+e.getMessage());
          }	
      }
      
   private void modificarValorTablaM(String nombreTabla, String usuario, String clave){
        try {
  	  Connection con = abrirConexion();
  	  Statement stmt = con.createStatement();
  	  stmt.executeUpdate("UPDATE "+nombreTabla+" SET CLAVE=PASSWORD(\'"+clave+"\') WHERE USUARIO=\'"+usuario+"\'");
  	  stmt.close();
  	  con.close();
  	  
          } catch(Exception e) {
  	    //e.printStackTrace();
  	     throw new ModificaValorTablaException("Hubo un error modificando una entrada de la tabla: "+e.getMessage());
  	     //System.out.println("Hubo un error modificando una entrada de la tabla");
          }	
      }
      
      private void insertarValorTablaM(String nombreTabla, String usuario, String clave){
        try {
  	  Connection con = abrirConexion();
  	  Statement stmt = con.createStatement();
  	  stmt.executeUpdate("INSERT INTO "+nombreTabla+" VALUES(\'"+usuario+"\',\'"+codificaClave(clave)+"\')");
  	  stmt.close();
  	  con.close();
  	  
          } catch(Exception e) {
  	    throw new InsertaValorTablaException("Hubo un error insertando una entrada en la tabla: "+e.getMessage());
          }	
      }

      private String consultarTablaMedicos(String nombreTabla){
  	String retorno="USUARIO\tCLAVE\n-----\t-----\n";
        try {
  	  Connection con = abrirConexion();
  	  Statement stmt = con.createStatement();
  	  ResultSet resultado = stmt.executeQuery("SELECT * FROM "+nombreTabla);
  	  int numCols = resultado.getMetaData().getColumnCount();
  	  while(resultado.next()) {
  	      for (int col=1; col<=numCols; col++ ) {
  		  retorno += resultado.getString(col)+"\t";
  	      }
  	      retorno+="\n";
  	    };
  	  
  	  resultado.close();
  	  stmt.close();
  	  con.close();
  	  
          } catch(Exception e) {
  	    //e.printStackTrace();
  	     throw new ConsultaTablaException("Hubo un error consultando la tabla: "+e.getMessage());
  	    
          }	
  	
        return retorno;
      }
      
      public String consultarClave(String nombreTabla, String nombreUsuario){
    		String retorno="CLAVE\n-----\n";
    		try {
    		  Connection con = abrirConexion();
    		  Statement stmt = con.createStatement();
    		  ResultSet resultado = stmt.executeQuery("SELECT CLAVE FROM "+nombreTabla+" WHERE USUARIO = '"+nombreUsuario+"'");
    		  int numCol = resultado.findColumn("CLAVE");
    		  while(resultado.next()) {
    		      retorno += resultado.getString(numCol);
    		      retorno+="\n";
    		    };
    		  
    		  resultado.close();
    		  stmt.close();
    		  con.close();
    		  
    	        } catch(Exception e) {
    		    //e.printStackTrace();
    		     throw new ConsultaTablaException("Hubo un error consultando la tabla: "+e.getMessage());
    		    
    	        }	
    		
    	      return retorno;
    	    }  

      private String consultarNombre(String nombreTabla, String clave){
    		String retorno="USUARIO\n-----\n";
    		try {
    		  Connection con = abrirConexion();
    		  Statement stmt = con.createStatement();
    		  //String claveCodif = codificaClave(clave);
    		  ResultSet resultado = stmt.executeQuery("SELECT USUARIO FROM "+nombreTabla+" WHERE CLAVE = \'"+codificaClave(clave)+"\'");
    		  int numCol = resultado.findColumn("USUARIO");
    		  while(resultado.next()) {
    		      retorno += resultado.getString(numCol);
    		      retorno+="\n";
    		    };
    		  
    		  resultado.close();
    		  stmt.close();
    		  con.close();
    		  
    	        } catch(Exception e) {
    		    //e.printStackTrace();
    		     throw new ConsultaTablaException("Hubo un error consultando la tabla: "+e.getMessage());
    		    
    	        }	
    		
    	      return retorno;
    	    }
      
      public String consultarClave2(String nombreTabla, String nombreUsuario) throws ConsultaTablaException
      {
  	String retorno = "";
  	try {
  	  Connection con = abrirConexion();
  	  Statement stmt = con.createStatement();
  	  ResultSet resultado = stmt.executeQuery("SELECT CLAVE FROM "+nombreTabla+" WHERE USUARIO = \'"+nombreUsuario+"\'");
  	  int numCol = resultado.findColumn("CLAVE");
  	  //como el nombre de usuario es clave primaria se que solo me devolvera un valor
  	  resultado.next();
  	  retorno = resultado.getString(numCol);
  	    
  	  resultado.close();
  	  stmt.close();
  	  con.close();
  	  
          } catch(Exception e) {
  	    //e.printStackTrace();
  	     throw new ConsultaTablaException("Hubo un error consultando la tabla: "+e.getMessage());
  	    
          }	
  	
        return retorno;
      }
      
      public String login(String nombreTabla, String nombreUsuario, String clave) throws ConsultaTablaException
      {
  	String retorno = "";
  	try {
  	  Connection con = abrirConexion();
  	  Statement stmt = con.createStatement();
  	  ResultSet resultado = stmt.executeQuery("SELECT CLAVE FROM "+nombreTabla+" WHERE USUARIO = \'"+nombreUsuario+"\'");
  	  int numCol = resultado.findColumn("CLAVE");
  	  //como el nombre de usuario es clave primaria se que solo me devolvera un valor
  	  resultado.next();
  	  retorno = resultado.getString(numCol);
  	    
  	  resultado.close();
  	  stmt.close();
  	  con.close();
  	  
  	  if(retorno.equals(codificaClave(clave))) return "si";
  	  else return "no";
  	  
       } catch(Exception e) {
  	    //e.printStackTrace();
  	     throw new ConsultaTablaException("Hubo un error consultando la tabla: "+e.getMessage());
  	    
        }	
      }
      
//Tablas correspondientes a los pacientes: tienen como entradas el nombre del paciente y las rutas donde se encuentran almacenados sus expedientes y
//los ultimos analisis
    
    private void crearTablaPacientes (String nombreTabla){
        try {
  	  Connection con = abrirConexion();
  	  Statement stmt = con.createStatement();
  	  /*
  	  varchar(80) pq la codificacion me da 20 digitos hexadecimales, cada uno como maximo da 4 caracteres
  	  */
  	  stmt.executeUpdate("CREATE TABLE "+nombreTabla+"(USUARIO VARCHAR(32) NOT NULL, EXPEDIENTE VARCHAR(80), ULTIMOS_ANALISIS VARCHAR(80))");
  	  stmt.close();
  	  con.close();
  	  
          } catch(Exception e) {
  	    throw new CreaTablaException("Hubo un error creando la tabla: "+e.getMessage());
          }	
      }
    
    private void eliminarExpedienteTablaP(String nombreTabla, String expediente){
      try {
	  Connection con = abrirConexion();
	  Statement stmt = con.createStatement();
	  stmt.executeUpdate("DELETE FROM "+nombreTabla+" WHERE EXPEDIENTE=\'"+expediente+"\'");
	  stmt.close();
	  con.close();
	  
        } catch(Exception e) {
	    throw new EliminaValorTablaException("Hubo un error eliminando una entrada de la tabla: "+e.getMessage());
        }	
    }
    
    private void eliminarAnalisisTablaP(String nombreTabla, String analisis){
        try {
  	  Connection con = abrirConexion();
  	  Statement stmt = con.createStatement();
  	  stmt.executeUpdate("DELETE FROM "+nombreTabla+" WHERE ULTIMOS_ANALISIS=\'"+analisis+"\'");
  	  stmt.close();
  	  con.close();
  	  
          } catch(Exception e) {
  	    throw new EliminaValorTablaException("Hubo un error eliminando una entrada de la tabla: "+e.getMessage());
          }	
      }
    
    private void modificarExpedienteTabla(String nombreTabla, String usuario, String expediente){
      try {
	  Connection con = abrirConexion();
	  Statement stmt = con.createStatement();
	  stmt.executeUpdate("UPDATE "+nombreTabla+" SET EXPEDIENTE=(\'"+expediente+"\') WHERE USUARIO=\'"+usuario+"\'");
	  stmt.close();
	  con.close();
	  
        } catch(Exception e) {
	     throw new ModificaValorTablaException("Hubo un error modificando una entrada de la tabla: "+e.getMessage());
        }	
    }
    
    private void modificarAnalisisTabla(String nombreTabla, String usuario, String analisis){
        try {
  	  Connection con = abrirConexion();
  	  Statement stmt = con.createStatement();
  	  stmt.executeUpdate("UPDATE "+nombreTabla+" SET ULTIMOS_ANALISIS=(\'"+analisis+"\') WHERE USUARIO=\'"+usuario+"\'");
  	  stmt.close();
  	  con.close();
  	  
          } catch(Exception e) {
  	    //e.printStackTrace();
  	     throw new ModificaValorTablaException("Hubo un error modificando una entrada de la tabla: "+e.getMessage());
  	     //System.out.println("Hubo un error modificando una entrada de la tabla");
          }	
      }
    
    private void insertarValoresTablaP(String nombreTabla, String usuario, String expediente, String analisis){
      try {
	  Connection con = abrirConexion();
	  Statement stmt = con.createStatement();
	  stmt.executeUpdate("INSERT INTO "+nombreTabla+" VALUES(\'"+usuario+"\',\'"+expediente+"\', \'"+analisis+"\')");
	  stmt.close();
	  con.close();
	  
        } catch(Exception e) {
	    throw new InsertaValorTablaException("Hubo un error insertando una entrada en la tabla: "+e.getMessage());
        }	
    }

    private String consultarTablaPacientes(String nombreTabla){
	String retorno="USUARIO\tEXPEDIENTE\tULTIMOS_ANALISIS\n-----\t-----\n";
      try {
	  Connection con = abrirConexion();
	  Statement stmt = con.createStatement();
	  ResultSet resultado = stmt.executeQuery("SELECT * FROM "+nombreTabla);
	  int numCols = resultado.getMetaData().getColumnCount();
	  while(resultado.next()) {
	      for (int col=1; col<=numCols; col++ ) {
		  retorno += resultado.getString(col)+"\t";
	      }
	      retorno+="\n";
	    };
	  
	  resultado.close();
	  stmt.close();
	  con.close();
	  
        } catch(Exception e) {
	    //e.printStackTrace();
	     throw new ConsultaTablaException("Hubo un error consultando la tabla: "+e.getMessage());
	    
        }	
	
      return retorno;
    }
    
  
    private String consultarTablaAlfabetico(String nombreTabla, String nombreUsuario){
      String retorno="USUARIO\tCLAVE\n-----\t-----\n";
      try {
	  Connection con = abrirConexion();
	  Statement stmt = con.createStatement();
	  ResultSet resultado = stmt.executeQuery("SELECT * FROM "+nombreTabla+" WHERE USUARIO >= '"+nombreUsuario+"'");
	  int numCols = resultado.getMetaData().getColumnCount();
	  while(resultado.next()) {
	      for (int col=1; col<=numCols; col++ ) {
		  retorno += resultado.getString(col)+"\t";
	      }
	      retorno+="\n";
	    };
	  
	  resultado.close();
	  stmt.close();
	  con.close();
	  
        } catch(Exception e) {
	    //e.printStackTrace();
	     throw new ConsultaTablaException("Hubo un error consultando la tabla: "+e.getMessage());
	    
        }	
	
      return retorno;
    }    
    
    
    
    public String consultaExpediente (String nombreTabla, String nombreP){
    	/*String salida = "Estoy consultando el expediente de "+ nombreP;
    	return salida;*/
    	String retorno="";
		try {
		  Connection con = abrirConexion();
		  Statement stmt = con.createStatement();
		  ResultSet resultado = stmt.executeQuery("SELECT EXPEDIENTE FROM "+nombreTabla+" WHERE USUARIO = '"+nombreP+"'");
		  int numCol = resultado.findColumn("EXPEDIENTE");
		  while(resultado.next()) {
		      retorno += resultado.getString(numCol);
		      retorno+="\n";
		    };
		  
		  resultado.close();
		  stmt.close();
		  con.close();
		  
	        } catch(Exception e) {
		    //e.printStackTrace();
		     throw new ConsultaTablaException("Hubo un error consultando la tabla: "+e.getMessage());
		    
	        }	
		
	      return retorno;
    }
    
    public String consultaAnalisis (String nombreTabla, String nombreP){
    	String retorno="";
		try {
		  Connection con = abrirConexion();
		  Statement stmt = con.createStatement();
		  ResultSet resultado = stmt.executeQuery("SELECT ULTIMOS_ANALISIS FROM "+nombreTabla+" WHERE USUARIO = '"+nombreP+"'");
		  int numCol = resultado.findColumn("ULTIMOS_ANALISIS");
		  while(resultado.next()) {
		      retorno += resultado.getString(numCol);
		      retorno+="\n";
		    };
		  
		  resultado.close();
		  stmt.close();
		  con.close();
		  
	        } catch(Exception e) {
		    //e.printStackTrace();
		     throw new ConsultaTablaException("Hubo un error consultando la tabla: "+e.getMessage());
		    
	        }	
		
	      return retorno;
    }
    
    
    //Procedimientos para la interacción con el usuario
    
    private static void muestraMenuConsultasM(){
	String str = "";
	
	str+="\tConsultas disponibles\n";
	str+="\t\t1. Mostrar el contenido de toda una tabla\n";
	str+="\t\t2. Buscar la clave asociada a un usuario\n";
	str+="\t\t3. Buscar los usuarios asociados a una clave\n";
	str+="\t\t4. Buscar los datos de los usuarios a partir de un nombre (orden alfabetico)\n";
	
	System.out.println(str);
    }
    
    private static void muestraMenuConsultasP(){
    	String str = "";
    	
    	str+="\tConsultas disponibles\n";
    	str+="\t\t1. Mostrar el contenido de toda una tabla\n";
    	str+="\t\t2. Buscar el expediente de un paciente\n";
    	str+="\t\t3. Buscar los analisis de un paciente\n";
    	System.out.println(str);
        }
    
    private static int MenuConsultasM(){
	//leer opciones del menu
	boolean ok = false;
	int retorno=0;
	String msjPedida = "\tElije una consulta";
	String msjError = "Las opciones van de 1 a 4";
	
	while (!ok) {
	    muestraMenuConsultasM();  
	    retorno = leerEntero(msjPedida);
	    ok = (retorno >= 1) && (retorno<=5);
	    if (!ok) {
		System.out.println(msjError);
	    }
	}
       
	return retorno;
    }
    
    private static int MenuConsultasP(){
    	//leer opciones del menu
    	boolean ok = false;
    	int retorno=0;
    	String msjPedida = "\tElije una consulta";
    	String msjError = "Las opciones van de 1 a 3";
    	
    	while (!ok) {
    	    muestraMenuConsultasP();  
    	    retorno = leerEntero(msjPedida);
    	    ok = (retorno >= 1) && (retorno<=3);
    	    if (!ok) {
    		System.out.println(msjError);
    	    }
    	}
           
    	return retorno;
        }
    
    private static void muestraMenu(){
	String str = "";
	
	str +="Menu\n";
	//str +="\t0. Salir\n";
	str +="\t1. Crear una nueva tabla\n";
	str +="\t2. Eliminar una tabla\n";
	str +="\t3. Insertar una entrada en una tabla\n";
	str +="\t4. Borrar una entrada de la tabla\n";
	str +="\t5. Modificar una entrada de la tabla\n";
	str +="\t6. Buscar datos en las tablas\n";
     
	System.out.println(str);
    }
    
    
     private static int Menu(){
    	 //leer opciones del menu
    	 boolean ok2 = false;
    	 int retorno2=0;
    	 String msjPedida = "Elije una opción";
    	 String msjError = "Las opciones van de 1 a 6";
    	 while (!ok2) {
	    muestraMenu();  
	    retorno2 = leerEntero(msjPedida);
	    ok2 = (retorno2 >= 0) && (retorno2<=6);
	    if (!ok2) {
		System.out.println(msjError);
	    }
	}
       
	return retorno2;
    }
    
     
    private static String leerString(String s)
    {
    	BufferedReader teclado=new BufferedReader(new InputStreamReader(System.in));	
	    String retorno = "";
     	
	try {
	    System.out.println(s);
	    retorno =teclado.readLine();
	}
	catch (IOException e) {System.out.println("Error de E/S");  }
		
	return retorno;
    }
    
    private static int leerEntero(String s)
    { //pedir usando s como mensaje,  y leer un entero por teclado 
    
	BufferedReader teclado=new BufferedReader(new InputStreamReader(System.in));	
	int retorno = 0;
	
	boolean error = false;
	do{
	    try {
		System.out.println(s);
		error = false;
		retorno = Integer.valueOf(teclado.readLine()).intValue();
	    }
	    catch (NumberFormatException e) {
		error = true;
		System.out.println("Error en el formato");
	       }
	    catch (IOException e) {System.out.println("Error de E/S");  }
	}while (error);
	
	return retorno;
    }
    
	public static int eligeTabla(){
		String mOp = "Elige:\n0. Salir.\n1. Tabla de médicos.\n2. Tabla de pacientes.\n";
	    return leerEntero(mOp);
    }
	
	public static int menuBorrarPacientes(){
		String mOp = "Elige:\n1. Borrar expediente.\n2. Borrar ultimos analisis.\n";
	    return leerEntero(mOp);
	}
	
	public static int menuModificarPacientes(){
		String mOp = "Elige:\n1. Modificar expediente.\n2. Modificar ultimos analisis.\n";
	    return leerEntero(mOp);
	}
	
    public static void main(String [] args){
    	
	//BaseDatos bd = new BaseDatos("baseClaves", "alumno", "alumno");
    BaseDatos bd = new BaseDatos("bdpersonal", "maria", "1829");
    
    int tipoTabla = eligeTabla();
	
    while (tipoTabla != 0){
    if(tipoTabla == 1){
	int opcion= Menu(); 
	String tabla, usuario, clave= "",consulta;

	    try {
		
		switch (opcion) {
		case 0:
		    break;
		case 1: bd.crearTablaMedicos(leerString("Introduce el nombre de la tabla a crear"));
		        opcion=eligeTabla();		
			break;
		case 2: bd.eliminarTabla(leerString("Introduce el nombre de la tabla a eliminar"));
		        opcion=eligeTabla();
			break;
		case 3: tabla = leerString("¿En que tabla?");
	                usuario = leerString("¿Que usuario?");
			clave = leerString("¿Que clave?");
			bd.insertarValorTablaM(tabla, usuario, clave);
			opcion=eligeTabla();
			break;
		case 4: tabla = leerString("¿En que tabla?");
	                usuario = leerString("¿Que usuario?");
			bd.eliminarValorTablaM(tabla, usuario);
			opcion=eligeTabla();
			break;
		    
		case 5: tabla = leerString("¿En que tabla?");
		        usuario = leerString("¿Que usuario?");
			clave = leerString("¿Que clave?");
			bd.modificarValorTablaM(tabla, usuario, clave);
			opcion=eligeTabla();
			break;
		    
		case 6: opcion = MenuConsultasM();
		        switch (opcion) {
			case 1: tabla = leerString("¿En que tabla?");
			        consulta = bd.consultarTablaMedicos(tabla);
				System.out.println("Resultado de la consulta:\n"+consulta);
				break;
			case 2: tabla = leerString("¿En que tabla?");
			        usuario = leerString("¿Que usuario?");
			        consulta = bd.consultarClave(tabla, usuario);
				System.out.println("Resultado de la consulta:\n"+consulta);
				break;
			case 3: tabla = leerString("¿En que tabla?");
			        clave = leerString("¿Que clave?");
				consulta = bd.consultarNombre(tabla, clave);
				System.out.println("Resultado de la consulta:\n"+consulta);
				break;
			case 4: tabla = leerString("¿En que tabla?");
			        usuario = leerString("¿Que usuario?");
				consulta = bd.consultarTablaAlfabetico(tabla, usuario);
				System.out.println("Resultado de la consulta:\n"+consulta);
				break;
			case 5: System.out.println("Resultado de la consulta:\n"+ bd.consultarClave2("claves", "pepe"));

				    break;
			default:
			    break;
			}
			
			opcion=eligeTabla();
			break;
		default: opcion=eligeTabla();
		         break;
		}
	    }
	     catch (Exception e) {
		 System.out.println(e.getMessage());
		 opcion=eligeTabla();
	    }

	    
	//}
  //}
    } else {
    	int opcion= Menu(); 
    	String tabla, usuario, expediente = "", analisis = "" ,consulta;
    	
    	    try {
    		
    		switch (opcion) {
    		case 0:
    		    break;
    		case 1: bd.crearTablaPacientes(leerString("Introduce el nombre de la tabla a crear"));
    		        opcion=eligeTabla();		
    			break;
    		case 2: bd.eliminarTabla(leerString("Introduce el nombre de la tabla a eliminar"));
    		        opcion=eligeTabla();
    			break;
    		case 3: tabla = leerString("¿En que tabla?");
    	            usuario = leerString("¿Que usuario?");
    	            expediente = leerString("¿Que expediente?");
    	            analisis = leerString("¿Que ultimos analisis?");
    	            bd.insertarValoresTablaP(tabla, usuario, expediente, analisis);
    	            opcion=eligeTabla();
    	            break;
    		case 4: int borrar = menuBorrarPacientes();
    				tabla = leerString("¿En que tabla?");
    				usuario = leerString("¿Que usuario?");
    	            if (borrar == 1){
    	            	expediente = leerString("¿Que expediente?");
    	            	bd.eliminarExpedienteTablaP(tabla, expediente);
    	            }
    	            else{
    	            	analisis = leerString("¿Que ultimos analisis?");
    	            	bd.eliminarAnalisisTablaP(tabla, usuario);
    	            }
    	            opcion=eligeTabla();
    	            break;   		    
    		case 5: int modificar = menuModificarPacientes();
    				tabla = leerString("¿En que tabla?");
    				usuario = leerString("¿Que usuario?");
    				if (modificar == 1){
    					expediente = leerString("¿Que expediente?");
    					bd.modificarExpedienteTabla(tabla, usuario, expediente);
    				}
    				else{
    					analisis = leerString("¿Que ultimos analisis?");
    					bd.modificarAnalisisTabla(tabla, usuario, analisis);
    				}
    				opcion=eligeTabla();
    				break;    		    
    		case 6: opcion = MenuConsultasP();
    		        switch (opcion) {
    			case 1: tabla = leerString("¿En que tabla?");
    			        consulta = bd.consultarTablaPacientes(tabla);
    			        System.out.println("Resultado de la consulta:\n"+consulta);
    			        break;
    			case 2: tabla = leerString("¿En que tabla?");
    			        usuario = leerString("¿Que usuario?");
    			        consulta = bd.consultaExpediente(tabla, usuario);
    				System.out.println("Resultado de la consulta:\n"+consulta);
    				break;
    			case 3: tabla = leerString("¿En que tabla?");
    					usuario = leerString("¿Que usuario?");
    					consulta = bd.consultaAnalisis(tabla, usuario);
    					System.out.println("Resultado de la consulta:\n"+consulta);
    					break;
    			default:
    			    break;
    			}
    			
    			opcion=eligeTabla();
    			break;
    		default: opcion=eligeTabla();
    		         break;
    		}
    	    }
    	     catch (Exception e) {
    		 System.out.println(e.getMessage());
    		 opcion=eligeTabla();
    	    }

    	    
    	}
      }
    }
//}
}

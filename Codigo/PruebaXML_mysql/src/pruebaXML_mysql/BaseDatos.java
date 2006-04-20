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

    public BaseDatos(String nBd, String usr, String pw){
	nombreBaseDatos = nBd;
	usuario = usr;
	password = pw;
    }
     
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
    private Connection abrirConexion() throws ClassNotFoundException
					      ,InstantiationException
					      ,IllegalAccessException
					      ,SQLException{
	String urlBD = "jdbc:mysql://localhost/"+nombreBaseDatos+"?user="+usuario+"&password="+password;
	Class.forName("com.mysql.jdbc.Driver").newInstance();
	Connection con = DriverManager.getConnection(urlBD);
	
	return con;      
    }

    
    private void crearTablaClaves(String nombreTabla){
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
    
    private void eliminarTablaClaves(String nombreTabla){
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
    
    private void eliminarValorTabla(String nombreTabla, String usuario){
      try {
	  Connection con = abrirConexion();
	  Statement stmt = con.createStatement();
	  //String dest = "DELETE FROM "+nombreTabla+" WHERE USUARIO=\'"+usuario+"\'";
	  //System.out.println(dest);
	  stmt.executeUpdate("DELETE FROM "+nombreTabla+" WHERE USUARIO=\'"+usuario+"\'");
	  stmt.close();
	  con.close();
	  
        } catch(Exception e) {
	    //e.printStackTrace();
	    throw new EliminaValorTablaException("Hubo un error eliminando una entrada de la tabla: "+e.getMessage());
	    //System.out.println("Hubo un error eliminando una entrada de la tabla");
        }	
    }
    
    private void modificarValorTabla(String nombreTabla, String usuario, String clave){
      try {
	  Connection con = abrirConexion();
	  Statement stmt = con.createStatement();
	  //String claveCodif = codificaClave(clave);
	  //stmt.executeUpdate("UPDATE "+nombreTabla+" SET CLAVE=\'"+codificaClave(clave)+"\' WHERE USUARIO=\'"+usuario+"\'");
	  stmt.executeUpdate("UPDATE "+nombreTabla+" SET CLAVE=PASSWORD(\'"+clave+"\') WHERE USUARIO=\'"+usuario+"\'");
	  stmt.close();
	  con.close();
	  
        } catch(Exception e) {
	    //e.printStackTrace();
	     throw new ModificaValorTablaException("Hubo un error modificando una entrada de la tabla: "+e.getMessage());
	     //System.out.println("Hubo un error modificando una entrada de la tabla");
        }	
    }
    
    private void insertarValorTabla(String nombreTabla, String usuario, String clave){
      try {
	  Connection con = abrirConexion();
	  Statement stmt = con.createStatement();
	  //String claveCodif = codificaClave(clave);
	  // System.out.println(claveCodif);
	  stmt.executeUpdate("INSERT INTO "+nombreTabla+" VALUES(\'"+usuario+"\',\'"+codificaClave(clave)+"\')");
	  stmt.close();
	  con.close();
	  
        } catch(Exception e) {
	    throw new InsertaValorTablaException("Hubo un error insertando una entrada en la tabla: "+e.getMessage());
	    //e.printStackTrace();
	    //System.out.println("Hubo un error insertando en la tabla "+nombreTabla);
        }	
    }

    private String consultarTodaTabla(String nombreTabla){
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
    
    private static void muestraMenuConsultas(){
	String str = "";
	
	str+="\tConsultas disponibles\n";
	str+="\t\t1. Mostrar el contenido de toda una tabla\n";
	str+="\t\t2. Buscar la clave asociada a un usuario\n";
	str+="\t\t3. Buscar los usuarios asociados a una clave\n";
	str+="\t\t4. Buscar los datos de los usuarios a partir de un nombre (orden alfabetico)\n";
	
	System.out.println(str);
    }
    
    private static int MenuConsultas(){
	//leer opciones del menu
	boolean ok = false;
	int retorno=0;
	String msjPedida = "\tElije una consulta";
	String msjError = "Las opciones van de 1 a 4";
	
	while (!ok) {
	    muestraMenuConsultas();  
	    retorno = leerEntero(msjPedida);
	    ok = (retorno >= 1) && (retorno<=5);
	    if (!ok) {
		System.out.println(msjError);
	    }
	}
       
	return retorno;
    }
    
        
    private static void muestraMenuPrincipal(){
	String str = "";
	
	str +="Menu\n";
	str +="\t0. Salir\n";
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
	boolean ok = false;
	int retorno=0;
	String msjPedida = "Elije una opción";
	String msjError = "Las opciones van de 0 a 6";
	
	while (!ok) {
	    muestraMenuPrincipal();  
	    retorno = leerEntero(msjPedida);
	    ok = (retorno >= 0) && (retorno<=6);
	    if (!ok) {
		System.out.println(msjError);
	    }
	}
       
	return retorno;
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
    public static void main(String [] args){
    	
	//BaseDatos bd = new BaseDatos("baseClaves", "alumno", "alumno");
    BaseDatos bd = new BaseDatos("bdpersonal", "maria", "1829");
	
	int opcion= Menu(); 
	String tabla, usuario, clave= "",consulta;
	
	while (opcion!=0) {
	    try {
		
		switch (opcion) {
		case 0:
		    break;
		case 1: bd.crearTablaClaves(leerString("Introduce el nombre de la tabla a crear"));
		        opcion=Menu();		
			break;
		case 2: bd.eliminarTablaClaves(leerString("Introduce el nombre de la tabla a eliminar"));
		        opcion=Menu();
			break;
		case 3: tabla = leerString("¿En que tabla?");
	                usuario = leerString("¿Que usuario?");
			clave = leerString("¿Que clave?");
			bd.insertarValorTabla(tabla, usuario, clave);
			opcion=Menu();
			break;
		case 4: tabla = leerString("¿En que tabla?");
	                usuario = leerString("¿Que usuario?");
			bd.eliminarValorTabla(tabla, usuario);
			opcion=Menu();
			break;
		    
		case 5: tabla = leerString("¿En que tabla?");
		        usuario = leerString("¿Que usuario?");
			clave = leerString("¿Que clave?");
			bd.modificarValorTabla(tabla, usuario, clave);
			opcion=Menu();
			break;
		    
		case 6: opcion = MenuConsultas();
		        switch (opcion) {
			case 1: tabla = leerString("¿En que tabla?");
			        consulta = bd.consultarTodaTabla(tabla);
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
			
			opcion=Menu();
			break;
		default: opcion=Menu();
		         break;
		}
	    }
	     catch (Exception e) {
		 System.out.println(e.getMessage());
		 opcion=Menu();
	    }

	    
	}
  }
}


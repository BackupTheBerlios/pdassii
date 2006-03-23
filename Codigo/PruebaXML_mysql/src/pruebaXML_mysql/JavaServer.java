package pruebaXML_mysql;


import java.util.*; 
import org.apache.xmlrpc.*;

public class JavaServer {
	public JavaServer () {
        // Our handler is a regular Java object. It can have a
        // constructor and member variables in the ordinary fashion.
        // Public methods will be exposed to XML-RPC clients.
    }

    public Hashtable sumAndDifference (int x, int y) {
        Hashtable result = new Hashtable();
        result.put("sum", new Integer(x + y));
        result.put("difference", new Integer(x - y));
        return result;
    }

    public static void main (String [] args) {
        try {
            
            // Invoke me as <http://localhost:8080/RPC2>.
            WebServer server = new WebServer(8080);
            server.addHandler("pruebaxml", new JavaServer());
            server.addHandler("bd", new BaseDatos("bdpersonal", "maria", "1829"));
            server.start();

        } catch (Exception exception) {
            System.err.println("JavaServer: " + exception.toString());
        }
    }
}
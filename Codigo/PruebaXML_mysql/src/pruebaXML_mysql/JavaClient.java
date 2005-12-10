package pruebaXML_mysql;


import java.util.*;

import org.apache.xmlrpc.*;

public class JavaClient {
//	 The location of our server.
    private final static String server_url =
        "http://localhost:8080";

    public static void main (String [] args) {
        try {

            // Create an object to represent our server.
            XmlRpcClient server = new XmlRpcClient(server_url);
            System.out.println("he creado un cliente");
            // Build our parameter list.
            Vector params = new Vector();
            params.addElement(new Integer(5));
            params.addElement(new Integer(3));
            System.out.println("he añadido los parametros");

            // Call the server, and get our result.
            Hashtable result =
                (Hashtable) server.execute("pruebaxml.sumAndDifference", params);
            int sum = ((Integer) result.get("sum")).intValue();
            int difference = ((Integer) result.get("difference")).intValue();
            
           /*
            System.out.println("Voy a mandar los datos");
	        String result =(String) server.execute("pruebaxml.login", params);
	        System.out.println(result);
*/	        
            // Print out our result.
            System.out.println("Sum: " + Integer.toString(sum) +
                               ", Difference: " +
                               Integer.toString(difference));
	    
	        
        } catch (XmlRpcException exception) {
            System.err.println("JavaClient: XML-RPC Fault #" +
                               Integer.toString(exception.code) + ": " +
                               exception.toString());
        } catch (Exception exception) {
            System.err.println("JavaClient: " + exception.toString());
        }
    }

}

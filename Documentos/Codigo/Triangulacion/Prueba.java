package primero;

public class Prueba 
{
	public static void main(String args[])
	{
		// Lo primero que debemos hacer es una lista de todos los nodos
		Nodo nod0 = new Nodo(0,0,false,"0",5,10);
		Nodo nod1 = new Nodo(0,5,false,"1",5,10);
		Nodo nod2 = new Nodo(0,10,false,"2",5,10);
		Nodo nod3 = new Nodo(10,0,false,"3",5,10);
		Nodo nod4 = new Nodo(10,5,false,"4",5,10);
		Nodo nod5 = new Nodo(10,10,false,"5",5,10);
		Nodo nod6 = new Nodo(20,0,true,"6",5,10);
		Nodo nod7 = new Nodo(20,5,false,"7",5,10);
		Nodo nod8 = new Nodo(20,10,false,"8",5,10);
		Nodo nod9 = new Nodo(30,0,false,"9",5,10);
		Nodo nod10 = new Nodo(40,5,false,"10",5,10);
		Nodo nod11 = new Nodo(30,10,true,"11",5,10);
		
		// Luego creamos el mapa
		Mapa map = new Mapa(7,12);
		
		// A continuacion aņadimos los nodos EN ORDEN al mapa
		map.aņadeNodo(nod0);
		map.aņadeNodo(nod1);
		map.aņadeNodo(nod2);
		map.aņadeNodo(nod3);
		map.aņadeNodo(nod4);
		map.aņadeNodo(nod5);
		map.aņadeNodo(nod6);
		map.aņadeNodo(nod7);
		map.aņadeNodo(nod8);
		map.aņadeNodo(nod9);
		map.aņadeNodo(nod10);
		map.aņadeNodo(nod11);
		
		// Acto seguido aņadimos las distancias de un nodo al otro
		map.aņadeDistancia(0,1,3);
		map.aņadeDistancia(1,2,3);
		map.aņadeDistancia(1,4,8);
		map.aņadeDistancia(3,4,3);
		map.aņadeDistancia(4,5,3);
		map.aņadeDistancia(4,7,8);
		map.aņadeDistancia(6,7,3);
		map.aņadeDistancia(7,8,3);
		map.aņadeDistancia(7,10,8);
		map.aņadeDistancia(9,10,3);
		map.aņadeDistancia(10,11,3);
	
		punto p = new punto(2,11,0);// aqui debemos poner el punto devuelto por el algoritmo de triangulacion
		Nodo n = map.buscaNodoImpresora(p); // LLamada a la funcion mas importante.Esta realiza la localizacion
		System.out.println("Debes acudir al nodo " + n.getDesc()+ " para recoger el impreso. ");
	}
}

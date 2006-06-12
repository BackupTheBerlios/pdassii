package triangulacion;

import java.util.Vector;

import primero.Nodo;
import primero.punto;
/** Varias cosas :
 * -> La matriz de adyacencias contiene los siguientes valores:
 * 	  [i][j] = 'Integer.MAX_VALUE' cuando no haya camino entre los nodos 'i' y 'j' 
 * 	  [i][j] = 0 cuando 'i'=='j' 
 *    [i][j] = k cuando la distancia entre 'i' y 'j' sea de valor k
 */ 
public class Mapa 
{
	/** Almacena el listado de los nodos */
	private Vector mapNodos; 
	/** Almacena la matriz de adyacencias */
	private float [][] mapAristas; // Almacena en forma de matriz,el valor de las aristas
	/** Indica el número de nodos presentes en el grafo */
	private int numNodos; // Numero de nodos presentes en la planta
	/** Muestra la planta en la cual nos encontramos */
	private int planta; // Planta en la cual nos situamos
	/** Almacena la matriz de Floyd */
	private float[][] mapFinal;
	
	public Mapa()
	{
		this.mapNodos = new Vector();
		this.planta = 0;
		this.numNodos = 0;		
		this.mapAristas = this.inicializaMatriz(this.mapAristas);
		this.mapFinal = this.inicializaMatriz(this.mapFinal);
	}
	
	public Mapa(int pl,int num)
	{
		mapNodos = new Vector();
		this.planta = pl;
		this.numNodos = num;
		this.mapAristas = this.inicializaMatriz(this.mapAristas);
		this.mapFinal = this.inicializaMatriz(this.mapFinal);
	}
	
	public int getNumNodos()
	{
		return this.mapNodos.size();
	}
	
	public Nodo getNodo(int n)
	{
		Nodo naux = (Nodo)this.mapNodos.elementAt(n);
		return naux;
	}
	
	public void añadeNodo(Nodo n)
	{
		this.mapNodos.addElement(n);
	}
	
	public void añadeNodo(int posicion,Nodo n)
	{
		this.mapNodos.add(posicion,n);
	}
	
	public void añadeDistancia(int origen,int destino,int dist)
	{
		this.mapAristas[origen][destino] = dist;
		this.mapAristas[destino][origen] = dist;
	}
	
	/** Crea una instancia de la matriz de adyacencia e inicializa
	*   las diagonales a cero y el resto a 'Integer.MAX_VALUE'
	*/	
	public float[][] inicializaMatriz(float [][]mat)
	{
		mat = new float[this.numNodos][this.numNodos];
		for(int i = 0;i < this.numNodos;i++)
			for(int j = 0;j < this.numNodos;j++)
			{
				if(i==j)
					mat[i][j] = 0;
				else 
					mat[i][j] = Integer.MAX_VALUE;
			}
		return mat;
	}
	/** Realiza la copia de una matriz en otra, 
	* 	y la devuelve como resultado
	*/
	public float[][] copiarMat(float[][]dest, float[][] or)
	{
		for(int i = 0;i<this.getNumNodos();i++)
			for(int j = 0;j<this.getNumNodos();j++)
				dest[i][j] = or[i][j];
		return dest;
	}
	
	/** Ejecuta el algoritmo de Floyd para hallar el camino 
	*	mínimo entre cada par de nodos 
	*/
	public float[][] Floyd()
	{
		copiarMat(this.mapFinal,this.mapAristas);
		
		for(int k = 0;k < this.getNumNodos();k++)
			for(int i = 0;i < this.getNumNodos();i++)
				for(int j = 0;j < this.getNumNodos();j++)
				{
					if(this.mapFinal[i][j]>(this.mapFinal[i][k] + this.mapFinal[k][j]))
					{
						this.mapFinal[i][j] = this.mapFinal[i][k] + this.mapFinal[k][j];
					}
				}
		return this.mapFinal;
	}
	
	/** Rastrea entre todos los nodos existentes, aquel que sea
	*   más cercano a la PDA y que además 
	* 	contenga impresora, 
	*/
	public Nodo filtar(Nodo nod)
	{
		Nodo naux; // Nodo auxiliar para operar con él
		boolean [] usado = new boolean [this.getNumNodos()];
		int num = this.correspondenciaNodo(nod);
		int nodoDistanciaMin;
		if(nod.getTieneImp())
			naux = (Nodo)this.mapNodos.elementAt(num);
		else
		{
			do
			{
				nodoDistanciaMin = eligeMin(num,usado);
				usado[nodoDistanciaMin] = true;
				naux = (Nodo)this.mapNodos.elementAt(nodoDistanciaMin);
			}
			while (!naux.getTieneImp());
		}
		return naux;
	}
	
	/** Elige el índice cuyo nodo tiene una distancia menor
	*   con el indice que le pasamos por parámetro 
	*/
	public int eligeMin(int n,boolean []us)
	{
		float minimo = Integer.MAX_VALUE;
		int indice = 0;
		for(int i = 0;i < this.getNumNodos();i++)
		{
			if(!us[i])
				if(n!=i)	
					if(this.mapFinal [n][i] < minimo)
					{
						minimo = this.mapFinal [n][i];
						indice = i;
					}
			}
		return indice;
	}
	
	/** Devuelve el numero de nodo al 
	*   que corresponde el nodo que pasamos por parámetro
	*/
	public int correspondenciaNodo(Nodo n)
	{
		int num = -1;
		Nodo naux; // Nodo auxiliar para operar con el
		for(int i = 0;i < this.getNumNodos(); i++)
		{
			naux = (Nodo)this.mapNodos.elementAt(i);
			if(n.getX()== naux.getX())
			{
				if(n.getY()== naux.getY())
				{
					num = i;
				}					
			}				
		}
		return num;
	}
	/**A partir de las coordenadas devueltas por el algoritmo de
	*  triangulacion, la función devuelve el nodo donde se ubican
	*  estas coordenadas
	*/
	public Nodo localizaPunto(punto pto)
	{
		Nodo retorno = null;
		double x = pto.getX();
		double y = pto.getY();
		
		for(int i = 0 ; i<this.getNumNodos(); i++ )
		{
			Nodo n = (Nodo)this.mapNodos.elementAt(i);
			if((n.getX() < x) && (n.getX() + n.getLargo())>x)
				if((n.getY() < y) && (n.getY() + n.getAncho())>y)
					retorno = n;
		}
	return retorno;
	}
	
	/**Busca el nodo más cercano que contenga impresora,
	*  a partir del nodo donde se encuentra la PDA
	*/	
	public Nodo buscaNodoImpresora(punto p)
	{
		Nodo nodo = this.localizaPunto(p);
		Nodo n;
		this.Floyd();
		n = this.filtar(nodo);
		return n;
	}
}

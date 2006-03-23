package primero;

public class Nodo 
{
	/** Coordenadas de la PDA */
	private float x,y; 
	/** Indica si tiene impresora el nodo actual */
	private boolean tieneImpresora;	
	/** Proporciona descpción del nodo */
	private String descripcion;
	
	public Nodo()
	{		
	}
	
	public Nodo( float a,float b,boolean bo,String desc)
	{
		this.x = a;
		this.y = b;
		this.tieneImpresora = bo;		
		this.descripcion = desc;
	}
	
	public Nodo( float a,float b,boolean bo)
	{
		this.x = a;
		this.y = b;
		this.tieneImpresora = bo;		
		this.descripcion = " ";
	}
	
	public float getX()
	{
		return this.x;
	}

	public float getY()
	{
		return this.y;		
	}
	
	public boolean getTieneImp()
	{
		return this.tieneImpresora;
	}
	
	public String getDesc()
	{
		return this.descripcion;
	}
	
	public void setX(int a)
	{
		this.x = a;		
	}
	
	public void setY(int b)
	{
		this.y = b;
	}
	
	public void setTieneImp(boolean bo)
	{
		this.tieneImpresora = bo;
	}
	
	public boolean igualA(Nodo n)
	{
		boolean retorno=false;
		if(this.x == n.getX())
		{
			if(this.y == n.getY())
				retorno = true;
		}
		return retorno;
	}
	
	public void setDesc(String des)
	{
		this.descripcion = des;
	}
}


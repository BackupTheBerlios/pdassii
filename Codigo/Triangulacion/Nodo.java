package Triangulacion;

public class Nodo 
{
	/** Coordenadas de la PDA */
	private float x,y; 
	/** Indica si tiene impresora el nodo actual */
	private boolean tieneImpresora;	
	/** Proporciona descpcin del nodo */
	private String descripcion;
	
	public Nodo()
	{		
	}
	
	public Nodo( float x,float y,boolean bo,String desc)
	{
		this.x = x;
		this.y = y;
		this.tieneImpresora = bo;		
		this.descripcion = desc;
	}
	
	public Nodo( float x,float y,boolean bo)
	{
		this.x = x;
		this.y = y;
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
	
	public void setX(int x)
	{
		this.x = x;		
	}
	
	public void setY(int y)
	{
		this.y = y;
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


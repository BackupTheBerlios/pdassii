package triangulacion;

public class Nodo 
{
	/** Coordenadas del nodo */
	private double x,y; 
	/** Indica el ancho y largo del nodo */
	private double largo,ancho;
	/** Indica si tiene impresora el nodo actual */
	private boolean tieneImpresora;	
	/** Proporciona descpción del nodo */
	private String descripcion;
	
	public Nodo()
	{		
	}
	
	public Nodo( double a,double b,boolean bo,String desc,double lar,double anch)
	{
		this.x = a;
		this.y = b;
		this.tieneImpresora = bo;		
		this.descripcion = desc;
		this.largo = lar;
		this.ancho = anch;
	}
	
	public Nodo( double a,double b,boolean bo,double lar,double anc)
	{
		this.x = a;
		this.y = b;
		this.tieneImpresora = bo;		
		this.descripcion = " ";
		this.largo = lar;
		this.ancho = anc;
	}
	
	public double getX()
	{
		return this.x;
	}

	public double getY()
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
	
	public double getAncho()
	{
		return this.ancho;
	}
	
	public double getLargo()
	{
		return this.largo;
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


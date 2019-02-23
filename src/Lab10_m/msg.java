package Lab10_m;
import GenCol.*;

public class msg extends entity // job ¿Á¡§¿«
{
	int tmp;
	boolean swtch; // true = on, false = off
	
	public msg(String name, boolean _swtch)
	{  
		super(name);  
		swtch = _swtch;
	}
	
	public msg(String name, int _tmp)
	{  
		super(name);  
		tmp = _tmp;
		swtch = true;
	}
}
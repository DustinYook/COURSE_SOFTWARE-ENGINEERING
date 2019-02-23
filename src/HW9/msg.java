package HW9;
import GenCol.*;

public class msg extends entity
{   
	int ans;
	int dest;
	int[] num = new int[10];
	int num1, num2; 
	
	public msg(String name, int _dest, int _num1, int _num2)
	{  
		super(name);
		dest = _dest;
		num1 = _num1;
		num2 = _num2;
	}
	
	public msg(String name, int _dest, int[] _num)
	{  
		super(name);
		dest = _dest;
		num = _num;
	}
	
	public msg(String name, int _ans)
	{  
		super(name);
		ans = _ans;
	}
}
package Lab5;
import GenCol.*;

public class addRequest extends entity // job
{   
	int num1;
	int num2;
	
	public addRequest(String name, int _num1, int _num2)
	{  
		super(name);  
		
		num1 = _num1;
		num2 = _num2;
	}
}
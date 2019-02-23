package HW5;
import GenCol.*;

public class Request extends entity // job
{
	double num1; // 피연산자1
 	double num2; // 피연산자2
	char oper; // 이항산술연산자
	
	public Request(String name, double _num1, char _oper, double _num2)
	{  
		super(name);
		num1 = _num1;
		oper = _oper;
		num2 = _num2;
	}
}
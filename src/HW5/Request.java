package HW5;
import GenCol.*;

public class Request extends entity // job
{
	double num1; // �ǿ�����1
 	double num2; // �ǿ�����2
	char oper; // ���׻��������
	
	public Request(String name, double _num1, char _oper, double _num2)
	{  
		super(name);
		num1 = _num1;
		oper = _oper;
		num2 = _num2;
	}
}
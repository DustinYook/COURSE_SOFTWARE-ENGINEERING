// job.java : job class
package Lab6;
import GenCol.*;

public class job extends entity // �����ϴ� �۾� ��ü�� ��Ÿ���� ��ü
{   
	int num; // ����� ������
	boolean isLast; // ���������� ǥ���ϴ� �÷���
	
	/* Overloaded Constructor */
	public job(String name, int _num)
	{  
		super(name);  
		
		num = _num;
		isLast = false; // �⺻������ ������ �÷���
	}
	
	/* Overloaded Constructor */
	public job(String name, int _num, boolean _isLast)
	{
		super(name);  
		
		num = _num;
		isLast = _isLast;
	}
}

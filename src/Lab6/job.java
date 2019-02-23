// job.java : job class
package Lab6;
import GenCol.*;

public class job extends entity // 수행하는 작업 자체를 나타내는 객체
{   
	int num; // 저장된 데이터
	boolean isLast; // 마지막인지 표시하는 플래그
	
	/* Overloaded Constructor */
	public job(String name, int _num)
	{  
		super(name);  
		
		num = _num;
		isLast = false; // 기본적으로 설정된 플래그
	}
	
	/* Overloaded Constructor */
	public job(String name, int _num, boolean _isLast)
	{
		super(name);  
		
		num = _num;
		isLast = _isLast;
	}
}

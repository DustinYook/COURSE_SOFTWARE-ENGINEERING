package Lab8;
import GenCol.*;

public class packet extends entity
{   
	int dest; // 목적지 변수
	
	public packet(String name, int _dest)
	{  
		super(name);  
		
		dest = _dest; // 목적지 받음
	}
}
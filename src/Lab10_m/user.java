package Lab10_m;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class user extends ViewableAtomic
{
	int user_temp; // #���� �߰�
	public user() { this("user", 24); }
	public user(String name, int _user_temp)
	{
		super(name);
   
		addOutport("out"); // in ��Ʈ�� ����
		user_temp = _user_temp; // #���⼭ �ʱ�ȭ
	}
  
	public void initialize() 
	{ 
		holdIn("active", 0); // 0���� ���� 
	}
  
	public void deltext(double e, message x) { /* in port ���� ���� */ }

	public void deltint()
	{
		if (phaseIs("active"))
			holdIn("Switch On", 100); // #100���� �ٲ�
		else if (phaseIs("Switch On"))
			holdIn("Switch Off", 0);
	}

	public message out()
	{
		message m = new message();
		
		if(phaseIs("active"))
			m.add(makeContent("out", new msg("switch : on, set temp:" + user_temp, user_temp))); // active ���� �µ��� ���� ����
		else if(phaseIs("Switch On"))
			m.add(makeContent("out", new msg("switch : off", false))); // �� ����
		
		return m;
	}
  
	public String getTooltipText() { return super.getTooltipText(); }
}
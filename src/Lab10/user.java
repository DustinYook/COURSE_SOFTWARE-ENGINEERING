package Lab10;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class user extends ViewableAtomic
{
	public user() 
	{
		this("user");
	}
  
	public user(String name)
	{
		super(name);
   
		addOutport("out"); // in ��Ʈ�� ����
	}
  
	public void initialize()
	{
		holdIn("active", 0); // 0���� ����
	}
  
	public void deltext(double e, message x) { /* in port ���� ���� */ }

	public void deltint()
	{
		if (phaseIs("active"))
			holdIn("Switch On", 15); // �ð��� ���Ƿ� �� -> 15���� out �θ�
		else if (phaseIs("Switch On"))
			holdIn("temp_sent", 85); // �������� -> 85 ���� out �θ�
		else if(phaseIs("temp_sent"))
			holdIn("Switch Off", INFINITY);
	}

	public message out()
	{
		message m = new message();
		
		if(phaseIs("active"))
			m.add(makeContent("out", new msg("on", true))); // �޼��� ����
		else if(phaseIs("Switch On"))
			m.add(makeContent("out", new msg("set temperature : 24", 24))); // 24�� ����
		else if(phaseIs("temp_sent"))
			m.add(makeContent("out", new msg("off", false))); // 24�� ����
		
		return m;
	}
  
	public String getTooltipText() { return super.getTooltipText(); }
}
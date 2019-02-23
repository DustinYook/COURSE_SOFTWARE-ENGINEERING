package Lab7;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class client extends ViewableAtomic // client�� generator
{
	protected double int_arr_time;
	protected int count;
  
	public client() 
	{
		this("client", 30);
	}
  
	public client(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
    
		int_arr_time = Int_arr_time;
	}
  
	/* Initialize Function */
	public void initialize()
	{
		count = 1;
		
		holdIn("Active", int_arr_time); 
		// ���������� int_arr_time��ŭ Active ����
	}
 
	/* External Transition Function */
	public void deltext(double e, message x)
	{
		Continue(e);
		
		// SYN�� ������ Sent ���·� server�� ������ ��ٸ�
		if (phaseIs("Sent"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					holdIn("Sent", int_arr_time);
					// int_arr_time��ŭ Sent ���� 
				}
			}
		}
	}

	/* Internal Transition Function */
	public void deltint()
	{
		// State�� Active�� �� 
		if(phaseIs("Active")) // ����: out function���� hold in �ϸ� �ȵ� -> server�� �ٲ��� ��
			holdIn("Sent", INFINITY); // ������ block�Լ�
		else if(phaseIs("Sent")) // State�� Sent�� ��
			holdIn("Connected", INFINITY);
		// INFINITY�� �ǹ�: �ٸ� Atomic Model�κ��� �Է°��� ���;߸� ��� �� ����
					// ������ ���� ������ ��� �� ���ٴ� ��
	}

	/* Out Function */
	public message out()
	{
		message m = new message();
		if(phaseIs("Active")) // State�� Active�� �� 
			m.add(makeContent("out", new packet("SYN")));
		else if(phaseIs("Sent")) // State�� Sent�� ��
			m.add(makeContent("out", new packet("ACK")));
		return m;
	}
  
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time
        + "\n" + " count: " + count;
	}
}
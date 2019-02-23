package HW8;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class sender extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int count;
  
	public sender() 
	{
		this("genr", 30);
	}
  
	public sender(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
    
		int_arr_time = Int_arr_time;
	}
  
	public void initialize()
	{
		count = 0; // �츮�� 0���� ��
		
		holdIn("active", int_arr_time);
	}
  
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("wait")) // wait
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					holdIn("active", int_arr_time); // wait -> active
				}
			}
		}
	}

	public void deltint()
	{
		if (phaseIs("active"))
		{
			count = count + 1; // �״��
			
			if(count != 5)
				holdIn("active", int_arr_time);
			else if(count == 5)
			{
				count = 0; // �ʱ�ȭ (�ٽ� ó�� �ϱ� ����)
				holdIn("wait", INFINITY); // ���� �� ������ ��ٸ� (active -> wait)
			}
		}
	}

	public message out()
	{
		message m = new message();
		m.add(makeContent("out", new packet("packet" + (count +1), (int)(Math.random() * 5 + 1))));
		// Math.random()�� ���� ������ �ּҸ� �������� ����
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

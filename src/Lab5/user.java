package Lab5;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class user extends ViewableAtomic // generator
{
	protected double int_arr_time;
	protected int count;

	/* Overloaded Constructor */
	public user() 
	{
		this("user", 30);
	}
  
	/* Overloaded Constructor */
	public user(String name, double Int_arr_time)
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
		
		holdIn("active", int_arr_time);
	}
  
	/* External Transition Function */
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("active"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					holdIn("stop", INFINITY);
				}
			}
		}
	}

	/* Internal Transition Function */
	public void deltint()
	{
		if (phaseIs("active"))
		{
			count = count + 1;
			
			// holdIn("active", int_arr_time); // �������� ����� ���� ���� -> 2�� ������ �Ǵ� ����
			if(count >= 2)
				holdIn("stop", INFINITY); // active ���¸� ���� (�� ���� ������ ����)
		}
	}

	/* Out Function */
	public message out()
	{
		message m = new message();
		
		m.add(makeContent("out", new addRequest("2 + 3", 2, 3))); // ���� ����
		
		return m;
	}
  
	/* Tool Tip */
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time
        + "\n" + " count: " + count;
	}
}
package Lab9;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class add extends ViewableAtomic
{
  
	int sum;
//	protected entity job;
//	protected double processing_time;

	public add()
	{
		this("proc"); //
	}

	public add(String name)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
//		processing_time = Processing_time;
	}
  
	public void initialize()
	{
//		job = new entity("");
		sum = 0; // 
		holdIn("passive", INFINITY); // 
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
//					job = x.getValOnPort("in", i);
					msg job;//
					job = (msg)x.getValOnPort("in", i);//
					
					sum = job.num1 + job.num2;//
					
					holdIn("busy", 0);//
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("busy"))
		{
//			job = new entity("");
			
			holdIn("passive", INFINITY); // �ܺο��� ������ ����
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy")) // �״��
			m.add(makeContent("out", new msg("" + sum, sum))); // ���� ������ string
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText();
	}

}


package Lab9;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class router extends ViewableAtomic
{
  
//	protected entity job;
//	protected double processing_time;
	msg forward_msg;

	public router()
	{
		this("router"); // 
	}

	public router(String name)
	{
		super(name);
    
		addInport("in");
		
		addOutport("out1");
		addOutport("out2");
		
//		processing_time = Processing_time;
	}
  
	public void initialize()
	{
//		job = new entity("");
		
		holdIn("passive", INFINITY); //
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive")) // 
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					forward_msg = (msg)x.getValOnPort("in", i); //
					
					holdIn("sending", 0); //
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("sending")) // 
		{
//			job = new entity("");
			holdIn("passive", INFINITY); //
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("sending")) //
		{
			m.add(makeContent("out" + forward_msg.dest, forward_msg)); //
		}
		return m;
	}

	public String getTooltipText()
	{
		return super.getTooltipText(); // 
	}

}


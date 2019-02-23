package Lab8;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class receiver extends ViewableAtomic
{
  
	protected entity job; // 그냥 놔둠
	protected double processing_time;

	public receiver()
	{
		this("proc", 20);
	}

	public receiver(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		//addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		job = new entity("");
		
		holdIn("passive", INFINITY);
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
					job = x.getValOnPort("in", i);
					
					holdIn("busy", processing_time);
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			job = new entity(""); // packet을 받아 job으로 쓸 수 있음 
			// job 'is a' entity (O)
			// entity 'is' a job (X)
			// 상속관계를 생각 (12:29)
			
			holdIn("passive", INFINITY); //
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
		{
			m.add(makeContent("out", job));
		}
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText()
		+ "\n" + "job: " + job.getName();
	}

}


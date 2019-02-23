package HW3;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class proc extends ViewableAtomic
{
	protected entity job;
	protected double processing_time;

	/* Overloaded Constructor */
	public proc()
	{
		this("proc", 20);
	}

	/* Overloaded Constructor */
	public proc(String name, double Processing_time)
	{
		super(name);
		
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	/* Initialize Function */
	public void initialize()
	{
		job = new entity("");
		
		holdIn("passive", INFINITY); 
	}

	/* External Transition Function */
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive")) // passive 아니면 active
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
//		else if(phaseIs("busy"))
//			continue; // 일 하는 중에는 active -> busy이면 ignore
	}
	
	/* Internal Transition Function */
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			job = new entity(""); // job 생성
		
			holdIn("passive", INFINITY);  
		}
	}
  
	/* Out Function */
	public message out()
	{
		message m = new message();
		
		if (phaseIs("busy"))
			m.add(makeContent("out", job));
		
		return m;
	}
	
	/* Tool Tip */
	public String getTooltipText()
	{
		return super.getTooltipText() + "\n" 
				+ "job: " + job.getName();
	}
}
package Lab9;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class acc extends ViewableAtomic
{
	int sum;
//	protected entity job;
//	protected double processing_time;

	public acc()
	{
		this("proc");
	}

	public acc(String name)
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
					msg job; //
				    job = (msg)x.getValOnPort("in", i);
					
				    // 배열에서 값을 하나씩 빼면서 누적 -> 빼는 처리 없어 고정
				    for(int k =0; k < job.num.length; k++)
				    {
				    	sum += job.num[k];
				    }
				    holdIn("busy", 0);
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("busy"))
		{
//			job = new entity("");
			
			holdIn("passive", INFINITY);
		}
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
		{
			m.add(makeContent("out", new msg(""+sum, sum))); //
		}
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText(); //
	}
}
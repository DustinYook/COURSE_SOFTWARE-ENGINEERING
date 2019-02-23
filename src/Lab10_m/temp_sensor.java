package Lab10_m;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class temp_sensor extends ViewableAtomic
{
	protected double processing_time;

	public temp_sensor()
	{
		this("temp_sensor", 20); // ÀÌ¸§ ¹Ù²Þ
	}

	public temp_sensor(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	public void initialize()
	{
		holdIn("off", INFINITY); // ±âº» off »óÅÂ
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("off"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					msg job;
					job = (msg)x.getValOnPort("in", i);
					
					if(job.swtch)
						holdIn("on", processing_time);
				}
			}
		}
		if (phaseIs("on"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					msg job;
					job = (msg)x.getValOnPort("in", i);
					
					if(!job.swtch) // off »óÅÂ¸é false
						holdIn("off", INFINITY); // off·Î ²¨Áü
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("on"))
			holdIn("on", processing_time); // out->deltint
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("on"))
		{
			int temp = (int)((Math.random() * 20) + 15); // ·£´ý °ª
			
			m.add(makeContent("out", new msg("temp: " + temp, temp)));
		}
		return m;
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText();
	}

}


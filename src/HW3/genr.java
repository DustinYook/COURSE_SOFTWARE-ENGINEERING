package HW3;
import simView.*;
import genDevs.modeling.*; 
import GenCol.*;

public class genr extends ViewableAtomic
{
	protected double int_arr_time;
	protected int count;
	
	/* Overloaded Constructor */
	public genr() 
	{
		this("g", 20);
	}
	
	/* Overloaded Constructor */
	public genr(String name, double Int_arr_time)
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
					// holdIn("stop", INFINITY); 
					// 상태 천이를 수행하지 않게하기 위해 주석처리
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
			holdIn("active", int_arr_time); 
			// holdIn("active", 90);와 같이도 쓸 수 있음
		}
	}
	
	/* Out Function */
	public message out()
	{
		message m = new message();
		m.add(makeContent("out", new entity("job" + count)));

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
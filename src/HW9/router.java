package HW9;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class router extends ViewableAtomic // PROCESSOR
{
	msg forward_msg;
	public router() { this("router"); }
	public router(String name)
	{
		super(name);
    
		addInport("in");
		
		addOutport("out1"); // add
		addOutport("out2"); // acc
		addOutport("out3"); // mult
	}
	
	/* INITIALIZE FUNCTION */
	public void initialize() { holdIn("passive", INFINITY); }

	/* EXTERNAL TRANSITION FUNCTION */
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					forward_msg = (msg)x.getValOnPort("in", i);
					holdIn("sending", 0);
				}
			}
		}
	}
  
	/* INTERNAL TRANSITION FUNCTION */
	public void deltint()
	{
		if (phaseIs("sending"))
			holdIn("passive", INFINITY);
	}

	/* OUT FUNCTION */
	public message out()
	{
		message m = new message();
		if (phaseIs("sending"))
			m.add(makeContent("out" + forward_msg.dest, forward_msg)); // dest에 의해 포트지정
		return m;
	}

	public String getTooltipText() { return super.getTooltipText(); }
}
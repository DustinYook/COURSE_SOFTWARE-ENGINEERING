package HW9;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class acc extends ViewableAtomic
{
	int result; // 결과를 저장할 변수

	public acc() { this("proc"); }
	public acc(String name)
	{
		super(name);
		addInport("in");
		addOutport("out");
	}

	/* INITIALIZE FUNCTION */
	public void initialize()
	{
		result = 0; // 초기화
		holdIn("passive", INFINITY); // 상태를 passive로 초기화
	}

	/* EXTERNAL TRANSITION FUCNTION */
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					msg job;
				    job = (msg)x.getValOnPort("in", i);
					
				    for(int k = 0; k < job.num.length; k++)
				    	result += job.num[k];
				    holdIn("busy", 0);
				}
			}
		}
	}

	/* INTERNAL TRANSITION FUNCTION */
	public void deltint()
	{
		if (phaseIs("busy"))
			holdIn("passive", INFINITY);
	}

	/* OUT FUNCTION */
	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
			m.add(makeContent("out", new msg(""+result, result)));
		return m;
	}

	public String getTooltipText() { return super.getTooltipText(); }
}
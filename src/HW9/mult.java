package HW9;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class mult extends ViewableAtomic
{
	int result; // ���������� ����� ������ ����
	public mult() { this("mult"); }
	public mult(String name)
	{
		super(name);
		addInport("in");
		addOutport("out");
	}
  
	/* INITIALIZE FUNCTION */
	public void initialize()
	{
		result = 1;
		holdIn("passive", INFINITY);
	}

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
					msg job;
					job = (msg)x.getValOnPort("in", i);
					result = job.num1 * job.num2; // ���ϱ⿡�� ���ϱ�� ����
					holdIn("busy", 0);
				}
			}
		}
	}
  
	/* INTERNAL TRANSITION FUNCTION */
	public void deltint()
	{
		if (phaseIs("busy"))
			holdIn("passive", INFINITY); // �ܺο��� ������ ����
	}

	/* OUT FUNCTION */
	public message out()
	{
		message m = new message();
		if (phaseIs("busy"))
			m.add(makeContent("out", new msg("" + result, result))); // ���� ������ string
		return m;
	}

	public String getTooltipText() { return super.getTooltipText(); }
}
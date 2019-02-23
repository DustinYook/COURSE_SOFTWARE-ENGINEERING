package Lab10;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class controller extends ViewableAtomic
{
	public controller() { this("controller"); }
	public controller(String name)
	{
		super(name);
		addInport("in1");
		addInport("in2");
		addOutport("out");
	}
  
	public void initialize()
	{
		holdIn("off", INFINITY); // input 전까지 잠
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("off")) // user로부터 들어옴
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in1", i)) // 1번 포트
				{
					msg job;
					job = (msg)x.getValOnPort("in1", i);
					
					holdIn("wait", 0); // off -> wait
				}
			}
		}
		else if(phaseIs("ready"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in2", i)) // 2번 포트
				{
					msg job;
					job = (msg)x.getValOnPort("in2", i);
					
					if(job.tmp < 24)
						holdIn("heating", INFINITY); // INFINITY 외부에서 올때까지 잠잠
					if(job.tmp > 24)
						holdIn("cooling", INFINITY);
				}
			}
		}
		else if(phaseIs("heating"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in2", i)) // 2번 포트 - 센서랑 왔다갔다
				{
					msg job;
					job = (msg)x.getValOnPort("in2", i);
					
					if(job.tmp > 24)
						holdIn("cooling", INFINITY);
				}
				
				if (messageOnPort(x, "in1", i)) // 2번 포트
				{
					msg job;
					job = (msg)x.getValOnPort("in1", i);
					
					if(!job.swtch)
						holdIn("heating", 0); // out으로 가서 전이
				}
			}
		}
		else if(phaseIs("cooling"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in2", i))
				{
					msg job;
					job = (msg)x.getValOnPort("in2", i);
					
					if(job.tmp < 24) // 등호 바뀜
						holdIn("heating", INFINITY);
				}
				
				if (messageOnPort(x, "in1", i)) 
				{
					msg job;
					job = (msg)x.getValOnPort("in1", i);
					
					if(!job.swtch)
						holdIn("cooling", 0); // cooling 그대로 가져가면서 out으로 가서 전이
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("wait"))
			holdIn("ready", INFINITY); // 상태전이
		else if(phaseIs("heating"))
			holdIn("off", INFINITY);  // 멈춰줌
		else if(phaseIs("cooling"))
			holdIn("off", INFINITY);  // 멈춰줌
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("wait"))
			m.add(makeContent("out", new msg("on", true)));
		if(phaseIs("heating"))
			m.add(makeContent("out", new msg("off", false)));
		if(phaseIs("cooling")) // cooling, 0에서 넘어옴
			m.add(makeContent("out", new msg("off", false)));
		return m;
	}

	public String getTooltipText() { return super.getTooltipText(); }
}
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
		holdIn("off", INFINITY); // input ������ ��
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("off")) // user�κ��� ����
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in1", i)) // 1�� ��Ʈ
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
				if (messageOnPort(x, "in2", i)) // 2�� ��Ʈ
				{
					msg job;
					job = (msg)x.getValOnPort("in2", i);
					
					if(job.tmp < 24)
						holdIn("heating", INFINITY); // INFINITY �ܺο��� �ö����� ����
					if(job.tmp > 24)
						holdIn("cooling", INFINITY);
				}
			}
		}
		else if(phaseIs("heating"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in2", i)) // 2�� ��Ʈ - ������ �Դٰ���
				{
					msg job;
					job = (msg)x.getValOnPort("in2", i);
					
					if(job.tmp > 24)
						holdIn("cooling", INFINITY);
				}
				
				if (messageOnPort(x, "in1", i)) // 2�� ��Ʈ
				{
					msg job;
					job = (msg)x.getValOnPort("in1", i);
					
					if(!job.swtch)
						holdIn("heating", 0); // out���� ���� ����
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
					
					if(job.tmp < 24) // ��ȣ �ٲ�
						holdIn("heating", INFINITY);
				}
				
				if (messageOnPort(x, "in1", i)) 
				{
					msg job;
					job = (msg)x.getValOnPort("in1", i);
					
					if(!job.swtch)
						holdIn("cooling", 0); // cooling �״�� �������鼭 out���� ���� ����
				}
			}
		}
	}
  
	public void deltint()
	{
		if (phaseIs("wait"))
			holdIn("ready", INFINITY); // ��������
		else if(phaseIs("heating"))
			holdIn("off", INFINITY);  // ������
		else if(phaseIs("cooling"))
			holdIn("off", INFINITY);  // ������
	}

	public message out()
	{
		message m = new message();
		if (phaseIs("wait"))
			m.add(makeContent("out", new msg("on", true)));
		if(phaseIs("heating"))
			m.add(makeContent("out", new msg("off", false)));
		if(phaseIs("cooling")) // cooling, 0���� �Ѿ��
			m.add(makeContent("out", new msg("off", false)));
		return m;
	}

	public String getTooltipText() { return super.getTooltipText(); }
}
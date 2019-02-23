package Lab3;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class proc extends ViewableAtomic
{
	protected entity job; // ex) job1, job2, ...
	protected double processing_time; // ó���ð�

	/* Overloaded Constructor */
	public proc()
	{
		this("proc", 20);
	}

	/* Overloaded Constructor */
	public proc(String name, double Processing_time)
	{
		super(name); // ���μ����� �̸��� ����
		
		addInport("in"); // in��Ʈ ����
		addOutport("out"); // out��Ʈ ����
		
		processing_time = Processing_time; // �ñ׸�����
	}
  
	/* Initialize Function */
	public void initialize()
	{
		job = new entity("");
		
		holdIn("passive", INFINITY); 
		// �ǹ�: �ʱ⿡�� ������ passive phase�� ����
	}

	/* External Transition Function */
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive")) // external�� �ڽ��� ���¸� ���� üũ -> ó�� ���� ��� busy
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					job = x.getValOnPort("in", i); // in��Ʈ���� job�� �޾ƿ�
					holdIn("busy", processing_time); 
					// processing_time ���� busy���� ����
				}
			}
		}
	}
  
	/* Internal Transition Function */
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			job = new entity(""); // job ���� ����
			holdIn("passive", INFINITY); // passive ���� ����
		}
	}
	
	/* Out Function */
	public message out() // �ڵ������� internal ȣ��
	{
		message m = new message(); // �� �޼��� ����
		
		if (phaseIs("busy"))
			m.add(makeContent("out", job)); // generator�� ������!
		
		return m;
	}
	
	/* Tool Tip */
	public String getTooltipText()
	{
		return super.getTooltipText() + "\n" 
				+ "job: " + job.getName();
	}
}
package Lab7;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class server extends ViewableAtomic // server�� processor
{
	protected entity job;
	protected double processing_time;

	public server()
	{
		this("server", 20);
	}

	public server(String name, double Processing_time)
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
		
		holdIn("Wait", INFINITY); 
		// �ʱ⿡ Wait���� ��ϵǾ� ����
	}

	/* External Transition Function */
	public void deltext(double e, message x)
	{
		Continue(e);
		
		// SYN�� ���Դ��� Ȯ��
		if (phaseIs("Wait"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					/* ���� ���Դٴ� ���� Wait ���¿��� �޼����� ���� ��� */
					
					job = (packet)x.getValOnPort("in", i); 
					// �ݵ�� packet���� ��������ȯ ���־�� ��
					
					holdIn("Wait", processing_time);
					// Wait ���¿��� Out Function�� ���� ó����
				}
			}
		}
		// ACK�� ���Դ��� Ȯ��
		else if(phaseIs("SYN-received")) 
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					/* ���� ���Դٴ� ���� SYN-received ���¿��� �޼����� ���� ��� */
					
					job = (packet)x.getValOnPort("in", i);
					// �ݵ�� packet���� ��������ȯ ���־�� ��
					
					holdIn("Established", INFINITY); 
					// Established ���·� ��ϵ�
				}
			}
		}
	}
  
	/* Internal Transition Function */
	public void deltint()
	{
		if (phaseIs("Wait"))
			holdIn("SYN-received", processing_time); // ���� �ٲ�
	}

	/* Out Function */
	public message out()
	{
		message m = new message();
		if (phaseIs("Wait"))
			m.add(makeContent("out", new packet("SYN-ACK"))); // ���� �ٲ�
		return m; // �޼����� ����
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText()
		+ "\n" + "job: " + job.getName();
	}
}
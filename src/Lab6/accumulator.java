// acccumulator.java : processor atomic model
package Lab6;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class accumulator extends ViewableAtomic
{
  
	protected job job; // entity�� job���� �ٲ�
	protected double processing_time;
	
	// ���� �߰�
	protected Queue q; // processor�� ť ���� (������ �ڹ� ���̺귯���� �ƴ� GenCol�� ���ǵ� ����!)
	protected int result; // ���� ������ ����� ������ ����

	/* Overloaded Constructor */
	public accumulator()
	{
		this("proc", 20);
	}

	/* Overloaded Constructor */
	public accumulator(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	/* Initialize Function */
	public void initialize()
	{
		q = new Queue(); // �� ť ����
		job = new job("", 0, false); // �� job ����
		result = 0; // ������� ���� �ʱ�ȭ
		
		holdIn("passive", INFINITY);
	}

	/* External Transition Function */
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive")) // �Է´�� ����
		{
			for (int i = 0; i < x.getLength(); i++) // �Է��� �ֳ�?
			{
				if (messageOnPort(x, "in", i))
				{
					job = (job)x.getValOnPort("in", i); // (job)���� coercion
					q.add(job); // ť�� job �߰�
					
					holdIn("passive", processing_time); // passive ���� ����
				}
			}
		}
	}
  
	/* Internal Transition Function */
	public void deltint()
	{
		if (phaseIs("passive")) // �Է´�� ����
		{
			job = (job) q.getLast(); 
			// System.out.println(job + " " + q.size()); // ����� ���� �ڵ�
			// System.out.println(job.isLast); // ����� ���� �ڵ�
			
			if(job.isLast) // ť�� ���������� Ȯ��
			{
				int qSize = q.size(); // ť�� ����� �������̱� ������ �ϴ� �޾Ƽ� �������־�� ��
				
				// processor�� ť�� ������ �� ������ �𸣱� ������ ����ũ��� '10'�� ���� ���� Ʋ����!
				for(int i = 0; i < qSize; i++) 
				{
					job = (job)q.removeFirst(); // dequeue�� ������ ���� ������ ����
					result = result + job.num; // dequeue�� element�� ���� ��������
					
					holdIn("processing", processing_time); // passive���� processing���� ����õ��
				}
			}
		}
	}

	/* Out Function */
	public message out()
	{
		message m = new message();
		
		if (phaseIs("processing")) // processing ������ ��� ó������
		{
			m.add(makeContent("out", new job(Integer.toString(result), result))); // �޼����� ó����� ����
			
			holdIn("finished", INFINITY); // processing���� finished�� ����õ��
		}
		
		return m;
	}
	
	/* Tool Tip */
	public String getTooltipText()
	{
		return
		super.getTooltipText()
		+ "\n" + "queue length: " + q.size()
		+ "\n" + "queue itself: " + q.toString(); // ���� ǥ�ó��� ����
	}
}
package Lab5;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class add extends ViewableAtomic // processor
{
	protected addRequest addNum; 
	protected double processing_time;
	int result; // ����� ������ ����

	/* Overloaded Constructor */
	public add()
	{
		this("add", 20); // add�� ĸ�Ǽ���
	}

	/* Overloaded Constructor */
	public add(String name, double Processing_time)
	{
		super(name);
		
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	/* Initialize Function */
	public void initialize()
	{
		addNum = new addRequest("", 0, 0);
		
		holdIn("passive", INFINITY); // �ʱ� phase�� passive
	}

	/* External Transition Function */
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					addNum = (addRequest) x.getValOnPort("in", i); // ��������ȯ
					
					result = addNum.num1 + addNum.num2; // ó������
					
					holdIn("busy", processing_time);
				}
			}
		}
	}
  
	/* Internal Transition Function */
	public void deltint()
	{
		if (phaseIs("busy")) // ó���Ϸ��ϰ� �Ǹ� busy���� passive�� ����õ�� �߻�
		{
			addRequest addNum = new addRequest("", 0, 0); // job �ʱ�ȭ �Ҵ�
			
			holdIn("passive", INFINITY); // busy���� passive�� ����õ��
		}
	}

	/* Out Function */
	public message out()
	{
		message m = new message();
		if (phaseIs("busy")) // phase �ľ��ϴ� �� �߿�!
		{
			m.add(makeContent("out", new entity(Integer.toString(result)))); 
			// result�� string ���·� wrap�Ͽ� �ٸ� atomic model�� ����
		}
		return m;
	}

	/* Tool Tip */
	public String getTooltipText() // hover�� �� ������ ����
	{
		return
		super.getTooltipText()
		+ "\n" + "job: " + addNum.getName(); //
	}
}
package HW9;

import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class user extends ViewableAtomic 
{
	int flag = 0;
	int num1 = 2, num2 = 8; // ������ ������ ���� �ǿ�����
	int[] num = { 3, 6, 5, 5, 7, 1, 8, 5, 2, 1 }; // acc�� ���� �ǿ�����
	
	public user() { this("user"); }
	public user(String name) 
	{
		super(name);
		addOutport("out");
		addInport("in");
	}

	/* INITIALIZE FUNCTION */
	public void initialize() 
	{
		flag = 0;
		holdIn("active", 0);
	}

	/* EXTERNAL TRANSITION FUNCTION */
	public void deltext(double e, message x) 
	{
		Continue(e);
		if (phaseIs("sent_1"))
		{
			for (int i = 0; i < x.getLength(); i++) 
			{
				if (messageOnPort(x, "in", i)) 
				{
					msg result; 
					result = (msg)x.getValOnPort("in", i);
					System.out.println(num1 + " + " + num2 + " = " + result.ans);
					holdIn("active", 0); // ��� ������ �ٽ� active ���·� õ��
				}
			}
		}
		else if (phaseIs("sent_2"))
		{
			for (int i = 0; i < x.getLength(); i++) 
			{
				if (messageOnPort(x, "in", i)) 
				{
					msg result;
					result = (msg)x.getValOnPort("in", i);
					System.out.println("sum : " + result.ans);
					holdIn("active", 0); // ��� ������ �ٽ� active ���·� õ��
				}
			}
		}
		else if(phaseIs("sent_3")) // ���� �߰��� �𵨿� ������ ó��
		{
			for (int i = 0; i < x.getLength(); i++) 
			{
				if (messageOnPort(x, "in", i)) 
				{
					msg result; 
					result = (msg)x.getValOnPort("in", i);
					System.out.println(num1 + " * " + num2 + " = " + result.ans);
					holdIn("finished", INFINITY);
				}
			}
		}
	}

	/* INTERNAL TRANSTION FUNCTION */
	public void deltint()
	{
		if (phaseIs("active") && flag == 0)
		{
			holdIn("sent_1", INFINITY);
			flag = 1;
		}
		else if(phaseIs("active") && flag == 1)
		{
			holdIn("sent_2", INFINITY);
			flag = 2;
		}
		else if(phaseIs("active") && flag == 2) // ���� �߰��� ���� ���� ó��
			holdIn("sent_3", INFINITY);
	}

	/* OUT FUNCTION */
	public message out() 
	{
		message m = new message();

		if (phaseIs("active") && flag == 0)
			m.add(makeContent("out", new msg(num1 + "+" + num2, 1, num1, num2)));
		else if (phaseIs("active") && flag == 1)
			m.add(makeContent("out", new msg("num array", 2, num)));
		else if (phaseIs("active") && flag == 2)
			m.add(makeContent("out", new msg(num1 + "*" + num2, 3, num1, num2))); 
		// out��Ʈ 3���� �޼����� ������
		return m;
	}

	public String getTooltipText() { return super.getTooltipText(); }
}
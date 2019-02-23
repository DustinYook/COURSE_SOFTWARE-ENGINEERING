package HW5;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class calc extends ViewableAtomic // processor
{
	/* ������ �κ� */
	protected Request calc; // ����� ó���ϴ� ��ü (�̸� �ٲ�)
	protected double processing_time;
	double result; // ����� ������ ����

	/* Overloaded Constructor */
	public calc()
	{
		/* ������ �κ� */
		this("calc", 20); // ĸ���� calc�� �ٲ���
	}
	public calc(String name, double Processing_time)
	{
		super(name);
		addInport("in");
		addOutport("out");
		processing_time = Processing_time;
	}
  
	/* Initialize Function */
	public void initialize()
	{
		/* ������ �κ� */
		calc = new Request("", 0, ' ', 0);
		// Request �������� ����° ���ڸ� �߰��� (�����ڸ� ���� �� �ְ� �ϱ� ����)
		holdIn("passive", INFINITY);
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
					calc = (Request) x.getValOnPort("in", i); // Request Ÿ������ ��������ȯ
					
					/* ������ �κ� */
					switch(calc.oper) // calc�� �Էµ� �����ڿ� ���� �б� �� ó������
					{
					case '+': // ���ϱ� ����
						result = calc.num1 + calc.num2;
						break;
					case '-': // ���� ����
						result = calc.num1 - calc.num2;
						break;
					case '*': // ���ϱ� ����
						result = calc.num1 * calc.num2;
						break;
					case '/': // ������ ����
						result = calc.num1 / calc.num2;
						break;
					}
					holdIn("busy", processing_time);
				}
			}
		}
	}
  
	/* Internal Transition Function */
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			calc = new Request("", 0,' ', 0); // job �ʱ�ȭ �Ҵ�
			// Request �������� ����° ���ڸ� �߰��� (�����ڸ� ���� �� �ְ� �ϱ� ����)
			holdIn("passive", INFINITY);
		}
	}

	/* Out Function */
	public message out()
	{
		message m = new message();
		
		/* ������ �κ� */
		if (phaseIs("busy"))
			m.add(makeContent("out", new entity(Double.toString(result))));
		// double�� result�� string���� �ٲپ� �޼����� ������
		return m;
	}
	public String getTooltipText()
	{
		return
		super.getTooltipText()
		+ "\n" + "job: " + calc.getName(); // �̸��� �޾ƿ�
	}
}
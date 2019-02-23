package HW2; // ���� �߰��� ��Ű���� �̸��� �ۼ�
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class modified_genr extends ViewableAtomic // generator�� ViewableAtomic�̶�� Ŭ������ ��ӹ���
{
	protected double int_arr_time; // �� ���� �ð����� job�� �������� �����ϴ� ����
	protected int count; // job�� ������ ī��Ʈ �� �� ����ϴ� ����
	
	/* Generator's overloaded constructor */
	public modified_genr() 
	{
		this("g", 40); // out�� 40���� �ٲٷ��� ���� �ֱ⸦ ���� (20���� 40���� �ٲ�)
		// "g"�� ViewableAtomic�� �̸��� �ְ�, �ñ׸� ���� 40���� �����Ѵٴ� �ǹ� 
	}
	public modified_genr(String name, double Int_arr_time) // �����ڿ����� ��Ʈ, ���;���̹� �ϳ� ����
	{
		super(name);
		/* �����ð��� �ۼ��� �κ� */
		addOutport("out"); // out port ����
		addInport("in"); // in port ����
		int_arr_time = Int_arr_time; // ���ڷ� ���� ���� int_arr_time�� ������
	}
	
	/* Generator's four major functions */
	
	// 1) Initialize Function
	public void initialize()
	{
		count = 0; // ī��Ʈ�� 0���� �����ϰ� �� (1���� 0���� �ٲ�)
		holdIn("active", int_arr_time); // �ñ׸� �ð� ��ŭ active�� ����
	}

	// 2) External Transition Function : �ܺηκ��� �޼����� ���� ���� (�ֱ������� üũ)
	public void deltext(double e, message x)
	{
		Continue(e);
		// phaseIs()�� ���� �ڽ��� ���°� ��� üũ�Ͽ� 1 �Ǵ� 0�� ��ȯ�ϴ� ����
		if (phaseIs("active"))
		{ 
			// ������ ��� in��Ʈ�� 1������ �� ���� ���ư����� in��Ʈ�� 3���� �� ���� ���ư��� ��
			for (int i = 0; i < x.getLength(); i++)
			{ 
				if (messageOnPort(x, "in", i)) // ���߶�� ��ȣ�� �ִ� ���
				{
					holdIn("stop", INFINITY); // job ������ ���߶�� �ǹ�
				}
			}
		}
	}
	
	// 3) Out Function: ó������� ���� �޼����� �ٸ� atomic model�� ����
	public message out()
	{
		message m = new message(); // �޼����� ����
		m.add(makeContent("out", new entity("job" + count))); // �޼����� �������� �ۼ�
		// out��Ʈ�� ������ �Ű�, �ű⿡ �ƾ� ������ �޼����� entity��� ���ε� job1�� ���� �������� ������
		return m;
	}	
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time
        + "\n" + " count: " + count;
		// ViewableAtomic���� ������ ���� �θ���
		// arrival time�� count�� �����ִ� ����
	}

	// 4) Internal Transition Function: Out Function�� ���� �ڵ�ȣ��, ����ó�� (�ֱ������� ����)
	public void deltint()
	{
		if (phaseIs("active"))
		{
			// ������ Out Function�� ���� �Ҹ��� ������ 1�� ���� ���������ִ� ����
			count = count + 1; // active ���¶�� 1����
			// holdIn("active", int_arr_time); // holdIn("active", 90);�� ���̵� �� �� ����
			if(count % 2 == 1) // Ȧ���� �� 50 ���� (���� �κ� �߰�)
				holdIn("active", 50); 
			else // ¦�� �� �� 40 ���� (���� �κ� �߰�)
				holdIn("active", 40);
		}
	}
}
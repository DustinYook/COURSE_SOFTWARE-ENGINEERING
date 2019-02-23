package Lab3; // ���� �߰��� ��Ű�� �̸��� �ۼ�
import simView.*; // ���迡 �ȳ��� 
import genDevs.modeling.*; // ���迡 �ȳ���
import GenCol.*; // ���迡 �ȳ���

public class genr extends ViewableAtomic // generator�� ViewableAtomic�̶�� Ŭ������ ��ӹ���
{
	protected double int_arr_time; // �� ���� �ð����� job�� �������� �����ϴ� ����
	protected int count; // job�� ������ ī��Ʈ �� �� ����ϴ� ����
	
	/* Overloaded Constructor */
	public genr() 
	{
		this("g", 20);
		// "g"�� ViewableAtomic�� �̸��� �ְ�, �ñ׸� ���� 20���� �����Ѵٴ� �ǹ� 
	}
	
	/* Overloaded Constructor */
	public genr(String name, double Int_arr_time) // �����ڿ����� ��Ʈ, ���;���̹� �ϳ� ����
	{
		super(name);
		/* �����ð��� �ۼ��� �κ� */
		addOutport("out"); // out port ����
		addInport("in"); // in port ����
		int_arr_time = Int_arr_time; // ���ڷ� ���� ���� int_arr_time�� ������
	}
	
	// Generator's four major functions
	
	/* Initialize Function */
	public void initialize()
	{
		count = 1; // ī��Ʈ�� 1���� �����ϰ� ��
		holdIn("active", int_arr_time); // �ñ׸� �ð� ��ŭ active�� ����
	}

	/* External Transition Function */ 
	public void deltext(double e, message x) // �ܺηκ��� �޼����� ���� ���� (�ֱ������� üũ)
	{
		Continue(e);
		if (phaseIs("active")) // ���� ���� üũ�Ͽ� 1 �Ǵ� 0 ��ȯ
		{ 
			// ������ ��� in��Ʈ�� 1������ �� ���� ���ư����� in��Ʈ�� 3���� �� ���� ���ư��� ��
			for (int i = 0; i < x.getLength(); i++)
			{ 
				if (messageOnPort(x, "in", i)) // ���߶�� ��ȣ�� �ִ� ���
				{
					 holdIn("stop", INFINITY); // stop���·� ��ȯ�Ǵ� ���� �������� �ּ�ó��
				}
			}
		}
	}
	
	/* Internal Transition Function */
	public void deltint() // Out Function�� ���� �ڵ�ȣ��, ����ó�� (�ֱ������� ����)
	{
		if (phaseIs("active"))
		{
			// ������ Out Function�� ���� �Ҹ��� ������ 1�� ���� ���������ִ� ����
			count = count + 1; // active ���¶�� 1����
			holdIn("active", int_arr_time); // holdIn("active", 90);�� ���̵� �� �� ����
		}
	}
	
	/* Out Function */
	public message out() // ó������� ���� �޼����� �ٸ� atomic model�� ����
	{
		message m = new message(); // �޼����� ����
		m.add(makeContent("out", new entity("job" + count))); // �޼����� �������� �ۼ�
		// out��Ʈ�� ������ �Ű�, �ű⿡ �ƾ� ������ �޼����� entity��� ���ε� job1�� ���� �������� ������
		return m;
	}	

	/* Tool Tip */
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time
        + "\n" + " count: " + count;
		// ViewableAtomic���� ������ ���� �θ���
		// arrival time�� count�� �����ִ� ����
	}
}
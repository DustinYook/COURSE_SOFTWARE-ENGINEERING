package HW8;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class router extends ViewableAtomic
{
	
	protected Queue q;
	protected packet packet; // �̸� �ٲ�
	protected double processing_time;
	
	public router()
	{
		this("procQ", 20);
	}

	public router(String name, double Processing_time)
	{
		super(name);
		addInport("in"); // sender�� ����Ǵ� ��Ʈ
		
		for(int i = 1; i <= 5; i++)
			addOutport("out" + i); // receiver�� ����Ǵ� ��Ʈ ����
		
		addOutport("out"); // sender�� ����Ǵ� ��Ʈ
		
		processing_time = Processing_time;
	}
	
	/* Initialize Function */
	public void initialize()
	{
		q = new Queue();
		packet = new packet("", 0); // �̸� �ٲ�
		
		holdIn("passive", INFINITY);
	}

	/* External Transition Function */
	public void deltext(double e, message x)
	{
		System.out.println("#deltext"); // deltext���� ǥ��
		Continue(e);
		if (phaseIs("passive")) // phase�� passive�� �״�� ��
		{
			for (int i = 0; i < x.size(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					packet = (packet)x.getValOnPort("in", i); // packet���� �ٲ�
					q.add(packet); // ť�� ����
					
					/* ���� �Ʒ����� �ٲ� */
					if(q.size() <= 5) // ��ȣ�� ��
					{
						holdIn("passive", INFINITY); // 1���� 5���� passive ���� ���� (5�� ������ �ϴ� passive ���� ����)
						System.out.println(q.size() + " : " + phase);
						
						if(q.size() == 5)
							holdIn("sending", processing_time); // 5�� �Ǹ� sending���� ����õ��
					}
				}
			}
		}
	}
	
	/* Internal Transition Function */
	public void deltint(){}

	/* Out Function */
	public message out()
	{
		message m = new message();
		System.out.println("#out");
		
		if(phaseIs("sending")) // sending ����
		{
			System.out.println(q.size() + " : " + phase);
			if(!q.isEmpty())
			{
				packet = (packet)q.removeFirst(); // ť���� ����
				
				int portNum = packet.dest; // ������ �ּ� �޾ƿ�
				
				m.add(makeContent("out" + portNum, packet)); // �޼��� �ۼ�
				holdIn("sending", processing_time); // ��������
			}
			else
			{
				m.add(makeContent("out", new packet("done", 0))); // �� ���´ٰ� ��Ŷ������
				
				holdIn("passive", INFINITY); // sender�κ��� �޼��� ���� ������ ��ٸ�
			}
		}
		return m;
	}	
	
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + "queue length: " + q.size()
        + "\n" + "queue itself: " + q.toString();
	}
}
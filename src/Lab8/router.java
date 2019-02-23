package Lab8;
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
	
	public void initialize()
	{
		q = new Queue();
		packet = new packet("", 0); // �̸� �ٲ�
		
		holdIn("passive", INFINITY);
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive")) // �״�� ��!
		{
			for (int i = 0; i < x.size(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					packet = (packet)x.getValOnPort("in", i); // �ٲ�
					
					q.add(packet); //
					
					if(q.size() < 5) // 5���� ������ passive
						holdIn("passive", INFINITY);
					else if(q.size() == 5) // 5�� ���� sending���� �ٲ�
						holdIn("sending", processing_time);
				}
			}
		}
	}
	
	public void deltint(){}

	public message out()
	{
		message m = new message();
		
		if(phaseIs("sending")) // sending ����
		{
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
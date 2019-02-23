package Lab8;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class router extends ViewableAtomic
{
	
	protected Queue q;
	protected packet packet; // 이름 바꿈
	protected double processing_time;
	
	public router()
	{
		this("procQ", 20);
	}

	public router(String name, double Processing_time)
	{
		super(name);
    
		addInport("in"); // sender와 연결되는 포트
		
		for(int i = 1; i <= 5; i++)
			addOutport("out" + i); // receiver와 연결되는 포트 생성
		
		addOutport("out"); // sender와 연결되는 포트
		
		processing_time = Processing_time;
	}
	
	public void initialize()
	{
		q = new Queue();
		packet = new packet("", 0); // 이름 바꿈
		
		holdIn("passive", INFINITY);
	}

	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive")) // 그대로 둠!
		{
			for (int i = 0; i < x.size(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					packet = (packet)x.getValOnPort("in", i); // 바꿈
					
					q.add(packet); //
					
					if(q.size() < 5) // 5보다 작으면 passive
						holdIn("passive", INFINITY);
					else if(q.size() == 5) // 5인 순간 sending으로 바뀜
						holdIn("sending", processing_time);
				}
			}
		}
	}
	
	public void deltint(){}

	public message out()
	{
		message m = new message();
		
		if(phaseIs("sending")) // sending 상태
		{
			if(!q.isEmpty())
			{
				packet = (packet)q.removeFirst(); // 큐에서 빼옴
				
				int portNum = packet.dest; // 목적지 주소 받아옴
				
				m.add(makeContent("out" + portNum, packet)); // 메세지 작성
				holdIn("sending", processing_time); // 상태유지
			}
			else
			{
				m.add(makeContent("out", new packet("done", 0))); // 다 보냈다고 패킷보냄ㄴ
				
				holdIn("passive", INFINITY); // sender로부터 메세지 받을 때까지 기다림
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
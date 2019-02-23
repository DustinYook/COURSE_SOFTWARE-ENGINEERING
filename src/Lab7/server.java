package Lab7;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class server extends ViewableAtomic // server는 processor
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
		// 초기에 Wait으로 블록되어 있음
	}

	/* External Transition Function */
	public void deltext(double e, message x)
	{
		Continue(e);
		
		// SYN이 들어왔는지 확인
		if (phaseIs("Wait"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					/* 여기 들어왔다는 것은 Wait 상태에서 메세지가 들어온 경우 */
					
					job = (packet)x.getValOnPort("in", i); 
					// 반드시 packet으로 강제형변환 해주어야 함
					
					holdIn("Wait", processing_time);
					// Wait 상태여야 Out Function이 일을 처리함
				}
			}
		}
		// ACK이 들어왔는지 확인
		else if(phaseIs("SYN-received")) 
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					/* 여기 들어왔다는 것은 SYN-received 상태에서 메세지가 들어온 경우 */
					
					job = (packet)x.getValOnPort("in", i);
					// 반드시 packet으로 강제형변환 해주어야 함
					
					holdIn("Established", INFINITY); 
					// Established 상태로 블록됨
				}
			}
		}
	}
  
	/* Internal Transition Function */
	public void deltint()
	{
		if (phaseIs("Wait"))
			holdIn("SYN-received", processing_time); // 여기 바뀜
	}

	/* Out Function */
	public message out()
	{
		message m = new message();
		if (phaseIs("Wait"))
			m.add(makeContent("out", new packet("SYN-ACK"))); // 여기 바뀜
		return m; // 메세지를 보냄
	}

	public String getTooltipText()
	{
		return
		super.getTooltipText()
		+ "\n" + "job: " + job.getName();
	}
}
package Lab7;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class client extends ViewableAtomic // client는 generator
{
	protected double int_arr_time;
	protected int count;
  
	public client() 
	{
		this("client", 30);
	}
  
	public client(String name, double Int_arr_time)
	{
		super(name);
   
		addOutport("out");
		addInport("in");
    
		int_arr_time = Int_arr_time;
	}
  
	/* Initialize Function */
	public void initialize()
	{
		count = 1;
		
		holdIn("Active", int_arr_time); 
		// 생성시점에 int_arr_time만큼 Active 유지
	}
 
	/* External Transition Function */
	public void deltext(double e, message x)
	{
		Continue(e);
		
		// SYN을 보내고 Sent 상태로 server의 응답을 기다림
		if (phaseIs("Sent"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					holdIn("Sent", int_arr_time);
					// int_arr_time만큼 Sent 유지 
				}
			}
		}
	}

	/* Internal Transition Function */
	public void deltint()
	{
		// State가 Active일 때 
		if(phaseIs("Active")) // 주의: out function에서 hold in 하면 안됨 -> server도 바꿔줄 것
			holdIn("Sent", INFINITY); // 일종의 block함수
		else if(phaseIs("Sent")) // State가 Sent일 때
			holdIn("Connected", INFINITY);
		// INFINITY의 의미: 다른 Atomic Model로부터 입력값이 들어와야만 깨어날 수 있음
					// 주의할 점은 스스로 깨어날 수 없다는 것
	}

	/* Out Function */
	public message out()
	{
		message m = new message();
		if(phaseIs("Active")) // State가 Active일 때 
			m.add(makeContent("out", new packet("SYN")));
		else if(phaseIs("Sent")) // State가 Sent일 때
			m.add(makeContent("out", new packet("ACK")));
		return m;
	}
  
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time
        + "\n" + " count: " + count;
	}
}
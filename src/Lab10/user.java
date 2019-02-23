package Lab10;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class user extends ViewableAtomic
{
	public user() 
	{
		this("user");
	}
  
	public user(String name)
	{
		super(name);
   
		addOutport("out"); // in 포트가 없음
	}
  
	public void initialize()
	{
		holdIn("active", 0); // 0으로 설정
	}
  
	public void deltext(double e, message x) { /* in port 없기 때문 */ }

	public void deltint()
	{
		if (phaseIs("active"))
			holdIn("Switch On", 15); // 시간은 임의로 줌 -> 15이후 out 부름
		else if (phaseIs("Switch On"))
			holdIn("temp_sent", 85); // 상태전이 -> 85 이후 out 부름
		else if(phaseIs("temp_sent"))
			holdIn("Switch Off", INFINITY);
	}

	public message out()
	{
		message m = new message();
		
		if(phaseIs("active"))
			m.add(makeContent("out", new msg("on", true))); // 메세지 보냄
		else if(phaseIs("Switch On"))
			m.add(makeContent("out", new msg("set temperature : 24", 24))); // 24도 세팅
		else if(phaseIs("temp_sent"))
			m.add(makeContent("out", new msg("off", false))); // 24도 세팅
		
		return m;
	}
  
	public String getTooltipText() { return super.getTooltipText(); }
}
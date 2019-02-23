package Lab10_m;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class user extends ViewableAtomic
{
	int user_temp; // #새로 추가
	public user() { this("user", 24); }
	public user(String name, int _user_temp)
	{
		super(name);
   
		addOutport("out"); // in 포트가 없음
		user_temp = _user_temp; // #여기서 초기화
	}
  
	public void initialize() 
	{ 
		holdIn("active", 0); // 0으로 설정 
	}
  
	public void deltext(double e, message x) { /* in port 없기 때문 */ }

	public void deltint()
	{
		if (phaseIs("active"))
			holdIn("Switch On", 100); // #100으로 바꿈
		else if (phaseIs("Switch On"))
			holdIn("Switch Off", 0);
	}

	public message out()
	{
		message m = new message();
		
		if(phaseIs("active"))
			m.add(makeContent("out", new msg("switch : on, set temp:" + user_temp, user_temp))); // active 상태 온도랑 같이 보냄
		else if(phaseIs("Switch On"))
			m.add(makeContent("out", new msg("switch : off", false))); // 꺼 버림
		
		return m;
	}
  
	public String getTooltipText() { return super.getTooltipText(); }
}
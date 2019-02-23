package Lab3;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class proc extends ViewableAtomic
{
	protected entity job; // ex) job1, job2, ...
	protected double processing_time; // 처리시간

	/* Overloaded Constructor */
	public proc()
	{
		this("proc", 20);
	}

	/* Overloaded Constructor */
	public proc(String name, double Processing_time)
	{
		super(name); // 프로세서의 이름을 설정
		
		addInport("in"); // in포트 생성
		addOutport("out"); // out포트 생성
		
		processing_time = Processing_time; // 시그마설정
	}
  
	/* Initialize Function */
	public void initialize()
	{
		job = new entity("");
		
		holdIn("passive", INFINITY); 
		// 의미: 초기에는 무한정 passive phase를 유지
	}

	/* External Transition Function */
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive")) // external은 자신의 상태를 먼저 체크 -> 처리 중인 경우 busy
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					job = x.getValOnPort("in", i); // in포트에서 job을 받아옴
					holdIn("busy", processing_time); 
					// processing_time 동안 busy상태 유지
				}
			}
		}
	}
  
	/* Internal Transition Function */
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			job = new entity(""); // job 새로 생성
			holdIn("passive", INFINITY); // passive 상태 유지
		}
	}
	
	/* Out Function */
	public message out() // 자동적으로 internal 호출
	{
		message m = new message(); // 빈 메세지 생성
		
		if (phaseIs("busy"))
			m.add(makeContent("out", job)); // generator와 차이점!
		
		return m;
	}
	
	/* Tool Tip */
	public String getTooltipText()
	{
		return super.getTooltipText() + "\n" 
				+ "job: " + job.getName();
	}
}
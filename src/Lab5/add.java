package Lab5;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class add extends ViewableAtomic // processor
{
	protected addRequest addNum; 
	protected double processing_time;
	int result; // 결과를 저장할 변수

	/* Overloaded Constructor */
	public add()
	{
		this("add", 20); // add로 캡션설정
	}

	/* Overloaded Constructor */
	public add(String name, double Processing_time)
	{
		super(name);
		
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	/* Initialize Function */
	public void initialize()
	{
		addNum = new addRequest("", 0, 0);
		
		holdIn("passive", INFINITY); // 초기 phase는 passive
	}

	/* External Transition Function */
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive"))
		{
			for (int i = 0; i < x.getLength(); i++)
			{
				if (messageOnPort(x, "in", i))
				{
					addNum = (addRequest) x.getValOnPort("in", i); // 강제형변환
					
					result = addNum.num1 + addNum.num2; // 처리수행
					
					holdIn("busy", processing_time);
				}
			}
		}
	}
  
	/* Internal Transition Function */
	public void deltint()
	{
		if (phaseIs("busy")) // 처리완료하게 되면 busy에서 passive로 상태천이 발생
		{
			addRequest addNum = new addRequest("", 0, 0); // job 초기화 할당
			
			holdIn("passive", INFINITY); // busy에서 passive로 상태천이
		}
	}

	/* Out Function */
	public message out()
	{
		message m = new message();
		if (phaseIs("busy")) // phase 파악하는 것 중요!
		{
			m.add(makeContent("out", new entity(Integer.toString(result)))); 
			// result를 string 형태로 wrap하여 다른 atomic model에 전달
		}
		return m;
	}

	/* Tool Tip */
	public String getTooltipText() // hover할 때 나오는 툴팁
	{
		return
		super.getTooltipText()
		+ "\n" + "job: " + addNum.getName(); //
	}
}
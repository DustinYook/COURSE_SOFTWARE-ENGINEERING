package HW5;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class calc extends ViewableAtomic // processor
{
	/* 수정한 부분 */
	protected Request calc; // 계산을 처리하는 객체 (이름 바꿈)
	protected double processing_time;
	double result; // 결과를 저장할 변수

	/* Overloaded Constructor */
	public calc()
	{
		/* 수정한 부분 */
		this("calc", 20); // 캡션을 calc로 바꿔줌
	}
	public calc(String name, double Processing_time)
	{
		super(name);
		addInport("in");
		addOutport("out");
		processing_time = Processing_time;
	}
  
	/* Initialize Function */
	public void initialize()
	{
		/* 수정한 부분 */
		calc = new Request("", 0, ' ', 0);
		// Request 생성자의 세번째 인자를 추가함 (연산자를 받을 수 있게 하기 위함)
		holdIn("passive", INFINITY);
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
					calc = (Request) x.getValOnPort("in", i); // Request 타입으로 강제형변환
					
					/* 수정한 부분 */
					switch(calc.oper) // calc에 입력된 연산자에 따라 분기 및 처리진행
					{
					case '+': // 더하기 연산
						result = calc.num1 + calc.num2;
						break;
					case '-': // 빼기 연산
						result = calc.num1 - calc.num2;
						break;
					case '*': // 곱하기 연산
						result = calc.num1 * calc.num2;
						break;
					case '/': // 나누기 연산
						result = calc.num1 / calc.num2;
						break;
					}
					holdIn("busy", processing_time);
				}
			}
		}
	}
  
	/* Internal Transition Function */
	public void deltint()
	{
		if (phaseIs("busy"))
		{
			calc = new Request("", 0,' ', 0); // job 초기화 할당
			// Request 생성자의 세번째 인자를 추가함 (연산자를 받을 수 있게 하기 위함)
			holdIn("passive", INFINITY);
		}
	}

	/* Out Function */
	public message out()
	{
		message m = new message();
		
		/* 수정한 부분 */
		if (phaseIs("busy"))
			m.add(makeContent("out", new entity(Double.toString(result))));
		// double인 result를 string으로 바꾸어 메세지에 기입함
		return m;
	}
	public String getTooltipText()
	{
		return
		super.getTooltipText()
		+ "\n" + "job: " + calc.getName(); // 이름을 받아옴
	}
}
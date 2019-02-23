package HW2; // 새로 추가한 패키지의 이름을 작성
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class modified_genr extends ViewableAtomic // generator는 ViewableAtomic이라는 클래스를 상속받음
{
	protected double int_arr_time; // 몇 단위 시간마다 job을 생성할지 설정하는 변수
	protected int count; // job의 개수를 카운트 할 때 사용하는 변수
	
	/* Generator's overloaded constructor */
	public modified_genr() 
	{
		this("g", 40); // out을 40으로 바꾸려면 생성 주기를 조정 (20에서 40으로 바꿈)
		// "g"를 ViewableAtomic의 이름에 넣고, 시그마 값을 40으로 설정한다는 의미 
	}
	public modified_genr(String name, double Int_arr_time) // 생성자에서는 포트, 인터어라이벌 하나 만듦
	{
		super(name);
		/* 수업시간에 작성한 부분 */
		addOutport("out"); // out port 생성
		addInport("in"); // in port 생성
		int_arr_time = Int_arr_time; // 인자로 받은 값을 int_arr_time에 설정함
	}
	
	/* Generator's four major functions */
	
	// 1) Initialize Function
	public void initialize()
	{
		count = 0; // 카운트를 0부터 시작하게 함 (1에서 0으로 바꿈)
		holdIn("active", int_arr_time); // 시그마 시간 만큼 active를 유지
	}

	// 2) External Transition Function : 외부로부터 메세지를 전달 받음 (주기적으로 체크)
	public void deltext(double e, message x)
	{
		Continue(e);
		// phaseIs()는 지금 자신의 상태가 어떤지 체크하여 1 또는 0을 반환하는 역할
		if (phaseIs("active"))
		{ 
			// 지금의 경우 in포트가 1개여서 한 개만 돌아가지만 in포트가 3개면 세 개가 돌아가야 함
			for (int i = 0; i < x.getLength(); i++)
			{ 
				if (messageOnPort(x, "in", i)) // 멈추라는 신호가 있는 경우
				{
					holdIn("stop", INFINITY); // job 생성을 멈추라는 의미
				}
			}
		}
	}
	
	// 3) Out Function: 처리결과를 담은 메세지를 다른 atomic model로 전달
	public message out()
	{
		message m = new message(); // 메세지를 생성
		m.add(makeContent("out", new entity("job" + count))); // 메세지에 컨텐츠를 작성
		// out포트로 내보낼 거고, 거기에 싣어 보내는 메세지는 entity라는 얘인데 job1과 같은 형식으로 내보냄
		return m;
	}	
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time
        + "\n" + " count: " + count;
		// ViewableAtomic에서 보여줄 것을 부르고
		// arrival time과 count를 보여주는 역할
	}

	// 4) Internal Transition Function: Out Function에 의해 자동호출, 내부처리 (주기적으로 수행)
	public void deltint()
	{
		if (phaseIs("active"))
		{
			// 원래는 Out Function이 먼저 불리기 때문에 1을 먼저 증가시켜주는 것임
			count = count + 1; // active 상태라면 1증가
			// holdIn("active", int_arr_time); // holdIn("active", 90);와 같이도 쓸 수 있음
			if(count % 2 == 1) // 홀수일 때 50 생성 (여기 부분 추가)
				holdIn("active", 50); 
			else // 짝수 일 때 40 생성 (여기 부분 추가)
				holdIn("active", 40);
		}
	}
}
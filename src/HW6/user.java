// user.java : generator atomic model
package HW6;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class user extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int count;
	
	protected Queue queue; // 큐 정의 -> 이 패키지의 큐를 이용
	// Queue<int> q = new Queue<double>(); // 자바 기본 문법과 다름
  
	public user() 
	{
		this("genr", 30);
	}
  
	public user(String name, double Int_arr_time)
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
		
		// 여기 추가
		queue = new Queue(); // 큐 생성
		
		queue.add(2); // 데이터 삽입
		queue.add(6);
		queue.add(4);
		queue.add(4);
		queue.add(7);
		queue.add(1);
		queue.add(8);
		queue.add(5);
		queue.add(2);
		queue.add(1);
		
		holdIn("active", int_arr_time);
	}
  
	/* External Transition Function */
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive")) // passive : 대기 중인 상태에서 
		{
			for (int i = 0; i < x.getLength(); i++) 
			{
				if (messageOnPort(x, "in", i)) // 메세지가 들어오면
				{
					holdIn("finished", INFINITY); // passive에서 finished로 상태천이 
				}
			}
		}
	}

	/* Internal Transition Function */
	public void deltint()
	{
		if (phaseIs("active"))
		{
			count = count + 1;
			
			holdIn("active", int_arr_time);
		}
	}

	/* Out Function */
	public message out()
	{
		message m = new message();
		
		// 여기 추가
		if(queue.size() > 1) // 중요) 왜 '0'일 때가 아닌 '1'일 때  건드냐? - 마지막일 때 수행할 처리가 있기 때문 
		{
			int num = (int) queue.removeFirst(); // dequeue를 수행한 후 해당 값을 변수에 저장
			m.add(makeContent("out", new job(Integer.toString(num), num))); // 파라미터 2개
		}
		else if(queue.size() == 1) // 마지막일 때 할 처리가 있기 때문
		{
			int num = (int)queue.removeFirst(); // dequeue를 수행한 후 해당 값을 변수에 저장
			m.add(makeContent("out", new job(Integer.toString(num) + ", Last", num, true))); // 파라미터 3개
			
			// 큐가 빈 경우 active에서 passive로 천이
			holdIn("passive", INFINITY); // active에서 passive 상태로 천이
		}
		return m;
	}
	
	/* Tool tip */
	public String getTooltipText()
	{
		return
        super.getTooltipText()
        + "\n" + " int_arr_time: " + int_arr_time
        + "\n" + " count: " + count;
	}
}
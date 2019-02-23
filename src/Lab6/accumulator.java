// acccumulator.java : processor atomic model
package Lab6;
import genDevs.modeling.*;
import GenCol.*;
import simView.*;

public class accumulator extends ViewableAtomic
{
  
	protected job job; // entity를 job으로 바꿈
	protected double processing_time;
	
	// 여기 추가
	protected Queue q; // processor의 큐 생성 (기존의 자바 라이브러리가 아닌 GenCol에 정의된 것임!)
	protected int result; // 누적 연산한 결과를 저장할 변수

	/* Overloaded Constructor */
	public accumulator()
	{
		this("proc", 20);
	}

	/* Overloaded Constructor */
	public accumulator(String name, double Processing_time)
	{
		super(name);
    
		addInport("in");
		addOutport("out");
		
		processing_time = Processing_time;
	}
  
	/* Initialize Function */
	public void initialize()
	{
		q = new Queue(); // 빈 큐 생성
		job = new job("", 0, false); // 빈 job 생성
		result = 0; // 결과저장 변수 초기화
		
		holdIn("passive", INFINITY);
	}

	/* External Transition Function */
	public void deltext(double e, message x)
	{
		Continue(e);
		if (phaseIs("passive")) // 입력대기 상태
		{
			for (int i = 0; i < x.getLength(); i++) // 입력이 있나?
			{
				if (messageOnPort(x, "in", i))
				{
					job = (job)x.getValOnPort("in", i); // (job)으로 coercion
					q.add(job); // 큐에 job 추가
					
					holdIn("passive", processing_time); // passive 상태 유지
				}
			}
		}
	}
  
	/* Internal Transition Function */
	public void deltint()
	{
		if (phaseIs("passive")) // 입력대기 상태
		{
			job = (job) q.getLast(); 
			// System.out.println(job + " " + q.size()); // 디버깅 위한 코드
			// System.out.println(job.isLast); // 디버깅 위한 코드
			
			if(job.isLast) // 큐의 마지막인지 확인
			{
				int qSize = q.size(); // 큐의 사이즈가 가변적이기 때문에 일단 받아서 저장해주어야 함
				
				// processor는 큐의 개수가 몇 개인지 모르기 때문에 고정크기로 '10'과 같이 쓰면 틀린다!
				for(int i = 0; i < qSize; i++) 
				{
					job = (job)q.removeFirst(); // dequeue를 수행한 값을 변수에 저장
					result = result + job.num; // dequeue된 element의 값을 누적덧셈
					
					holdIn("processing", processing_time); // passive에서 processing으로 상태천이
				}
			}
		}
	}

	/* Out Function */
	public message out()
	{
		message m = new message();
		
		if (phaseIs("processing")) // processing 상태인 경우 처리수행
		{
			m.add(makeContent("out", new job(Integer.toString(result), result))); // 메세지에 처리결과 기입
			
			holdIn("finished", INFINITY); // processing에서 finished로 상태천이
		}
		
		return m;
	}
	
	/* Tool Tip */
	public String getTooltipText()
	{
		return
		super.getTooltipText()
		+ "\n" + "queue length: " + q.size()
		+ "\n" + "queue itself: " + q.toString(); // 툴팁 표시내용 수정
	}
}
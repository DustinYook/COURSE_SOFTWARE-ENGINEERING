package Lab4;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class transd extends  ViewableAtomic // transducer
{
	protected Function arrived; // arrived된 job을 저장하는 리스트
	protected Function solved; // solved된 job을 저장하는 리스트
	protected double clock; // clock : 시뮬레이션의 하단부에 나오는 시간(클럭 값)
	protected double total_ta; // total_ta : total turn-around time
	protected double observation_time; // observation_time : 관찰하는 시간

	/* Overloaded Constructor */
	public transd()
	{
		this("transd", 200);
	}
	
	/* Overloaded Constructor */
	public transd(String name, double Observation_time)
	{
		super(name); // 이름캡션
		
		addOutport("out"); // out 포트
		addInport("ariv"); // ariv 포트
		addInport("solved"); // solved 포트
		
		arrived = new Function(); 
		solved = new Function();
		
		observation_time = Observation_time;
	}
	
	/* Initialize Function */
	public void initialize()
	{	
		clock = 0; // clock 시간 초기화
		total_ta = 0; // total turn-around time 시간 초기화
    
		arrived = new Function();
		solved = new Function();
		
		holdIn("on", observation_time); 
		// observation_time 동안 on 상태를 유지
	}

	/* External Transition Function */
	public void deltext(double e, message x)
	{
		clock = clock + e; // e는  경과된 clock 수를 의미
		System.out.println("\nclock : " + clock); // 현재 clock을 표시
		
		Continue(e);
		entity val; // job을 집어 넣을 곳
 
		if(phaseIs("on"))
		{ 
			// 여기부터 중요!
			for (int i = 0; i < x.size(); i++) // 포트 개수만큼 for문 돌림
			{ 
				// 1) ariv 포트에 메세지 있는지 확인 - 중요!
				if (messageOnPort(x, "ariv", i)) // generator부터 받은 job이 있는지 확인
				{
					val = x.getValOnPort("ariv", i); 
					// ariv 포트에 들어온 값을 저장
					
					arrived.put(val.getName(), new doubleEnt(clock)); 
					// arrived 리스트에 이름, 클럭 저장
					
					System.out.println("Func arrived : " + arrived);
					// arrived 리스트를 출력
				}
				
				// 2) solved 포트에 메세지 있는지 확인 - 중요!
				if (messageOnPort(x, "solved", i)) // processor로부터 처리된 job이 있는지 확인
				{
					val = x.getValOnPort("solved", i);
					// solved 포트에 들어온 값을 저장
					
					// 특정 job 이름을 키로 가진 것이 있는지 확인
					if (arrived.containsKey(val.getName())) 
					{
						// 저장 시 (name, clock)의 형태로 저장되어 있으므로 
						// 이렇게 묶여져 있는 것을 name을 키 값으로 하여 가져옴
						
						// 1) job의 name이 무엇인지 확인
						entity ent = (entity) arrived.assoc(val.getName()); // job name
						System.out.println("val.getname solved : " + val.getName()); 
						
						// 2) job이 언제 generator에게 보내진 것인지 확인
						doubleEnt num = (doubleEnt) ent; // job's arrived time
						System.out.println("entity solved : " + ent); 
						
						// 3) job이 언제 processor로부터 받은 것인지 확인
						double arrival_time = num.getv(); // 도착한 job의  solved 시간
						
						// 4) turn-around time을 구하는 처리를 수행
						double turn_around_time = clock - arrival_time; 
						// 처리완료 시간과 도착시간을 빼서 turn-around time을 구함
						total_ta = total_ta + turn_around_time; 
						// 누적하여 total turn-around time 구함
          
						// 5) solved list에 추가
						solved.put(val, new doubleEnt(clock));
						System.out.println("Func solved : " + solved);
					}
				}
			}
			show_state(); // 상태출력
		}
	}

	/* Internal Transition Function */
	public void deltint()
	{
		if (phaseIs("on"))
		{
			clock = clock + sigma;
			System.out.println("--------------------------------------------------------");
	   		show_state();
	   		System.out.println("--------------------------------------------------------");
	   		
	   		holdIn("off", 0);
		}
	}
	
	/* Out Function */
	public message out()
	{
		message m = new message();
		
		if (phaseIs("on"))
			m.add(makeContent("out", new entity("TA: " + compute_TA())));
		
		return m;
	}
	
	/* Show State */
	public void show_state()
	{
		System.out.println("state of  " + name + ": ");
		System.out.println("phase, sigma : " + phase + " " + sigma + " ");
		
		if (arrived != null && solved != null)
		{
			System.out.println("Total jobs arrived : "+ arrived.size());
			System.out.println("Total jobs solved : " + solved.size());
			System.out.println("AVG TA = " + compute_TA()); // Average Turn-around Time
			System.out.println("THRUPUT = " + compute_Thru() + '\n'); // Throughput
		}
	}	
	
	/* Turn-around Time Calculation */
	public double compute_TA() // turn-around time을 구하는 함수
	{
		double avg_ta_time = 0.0;
		
		if (!solved.isEmpty()) // 리스트가 비어있지 않으면
			avg_ta_time = ((double) total_ta) / solved.size();
		
		return avg_ta_time;
	}
	
	/* Throughput Calculation */
	public String compute_Thru() 
	{
		// throughput을 구하는 함수 (결과는 보여주기위한 목적으로 문자열로 반환)
		String thruput = "";
		
		if (clock > 0)
			thruput = solved.size() + " / " + clock;
		
		return thruput;
	}
	
	/* Tool Tip */
	public String getTooltipText()
	{
		String s = "";
		if (arrived != null && solved != null)
		{
			s = "\n" + "jobs arrived :" + arrived.size()
			+ "\n" + "jobs solved :" + solved.size()
			+ "\n" + "AVG TA = " + compute_TA()
			+ "\n" + "THRUPUT = " + compute_Thru();
		}
		return super.getTooltipText() + s;
	}
}
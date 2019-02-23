package Lab4;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class transd extends  ViewableAtomic // transducer
{
	protected Function arrived; // arrived�� job�� �����ϴ� ����Ʈ
	protected Function solved; // solved�� job�� �����ϴ� ����Ʈ
	protected double clock; // clock : �ùķ��̼��� �ϴܺο� ������ �ð�(Ŭ�� ��)
	protected double total_ta; // total_ta : total turn-around time
	protected double observation_time; // observation_time : �����ϴ� �ð�

	/* Overloaded Constructor */
	public transd()
	{
		this("transd", 200);
	}
	
	/* Overloaded Constructor */
	public transd(String name, double Observation_time)
	{
		super(name); // �̸�ĸ��
		
		addOutport("out"); // out ��Ʈ
		addInport("ariv"); // ariv ��Ʈ
		addInport("solved"); // solved ��Ʈ
		
		arrived = new Function(); 
		solved = new Function();
		
		observation_time = Observation_time;
	}
	
	/* Initialize Function */
	public void initialize()
	{	
		clock = 0; // clock �ð� �ʱ�ȭ
		total_ta = 0; // total turn-around time �ð� �ʱ�ȭ
    
		arrived = new Function();
		solved = new Function();
		
		holdIn("on", observation_time); 
		// observation_time ���� on ���¸� ����
	}

	/* External Transition Function */
	public void deltext(double e, message x)
	{
		clock = clock + e; // e��  ����� clock ���� �ǹ�
		System.out.println("\nclock : " + clock); // ���� clock�� ǥ��
		
		Continue(e);
		entity val; // job�� ���� ���� ��
 
		if(phaseIs("on"))
		{ 
			// ������� �߿�!
			for (int i = 0; i < x.size(); i++) // ��Ʈ ������ŭ for�� ����
			{ 
				// 1) ariv ��Ʈ�� �޼��� �ִ��� Ȯ�� - �߿�!
				if (messageOnPort(x, "ariv", i)) // generator���� ���� job�� �ִ��� Ȯ��
				{
					val = x.getValOnPort("ariv", i); 
					// ariv ��Ʈ�� ���� ���� ����
					
					arrived.put(val.getName(), new doubleEnt(clock)); 
					// arrived ����Ʈ�� �̸�, Ŭ�� ����
					
					System.out.println("Func arrived : " + arrived);
					// arrived ����Ʈ�� ���
				}
				
				// 2) solved ��Ʈ�� �޼��� �ִ��� Ȯ�� - �߿�!
				if (messageOnPort(x, "solved", i)) // processor�κ��� ó���� job�� �ִ��� Ȯ��
				{
					val = x.getValOnPort("solved", i);
					// solved ��Ʈ�� ���� ���� ����
					
					// Ư�� job �̸��� Ű�� ���� ���� �ִ��� Ȯ��
					if (arrived.containsKey(val.getName())) 
					{
						// ���� �� (name, clock)�� ���·� ����Ǿ� �����Ƿ� 
						// �̷��� ������ �ִ� ���� name�� Ű ������ �Ͽ� ������
						
						// 1) job�� name�� �������� Ȯ��
						entity ent = (entity) arrived.assoc(val.getName()); // job name
						System.out.println("val.getname solved : " + val.getName()); 
						
						// 2) job�� ���� generator���� ������ ������ Ȯ��
						doubleEnt num = (doubleEnt) ent; // job's arrived time
						System.out.println("entity solved : " + ent); 
						
						// 3) job�� ���� processor�κ��� ���� ������ Ȯ��
						double arrival_time = num.getv(); // ������ job��  solved �ð�
						
						// 4) turn-around time�� ���ϴ� ó���� ����
						double turn_around_time = clock - arrival_time; 
						// ó���Ϸ� �ð��� �����ð��� ���� turn-around time�� ����
						total_ta = total_ta + turn_around_time; 
						// �����Ͽ� total turn-around time ����
          
						// 5) solved list�� �߰�
						solved.put(val, new doubleEnt(clock));
						System.out.println("Func solved : " + solved);
					}
				}
			}
			show_state(); // �������
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
	public double compute_TA() // turn-around time�� ���ϴ� �Լ�
	{
		double avg_ta_time = 0.0;
		
		if (!solved.isEmpty()) // ����Ʈ�� ������� ������
			avg_ta_time = ((double) total_ta) / solved.size();
		
		return avg_ta_time;
	}
	
	/* Throughput Calculation */
	public String compute_Thru() 
	{
		// throughput�� ���ϴ� �Լ� (����� �����ֱ����� �������� ���ڿ��� ��ȯ)
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
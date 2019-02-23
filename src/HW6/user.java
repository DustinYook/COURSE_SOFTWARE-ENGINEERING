// user.java : generator atomic model
package HW6;
import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class user extends ViewableAtomic
{
	
	protected double int_arr_time;
	protected int count;
	
	protected Queue queue; // ť ���� -> �� ��Ű���� ť�� �̿�
	// Queue<int> q = new Queue<double>(); // �ڹ� �⺻ ������ �ٸ�
  
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
		
		// ���� �߰�
		queue = new Queue(); // ť ����
		
		queue.add(2); // ������ ����
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
		if (phaseIs("passive")) // passive : ��� ���� ���¿��� 
		{
			for (int i = 0; i < x.getLength(); i++) 
			{
				if (messageOnPort(x, "in", i)) // �޼����� ������
				{
					holdIn("finished", INFINITY); // passive���� finished�� ����õ�� 
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
		
		// ���� �߰�
		if(queue.size() > 1) // �߿�) �� '0'�� ���� �ƴ� '1'�� ��  �ǵ��? - �������� �� ������ ó���� �ֱ� ���� 
		{
			int num = (int) queue.removeFirst(); // dequeue�� ������ �� �ش� ���� ������ ����
			m.add(makeContent("out", new job(Integer.toString(num), num))); // �Ķ���� 2��
		}
		else if(queue.size() == 1) // �������� �� �� ó���� �ֱ� ����
		{
			int num = (int)queue.removeFirst(); // dequeue�� ������ �� �ش� ���� ������ ����
			m.add(makeContent("out", new job(Integer.toString(num) + ", Last", num, true))); // �Ķ���� 3��
			
			// ť�� �� ��� active���� passive�� õ��
			holdIn("passive", INFINITY); // active���� passive ���·� õ��
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
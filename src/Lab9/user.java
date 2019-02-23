package Lab9;

import simView.*;
import genDevs.modeling.*;
import GenCol.*;

public class user extends ViewableAtomic 
{
	boolean flag; // add로 보낼까 acc로 보낼까 결정하는 플래그
	int add_num1 = 2, add_num2 = 8; // 숫자
	int[] num = { 3, 6, 5, 5, 7, 1, 8, 5, 2, 1 }; // 배열

	// protected double int_arr_time;
	// protected int count;

	public user() 
	{
		this("user");
	}

	public user(String name) 
	{
		super(name);

		addOutport("out");
		addInport("in");

		// int_arr_time = Int_arr_time;
	}

	public void initialize() 
	{
		// count = 1;
		flag = false; //
		holdIn("active", 0); //
	}

	public void deltext(double e, message x) 
	{
		Continue(e);
		if (phaseIs("sent_1")) //  
		{
			for (int i = 0; i < x.getLength(); i++) 
			{
				if (messageOnPort(x, "in", i)) 
				{
					msg result; // 
					result = (msg)x.getValOnPort("in", i); //
					
					System.out.println(add_num1 + " + " + add_num2 + " = " + result.ans); //
					
					holdIn("active", 0); // 결과 받으면 다시 active 상태로 천이
				}
			}
		}
		else if (phaseIs("sent_2")) //  
		{
			for (int i = 0; i < x.getLength(); i++) 
			{
				if (messageOnPort(x, "in", i)) 
				{
					msg result; // 
					result = (msg)x.getValOnPort("in", i); //
					
					System.out.println("sum : " + result.ans); //
					
					holdIn("finished", INFINITY); //
				}
			}
		}
		
	}

	public void deltint()
	{
		if (phaseIs("active") && !flag)
		{
			if(!flag) flag = true;
			holdIn("sent_1", INFINITY);
		}
		else if(phaseIs("active") && flag)
			holdIn("sent_2", INFINITY);
	}

	public message out() 
	{
		message m = new message();

		if (phaseIs("active") && !flag) // active
			m.add(makeContent("out", new msg(add_num1 + "+" + add_num2, 1, add_num1, add_num2))); // 1이 destination 지정
		if (phaseIs("active") && flag) //
			m.add(makeContent("out", new msg("num array", 2, num))); // 2도 destination 지정

		return m;
	}

	public String getTooltipText() 
	{
		return super.getTooltipText(); //
	}
}
package Lab3;
import java.awt.*;
import simView.*;

public class gproc extends ViewableDigraph // Atomic���� �������ִ� ���� (coupling)
{

	public gproc()
	{
		super("gproc"); // caption�� ��Ÿ���� ����
		
		ViewableAtomic g = new genr("genr", 30); // �ʱ� sigma�� 30
		ViewableAtomic p = new proc("proc", 40); // �ʱ� sigma�� 40
		
		add(g); // �׷����� ǥ�� (������)
		add(p); // �׷����� ǥ�� (������)
		
		// �� atomic model�� ��Ʈ�� ���� -> coupling ����
		addCoupling(g, "out", p, "in"); 
		addCoupling(p, "out", g, "in");
	}

    
    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(768, 448);
        ((ViewableComponent)withName("genr")).setPreferredLocation(new Point(313, 79));
        ((ViewableComponent)withName("proc")).setPreferredLocation(new Point(321, 251));
    }
}
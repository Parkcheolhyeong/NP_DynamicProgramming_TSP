package Main;

import java.util.List;

//Tsp ���� Ŭ����
public class TspManager {
	private TspCalc solver; // ���Ŭ���� ���۷��� ���� ����

	private String[] DAEGU = // ���� index�� ��Ī ����
		{ "�뱸", "û��", "����", "����",
		  "����", "����", "���", "�λ�",
		  "����", "����", "õ��", "��õ" };

	private String[] SEOUL = // ���� index�� ��Ī ����
		{ "����", "����", "����", "����",
		  "���", "�λ�", "����", "����",
		  "�뱸", "û��", "õ��", "��õ" };

	public void SetMode(int[][] Temp) {
		int[][] W = Temp;	// prmt�� ���� ����ġ �׷��� ����

		/*������ ������� ���� ��δ� 999������ ����*/
		for (int i = 0; i < W.length; i++)
			for (int j = 0; j < W.length; j++)
				if (W[i][j] == 0)
					W[i][j] = 999;

		solver = new TspCalc(W);	// Combination�� Ȱ���� �ּҰ���ġ �� ��� ��� TspCalc Ŭ���� ����
	}

	
	/* �뱸���� ����� ����� ��� */
	public void dPath() {
		/*TspCalc Ŭ�������� ����� ��ε��� �÷����� �޾� ���*/
		List<Integer> tempPrinter = solver.getPath();
		System.out.print("Path: ");

		
		for (int i = 0; i < tempPrinter.size(); i++) {
			// ������ index�� ������ �̸��� ���
			System.out.print(DAEGU[tempPrinter.get(i)] + "(" + tempPrinter.get(i) + ")");
			if (i != tempPrinter.size() - 1)
				System.out.print("->");
		}

		System.out.println("\n");

		cost();
	}
	

	/* ���￡�� ����� ����� ��� */
	public void sPath() {
		List<Integer> tempPrinter = solver.getPath();
		System.out.print("Path: ");

		for (int i = 0; i < tempPrinter.size(); i++) {
			System.out.print(SEOUL[tempPrinter.get(i)] + "(" + tempPrinter.get(i) + ")");
			if (i != tempPrinter.size() - 1)
				System.out.print("->");
		}

	
		System.out.println("\n");

		cost();
	}
	
	public void cost() {
		System.out.println("Path Cost: " + solver.getCost());

		for (int i = 0; i < 100; i++)
			System.out.print("-");

		System.out.println("\n");
	}
	
}

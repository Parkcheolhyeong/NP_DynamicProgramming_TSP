package Main;

import java.util.List;

//Tsp 관리 클래스
public class TspManager {
	private TspCalc solver; // 계산클래스 레퍼런스 변수 지정

	private String[] DAEGU = // 정점 index의 명칭 지정
		{ "대구", "청주", "서울", "강릉",
		  "동해", "울진", "울산", "부산",
		  "광주", "대전", "천안", "인천" };

	private String[] SEOUL = // 정점 index의 명칭 지정
		{ "서울", "강릉", "동해", "울진",
		  "울산", "부산", "광주", "대전",
		  "대구", "청주", "천안", "인천" };

	public void SetMode(int[][] Temp) {
		int[][] W = Temp;	// prmt로 받은 가중치 그래프 저장

		/*간선이 연결되지 않은 경로는 999값으로 지정*/
		for (int i = 0; i < W.length; i++)
			for (int j = 0; j < W.length; j++)
				if (W[i][j] == 0)
					W[i][j] = 999;

		solver = new TspCalc(W);	// Combination을 활용한 최소가중치 및 경로 계산 TspCalc 클래스 실행
	}

	
	/* 대구에서 출발할 경우의 경로 */
	public void dPath() {
		/*TspCalc 클래스에서 저장된 경로들의 컬렉션을 받아 출력*/
		List<Integer> tempPrinter = solver.getPath();
		System.out.print("Path: ");

		
		for (int i = 0; i < tempPrinter.size(); i++) {
			// 정점의 index로 정점의 이름을 출력
			System.out.print(DAEGU[tempPrinter.get(i)] + "(" + tempPrinter.get(i) + ")");
			if (i != tempPrinter.size() - 1)
				System.out.print("->");
		}

		System.out.println("\n");

		cost();
	}
	

	/* 서울에서 출발할 경우의 경로 */
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

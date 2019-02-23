package Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TspCalc {

	private int start, n; // 시작, 마지막 정점
	private int[][] W; // 가중치 2차원 배열
	private List<Integer> path = new ArrayList<>(); // 경로를 저장할 배열 컬렉션
	private int minCost = 99999; // 최소경로 변수

	/* 생성자 */
	public TspCalc(int[][] W) {
		this.n = W.length; // 가중치 배열의 길이로 정점의 갯수 설정
		this.start = 0; // 시작 정점 설정
		this.W = W; // prmt로 넘겨받은 W값 설정
		solve(); // TSP 수행
	}

	

	/* 최단 경로 */
	/* solve()함수로 구한 path(컬렉션)을 반환 */
	public List<Integer> getPath() {
		return path;
	}

	/* 최단 경로의 가중치 */
	/* 갱신한 minCost를 반환 */
	public int getCost() {
		return minCost;
	}
	
	
	/* Dynamic programming을 이용한 외판원문제해결 method */
	private void solve() {
		List<Integer> rSubset;
		
		int ends = (1 << n) - 1; // 마지막 지점 설정

		int[][] D = new int[n][1 << n]; // 가중치 값을 저장한 D[n][pow(2,n)]

		for (int i = 1; i < n; i++) { // nC2값을 구함(시작점 가중치를 포함시킴)
			D[i][(1 << start) | (1 << i)] = W[start][i];
		}

		for (int r = 3; r <= n; r++) { // 순열의 조합을 구한다.
			// nC3, nC4 ... nCr의 경우를 순차적으로 접근
			rSubset = new ArrayList<>();
			comb(0, 0, r, n, rSubset);
			for (int A : rSubset) {
				for (int i = 1; i < n; i++) {
					if ((subInclude(i, A))) { // 선택한 정점이 집합 subset에 포함되어있다면
						/* 집합 A에서 i정점을 제외한 집합 && 최소값 갱신을 위해 높은값 설정 */

						int subsetExcepti = A ^ (1 << i);
						int minmum = 999;

						for (int j = 1; j < n; j++) {
							if ((j != i) && (subInclude(j, A))) {
								/* A에서 i를 제외한 값에서 i정점를 추가할 경우 가장 작은 값을 갱신 */
								int minTemp = W[j][i] + D[j][subsetExcepti];

								if (minTemp < minmum) {
									minmum = minTemp; // 최소가중치 갱신
								}
							}
						}
						D[i][A] = minmum; // 해당 집합에서의 최소가중치 저장

					}
				}
			}
		}

		for (int i = 1; i < n; i++) {
			int minimum = D[i][ends] + W[i][start]; // 해당정점에서 다시 시작점으로 돌아오는경우
			if (minimum < minCost) {
				minCost = minimum; // 최소 값 갱신
			}
		}

		/* DP로 구한 D배열에 저장된 값을 역순으로 최소경로 가중치를 비교하여 경로를 저장 */
		int selectIndex = start;
		int tempSubset = ends;
		path.add(start);

		/* 출발정점을 제외한 정점의 수만큼 순회*/
		for (int i = 1; i < n; i++) {

			int index = 999;
			for (int j = 0; j < n; j++) {
				/*i가 선택되고 j를 계속 비교하면서 최종적으로 나온 최소가중치가 있다면 갱신*/
				if ((start != j) && (subInclude(j, tempSubset))) { // j정점이 전체 집합에포함 되었고 시작점이 아닐때
					if (index == 999) // index와 j값 비교를 위한 index 설정
						index = j;

					/* 최소경로 가중치인 정점을 계속 갱신 */
					int preCost = D[index][tempSubset] + W[index][selectIndex];
					int newCost = D[j][tempSubset] + W[j][selectIndex];

					if (newCost < preCost) { // 최소 정점 j 갱신
						index = j;

					}
				}
			}
			path.add(index); // 최종 갱신된 정점을 삽입
			tempSubset = tempSubset ^ (1 << index); // 선택된 정점을 경로에서 제외
			selectIndex = index;	// selectIndex에 대한 최소경로를 위해 저장
		}

		path.add(start);

		Collections.reverse(path);
		// 모든 정점을 지난 값들로 부터 역순으로 정점을 구해 컬렉션에 삽입하였으므로 역전(Reverse) 수행

	}




	
	
	
	/*--------------------조합 계산------------------------------------------*/

	/* 조합구하기 nCr */
	// 포함한상태를 나타내는 set, 현재포함중인 정점의 수 i,. 싶은 집합은 크기 n, 선택할 정점 r을 prmt로 받음
	
	private void comb(int set, int iClude, int r, int n, List<Integer> subset) {
		
		/* 포함해야할 정점(r)보다 현재포함중인 정점(iClude)이 크면 종료*/
		if (n - iClude < r) 
			return;

		if (r == 0) { // 더 이상 고려할 수가 없으면 추가된 set을 컬렉션에 삽입
			subset.add(set);
		} else {
			for (int i = iClude; i < n; i++) {
				set = (set | (1 << i)); // 정점이 포함된 상태
				comb(set, i + 1, r - 1, n, subset); // 다음 i를 넘기고 선택할 r를 줄인다.
				set = (set & (~(1 << i))); // 정점이 선택 안된경우로 갱신
			}
		}
	}
	
	private boolean subInclude(int i, int subset) {// 포함여부검사
		if ((subset & (1 << i)) != 0) // prmt로 받은 정점이 집합에 포함되어있지않으면 true
			return true;
		else
			return false;
	}

}

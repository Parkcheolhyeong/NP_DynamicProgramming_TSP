package Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TspCalc {

	private int start, n; // ����, ������ ����
	private int[][] W; // ����ġ 2���� �迭
	private List<Integer> path = new ArrayList<>(); // ��θ� ������ �迭 �÷���
	private int minCost = 99999; // �ּҰ�� ����

	/* ������ */
	public TspCalc(int[][] W) {
		this.n = W.length; // ����ġ �迭�� ���̷� ������ ���� ����
		this.start = 0; // ���� ���� ����
		this.W = W; // prmt�� �Ѱܹ��� W�� ����
		solve(); // TSP ����
	}

	

	/* �ִ� ��� */
	/* solve()�Լ��� ���� path(�÷���)�� ��ȯ */
	public List<Integer> getPath() {
		return path;
	}

	/* �ִ� ����� ����ġ */
	/* ������ minCost�� ��ȯ */
	public int getCost() {
		return minCost;
	}
	
	
	/* Dynamic programming�� �̿��� ���ǿ������ذ� method */
	private void solve() {
		List<Integer> rSubset;
		
		int ends = (1 << n) - 1; // ������ ���� ����

		int[][] D = new int[n][1 << n]; // ����ġ ���� ������ D[n][pow(2,n)]

		for (int i = 1; i < n; i++) { // nC2���� ����(������ ����ġ�� ���Խ�Ŵ)
			D[i][(1 << start) | (1 << i)] = W[start][i];
		}

		for (int r = 3; r <= n; r++) { // ������ ������ ���Ѵ�.
			// nC3, nC4 ... nCr�� ��츦 ���������� ����
			rSubset = new ArrayList<>();
			comb(0, 0, r, n, rSubset);
			for (int A : rSubset) {
				for (int i = 1; i < n; i++) {
					if ((subInclude(i, A))) { // ������ ������ ���� subset�� ���ԵǾ��ִٸ�
						/* ���� A���� i������ ������ ���� && �ּҰ� ������ ���� ������ ���� */

						int subsetExcepti = A ^ (1 << i);
						int minmum = 999;

						for (int j = 1; j < n; j++) {
							if ((j != i) && (subInclude(j, A))) {
								/* A���� i�� ������ ������ i������ �߰��� ��� ���� ���� ���� ���� */
								int minTemp = W[j][i] + D[j][subsetExcepti];

								if (minTemp < minmum) {
									minmum = minTemp; // �ּҰ���ġ ����
								}
							}
						}
						D[i][A] = minmum; // �ش� ���տ����� �ּҰ���ġ ����

					}
				}
			}
		}

		for (int i = 1; i < n; i++) {
			int minimum = D[i][ends] + W[i][start]; // �ش��������� �ٽ� ���������� ���ƿ��°��
			if (minimum < minCost) {
				minCost = minimum; // �ּ� �� ����
			}
		}

		/* DP�� ���� D�迭�� ����� ���� �������� �ּҰ�� ����ġ�� ���Ͽ� ��θ� ���� */
		int selectIndex = start;
		int tempSubset = ends;
		path.add(start);

		/* ��������� ������ ������ ����ŭ ��ȸ*/
		for (int i = 1; i < n; i++) {

			int index = 999;
			for (int j = 0; j < n; j++) {
				/*i�� ���õǰ� j�� ��� ���ϸ鼭 ���������� ���� �ּҰ���ġ�� �ִٸ� ����*/
				if ((start != j) && (subInclude(j, tempSubset))) { // j������ ��ü ���տ����� �Ǿ��� �������� �ƴҶ�
					if (index == 999) // index�� j�� �񱳸� ���� index ����
						index = j;

					/* �ּҰ�� ����ġ�� ������ ��� ���� */
					int preCost = D[index][tempSubset] + W[index][selectIndex];
					int newCost = D[j][tempSubset] + W[j][selectIndex];

					if (newCost < preCost) { // �ּ� ���� j ����
						index = j;

					}
				}
			}
			path.add(index); // ���� ���ŵ� ������ ����
			tempSubset = tempSubset ^ (1 << index); // ���õ� ������ ��ο��� ����
			selectIndex = index;	// selectIndex�� ���� �ּҰ�θ� ���� ����
		}

		path.add(start);

		Collections.reverse(path);
		// ��� ������ ���� ����� ���� �������� ������ ���� �÷��ǿ� �����Ͽ����Ƿ� ����(Reverse) ����

	}




	
	
	
	/*--------------------���� ���------------------------------------------*/

	/* ���ձ��ϱ� nCr */
	// �����ѻ��¸� ��Ÿ���� set, ������������ ������ �� i,. ���� ������ ũ�� n, ������ ���� r�� prmt�� ����
	
	private void comb(int set, int iClude, int r, int n, List<Integer> subset) {
		
		/* �����ؾ��� ����(r)���� ������������ ����(iClude)�� ũ�� ����*/
		if (n - iClude < r) 
			return;

		if (r == 0) { // �� �̻� ����� ���� ������ �߰��� set�� �÷��ǿ� ����
			subset.add(set);
		} else {
			for (int i = iClude; i < n; i++) {
				set = (set | (1 << i)); // ������ ���Ե� ����
				comb(set, i + 1, r - 1, n, subset); // ���� i�� �ѱ�� ������ r�� ���δ�.
				set = (set & (~(1 << i))); // ������ ���� �ȵȰ��� ����
			}
		}
	}
	
	private boolean subInclude(int i, int subset) {// ���Կ��ΰ˻�
		if ((subset & (1 << i)) != 0) // prmt�� ���� ������ ���տ� ���ԵǾ����������� true
			return true;
		else
			return false;
	}

}

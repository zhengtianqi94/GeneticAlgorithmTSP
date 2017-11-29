package GeneticAlgorithm;

import java.util.Random;

public class Crossing {

	// Semi-OX crossing operator
	public static void OXCross(int k1, int k2, int cityNum, int[][] NewGeneration, Random random) {
		int i, j, k, flag;
		int ran1, ran2, temp;
		int[] Gh1 = new int[cityNum];
		int[] Gh2 = new int[cityNum];

		ran1 = random.nextInt(65535) % cityNum;
		ran2 = random.nextInt(65535) % cityNum;

		while (ran1 == ran2) {
			ran2 = random.nextInt(65535) % cityNum;
		}

		if (ran1 > ran2) {
			temp = ran1;
			ran1 = ran2;
			ran2 = temp;
		}

		flag = ran2 - ran1 + 1;
		for (i = 0, j = ran1; i < flag; i++, j++) {
			Gh1[i] = NewGeneration[k2][j];
			Gh2[i] = NewGeneration[k1][j];
		}

		for (k = 0, j = flag; j < cityNum;) {
			Gh1[j] = NewGeneration[k1][k++];
			for (i = 0; i < flag; i++) {
				if (Gh1[i] == Gh1[j]) {
					break;
				}
			}
			if (i == flag) {
				j++;
			}
		}

		for (k = 0, j = flag; j < cityNum;) {
			Gh2[j] = NewGeneration[k2][k++];
			for (i = 0; i < flag; i++) {
				if (Gh2[i] == Gh2[j]) {
					break;
				}
			}
			if (i == flag) {
				j++;
			}
		}

		for (i = 0; i < cityNum; i++) {
			NewGeneration[k1][i] = Gh1[i];
			NewGeneration[k2][i] = Gh2[i];
		}
	}

	// Crossing operator, same gene cross to mutate new gene
	public static void SameGeneOXCross(int k1, int k2, int cityNum, int[][] NewGeneration, Random random) {
		int i, j, k, flag;
		int ran1, ran2, temp;
		int[] Gh1 = new int[cityNum];
		int[] Gh2 = new int[cityNum];

		ran1 = random.nextInt(65535) % cityNum;
		ran2 = random.nextInt(65535) % cityNum;
		while (ran1 == ran2) {
			ran2 = random.nextInt(65535) % cityNum;
		}

		if (ran1 > ran2) {
			temp = ran1;
			ran1 = ran2;
			ran2 = temp;
		}

		for (i = 0, j = ran2; j < cityNum; i++, j++) {
			Gh2[i] = NewGeneration[k1][j];
		}

		flag = i;

		for (k = 0, j = flag; j < cityNum;) {
			Gh2[j] = NewGeneration[k2][k++];
			for (i = 0; i < flag; i++) {
				if (Gh2[i] == Gh2[j]) {
					break;
				}
			}
			if (i == flag) {
				j++;
			}
		}

		flag = ran1;
		for (k = 0, j = 0; k < cityNum;) {
			Gh1[j] = NewGeneration[k1][k++];
			for (i = 0; i < flag; i++) {
				if (NewGeneration[k2][i] == Gh1[j]) {
					break;
				}
			}
			if (i == flag) {
				j++;
			}
		}

		flag = cityNum - ran1;

		for (i = 0, j = flag; j < cityNum; j++, i++) {
			Gh1[j] = NewGeneration[k2][i];
		}

		for (i = 0; i < cityNum; i++) {
			NewGeneration[k1][i] = Gh1[i];
			NewGeneration[k2][i] = Gh2[i];
		}
	}

	// Multi-change mutation operator
	public static void OnCVariation(int k, int cityNum, int[][] NewGeneration, Random random) {
		int ran1, ran2, temp;

		// Change times
		int count;

		count = random.nextInt(65535) % cityNum;

		for (int i = 0; i < count; i++) {

			ran1 = random.nextInt(65535) % cityNum;
			ran2 = random.nextInt(65535) % cityNum;
			while (ran1 == ran2) {
				ran2 = random.nextInt(65535) % cityNum;
			}
			temp = NewGeneration[k][ran1];
			NewGeneration[k][ran1] = NewGeneration[k][ran2];
			NewGeneration[k][ran2] = temp;
		}
	}

}

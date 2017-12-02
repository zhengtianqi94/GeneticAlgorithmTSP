package GeneticAlgorithm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class GeneticAlgorithm {

	// Initial genetic group scale
	private int scale;

	// Initial genetic length (Total city number in TSP)
	private int cityNum;

	// Total generation times
	private int GenerationTimes;

	// Matrix of score (distance in TSP)
	private int[][] distance;

	// Best generation index
	private int BestGeneration;

	// Best length
	private int bestLength;

	// Best path
	private int[] BestPath;

	// Initial generation genetic, thus the father generation, lines indicate
	// the scale，each line is a generation (full path in TSP)，each column is a
	// genetic (city index in TSP)
	private int[][] InitialGeneration;

	// New generation, thus the child generation
	private int[][] NewGeneration;

	// Fitness of generations
	private int[] fitness;

	// Integration possibility of each generation
	private float[] IntegrationPossibility;

	// Crossing possibility when generate
	private float CrossingPossibility;

	// Mutate possibility when generate
	private float MutationPossibility;

	// Current generation
	private int t;

	private Random random;

	// GA constructor.
	// s -> scale, n -> city number, g -> generation times,
	// c -> cross possibility, m -> mutation possibility
	public GeneticAlgorithm(int s, int n, int g, float c, float m) {
		scale = s;
		cityNum = n;
		GenerationTimes = g;
		CrossingPossibility = c;
		MutationPossibility = m;
	}

	public int[][] getMatrix() {
		return this.distance;
	}

	public int getBestGeneration() {
		return this.BestGeneration;
	}

	public int getScale() {
		return this.scale;
	}
	
	public int[] getBestPath(){
		return this.BestPath;
	}

	// Initialization of GA, need a file path that stores the meta data.
	// The test uses att48.tsp from
	// http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/
	@SuppressWarnings("resource")
	public void init(String filename) throws IOException {
		// Read data
		int[] x;
		int[] y;
		String strbuff;
		BufferedReader data = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		distance = new int[cityNum][cityNum];
		x = new int[cityNum];
		y = new int[cityNum];
		for (int i = 0; i < cityNum; i++) {

			// Read one line, parameter
			// city number, coordinate of x, coordinate of y
			strbuff = data.readLine();

			// Split a line of data
			String[] strcol = strbuff.split(" ");

			// coordinator of x
			x[i] = Integer.valueOf(strcol[1]);

			// coordinate of y
			y[i] = Integer.valueOf(strcol[2]);
		}

		// Compute the distance matrix
		for (int i = 0; i < cityNum - 1; i++) {
			distance[i][i] = 0;
			for (int j = i + 1; j < cityNum; j++) {
				double rij = Math.sqrt(((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j]) * (y[i] - y[j])) / 10.0);
				int tij = (int) Math.round(rij);
				if (tij < rij) {
					distance[i][j] = tij + 1;
					distance[j][i] = distance[i][j];
				} else {
					distance[i][j] = tij;
					distance[j][i] = distance[i][j];
				}
			}
		}

		distance[cityNum - 1][cityNum - 1] = 0;

		// Initialize parameters
		bestLength = Integer.MAX_VALUE;
		BestPath = new int[cityNum + 1];
		BestGeneration = 0;
		t = 0;
		NewGeneration = new int[scale][cityNum];
		InitialGeneration = new int[scale][cityNum];
		fitness = new int[scale];
		IntegrationPossibility = new float[scale];
		random = new Random(System.currentTimeMillis());
	}

	// Initialize Group, the group is set to be 30
	// Each group has a random order of gene without duplicate (city index in
	// TSP)
	private void initGroup() {
		int i, j, k;

		// Group number = scale
		for (k = 0; k < scale; k++) {
			InitialGeneration[k][0] = random.nextInt(65535) % cityNum;

			// Each group holds gene number = cityNum
			for (i = 1; i < cityNum;) {
				InitialGeneration[k][i] = random.nextInt(65535) % cityNum;
				for (j = 0; j < i; j++) {
					if (InitialGeneration[k][i] == InitialGeneration[k][j]) {
						break;
					}
				}
				if (j == i) {
					i++;
				}
			}
		}
	}

	// Find the most fit child individual gene (the nearest city in TSP), copy
	// it to the next generation, need the individual fitness
	private void selectBestGene() {
		int k, i, maxid;
		int maxevaluation;

		maxid = 0;
		maxevaluation = fitness[0];
		for (k = 1; k < scale; k++) {
			if (maxevaluation > fitness[k]) {
				maxevaluation = fitness[k];
				maxid = k;
			}
		}

		if (bestLength > maxevaluation) {
			bestLength = maxevaluation;
			BestGeneration = t;
			for (i = 0; i < cityNum; i++) {
				BestPath[i] = InitialGeneration[maxid][i];
			}
		}

		// Copy that gene
		copyGene(0, maxid);
	}

	// Copy gene, k is the position in child generation, kk is the position in
	// father generation
	private void copyGene(int k, int kk) {
		int i;
		for (i = 0; i < cityNum; i++) {
			NewGeneration[k][i] = InitialGeneration[kk][i];
		}
	}

	// Random selection, when generate next generation
	private void select() {
		int k, i, selectId;
		float ran1;
		for (k = 1; k < scale; k++) {
			ran1 = (float) (random.nextInt(65535) % 1000 / 1000.0);
			for (i = 0; i < scale; i++) {
				if (ran1 <= IntegrationPossibility[i]) {
					break;
				}
			}
			selectId = i;
			copyGene(k, selectId);
		}
	}

	// Evolution, general order, save the best gene of father generation
	private void GeneralEvolution() {
		int k;

		selectBestGene();
		select();

		float r;

		// Crossing, the group will evolute to next generation
		for (k = 0; k < scale; k = k + 2) {

			// Crossing possibility of k and k + 1
			r = random.nextFloat();
			if (r < CrossingPossibility) {
				Crossing.SameGeneOXCross(k, k + 1, cityNum, NewGeneration, random);
			} else {

				// Mutation possibility of this gene (k)
				r = random.nextFloat();

				// Mutation
				if (r < MutationPossibility) {
					Crossing.OnCVariation(k, cityNum, NewGeneration, random);
				}

				// Mutation possibility (k + 1)
				r = random.nextFloat();

				// Mutation
				if (r < MutationPossibility) {
					Crossing.OnCVariation(k + 1, cityNum, NewGeneration, random);
				}
			}

		}
	}

	// Evolution, no crossing mutation, save the best gene of father generation
	private void NoCrossingEvolution() {
		int k;

		selectBestGene();
		select();

		float r;

		// No crossing, the group will evolute to next generation
		for (k = 1; k + 1 < scale / 2; k = k + 2) {

			// Crossing possibility of k and k + 1
			r = random.nextFloat();
			if (r < CrossingPossibility) {
				Crossing.SameGeneOXCross(k, k + 1, cityNum, NewGeneration, random);
			} else {

				r = random.nextFloat();
				if (r < MutationPossibility) {
					Crossing.OnCVariation(k, cityNum, NewGeneration, random);
				}

				r = random.nextFloat();
				if (r < MutationPossibility) {
					Crossing.OnCVariation(k + 1, cityNum, NewGeneration, random);
				}
			}
		}

		if (k == scale / 2 - 1) {
			r = random.nextFloat();
			if (r < MutationPossibility) {
				Crossing.OnCVariation(k, cityNum, NewGeneration, random);
			}
		}
	}

	// Generate next generation with gene crossing
	public int GeneralSolve() {

		int i;
		int k;

		// Initial the group
		initGroup();

		// Initial group fitness
		for (k = 0; k < scale; k++) {
			fitness[k] = Evaluation.evaluate(InitialGeneration[k], cityNum, distance);
		}

		// Compute initial generation's integration possibility
		Evaluation.countRate(scale, IntegrationPossibility, fitness);

		for (t = 0; t < GenerationTimes; t++) {

			GeneralEvolution();

			// Copy, prepare to generate
			for (k = 0; k < scale; k++) {
				for (i = 0; i < cityNum; i++) {
					InitialGeneration[k][i] = NewGeneration[k][i];
				}
			}

			// Count fitness
			for (k = 0; k < scale; k++) {
				fitness[k] = Evaluation.evaluate(InitialGeneration[k], cityNum, distance);
			}

			// Compute integration possibility
			Evaluation.countRate(scale, IntegrationPossibility, fitness);
		}
		return bestLength;
	}

	// Generate next generation without gene crossing
	public int NoCrossingSolve() {

		int i;
		int k;

		// Initial the group
		initGroup();

		// Initial group fitness
		for (k = 0; k < scale; k++) {
			fitness[k] = Evaluation.evaluate(InitialGeneration[k], cityNum, distance);
		}

		// Compute initial generation's integration possibility
		Evaluation.countRate(scale, IntegrationPossibility, fitness);

		for (t = 0; t < GenerationTimes; t++) {

			NoCrossingEvolution();

			// Copy, prepare to generate
			for (k = 0; k < scale; k++) {
				for (i = 0; i < cityNum; i++) {
					InitialGeneration[k][i] = NewGeneration[k][i];
				}
			}

			// Count fitness
			for (k = 0; k < scale; k++) {
				fitness[k] = Evaluation.evaluate(InitialGeneration[k], cityNum, distance);
			}

			// Compute integration possibility
			Evaluation.countRate(scale, IntegrationPossibility, fitness);
		}
		return bestLength;
	}
}

package GeneticAlgorithm;

public class Evaluation {

	public static int evaluate(int[] chromosome, int cityNum, int[][] distance) {
		int len = 0;
		for (int i = 1; i < cityNum; i++) {
			len += distance[chromosome[i - 1]][chromosome[i]];
		}
		len += distance[chromosome[cityNum - 1]][chromosome[0]];
		return len;
	}

	public static void countRate(int scale, float[] IntegrationPossibility, int[] fitness) {
		int k;
		double sumFitness = 0;
		double[] tempf = new double[scale];
		for (k = 0; k < scale; k++) {
			tempf[k] = 10.0 / fitness[k];
			sumFitness += tempf[k];
		}
		
		IntegrationPossibility[0] = (float) (tempf[0] / sumFitness);
		
		for (k = 1; k < scale; k++) {
			IntegrationPossibility[k] = (float) (tempf[k] / sumFitness + IntegrationPossibility[k - 1]);
		}
	}

}

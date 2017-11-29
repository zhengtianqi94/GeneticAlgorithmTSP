package GeneticAlgorithm;

public class Analyse {

	public static double getStandardDevition(int theory, int[] result) {

		double sum = 0;

		for (int i = 0; i < result.length; i++) {
			sum += (result[i] - theory) * (result[i] - theory);
		}

		return Math.sqrt((sum / (result.length)));
	}

	public static int getAvg(int[] result) {

		int sum = 0;

		for (int i = 0; i < result.length; i++) {
			sum += result[i];
		}

		return sum / result.length;
	}

}

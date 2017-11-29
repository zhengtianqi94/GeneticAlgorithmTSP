package GeneticAlgorithm;

public class Testing {

	public static void testGeneral(GeneticAlgorithm ga, int[] GeneralResult) {
		for (int i = 0; i < 100; i++) {
			GeneralResult[i] = ga.GeneralSolve();
			System.out.println("Best generation: " + ga.getBestGeneration());
		}
	}

	public static void testNoCrossing(GeneticAlgorithm ga, int[] NoCrossingResult) {
		for (int i = 0; i < 100; i++) {
			NoCrossingResult[i] = ga.NoCrossingSolve();
			System.out.println("Best generation: " + ga.getBestGeneration());
		}
	}

}

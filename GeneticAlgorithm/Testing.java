package GeneticAlgorithm;

import java.util.logging.Logger;

public class Testing {

	static String strClassName = Testing.class.getName();
	static Logger logger = Logger.getLogger(strClassName);

	public static void testGeneral(GeneticAlgorithm ga, int[] GeneralResult) {
		for (int i = 0; i < 1000; i++) {
			GeneralResult[i] = ga.GeneralSolve();
			logger.info("Best generation of general evolution: " + ga.getBestGeneration());
		}
	}

	public static void testNoCrossing(GeneticAlgorithm ga, int[] NoCrossingResult) {
		for (int i = 0; i < 1000; i++) {
			NoCrossingResult[i] = ga.NoCrossingSolve();
			logger.info("Best generation of no crossing evolution: " + ga.getBestGeneration());
		}
	}

}

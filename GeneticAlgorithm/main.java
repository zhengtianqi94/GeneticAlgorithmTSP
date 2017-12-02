package GeneticAlgorithm;

import java.io.IOException;
import java.util.logging.Logger;

public class main {

	private final static int IdealLength = 10628;

	private final static String filePath = "att48.tsp";

	private final static int scale = 30;
	private final static int cityNum = 48;
	private final static int GenerationTimes = 1000;
	private final static float CrossingPossibility = 0.8f;
	private final static float MutationPossibility = 0.9f;

	static String strClassName = main.class.getName();
	static Logger logger = Logger.getLogger(strClassName);

	public static void main(String[] args) {

		int[] GeneralResult = new int[1000];
		int[] NoCrossingResult = new int[1000];

		logger.info("Start solving TSP.");

		// Each ga is evolved 1000 times
		GeneticAlgorithm ga_general = new GeneticAlgorithm(scale, cityNum, GenerationTimes, CrossingPossibility,
				MutationPossibility);
		GeneticAlgorithm ga_nocrossing = new GeneticAlgorithm(scale, cityNum, GenerationTimes, CrossingPossibility,
				MutationPossibility);

		try {

			ga_general.init(filePath);
			logger.info("Start general evolution...");
			Testing.testGeneral(ga_general, GeneralResult);
			logger.info("General evolution finished.");

			ga_nocrossing.init(filePath);
			logger.info("Start no crossing evolution...");
			Testing.testNoCrossing(ga_nocrossing, NoCrossingResult);
			logger.info("No Crossing evolution finished.");

			logger.info("Result of general evolution:");
			System.out.println("Best generation of General Evolution: " + ga_general.getBestGeneration());
			System.out.println("Best Path of General Evolution: ");
			for (int i = 0; i < ga_general.getBestPath().length; i++) {
				if (i != 0 && i % 10 == 0)
					System.out.println();
				if (i == ga_general.getBestPath().length - 1)
					System.out.println(ga_general.getBestPath()[i]);
				else
					System.out.print(ga_general.getBestPath()[i] + " -> ");
			}
			System.out.println("Average length of General Solving: " + Analyse.getAvg(GeneralResult));
			System.out.println(
					"Standard Division of General Solving: " + Analyse.getStandardDevition(IdealLength, GeneralResult));

			System.out.println();

			logger.info("Result of no crossing evolution:");
			System.out.println("Best generation of No Crossing Evolution: " + ga_nocrossing.getBestGeneration());
			System.out.println("Best Path of No Crossing Evolution: ");
			for (int i = 0; i < ga_nocrossing.getBestPath().length; i++) {
				if (i != 0 && i % 10 == 0)
					System.out.println();
				if (i == ga_nocrossing.getBestPath().length - 1)
					System.out.println(ga_nocrossing.getBestPath()[i]);
				else
					System.out.print(ga_nocrossing.getBestPath()[i] + " -> ");
			}
			System.out.println("Average length of No Crossing Solving: " + Analyse.getAvg(NoCrossingResult));
			System.out.println("Standard Division of No Crossing Solving: "
					+ Analyse.getStandardDevition(IdealLength, NoCrossingResult));

		} catch (IOException e) {
			System.out.println(e.getCause().getMessage());
		}
	}

}

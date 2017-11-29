package GeneticAlgorithm;

import java.io.IOException;

public class main {

	private final static int IdealLength = 10628;

	private final static String filePath = "att48.tsp";

	private final static int scale = 30;
	private final static int cityNum = 48;
	private final static int GenerationTimes = 1000;
	private final static float CrossingPossibility = 0.8f;
	private final static float MutationPossibility = 0.9f;

	public static void main(String[] args) {

		int[] GeneralResult = new int[100];
		int[] NoCrossingResult = new int[100];

		System.out.println("Start");

		// Each ga is generated 1000 times
		GeneticAlgorithm ga = new GeneticAlgorithm(scale, cityNum, GenerationTimes, CrossingPossibility,
				MutationPossibility);

		try {

			ga.init(filePath);
			System.out.println("Running...");
			Testing.testGeneral(ga, GeneralResult);
			Testing.testNoCrossing(ga, NoCrossingResult);

			System.out.println("Average length of General Solving: " + Analyse.getAvg(GeneralResult));
			System.out.println(
					"Standard Division of General Solving: " + Analyse.getStandardDevition(IdealLength, GeneralResult));

			System.out.println("Average length of No Crossing Solving: " + Analyse.getAvg(NoCrossingResult));
			System.out.println("Standard Division of No Crossing Solving: "
					+ Analyse.getStandardDevition(IdealLength, NoCrossingResult));

		} catch (IOException e) {
			System.out.println(e.getCause().getMessage());
		}
	}

}

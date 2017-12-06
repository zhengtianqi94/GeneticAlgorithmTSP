package Test;

import java.io.IOException;
import org.junit.Test;
import GeneticAlgorithm.GeneticAlgorithm;
import GeneticAlgorithm.Testing;
import GeneticAlgorithm.Analyse;
import GeneticAlgorithm.Evaluation;

public class GATest {

	private final static int IdealLength = 10628;

	private final static String filePath = "att48.tsp";

	private final static int scale = 30;
	private final static int cityNum = 48;
	private final static int GenerationTimes = 1000;
	private final static float CrossingPossibility = 0.8f;
	private final static float MutationPossibility = 0.9f;

	// Test the distance matrix has been successfully initialized
	@Test
	public void testInitialDistance() {
		GeneticAlgorithm ga = new GeneticAlgorithm(scale, cityNum, GenerationTimes, CrossingPossibility,
				MutationPossibility);
		try {
			ga.init(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert (ga.getScale() == 30);
		assert (ga.getMatrix().length == 48);
		assert (ga.getMatrix()[0].length == 48);
	}

	// Test Select BestGene Selection
	@Test
	public void testBestSelection() {
		GeneticAlgorithm ga = new GeneticAlgorithm(scale, cityNum, GenerationTimes, CrossingPossibility,
				MutationPossibility);
		boolean flag = true;
		try {
			ga.init(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ga.initGroup();
		ga.selectBestGene();
		for (int i = 0; i < ga.getScale(); i++) {
			if (Evaluation.evaluate(ga.getInitial()[i], this.cityNum, ga.getMatrix()) < ga.getBestLength())
				flag = false;
		}
		assert (flag == true);
	}

	// Test GeneralSolve
	@Test
	public void testGeneralSolve() {
		GeneticAlgorithm ga = new GeneticAlgorithm(scale, cityNum, GenerationTimes, CrossingPossibility,
				MutationPossibility);
		try {
			ga.init(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert (ga.GeneralSolve() != Integer.MAX_VALUE);
	}

	// Test NoCrossingSolve
	@Test
	public void testNoCrossingSolve() {
		GeneticAlgorithm ga = new GeneticAlgorithm(scale, cityNum, GenerationTimes, CrossingPossibility,
				MutationPossibility);
		try {
			ga.init(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert (ga.NoCrossingSolve() != Integer.MAX_VALUE);
	}

	// Test GeneralSolve result
	@Test
	public void testGeneralSolveResult() {
		int[] GeneralResult = new int[1000];
		GeneticAlgorithm ga = new GeneticAlgorithm(scale, cityNum, GenerationTimes, CrossingPossibility,
				MutationPossibility);
		try {
			ga.init(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Testing.testGeneral(ga, GeneralResult);
		assert (Analyse.getStandardDevition(IdealLength, GeneralResult) > 10000);
	}

	// Test NoCrossingSolve result
	@Test
	public void testNoCrossingSolveResult() {
		int[] NoCrossingResult = new int[1000];
		GeneticAlgorithm ga = new GeneticAlgorithm(scale, cityNum, GenerationTimes, CrossingPossibility,
				MutationPossibility);
		try {
			ga.init(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Testing.testNoCrossing(ga, NoCrossingResult);
		assert (Analyse.getStandardDevition(IdealLength, NoCrossingResult) < 5000);
	}

}

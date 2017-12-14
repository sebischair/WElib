package de.tum.in.wwwmatthes.stm;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bytedeco.javacpp.avfilter.avfilter_action_func;
import org.datavec.api.util.ClassPathResource;
import org.junit.Test;

import de.tum.in.wwwmatthes.stm.evaluation.Evaluation;
import de.tum.in.wwwmatthes.stm.evaluation.EvaluationDataSet;
import de.tum.in.wwwmatthes.stm.evaluation.EvaluationDataSetItem;
import de.tum.in.wwwmatthes.stm.evaluation.EvaluationDataSets;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSet;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSetItem;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSets;
import de.tum.in.wwwmatthes.stm.exceptions.InvalidConfigException;
import de.tum.in.wwwmatthes.stm.models.Model;
import de.tum.in.wwwmatthes.stm.models.ModelFactory;
import de.tum.in.wwwmatthes.stm.models.config.Config;
import de.tum.in.wwwmatthes.stm.models.config.ConfigTfidfFactory;
import junit.framework.Assert;

public class EvaluationTests {
	
	private static String testInput1 = "Before healing others, heal yourself.";
	private static String testInput2 = "Exchange dollars in a stock market .";
	private static String testInput3 = "Time and health are two precious assets that we don't recognize and appreciate until they have been depleted. The way to make money is to buy when blood is running in the streets.";
	
	@Test
	public void test() throws Exception {
		
		// Create Config
		Config config = new ConfigTfidfFactory()
				.documentsSourceFile(new ClassPathResource("examples/unlabeled").getFile())
				.build();

		// Create Model
		Model model = ModelFactory.createFromConfig(config);
		model.fit();
		
		// Evaluation
		EvaluationDataSets evaluatedDataSets = Evaluation.evaluate(createDatasets(), model);
				
		// -- Data Set 1
		EvaluationDataSet evaluatedDataSet1 = evaluatedDataSets.getEvaluatedDataSetList().get(1);
		
		assertDouble(evaluatedDataSet1.getAverageMrr(), (1 + 0.5 + 1/3.0)/3);
		assertDouble(evaluatedDataSet1.getAverageRank(), 2);
		assertDouble(evaluatedDataSet1.getAverageRankInPercentage(), 0.5);
		assertDouble(evaluatedDataSet1.getAverageEvaluationOptimalRank(), 1);
		assertDouble(evaluatedDataSet1.getAverageEvaluationOptimalRankInPercentage(), 1);
		assertDouble(evaluatedDataSet1.getMinRank(), 1);
		assertDouble(evaluatedDataSet1.getMinRankInPercentage(), 1);
		assertDouble(evaluatedDataSet1.getMaxRank(), 3);
		assertDouble(evaluatedDataSet1.getMaxRankInPercentage(), 0);
		assertDouble(evaluatedDataSet1.getMinMaxRankDifference(), 2);
		assertDouble(evaluatedDataSet1.getAverageMinRank(), 2);
		assertDouble(evaluatedDataSet1.getAverageMinRankInPercentage(), 0.5);
		assertDouble(evaluatedDataSet1.getAverageMaxRank(), 2);
		assertDouble(evaluatedDataSet1.getAverageMaxRankInPercentage(), 0.5);
		assertDouble(evaluatedDataSet1.getAverageMinMaxRankDifference(), 0);
		assertDouble(evaluatedDataSet1.getRelativeAverageRankInPercentage(), 0.5);
		assertDouble(evaluatedDataSet1.getAverageInputLength(), testInput1.length());
		//assertDouble(evaluatedDataSet1.getAverageDocumentInputLengthRatio(), 1);
		assertDouble(evaluatedDataSet1.getAverageOutputLength(), averageOutputLengthDataSet1());
		assertDouble(evaluatedDataSet1.getAverageOutputInputLengthRatio(), averageOutputInputLengthRatioDataSet1());
		
		// ---- Data Set Item i1
		EvaluationDataSetItem evaluatedDataSetItemI1 = evaluatedDataSet1.getEvaluatedDataSetItemList().get(0);
		
		assertDouble(evaluatedDataSetItemI1.getMrr(), 1.0);
		assertDouble(evaluatedDataSetItemI1.getAverageRank(), 1.0);
		assertDouble(evaluatedDataSetItemI1.getAverageRankInPercentage(), 1);
		assertDouble(evaluatedDataSetItemI1.getEvaluationOptimalRank(), 1);
		assertDouble(evaluatedDataSetItemI1.getEvaluationOptimalRankInPercentage(), 1);
		assertDouble(evaluatedDataSetItemI1.getMinRank(), 1);
		assertDouble(evaluatedDataSetItemI1.getMinRankInPercentage(), 1);
		assertDouble(evaluatedDataSetItemI1.getMaxRank(), 1);
		assertDouble(evaluatedDataSetItemI1.getMaxRankInPercentage(), 1);
		assertDouble(evaluatedDataSetItemI1.getMinMaxRankDifference(), 0);
		assertDouble(evaluatedDataSetItemI1.getRelativeAverageRankInPercentage(), 1);
		assertDouble(evaluatedDataSetItemI1.getInputLength(), testInput1.length());
		//assertDouble(evaluatedDataSetItemI1.getAverageDocumentInputLengthRatio(), 1);
		assertDouble(evaluatedDataSetItemI1.getAverageOutputLength(), averageOutputLengthDataSet1DataSetItemI1());
		assertDouble(evaluatedDataSetItemI1.getAverageOutputInputLengthRatio(), averageOutputInputLengthRatioDataSet1DataSetItemI1());
		
		// ---- Data Set Item i2
		EvaluationDataSetItem evaluatedDataSetItemI2 = evaluatedDataSet1.getEvaluatedDataSetItemList().get(1);

		assertDouble(evaluatedDataSetItemI2.getMrr(), 0.5);
		assertDouble(evaluatedDataSetItemI2.getAverageRank(), 2);
		assertDouble(evaluatedDataSetItemI2.getAverageRankInPercentage(), 0.5);
		assertDouble(evaluatedDataSetItemI2.getEvaluationOptimalRank(), 1);
		assertDouble(evaluatedDataSetItemI2.getEvaluationOptimalRankInPercentage(), 1);
		assertDouble(evaluatedDataSetItemI2.getMinRank(), 2);
		assertDouble(evaluatedDataSetItemI2.getMinRankInPercentage(), 0.5);
		assertDouble(evaluatedDataSetItemI2.getMaxRank(), 2);
		assertDouble(evaluatedDataSetItemI2.getMaxRankInPercentage(), 0.5);
		assertDouble(evaluatedDataSetItemI2.getMinMaxRankDifference(), 0);
		assertDouble(evaluatedDataSetItemI2.getRelativeAverageRankInPercentage(), 0.5);
		assertDouble(evaluatedDataSetItemI2.getInputLength(), testInput1.length());
		//assertDouble(evaluatedDataSetItemI2.getAverageDocumentInputLengthRatio(), 1);
		assertDouble(evaluatedDataSetItemI2.getAverageOutputLength(), averageOutputLengthDataSet1DataSetItemI2());
		assertDouble(evaluatedDataSetItemI2.getAverageOutputInputLengthRatio(), averageOutputInputLengthRatioDataSet1DataSetItemI2());
		
		// ---- Data Set Item i3
		EvaluationDataSetItem evaluatedDataSetItemI3 = evaluatedDataSet1.getEvaluatedDataSetItemList().get(2);

		assertDouble(evaluatedDataSetItemI3.getMrr(), 1/3.0);
		assertDouble(evaluatedDataSetItemI3.getAverageRank(), 3);
		assertDouble(evaluatedDataSetItemI3.getAverageRankInPercentage(), 0.0);
		assertDouble(evaluatedDataSetItemI3.getEvaluationOptimalRank(), 1);
		assertDouble(evaluatedDataSetItemI3.getEvaluationOptimalRankInPercentage(), 1);
		assertDouble(evaluatedDataSetItemI3.getMinRank(), 3);
		assertDouble(evaluatedDataSetItemI3.getMinRankInPercentage(), 0.0);
		assertDouble(evaluatedDataSetItemI3.getMaxRank(), 3);
		assertDouble(evaluatedDataSetItemI3.getMaxRankInPercentage(), 0.0);
		assertDouble(evaluatedDataSetItemI3.getMinMaxRankDifference(), 0);
		assertDouble(evaluatedDataSetItemI3.getRelativeAverageRankInPercentage(), 0.0);
		assertDouble(evaluatedDataSetItemI3.getInputLength(), testInput1.length());
		//assertDouble(evaluatedDataSetItemI2.getAverageDocumentInputLengthRatio(), 1);
		assertDouble(evaluatedDataSetItemI3.getAverageOutputLength(), averageOutputLengthDataSet1DataSetItemI3());
		assertDouble(evaluatedDataSetItemI3.getAverageOutputInputLengthRatio(), averageOutputInputLengthRatioDataSet1DataSetItemI3());
		
		// -- Data Set 2
		EvaluationDataSet evaluatedDataSet2 = evaluatedDataSets.getEvaluatedDataSetList().get(0);
		
		assertDouble(evaluatedDataSet2.getAverageMrr(), (1+0.75)/2);
		assertDouble(evaluatedDataSet2.getAverageRank(), (1+1.5)/2);
		assertDouble(evaluatedDataSet2.getAverageRankInPercentage(), 0.875);
		assertDouble(evaluatedDataSet2.getAverageEvaluationOptimalRank(), 1.25);
		assertDouble(evaluatedDataSet2.getAverageEvaluationOptimalRankInPercentage(), 0.875);
		assertDouble(evaluatedDataSet2.getMinRank(), 1);
		assertDouble(evaluatedDataSet2.getMinRankInPercentage(), 1);
		assertDouble(evaluatedDataSet2.getMaxRank(), 2);
		assertDouble(evaluatedDataSet2.getMaxRankInPercentage(), 0.5);
		assertDouble(evaluatedDataSet2.getMinMaxRankDifference(), 1);
		assertDouble(evaluatedDataSet2.getAverageMinRank(), 1);
		assertDouble(evaluatedDataSet2.getAverageMinRankInPercentage(), 1);
		assertDouble(evaluatedDataSet2.getAverageMaxRank(), 1.5);
		assertDouble(evaluatedDataSet2.getAverageMaxRankInPercentage(), 0.75);
		assertDouble(evaluatedDataSet2.getAverageMinMaxRankDifference(), 0.5);
		assertDouble(evaluatedDataSet2.getRelativeAverageRankInPercentage(), 1);
		assertDouble(evaluatedDataSet2.getAverageInputLength(), (testInput2.length() + testInput3.length())/2.0);
		//assertDouble(evaluatedDataSet2.getAverageDocumentInputLengthRatio(), 1);
		assertDouble(evaluatedDataSet2.getAverageOutputLength(), averageOutputLengthDataSet2());
		assertDouble(evaluatedDataSet2.getAverageOutputInputLengthRatio(), averageOutputInputLengthRatioDataSet2());
		
		
		// ---- Data Set Item i4
		EvaluationDataSetItem evaluatedDataSetItemI4 = evaluatedDataSet2.getEvaluatedDataSetItemList().get(0);

		assertDouble(evaluatedDataSetItemI4.getMrr(), 1);
		assertDouble(evaluatedDataSetItemI4.getAverageRank(), 1);
		assertDouble(evaluatedDataSetItemI4.getAverageRankInPercentage(), 1);
		assertDouble(evaluatedDataSetItemI4.getEvaluationOptimalRank(), 1);
		assertDouble(evaluatedDataSetItemI4.getEvaluationOptimalRankInPercentage(), 1);
		assertDouble(evaluatedDataSetItemI4.getMinRank(), 1);
		assertDouble(evaluatedDataSetItemI4.getMinRankInPercentage(), 1);
		assertDouble(evaluatedDataSetItemI4.getMaxRank(), 1);
		assertDouble(evaluatedDataSetItemI4.getMaxRankInPercentage(), 1);
		assertDouble(evaluatedDataSetItemI4.getMinMaxRankDifference(), 0);
		assertDouble(evaluatedDataSetItemI4.getRelativeAverageRankInPercentage(), 1);
		assertDouble(evaluatedDataSetItemI4.getInputLength(), testInput2.length());
		//assertDouble(evaluatedDataSetItemI4.getAverageDocumentInputLengthRatio(), 1);
		assertDouble(evaluatedDataSetItemI4.getAverageOutputLength(), averageOutputLengthDataSet2DataSetItemI4());
		assertDouble(evaluatedDataSetItemI4.getAverageOutputInputLengthRatio(), averageOutputInputLengthRatioDataSet2DataSetItemI4());
		
		// ---- Data Set Item i5
		EvaluationDataSetItem evaluatedDataSetItemI5 = evaluatedDataSet2.getEvaluatedDataSetItemList().get(1);

		assertDouble(evaluatedDataSetItemI5.getMrr(), 0.75);
		assertDouble(evaluatedDataSetItemI5.getAverageRank(), 1.5);
		assertDouble(evaluatedDataSetItemI5.getAverageRankInPercentage(), 0.75);
		assertDouble(evaluatedDataSetItemI5.getEvaluationOptimalRank(), 1.5);
		assertDouble(evaluatedDataSetItemI5.getEvaluationOptimalRankInPercentage(), 0.75);
		assertDouble(evaluatedDataSetItemI5.getMinRank(), 1);
		assertDouble(evaluatedDataSetItemI5.getMinRankInPercentage(), 1);
		assertDouble(evaluatedDataSetItemI5.getMaxRank(), 2);
		assertDouble(evaluatedDataSetItemI5.getMaxRankInPercentage(), 0.5);
		assertDouble(evaluatedDataSetItemI5.getMinMaxRankDifference(), 1);
		assertDouble(evaluatedDataSetItemI5.getRelativeAverageRankInPercentage(), 1);
		assertDouble(evaluatedDataSetItemI5.getInputLength(), testInput3.length());
		//assertDouble(evaluatedDataSetItemI5.getAverageDocumentInputLengthRatio(), 1);
		assertDouble(evaluatedDataSetItemI5.getAverageOutputLength(), averageOutputLengthDataSet2DataSetItemI5());
		assertDouble(evaluatedDataSetItemI5.getAverageOutputInputLengthRatio(), averageOutputInputLengthRatioDataSet2DataSetItemI5());
		
	}
	
	/*
	 * item i1: health and should be health
	 * item i2: finance and should be health
	 * item i3: science and should be health
	 */
	
	private static DataSets createDatasets() {	
		
		 // Health
		DataSetItem item1 = new DataSetItem("i1", testInput1, Arrays.asList("health"));
		DataSetItem item2 = new DataSetItem("i2", testInput1, Arrays.asList("finance"));
		DataSetItem item3 = new DataSetItem("i3", testInput1, Arrays.asList("science"));
		
		DataSetItem item4 = new DataSetItem("i4", testInput2, Arrays.asList("finance"));
		DataSetItem item5 = new DataSetItem("i5", testInput3, Arrays.asList("health", "finance"));

		List<DataSetItem> itemList = new ArrayList<DataSetItem>();
		itemList.add(item1);
		itemList.add(item2);

		return new DataSets(Arrays.asList(
				new DataSet("DataSet1", Arrays.asList(item1, item2, item3)), 
				new DataSet("DataSet2", Arrays.asList(item4, item5)))
			);
	}
	
	private static String readHealthOutput() {
		try {
			return FileUtils.readFileToString(new ClassPathResource("examples/unlabeled/health/h01.txt").getFile()).trim();
		} catch (Exception e) { return null; }
	}
	
	private static String readFinanceOutput() {
		try {
			return FileUtils.readFileToString(new ClassPathResource("examples/unlabeled/finance/f01.txt").getFile()).trim();
		} catch (Exception e) { return null; }
	}
	
	private static String readScienceOutput() {
		try {
			return FileUtils.readFileToString(new ClassPathResource("examples/unlabeled/science/s01.txt").getFile()).trim();
		} catch (Exception e) { return null; }
	}
	
	private static Double averageOutputInputLengthRatioDataSet1() {
		return calcAverage(averageOutputInputLengthRatioDataSet1DataSetItemI1(), averageOutputInputLengthRatioDataSet1DataSetItemI2(), averageOutputInputLengthRatioDataSet1DataSetItemI3());
	}
	
	private static Double averageOutputLengthDataSet1() {
		return calcAverage(averageOutputLengthDataSet1DataSetItemI1(), averageOutputLengthDataSet1DataSetItemI2(), averageOutputLengthDataSet1DataSetItemI3());
	}
	
	private static Double averageOutputInputLengthRatioDataSet2() {
		return calcAverage(averageOutputInputLengthRatioDataSet2DataSetItemI4(), averageOutputInputLengthRatioDataSet2DataSetItemI5());
	}
	
	private static Double averageOutputLengthDataSet2() {
		return calcAverage(averageOutputLengthDataSet2DataSetItemI4(), averageOutputLengthDataSet2DataSetItemI5());
	}
	
	private static Double averageOutputInputLengthRatioDataSet1DataSetItemI1() {
		return averageOutputLengthDataSet1DataSetItemI1() / (double) testInput1.length();
	}
	
	private static Double averageOutputInputLengthRatioDataSet1DataSetItemI2() {
		return averageOutputLengthDataSet1DataSetItemI2() / (double) testInput1.length();
	}
	
	private static Double averageOutputInputLengthRatioDataSet1DataSetItemI3() {
		return averageOutputLengthDataSet1DataSetItemI3() / (double) testInput1.length();
	}
	
	private static Double averageOutputInputLengthRatioDataSet2DataSetItemI4() {
		return averageOutputLengthDataSet2DataSetItemI4() / (double) testInput2.length();
	}
	
	private static Double averageOutputInputLengthRatioDataSet2DataSetItemI5() {
		return averageOutputLengthDataSet2DataSetItemI5() / (double) testInput3.length();
	}
	
	private static Double averageOutputLengthDataSet1DataSetItemI1() {
		return (double) readHealthOutput().length();
	}
	
	private static Double averageOutputLengthDataSet1DataSetItemI2() {
		return (double) readFinanceOutput().length();
	}
	
	private static Double averageOutputLengthDataSet1DataSetItemI3() {
		return (double) readScienceOutput().length();
	}
	
	private static Double averageOutputLengthDataSet2DataSetItemI4() {
		return (double) readFinanceOutput().length();
	}
	
	private static Double averageOutputLengthDataSet2DataSetItemI5() {
		return calcAverage((double) readFinanceOutput().length(), (double) readHealthOutput().length());
	}
	
	private static Double calcAverage(Double... values) {		
		Double avg = 0.0;
		for(Double v: values) {
			avg += v;
		}
		return avg / values.length;
	}
	
	private static void assertDouble(double actual, double expected) {
		assertEquals(expected, actual, 0.000005);
	}
	
}

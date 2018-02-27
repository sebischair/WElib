package de.tum.in.wwwmatthes.stm;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.datavec.api.util.ClassPathResource;
import org.junit.Test;

import de.tum.in.wwwmatthes.stm.evaluation.Evaluation;
import de.tum.in.wwwmatthes.stm.evaluation.EvaluationDataSet;
import de.tum.in.wwwmatthes.stm.evaluation.EvaluationDataSets;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSet;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSetItem;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSets;
import de.tum.in.wwwmatthes.stm.models.Model;
import de.tum.in.wwwmatthes.stm.models.ModelFactory;
import de.tum.in.wwwmatthes.stm.models.config.Config;
import de.tum.in.wwwmatthes.stm.models.config.ConfigTfidfFactory;

public class EvaluationErrorTests {
	
	/*
	@Test
	public void test() throws Exception {
		
		// Create Config
		Config config = new ConfigTfidfFactory()
				//.documentsSourceFile(new ClassPathResource("examples/unlabeled").getFile())
				.minWordFrequency(0)
				.build();

		// Create Model
		Model model = ModelFactory.createFromConfig(config);
		model.fit();
		
		// Evaluation
		EvaluationDataSets evaluatedDataSets = Evaluation.evaluate(createDatasets(), model);
		
		// -- Data Set 1
		EvaluationDataSet evaluatedDataSet1 = evaluatedDataSets.getEvaluatedDataSetList().get(0);
		System.out.println(evaluatedDataSet1.getEvaluatedDataSetItemList().get(0).getOutputMap());
		System.out.println(evaluatedDataSet1.getEvaluatedDataSetItemList().get(0).getSimilarityList());
				
		// -- Data Set 2
		EvaluationDataSet evaluatedDataSet2WithError = evaluatedDataSets.getEvaluatedDataSetListWithError().get(0);
		System.out.println(evaluatedDataSet2WithError.getErrorMessage());
		System.out.println(evaluatedDataSet2WithError.getEvaluatedDataSetItemListWithError().get(0).getOutputMap());
		System.out.println(evaluatedDataSet2WithError.getEvaluatedDataSetItemListWithError().get(0).getSimilarityList());

	}
	
	private static DataSets createDatasets() {	
		
		 // Health
		DataSetItem item1 = new DataSetItem("i1", "Before healing others, heal yourself.", Arrays.asList("health"));
		DataSetItem item2 = new DataSetItem("i2", "pumuckl", Arrays.asList("finance"));
		
		DataSetItem item3 = new DataSetItem("i3", "pumuckl", Arrays.asList("science"));

		List<DataSetItem> itemList = new ArrayList<DataSetItem>();
		itemList.add(item1);
		itemList.add(item2);

		return new DataSets(Arrays.asList(
				new DataSet("DataSet1", Arrays.asList(item1, item2)), 
				new DataSet("DataSet2", Arrays.asList(item3)))
			);
	}
	
	*/

}

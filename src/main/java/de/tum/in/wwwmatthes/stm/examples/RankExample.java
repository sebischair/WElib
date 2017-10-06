package de.tum.in.wwwmatthes.stm.examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.datavec.api.util.ClassPathResource;
import de.tum.in.wwwmatthes.stm.evaluation.DataSet;
import de.tum.in.wwwmatthes.stm.evaluation.DataSetItem;
import de.tum.in.wwwmatthes.stm.evaluation.DataSets;
import de.tum.in.wwwmatthes.stm.exceptions.InvalidConfigException;
import de.tum.in.wwwmatthes.stm.models.config.Config;
import de.tum.in.wwwmatthes.stm.models.config.ConfigDoc2VecFactory;
import de.tum.in.wwwmatthes.stm.models.config.ConfigFactory;
import de.tum.in.wwwmatthes.stm.models.config.ConfigTfidfFactory;
import de.tum.in.wwwmatthes.stm.models.config.ConfigType;
import de.tum.in.wwwmatthes.stm.models.Model;
import de.tum.in.wwwmatthes.stm.models.ModelFactory;

public class RankExample {

	public static void main(String[] args) throws IOException, InvalidConfigException {

		// Load Configuration File
		File configFile = new ClassPathResource("examples/config/example.config").getFile();

		// Create Configuration
		// Config config = ConfigFactory.buildFromFile(configFile);
		// config.writeToFile(new File("/path/to/file"));
		
		Config config = new ConfigDoc2VecFactory()
				.useStemming(true)
				.allowedPosTags(Arrays.asList("NN", "NNS"))
				.documentsSourceFile(new ClassPathResource("examples/labeled").getFile())
				// .corpusFile(new File("/Users/christopherl/citadel/data/corpus/CORP_CONTROLS"))
				.addDefaultStopWords(true)
				.stopWords(Arrays.asList("Hallo"))
				.epochs(1)
				.iterations(1)
				.batchSize(1)
				.layerSize(1)
				.windowSize(1)
				.minLearningRate(0.4)
				.learningRate(0.3)
				.sampling(0.5)
				.negativeSample(0.5)
				.build();
		
		// Create Model
		Model model = ModelFactory.createFromConfig(config);

		// Fit Model
		model.fit();

		/*
		 * Alternative:
		 * 
		 * Model model = ModelFactory.createFromConfigFile(configFile);
		 * 
		 */

		// Create dataset with truths
		DataSets dataSets = createDatasets();
		System.out.println(dataSets);

		System.out.println("Evaluation");
		System.out.println("MRR: " + dataSets.getMRR());
		dataSets.evaluateWithModel(model);
		System.out.println("MRR: " + dataSets.getMRR());

		// dataSets.writeToFile(new File("/path/to/file"));
	}

	private static DataSets createDatasets() {

		List<String> item1List = new ArrayList<String>();
		item1List.add("finance");

		DataSetItem item1 = new DataSetItem("i1",
				"One of the funny things about the stock market is that every time one person buys, another sells, and both think they are astute.",
				item1List);

		List<String> item2List = new ArrayList<String>();
		item2List.add("health");

		DataSetItem item2 = new DataSetItem("i2",
				"People who attend support groups who have been diagnosed with a life-challenging illness live on average twice as long after diagnosis as people who don't.",
				item2List);

		List<DataSetItem> itemList = new ArrayList<DataSetItem>();
		itemList.add(item1);
		itemList.add(item2);

		return new DataSets(new DataSet("Random", itemList));
	}

}

package de.tum.in.wwwmatthes.stm.examples;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.resource.ResourceInitializationException;
import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.nd4j.linalg.primitives.Pair;

import de.tum.in.wwwmatthes.stm.evaluation.Evaluation;
import de.tum.in.wwwmatthes.stm.evaluation.EvaluationDataSet;
import de.tum.in.wwwmatthes.stm.evaluation.EvaluationDataSetItem;
import de.tum.in.wwwmatthes.stm.evaluation.EvaluationDataSets;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSet;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSetItem;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSets;
import de.tum.in.wwwmatthes.stm.exceptions.InvalidConfigException;
import de.tum.in.wwwmatthes.stm.exceptions.VocabularyMatchException;
import de.tum.in.wwwmatthes.stm.models.Model;
import de.tum.in.wwwmatthes.stm.models.ModelFactory;
import de.tum.in.wwwmatthes.stm.models.config.Config;
import de.tum.in.wwwmatthes.stm.models.config.ConfigDoc2VecFactory;
import de.tum.in.wwwmatthes.stm.models.config.ConfigTfidfFactory;
import de.tum.in.wwwmatthes.stm.models.config.ConfigWord2VecFactory;
import de.tum.in.wwwmatthes.stm.tokenizers.CustomTokenizerFactory;

public class RankExample {

	public static void main(String[] args) throws Exception {
		
		// Documents To Rank
		Map<String, String> documents = new HashMap<String, String>();
		documents.put("FINANCE", "The Russian Trading System (RTS) was a stock market established in 1995 in Moscow, consolidating various regional trading floors into one exchange.");
		documents.put("HEALTH", "Without treatment, the average survival time after infection with HIV is estimated to be 9 to 11 years, depending on the HIV subtype.");
		documents.put("SCIENCE", "Big data is a broad term for data sets so large or complex that traditional data processing applications are inadequate.");
		
		// Config
		Config config = new ConfigWord2VecFactory()
				.corpusSourceFile(new ClassPathResource("examples/corpus").getFile())
				.enablePreprocessing(true)
				.epochs(10)
				.build();

		// Model
		Model model = ModelFactory.createFromConfig(config);
		model.fit();
		model.putDocuments(documents);
		
		System.out.println(model.rankedDocumentsForText("bank"));
		System.out.println(model.rankedDocumentsForText("software"));
		
		// Evaluation
		EvaluationDataSets evaluatedDataSets = Evaluation.evaluate(createDatasets(), model);
		System.out.println("RPS: " + evaluatedDataSets.getAverageRank());
	}

	private static DataSets createDatasets() {

		DataSetItem item1 = new DataSetItem("i1",
				"bank",
				Arrays.asList("FINANCE"));
		
		DataSetItem item2 = new DataSetItem("i2",
				"software",
				Arrays.asList("SCIENCE"));
	
		return new DataSets(Arrays.asList(
				new DataSet("Something1", Arrays.asList(item1, item2))
			));
	}
	
}

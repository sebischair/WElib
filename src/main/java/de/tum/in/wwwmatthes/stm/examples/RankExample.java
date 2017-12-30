package de.tum.in.wwwmatthes.stm.examples;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.uima.resource.ResourceInitializationException;
import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
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
import de.tum.in.wwwmatthes.stm.models.config.ConfigWord2VecFactory;
import de.tum.in.wwwmatthes.stm.models.config.ConfigWord2VecTfidfFactory;
import de.tum.in.wwwmatthes.stm.tokenizers.CustomTokenizerFactory;

public class RankExample {

	public static void main(String[] args) throws Exception {
		
		// Build Config
		Config config2 = new ConfigDoc2VecFactory()
				.corpusSourceFile(new ClassPathResource("examples/labeled").getFile())
				.documentsSourceFile(new ClassPathResource("examples/unlabeled").getFile())
				.minWordFrequency(0)
				.epochs(5)
				.addDefaultStopWords(true)
				.enablePreprocessing(true)
				.build();

		// Create Model
		Model model2 = ModelFactory.createFromConfig(config2);
		model2.fit();
		
		// Evaluation
		EvaluationDataSets evaluatedDataSets2 = Evaluation.evaluate(createDatasets(), model2);
		
		System.out.println();
		System.out.println("=Data Sets=");
		System.out.println(evaluatedDataSets2.getRelativeAverageRankInPercentage());
		
		System.exit(0);
		
		// Build Config
		Config config = new ConfigWord2VecFactory()
				.corpusSourceFile(new ClassPathResource("examples/labeled").getFile())
				.documentsSourceFile(new ClassPathResource("examples/unlabeled").getFile())
				.minWordFrequency(0)
				.addDefaultStopWords(true)
				.enablePreprocessing(true)
				//.enableStemming(true)
				//.allowedPosTags(Arrays.asList("NN", "NNS", "NNP", "NNPS"))
				//.epochs(5)
				.build();

		// Create Model
		Model model = ModelFactory.createFromConfig(config);
		model.fit();
		
		// Evaluation
		EvaluationDataSets evaluatedDataSets = Evaluation.evaluate(createDatasets(), model);
		
		System.out.println();
		System.out.println("=Data Sets=");
		System.out.println(evaluatedDataSets.getAverageMrr());
		System.out.println(evaluatedDataSets.getAverageInputLength());
		
		for(EvaluationDataSet evaluatedDataSet: evaluatedDataSets.getEvaluatedDataSetList()) {
			System.out.println();
			System.out.println("==Data Set==");
			System.out.println(evaluatedDataSet.getDataSet().getIdentifier());
			System.out.println(evaluatedDataSet.getAverageMrr());
			System.out.println(evaluatedDataSet.getAverageInputLength());
			
			for(EvaluationDataSetItem evaluatedDataSetItem: evaluatedDataSet.getEvaluatedDataSetItemList()) {
				System.out.println();
				System.out.println("===Data Set Item===");
				System.out.println(evaluatedDataSetItem.getDataSetItem().getIdentifier());
				System.out.println(evaluatedDataSetItem.getOutputMap());
				System.out.println("MRR: " + evaluatedDataSetItem.getMrr());
				System.out.println("AR: " + evaluatedDataSetItem.getAverageRank());
				System.out.println("AR%: " + evaluatedDataSetItem.getAverageRankInPercentage());
				System.out.println("EOR: " + evaluatedDataSetItem.getEvaluationOptimalRank());
				System.out.println("EOR%: " + evaluatedDataSetItem.getEvaluationOptimalRankInPercentage());
				System.out.println("RAR%: " + evaluatedDataSetItem.getRelativeAverageRankInPercentage());
			}
		}
	}

	private static DataSets createDatasets() {

		DataSetItem item1 = new DataSetItem("i1",
				"The Russian Trading System (RTS) was a stock market established in 1995 in Moscow, consolidating various regional trading floors into one exchange.\n" + 
				"Originally RTS was modeled on NASDAQ's trading and settlement software; in 1998 the exchange went on line with its own in-house system.\n" + 
				"Initially created as a non-profit organisation, it was transformed into a joint-stock company.\n" + 
				"In 2011 MICEX merged with RTS creating Moscow Exchange.\n" + 
				"The RTS Index, RTSI, the official Moscow Exchange indicator, was first calculated on September 1, 1995.\n" + 
				"The RTS is a capitalization-weighted composite index calculated based on prices of the 50 most liquid Russian stocks listed on Moscow Exchange.\n" + 
				"The Index is calculated in real time and denominated in US dollars.",
				Arrays.asList("finance"));
		
		DataSetItem item2 = new DataSetItem("i2",
				"Human immunodeficiency virus infection and acquired immune deficiency syndrome (HIV/AIDS) is a spectrum of conditions caused by infection with the human immunodeficiency virus (HIV).\n" + 
				"It may also be referred to as HIV disease or HIV infection.\n" + 
				"Following initial infection, a person may experience a brief period of influenza-like illness.\n" + 
				"This is typically followed by a prolonged period without symptoms.\n" + 
				"As the infection progresses, it interferes more and more with the immune system, making the person much more susceptible to common infections, like tuberculosis, as well as opportunistic infections and tumors that do not usually affect people who have working immune systems.\n" + 
				"The late symptoms of the infection are referred to as AIDS.\n" + 
				"This stage is often complicated by an infection of the lung known as pneumocystis pneumonia, severe weight loss, skin lesions caused by Kaposi's sarcoma, or other AIDS-defining conditions.\n" + 
				"HIV is transmitted primarily via unprotected sexual intercourse (including anal and oral sex), contaminated blood transfusions, hypodermic needles, and from mother to child during pregnancy, delivery, or breastfeeding.\n" + 
				"Some bodily fluids, such as saliva and tears, do not transmit HIV.\n" + 
				"Common methods of HIV/AIDS prevention include encouraging and practicing safe sex, needle-exchange programs, and treating those who are infected.\n" + 
				"There is no cure or vaccine; however, antiretroviral treatment can slow the course of the disease and may lead to a near-normal life expectancy.\n" + 
				"While antiretroviral treatment reduces the risk of death and complications from the disease, these medications are expensive and have side effects. Diagnosis.\n" + 
				"Treatment is recommended as soon as the diagnosis is made.\n" + 
				"Without treatment, the average survival time after infection with HIV is estimated to be 9 to 11 years, depending on the HIV subtype.",
				Arrays.asList("health"));
		
		DataSetItem item3 = new DataSetItem("i3",
				"The first wealth is health.",
				Arrays.asList("health"));
		DataSetItem item4 = new DataSetItem("i4",
				"In this world nothing can be said to be certain, except death and taxes.",
				Arrays.asList("finance"));
		DataSetItem item5 = new DataSetItem("i5",
				"Time and health are two precious assets that we don't recognize and appreciate until they have been depleted."
				+ " The way to make money is to buy when blood is running in the streets.",
				Arrays.asList("health", "finance"));

		List<DataSetItem> itemList = new ArrayList<DataSetItem>();
		itemList.add(item1);
		itemList.add(item2);

		return new DataSets(Arrays.asList(
				new DataSet("Something1", Arrays.asList(item1, item2)), 
				new DataSet("Something2", Arrays.asList(item3, item4, item5)))
			);
	}
	
	private static void runNativeWord2Vec() throws FileNotFoundException, ResourceInitializationException {
        // Strip white space before and after for each line
		for(int i = 0; i < 1; i++) {
	        SentenceIterator iter = new BasicLineIterator(new ClassPathResource("examples/corpus/raw_sentences.txt").getFile());
	        
	        List<String> tags = new ArrayList<String>(); 
	        //tags.addAll(Arrays.asList("NOUN"));
	        tags.addAll(Arrays.asList("NN", "NNS", "NNP", "NNPS"));
	        //tags.addAll(Arrays.asList("CC", "CD", "DT", "EX", "FW", "IN"));
	        tags.addAll(Arrays.asList("JJ", "JJR", "JJS", "LS", "MD"));


	        //TokenizerFactory t = new DefaultTokenizerFactory();
	        //TokenizerFactory t = new DefaultTokenizerFactory();
	        //TokenizerFactory t = new UimaTokenizerFactory();
	        
	        CustomTokenizerFactory t = new CustomTokenizerFactory();
	        t.setStemmingEnabled(true);
	        t.setPreprocessingEnabled(true);
	        t.setAllowedPosTags(tags);
	        
	        //TokenizerFactory t = new PosUimaTokenizerFactory(tags);
	        //TokenizerFactory t = new PosUimaTokenizerFactory(Arrays.asList("NN", "NNS"), false);

	        ParagraphVectors vec = new ParagraphVectors.Builder()
	        //Word2Vec vec = new Word2Vec.Builder()
	                .minWordFrequency(5)
	                .epochs(5)
	                .iterations(5)
	                .layerSize(100)
	                .seed(42)
	                .windowSize(5)
	                .iterate(iter)
	                .tokenizerFactory(t)
	                .allowParallelTokenization(false)
	                .trainWordVectors(true)
	                .build();
	        vec.fit();
	        
	        System.out.println(vec.getVocab().docAppearedIn("day"));
	        System.out.println(vec.getConfiguration());
	        
			System.out.println(vec.wordsNearest("day", 20));
			// [night, week, year, game, season, group, time, office, second, off]
		}
	}
	
}

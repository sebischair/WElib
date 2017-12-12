package de.tum.in.wwwmatthes.stm.examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.uima.resource.ResourceInitializationException;
import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.PosUimaTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.UimaTokenizerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;

import de.tum.in.wwwmatthes.stm.evaluation.Evaluation;
import de.tum.in.wwwmatthes.stm.evaluation.EvaluationDataSet;
import de.tum.in.wwwmatthes.stm.evaluation.EvaluationDataSetItemOutput;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSet;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSetItem;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSets;
import de.tum.in.wwwmatthes.stm.exceptions.InvalidConfigException;
import de.tum.in.wwwmatthes.stm.models.config.Config;
import de.tum.in.wwwmatthes.stm.models.config.ConfigDoc2VecFactory;
import de.tum.in.wwwmatthes.stm.models.config.ConfigFactory;
import de.tum.in.wwwmatthes.stm.models.config.ConfigTfidfFactory;
import de.tum.in.wwwmatthes.stm.models.config.ConfigType;
import de.tum.in.wwwmatthes.stm.models.config.ConfigWord2VecFactory;
import de.tum.in.wwwmatthes.stm.tokenizers.CustomTokenizerFactory;
import de.tum.in.wwwmatthes.stm.models.Model;
import de.tum.in.wwwmatthes.stm.models.ModelFactory;

public class RankExample {

	public static void main(String[] args) throws IOException, InvalidConfigException, ResourceInitializationException {
		
		// Model
		
		Config config = new ConfigTfidfFactory()
				//.corpusSourceFile(corpusSourcePath)
				//.corpusSourceFile(new ClassPathResource("examples/corpus-doc/corpus_1").getFile())
				.documentsSourceFile(new File("/Users/christopherl/citadel/data/labels/DOC"))
				.minWordFrequency(0)
				.addDefaultStopWords(true)
				.enablePreprocessing(true)
				//.enableStemming(true)
				// .allowedPosTags(Arrays.asList("NN", "NNS", "NNP", "NNPS", "VB", "VBD", "VBG", "VBN"))
				.allowedPosTags(Arrays.asList("NN", "NNS", "NNP", "NNPS"))
				//.epochs(5)
				.build();
				
		
		/*
		Config config = new ConfigDoc2VecFactory()
				//.corpusSourceFile(corpusSourcePath)
				.corpusSourceFile(new File("/Users/christopherl/citadel/data/corpus/CTRLS"))
				//.corpusSourceFile(new ClassPathResource("examples/corpus-doc/corpus_1").getFile())
				.documentsSourceFile(new File("/Users/christopherl/citadel/data/labels/DOC_SUBTOPIC_TAGS_TOPIC"))
				.minWordFrequency(1)
				.addDefaultStopWords(true)
				.enablePreprocessing(true)
				//.enableStemming(true)
				// .allowedPosTags(Arrays.asList("NN", "NNS", "NNP", "NNPS", "VB", "VBD", "VBG", "VBN"))
				.allowedPosTags(Arrays.asList("NN", "NNS", "NNP", "NNPS"))
				//.epochs(5)
				.build();
				*/
		
		// Create Model
		Model model = ModelFactory.createFromConfig(config);
		model.fit();
		
		// Evaluation
		DataSets dataSets = DataSets.readFromFile(new File("/Users/christopherl/citadel/data/datasets/DEFAULT_CLEAN"));
		Evaluation evaluation = new Evaluation(dataSets);
		evaluation.evaluate(model);
		
		System.out.println(evaluation.getMrr());
		
		System.exit(0);
		
		System.out.println(" ");
		Map<String, EvaluationDataSetItemOutput> map = evaluation.getEvaluationDataSets().getDataSetList().get(0).getDataSetItemList().get(0).getOutputMap();
		for(String key : map.keySet()) {
			System.out.println(map.get(key).getRank());
		}
		
		System.out.println(" ");
		map = evaluation.getEvaluationDataSets().getDataSetList().get(1).getDataSetItemList().get(0).getOutputMap();
		for(String key : map.keySet()) {
			System.out.println(map.get(key).getRank());
		}
		
		System.out.println(" ");
		map = evaluation.getEvaluationDataSets().getDataSetList().get(2).getDataSetItemList().get(0).getOutputMap();
		for(String key : map.keySet()) {
			System.out.println(map.get(key).getRank());
		}
		
		
		System.exit(0);
		
		//compareWord2VecAndDoc2Vec();
		//System.exit(0);
		
		//testTfidf();
		//System.exit(0);
		
		//runNativeWord2Vec();
		//System.exit(0);

		// Load Configuration File
		//File configFile = new ClassPathResource("examples/config/example.config").getFile();

		// Create Configuration
		// Config config = ConfigFactory.buildFromFile(configFile);
		// config.writeToFile(new File("/path/to/file"));
		//List<Config> configs = Arrays.asList(config1, config2, config3, config4, config5);
		
		/*
		File corpusSourcePath = new File("/Users/christopherl/citadel/data/corpus/REG");
		
		for(int i = 0; i <  1; i++) {
			
			
			Config config = new ConfigDoc2VecFactory()
					//.corpusSourceFile(corpusSourcePath)
					.corpusSourceFile(new ClassPathResource("examples/corpus-doc/corpus_1").getFile())
					.documentsSourceFile(new File("/Users/christopherl/citadel/data/labels/DOC_SUBTOPIC_TITLE_TOPIC"))
					.minWordFrequency(1)
					.addDefaultStopWords(true)
					.enablePreprocessing(true)
					.epochs(5)
					.iterations(10)
					.windowSize(5)
					.layerSize(100)
					
					.build();
			
			// Create Model
			Model model = ModelFactory.createFromConfig(config);

			// Fit Model
			model.fit();
			
			// Evaluate
			evaluateDatasetsForCTRLS_REG(model);
		}
		System.exit(0);
		*/
		/*
		
        List<String> tags = new ArrayList<String>(); 
        tags.addAll(Arrays.asList("NN", "NNS", "NNP", "NNPS"));
        //tags.addAll(Arrays.asList("CC", "CD", "DT", "EX", "FW", "IN"));
        //tags.addAll(Arrays.asList("JJ", "JJR", "JJS", "LS", "MD"));
        //tags.addAll(Arrays.asList("VB", "VBD", "VBG", "VBN", "VBP", "VBZ"));
		
		// [analysis, processing, applications, making, information, velocity, logs, cameras]
		// [night, week, year, game, season, group, time, office, second, off]
		Config config1 = new ConfigDoc2VecFactory()
				
				.corpusSourceFile(new ClassPathResource("examples/corpus-doc/corpus_2").getFile())
				.documentsSourceFile(new File("/Users/christopherl/citadel/data/labels/DOC_SUBTOPIC_TITLE_TOPIC"))
				
				.minWordFrequency(0)
				.addDefaultStopWords(true)
				.enablePreprocessing(true)
				.epochs(9)
				
				.build();
		
		Config config2 = new ConfigDoc2VecFactory()
				
				.corpusSourceFile(new ClassPathResource("examples/corpus-doc/corpus_2").getFile())
				.documentsSourceFile(new File("/Users/christopherl/citadel/data/labels/DOC_SUBTOPIC_TITLE_TOPIC"))
				
				.minWordFrequency(1)
				.addDefaultStopWords(true)
				.enablePreprocessing(true)
				.epochs(9)
				
				.build();
		
		Config config3 = new ConfigDoc2VecFactory()
				
				.corpusSourceFile(new ClassPathResource("examples/corpus-doc/corpus_2").getFile())
				.documentsSourceFile(new File("/Users/christopherl/citadel/data/labels/DOC_SUBTOPIC_TITLE_TOPIC"))
				
				.minWordFrequency(5)
				.addDefaultStopWords(true)
				.enablePreprocessing(true)
				.epochs(9)
				
				.build();
		
		Config config4 = new ConfigDoc2VecFactory()
				
				.corpusSourceFile(new ClassPathResource("examples/corpus-doc/corpus_2").getFile())
				.documentsSourceFile(new File("/Users/christopherl/citadel/data/labels/DOC_SUBTOPIC_TITLE_TOPIC"))
				
				.minWordFrequency(2)
				.addDefaultStopWords(true)
				.enablePreprocessing(true)
				.epochs(9)
				
				.build();
		
		Config config5 = new ConfigDoc2VecFactory()
				
				.corpusSourceFile(new ClassPathResource("examples/corpus-doc/corpus_2").getFile())
				.documentsSourceFile(new File("/Users/christopherl/citadel/data/labels/DOC_SUBTOPIC_TITLE_TOPIC"))
				
				.minWordFrequency(2)
				.addDefaultStopWords(true)
				.enablePreprocessing(true)
				.epochs(9)
				
				.build();
		  
		List<Config> configs = Arrays.asList(config1, config2, config3, config4, config5);
		for(Config config : configs) {
			
			// Create Model
			Model model = ModelFactory.createFromConfig(config);

			// Fit Model
			model.fit();
			
			// Evaluate
			evaluateDatasetsForCTRLS_REG(model);
		}	
		*/
	}
	

	private static void evaluateDatasetsForCTRLS_REG(Model model) throws JsonSyntaxException, IOException {
		
		File dataSetsFile = new ClassPathResource("examples/datasets/truths.json").getFile();
		DataSets dataSets = new Gson().fromJson(FileUtils.readFileToString(dataSetsFile), DataSets.class);
		
		Evaluation evaluation = new Evaluation(dataSets);
		evaluation.evaluate(model);

		System.out.println("MRR: " + evaluation.getEvaluationDataSets().getMrr());
		System.out.println("AIDR: " + evaluation.getEvaluationDataSets().getAverageInputDocumentRatio());
		for(EvaluationDataSet dataSet : evaluation.getEvaluationDataSets().getDataSetList()) {
			System.out.println("Id: " + dataSet.getDataSet().getIdentifier() + " AIDR: " + dataSet.getAverageInputDocumentRatio());
		}
	}

	private static DataSets createDatasets() {

		List<String> item1List = new ArrayList<String>();
		item1List.add("finance");

		DataSetItem item1 = new DataSetItem("i1",
				"The Russian Trading System (RTS) was a stock market established in 1995 in Moscow, consolidating various regional trading floors into one exchange.\n" + 
				"Originally RTS was modeled on NASDAQ's trading and settlement software; in 1998 the exchange went on line with its own in-house system.\n" + 
				"Initially created as a non-profit organisation, it was transformed into a joint-stock company.\n" + 
				"In 2011 MICEX merged with RTS creating Moscow Exchange.\n" + 
				"The RTS Index, RTSI, the official Moscow Exchange indicator, was first calculated on September 1, 1995.\n" + 
				"The RTS is a capitalization-weighted composite index calculated based on prices of the 50 most liquid Russian stocks listed on Moscow Exchange.\n" + 
				"The Index is calculated in real time and denominated in US dollars.",
				item1List);

		List<String> item2List = new ArrayList<String>();
		item2List.add("health");

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
				item2List);

		List<DataSetItem> itemList = new ArrayList<DataSetItem>();
		itemList.add(item1);
		itemList.add(item2);

		return new DataSets(new DataSet("Random", itemList));
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
	
	static void testTfidf() throws InvalidConfigException, JsonSyntaxException, IOException {
		Config config = new ConfigTfidfFactory()
				.documentsSourceFile(new File("/Users/christopherl/citadel/data/labels/DOC_SUBTOPIC_TITLE"))
				
				.minWordFrequency(1)
				.addDefaultStopWords(true)
				.enablePreprocessing(true)
				.enableStemming(true)
				.allowedPosTags(null)
										
				.build();
		
		// Create Model
		Model model = ModelFactory.createFromConfig(config);

		// Fit Model
		model.fit();
		
		evaluateDatasetsForCTRLS_REG(model);
	}

	private static void compareWord2VecAndDoc2Vec() throws InvalidConfigException, JsonSyntaxException, IOException {
		
		// Word2Vec 
		Config config = new ConfigWord2VecFactory()
				.corpusSourceFile(new ClassPathResource("examples/corpus-doc/corpus_1").getFile())
				.documentsSourceFile(new File("/Users/christopherl/citadel/data/labels/DOC"))
				.minWordFrequency(1)
				.addDefaultStopWords(true)
				.enablePreprocessing(true)
				.epochs(1)
				
				.build();
		
		Model model = ModelFactory.createFromConfig(config);
		model.fit();
		evaluateDatasetsForCTRLS_REG(model);
		
		// Doc2 Vec
		Config config2 = new ConfigDoc2VecFactory()
				.corpusSourceFile(new ClassPathResource("examples/corpus-doc/corpus_2").getFile())
				.documentsSourceFile(new File("/Users/christopherl/citadel/data/labels/DOC"))
				.minWordFrequency(1)
				.addDefaultStopWords(true)
				.enablePreprocessing(true)
				.epochs(5)
				
				.build();
		
		Model model2 = ModelFactory.createFromConfig(config2);
		model2.fit();
		evaluateDatasetsForCTRLS_REG(model2);
	}
	
}

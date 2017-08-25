package de.tum.in.wwwmatthes.stm.examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.datavec.api.util.ClassPathResource;

import de.tum.in.wwwmatthes.stm.evaluation.DataSet;
import de.tum.in.wwwmatthes.stm.evaluation.DataSetItem;
import de.tum.in.wwwmatthes.stm.evaluation.Evaluation;
import de.tum.in.wwwmatthes.stm.models.ModelDoc2Vec;
import de.tum.in.wwwmatthes.stm.models.ModelTFIDF;
import de.tum.in.wwwmatthes.stm.models.base.Model;

public class RankExample {

	public static void main(String[] args) throws FileNotFoundException {
		
		// Load documents
		File corpusSourceFile 	= new ClassPathResource("examples/labeled").getFile();
		File documentsSourceFile = new ClassPathResource("examples/unlabeled").getFile();
		
		// Doc2Vec
		Model doc2Vec = new ModelDoc2Vec(documentsSourceFile, corpusSourceFile);
		doc2Vec.fit();
		System.out.println("Model Doc2Vec fitted.");
		
		// TFIDF
		Model tfidf = new ModelTFIDF(documentsSourceFile);
		tfidf.fit();
		System.out.println("Model TFIDF fitted.");
		
		// Create dataset with truths
		List<String> item1List = new ArrayList<String>();
		item1List.add("finance");
		
		DataSetItem item1 = new DataSetItem("i1", "One of the funny things about the stock market is that every time one person buys, another sells, and both think they are astute.", item1List);
		
		List<String> item2List = new ArrayList<String>();
		item2List.add("health");
		
		DataSetItem item2 = new DataSetItem("i2", "People who attend support groups who have been diagnosed with a life-challenging illness live on average twice as long after diagnosis as people who don't.", item2List);
		
		List<DataSetItem> itemList = new ArrayList<DataSetItem>();
		itemList.add(item1);
		itemList.add(item2);
		
		DataSet dataSet = new DataSet(itemList);
		System.out.println();
		System.out.println(dataSet);
		
		// Evaluate
		//System.out.println(doc2Vec.rankedDocumentsWithSimilaritiesForText(item1.getInput()));
		//System.out.println(doc2Vec.rankedDocumentsWithSimilaritiesForText(item2.getInput()));
		
		//System.out.println(tfidf.rankedDocumentsWithSimilaritiesForText(item1.getInput()));
		//System.out.println(tfidf.rankedDocumentsWithSimilaritiesForText(item2.getInput()));
		
		Evaluation evaluationDoc2Vec = new Evaluation(dataSet, doc2Vec);
		System.out.println("Doc2Vec MRR: " + evaluationDoc2Vec.evaluateWithMRR());
		
		Evaluation evaluationTFIDF = new Evaluation(dataSet, tfidf);
		System.out.println("TFIDF MRR: " + evaluationTFIDF.evaluateWithMRR());
		
	}
	
}

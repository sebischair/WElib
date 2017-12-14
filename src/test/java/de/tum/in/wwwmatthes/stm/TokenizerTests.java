package de.tum.in.wwwmatthes.stm;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import de.tum.in.wwwmatthes.stm.tokenizers.CustomTokenizerFactory;

public class TokenizerTests {

	private static String input = "I gambled at a casino yesterday and I won 50$. So I am gambling now and I will gamble tomorrow.";
	
	@Test
	public void testPreprocessing() {
		CustomTokenizerFactory factory = new CustomTokenizerFactory();
		factory.setPreprocessingEnabled(true);
		
		String expectedOutput 	= "i gambled at a casino yesterday and i won so i am gambling now and i will gamble tomorrow";
		String actualOutput		= factory.processString(input);
		
		assertString(actualOutput, expectedOutput);
	}
	
	@Test
	public void testPreprocessingAndStemming() {
		CustomTokenizerFactory factory = new CustomTokenizerFactory();
		factory.setPreprocessingEnabled(true);
		factory.setStemmingEnabled(true);
		
		String expectedOutput 	= "i gambl at a casino yesterday and i won so i am gambl now and i will gambl tomorrow";
		String actualOutput		= factory.processString(input);
		
		assertString(actualOutput, expectedOutput);
	}
	
	@Test
	public void testPosTaggingNouns() {
		CustomTokenizerFactory factory = new CustomTokenizerFactory();
		factory.setAllowedPosTags(Arrays.asList("NN", "NNS", "NNP", "NNPS"));
		
		String expectedOutput 	= "casino yesterday tomorrow";
		String actualOutput		= factory.processString(input);
		
		assertString(actualOutput, expectedOutput);
	}
	
	@Test
	public void testPosTaggingNounsAndVerbs() {
		CustomTokenizerFactory factory = new CustomTokenizerFactory();
		factory.setAllowedPosTags(Arrays.asList("NN", "NNS", "NNP", "NNPS",
				"VB", "VBD", "VBG", "VBN", "VBP", "VBZ"));
		
		String expectedOutput 	= "gambled casino yesterday won am gambling gamble tomorrow";
		String actualOutput		= factory.processString(input);
		
		assertString(actualOutput, expectedOutput);
	}
	
	// Private Static Methods
	
	private static void assertString(String actualString, String expectedString) {
		assertTrue("'" + actualString + "' not equal to '" + expectedString + "'", actualString.equals(expectedString));
	}

}

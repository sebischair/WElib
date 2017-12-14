package de.tum.in.wwwmatthes.stm.evaluation;

import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSets;
import de.tum.in.wwwmatthes.stm.models.Model;

public class Evaluation {
	
	public static EvaluationDataSets evaluate(DataSets dataSets, Model model) {
		return EvaluationDataSets.evaluate(dataSets, model);
	}

}

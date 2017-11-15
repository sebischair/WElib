package visualisation;

import java.io.File;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.plot.BarnesHutTsne;

public class PlotManager {
	
	public static void plot(ParagraphVectors paragraphVectors, File file) {

		BarnesHutTsne tsne = new BarnesHutTsne.Builder()
	            .setMaxIter(1000)
	            .stopLyingIteration(250)
	            .learningRate(500)
	            .useAdaGrad(false)
	            .theta(0.5)
	            .setMomentum(0.5)
	            .normalize(true)
	            .build();
		paragraphVectors.lookupTable().plotVocab(100, file);
	}

}

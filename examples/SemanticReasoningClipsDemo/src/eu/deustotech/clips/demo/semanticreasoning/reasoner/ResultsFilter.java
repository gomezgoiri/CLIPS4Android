package eu.deustotech.clips.demo.semanticreasoning.reasoner;

import eu.deustotech.clips.PrimitiveValue;

public interface ResultsFilter {
	public boolean isRetained(PrimitiveValue triple) throws Exception;
}

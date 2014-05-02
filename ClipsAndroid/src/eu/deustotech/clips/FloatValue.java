package eu.deustotech.clips;

/**
 * Any number consisting of an optional sign followed by only digits is stored as an integer.
 * All other numbers are stored as floats
 * (represented internally by CLIPS as a C double‑precision float).
 */
public class FloatValue extends NumberValue {

	public FloatValue() {
		super(new Double(0.0));
	}
	
	public FloatValue(double value) {
		super(new Double(value));
	}
	
	public FloatValue(Double value) {
		super(value);
	}
	
	protected Double getCastValue() {
		return ((Double) getValue());
	}
	
	public float floatValue() {
		return getCastValue().floatValue();
	}
	
	public double doubleValue() {
		return getCastValue().doubleValue();
	}
}
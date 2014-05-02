package eu.deustotech.clips;

/**
 * A number consists only of digits (0‑9), a decimal point (.), a sign (+ or ‑),
 * and, optionally, an (e) for exponential notation with its corresponding sign.
 * A number is either stored as a float or an integer. Any number consisting of
 * an optional sign followed by only digits is stored as an integer (represented
 * internally by CLIPS as a C long integer).
 */
public abstract class NumberValue extends PrimitiveValue {
	
	protected NumberValue(Number value) {
		super(value);
	}
	
	public Number numberValue() {
		return (Number) getValue();
	}
}

package eu.deustotech.clips;

/**
 * Any number consisting of an optional sign followed by only digits is
 * stored as an integer (represented internally by CLIPS as a C long integer).
 */
public class IntegerValue extends NumberValue {
	
	public IntegerValue() {
		super(new Long(0));
	}
	
	public IntegerValue(long value) {
		super(new Long(value));
	}
	
	public IntegerValue(Long value) {
		super(value);
	}
	
	protected Long getCastValue() {
		return ((Long) getValue());
	}
	
	public long longValue() {
		return getCastValue().longValue();
	}
	
	public int intValue() {
		return getCastValue().intValue();
	}
}
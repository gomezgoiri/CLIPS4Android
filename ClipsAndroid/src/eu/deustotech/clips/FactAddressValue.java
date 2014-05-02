package eu.deustotech.clips;

/**
 * <p>
 * A fact is a list of atomic values that are either referenced positionally
 * (ordered facts) or by name (non‑ordered or template facts). Facts are
 * referred to by index or address. This class represents the latter reference.
 * </p>
 * 
 * <p>
 * The printed format of a fact‑address is: <Fact-XXX>.
 * </p>
 */
public class FactAddressValue extends PrimitiveValue {
	private Environment owner;

	/*********************/
	/* FactAddressValue: */
	/*********************/
	public FactAddressValue(long value, Environment env) {
		super(new Long(value));
		this.owner = env;
	}

	/*******************/
	/* getEnvironment: */
	/*******************/
	public Environment getEnvironment() {
		return this.owner;
	}

	/*******************/
	/* getFactAddress: */
	/*******************/
	public long getFactAddress() {
		return ((Long) getValue()).longValue();
	}

	/****************/
	/* getFactSlot: */
	/****************/
	public PrimitiveValue getFactSlot(String slotName) {
		return Environment.getFactSlot(this, slotName);
	}

	/*****************/
	/* getFactIndex: */
	/*****************/
	public long getFactIndex() {
		return Environment.factIndex(this);
	}

	/***********/
	/* retain: */
	/***********/
	public void retain() {
		this.owner.incrementFactCount(this);
	}

	/************/
	/* release: */
	/************/
	public void release() {
		this.owner.decrementFactCount(this);
	}
	
	@Override
	public String toString() {
		return "<Fact-" + getFactIndex() + ">";
	}
}

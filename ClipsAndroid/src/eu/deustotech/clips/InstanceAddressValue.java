package eu.deustotech.clips;

/**
 * An instance is an object that is an instantiation or specific example of a
 * class. Objects in CLIPS are defined to be floats, integers, symbols, strings,
 * multifield values, external‑addresses, fact‑addresses or instances of a
 * user‑defined class. A user‑defined class is created using the defclass
 * construct. An instance of a user‑defined class is created with the
 * make‑instance function, and such an instance can be referred to uniquely by
 * address.
 */
public class InstanceAddressValue extends InstanceValue {
	private Environment owner;
	
	public InstanceAddressValue(long value, Environment env) {
		super(new Long(value));
		this.owner = env;
	}
	
	/**
	 * @return
	 * 		The environment to which this instance address belongs.
	 */
	public Environment getEnvironment() {
		return this.owner;
	}

	/***********************/
	/* getInstanceAddress: */
	/***********************/
	public long getInstanceAddress() {
		return ((Long) getValue()).longValue();
	}

	/******************/
	/* directGetSlot: */
	/******************/
	public PrimitiveValue directGetSlot(String slotName) {
		return Environment.directGetSlot(this, slotName);
	}

	/********************/
	/* getInstanceName: */
	/********************/
	public String getInstanceName() {
		return Environment.getInstanceName(this);
	}

	@Override
	public String toString() {
		return "<Instance-" + getInstanceName() + ">";
	}

	/***********/
	/* retain: */
	/***********/
	public void retain() {
		// System.out.println("InstanceAddressValue retain");
		this.owner.incrementInstanceCount(this);
	}

	/************/
	/* release: */
	/************/
	public void release() {
		// System.out.println("InstanceAddressValue release");
		this.owner.decrementInstanceCount(this);
	}
}
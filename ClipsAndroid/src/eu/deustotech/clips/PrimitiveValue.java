package eu.deustotech.clips;


public abstract class PrimitiveValue {
	private Object theValue;
	
	protected PrimitiveValue(Object value) {
		// TODO Would it be a good idea to previously check the parameters in Java (in each constructor)?
		// This way, we could avoid a bunch of CLIPS errors...
		this.theValue = value;
	}
	
	/**
	 * @return
	 * 		Java instance of a CLIPS' value.
	 * 		This value is an instance of any of CLIPS' primitive types and
	 * 		is represented in an equivalent Java class.
	 */
	public Object getValue() {
		return this.theValue;
	}
	
	@Override
	public String toString() {
		if (this.theValue== null) return "";
		return theValue.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.theValue == null) ? 0 : this.theValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrimitiveValue other = (PrimitiveValue) obj;
		if (this.theValue == null) {
			if (other.theValue != null)
				return false;
		} else if (!this.theValue.equals(other.theValue))
			return false;
		return true;
	}
}
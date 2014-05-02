package eu.deustotech.clips;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * In CLIPS, a placeholder that has a value (one of the primitive data types) is
 * referred to as a field. The primitive data types are referred to as
 * single‑field values. A constant is a non‑varying single field value directly
 * expressed as a series of characters (which means that external‑addresses,
 * fact‑addresses and instance‑addresses cannot be expressed as constants
 * because they can only be obtained through function calls and variable
 * bindings). A multifield value is a sequence of zero or more single field
 * values. When displayed by CLIPS, multifield values are enclosed in
 * parentheses. Collectively, single and multifield values are referred to as
 * values.
 * </p>
 * 
 * <p>
 * Some examples of multifield values are:
 * </p>
 * <ul>
 * <li>(a)</li>
 * <li>(1 bar foo)</li>
 * <li>()</li>
 * <li>(x 3.0 "red" 567)</li>
 * </ul>
 * 
 * <p>
 * Note that the multifield value (a) is not the same as the single field value
 * a. Multifield values are created either by calling functions which return
 * multifield values, by using wildcard arguments in a deffunction, object
 * message‑handler, or method, or by binding variables during the
 * pattern‑matching process for rules. In CLIPS, a variable is a symbolic
 * location that is used to store values. Variables are used by many of the
 * CLIPS constructs (such as defrule, deffunction, defmethod, and
 * defmessage‑handler) and their usage is explained in the sections describing
 * each of these constructs.
 * </p>
 */
public class MultifieldValue extends PrimitiveValue {
	public MultifieldValue() {
		super(new ArrayList());
	}

	public MultifieldValue(List value) {
		super(value);
	}

	public List multifieldValue() {
		return (List) getValue();
	}

	public PrimitiveValue get(int index) {
		List theList = (List) getValue();
		return (PrimitiveValue) theList.get(index);
	}

	public int size() {
		final List theList = (List) getValue();
		return theList.size();
	}

	public void retain() {
		// System.out.println("MultifieldValue release");
		final List theList = (List) getValue();

		for (Iterator itr = theList.iterator(); itr.hasNext();) {
			PrimitiveValue pv = (PrimitiveValue) itr.next();
		}
	}

	public void release() {
		// System.out.println("MultifieldValue release");
		final List theList = (List) getValue();

		for (Iterator itr = theList.iterator(); itr.hasNext();) {
			PrimitiveValue pv = (PrimitiveValue) itr.next();
		}
	}

	@Override
	public String toString() {
		final List theList = (List) getValue();
		boolean first = true;

		String theString = "(";

		for (Iterator itr = theList.iterator(); itr.hasNext();) {
			if (!first) {
				theString = theString + " " + itr.next();
			} else {
				theString = theString + itr.next();
				first = false;
			}
		}

		theString = theString + ")";

		return theString;
	}
}

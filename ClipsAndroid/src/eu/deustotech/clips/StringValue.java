package eu.deustotech.clips;

/**
 * <p>
 * A string is a set of characters that starts with a double quote (") and is
 * followed by zero or more printable characters. A string ends with double
 * quotes. Double quotes may be embedded within a string by placing a backslash
 * (\) in front of the character. A backslash may be embedded by placing two
 * consecutive back­slash characters in the string.
 * </p>
 * 
 * <p>
 * Some examples are:
 * </p>
 * <ul>
 * <li>"foo"</li>
 * <li>"a and b"</li>
 * <li>"1 number"</li>
 * <li>"a\"quote"</li>
 * </ul>
 * 
 * <p>
 * Note that the string “abcd" is not the same as the symbol abcd. They both
 * contain the same characters, but are of different types. The same holds true
 * for the instance name [abcd].
 * </p>
 */
public class StringValue extends LexemeValue {
	public StringValue() {
		super(new String(""));
	}

	public StringValue(String value) {
		super(value);
	}

	public String stringValue() {
		return (String) getValue();
	}

	@Override
	public String toString() {
		return "\"" + super.toString() + "\"";
	}
}
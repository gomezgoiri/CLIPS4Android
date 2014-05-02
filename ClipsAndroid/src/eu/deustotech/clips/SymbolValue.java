package eu.deustotech.clips;

/**
 * <p>
 * A symbol in CLIPS is any sequence of characters that starts with any
 * printable ASCII character and is followed by zero or more printable ASCII
 * characters. When a delimiter is found, the symbol is ended. The following
 * characters act as delimiters: any non‑printable ASCII character (including
 * spaces, tabs, carriage returns, and line feeds), a double quote, opening and
 * closing parentheses “(” and “)”, an ampersand “&”, a vertical bar “|”, a less
 * than “<”, and a tilde “~”. A semicolon “;” starts a CLIPS comment and also
 * acts as a de­limiter. Delimiters may not be included in symbols with the
 * exception of the “<“ character which may be the first character in a symbol.
 * In addition, a symbol may not begin with either the “?” character or the “$?”
 * sequence of characters (although a symbol may contain these characters).
 * These characters are reserved for variables (which are discussed later in
 * this section). CLIPS is case sensitive (i.e. uppercase letters will match
 * only uppercase let­ters). Note that numbers are a special case of symbols
 * (i.e. they satisfy the definition of a symbol, but they are treated as a
 * different data type).
 * </p>
 * 
 * <p>
 * Some simple examples of symbols are:
 * </p>
 * <ul>
 * <li>foo</li>
 * <li>Hello</li>
 * <li>B76-HI</li>
 * <li>bad_value</li>
 * <li>127A</li>
 * <li>456-93-039</li>
 * <li>@+=-%</li>
 * <li>2each</li>
 */
public class SymbolValue extends LexemeValue {
	/****************/
	/* SymbolValue: */
	/****************/
	public SymbolValue() {
		super(new String(""));
	}

	/****************/
	/* SymbolValue: */
	/****************/
	public SymbolValue(String value) {
		super(value);
	}

	/****************/
	/* symbolValue: */
	/****************/
	public String symbolValue() {
		return (String) getValue();
	}
}
package pe.edu.utp.dsa.StringManipulation;

// the last circle of hell is probably full of string manipulation functions

public class StringBuilderWrapper {

	String preffix = "";
	StringBuilder sb;

	String openedXmlTag;

	public StringBuilderWrapper() {
		sb = new StringBuilder();
	}

	public StringBuilderWrapper setPreffix(String p) {
		preffix = p;
		return this;
	}

	public StringBuilderWrapper line(String s) {
		sb.append(preffix)
				.append(s)
				.append("\n");
		return this;
	}

	public StringBuilderWrapper openXMLTag(String tag) {
		openedXmlTag = tag;
		this.line("<" + openedXmlTag + ">");
		return this;
	}

	public StringBuilderWrapper closeXMLTag() {
		this.line("</" + openedXmlTag + ">");
		return this;
	}

	public StringBuilderWrapper enclosedXmlValue(String valueName, String value) {
		sb.append(preffix)
				.append('<')
				.append(valueName)
				.append('>')
				.append(value)
				.append("</")
				.append(valueName)
				.append(">\n");
		return this;
	}

	public StringBuilderWrapper append(String s) {
		sb.append(s);
		return this;
	}

	public String toString() {
		return sb.toString();
	}
}

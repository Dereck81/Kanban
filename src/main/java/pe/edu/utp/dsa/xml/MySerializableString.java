package pe.edu.utp.dsa.xml;

public class MySerializableString implements XMLSerializable {

	String value;

	public MySerializableString(String x) {
		value = x;
	}

	@Override
	public String serialize() {
		return "<value>" + value + "</value>";
	}
}

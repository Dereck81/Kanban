package pe.edu.utp.dsa.xml;

import pe.edu.utp.dsa.DSA.DynamicArray;
import pe.edu.utp.dsa.DSA.Tuple;

public class XMLIteratorSerializer<K extends XMLSerializable, T extends Iterable<K>> {

	 DynamicArray<Tuple<T, String>> iterables;

	public XMLIteratorSerializer() {
		iterables = new DynamicArray<>();
	}

	public XMLIteratorSerializer<K, T> addIterable(T iter, String name) {
		iterables.pushBack(new Tuple<>(iter, name));
		return this;
	}

	public String export() {

		StringBuilder out = new StringBuilder();

		for (Tuple<T, String> pair : iterables) {

			// ide suggests it's better to do it this way ???
			out.append("<")
					.append(pair.second)
					.append(">\n");

			for (K obj : pair.first) {
				out.append("\t")
						.append(obj.serialize())
						.append("\n");
			}
			out.append("</")
					.append(pair.second)
					.append(">\n");

		}
		return out.toString();
	}
}

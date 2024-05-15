package pe.edu.utp.dsa.xml;

import pe.edu.utp.dsa.DSA.DynamicArray;


public class Test {
	public static void main(String[] args) {
		DynamicArray<MySerializableString> saludos = new DynamicArray<>();
		DynamicArray<MySerializableString> frutas = new DynamicArray<>();
		DynamicArray<MySerializableString> carnes = new DynamicArray<>();

		saludos.pushBack(new MySerializableString("Hola"));
		saludos.pushBack(new MySerializableString("Adios"));

		frutas.pushBack(new MySerializableString("Pollo"));
		frutas.pushBack(new MySerializableString("Res"));
		frutas.pushBack(new MySerializableString("Chancho"));

		carnes.pushBack(new MySerializableString("Aji"));
		carnes.pushBack(new MySerializableString("Uva"));
		carnes.pushBack(new MySerializableString("Manzana"));

		XMLIteratorSerializer<MySerializableString, DynamicArray<MySerializableString>> xmlExportStream = new XMLIteratorSerializer<>();
		xmlExportStream.addIterable(saludos, "Saludos")
				.addIterable(frutas, "Carnes")
				.addIterable(carnes, "Frutas");

		System.out.println(xmlExportStream.export());
	}
}

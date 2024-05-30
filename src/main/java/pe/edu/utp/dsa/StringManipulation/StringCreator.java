package pe.edu.utp.dsa.StringManipulation;

public class StringCreator {
	public static String ntimes(char t, int i) {
		StringBuilder sb = new StringBuilder();
		while (i-- > 0)
			sb.append(t);
		return sb.toString();
	}
}

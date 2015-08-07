package br.ufg.pm.util;

public class PageMiningUtil {
	
	public static String removeInvalidCharacters(String str) {
		if (str == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < str.length(); i++) {
			Character ch = str.charAt(i);

			if (Character.isSurrogate(ch) || Character.isHighSurrogate(ch) || Character.isLowSurrogate(ch)) {

				sb.append(' ');
				
			} else {
				sb.append(ch);
			}
		}

		return sb.toString();
	}

}

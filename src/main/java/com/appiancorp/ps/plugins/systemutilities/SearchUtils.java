package com.appiancorp.ps.plugins.systemutilities;

public class SearchUtils {
	public static boolean wildCardMatch(String text, String pattern)
	{
		// Create the cards by splitting using a RegEx. If more speed 
		// is desired, a simpler character based splitting can be done.
		
		text = text.toLowerCase();
		pattern = pattern.toLowerCase();
		
		String [] cards = pattern.split("\\*");

		// Iterate over the cards.
		for (String card : cards)
		{
			int idx = text.indexOf(card);

			// Card not detected in the text.
			if(idx == -1)
			{
				return false;
			}

			// Move ahead, towards the right of the text.
			text = text.substring(idx + card.length());
		}

		return true;
	}
}

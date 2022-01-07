package com.gismatullin.evalution1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestHelper {
	
    public static final int QUANTITY_OF_RANDOM_WORDS = 3;
    private static Random random = new Random();
    private final static Logger LOGGER = LogManager.getLogger();

    private TestHelper() {}
	
	// #1 Generates random year value in a range from 1970 to current year (inclusive)
	public static int getRandomYear() {
		return getRandomYearImplementation();
	}
	
	private static int getRandomYearImplementation() {
		int fromYear = 1970;
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		return fromYear + random.nextInt(currYear - fromYear + 1);
	}
	
	// #2 Generates random long value
	public static long getRandomLong() {
		return getRandomLongImplementation();
	}
	
	private static long getRandomLongImplementation() {
		return random.nextLong();
	}
	
	// #3 Generates new random string. Random string contains three words separated 
	// by space symbol e.g. "Hcoewa Aghf Zcj"
	public static String getRandomString() {
		return getRandomStringImplementation(QUANTITY_OF_RANDOM_WORDS);
	}
	
	// Generates new string contains needed number of words separated by space symbol
	public static String getRandomString(int words) {
		return getRandomStringImplementation(words);
	}
	
	private static String getRandomStringImplementation(int words) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < words; i++) {
			sb.append(generateRandomWord());
			if (i != words - 1) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}
	
	private static String generateRandomWord() {
		int minWordLength = 2;
		int maxWordLength = 12;
		int randomLength = minWordLength + random.nextInt(maxWordLength - minWordLength + 1);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < randomLength; i++) {
			char c = (char) ('a' + random.nextInt(26)); // get random char
			if (i == 0) {
				sb.append(Character.toUpperCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	// #4 Read data from file and put entries to hashmap
	public static HashMap<String, String> hashMapFromFile(String fileName) {
		return hashMapFromFileImplementation(fileName);
	}
	
	private static HashMap<String, String> hashMapFromFileImplementation(String fileName) {
	    if (fileName == null) {
			String msg = "Path parameter should be not a null";
            LOGGER.error(msg);
			throw new RuntimeException(msg);
	    }
	    Path path = Path.of(fileName).toAbsolutePath();
	    if (!Files.exists(path)) {
            String msg = String.format("File \"%s\" is not found", fileName);
            LOGGER.error(msg);
			throw new RuntimeException(msg);
	    }
	    Map<String, String> resultMap = new HashMap<>();
	    try (Stream<String> stream = Files.lines(path)) {
			stream.forEach(s -> {
				if (s.split("::").length == 2) {
					resultMap.put(s.split("::")[0].trim(), s.split("::")[1].trim());
				} else {
					String msg = String.format("Wrong data format at the line: \"%s\"", s);
					LOGGER.error(msg);
					throw new RuntimeException(msg);
				}
			});
	    } catch (IOException e) {
			String msg = String.format("Error was happened: %s", e.getMessage());
			LOGGER.error(msg);
			throw new RuntimeException(msg);
	    }
	    return (HashMap<String, String>) resultMap;
	}
	
	/* #5 Parses Calendar object from string 
	 * 
	 * @param dateString date in a string, e.g. "13.10.2021"
	 * @return Calendar object had been parsed from string
	 */
	public static Calendar calendarFromString(String dateString) {
	    if (dateString != null) {
            return calendarFromStringImplementation(dateString);
	    }
		String msg = "Parameter should be not a null";
		LOGGER.error(msg);
		throw new RuntimeException(msg);
	}
	
	private static Calendar calendarFromStringImplementation(String dateString) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.M.yyyy");
		Date date;
		try {
		     date = formatter.parse(dateString);
		} catch (ParseException e) {
		     LOGGER.error(e.getMessage());
        	 throw new RuntimeException(e);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	// #6 Parses Double value from string
	// In case of failure of parsing returns Double.NaN (not a number value)
	public static Double doubleFromString(String string) {
		return doubleFromStringImplementation(string);
	}
	
	private static Double doubleFromStringImplementation(String string) {
		try {
			return Double.parseDouble(string);
		} catch (NumberFormatException e) {
            String msg = String.format("Wrong number format: \"%s\"", string);
            LOGGER.info(msg);
			return Double.NaN;
		}
	}
}

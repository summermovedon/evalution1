package com.gismatullin.evalution1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;



public class TestHelperTest {
	
	@RepeatedTest(3)
	public void testGetRandomYear() {
		int randomYear = TestHelper.getRandomYear();
		int fromYear = 1970;
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		assertTrue(fromYear <= randomYear && randomYear <= currYear);
	}

	@RepeatedTest(3)
	public void testGetRandomLong() {
		long l = TestHelper.getRandomLong();
		assertTrue(Long.MIN_VALUE <= l && l <= Long.MAX_VALUE);
	}

	@RepeatedTest(3)
	public void testGetRandomString() {
		int words = TestHelper.QUANTITY_OF_RANDOM_WORDS;
		String regex = getRegex(words);
		String randomString = TestHelper.getRandomString();
		assertTrue(randomString.matches(regex));
	}
	
	@ParameterizedTest
	@ValueSource(ints = {-1, 0, 1, 3})
	public void testGetRandomString(int words) {
		String regex = getRegex(words);
		String randomString = TestHelper.getRandomString(words);
		assertTrue(randomString.matches(regex));
	}
	
	private String getRegex(int words) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < words; i++) {
			sb.append("([A-Z][a-z]+)");
			if (i != words - 1) {
				sb.append("\\s");
			}
		}
		return sb.toString();
	}

	@Test
	public void testHashMapFromFile() {
		String testPath = "src/test/resources/data_for_hashmap.txt";
		int entries = 3; // quantity of test entries in the file
		File file = new File(testPath);
		String fileName = file.getAbsolutePath();
		Map<String, String> testMap = TestHelper.hashMapFromFile(fileName);
		assert(entries == testMap.size());
	}

	@Test
	public void testCalendarFromStringImplementation() {
		String testString = "13.10.2021";
		Calendar actual = TestHelper.calendarFromString(testString);
		Calendar expected = new GregorianCalendar(2021, 9 , 13); // calendar object for date 13.10.2021
		assertTrue(expected.equals(actual));
	}

    @Test
	public void testParseStringToDoubleValue1() {
		String str = "0";
		Double actual = TestHelper.doubleFromString(str);
		assertTrue(actual.equals(0d));
	}
	
	@Test
	public void testParseStringToDoubleValue2() {
		String str = "1234567.123";
		Double actual = TestHelper.doubleFromString(str);
		assertTrue(actual.equals(1234567.123));
	}

	@Test
	public void testParseStringToDoubleValue3() {
		String str = "1.00100e+3";
		Double actual = TestHelper.doubleFromString(str);
		assertTrue(actual.equals(1001.0));
	}

    @Test
	public void testParseStringToDoubleValue4() {
		String str = "-99.9";
		Double actual = TestHelper.doubleFromString(str);
		assertTrue(actual.equals(-99.9));
	}

    @Test
	public void testParseEmptyStringToDoubleValue() {
		String str = "";
		Double actual = TestHelper.doubleFromString(str);
		assertTrue(actual.equals(Double.NaN));
	}
	
	@Test
	public void testParseWordStringToDouble() {
		String str = "abc";
		Double d = TestHelper.doubleFromString(str);
		assertTrue(d.equals(Double.NaN));
	}
}
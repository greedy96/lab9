package lab9;

import org.junit.Test;

public class UrlReaderTest {

	@Test
	public void test() throws Exception {
		String jsonposel = new UrlReader().readUrl("https://api-v3.mojepanstwo.pl/dane/poslowie.json");
		System.out.println(jsonposel);
	}

}

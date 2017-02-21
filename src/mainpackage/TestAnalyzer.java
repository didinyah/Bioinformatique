package mainpackage;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestAnalyzer extends Analyzer {

	@Test
	public void testCheckCds() throws Exception {
		assertTrue("Failure : checkCds", Analyzer.checkCds("CDS"));
		assertFalse("Failure : checkCds", Analyzer.checkCds("CDm"));
		assertFalse("Failure : checkCds", Analyzer.checkCds("no CDS"));
	}

	@Test
	public void testCheckInit() throws Exception {
		assertTrue("Failure : checkCds", Analyzer.checkInit("ORIGIN"));
		assertFalse("Failure : checkCds", Analyzer.checkInit("ORI no origin"));
		assertFalse("Failure : checkCds", Analyzer.checkInit("no origin"));
	}

	@Test
	public void testCheckEnd() throws Exception {
		assertTrue("Failure : checkCds", Analyzer.checkEnd("//"));
		assertFalse("Failure : checkCds", Analyzer.checkEnd("/no end"));
		assertFalse("Failure : checkCds", Analyzer.checkEnd("no end //"));
	}

}

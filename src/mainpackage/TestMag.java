package mainpackage;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMag extends Mag {

	@Test
	public void testCheckCds() throws Exception {
		assertTrue("Failure : checkCds", Mag.checkCds("CDS"));
		assertFalse("Failure : checkCds", Mag.checkCds("CDm"));
		assertFalse("Failure : checkCds", Mag.checkCds("no CDS"));
	}

	@Test
	public void testCheckInit() throws Exception {
		assertTrue("Failure : checkCds", Mag.checkInit("ORIGIN"));
		assertFalse("Failure : checkCds", Mag.checkInit("ORI no origin"));
		assertFalse("Failure : checkCds", Mag.checkInit("no origin"));
	}

	@Test
	public void testCheckEnd() throws Exception {
		assertTrue("Failure : checkCds", Mag.checkEnd("//"));
		assertFalse("Failure : checkCds", Mag.checkEnd("/no end"));
		assertFalse("Failure : checkCds", Mag.checkEnd("no end //"));
	}

}

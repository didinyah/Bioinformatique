package mainpackage;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMag extends Mag {

	@Test
	public void testComplement() throws Exception {
		assertEquals("Failure : Complement", "gcta", Mag.Complement("tagc"));
		assertEquals("Failure : Complement", "a", Mag.Complement("t"));
		assertEquals("Failure : Complement", "ttcgctaaac", Mag.Complement("gtttagcgaa"));
	}
	
	@Test
	public void testCheckIsCds() throws Exception {
		assertTrue("Failure : check is cds", Mag.checkIsCDS("     CDS             complement(388..813)"));
		assertTrue("Failure : check is cds", Mag.checkIsCDS("     CDS             complement(1206..2420)"));
		assertFalse("Failure : check is cds", Mag.checkIsCDS("     CDS             complement(2731..2420)"));
		assertFalse("Failure : check is cds", Mag.checkIsCDS("     CDS             <143859..144731"));
		assertFalse("Failure : check is cds", Mag.checkIsCDS("     CDS             4064..5311"));
		assertFalse("Failure : check is cds", Mag.checkIsCDS("     CDS             LPAGVESVRVVSRSSRPC"));
	}

}
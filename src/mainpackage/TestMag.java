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

}

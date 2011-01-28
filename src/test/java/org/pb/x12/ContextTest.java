package org.pb.x12;

import static org.junit.Assert.*;

import org.junit.Test;

public class ContextTest {

	@Test
	public void testContext() {
		Context ctxt = new Context();
		assertNotNull(ctxt);
	}

	@Test
	public void testContextCharacterCharacterCharacter() {
		Context ctxt = new Context('a', 'b', 'c');
		assertNotNull(ctxt);
	}

	@Test
	public void testGetCompositeElementSeparator() {
		Context ctxt = new Context('a', 'b', 'c');
		assertEquals(new Character('c'), ctxt.getCompositeElementSeparator());
	}

	@Test
	public void testGetElementSeparator() {
		Context ctxt = new Context('a', 'b', 'c');
		assertEquals(new Character('b'), ctxt.getElementSeparator());
	}

	@Test
	public void testGetSegmentSeparator() {
		Context ctxt = new Context('a', 'b', 'c');
		assertEquals(new Character('a'), ctxt.getSegmentSeparator());
	}

	@Test
	public void testSetCompositeElementSeparator() {
		Context ctxt = new Context();
		ctxt.setCompositeElementSeparator('c');
		assertEquals(new Character('c'), ctxt.getCompositeElementSeparator());

	}

	@Test
	public void testSetElementSeparator() {
		Context ctxt = new Context();
		ctxt.setElementSeparator('b');
		assertEquals(new Character('b'), ctxt.getElementSeparator());
	}

	@Test
	public void testSetSegmentSeparator() {
		Context ctxt = new Context();
		ctxt.setSegmentSeparator('b');
		assertEquals(new Character('b'), ctxt.getSegmentSeparator());
	}

	@Test
	public void testToString() {
		Context ctxt = new Context('a', 'b', 'c');
		assertEquals("[a,b,c]", ctxt.toString());
	}

}

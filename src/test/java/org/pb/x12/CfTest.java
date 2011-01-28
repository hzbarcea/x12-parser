package org.pb.x12;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CfTest {

	@Test
	public void testCfString() {
		Cf cf = new Cf("ISA");
		assertNotNull(cf);
	}

	@Test
	public void testCfStringString() {
		Cf cf = new Cf("2300", "CLM");
		assertNotNull(cf);
	}

	@Test
	public void testCfStringStringStringInteger() {
		Cf cf = new Cf("1000A", "NM1", "41", 1);
		assertNotNull(cf);
	}

	@Test
	public void testAddChildCf() {
		Cf cf1 = new Cf("ISA");
		Cf cf2 = new Cf("GS");
		cf1.addChild(cf2);
		assertEquals(cf2, cf1.childList().get(0));
		assertEquals("GS", cf1.childList().get(0).getName());
	}

	@Test
	public void testAddChildStringString() {
		Cf cf1 = new Cf("GS");
		Cf cf2 = new Cf("ST", "ST01");
		cf1.addChild(cf2);
		assertEquals(cf2, cf1.childList().get(0));
		assertEquals("ST", cf1.childList().get(0).getName());
	}

	@Test
	public void testAddChildStringStringStringInteger() {
		Cf cf1 = new Cf("ST");
		Cf cf2 = new Cf("1000A", "NM1", "41", 1);
		cf1.addChild(cf2);
		assertEquals(cf2, cf1.childList().get(0));
		assertEquals("1000A", cf1.childList().get(0).getName());
		assertEquals("NM1", cf1.childList().get(0).getSegment());
		assertEquals("41", cf1.childList().get(0).getSegmentQuals()[0]);
		assertEquals(new Integer(1), cf1.childList().get(0).getSegmentQualPos());
	}

	@Test
	public void testChildList() {
		Cf cf1 = new Cf("ST");
		cf1.addChild(new Cf("1000A", "NM1", "41", 1));
		cf1.addChild(new Cf("1000B", "NM1", "40", 1));
		assertEquals(2, cf1.childList().size());
		assertEquals("1000A", cf1.childList().get(0).getName());
		assertEquals("1000B", cf1.childList().get(1).getName());		
	}

	@Test
	public void testHasChildren() {
		Cf cf1 = new Cf("ST");
		cf1.addChild(new Cf("1000A", "NM1", "41", 1));
		cf1.addChild(new Cf("1000B", "NM1", "40", 1));
		assertEquals(true, cf1.hasChildren());
	}

	@Test
	public void testHasParent() {
		Cf cf1 = new Cf("ST");
		Cf cf2 = cf1.addChild("1000A", "NM1", "41", 1);
		Cf cf3 = cf1.addChild("1000B", "NM1", "40", 1);
		assertEquals(true, cf2.hasParent());
		assertEquals(true, cf3.hasParent());
	}

	@Test
	public void testGetParent() {
		Cf cf1 = new Cf("ST");
		Cf cf2 = cf1.addChild("1000A", "NM1", "41", 1);
		Cf cf3 = cf1.addChild("1000B", "NM1", "40", 1);
		assertEquals(cf1, cf2.getParent());
		assertEquals(cf1, cf3.getParent());
	}

	@Test
	public void testGetName() {
		Cf cf = new Cf("ISA");
		assertEquals("ISA", cf.getName());
	}

	@Test
	public void testGetSegment() {
		Cf cf = new Cf("2300", "CLM");
		assertEquals("CLM", cf.getSegment());
	}

	@Test
	public void testGetSegmentQuals() {
		Cf cf = new Cf("1000A", "NM1", "41", 1);
		assertEquals("41", cf.getSegmentQuals()[0]);
	}

	@Test
	public void testGetSegmentQualPos() {
		Cf cf = new Cf("1000A", "NM1", "41", 1);
		assertEquals(new Integer(1), cf.getSegmentQualPos());		
	}

	@Test
	public void testSetParent() {
		Cf cf1 = new Cf("ST");
		Cf cf2 = new Cf("1000A", "NM1", "41", 1);
		cf2.setParent(cf1);
		assertEquals(cf1, cf2.getParent());
	}

	@Test
	public void testSetChildren() {
		Cf cf1 = new Cf("ST");
		Cf cf2 = cf1.addChild("1000A", "NM1", "41", 1);
		Cf cf3 = cf1.addChild("1000B", "NM1", "40", 1);
		List<Cf> kids = new ArrayList<Cf>();
		kids.add(cf2);		
		kids.add(cf3);
		cf1.setChildren(kids);
		assertEquals(2, cf1.childList().size());
	}

	@Test
	public void testSetName() {
		Cf cf = new Cf("XXX");
		cf.setName("ISA");
		assertEquals("ISA", cf.getName());
	}

	@Test
	public void testSetSegment() {
		Cf cf = new Cf("XXXX", "XXX");
		cf.setSegment("CLM");
		assertEquals("CLM", cf.getSegment());
	}

	@Test
	public void testSetSegmentQuals() {
		Cf cf = new Cf("1000A", "NM1");
		String[] quals = {"41"};
		cf.setSegmentQuals(quals);
		assertEquals("41", cf.getSegmentQuals()[0]);
	}

	@Test
	public void testSetSegmentQualPos() {
		Cf cf = new Cf("1000A", "NM1");
		String[] quals = {"41"};
		cf.setSegmentQuals(quals);
		cf.setSegmentQualPos(1);
		assertEquals(new Integer(1), cf.getSegmentQualPos());		
	}

	@Test
	public void testToString() {
		Cf cf = new Cf("1000A", "NM1", "41", 1);
		assertEquals("+--1000A - NM1 - 41, - 1"
				+ System.getProperty("line.separator"), cf.toString());
	}

}

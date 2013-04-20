package org.pb.x12;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class X12SimpleTest {

	@Test
	public void testX12Simple() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		assertNotNull(x12);
	}

	@Test
	public void testAddSegment() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		Segment s = x12.addSegment();
		assertEquals("", s.toString());
	}

	@Test
	public void testAddSegmentString() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03");
		assertEquals("ISA*ISA01*ISA02*ISA03", x12.getSegment(0).toString());
	}

	@Test
	public void testAddSegmentSegment() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("ISA", "ISA01", "ISA02", "ISA03");
		x12.addSegment(s);
		assertEquals("ISA*ISA01*ISA02*ISA03", x12.getSegment(0).toString());
	}

	@Test
	public void testAddSegmentInt() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03");
		x12.addSegment("ST*ST01*ST02*ST03");
		Segment s = x12.addSegment(1); // test
		s.addElements("GS*GS01*GS02*GS03");
		assertEquals("GS*GS01*GS02*GS03", x12.getSegment(1).toString());
	}

	@Test
	public void testAddSegmentIntString() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03");
		x12.addSegment("ST*ST01*ST02*ST03");
		x12.addSegment(1, "GS*GS01*GS02*GS03"); // test
		assertEquals("GS*GS01*GS02*GS03", x12.getSegment(1).toString());
	}

	@Test
	public void testAddSegmentIntSegment() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03");
		x12.addSegment("ST*ST01*ST02*ST03");
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("GS*GS01*GS02*GS03");
		x12.addSegment(1, s); // test
		assertEquals("GS*GS01*GS02*GS03", x12.getSegment(1).toString());
	}

	@Test
	public void testFindSegment() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03");
		x12.addSegment("GS*GS01*GS02*GS03");
		x12.addSegment("ST*ST01*ST02*ST03");
		x12.addSegment("REF*REF01*REF02*REF03");
		x12.addSegment("REF*REF01*REF02*REF03");
		x12.addSegment("REF*REF01*REF02*REF03");
		x12.addSegment("SE*SE01*SE02*SE03");
		x12.addSegment("GE*GE01*GE02*GE03");
		x12.addSegment("IEA*IEA01*IEA02*IEA03");
		List<Segment> results = x12.findSegment("REF");
		assertEquals("REF*REF01*REF02*REF03", results.get(0).toString());
		assertEquals("REF*REF01*REF02*REF03", results.get(1).toString());
		assertEquals("REF*REF01*REF02*REF03", results.get(2).toString());
	}

	@Test
	public void testGetContext() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		assertEquals("[~,*,:]", x12.getContext().toString());
	}

	@Test
	public void testGetSegment() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03");
		x12.addSegment("GS*GS01*GS02*GS03");
		x12.addSegment("ST*ST01*ST02*ST03");
		assertEquals("GS*GS01*GS02*GS03", x12.getSegment(1).toString());
	}

	@Test
	public void testIterator() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		assertNotNull(x12.iterator());
	}

	@Test
	public void testRemoveSegment() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03");
		x12.addSegment("ST1*ST101*ST102*ST103");
		x12.addSegment("ST2*ST201*ST202*ST203");
		x12.addSegment("ST2*ST301*ST302*ST303");
		
		Segment s1 = x12.removeSegment(2); // test
		assertEquals("ST2*ST201*ST202*ST203", s1.toString());
		assertEquals(3, x12.size());
		assertEquals("ISA*ISA01*ISA02*ISA03~ST1*ST101*ST102*ST103~ST2*ST301*ST302*ST303~", x12.toString());
		
		Segment s2 = x12.removeSegment(0); // test
		assertEquals("ISA*ISA01*ISA02*ISA03", s2.toString());
		assertEquals(2, x12.size());
		assertEquals("ST1*ST101*ST102*ST103~ST2*ST301*ST302*ST303~", x12.toString());
	}
	
	@Test
	public void testSetContext() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.setContext(new Context('s', 'e', 'c'));
		assertEquals("[s,e,c]", x12.getContext().toString());
	}

	@Test
	public void testSetSegmentInt() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03");
		x12.addSegment("ST*ST01*ST02*ST03");
		x12.addSegment("ST*ST01*ST02*ST03");
		Segment s = x12.setSegment(1); // test
		s.addElements("GS*GS01*GS02*GS03");
		assertEquals("GS*GS01*GS02*GS03", x12.getSegment(1).toString());
	}

	@Test
	public void testSetSegmentIntString() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03");
		x12.addSegment("ST*ST01*ST02*ST03");
		x12.addSegment("ST*ST01*ST02*ST03");
		x12.setSegment(1, "GS*GS01*GS02*GS03"); // test
		assertEquals("GS*GS01*GS02*GS03", x12.getSegment(1).toString());
	}

	@Test
	public void testSetSegmentIntSegment() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03");
		x12.addSegment("ST*ST01*ST02*ST03");
		x12.addSegment("ST*ST01*ST02*ST03");
		Segment s = new Segment(new Context('~', '*', ':'));
		s.addElements("GS*GS01*GS02*GS03");
		x12.setSegment(1, s); // test
		assertEquals("GS*GS01*GS02*GS03", x12.getSegment(1).toString());
	}

	@Test
	public void testSize() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03");
		x12.addSegment("GS*GS01*GS02*GS03");
		x12.addSegment("ST*ST01*ST02*ST03");
		x12.addSegment("REF*REF01*REF02*REF03");
		x12.addSegment("REF*REF01*REF02*REF03");
		x12.addSegment("REF*REF01*REF02*REF03");
		x12.addSegment("SE*SE01*SE02*SE03");
		x12.addSegment("GE*GE01*GE02*GE03");
		x12.addSegment("IEA*IEA01*IEA02*IEA03");
		assertEquals(new Integer(9), new Integer(x12.size()));
	}

	@Test
	public void testToString() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03");
		x12.addSegment("GS*GS01*GS02*GS03");
		assertEquals("ISA*ISA01*ISA02*ISA03~GS*GS01*GS02*GS03~", x12.toString());
	}
	
	@Test
	public void testToStringRemoveTrailingEmptyElements() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03***");
		x12.addSegment("GS*GS01*GS02*GS03***");
		assertEquals("ISA*ISA01*ISA02*ISA03~GS*GS01*GS02*GS03~", x12.toString(true));
	}

	@Test
	public void testToStringRemoveTrailingEmptyElementsTwo() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03***ISA06");
		x12.addSegment("GS*GS01*GS02*GS03***");
		assertEquals("ISA*ISA01*ISA02*ISA03***ISA06~GS*GS01*GS02*GS03~", x12.toString(true));
	}

	@Test
	public void testToStringRemoveTrailingEmptyElementsThree() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03***ISA06");
		x12.addSegment("GS*GS01*GS02*GS03**GS05**");
		assertEquals("ISA*ISA01*ISA02*ISA03***ISA06~GS*GS01*GS02*GS03**GS05~", x12.toString(true));
	}
	
	@Test
	public void testToXML() {
		X12Simple x12 = new X12Simple(new Context('~', '*', ':'));
		x12.addSegment("ISA*ISA01*ISA02*ISA03");
		x12.addSegment("GS*GS01*GS02*GS03");
		assertEquals(
				"<X12><ISA><ISA01><![CDATA[ISA01]]></ISA01><ISA02><![CDATA[ISA02]]></ISA02><ISA03><![CDATA[ISA03]]></ISA03></ISA><GS><GS01><![CDATA[GS01]]></GS01><GS02><![CDATA[GS02]]></GS02><GS03><![CDATA[GS03]]></GS03></GS></X12>",
				x12.toXML());
	}

}

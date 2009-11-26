/**
 * 
 */
package org.pb.x12.examples;

import org.pb.x12.Context;
import org.pb.x12.Segment;
import org.pb.x12.X12;

/**
 * Example showing how to create a X12 transaction from scratch.
 * 
 * @author Prasad Balan
 */
public class CreateX12Transaction2Example {

	/**
	 * Example of creating a X12 transaction
	 * <pre>
	 * <code>//Create a X12 context. Set the segment, element and sub-element separator</code>
	 * <code>Context c = new Context('~','*',':');</code>
	 * <code></code>
	 * <code>//Create a X12 transaction passing the context</code>
	 * <code>X12 x = new X12(c);</code>
	 * <code></code>
	 * <code>//Add a segments to the X12 transaction</code>
	 * <code>Segment s = x.addSegment("ISA*00*          *00*      .... T*:");</code>
	 * <code>s = x.addSegment("GS*1212*SENDERID* ... X*00401");</code>
	 * <code>s = x.addSegment("ST*835*000000000");</code>
	 * <code></code>
	 * <code>//Modify one of the elements in ST segment just added</code>
	 * <code>s.setElement(2,"000000001");</code>
	 * <code></code>
	 * <code>//Add an empty segment and then add elements</code>
	 * <code>s = x.addSegment();</code>
	 * <code>s.addElement("BPR");</code>
	 * <code>s.addElement("DATA");</code>
	 * <code>s.addElement("NOT");</code>
	 * <code>...</code>
	 * <code>...</code>
	 * <code>//Convert X12 object to string representation</code>
	 * <code>String x12 = x.toString();</code>
	 * <code></code>
	 * <code></code>
	 * <code>//Convert X12 object to XML string representation if that is what you need</code>
	 * <code>String xml = x.toXML();</code> 
	 * </pre>
	 */
	public static void main(String[] args) {

		Context c = new Context('~','*',':');
		X12 x = new X12(c);
		
		//Add ISA segment directly as a string
		x.addSegment("ISA*00*          *00*          *ZZ*SENDERID       *" +
				"ZZ*RECEIVERID    *030409*0701*U*00401*0000000001*0*T*:");
		
		//Add GS segment directly as a string
		x.addSegment("GS*1212*SENDERID*RECEIVERID*0701*000000001*X*00401");
		
		//Add ST segment directly as a string, but replace of of the elements
		Segment s = x.addSegment("ST*835*000000000");
		//Modify one of the elements
		s.setElement(2,"000000001");

		//Add BPR segment, add elements one at a time
		s = x.addSegment();
		s.addElement("BPR");
		s.addElement("DATA");
		s.addElement("NOT");
		s.addElement("VALID");
		s.addElement("RANDOM");
		s.addElement("TEXT");

		x.addSegment("TRN*1*0000000000*1999999999");
		x.addSegment("DTM*111*20090915");
		x.addSegment("N1*PR*ALWAYS INSURANCE COMPANY");
		x.addSegment("N7*AROUND THE CORNER");
		x.addSegment("N4*SHINE CITY*GREEN STATE*ZIP");
		x.addSegment("REF*DT*435864864");
		x.addSegment("N1*PE*FI*888888888*P.O.BOX 456*SHINE CITY*GREEN STATE*ZIP*EARTH");
		x.addSegment("LX*1");
		x.addSegment("CLP*PCN123456789**5555.55**CCN987654321");
		x.addSegment("CAS*PR*909099*100.00");
		x.addSegment("NM1*QC*1*PATIENT*TREATED*ONE***34*333333333");
		x.addSegment("DTM*273*20020824");
		x.addSegment("AMT*A1*10.10");
		x.addSegment("AMT*A2*20.20");
		x.addSegment("LX*2");
		x.addSegment("CLP*PCN123456789**4444.44**CCN987654321");
		x.addSegment("CAS*PR*909099*200.00");
		x.addSegment("NM1*QC*1*PATIENT*TREATED*TWO***34*444444444");
		x.addSegment("DTM*273*20020824");
		x.addSegment("AMT*A1*30.30");
		x.addSegment("AMT*A2*40.40");
		x.addSegment("SE*24*000000001");
		x.addSegment("GE*1*000000001");
		x.addSegment("IEA*1*000000001");		
	
		String x12 = x.toString();
		System.out.print(x12);
		
	}
}

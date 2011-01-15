/*
   Copyright [2011] [Prasad Balan]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package org.pb.x12.example;

import org.pb.x12.Context;
import org.pb.x12.Segment;
import org.pb.x12.X12Simple;

/**
 * Example showing how to create a X12 transaction from scratch.
 * 
 * @author Prasad Balan
 * 
 * <pre>
 * Example of creating a X12 transaction
 * 
 * //Create a X12 context. Set the segment, element and sub-element separator
 * Context c = new Context('&tilde;','*',':');
 * 
 * //Create a X12 transaction passing the context
 * X12Simple x = new X12Simple(c);
 * 
 * //Add a segments to the X12 transaction
 * Segment s = x.addSegment(&quot;ISA*00*          *00*      .... T*:&quot;);
 * s = x.addSegment(&quot;GS*1212*SENDERID* ... X*00401&quot;);
 * s = x.addSegment(&quot;ST*835*000000000&quot;);
 * 
 * //Modify one of the elements in ST segment just added
 * s.setElement(2,&quot;000000001&quot;);
 * 
 * //Add an empty segment and then add elements
 * s = x.addSegment();
 * s.addElement('BPR');
 * s.addElement('DATA');
 * s.addElement('NOT');
 * ...
 * ...
 * //Convert X12 object to string representation
 * String x12 = x.toString();
 * 
 * 
 * //Convert X12 object to XML string representation if that is what you need
 * String xml = x.toXML();
 * </pre>
 */
public class exampleCreateX12SimpleTwo {

	public static void main(String[] args) {

		Context c = new Context('~', '*', ':');
		X12Simple x = new X12Simple(c);

		// Add ISA segment directly as a string
		x.addSegment("ISA*00*          *00*          *ZZ*SENDERID       *"
				+ "ZZ*RECEIVERID    *030409*0701*U*00401*0000000001*0*T*:");

		// Add GS segment directly as a string
		x.addSegment("GS*1212*SENDERID*RECEIVERID*0701*000000001*X*00401");

		// Add ST segment directly as a string, but replace one of the elements
		Segment s = x.addSegment("ST*835*000000000");
		// Modify one of the elements
		s.setElement(2, "000000001");

		// Add BPR segment, add elements one at a time
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
		x
				.addSegment("N1*PE*FI*888888888*P.O.BOX 456*SHINE CITY*GREEN STATE*ZIP*EARTH");
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

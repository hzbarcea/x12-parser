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

import org.pb.x12.Segment;
import org.pb.x12.X12Simple;
import org.pb.x12.Context;

/**
 * Example showing how to create a X12 transaction from scratch.
 * 
 * @author Prasad Balan
 * 
 * <pre>
 * Example of creating a X12 transaction
 * 
 * //Create a X12 context. Set the segment, element and sub-element separator
 * Context c = new Context('~','*',':');
 * 
 * //Create a X12 passing the context
 * X12Simple x = new X12Simple(c);
 * 
 * //Add a segment to the X12
 * Segment s = x.addSegment();
 * 
 * //Add elements to the segment
 * s.addElement("ISA");
 * s.addElement("00");
 * ...
 * //Continue to add segments and then add elements
 * s = x.addSegment();
 * s.addElement("GS");
 * s.addElement("121");
 * ...
 * 
 * 
 * //Convert X12Simple object to X12 string representation
 * String x12 = x.toString();
 * 
 * //Convert X12 object to XML string representation if that is what you need
 * String xml = x.toXML();
 * </pre>
 */
public class exampleCreateX12SimpleOne {

	public static void main(String[] args) {

		Context c = new Context('~','*',':');
		X12Simple x12 = new X12Simple(c);
		
		//add ISA segment
		Segment s = x12.addSegment();
		s.addElement("ISA");
		s.addElement("00");
		s.addElement("          ");
		s.addElement("00");
		s.addElement("          ");
		s.addElement("ZZ");
		s.addElement("SENDERID       ");
		s.addElement("ZZ");
		s.addElement("RECEIVERID    ");
		s.addElement("030409");
		s.addElement("0701");
		s.addElement("U");
		s.addElement("00401");
		s.addElement("0000000001");
		s.addElement("0");
		s.addElement("T");
		s.addElement(":");

		s = x12.addSegment();
		s.addElement("GS");
		s.addElement("1212");
		s.addElement("SENDERID");
		s.addElement("RECEIVERID");
		s.addElement("0701");
		s.addElement("000000001");
		s.addElement("X");
		s.addElement("00401");

		s = x12.addSegment();
		s.addElement("ST");
		s.addElement("835");
		s.addElement("000000001");

		s = x12.addSegment();
		s.addElement("BPR");
		s.addElement("DATA");
		s.addElement("NOT");
		s.addElement("VALID");
		s.addElement("RANDOM");
		s.addElement("TEXT");

		s = x12.addSegment();
		s.addElement("TRN");
		s.addElement("1");
		s.addElement("0000000000");
		s.addElement("1999999999");

		s = x12.addSegment();
		s.addElement("DTM");
		s.addElement("111");
		s.addElement("20090915");

		s = x12.addSegment();
		s.addElement("N1");
		s.addElement("PR");
		s.addElement("ALWAYS INSURANCE COMPANY");

		s = x12.addSegment();
		s.addElement("N7");
		s.addElement("AROUND THE CORNER");

		s = x12.addSegment();
		s.addElement("N4");
		s.addElement("SHINE CITY");
		s.addElement("GREEN STATE");
		s.addElement("ZIP");

		s = x12.addSegment();
		s.addElement("REF");
		s.addElement("DT");
		s.addElement("435864864");

		s = x12.addSegment();
		s.addElement("N1");
		s.addElement("PE");
		s.addElement("FI");
		s.addElement("888888888");
		s.addElement("P.O.BOX 456");
		s.addElement("SHINE CITY");
		s.addElement("GREEN STATE");
		s.addElement("ZIP");
		s.addElement("EARTH");

		s = x12.addSegment();
		s.addElement("LX");
		s.addElement("1");

		s = x12.addSegment();
		s.addElement("CLP");
		s.addElement("PCN123456789");
		s.addElement("");
		s.addElement("5555.55");
		s.addElement("");
		s.addElement("CCN987654321");

		s = x12.addSegment();
		s.addElement("CAS");
		s.addElement("PR");
		s.addElement("909099");
		s.addElement("100.00");

		s = x12.addSegment();
		s.addElement("NM1");
		s.addElement("QC");
		s.addElement("1");
		s.addElement("PATIENT");
		s.addElement("TREATED");
		s.addElement("ONE");
		s.addElement("");
		s.addElement("");
		s.addElement("34");
		s.addElement("333333333");

		s = x12.addSegment();
		s.addElement("DTM");
		s.addElement("273");
		s.addElement("20020824");

		s = x12.addSegment();
		s.addElement("AMT");
		s.addElement("A1");
		s.addElement("10.10");

		s = x12.addSegment();
		s.addElement("AMT");
		s.addElement("A2");
		s.addElement("20.20");

		s = x12.addSegment();
		s.addElement("LX");
		s.addElement("2");

		s = x12.addSegment();
		s.addElement("CLP");
		s.addElement("PCN123456789");
		s.addElement("");
		s.addElement("4444.44");
		s.addElement("");
		s.addElement("CCN987654321");

		s = x12.addSegment();
		s.addElement("CAS");
		s.addElement("PR");
		s.addElement("909099");
		s.addElement("200.00");

		s = x12.addSegment();
		s.addElement("NM1");
		s.addElement("QC");
		s.addElement("1");
		s.addElement("PATIENT");
		s.addElement("TREATED");
		s.addElement("TWO");
		s.addElement("");
		s.addElement("");
		s.addElement("34");
		s.addElement("444444444");

		s = x12.addSegment();
		s.addElement("DTM");
		s.addElement("273");
		s.addElement("20020824");

		s = x12.addSegment();
		s.addElement("AMT");
		s.addElement("A1");
		s.addElement("30.30");

		s = x12.addSegment();
		s.addElement("AMT");
		s.addElement("A2");
		s.addElement("40.40");

		s = x12.addSegment();
		s.addElement("SE");
		s.addElement("24");
		s.addElement("000000001");

		s = x12.addSegment();
		s.addElement("GE");
		s.addElement("1");
		s.addElement("000000001");

		s = x12.addSegment();
		s.addElement("IEA");
		s.addElement("1");
		s.addElement("000000001");
		
		String x12String = x12.toString();
		System.out.print(x12String);
	}
}

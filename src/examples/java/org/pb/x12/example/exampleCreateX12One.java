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

import java.util.List;

import org.pb.x12.Context;
import org.pb.x12.Loop;
import org.pb.x12.Segment;
import org.pb.x12.X12;

/**
 * Example showing how to create a X12 transaction from scratch.
 * 
 * @author Prasad Balan
 * 
 * <pre>
 * Context c = new Context('&tilde;', '*', ':');
 * X12 x12 = new X12(c);
 * Loop loop_isa = x12.addChild(&quot;ISA&quot;);
 * 
 * // Add ISA segment to the loop
 * loop_isa.addSegment(&quot;ISA*00*          *00*          *ZZ*SENDERID       *&quot;
 * 		+ &quot;ZZ*RECEIVERID    *030409*0701*U*00401*0000000001*0*T*:&quot;);
 * 
 * // Add GS child loop to ISA loop
 * Loop loop_gs = loop_isa.addChild(&quot;GS&quot;);
 * // Add GS segment directly as a string
 * loop_gs.addSegment(&quot;GS*1212*SENDERID*RECEIVERID*0701*000000001*X*00401&quot;);
 * 
 * Loop loop_st = loop_gs.addChild(&quot;ST&quot;);
 * loop_st.addSegment(&quot;ST*835*000000001&quot;);
 * loop_st.addSegment(&quot;BPR*DATA*NOT*VALID*RANDOM*TEXT&quot;);
 * loop_st.addSegment(&quot;TRN*1*0000000000*1999999999&quot;);
 * loop_st.addSegment(&quot;DTM*111*20090915&quot;);
 * 
 * Loop loop_1000A = loop_st.addChild(&quot;1000A&quot;);
 * loop_1000A.addSegment(&quot;N1*PR*ALWAYS INSURANCE COMPANY&quot;);
 * loop_1000A.addSegment(&quot;N7*AROUND THE CORNER&quot;);
 * loop_1000A.addSegment(&quot;N4*SHINE CITY*GREEN STATE*ZIP&quot;);
 * loop_1000A.addSegment(&quot;REF*DT*435864864&quot;);
 * 
 * Loop loop_1000B = loop_st.addChild(&quot;1000B&quot;);
 * loop_1000B
 * 		.addSegment(&quot;N1*PE*FI*888888888*P.O.BOX 456*SHINE CITY*GREEN STATE*ZIP*EARTH&quot;);
 * 
 * Loop loop_2000 = loop_st.addChild(&quot;2000&quot;);
 * loop_2000.addSegment(&quot;LX*1&quot;);
 * 
 * Loop loop_2010_1 = loop_2000.addChild(&quot;2010&quot;);
 * loop_2010_1.addSegment(&quot;CLP*PCN123456789**5555.55**CCN987654321&quot;);
 * loop_2010_1.addSegment(&quot;CAS*PR*909099*100.00&quot;);
 * loop_2010_1.addSegment(&quot;NM1*QC*1*PATIENT*TREATED*ONE***34*333333333&quot;);
 * loop_2010_1.addSegment(&quot;DTM*273*20020824&quot;);
 * loop_2010_1.addSegment(&quot;AMT*A1*10.10&quot;);
 * loop_2010_1.addSegment(&quot;AMT*A2*20.20&quot;);
 * 
 * Loop loop_2010_2 = loop_2000.addChild(&quot;2010&quot;);
 * loop_2010_2.addSegment(&quot;LX*2&quot;);
 * loop_2010_2.addSegment(&quot;CLP*PCN123456789**4444.44**CCN987654321&quot;);
 * loop_2010_2.addSegment(&quot;CAS*PR*909099*200.00&quot;);
 * loop_2010_2.addSegment(&quot;NM1*QC*1*PATIENT*TREATED*TWO***34*444444444&quot;);
 * loop_2010_2.addSegment(&quot;DTM*273*20020824&quot;);
 * loop_2010_2.addSegment(&quot;AMT*A1*30.30&quot;);
 * loop_2010_2.addSegment(&quot;AMT*A2*40.40&quot;);
 * 
 * Loop loop_se = loop_gs.addChild(&quot;SE&quot;);
 * loop_se.addSegment(&quot;SE*XX*000000001&quot;);
 * 
 * Loop loop_ge = loop_isa.addChild(&quot;GE&quot;);
 * loop_ge.addSegment(&quot;GE*1*000000001&quot;);
 * 
 * Loop loop_iea = x12.addChild(&quot;IEA&quot;);
 * loop_iea.addSegment(&quot;IEA*1*000000001&quot;);
 * 
 * // Since the SE loop has the incorrect segment count let us fix that.
 * Integer count = loop_st.size();
 * count += 1; // In the loop hierarchy SE is not a child loop of ST. So
 * // when we get the rows in ST loop it does not have the count of SE.
 * // so add 1.
 * 
 * // We can set the count directly, like
 * // loop_se.getSegment(0).setElement(1, count.toString());
 * // this is just to show how to use the findLoop()
 * List&lt;Loop&gt; trailer = x12.findLoop(&quot;SE&quot;);
 * trailer.get(0).getSegment(0).setElement(1, count.toString());
 * 
 * //another way
 * List&lt;Segment&gt; se = x12.findSegment(&quot;SE&quot;);
 * se.get(0).setElement(1, count.toString());
 * 
 * //another way
 * loop_se.getSegment(0).setElement(1, count.toString());
 * 
 * System.out.println(loop_st.size());
 * System.out.println(x12.toString());
 * System.out.println(x12.toXML());
 * 
 * </pre>
 */
public class exampleCreateX12One {

	public static void main(String[] args) {

		Context c = new Context('~', '*', ':');

		X12 x12 = new X12(c);
		Loop loop_isa = x12.addChild("ISA");

		// Add ISA segment to the loop
		loop_isa
				.addSegment("ISA*00*          *00*          *ZZ*SENDERID       *"
						+ "ZZ*RECEIVERID    *030409*0701*U*00401*0000000001*0*T*:");

		// Add GS child loop to ISA loop
		Loop loop_gs = loop_isa.addChild("GS");
		// Add GS segment directly as a string
		loop_gs
				.addSegment("GS*1212*SENDERID*RECEIVERID*0701*000000001*X*00401");

		Loop loop_st = loop_gs.addChild("ST");
		loop_st.addSegment("ST*835*000000001");
		loop_st.addSegment("BPR*DATA*NOT*VALID*RANDOM*TEXT");
		loop_st.addSegment("TRN*1*0000000000*1999999999");
		loop_st.addSegment("DTM*111*20090915");

		Loop loop_1000A = loop_st.addChild("1000A");
		loop_1000A.addSegment("N1*PR*ALWAYS INSURANCE COMPANY");
		loop_1000A.addSegment("N7*AROUND THE CORNER");
		loop_1000A.addSegment("N4*SHINE CITY*GREEN STATE*ZIP");
		loop_1000A.addSegment("REF*DT*435864864");

		Loop loop_1000B = loop_st.addChild("1000B");
		loop_1000B
				.addSegment("N1*PE*FI*888888888*P.O.BOX 456*SHINE CITY*GREEN STATE*ZIP*EARTH");

		Loop loop_2000 = loop_st.addChild("2000");
		loop_2000.addSegment("LX*1");

		Loop loop_2010_1 = loop_2000.addChild("2010");
		loop_2010_1.addSegment("CLP*PCN123456789**5555.55**CCN987654321");
		loop_2010_1.addSegment("CAS*PR*909099*100.00");
		loop_2010_1.addSegment("NM1*QC*1*PATIENT*TREATED*ONE***34*333333333");
		loop_2010_1.addSegment("DTM*273*20020824");
		loop_2010_1.addSegment("AMT*A1*10.10");
		loop_2010_1.addSegment("AMT*A2*20.20");

		Loop loop_2010_2 = loop_2000.addChild("2010");
		loop_2010_2.addSegment("LX*2");
		loop_2010_2.addSegment("CLP*PCN123456789**4444.44**CCN987654321");
		loop_2010_2.addSegment("CAS*PR*909099*200.00");
		loop_2010_2.addSegment("NM1*QC*1*PATIENT*TREATED*TWO***34*444444444");
		loop_2010_2.addSegment("DTM*273*20020824");
		loop_2010_2.addSegment("AMT*A1*30.30");
		loop_2010_2.addSegment("AMT*A2*40.40");

		Loop loop_se = loop_gs.addChild("SE");
		loop_se.addSegment("SE*XX*000000001");

		Loop loop_ge = loop_isa.addChild("GE");
		loop_ge.addSegment("GE*1*000000001");

		Loop loop_iea = x12.addChild("IEA");
		loop_iea.addSegment("IEA*1*000000001");

		// Since the SE loop has the incorrect segment count let us fix that.
		Integer count = loop_st.size();
		count += 1; // In the loop hierarchy SE is not a child loop of ST. So
		// when we get the rows in ST loop it does not have the count of SE.
		// so add 1.

		// We can set the count directly, like
		// loop_se.getSegment(0).setElement(1, count.toString());
		// this is just to show how to use the findLoop()
		List<Loop> trailer = x12.findLoop("SE");
		trailer.get(0).getSegment(0).setElement(1, count.toString());
		
		//another way
		List<Segment> se = x12.findSegment("SE");
		se.get(0).setElement(1, count.toString());

		//another way
		loop_se.getSegment(0).setElement(1, count.toString());
		
		System.out.println(loop_st.size());
		System.out.println(x12.toString());
		System.out.println(x12.toXML());
	}
}

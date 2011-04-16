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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.pb.x12.Cf;
import org.pb.x12.Loop;
import org.pb.x12.Parser;
import org.pb.x12.Segment;
import org.pb.x12.X12;
import org.pb.x12.X12Parser;

/**
 * Example showing X12 Parser reading a X12 file and looping over the segments.
 * 
 * @author Prasad Balan
 * 
 * <pre>
 * Example of parsing a X12 InputStream.
 * 
 * This is the modified loop hierarchy of a 835 transaction used in this example.
 * The original/actual hierarchy is in example exampleParseX12FileTwo.
 * This just illustrates different ways you can setup the hierarchy to achieve
 * the desired results.
 * Note: Such a hierarchy change will work only when there is not more than
 * one Loop with the same identifiers. For e.g. in an 837 transaction both
 * Loop 2010BA (Subscriber) and 2330A (Other Subscriber) have the same identifiers,
 * which are segment id NM1 and IL at position NM102 (or index 1). This might
 * cause the parser to identify both elements as the same loop. In such cases
 * it is advisable to maintain the hierarchy. 
 *  
 *  +--X12
 *  |  +--ISA - ISA
 *  |  +--GS - GS
 *  |  +--ST - ST - 835, - 1
 *  |  +--1000A - N1 - PR, - 1
 *  |  +--1000B - N1 - PE, - 1
 *  |  +--2000 - LX
 *  |  +--2100 - CLP
 *  |  +--2110 - SVC
 *  |  +--SE - SE  
 *  |  +--GE - GE  
 *  |  +--IEA - IEA
 *     
 * Cf cf835 = loadCf();
 * Parser parser = new X12Parser(cf835);
 * // The configuration Cf can be loaded using DI framework.
 * // Check the sample spring application context file provided.
 *    
 * Double totalChargeAmount = 0.0;
 * X12 x12 = (X12) parser.parse(new File(&quot;C:\\test\\835.txt&quot;));
 * List&lt;Segment&gt; segments = x12.findSegment(&quot;CLP&quot;);
 * for (Segment s : segments) {
 *     totalChargeAmount = totalChargeAmount + Double.parseDouble(s.getElement(3));
 * }
 * System.out.println(&quot;Total Change Amount &quot; + s.getElement(3));
 * </pre>
 * 
 * <pre>
 * // Simple configuration, single level hierarchy
 * // Alternately can be loaded using Spring/DI 
 * private static Cf loadCf() {
 * 		Cf cfX12 = new Cf(&quot;X12&quot;);
 * 		cfX12.addChild(&quot;ISA&quot;, &quot;ISA&quot;);
 * 		cfX12.addChild(&quot;GS&quot;, &quot;GS&quot;);
 * 		cfX12.addChild(&quot;ST&quot;, &quot;ST&quot;, &quot;835&quot;, 1);
 * 		cfX12.addChild(&quot;1000A&quot;, &quot;N1&quot;, &quot;PR&quot;, 1);
 * 		cfX12.addChild(&quot;1000B&quot;, &quot;N1&quot;, &quot;PE&quot;, 1);
 * 		cfX12.addChild(&quot;2000&quot;, &quot;LX&quot;);
 * 		cfX12.addChild(&quot;2100&quot;, &quot;CLP&quot;);
 * 		cfX12.addChild(&quot;2110&quot;, &quot;SVC&quot;);
 * 		cfX12.addChild(&quot;GE&quot;, &quot;GE&quot;);
 * 		cfX12.addChild(&quot;IEA&quot;, &quot;IEA&quot;);
 * 		
 * 		//System.out.println(cfX12);
 * 		return cfX12;
 * }
 * 
 * </pre>
 */


public class exampleParseX12FileThree {

	public static void main(String[] args) throws FileNotFoundException {
		X12 x12 = null;
		Cf cf835 = loadCf(); // candidate for dependency injection
		Parser parser = new X12Parser(cf835);
		Double totalChargeAmount = 0.0;
		
		InputStream is = exampleParseX12FileOne.class.getClass().getResourceAsStream("/org/pb/x12/example/example835One.txt");

		try {
			x12 = (X12) parser.parse(is);
			
			// Calculate the total charge amount
			List<Loop> loops = x12.findLoop("2100");
			for (Loop loop : loops) {
				for (Segment s : loop) {
					if (s.getElement(0).equals("CLP")) {
						totalChargeAmount = totalChargeAmount
								+ Double.parseDouble(s.getElement(3));
					}
				}
			}
			System.out.println("Total Charged Amount = "
					+ totalChargeAmount.toString());

			// Calculate the total charge amount - alternate method
			totalChargeAmount = 0.0;
			List<Segment> segments = x12.findSegment("CLP");
			for (Segment s : segments) {
				totalChargeAmount = totalChargeAmount
						+ Double.parseDouble(s.getElement(3));
			}
			System.out.println("Total Charged Amount = "
					+ totalChargeAmount.toString());

			System.out.println(x12.toString());
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// Alternately can be loaded using Spring/DI 
	private static Cf loadCf() {
		Cf cfX12 = new Cf("X12");
		cfX12.addChild("ISA", "ISA");
		cfX12.addChild("GS", "GS");
		cfX12.addChild("ST", "ST", "835", 1);
		cfX12.addChild("1000A", "N1", "PR", 1);
		cfX12.addChild("1000B", "N1", "PE", 1);
		cfX12.addChild("2000", "LX");
		cfX12.addChild("2100", "CLP");
		cfX12.addChild("2110", "SVC");
		cfX12.addChild("GE", "GE");
		cfX12.addChild("IEA", "IEA");
		//System.out.println(cfX12);
		return cfX12;
	}
}

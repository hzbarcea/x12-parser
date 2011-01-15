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

import java.io.File;
import java.net.URL;
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
 * Example of parsing a X12 file
 * 
 * This is the loop hierarchy of a 835 transaction used here.
 *  
 * +--X12
 * |  +--ISA - ISA
 * |  |  +--GS - GS
 * |  |  |  +--ST - ST - 835, - 1
 * |  |  |  |  +--1000A - N1 - PR, - 1
 * |  |  |  |  +--1000B - N1 - PE, - 1
 * |  |  |  |  +--2000 - LX
 * |  |  |  |  |  +--2100 - CLP
 * |  |  |  |  |  |  +--2110 - SVC
 * |  |  |  +--SE - SE
 * |  |  +--GE - GE
 * |  +--IEA - IEA
 * 
 * Cf cf835 = loadCf();
 * Parser parser = new X12Parser(cf835);
 * // The configuration Cf can be loaded using DI framework.
 * // Check the sample spring application context file provided.
 *    
 * Double totalChargeAmount = 0.0;
 * X12 x12 = (X12) parser.parse(new File("C:\\test\\835.txt"));
 * List<Segment> segments = x12.findSegment("CLP");
 * for (Segment s : segments) {
 *     totalChargeAmount = totalChargeAmount + Double.parseDouble(s.getElement(3));
 * }
 * System.out.println("Total Change Amount " + s.getElement(3)); 
 * 
 * </pre>
 */
public class exampleParseX12FileOne {

	public static void main(String[] args) {
		X12 x12 = null;
		Cf cf835 = loadCf(); // candidate for dependency injection
		Parser parser = new X12Parser(cf835);
		Double totalChargeAmount = 0.0;
		
		URL url = exampleParseX12FileOne.class.getClass().getResource("/org/pb/x12/example/example835One.txt");
		File f1 = new File(url.getFile());

		try {
			x12 = (X12) parser.parse(f1);
			
			// calculate the total charge amount
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

			// calculate the total charge amount - alternate method
			totalChargeAmount = 0.0;
			List<Segment> segments = x12.findSegment("CLP");
			for (Segment s : segments) {
				totalChargeAmount = totalChargeAmount
						+ Double.parseDouble(s.getElement(3));
			}
			System.out.println("Total Charged Amount = "
					+ totalChargeAmount.toString());

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// Alternately can be loaded using Spring/DI 
	private static Cf loadCf() {
		Cf cfX12 = new Cf("X12");
		Cf cfISA = cfX12.addChild("ISA", "ISA");
		Cf cfGS = cfISA.addChild("GS", "GS");
		Cf cfST = cfGS.addChild("ST", "ST", "835", 1);
		cfST.addChild("1000A", "N1", "PR", 1);
		cfST.addChild("1000B", "N1", "PE", 1);
		Cf cf2000 = cfST.addChild("2000", "LX");
		Cf cf2100 = cf2000.addChild("2100", "CLP");
		cf2100.addChild("2110", "SVC");
		cfISA.addChild("GE", "GE");
		cfX12.addChild("IEA", "IEA");
		//System.out.println(cfX12);
		return cfX12;
	}
}

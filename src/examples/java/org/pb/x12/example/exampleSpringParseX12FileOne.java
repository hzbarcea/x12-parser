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
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * Example showing X12 Parser reading a X12 file and looping over the segments.
 * 
 * @author Prasad Balan
 *
 * <pre>
 * Example of using Spring to load the configuration
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
 * // Check this example to see how to load configuration using Spring.
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


public class exampleSpringParseX12FileOne {

	public static void main(String[] args) {
		X12 x12 = null;

		Resource xmlResource = new FileSystemResource("./target/classes/cf/appContext_835_004010X091.xml");
		BeanFactory factory = new XmlBeanFactory(xmlResource);
		Cf cf = (Cf) factory.getBean("bean_X12");
		
		Double totalChargeAmount = 0.0;
		
		URL url = exampleSpringParseX12FileOne.class.getClass().getResource("/org/pb/x12/example/example835One.txt");
		File f1 = new File(url.getFile());

		Parser parser = new X12Parser(cf);
		
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
}

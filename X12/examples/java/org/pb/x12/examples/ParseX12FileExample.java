/*
   Copyright [2009] [Prasad Balan]

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
package org.pb.x12.examples;

import java.io.File;
import org.pb.x12.Parser;
import org.pb.x12.Segment;
import org.pb.x12.X12;


/**
 * Example showing X12 Parser reading a X12 file and looping
 * over the segments.
 * 
 * @author Prasad Balan
 */
public class ParseX12FileExample {

	/**
	 * Example of parsing a X12 file
	 * <pre>
	 * <code>X12 x12 = new Parser().parse(new File("C:\\test\\835.txt"));</code>
	 * <code>for (Segment s : x12) {</code>
	 * <code>    if (s.getElement(0).equals("CLP")) {</code>
	 * <code>        System.out.println("Total Change Amount " + s.getElement(3));</code>
	 * <code>    }</code>
	 * <code>}</code>
	 * </pre>
	 */
	public static void main(String[] args) {
		X12 x1 = null;
		Parser p1 = new Parser();
		Double totalChargeAmount = 0.0;
		File f1 = new File("C:\\_____PRASAD\\DEVELOP\\eclipse\\workspace\\X12\\src\\examples\\java\\org\\pb\\x12\\Dummy835Example.txt");
		try {
			x1 = p1.parse(f1);
			for (Segment s : x1) {
				if (s.getElement(0).equals("CLP")) {
					totalChargeAmount = totalChargeAmount + Double.parseDouble(s.getElement(3));
				}
			}
			System.out.println("Total Charged Amount = " + totalChargeAmount.toString());
						
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}

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
package org.pb.x12;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * The class represents methods used to translate a X12 transaction 
 * represented as a file or string into an X12 object.
 * @author Prasad Balan

 */
public class Parser {

	/**
	 * The method takes a X12 file and converts it into a X2 object.
	 * The X12 class has methods to convert it into XML format as well
	 * as methods to modify the contents.
	 * @param fileName a X12 file
	 * @return the X12 object
	 * @throws FormatException
	 * @throws IOException
	 */
	public X12 parse(File fileName) throws FormatException, IOException {
		char[] c = new char[106];
		FileReader fr = new FileReader(fileName);
		int count = fr.read(c);
		fr.close();
		if (count != 106) {
			throw new FormatException();
		}
		Context context = new Context();
		context.setSegmentSeperator(c[105]);
		context.setElementSeperator(c[3]);
		context.setCompositeElementSeperator(c[104]);

		Scanner scanner = new Scanner(fileName);
		scanner.useDelimiter(context.getSegmentSeperator() + "\r\n|"
				+ context.getSegmentSeperator() + "\n|"
				+ context.getSegmentSeperator());
		X12 x12 = new X12(context);
		while (scanner.hasNext()) {
			String line = scanner.next();
			Segment s = x12.addSegment();
			String[] tokens = line.split("\\" + context.getElementSeperator());
			s.addElements(tokens);
		}
		scanner.close();
		return x12;
	}

	/**
	 * The method takes a X12 string and converts it into a X2 object.
	 * The X12 class has methods to convert it into XML format as well
	 * as methods to modify the contents.
	 * @param x12String
	 * @return the X12 object
	 * @throws FormatException
	 * @throws IOException
	 */
	public X12 parse(String x12String) throws FormatException, IOException {

		if(x12String.length()<106) {
			throw new FormatException();
		}
		Context context = new Context();
		context.setSegmentSeperator(x12String.charAt(105));
		context.setElementSeperator(x12String.charAt(3));
		context.setCompositeElementSeperator(x12String.charAt(104));
		
		Scanner scanner = new Scanner(x12String);
		scanner.useDelimiter(context.getSegmentSeperator() + "\r\n|"
				+ context.getSegmentSeperator() + "\n|"
				+ context.getSegmentSeperator());
		X12 x12 = new X12(context);
		while (scanner.hasNext()) {
			String line = scanner.next();
			Segment s = x12.addSegment();
			String[] tokens = line.split("\\" + context.getElementSeperator());
			s.addElements(tokens);
		}
		scanner.close();
		return x12;
	}
}

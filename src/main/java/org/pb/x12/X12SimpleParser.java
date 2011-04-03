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
package org.pb.x12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

/**
 * The class represents methods used to translate a X12 transaction represented
 * as a file or string into an X12 object.
 * 
 * @author Prasad Balan
 */
public class X12SimpleParser implements Parser {

	static final int SIZE = 106;
	static final int POS_SEGMENT = 105;
	static final int POS_ELEMENT = 3;
	static final int POS_COMPOSITE_ELEMENT = 104;

	/**
	 * The method takes a X12 file and converts it into a X2 object. The X12
	 * class has methods to convert it into XML format as well as methods to
	 * modify the contents.
	 * 
	 * @param fileName
	 *            a X12 file
	 * @return the X12 object
	 * @throws FormatException
	 * @throws IOException
	 */
	@Override
	public EDI parse(File fileName) throws FormatException, IOException {
		final char[] buffer = new char[SIZE];
		FileReader fr = new FileReader(fileName);
		int count = fr.read(buffer);
		fr.close();
		if (count != SIZE) {
			throw new FormatException();
		}
		Context context = new Context();
		context.setSegmentSeparator(buffer[POS_SEGMENT]);
		context.setElementSeparator(buffer[POS_ELEMENT]);
		context.setCompositeElementSeparator(buffer[POS_COMPOSITE_ELEMENT]);

		Scanner scanner = new Scanner(fileName);
		scanner.useDelimiter(context.getSegmentSeparator() + "\r\n|"
				+ context.getSegmentSeparator() + "\n|"
				+ context.getSegmentSeparator());
		X12Simple x12 = new X12Simple(context);
		while (scanner.hasNext()) {
			String line = scanner.next();
			x12.addSegment(line);
		}
		scanner.close();
		return x12;
	}

	/**
	 * The method takes a InputStream and converts it into a X2 object. The X12
	 * class has methods to convert it into XML format as well as methods to
	 * modify the contents.
	 * 
	 * @param source
	 *            InputStream
	 * @return the X12 object
	 * @throws FormatException
	 * @throws IOException
	 */
	@Override
	public EDI parse(InputStream source) throws FormatException, IOException {
		StringBuffer strBuffer = new StringBuffer();
		char[] cbuf = new char[1024];
		int length = -1;

		Reader reader = new BufferedReader(new InputStreamReader(source));

		while ((length = reader.read(cbuf)) != -1) {
			strBuffer.append(cbuf, 0, length);
		}

		String strSource = strBuffer.toString();

		return parse(strSource);
	}

	/**
	 * The method takes a X12 string and converts it into a X2 object. The X12
	 * class has methods to convert it into XML format as well as methods to
	 * modify the contents.
	 * 
	 * @param source
	 *            String
	 * @return the X12 object
	 * @throws FormatException
	 * @throws IOException
	 */
	@Override
	public EDI parse(String source) throws FormatException {
		if (source.length() < SIZE) {
			throw new FormatException();
		}
		Context context = new Context();
		context.setSegmentSeparator(source.charAt(POS_SEGMENT));
		context.setElementSeparator(source.charAt(POS_ELEMENT));
		context.setCompositeElementSeparator(source
				.charAt(POS_COMPOSITE_ELEMENT));

		Scanner scanner = new Scanner(source);
		scanner.useDelimiter(context.getSegmentSeparator() + "\r\n|"
				+ context.getSegmentSeparator() + "\n|"
				+ context.getSegmentSeparator());
		X12Simple x12 = new X12Simple(context);
		while (scanner.hasNext()) {
			String line = scanner.next();
			Segment s = x12.addSegment();
			String[] tokens = line.split("\\" + context.getElementSeparator());
			s.addElements(tokens);
		}
		scanner.close();
		return x12;
	}

}

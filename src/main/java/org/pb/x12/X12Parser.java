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
import java.util.regex.Pattern;

/**
 * The class represents methods used to translate a X12 transaction represented
 * as a file or string into an X12 object.
 * 
 * @author Prasad Balan
 */
public class X12Parser implements Parser {

	private static final int SIZE = 106;
	public static final int POS_SEGMENT = 105;
	public static final int POS_ELEMENT = 3;
	public static final int POS_COMPOSITE_ELEMENT = 104;

	private Cf x12Cf;
	private Cf cfMarker;
	private Loop loopMarker;

	public X12Parser(Cf cf) {
		this.x12Cf = cf;
	}

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
		X12 x12 = scanSource(scanner, context);
		scanner.close();
		return x12;
	}

	/**
	 * private helper method
	 * @param scanner
	 * @param context
	 * @return
	 */
	private X12 scanSource(Scanner scanner, Context context) {
		Character segmentSeparator = context.getSegmentSeparator();
		String quotedSegmentSeparator = Pattern.quote(segmentSeparator.toString());
		
		scanner.useDelimiter(quotedSegmentSeparator + "\r\n|" + quotedSegmentSeparator + "\n|" + quotedSegmentSeparator);

		cfMarker = x12Cf;
		X12 x12 = new X12(context);
		loopMarker = x12;
		Loop loop = x12;

		while (scanner.hasNext()) {
			String line = scanner.next();
			String[] tokens = line.split("\\" + context.getElementSeparator());
			if (doesChildLoopMatch(cfMarker, tokens)) {
				loop = loop.addChild(cfMarker.getName());
				loop.addSegment(line);
			} else if (doesParentLoopMatch(cfMarker, tokens, loop)) {
				loop = loopMarker.addChild(cfMarker.getName());
				loop.addSegment(line);
			} else {
				loop.addSegment(line);
			}
		}
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
		context.setCompositeElementSeparator(source.charAt(POS_COMPOSITE_ELEMENT));

		Scanner scanner = new Scanner(source);
		X12 x12 = scanSource(scanner, context);
		scanner.close();
		return x12;
	}
 
	/**
	 * Checks if the segment (or line read) matches to current loop
	 * 
	 * @param cf
	 *            Cf
	 * @param tokens
	 *            String[] represents the segment broken into elements
	 * @return boolean
	 */
	private boolean doesLoopMatch(Cf cf, String[] tokens) {
		if (cf.getSegment().equals(tokens[0])) {
			if (null == cf.getSegmentQualPos()) {
				return true;
			} else {
				for (String qual : cf.getSegmentQuals()) {
					if (qual.equals(tokens[cf.getSegmentQualPos()])) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the segment (or line read) matches to any of the child loops
	 * configuration.
	 * 
	 * @param cf
	 *            Cf
	 * @param tokens
	 *            String[] represents the segment broken into elements
	 * @return boolean
	 */
	boolean doesChildLoopMatch(Cf parent, String[] tokens) {
		for (Cf cf : parent.childList()) {
			if (doesLoopMatch(cf, tokens)) {
				cfMarker = cf;
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the segment (or line read) matches the parent loop
	 * configuration.
	 * 
	 * @param cf
	 *            Cf
	 * @param tokens
	 *            String[] represents the segment broken into elements
	 * @param loop
	 *            Loop            
	 * @return boolean
	 */
	private boolean doesParentLoopMatch(Cf child, String[] tokens, Loop loop) {
		Cf parent = child.getParent();
		if (parent == null)
			return false;
		
		loopMarker = loop.getParent();
		for (Cf cf : parent.childList()) {
			if (doesLoopMatch(cf, tokens)) {
				cfMarker = cf;
				return true;
			}
		}
		
		if (doesParentLoopMatch(parent, tokens, loopMarker))
			return true;
		
		return false;
	}
}

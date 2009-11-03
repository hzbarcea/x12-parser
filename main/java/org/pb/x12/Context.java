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
/**
 * The class represents an X12 context. A X12 context consists of a segment
 * separator, element separator and a composite element separator.
 * 
 * @author Prasad Balan
 */
public class Context {
	private char segmentSeperator;
	private char elementSeperator;
	private char compositeElementSeperator;

	/**
	 * Default constructor.
	 */
	public Context() {

	}

	/**
	 * Constructor which takes the segment separator, element separator and
	 * composite element separator as input.
	 * 
	 * @param s
	 *            segment separator
	 * @param e
	 *            element separator
	 * @param c
	 *            composite element separator
	 */
	public Context(char s, char e, char c) {
		this.segmentSeperator = s;
		this.elementSeperator = e;
		this.compositeElementSeperator = c;
	}

	/**
	 * Returns the segment separator.
	 * 
	 * @return a segment separator
	 */
	public char getSegmentSeperator() {
		return segmentSeperator;
	}

	/**
	 * Sets the segment separator.
	 * 
	 * @param segmentSeperator
	 *            the segment separator
	 */
	public void setSegmentSeperator(char segmentSeperator) {
		this.segmentSeperator = segmentSeperator;
	}

	/**
	 * Returns the element separator.
	 * 
	 * @return an element separator
	 */
	public char getElementSeperator() {
		return elementSeperator;
	}

	/**
	 * Sets the element separator.
	 * 
	 * @param elementSeperator
	 *            the element separator.
	 */
	public void setElementSeperator(char elementSeperator) {
		this.elementSeperator = elementSeperator;
	}

	/**
	 * Returns the composite element separator.
	 * 
	 * @return composite element seperator
	 */
	public char getCompositeElementSeperator() {
		return compositeElementSeperator;
	}

	/**
	 * Sets the composite element separator.
	 * 
	 * @param compositeElementSeperator
	 *            the composite element separator.
	 */
	public void setCompositeElementSeperator(char compositeElementSeperator) {
		this.compositeElementSeperator = compositeElementSeperator;
	}

	/**
	 * Returns a <code>String</code> consisting of segment, element and
	 * composite element separator.
	 */
	public String toString() {
		return "[" + this.compositeElementSeperator + ","
				+ this.elementSeperator + "," + this.segmentSeperator + "]";
	}

}

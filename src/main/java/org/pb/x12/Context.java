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

/**
 * The class represents an X12 context. A X12 context consists of a segment
 * separator, element separator and a composite element separator.
 * 
 * @author Prasad Balan
 */
public class Context {
	private Character s;
	private Character e;
	private Character c;

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
	public Context(Character s, Character e, Character c) {
		this.s = s;
		this.e = e;
		this.c = c;
	}

	/**
	 * Returns the composite element separator.
	 * 
	 * @return composite element separator
	 */
	public Character getCompositeElementSeparator() {
		return c;
	}

	/**
	 * Returns the element separator.
	 * 
	 * @return an element separator
	 */
	public Character getElementSeparator() {
		return e;
	}

	/**
	 * Returns the segment separator.
	 * 
	 * @return a segment separator
	 */
	public Character getSegmentSeparator() {
		return s;
	}

	/**
	 * Sets the composite element separator.
	 * 
	 * @param c
	 *            the composite element separator.
	 */
	public void setCompositeElementSeparator(Character c) {
		this.c = c;
	}

	/**
	 * Sets the element separator.
	 * 
	 * @param e
	 *            the element separator.
	 */
	public void setElementSeparator(Character e) {
		this.e = e;
	}

	/**
	 * Sets the segment separator.
	 * 
	 * @param s
	 *            the segment separator
	 */
	public void setSegmentSeparator(Character s) {
		this.s = s;
	}

	/**
	 * Returns a <code>String</code> consisting of segment, element and
	 * composite element separator.
	 */
	public String toString() {
		return "[" + this.c + "," + this.e + "," + this.s + "]";
	}

}

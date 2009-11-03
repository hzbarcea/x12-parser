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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents an X12 segment.
 * 
 * @author Prasad Balan
 */
public class Segment implements Iterable<String> {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> a;
	private Context context;

	/**
	 * The constructor takes a <code>Context</code> object as input. The context
	 * object represents the delimiters in a X12 transaction.
	 * 
	 * @param c
	 *            the context object
	 */
	public Segment(Context c) {
		this.context = c;
		this.a = new ArrayList<String>();
	}

	/**
	 * Adds <code>String</code> elements to the segment. The elements are added
	 * at the end of the elements in the current segment.
	 * 
	 * @param elements
	 */
	public void addElements(String... elements) {
		for (String s : elements) {
			a.add(s);
		}
	}

	/**
	 * Adds <code>String</code> element to the segment. The element is added at
	 * the end of the elements in the current segment.
	 * 
	 * @param e the element to be added
	 * @return boolean
	 */
	public boolean addElement(String e) {
		return a.add(e);
	}

	/**
	 * Adds <code>String</code> element to the segment at specified position.
	 * 
	 * @param index position at which element is to be added
	 * @param e element to be added
	 */
	public void addElement(int index, String e) {
		a.add(index, e);
	}

	/**
	 * Adds strings as a composite element to the end of the segment.
	 * 
	 * @param compele sub-elements of a composite element
	 */
	public void addCompositeElement(String... compele) {
		StringBuffer dump = new StringBuffer();
		for (String s : compele) {
			dump.append(s);
			dump.append(context.getCompositeElementSeperator());
		}
		a.add(dump.substring(0, dump.length() - 1));
	}

	/**
	 * Replaces element at the specified position with the specified <code>String</code>
	 * @param index position of the element to be replaced
	 * @param s new element with which to replace
	 * @return the element at the specified position.
	 */
	public String setElement(int index, String s) {
		a.set(index, s);
		return s;

	}

	/**
	 * Returns the <code>String<code> element at the specified position.
	 * @param index position
	 * @return the element at the specified position.
	 */
	public String getElement(int index) {
		return a.get(index);
	}

	/**
	 * Returns the X12 representation of the segment.
	 */
	public String toString() {
		StringBuffer dump = new StringBuffer();
		for (String s : this.a) {
			dump.append(s);
			dump.append(context.getElementSeperator());
		}
		return dump.substring(0, dump.length() - 1);
	}

	/**
	 * Returns the XML representation of the segment.
	 * @return <code>String</code>
	 */
	public String toXML() {
		StringBuffer dump = new StringBuffer();
		dump.append("<" + this.a.get(0) + ">");
		for (int i = 1; i < this.a.size(); i++) {
			dump.append("<" + this.a.get(0) + String.format("%1$02d", i)
					+ "><![CDATA[");
			dump.append(this.a.get(i));
			dump.append("]]></" + this.a.get(0) + String.format("%1$02d", i)
					+ ">");
		}
		dump.append("</" + this.a.get(0) + ">");
		return dump.toString();
	}
	
	/**
	 * Returns the context object
	 * @return Context object
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * Sets the context of the segment
	 * @param context context object
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * Returns and <code>Iterator</code> to the elements in the segment.
	 */
	@Override
	public Iterator<String> iterator() {
		return a.iterator();
	}
}

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents an X12 segment.
 * 
 * @author Prasad Balan
 */
public class Segment implements Iterable<String> {
	private static final long serialVersionUID = 1L;
	private Context context;
	private List<String> elements = new ArrayList<String>();

	/**
	 * The constructor takes a <code>Context</code> object as input. The context
	 * object represents the delimiters in a X12 transaction.
	 * 
	 * @param c
	 *            the context object
	 */
	public Segment(Context c) {
		this.context = c;
	}

	/**
	 * Adds <code>String</code> element to the segment. The element is added at
	 * the end of the elements in the current segment.
	 * 
	 * @param e
	 *            the element to be added
	 * @return boolean
	 */
	public boolean addElement(String e) {
		return elements.add(e);
	}

	/**
	 * Adds <code>String</code> with elements to the segment. The elements are
	 * added at the end of the elements in the current segment. e.g.
	 * <code>addElements("ISA*ISA01*ISA02");</code>
	 * 
	 * @param es
	 * @return boolean
	 */
	public boolean addElements(String s) {
		String[] elements = s.split("\\" + context.getElementSeparator());
		return this.addElements(elements);
	}

	/**
	 * Adds <code>String</code> elements to the segment. The elements are added
	 * at the end of the elements in the current segment. e.g.
	 * <code> addElements("ISA", "ISA01", "ISA02");</code>
	 * @param es
	 * @return boolean
	 */
	public boolean addElements(String... es) {
		for (String s : es) {
			if (!this.elements.add(s))
				return false;
		}
		return true;
	}

	/**
	 * Adds strings as a composite element to the end of the segment.
	 * 
	 * @param ces
	 *            sub-elements of a composite element
	 * @return boolean
	 */
	public boolean addCompositeElement(String... ces) {
		StringBuffer dump = new StringBuffer();
		for (String s : ces) {
			dump.append(s);
			dump.append(context.getCompositeElementSeparator());
		}
		return this.elements.add(dump.substring(0, dump.length() - 1));
	}

	/**
	 * Inserts <code>String</code> element to the segment at the specified
	 * position
	 * 
	 * @param e
	 *            the element to be added
	 * @return boolean
	 */
	public boolean addElement(int index, String e) {
		return this.elements.add(e);
	}

	/**
	 * Inserts strings as a composite element to segment at specified position
	 * 
	 * @param ces
	 *            sub-elements of a composite element
	 */
	public void addCompositeElement(int index, String... ces) {
		StringBuffer dump = new StringBuffer();
		for (String s : ces) {
			dump.append(s);
			dump.append(context.getCompositeElementSeparator());
		}
		this.elements.add(index, dump.substring(0, dump.length() - 1));
	}

	/**
	 * Returns the context object
	 * 
	 * @return Context object
	 */
	public Context getContext() {
		return this.context;
	}

	/**
	 * Returns the <code>String<code> element at the specified position.
	 * 
	 * @param index
	 *            position
	 * @return the element at the specified position.
	 */
	public String getElement(int index) {
		return elements.get(index);
	}

	/**
	 * Returns and <code>Iterator</code> to the elements in the segment.
	 * 
	 * @return Iterator<String>
	 */
	@Override
	public Iterator<String> iterator() {
		return elements.iterator();
	}
	
	/**
	 * Sets the context of the segment
	 * 
	 * @param context
	 *            context object
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * Replaces element at the specified position with the specified
	 * <code>String</code>
	 * 
	 * @param index
	 *            position of the element to be replaced
	 * @param s
	 *            new element with which to replace
	 */
	public void setElement(int index, String s) {
		elements.set(index, s);
	}

	/**
	 * Replaces composite element at the specified position in segment.
	 * 
	 * @param ces
	 *            sub-elements of a composite element
	 */
	public void setCompositeElement(int index, String... ces) {
		StringBuffer dump = new StringBuffer();
		for (String s : ces) {
			dump.append(s);
			dump.append(context.getCompositeElementSeparator());
		}
		elements.set(index, dump.substring(0, dump.length() - 1));
	}

	/**
	 * Returns number of elements in the segment.
	 * 
	 * @return size
	 */
	public int size() {
		return elements.size();
	}

	/**
	 * Returns the X12 representation of the segment.
	 */
	public String toString() {
		StringBuffer dump = new StringBuffer();
		for (String s : this.elements) {
			dump.append(s);
			dump.append(context.getElementSeparator());
		}
		if (dump.length() == 0) {
			return "";
		}
		return dump.substring(0, dump.length() - 1);
	}

	/**
	 * Returns the XML representation of the segment.
	 * 
	 * @return <code>String</code>
	 */
	public String toXML() {
		StringBuffer dump = new StringBuffer();
		dump.append("<" + this.elements.get(0) + ">");
		for (int i = 1; i < this.elements.size(); i++) {
			dump.append("<" + this.elements.get(0) + String.format("%1$02d", i)
					+ "><![CDATA[");
			dump.append(this.elements.get(i));
			dump.append("]]></" + this.elements.get(0)
					+ String.format("%1$02d", i) + ">");
		}
		dump.append("</" + this.elements.get(0) + ">");
		return dump.toString();
	}

}

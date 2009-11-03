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
 * The <code>X12</code> class is the object representation of an
 * ANSI X12 transaction. The building block of an X12 transaction is
 * an element. Some elements may be made of sub elements. Elements 
 * combine to form segments. Segments are grouped as loops. And a set
 * of loops form an X12 transaction.
 * @author Prasad Balan
 *
 */

public class X12 implements Iterable<Segment> {
	private static final long serialVersionUID = 1L;
	private ArrayList<Segment> a;
	private Context context;

	/**
	 * The constructor takes a context object. 
	 * @param c a Context object
	 */
	X12(Context c) {
		this.context = c;
		this.a = new ArrayList<Segment>();
	}

	/**
	 * Creates an empty instance of <code>Segment</code> and adds the segment
	 * to the end of the X12 transaction. The returned instance can be 
	 * used to add elements to the segment.
	 * @return a new Segment object
	 */
	public Segment addSegment() {
		Segment s = new Segment(this.context);
		a.add(s);
		return s;
	}
	/**
	 * Creates an empty instance of <code>Segment</code> and adds the segment at the 
	 * specified position in the X12 transaction. The returned instance can be used
	 * to add elements to the segment.
	 * @param index position at which to add the segment.
	 * @return a new Segment object
	 */
	public Segment addSegment(int index) {
		Segment s = new Segment(this.context);
		a.add(index, s);
		return s;
	}

	/**
	 * Takes a <code>String</code> representation of segment, creates a <code>Segment</code>
	 * object and adds the segment to the end of the X12 transaction.
	 * @param segment <code>String</code> representation of the Segment.
	 * @return a new Segment object
	 */
	public Segment addSegment(String segment) {
		Segment s = new Segment(this.context);
		String[] elements = segment.split("\\" + context.getElementSeperator());
		s.addElements(elements);
		a.add(s);
		return s;
	}

	/**
	 * Takes a <code>String</code> representation of segment, creates a <code>Segment</code>
	 * object and adds the segment at the specified position in the X12 transaction.
	 * @param index position to add the segment.
	 * @param segment <code>String</code> representation of the segment.
	 * @return a new Segment object
	 */
	public Segment addSegment(int index, String segment) {
		Segment s = new Segment(this.context);
		String[] elements = segment.split("\\" + context.getElementSeperator());
		s.addElements(elements);
		a.add(index, s);
		return s;
	}

	/**
	 * Takes a <code>String</code> representation of segment, creates a <code>Segment</code>
	 * object and replaces the segment at the specified position in the X12 transaction.
	 * @param index position of the segment to be replaced.
	 * @param segment <code>String</code> representation of the Segment.
	 * @return a new Segment object
	 */
	public Segment setSegment(int index, String segment) {
		Segment s = new Segment(this.context);
		String[] elements = segment.split("\\" + context.getElementSeperator());
		s.addElements(elements);
		a.set(index, s);
		return s;		
	}
	
	/**
	 * Returns the <code>Segment<code> at the specified position.
	 * @param index
	 * @return Segment at the specified index
	 */
	public Segment getSegment(int index) {
		return a.get(index);
	}
	
	/**
	 * Returns the X12 transaction in <code>String</code> format.
	 * This method is used to convert the X12 object into a X12
	 * transaction.
	 */
	public String toString() {
		StringBuffer dump = new StringBuffer();
		for (Segment s : this.a) {
			dump.append(s.toString());
			dump.append(context.getSegmentSeperator());
		}
		return dump.toString();
	}

	/**
	 * Returns the X12 transaction in XML format. This method translates
	 * the X12 object into XML format. 
	 * @return XML string
	 */
	public String toXML() {
		StringBuffer dump = new StringBuffer();
		dump.append("<X12>");
		for (Segment s : this.a) {
			dump.append(s.toXML());
		}
		dump.append("</X12>");
		return dump.toString();
	}

	/**
	 * Returns the context of the X12 transaction.
	 * @return Context object
	 */
	public Context getContext() {
		return context;
	}

	/** 
	 * Sets the context of the current transaction.
	 * @param context
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * Returns a <code>Iterator</code> to the segments in the
	 * X12 object.
	 */
	@Override
	public Iterator<Segment> iterator() {
		return this.a.iterator();
	}

}

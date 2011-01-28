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
 * The X12 class is the object representation of an ANSI X12
 * transaction. The building block of an X12 transaction is an element. Some
 * elements may be made of sub elements. Elements combine to form segments.
 * Segments are grouped as loops. And a set of loops form an X12 transaction.
 * 
 * @author Prasad Balan
 * 
 */

public class X12Simple implements EDI, Iterable<Segment> {
	private static final long serialVersionUID = 1L;
	private Context context;
	private List<Segment> segments;

	/**
	 * The constructor takes a context object.
	 * 
	 * @param c
	 *            a Context object
	 */
	public X12Simple(Context c) {
		this.context = c;
		this.segments = new ArrayList<Segment>();
	}

	/**
	 * Creates an empty instance of <code>Segment</code> and adds the segment to
	 * the end of the X12 transaction. The returned instance can be used to add
	 * elements to the segment.
	 * 
	 * @return a new Segment object
	 */
	public Segment addSegment() {
		Segment s = new Segment(this.context);
		segments.add(s);
		return s;
	}

	/**
	 * Takes a <code>String</code> representation of segment, creates a
	 * <code>Segment</code> object and adds the segment to the end of the X12
	 * transaction.
	 * 
	 * @param segment
	 *            <code>String</code> representation of the Segment.
	 * @return a new Segment object
	 */
	public Segment addSegment(String segment) {
		Segment s = new Segment(this.context);
		String[] elements = segment.split("\\" + context.getElementSeparator());
		s.addElements(elements);
		segments.add(s);
		return s;
	}

	/**
	 * Takes a <code>Segment</code> and adds the segment to the end of the X12
	 * transaction.
	 * 
	 * @param segment
	 *            <code>Segment</code> representation of the Segment.
	 * @return a new Segment object
	 */
	public Segment addSegment(Segment segment) {
		segments.add(segment);
		return segment;
	}

	/**
	 * Creates an empty instance of <code>Segment</code> and inserts the segment
	 * at the specified position in the X12 transaction. The returned instance
	 * can be used to add elements to the segment.
	 * 
	 * @param index
	 *            position at which to add the segment.
	 * @return a new Segment object
	 */
	public Segment addSegment(int index) {
		Segment s = new Segment(this.context);
		segments.add(index, s);
		return s;
	}

	/**
	 * Takes a <code>String</code> representation of segment, creates a
	 * <code>Segment</code> object and inserts the segment at the specified
	 * position
	 * 
	 * @param segment
	 *            <code>String</code> representation of the Segment.
	 * @return a new Segment object
	 */
	public Segment addSegment(int index, String segment) {
		Segment s = new Segment(this.context);
		String[] elements = segment.split("\\" + context.getElementSeparator());
		s.addElements(elements);
		segments.add(index, s);
		return s;
	}

	/**
	 * Takes a <code>String</code> representation of segment, creates a
	 * <code>Segment</code> object and inserts the segment at the specified
	 * position
	 * 
	 * @param segment
	 *            <code>String</code> representation of the Segment.
	 * @return a new Segment object            
	 */
	public Segment addSegment(int index, Segment segment) {
		segments.add(index, segment);
		return segment;
	}

	/**
	 * Get the segments in the X12 transaction.
	 * 
	 * @param name
	 *            name of a segment
	 * @return List<Segment>
	 */
	public List<Segment> findSegment(String name) {
		List<Segment> foundSegments = new ArrayList<Segment>();
		for (Segment s : this.segments) {
			if (name.equals(s.getElement(0))) {
				foundSegments.add(s);
			}
		}
		return foundSegments;
	}
	
	/**
	 * Returns the context of the X12 transaction.
	 * 
	 * @return Context object
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * Returns the <code>Segment<code> at the specified position.
	 * 
	 * @param index
	 * @return Segment at the specified index
	 */
	public Segment getSegment(int index) {
		return segments.get(index);
	}

	/**
	 * Returns and <code>Iterator</code> to the elements in the segment.
	 * 
	 * @return Iterator<String>
	 */
	@Override
	public Iterator<Segment> iterator() {
		return segments.iterator();
	}
	
	/**
	 * Sets the context of the current transaction.
	 * 
	 * @param context
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * Creates an empty instance of <code>Segment</code> and replaces the
	 * segment at specified position in the X12 transaction. The returned
	 * instance can be used to add elements to the segment.
	 * 
	 * @param index
	 *            position at which to add the segment.
	 * @return a new Segment object
	 */
	public Segment setSegment(int index) {
		Segment s = new Segment(this.context);
		segments.set(index, s);
		return s;
	}

	/**
	 * Takes a <code>String</code> representation of segment, creates a
	 * <code>Segment</code> object and replaces the segment at the specified
	 * position in the X12 transaction.
	 * 
	 * @param index
	 *            position of the segment to be replaced.
	 * @param segment
	 *            <code>String</code> representation of the Segment.
	 * @return a new Segment object
	 */
	public Segment setSegment(int index, String segment) {
		Segment s = new Segment(this.context);
		String[] elements = segment.split("\\" + context.getElementSeparator());
		s.addElements(elements);
		segments.set(index, s);
		return s;
	}

	/**
	 * Replaces
	 * <code>Segment<code> at the specified position in X12 transaction.
	 * 
	 * @param index
	 *            position of the segment to be replaced.
	 * @param segment
	 *            <code>Segment</code>
	 * @return a new Segment object
	 */
	public Segment setSegment(int index, Segment segment) {
		segments.set(index, segment);
		return segment;
	}

	/**
	 * Returns number of segments in the transaction
	 * 
	 * @return size
	 */
	public int size() {
		return segments.size();
	}

	/**
	 * Returns the X12 transaction in <code>String</code> format. This method is
	 * used to convert the X12 object into a X12 transaction.
	 */
	public String toString() {
		StringBuffer dump = new StringBuffer();
		for (Segment s : this.segments) {
			dump.append(s.toString());
			dump.append(context.getSegmentSeparator());
		}
		return dump.toString();
	}

	/**
	 * Returns the X12 transaction in XML format. This method translates the X12
	 * object into XML format.
	 * 
	 * @return XML string
	 */
	public String toXML() {
		StringBuffer dump = new StringBuffer();
		dump.append("<X12>");
		for (Segment s : this.segments) {
			dump.append(s.toXML());
		}
		dump.append("</X12>");
		return dump.toString();
	}
}

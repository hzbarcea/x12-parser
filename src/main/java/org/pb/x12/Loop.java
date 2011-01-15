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

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * The <code>Loop</code> class is the representation of an Loop in a ANSI X12
 * transaction. The building block of an X12 transaction is an element. Some
 * elements may be made of sub elements. Elements combine to form segments.
 * Segments are grouped as loops. And a set of loops form an X12 transaction.
 * 
 * @author Prasad Balan
 * 
 */

public class Loop implements Iterable<Segment> {
	private static final long serialVersionUID = 1L;
	private Context context;
	private String name;
	private List<Segment> segments = new ArrayList<Segment>();
	private List<Loop> loops = new ArrayList<Loop>();

	/**
	 * The constructor takes a context object.
	 * 
	 * @param c
	 *            a Context object
	 */
	public Loop(Context c, String name) {
		this.context = c;
		this.name = name;
	}

	/**
	 * Creates an empty instance of <code>Loop</code> and adds the loop as a
	 * child to the current Loop. The returned instance can be used to add
	 * segments to the child loop.
	 * 
	 * @param name
	 *            name of the loop
	 * @return a new child Loop object
	 */
	public Loop addChild(String name) {
		Loop l = new Loop(this.context, name);
		loops.add(l);
		return l;
	}

	/**
	 * Inserts <code>Loop</code> as a child loop at the specified position.
	 * 
	 * @param index
	 *            position at which to add the loop.
	 */
	public void addChild(int index, Loop loop) {
		loops.add(index, loop);
	}

	/**
	 * Creates an empty instance of <code>Segment</code> and adds the segment to
	 * current Loop. The returned instance can be used to add elements to the
	 * segment.
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
	 * <code>Segment</code> object and adds the segment to the current Loop.
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
	 * Adds <code>Segment</code> at the end of the current Loop
	 * 
	 * @param segment
	 *            <code>Segment</code>
	 */
	public void addSegment(Segment segment) {
		segments.add(segment);
	}

	/**
	 * Creates an empty instance of <code>Segment</code> and adds the segment at
	 * the specified position in the current Loop. The returned instance can be
	 * used to add elements to the segment.
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
	 * <code>Segment</code> object and adds the segment at the specified
	 * position in the current Loop.
	 * 
	 * @param index
	 *            position to add the segment.
	 * @param segment
	 *            <code>String</code> representation of the segment.
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
	 * Adds <code>Segment</code> at the specified position in current Loop.
	 * 
	 * @param index
	 *            position to add the segment.
	 * @param segment
	 *            <code>String</code> representation of the segment.
	 */
	public void addSegment(int index, Segment segment) {
		segments.add(index, segment);
	}

	/**
	 * Creates an empty instance of <code>Loop</code> and inserts the loop as a
	 * child loop at the specified position. The returned instance can be used
	 * to add segments to the child loop.
	 * 
	 * @param index
	 *            position at which to add the loop
	 * @param name
	 *            name of the loop
	 * @return a new child Loop object
	 */
	public Loop addChild(int index, String name) {
		Loop l = new Loop(this.context, name);
		loops.add(index, l);
		return l;
	}

	/**
	 * Checks if the Loop contains the specified child Loop. It will check the
	 * complete child hierarchy.
	 * 
	 * @param name
	 *            name of a child loop
	 * @return boolean
	 */
	public boolean hasLoop(String name) {
		for (Loop l : this.childList()) {
			if (name.equals(l.getName())) {
				return true;
			}
			if (l.hasLoop(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the loop in the X12 transaction It will check the complete child
	 * hierarchy.
	 * 
	 * @param name
	 *            name of a loop
	 * @return List<Loop>
	 */
	public List<Loop> findLoop(String name) {
		List<Loop> foundLoops = new ArrayList<Loop>();
		for (Loop l : this.childList()) {
			if (name.equals(l.getName())) {
				foundLoops.add(l);
			}
			List<Loop> moreLoops = l.findLoop(name);
			if (moreLoops.size() > 0) {
				foundLoops.addAll(moreLoops);
			}
		}
		return foundLoops;
	}

	/**
	 * Get the segment in the X12 transaction It will check the current loop and
	 * the complete child hierarchy.
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
		for (Loop l : this.childList()) {
			List<Segment> moreSegments = l.findSegment(name);
			if (moreSegments.size() > 0) {
				foundSegments.addAll(moreSegments);
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
	 * Returns the <code>Loop<code> at the specified position.
	 * 
	 * @param index
	 * @return Loop at the specified index
	 */
	public Loop getLoop(int index) {
		return loops.get(index);
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
	 * Returns the name of the current Loop.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns and <code>Iterator</code> to the segments in the loop.
	 * 
	 * @return Iterator<Segment>
	 */
	@Override
	public Iterator<Segment> iterator() {
		return segments.iterator();
	}
	
	/**
	 * Returns <code>List<Loop></code> of child Loops
	 * 
	 * @return List<Loop>
	 */
	public List<Loop> childList() {
		return this.loops;
	}

	/**
	 * Returns number of segments in Loop and child loops
	 * 
	 * @return size
	 */
	public int size() {
		int size = 0;
		size = this.segments.size();
		for (Loop l : this.childList()) {
			size += l.size();
		}
		return size;
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
	 * Creates a new <code>Loop</code> and replaces the child loop at the
	 * specified position. The returned instance can be used to add segments to
	 * the child loop.
	 * 
	 * @param name
	 *            name of the loop 
	 * @param index
	 *            position at which to add the loop.
	 * @return a new child Loop object
	 */
	public Loop setChild(int index, String name) {
		Loop l = new Loop(this.context, name);
		loops.set(index, l);
		return l;
	}

	/**
	 * Replaces child <code>Loop</code> at the specified position.
	 * 
	 * @param index
	 *            position at which to add the loop.
	 * @param loop
	 *            Loop to add            
	 */
	public void setChild(int index, Loop loop) {
		loops.set(index, loop);
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
	 */
	public void setSegment(int index, Segment segment) {
		segments.set(index, segment);
	}

	/**
	 * Sets the name of the current Loop
	 * 
	 * @param name
	 *            <code>String</code>
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the Loop in X12 <code>String</code> format. This method is used
	 * to convert the X12 object into a X12 transaction.
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer dump = new StringBuffer();
		for (Segment s : this.segments) {
			dump.append(s.toString());
			dump.append(context.getSegmentSeparator());
		}
		for (Loop l : this.childList()) {
			dump.append(l.toString());
		}
		return dump.toString();
	}

	/**
	 * Returns the Loop in XML <code>String</code> format. This method is used
	 * to convert the X12 object into a XML string.
	 * 
	 * @return XML String
	 */
	public String toXML() {
		StringBuffer dump = new StringBuffer();
		dump.append("<LOOP NAME=\"").append(this.name).append("\">");
		for (Segment s : this.segments) {
			dump.append(s.toXML());
		}
		for (Loop l : this.childList()) {
			dump.append(l.toXML());
		}
		dump.append("</LOOP>");
		return dump.toString();
	}

}

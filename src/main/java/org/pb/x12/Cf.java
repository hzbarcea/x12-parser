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
import java.util.List;

/**
 * The <code>Cf</code> class represents a configuration element. Each Cf
 * instance represents items required to identify a Loop in a X12 transaction.
 * Some Loops can be identified by only the segment id. Others require segment
 * id and additional qualifiers to be able to identify the Loop.
 * 
 * <code>Cf</code> needs to be used in conjunction with X12Parser, to be able to
 * parse a X12 transaction into a loop hierarchy.
 * 
 * A X12 Cf can be loaded using many ways: custom code O/X mapping DI or any
 * other way you may find appropriate
 * 
 * A Sample 835 hierarchy is shown below. Each row shows a Cf element, in the
 * format
 * 
 * <pre>
 *    (A) - (B) - (C) - (D)
 *    (A) - Loop Name
 *    (B) - Segment id, that identifies the loop
 *    (C) - Segment qualifiers, that are needed to identify the loop. If there are multiple
 *          qualifiers they need to be separated by COMMA.
 *    (D) - Position in the segment where the qualifiers are present
 * </pre>
 * 
 * e.g. In X12 835, Loops 1000A and 1000B have the same segment id (N1), to
 * differentiate them we need additional attributes. The N102 (index 1) element
 * has PR for 1000A loop and PE for 1000B loop.
 * 
 * <pre>
 * +--X12
 * |  +--ISA - ISA
 * |  |  +--GS - GS
 * |  |  |  +--ST - ST - 835, - 1
 * |  |  |  |  +--1000A - N1 - PR, - 1
 * |  |  |  |  +--1000B - N1 - PE, - 1
 * |  |  |  |  +--2000 - LX
 * |  |  |  |  |  +--2100 - CLP
 * |  |  |  |  |  |  +--2110 - SVC
 * |  |  |  +--SE - SE
 * |  |  +--GE - GE
 * |  +--IEA - IEA
 * </pre>
 * 
 * To parse a X12 835 in the above hierarchy, you need to create a Cf object
 * that represent the hierarchy. Here is the sample code to achieve this.
 * 
 * <pre>
 * Cf cfX12 = new Cf("X12"); // root node
 * Cf cfISA = cfX12.addChild("ISA", "ISA"); // add as child of X12 
 * Cf cfGS = cfISA.addChild("GS", "GS"); // add as child of ISA
 * Cf cfST = cfGS.addChild("ST", "ST", "835", 1); // add as child of GS
 * cfST.addChild("1000A", "N1", "PR", 1); // add as child of ST
 * cfST.addChild("1000B", "N1", "PE", 1); // add as child of ST
 * Cf cf2000 = cfST.addChild("2000", "LX")
 * Cf cf2100 = cf2000.addChild("2100", "CLP");
 * cf2100.addChild("2110", "SVC");
 * cfISA.addChild("GE", "GE");
 * cfX12.addChild("IEA", "IEA");
 * </pre>
 * 
 * Alternate hierarchy for the same transaction. On most occasions a simple
 * hierarchy like below would work. Only when there is more that one loop that
 * is identified by the same segment id and additional qualifiers, you need to
 * put them under the appropriate parent Cf.
 * 
 * <pre>
 *  +--X12
 *  |  +--ISA - ISA
 *  |  +--GS - GS
 *  |  +--ST - ST - 835, - 1
 *  |  +--1000A - N1 - PR, - 1
 *  |  +--1000B - N1 - PE, - 1
 *  |  +--2000 - LX
 *  |  +--2100 - CLP
 *  |  +--2110 - SVC
 *  |  +--SE - SE  
 *  |  +--GE - GE  
 *  |  +--IEA - IEA
 * </pre>
 * 
 * @author Prasad Balan
 */
public class Cf {
	private String name;
	private String segment;
	private String[] segmentQuals;
	private Integer segmentQualPos;
	private int depth;

	private List<Cf> children = new ArrayList<Cf>();
	private Cf parent;

	public Cf(String name) {
		this.name = name;
	}

	public Cf(String name, String segment) {
		this.name = name;
		this.segment = segment;
	}

	public Cf(String name, String segment, String segmentQual,
			Integer segmentQualPos) {
		this.name = name;
		this.segment = segment;
		this.segmentQuals = segmentQual.split(",");
		this.segmentQualPos = segmentQualPos;
	}

	public void addChild(Cf cf) {
		cf.depth = this.depth + 1;
		this.children.add(cf);
		cf.setParent(this);
	}
	
	public Cf addChild(String name, String segment) {
		Cf cf = new Cf(name, segment);
		cf.depth = this.depth + 1;
		this.children.add(cf);
		cf.setParent(this);
		return cf;
	}

	public Cf addChild(String name, String segment, String segmentQual,
			Integer segmentQualPos) {
		Cf cf = new Cf(name, segment, segmentQual, segmentQualPos);
		cf.depth = this.depth + 1;
		this.children.add(cf);
		cf.setParent(this);
		return cf;
	}
	
	public List<Cf> childList() {
		return children;
	}

	public boolean hasChildren() {
		return this.children.size() > 0 ? true : false;
	}

	public boolean hasParent() {
		return this.parent == null ? false : true;
	}

	public Cf getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	public String getSegment() {
		return segment;
	}

	public String[] getSegmentQuals() {
		return segmentQuals;
	}

	public Integer getSegmentQualPos() {
		return segmentQualPos;
	}

	public void setParent(Cf cf) {
		this.parent = cf;
	}

	public void setChildren(List<Cf> cfList) {
		this.children = cfList;
		for (Cf cf : cfList) {
			cf.depth = this.depth + 1;
			cf.setParent(this);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public void setSegmentQuals(String[] segmentQuals) {
		this.segmentQuals = segmentQuals;
	}

	public void setSegmentQualPos(Integer segmentQualPos) {
		this.segmentQualPos = segmentQualPos;
	}

	public String toString() {
		StringBuffer dump = new StringBuffer();
		for(int i=0; i < depth; i++){
			dump.append("|  ");
		}
		dump.append("+--");
		dump.append(name);
		if (segment != null)
			dump.append(" - ").append(segment);
		if (segmentQuals != null) {
			dump.append(" - ");
			for(String s : segmentQuals) {
				dump.append(s).append(",");
			}
		}
		if (segmentQualPos != null)
			dump.append(" - ").append(segmentQualPos);
		dump.append(System.getProperty("line.separator"));
		for (Cf cf : children) {
			dump.append(cf.toString());
		}
		return dump.toString();
	}
}
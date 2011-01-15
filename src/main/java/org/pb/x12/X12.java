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
 * The <code>X12</code> class is the object representation of an ANSI X12
 * transaction. The building block of an X12 transaction is an element. Some
 * elements may be made of sub elements. Elements combine to form segments.
 * Segments are grouped as loops. And a set of loops form an X12 transaction.
 * 
 * @author Prasad Balan
 * 
 */

public class X12 extends Loop implements EDI {

	/**
	 * The constructor takes a context object.
	 * 
	 * @param c
	 *            a Context object
	 */
	public X12(Context c) {
		super(c, "X12");
	}
}

/**
 * Copyright (C) 2010 University of Washington
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.opendatakit.aggregate.datamodel;

import org.opendatakit.common.datamodel.DynamicDocumentBase;
import org.opendatakit.common.persistence.DataField;
import org.opendatakit.common.security.User;

/**
 * Long strings may span multiple texts; this class holds one 
 * text in the sequence of texts that comprise a long string.
 * The class holds just the textual content.  
 * See {@link LongStringRefText} which defines the ordering
 * of Texts within a long string.
 * <p>
 * The intent is that this is a write-once record with put/get
 * semantics.  Its functionality could be replaced with S3, or 
 * other document storage services. 
 * 
 * @author mitchellsundt@gmail.com
 * @author wbrunette@gmail.com
 * 
 */
public final class RefText extends DynamicDocumentBase {

	private static final DataField VALUE = new DataField("VALUE",DataField.DataType.LONG_STRING, false);
	public final DataField value;
	
	/**
	 * Construct a relation prototype.
	 * 
	 * @param databaseSchema
	 * @param tableName
	 */
	public RefText(String databaseSchema, String tableName) {
		super(databaseSchema, tableName);
		fieldList.add(value = new DataField(VALUE));
	}

	/**
	 * Construct an empty entity.  Only called via {@link #getEmptyRow(User)}
	 * 
	 * @param ref
	 * @param user
	 */
	private RefText(RefText ref, User user) {
		super(ref, user);
		value = ref.value;
	}

	// Only called from within the persistence layer.
	@Override
	public RefText getEmptyRow(User user) {
		return new RefText(this, user);
	}

	public String getValue() {
		return getStringField(value);
	}
	
	public void setValue(String str) {
		setStringField(value, str);
	}
}
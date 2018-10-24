/*
 *  Copyright 2008 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mybatis.generator.codegen;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Context;

/**
 * 
 * @author Jeff Butler
 * 
 */
public abstract class AbstractGenerator {
	protected static final String modalName = "record";
	protected static final String paramName = "data";

	protected static final String SUPER = "super";
	protected static final String THIS = "this";
	protected static final String RETURN = "return";
	/** 顿号 **/
	protected static final String SEPARATOR = ".";
	/** 空格 **/
	protected static final String BLANK = " ";
	/** 左括弧 **/
	protected static final String LEFT_PARENTHESIS = "(";
	/** 右括弧 **/
	protected static final String RIGHT_PARENTHESIS = ")";
	/** 分号 **/
	protected static final String semicolon = ";";

	/** 分号 **/
	protected void appendSemicolon(StringBuilder sb) {
		sb.append(semicolon);
	}

	/** 左括弧 **/
	protected void appendLeftParentheis(StringBuilder sb) {
		sb.append(LEFT_PARENTHESIS);
	}

	/** 右括弧 **/
	protected void appendRightParentheis(StringBuilder sb) {
		sb.append(RIGHT_PARENTHESIS);
	}

	/** 顿号 **/
	protected void appendSeparator(StringBuilder sb) {
		sb.append(SEPARATOR);
	}

	/** 空格 **/
	protected void appendBlank(StringBuilder sb) {
		sb.append(BLANK);
	}

	protected Context context;
	protected IntrospectedTable introspectedTable;
	protected List<String> warnings;
	protected ProgressCallback progressCallback;

	public AbstractGenerator() {
		super();
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public IntrospectedTable getIntrospectedTable() {
		return introspectedTable;
	}

	public void setIntrospectedTable(IntrospectedTable introspectedTable) {
		this.introspectedTable = introspectedTable;
	}

	public List<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

	public ProgressCallback getProgressCallback() {
		return progressCallback;
	}

	public void setProgressCallback(ProgressCallback progressCallback) {
		this.progressCallback = progressCallback;
	}
}

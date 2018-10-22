package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class QueryAllOnPageGenerator extends AbstractXmlElementGenerator {

	public QueryAllOnPageGenerator() {
		super();
	}

	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("select");

		answer.addAttribute(new Attribute("id", "queryList"));
		// �Ƿ���Ҫ�������Blob��resultMap
		if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
			answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
					introspectedTable.getResultMapWithBLOBsId()));
		} else {
			answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
					introspectedTable.getBaseResultMapId()));
		}

		answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
				"map"));

		context.getCommentGenerator().addComment(answer);

		StringBuilder sb = new StringBuilder();

		if (ShellRunner.isOracle)
			insertOraclePageinit(sb);

		sb.append(" 	select "); //$NON-NLS-1$

		if (stringHasValue(introspectedTable.getSelectByPrimaryKeyQueryId())) {
			sb.append('\'');
			sb.append(introspectedTable.getSelectByPrimaryKeyQueryId());
			sb.append("' as QUERYID,"); //$NON-NLS-1$
		}
		answer.addElement(new TextElement(sb.toString()));
		answer.addElement(getBaseColumnListElement());
		if (introspectedTable.hasBLOBColumns()) {
			answer.addElement(new TextElement(",")); //$NON-NLS-1$
			answer.addElement(getBlobColumnListElement());
		}

		sb.setLength(0);
		sb.append("from "); //$NON-NLS-1$
		// table
		sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
		answer.addElement(new TextElement(sb.toString()));

		XmlElement dynamicElement = new XmlElement("where"); //$NON-NLS-1$
		answer.addElement(dynamicElement);

		int start = 0;

		for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
			// .getNonPrimaryKeyColumns()) {
			XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
			sb.setLength(0);
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(" != null and "); //$NON-NLS-1$
			sb.append(introspectedColumn.getJavaProperty());
			sb.append(" != ''");
			isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
			dynamicElement.addElement(isNotNullElement);

			sb.setLength(0);

			if (start != 0)
				sb.append(" and ");
			++start;

			sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
			sb.append(" = "); //$NON-NLS-1$
			sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));

			isNotNullElement.addElement(new TextElement(sb.toString()));
		}

		if (ShellRunner.isOracle) {
			sb.setLength(0);
			insertOraclePageend(sb);
		} else {
			sb.setLength(0);
			sb.append(" limit #{offset},#{limit}");
		}
		answer.addElement(new TextElement(sb.toString()));

		// if (context.getPlugins()
		// .sqlMapUpdateByPrimaryKeySelectiveElementGenerated(answer,
		// introspectedTable)) {
		parentElement.addElement(answer);
		// }

	}

	public void insertOraclePageinit(StringBuilder sb) {
		sb.append(" SELECT * FROM (\n");
		sb.append("		SELECT A.*, ROWNUM RN \n");
		sb.append("				FROM ( \n");
	}

	/**
	 * @Describe
	 * @Author Dougest
	 * @Date 2018��9��21�� ����4:06:13
	 * @param sb
	 */

	public StringBuilder insertOraclePageend(StringBuilder sb) {
		sb.append("				) A  \n");
		sb.append("			) \n");
		sb.append("	WHERE RN BETWEEN #{start} AND #{end} \n");
		return sb;
	}

}

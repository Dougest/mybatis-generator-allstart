package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
/**
 * @Descripe 统计页码
 * @author @Dougest
 * @Date 2018年9月21日 下午3:19:55
 */
public class Count4QueryListOnPageGenerator extends
	AbstractXmlElementGenerator {

	public Count4QueryListOnPageGenerator(){
		super();
	}
	
	@Override
	public void addElements(XmlElement parentElement) {
		XmlElement answer = new XmlElement("select");
		
		answer.addAttribute(new Attribute("id", "countList"));
		answer.addAttribute(new Attribute("resultType", "int"));
	    answer.addAttribute(new Attribute("parameterType", "map"));
	
	    context.getCommentGenerator().addComment(answer);
	
	    StringBuilder sb = new StringBuilder();
	    sb.append("select count(1) from "); //$NON-NLS-1$
	    //table
	    sb.append(introspectedTable
	            .getAliasedFullyQualifiedTableNameAtRuntime());
	    answer.addElement(new TextElement(sb.toString()));
	    
	    XmlElement dynamicElement = new XmlElement("where"); //$NON-NLS-1$
	    answer.addElement(dynamicElement);
	    int start = 0;
	    for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()){
	        XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
	        sb.setLength(0);
	        sb.append(introspectedColumn.getJavaProperty());
	        sb.append(" != null and "); //$NON-NLS-1$
	        sb.append(introspectedColumn.getJavaProperty());
	        sb.append(" != ''");
	        isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
	        dynamicElement.addElement(isNotNullElement);
	
	        sb.setLength(0);
	        
	        if(start != 0) 
            	sb.append(" and ");
	        start++;
	        sb.append(MyBatis3FormattingUtilities
	                .getEscapedColumnName(introspectedColumn));
	        sb.append(" = "); //$NON-NLS-1$
	        sb.append(MyBatis3FormattingUtilities
	                .getParameterClause(introspectedColumn));
	        //sb.append(',');
	
	        isNotNullElement.addElement(new TextElement(sb.toString()));
	    }
	
//	    if (context.getPlugins()
//	            .sqlMapUpdateByPrimaryKeySelectiveElementGenerated(answer,
//	                    introspectedTable)) {
	        parentElement.addElement(answer);
//	    }
//		
		
	}
}

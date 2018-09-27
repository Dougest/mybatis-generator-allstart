package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class QueryAllOnPageMethodGenerator extends AbstractJavaMapperMethodGenerator {

	@Override
	public void addInterfaceElements(Interface interfaze) {
		
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		
		Method method = new Method();
		method.setName("queryList");
		
		FullyQualifiedJavaType param = FullyQualifiedJavaType.getNewMapInstance();
        param.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
        param.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
        Parameter parameter = new Parameter(param,"map");
        method.addParameter(parameter);
        importedTypes.add(param);
		
		FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
		FullyQualifiedJavaType entity = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		returnType.addTypeArgument(entity);
		
		method.setReturnType(returnType);
		method.setVisibility(JavaVisibility.PUBLIC);
		
		importedTypes.add(returnType);
		importedTypes.add(entity);
		
		
		interfaze.addMethod(method);
		interfaze.addImportedTypes(importedTypes);
		
		
	}
	
}

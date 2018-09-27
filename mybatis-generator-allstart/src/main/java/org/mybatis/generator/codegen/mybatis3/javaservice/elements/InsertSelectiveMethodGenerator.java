package org.mybatis.generator.codegen.mybatis3.javaservice.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.ConstKey;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

public class InsertSelectiveMethodGenerator extends AbstractJavaMapperMethodGenerator {
	public InsertSelectiveMethodGenerator() {
		super();
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();

		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName(ConstKey.SERVICE_ADD);

		FullyQualifiedJavaType parameterType = introspectedTable.getRules().calculateAllFieldsClass();

		importedTypes.add(parameterType);
		method.addParameter(new Parameter(parameterType, "record")); //$NON-NLS-1$

		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

		addMapperAnnotations(interfaze, method);

		if (context.getPlugins().clientInsertSelectiveMethodGenerated(method, interfaze, introspectedTable)) {
			interfaze.addImportedTypes(importedTypes);
			interfaze.addMethod(method);
		}
	}

	public void addMapperAnnotations(Interface interfaze, Method method) {
		return;
	}
}

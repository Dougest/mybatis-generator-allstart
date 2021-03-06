package org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.ConstKey;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.AbstractJavaMethodGenerator;

public class InfoMethodGenerator extends AbstractJavaMethodGenerator {

	@Override
	public void addClassMethod(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		// add return type
		method.setReturnType(introspectedTable.getRules().calculateAllFieldsClass());
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName(ConstKey.SERVICE_INFO);
		// add Parameter
		List<IntrospectedColumn> introspectedColumns = introspectedTable.getPrimaryKeyColumns();
		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
			importedTypes.add(type);
			Parameter parameter = new Parameter(type, introspectedColumn.getJavaProperty());
			method.addParameter(parameter);
		}
		// add body
		addMethodBody(method);

		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	@Override
	protected void addMethodBody(Method method) {
		List<Parameter> list = method.getParameters();
		String param = "id";
		if (list != null && !list.isEmpty()) {
			param = list.get(0).getName();
		}
		method.addBodyLine("return " + ConstKey.service_reference + ".selectByPrimaryKey(" + param + ");");
	}

}

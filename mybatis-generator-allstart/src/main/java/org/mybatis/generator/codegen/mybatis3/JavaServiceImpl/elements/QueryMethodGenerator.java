package org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements;

import org.mybatis.generator.api.ConstKey;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.AbstractJavaMethodGenerator;

public class QueryMethodGenerator extends AbstractJavaMethodGenerator {

	@Override
	public void addClassMethod(TopLevelClass topLevelClass) {
		Method method = new Method();
		method.setName(ConstKey.SERVICE_OLIST);
		method.setVisibility(JavaVisibility.PUBLIC);
		// add return type
		FullyQualifiedJavaType list = FullyQualifiedJavaType.getNewListInstance();
		list.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());
		method.setReturnType(list);
		// add parameter
		FullyQualifiedJavaType param = FullyQualifiedJavaType.getNewMapInstance();
		param.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
		param.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
		Parameter parameter = new Parameter(param, "map");
		method.addParameter(parameter);

		topLevelClass.addImportedType(param);
		topLevelClass.addImportedType(list);
		addMethodBody(method);
		topLevelClass.addMethod(method);
	}

	@Override
	protected void addMethodBody(Method method) {
		method.addBodyLine("return " + ConstKey.service_reference + ".queryListNoPage("
				+ method.getParameters().get(0).getName() + ");");

	}

}

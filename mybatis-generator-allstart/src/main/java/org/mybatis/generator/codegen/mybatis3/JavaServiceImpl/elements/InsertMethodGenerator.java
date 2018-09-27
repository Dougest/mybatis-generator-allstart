package org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements;

import org.mybatis.generator.api.ConstKey;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.AbstractJavaMethodGenerator;

public class InsertMethodGenerator extends AbstractJavaMethodGenerator {

	@Override
	public void addClassMethod(TopLevelClass topLevelClass) {
		Method method = new Method();
		method.setName(ConstKey.SERVICE_ADD);
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());

		FullyQualifiedJavaType modal = introspectedTable.getRules().calculateAllFieldsClass();
		// method.addParameter(new Parameter(parameterType, "record"));
		// $NON-NLS-1$
		FullyQualifiedJavaType string = FullyQualifiedJavaType.getStringInstance();
		method.addParameter(new Parameter(string, "data")); //$NON-NLS-1$

		// add body
		addMethodBody(method);
		topLevelClass.addImportedType(modal);
		topLevelClass.addMethod(method);

	}

	@Override
	protected void addMethodBody(Method method) {
		FullyQualifiedJavaType modal = introspectedTable.getRules().calculateAllFieldsClass();
		String modalname = modal.getBaseShortName();
		method.addBodyLine("JSONObject jsonObject = JSONObject.fromObject(" + paramName + ");");
		method.addBodyLine(modalname + " " + modalName + " = (" + modalname + ") JSONObject.toBean(jsonObject, "
				+ modalname + ".class);");
		method.addBodyLine("return " + ConstKey.service_reference + ".insertSelective(record);");
	}

}

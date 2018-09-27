package org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.ConstKey;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.AbstractJavaMethodGenerator;

public class UpdateMethodGenerator extends AbstractJavaMethodGenerator {

	@Override
	public void addClassMethod(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		FullyQualifiedJavaType modal = introspectedTable.getRules().calculateAllFieldsClass();
		// $NON-NLS-1$
		FullyQualifiedJavaType string = FullyQualifiedJavaType.getStringInstance();

		importedTypes.add(modal);

		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName(ConstKey.SERVICE_UPDATE);
		// add Return Type
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		// add Parameter
		method.addParameter(new Parameter(string, "data")); //$NON-NLS-1$
		// add body
		addMethodBody(method);
		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	@Override
	protected void addMethodBody(Method method) {
		FullyQualifiedJavaType modal = introspectedTable.getRules().calculateAllFieldsClass();
		String modalname = modal.getBaseShortName();
		method.addBodyLine("JSONObject jsonObject = JSONObject.fromObject(" + paramName + ");");
		method.addBodyLine(modalname + " " + modalName + " = (" + modalname + ") JSONObject.toBean(jsonObject, "
				+ modalname + ".class);");
		method.addBodyLine("return " + ConstKey.service_reference + ".updateByPrimaryKeySelective(record);");
	}

}

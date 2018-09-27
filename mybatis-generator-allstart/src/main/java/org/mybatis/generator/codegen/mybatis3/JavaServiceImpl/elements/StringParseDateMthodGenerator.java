package org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.AbstractJavaMethodGenerator;

public class StringParseDateMthodGenerator extends AbstractJavaMethodGenerator {

	@Override
	public void addClassMethod(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setName("stringParseDate");
		method.setVisibility(JavaVisibility.PRIVATE);
		// add returntype
		FullyQualifiedJavaType date = FullyQualifiedJavaType.getDateInstance();
		method.setReturnType(date);
		importedTypes.add(date);
		// add parameter
		FullyQualifiedJavaType string = FullyQualifiedJavaType.getStringInstance();
		method.addParameter(0, new Parameter(string, "dateStr"));
		method.addParameter(1, new Parameter(string, "pattern"));
		importedTypes.add(string);
		// add body
		addMethodBody(method);
		// class add method
		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	@Override
	protected void addMethodBody(Method method) {
		method.addBodyLine("if (dateStr == null || dateStr.length() < 1) {");
		method.addBodyLine("return new Date();");
		method.addBodyLine("} else {");
		method.addBodyLine("SimpleDateFormat sdf = new SimpleDateFormat(pattern);");
		method.addBodyLine("try {");
		method.addBodyLine("return sdf.parse(dateStr);");
		method.addBodyLine("} catch (ParseException arg3) {");
		method.addBodyLine("arg3.printStackTrace();");
		method.addBodyLine("return new Date();");
		method.addBodyLine("}");
		method.addBodyLine("}");
	}

}

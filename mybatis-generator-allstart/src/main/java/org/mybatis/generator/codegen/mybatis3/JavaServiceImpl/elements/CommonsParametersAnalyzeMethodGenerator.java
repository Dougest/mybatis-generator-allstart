package org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.AbstractJavaMethodGenerator;

public class CommonsParametersAnalyzeMethodGenerator extends AbstractJavaMethodGenerator {

	@Override
	public void addClassMethod(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setName("commonsAnalyze");
		method.setVisibility(JavaVisibility.PRIVATE);
		// add returntype
		FullyQualifiedJavaType map = FullyQualifiedJavaType.getNewMapInstance();
		map.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
		map.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
		method.setReturnType(map);
		importedTypes.add(map);
		// add parameter
		FullyQualifiedJavaType string = FullyQualifiedJavaType.getStringInstance();
		FullyQualifiedJavaType ints = FullyQualifiedJavaType.getIntInstance();

		method.addParameter(0, new Parameter(ints, "pageIndex"));
		method.addParameter(1, new Parameter(ints, "pageSize"));
		method.addParameter(2, new Parameter(string, "reqJson"));

		importedTypes.add(string);
		// add body
		addMethodBody(method);
		// class add method
		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	protected void addMethodBody(Method method) {
		method.addBodyLine("Map<String, Object> map = this.JsonStrTOMap(reqJson);");

		method.addJavaDocLine("// oracle");
		method.addBodyLine("String start = String.valueOf((pageIndex - 1) * pageSize + 1);");
		method.addBodyLine("String end = String.valueOf(pageIndex * pageSize);");

		method.addBodyLine("map.put(\"start\", start);");
		method.addBodyLine("map.put(\"end\", end);");

		method.addJavaDocLine("// mysql");
		method.addBodyLine("String offset = String.valueOf(pageIndex * pageSize);");
		method.addBodyLine("String limit = String.valueOf(pageSize);");
		method.addBodyLine("map.put(\"offset\", offset);");
		method.addBodyLine("map.put(\"limit\", limit);");

		method.addBodyLine("");
		method.addBodyLine("String time;");
		method.addBodyLine("Date date;");
		method.addBodyLine("");
		method.addBodyLine("if (map.get(\"startTime\") != null) {");
		method.addBodyLine("	time = map.get(\"startTime\").toString();");
		method.addBodyLine("	if (time.length() > 0) {");
		method.addBodyLine("	time = time.replaceAll(\"T\", \" \");");
		method.addBodyLine("date = this.stringParseDate(time, \"yyyy-MM-dd HH:mm:ss\");");
		method.addBodyLine("	map.put(\"startTime\", date);");
		method.addBodyLine("}else map.put(\"startTime\",null);");
		method.addBodyLine("");
		method.addBodyLine("}");
		method.addBodyLine("if (map.get(\"endTime\") != null){");
		method.addBodyLine("	time = map.get(\"endTime\").toString();");
		method.addBodyLine("	if (time.length() > 0) {");
		method.addBodyLine("		time = time.replaceAll(\"T\", \" \");");
		method.addBodyLine("		date = this.stringParseDate(time, \"yyyy-MM-dd HH:mm:ss\");");
		method.addBodyLine("			map.put(\"endTime\", date);");
		method.addBodyLine("		} else");
		method.addBodyLine("			map.put(\"endTime\", null);");
		method.addBodyLine("	}");
		method.addBodyLine("	return map;");
	}
}

package org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.AbstractJavaMethodGenerator;

public class JsonStrTOMapMethodGenerator extends AbstractJavaMethodGenerator {

	@Override
	public void addClassMethod(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setName("JsonStrTOMap");
		method.setVisibility(JavaVisibility.PRIVATE);
		// add returntype
		FullyQualifiedJavaType map = FullyQualifiedJavaType.getNewMapInstance();
		map.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
		map.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
		method.setReturnType(map);
		importedTypes.add(map);
		// add parameter
		FullyQualifiedJavaType string = FullyQualifiedJavaType.getStringInstance();
		method.addParameter(0, new Parameter(string, "jsonStr"));
		importedTypes.add(string);
		// add body
		addMethodBody(method);
		// class add method
		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	@Override
	protected void addMethodBody(Method method) {
		method.addBodyLine("Map<String, Object> map = new HashMap<String, Object>();");
		method.addBodyLine("if (jsonStr == null || jsonStr.length() < 1)");
		method.addBodyLine("return map;");
		method.addBodyLine("else {");
		method.addBodyLine("JSONObject json = JSONObject.fromObject(jsonStr);");
		method.addBodyLine("if (json == null || json.isEmpty())");
		method.addBodyLine("return map;");
		method.addBodyLine("@SuppressWarnings(\"unchecked\")");

		method.addBodyLine("Set<Map.Entry<String, Object>> sets = json.entrySet();");
		method.addBodyLine("Iterator<Map.Entry<String, Object>> itera = sets.iterator();");
		method.addBodyLine("while (itera.hasNext()) {");
		method.addBodyLine("Entry<String, Object> entry = itera.next();");
		method.addBodyLine("String key = (String) entry.getKey();");
		method.addBodyLine("Object value = entry.getValue();");
		method.addBodyLine("map.put(key, value);");
		method.addBodyLine("}");
		method.addBodyLine("return map;");
		method.addBodyLine("}");
	}

}

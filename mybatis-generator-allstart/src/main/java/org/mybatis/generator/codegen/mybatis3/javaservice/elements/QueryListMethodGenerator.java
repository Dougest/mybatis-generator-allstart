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

public class QueryListMethodGenerator extends AbstractJavaMapperMethodGenerator {

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Method method = new Method();
		method.setName(ConstKey.SERVICE_OLIST);
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		method.setVisibility(JavaVisibility.PUBLIC);

		FullyQualifiedJavaType list = FullyQualifiedJavaType.getNewListInstance();
		list.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());
		method.setReturnType(list);

		FullyQualifiedJavaType param = FullyQualifiedJavaType.getNewMapInstance();
		param.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
		param.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
		Parameter parameter = new Parameter(param, "map");
		method.addParameter(parameter);
		importedTypes.add(param);

		importedTypes.add(list);

		interfaze.addMethod(method);
		interfaze.addImportedTypes(importedTypes);
	}

}

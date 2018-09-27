package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class CountAllMethodGenerator extends AbstractJavaMapperMethodGenerator {

	public CountAllMethodGenerator() {
		super();
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getIntInstance());
		method.setName("countList");

		FullyQualifiedJavaType param = FullyQualifiedJavaType.getNewMapInstance();
		param.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
		param.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());

		importedTypes.add(param);

		Parameter parameter = new Parameter(param, "map");
		method.addParameter(parameter);

		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
		interfaze.addImportedTypes(importedTypes);
		interfaze.addMethod(method);

	}

}

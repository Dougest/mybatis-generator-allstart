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

public class CommonListMethodGenerator extends AbstractJavaMapperMethodGenerator {

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);
		FullyQualifiedJavaType string = FullyQualifiedJavaType.getStringInstance();
		method.setReturnType(string);
		method.setName(ConstKey.SERVICE_LIST);

		FullyQualifiedJavaType ints = FullyQualifiedJavaType.getIntInstance();
		method.addParameter(0, new Parameter(ints, "pageIndex"));
		method.addParameter(1, new Parameter(ints, "pageSize"));
		method.addParameter(2, new Parameter(string, "data"));

		importedTypes.add(string);

		interfaze.addMethod(method);
		interfaze.addImportedTypes(importedTypes);
	}

}

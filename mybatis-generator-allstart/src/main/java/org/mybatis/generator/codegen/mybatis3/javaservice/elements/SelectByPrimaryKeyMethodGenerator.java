package org.mybatis.generator.codegen.mybatis3.javaservice.elements;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.ConstKey;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

public class SelectByPrimaryKeyMethodGenerator extends AbstractJavaMapperMethodGenerator {

	private boolean isSimple;

	public SelectByPrimaryKeyMethodGenerator(boolean isSimple) {
		super();
		this.isSimple = isSimple;
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);

		FullyQualifiedJavaType returnType = introspectedTable.getRules().calculateAllFieldsClass();
		method.setReturnType(returnType);
		importedTypes.add(returnType);

		method.setName(ConstKey.SERVICE_INFO);

		if (!isSimple && introspectedTable.getRules().generatePrimaryKeyClass()) {
			FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getPrimaryKeyType());
			importedTypes.add(type);
			method.addParameter(new Parameter(type, "key")); //$NON-NLS-1$
		} else {
			// no primary key class - fields are in the base class
			// if more than one PK field, then we need to annotate the
			// parameters
			// for MyBatis3
			List<IntrospectedColumn> introspectedColumns = introspectedTable.getPrimaryKeyColumns();
			boolean annotate = introspectedColumns.size() > 1;
			if (annotate) {
				importedTypes.add(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param")); //$NON-NLS-1$
			}
			StringBuilder sb = new StringBuilder();
			for (IntrospectedColumn introspectedColumn : introspectedColumns) {
				FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
				importedTypes.add(type);
				Parameter parameter = new Parameter(type, introspectedColumn.getJavaProperty());
				if (annotate) {
					sb.setLength(0);
					sb.append("@Param(\""); //$NON-NLS-1$
					sb.append(introspectedColumn.getJavaProperty());
					sb.append("\")"); //$NON-NLS-1$
					parameter.addAnnotation(sb.toString());
				}
				method.addParameter(parameter);
			}
		}

		addMapperAnnotations(interfaze, method);

		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

		if (context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable)) {
			interfaze.addImportedTypes(importedTypes);
			interfaze.addMethod(method);
		}
	}

	public void addMapperAnnotations(Interface interfaze, Method method) {
		return;
	}
}

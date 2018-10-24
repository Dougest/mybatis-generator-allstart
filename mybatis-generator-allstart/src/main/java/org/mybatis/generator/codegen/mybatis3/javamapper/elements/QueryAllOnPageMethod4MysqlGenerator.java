package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class QueryAllOnPageMethod4MysqlGenerator extends AbstractJavaMapperMethodGenerator {

	@Override
	public void addInterfaceElements(Interface interfaze) {

		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		Method method = new Method();

		method.addJavaDocLine("/** is suit for mysql **/");

		method.setName("queryList");

		FullyQualifiedJavaType param = FullyQualifiedJavaType.getNewMapInstance();
		FullyQualifiedJavaType intType = FullyQualifiedJavaType.getIntInstance();
		param.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
		param.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
		importedTypes.add(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param")); //$NON-NLS-1$

		Parameter map = new Parameter(param, "params");
		StringBuilder annotation = new StringBuilder();
		annotation.append("@Param(\""); //$NON-NLS-1$
		annotation.append("params");
		annotation.append("\")"); //$NON-NLS-1$
		map.addAnnotation(annotation.toString());
		method.addParameter(map);
		importedTypes.add(param);

		Parameter offset = new Parameter(intType, "offset");
		annotation.setLength(0);
		annotation.append("@Param(\""); //$NON-NLS-1$
		annotation.append("offset");
		annotation.append("\")"); //$NON-NLS-1$
		offset.addAnnotation(annotation.toString());
		method.addParameter(offset);
		importedTypes.add(intType);

		Parameter limit = new Parameter(intType, "limit");
		annotation.setLength(0);
		annotation.append("@Param(\""); //$NON-NLS-1$
		annotation.append("limit");
		annotation.append("\")"); //$NON-NLS-1$
		limit.addAnnotation(annotation.toString());
		method.addParameter(limit);
		// importedTypes.add(param);

		FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
		FullyQualifiedJavaType entity = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		returnType.addTypeArgument(entity);

		method.setReturnType(returnType);
		method.setVisibility(JavaVisibility.PUBLIC);

		importedTypes.add(returnType);
		importedTypes.add(entity);

		interfaze.addMethod(method);
		interfaze.addImportedTypes(importedTypes);

	}

}

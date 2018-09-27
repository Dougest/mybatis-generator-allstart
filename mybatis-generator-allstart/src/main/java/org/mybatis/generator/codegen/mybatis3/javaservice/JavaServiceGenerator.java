package org.mybatis.generator.codegen.mybatis3.javaservice;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.ConstKey;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.base.core.StringUtils;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javaservice.elements.CommonListMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javaservice.elements.DeleteByPrimaryKeyMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javaservice.elements.InsertMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javaservice.elements.QueryListMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javaservice.elements.SelectByPrimaryKeyMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.javaservice.elements.UpdateByPrimaryKeySelectiveMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PropertyRegistry;

public class JavaServiceGenerator extends AbstractJavaClientGenerator {

	public void setContext(Context context) {
		this.context = context;
	}

	public void setContext(IntrospectedTable introspectedTable) {
		this.introspectedTable = introspectedTable;
	}

	public JavaServiceGenerator() {
		super(true);
	}

	public JavaServiceGenerator(boolean requiresXMLGenerator) {
		super(requiresXMLGenerator);
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		// 获取注释生成器
		CommentGenerator commentGenerator = context.getCommentGenerator();
		// 获取modal
		FullyQualifiedJavaType genericType = introspectedTable.getRules().calculateAllFieldsClass();
		// 获取接口
		FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
		// 接口名
		type.setBaseShortName(genericType.getBaseShortName() + "Service");

		Interface interfaze = new Interface(type);
		interfaze.addImportedType(genericType);

		String baseservice = context.getJavaClientGeneratorConfiguration()
				.getProperty(PropertyRegistry.JAVA_TYPE_SUPER_SERVICE);
		String localtion = context.getJavaClientGeneratorConfiguration()
				.getProperty(PropertyRegistry.JAVA_TYPE_SERVICE_LOCATION);

		if (StringUtils.isBlank(localtion)) {
			System.err.println("the generator.xml " + PropertyRegistry.JAVA_TYPE_SERVICE_LOCATION
					+ "tag is wrong or null , can not generate the code for [Service] ");
			return null;
		}

		if (!StringUtils.isBlank(localtion)) {
			type.setPackageName(localtion);
		}
		interfaze.setVisibility(JavaVisibility.PUBLIC);
		if (!StringUtils.isBlank(baseservice)) {
			FullyQualifiedJavaType superInterface = FullyQualifiedJavaType.getNewInterface(baseservice);
			superInterface.addTypeArgument(genericType);
			interfaze.addSuperInterface(superInterface);
			interfaze.addImportedType(superInterface);
		} else {
			addDeleteByPrimaryKeyMethod(interfaze);
			addInsertMethod(interfaze);
			addSelectByPrimaryKeyMethod(interfaze);
			addUpdateByPrimaryKeyMethod(interfaze);
			addCommonListMethod(interfaze);
			addQueryListMethod(interfaze);
		}

		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
		if (context.getPlugins().clientGenerated(interfaze, null, introspectedTable)) {
			answer.add(interfaze);
		}

		List<CompilationUnit> extraCompilationUnits = getExtraCompilationUnits();
		if (extraCompilationUnits != null) {
			answer.addAll(extraCompilationUnits);
		}
		commentGenerator.addJavaFileComment(interfaze);

		// print
		if (ConstKey.isPrint) {
			System.err.println("\n");
			System.err.println("Java service start ...");
			System.err.println("\n");
			System.out.println(interfaze.getFormattedContent());
		}

		return answer;
	}

	private void addCommonListMethod(Interface interfaze) {
		AbstractJavaMapperMethodGenerator methodGenerator = new CommonListMethodGenerator();
		initializeAndExecuteGenerator(methodGenerator, interfaze);
	}

	private void addQueryListMethod(Interface interfaze) {
		AbstractJavaMapperMethodGenerator methodGenerator = new QueryListMethodGenerator();
		initializeAndExecuteGenerator(methodGenerator, interfaze);
	}

	private void addDeleteByPrimaryKeyMethod(Interface interfaze) {
		AbstractJavaMapperMethodGenerator methodGenerator = new DeleteByPrimaryKeyMethodGenerator(false);
		initializeAndExecuteGenerator(methodGenerator, interfaze);
	}

	private void addInsertMethod(Interface interfaze) {
		AbstractJavaMapperMethodGenerator methodGenerator = new InsertMethodGenerator(false);
		initializeAndExecuteGenerator(methodGenerator, interfaze);
	}

	private void addSelectByPrimaryKeyMethod(Interface interfaze) {
		AbstractJavaMapperMethodGenerator methodGenerator = new SelectByPrimaryKeyMethodGenerator(false);
		initializeAndExecuteGenerator(methodGenerator, interfaze);
	}

	private void addUpdateByPrimaryKeyMethod(Interface interfaze) {
		AbstractJavaMapperMethodGenerator methodGenerator = new UpdateByPrimaryKeySelectiveMethodGenerator();
		initializeAndExecuteGenerator(methodGenerator, interfaze);
	}

	protected void initializeAndExecuteGenerator(AbstractJavaMapperMethodGenerator methodGenerator,
			Interface interfaze) {
		methodGenerator.setContext(context);
		methodGenerator.setIntrospectedTable(introspectedTable);
		methodGenerator.setProgressCallback(progressCallback);
		methodGenerator.setWarnings(warnings);
		methodGenerator.addInterfaceElements(interfaze);
	}

	public List<CompilationUnit> getExtraCompilationUnits() {
		return null;
	}

	@Override
	public AbstractXmlGenerator getMatchedXMLGenerator() {
		return new XMLMapperGenerator();
	}

}

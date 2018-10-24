package org.mybatis.generator.codegen.mybatis3.JavaServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.ConstKey;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.base.core.StringUtils;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements.CommonsParametersAnalyzeMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements.CommontListMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements.DeleteMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements.InfoMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements.InsertMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements.JsonStrTOMapMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements.QueryMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements.StringParseDateMthodGenerator;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements.UpdateMethodGenerator;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PropertyRegistry;

public class JavaServiceImplGenerator extends AbstractJavaGenerator {

	public void setContext(Context context) {
		this.context = context;
	}

	public void setContext(IntrospectedTable introspectedTable) {
		this.introspectedTable = introspectedTable;
	}

	public JavaServiceImplGenerator() {
		super();
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		CommentGenerator commentGenerator = context.getCommentGenerator();
		String servicelocation = context.getJavaClientGeneratorConfiguration()
				.getProperty(PropertyRegistry.JAVA_TYPE_SERVICE_LOCATION);
		String baseserviceImpl = context.getJavaClientGeneratorConfiguration()
				.getProperty(PropertyRegistry.JAVA_TYPE_SUPER_SERVICE_IMPL);
		String localtion = context.getJavaClientGeneratorConfiguration()
				.getProperty(PropertyRegistry.JAVA_TYPE_SERVICE_IMPL_LOCATION);

		if (StringUtils.isBlank(localtion)) {
			System.err.println("the generator.xml " + PropertyRegistry.JAVA_TYPE_SERVICE_LOCATION
					+ "tag is wrong or null,can not generate the code for [ServiceImpl] ");
			return null;
		}
		// super class
		FullyQualifiedJavaType modal = introspectedTable.getRules().calculateAllFieldsClass();
		FullyQualifiedJavaType service = FullyQualifiedJavaType.getNewInterface(modal + "Service");
		service.setPackageName(servicelocation);
		// this type
		FullyQualifiedJavaType thisType = new FullyQualifiedJavaType(service.getBaseShortName() + "Impl");
		thisType.setPackageName(localtion);
		TopLevelClass topLevelClass = new TopLevelClass(thisType);
		// add annotation
		FullyQualifiedJavaType annotation = new FullyQualifiedJavaType("org.springframework.stereotype.Service");
		topLevelClass.addAnnotation("@Service");
		topLevelClass.addImportedType(annotation);

		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		commentGenerator.addJavaFileComment(topLevelClass);
		topLevelClass.addSuperInterface(service);
		topLevelClass.addImportedType(service);
		boolean isBaseServiceImpl = false;
		if (!StringUtils.isBlank(baseserviceImpl)) {
			FullyQualifiedJavaType baseServiceImpl = FullyQualifiedJavaType.getNewInterface(baseserviceImpl);
			FullyQualifiedJavaType genericType = introspectedTable.getRules().calculateAllFieldsClass();
			baseServiceImpl.addTypeArgument(genericType);

			topLevelClass.addImportedType(genericType);
			topLevelClass.setSuperClass(baseServiceImpl);
			topLevelClass.addImportedType(baseServiceImpl);
			isBaseServiceImpl = true;

		} else {
			addCommonsParametersAnalyzeMethod(topLevelClass);
			addJsonStrTOMapMethod(topLevelClass);
			addStringParseDateMthod(topLevelClass);

			topLevelClass.addImportedType(FullyQualifiedJavaType.getNewInterface("java.util.Map.Entry"));
			topLevelClass.addImportedType(FullyQualifiedJavaType.getNewInterface("java.util.Set"));
			topLevelClass.addImportedType(FullyQualifiedJavaType.getNewInterface("java.text.ParseException"));
			topLevelClass.addImportedType(FullyQualifiedJavaType.getNewInterface("java.text.SimpleDateFormat"));
			topLevelClass.addImportedType(FullyQualifiedJavaType.getNewIteratorInstance());
			topLevelClass.addImportedType(FullyQualifiedJavaType.getNewHashMapInstance());
		}
		topLevelClass.addImportedType(FullyQualifiedJavaType.getNewInterface("net.sf.json.JSONObject"));
		FullyQualifiedJavaType mapper = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());

		Field field = new Field();
		FullyQualifiedJavaType annotationType = new FullyQualifiedJavaType(
				"org.springframework.beans.factory.annotation.Autowired");
		field.addAnnotation("@Autowired");
		field.setType(mapper);
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setName(ConstKey.service_reference);

		topLevelClass.addField(field);

		topLevelClass.addImportedType(mapper);
		topLevelClass.addImportedType(annotationType);

		addInsertMethod(topLevelClass);
		addInfoMethod(topLevelClass);
		addUpdateMethod(topLevelClass);
		addDeleteMethod(topLevelClass);
		addCommontListMethod(topLevelClass, isBaseServiceImpl);
		addQueryMethod(topLevelClass);

		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
		answer.add(topLevelClass);

		// print
		if (ConstKey.isPrint) {
			System.err.println("\n");
			System.err.println("Java service Impl start ...");
			System.err.println("\n");
			System.out.println(topLevelClass.getFormattedContent());
		}

		return answer;
	}

	private void addInsertMethod(TopLevelClass topLevelClass) {
		AbstractJavaMethodGenerator generator = new InsertMethodGenerator();
		generator.setContext(context);
		generator.setIntrospectedTable(introspectedTable);
		generator.addClassMethod(topLevelClass);
	}

	private void addInfoMethod(TopLevelClass topLevelClass) {
		AbstractJavaMethodGenerator generator = new InfoMethodGenerator();
		generator.setContext(context);
		generator.setIntrospectedTable(introspectedTable);
		generator.addClassMethod(topLevelClass);
	}

	private void addDeleteMethod(TopLevelClass topLevelClass) {
		AbstractJavaMethodGenerator generator = new DeleteMethodGenerator();
		generator.setContext(context);
		generator.setIntrospectedTable(introspectedTable);
		generator.addClassMethod(topLevelClass);
	}

	private void addUpdateMethod(TopLevelClass topLevelClass) {
		AbstractJavaMethodGenerator generator = new UpdateMethodGenerator();
		generator.setContext(context);
		generator.setIntrospectedTable(introspectedTable);
		generator.addClassMethod(topLevelClass);

	}

	private void addCommontListMethod(TopLevelClass topLevelClass, boolean isBaseServiceImpl) {
		AbstractJavaMethodGenerator generator = new CommontListMethodGenerator(isBaseServiceImpl);
		generator.setContext(context);
		generator.setIntrospectedTable(introspectedTable);
		generator.addClassMethod(topLevelClass);

	}

	private void addQueryMethod(TopLevelClass topLevelClass) {
		AbstractJavaMethodGenerator generator = new QueryMethodGenerator();
		generator.setContext(context);
		generator.setIntrospectedTable(introspectedTable);
		generator.addClassMethod(topLevelClass);
	}

	private void addCommonsParametersAnalyzeMethod(TopLevelClass topLevelClass) {
		AbstractJavaMethodGenerator generator = new CommonsParametersAnalyzeMethodGenerator();
		generator.setContext(context);
		generator.setIntrospectedTable(introspectedTable);
		generator.addClassMethod(topLevelClass);
	}

	private void addJsonStrTOMapMethod(TopLevelClass topLevelClass) {
		AbstractJavaMethodGenerator generator = new JsonStrTOMapMethodGenerator();
		generator.setContext(context);
		generator.setIntrospectedTable(introspectedTable);
		generator.addClassMethod(topLevelClass);
	}

	private void addStringParseDateMthod(TopLevelClass topLevelClass) {
		AbstractJavaMethodGenerator generator = new StringParseDateMthodGenerator();
		generator.setContext(context);
		generator.setIntrospectedTable(introspectedTable);
		generator.addClassMethod(topLevelClass);
	}

}

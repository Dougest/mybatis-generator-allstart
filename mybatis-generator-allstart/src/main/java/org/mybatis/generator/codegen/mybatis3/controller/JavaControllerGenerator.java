package org.mybatis.generator.codegen.mybatis3.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.ConstKey;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.base.core.StringUtils;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PropertyRegistry;

public class JavaControllerGenerator extends AbstractJavaGenerator {
	private static final String reference = "service";

	private static final String paramName = "data";

	private static Boolean isBaseControl = false;

	public void setContext(Context context) {
		this.context = context;
	}

	public void setContext(IntrospectedTable introspectedTable) {
		this.introspectedTable = introspectedTable;
	}

	public JavaControllerGenerator() {
		super();
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		CommentGenerator commentGenerator = context.getCommentGenerator();
		String servicelocation = context.getJavaClientGeneratorConfiguration()
				.getProperty(PropertyRegistry.JAVA_TYPE_SERVICE_LOCATION);
		String baseContrller = context.getJavaClientGeneratorConfiguration()
				.getProperty(PropertyRegistry.JAVA_TYPE_SUPER_CONTROLLER);
		String localtion = context.getJavaClientGeneratorConfiguration()
				.getProperty(PropertyRegistry.JAVA_TYPE_CONTROLLER_LOCATION);

		if (StringUtils.isBlank(localtion)) {
			System.err.println("the generator.xml " + PropertyRegistry.JAVA_TYPE_SERVICE_LOCATION
					+ "tag is wrong or null , can not generate the code for [Controller] ");
			return null;
		}

		FullyQualifiedJavaType modal = introspectedTable.getRules().calculateAllFieldsClass();
		FullyQualifiedJavaType service = FullyQualifiedJavaType
				.getNewInterface(servicelocation + "." + modal.getBaseShortName() + "Service");

		service.setPackageName(servicelocation);

		FullyQualifiedJavaType thisType = new FullyQualifiedJavaType(modal + "Controller");
		thisType.setPackageName(localtion);
		TopLevelClass topLevelClass = new TopLevelClass(thisType);

		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		commentGenerator.addJavaFileComment(topLevelClass);

		if (!StringUtils.isBlank(baseContrller)) {
			FullyQualifiedJavaType basecontrller = FullyQualifiedJavaType.getNewInterface(baseContrller);
			FullyQualifiedJavaType genericType = introspectedTable.getRules().calculateAllFieldsClass();
			basecontrller.addTypeArgument(genericType);

			topLevelClass.addImportedType(genericType);
			topLevelClass.setSuperClass(basecontrller);
			topLevelClass.addImportedType(basecontrller);

			isBaseControl = true;
		}

		// add @Controller annotation
		FullyQualifiedJavaType annotationController = new FullyQualifiedJavaType(
				"org.springframework.stereotype.Controller");
		topLevelClass.addAnnotation("@Controller");
		topLevelClass.addImportedType(annotationController);
		// add @RequestMapping annotation
		String lowertable = modal.getBaseShortName();
		lowertable = lowertable.toLowerCase();
		FullyQualifiedJavaType annotationRequestMapping = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.RequestMapping");
		topLevelClass.addAnnotation("@RequestMapping(\"" + lowertable + "\")");
		topLevelClass.addImportedType(annotationRequestMapping);

		Field field = new Field();
		FullyQualifiedJavaType annotationType = new FullyQualifiedJavaType(
				"org.springframework.beans.factory.annotation.Autowired");

		field.addAnnotation("@Autowired");
		field.setType(service);
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setName(reference);

		topLevelClass.addField(field);
		topLevelClass.addImportedType(annotationType);
		topLevelClass.addImportedType(service);
		topLevelClass.addImportedType(FullyQualifiedJavaType.getStringInstance());

		addInsertMethod(topLevelClass);
		addInfoMethod(topLevelClass);
		addUpdateMethod(topLevelClass);
		addDeleteMethod(topLevelClass);
		addCommontListMethod(topLevelClass);

		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
		answer.add(topLevelClass);

		// print
		if (ConstKey.isPrint) {
			System.err.println("\n");
			System.err.println("Java controller start ...");
			System.err.println("\n");
			System.out.println(topLevelClass.getFormattedContent());
		}

		return answer;
	}

	public void addInsertMethod(TopLevelClass topLevelClass) {
		Method method = new Method();
		// add annotation
		FullyQualifiedJavaType annotationRequestMapping = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.RequestMapping");
		method.addAnnotation("@RequestMapping(\"" + ConstKey.PARAM_ADD + "\")");
		topLevelClass.addImportedType(annotationRequestMapping);
		// add annotation
		FullyQualifiedJavaType annotationResponseBody = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.ResponseBody");
		method.addAnnotation("@ResponseBody");
		topLevelClass.addImportedType(annotationResponseBody);
		// add name
		method.setName(ConstKey.SERVICE_ADD);
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getObjectInstance());
		// add parameter
		FullyQualifiedJavaType string = FullyQualifiedJavaType.getStringInstance();
		method.addParameter(new Parameter(string, paramName)); // $NON-NLS-1$
		// add body
		method.addBodyLine("try {");
		method.addBodyLine(reference + "." + ConstKey.SERVICE_ADD + "(" + paramName + ");");
		if (isBaseControl) {
			method.addBodyLine("return RESULT_OPER_SUCCESS;");
			method.addBodyLine("} catch(RuntimeException e) {");
			method.addBodyLine("e.printStackTrace();");
			method.addBodyLine("return RESULT_OPER_FAUILTRUE;");
			method.addBodyLine("}");
		} else {
			method.addBodyLine(ConstKey.returnSuccessData());
			method.addBodyLine("} catch(RuntimeException e) {");
			method.addBodyLine("e.printStackTrace();");
			method.addBodyLine(ConstKey.returnFauilreData());
			method.addBodyLine("}");
		}

		topLevelClass.addMethod(method);
	}

	public void addInfoMethod(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		// add annotation
		FullyQualifiedJavaType annotationRequestMapping = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.RequestMapping");
		method.addAnnotation("@RequestMapping(\"" + ConstKey.PARAM_INFO + "\")");
		topLevelClass.addImportedType(annotationRequestMapping);
		// add annotation
		FullyQualifiedJavaType annotationResponseBody = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.ResponseBody");
		method.addAnnotation("@ResponseBody");
		topLevelClass.addImportedType(annotationResponseBody);

		method.setReturnType(FullyQualifiedJavaType.getObjectInstance());
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName(ConstKey.SERVICE_INFO);

		List<IntrospectedColumn> introspectedColumns = introspectedTable.getPrimaryKeyColumns();
		Parameter parameter = null;
		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
			importedTypes.add(type);
			parameter = new Parameter(type, introspectedColumn.getJavaProperty());
			method.addParameter(parameter);
		}
		FullyQualifiedJavaType modal = introspectedTable.getRules().calculateAllFieldsClass();
		method.addBodyLine("try {");
		String modalName = modal.getBaseShortName();
		method.addBodyLine(
				modalName + " record = " + reference + "." + ConstKey.SERVICE_INFO + "(" + parameter.getName() + ");");

		method.addBodyLine("return record;");
		method.addBodyLine("} catch(RuntimeException e) {");
		method.addBodyLine("e.printStackTrace();");
		if (isBaseControl) {
			method.addBodyLine("return RESULT_OBJ_NULL;");
		} else {
			method.addBodyLine("return \"{}\";");
		}

		// method.addBodyLine(ConstKey.returnFauilreData("null"));
		method.addBodyLine("}");
		topLevelClass.addImportedType(modal);
		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	public void addDeleteMethod(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		// add annotation
		FullyQualifiedJavaType annotationRequestMapping = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.RequestMapping");
		method.addAnnotation("@RequestMapping(\"" + ConstKey.PARAM_DELETE + "\")");
		topLevelClass.addImportedType(annotationRequestMapping);
		// add annotation
		FullyQualifiedJavaType annotationResponseBody = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.ResponseBody");
		method.addAnnotation("@ResponseBody");
		topLevelClass.addImportedType(annotationResponseBody);

		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getObjectInstance());
		method.setName(ConstKey.SERVICE_DELETE);

		Parameter parameter = null;
		List<IntrospectedColumn> introspectedColumns = introspectedTable.getPrimaryKeyColumns();
		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
			importedTypes.add(type);
			parameter = new Parameter(type, introspectedColumn.getJavaProperty());
			method.addParameter(parameter);
		}
		method.addBodyLine("try {");
		method.addBodyLine(reference + "." + ConstKey.SERVICE_DELETE + "(" + parameter.getName() + ");");
		if (isBaseControl) {
			method.addBodyLine("return RESULT_OPER_SUCCESS;");
		} else {
			method.addBodyLine(ConstKey.returnSuccessData());
		}
		method.addBodyLine("} catch(RuntimeException e) {");
		method.addBodyLine("e.printStackTrace();");
		if (isBaseControl) {
			method.addBodyLine("return RESULT_OPER_FAUILTRUE;");
		} else {
			method.addBodyLine(ConstKey.returnFauilreData());
		}
		method.addBodyLine("}");
		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	public void addUpdateMethod(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		Method method = new Method();
		// add annotation
		FullyQualifiedJavaType annotationRequestMapping = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.RequestMapping");
		method.addAnnotation("@RequestMapping(\"" + ConstKey.PARAM_UPDATE + "\")");
		topLevelClass.addImportedType(annotationRequestMapping);
		// add annotation
		FullyQualifiedJavaType annotationResponseBody = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.ResponseBody");
		method.addAnnotation("@ResponseBody");
		topLevelClass.addImportedType(annotationResponseBody);

		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getObjectInstance());
		method.setName(ConstKey.SERVICE_UPDATE);
		FullyQualifiedJavaType string = FullyQualifiedJavaType.getStringInstance();
		method.addParameter(new Parameter(string, paramName)); // $NON-NLS-1$
		// method.addParameter(new Parameter(parameterType, "record"));
		// //$NON-NLS-1$

		// context.getCommentGenerator().addGeneralMethodComment(method,
		// introspectedTable);
		method.addBodyLine("try {");
		method.addBodyLine(reference + "." + ConstKey.SERVICE_UPDATE + "(" + paramName + ");");
		if (isBaseControl) {
			method.addBodyLine("return RESULT_OPER_SUCCESS;");
		} else {
			method.addBodyLine(ConstKey.returnSuccessData());
		}
		// method.addBodyLine(ConstKey.returnSuccessData());
		method.addBodyLine("} catch(RuntimeException e) {");
		method.addBodyLine("e.printStackTrace();");
		if (isBaseControl) {
			method.addBodyLine("return RESULT_OPER_FAUILTRUE;");
		} else {
			method.addBodyLine(ConstKey.returnFauilreData());
		}
		method.addBodyLine("}");

		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	public void addCommontListMethod(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		// add annotation
		FullyQualifiedJavaType annotationRequestMapping = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.RequestMapping");
		method.addAnnotation("@RequestMapping(\"" + ConstKey.PARAM_LIST + "\")");
		topLevelClass.addImportedType(annotationRequestMapping);
		// add annotation
		FullyQualifiedJavaType annotationResponseBody = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.ResponseBody");
		method.addAnnotation("@ResponseBody");
		topLevelClass.addImportedType(annotationResponseBody);

		method.setVisibility(JavaVisibility.PUBLIC);
		FullyQualifiedJavaType string = FullyQualifiedJavaType.getStringInstance();
		method.setReturnType(FullyQualifiedJavaType.getObjectInstance());
		method.setName(ConstKey.SERVICE_LIST);

		FullyQualifiedJavaType ints = FullyQualifiedJavaType.getIntInstance();
		method.addParameter(0, new Parameter(ints, "pageIndex"));
		method.addParameter(1, new Parameter(ints, "pageSize"));
		method.addParameter(2, new Parameter(string, "data"));

		StringBuffer sb = new StringBuffer();
		List<Parameter> ps = method.getParameters();
		for (Parameter p : ps) {
			sb.append(p.getName());
			sb.append(",");
		}
		sb.setLength(sb.length() - 1);
		// add body
		method.addBodyLine("try {");
		method.addBodyLine("return " + reference + "." + ConstKey.SERVICE_LIST + "(" + sb + ");");
		method.addBodyLine("} catch(RuntimeException e) {");
		method.addBodyLine("e.printStackTrace();");

		if (isBaseControl) {
			method.addBodyLine("return RESULT_LIST_NULL;");
		} else {
			method.addBodyLine("return \"" + ConstKey.RESULT_LIST_NULL + "\";");
		}

		method.addBodyLine("}");

		importedTypes.add(string);

		topLevelClass.addMethod(method);
		topLevelClass.addImportedTypes(importedTypes);
	}

	public void addQueryMethod(TopLevelClass topLevelClass) {
		Method method = new Method();
		// add annotation
		FullyQualifiedJavaType annotationRequestMapping = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.RequestMapping");
		method.addAnnotation("@RequestMapping(\"" + ConstKey.PARAM_LIST_NO_PAGE + "\")");
		topLevelClass.addImportedType(annotationRequestMapping);
		// add annotation
		FullyQualifiedJavaType annotationResponseBody = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.ResponseBody");
		method.addAnnotation("@ResponseBody");
		topLevelClass.addImportedType(annotationResponseBody);

		method.setName(ConstKey.SERVICE_OLIST);
		method.setVisibility(JavaVisibility.PUBLIC);
		// add return type
		FullyQualifiedJavaType list = FullyQualifiedJavaType.getNewListInstance();
		list.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());
		method.setReturnType(FullyQualifiedJavaType.getObjectInstance());
		// add parameter
		FullyQualifiedJavaType param = FullyQualifiedJavaType.getNewMapInstance();
		param.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
		param.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
		Parameter parameter = new Parameter(param, "map");
		method.addParameter(parameter);
		// add body
		method.addBodyLine("try {");
		method.addBodyLine("return " + reference + "." + ConstKey.SERVICE_OLIST + "(" + parameter.getName() + ");");
		method.addBodyLine("} catch(RuntimeException e) {");
		method.addBodyLine("e.printStackTrace();");

		if (isBaseControl) {
			method.addBodyLine("return RESULT_ARRAY_NULL;");
		} else {
			method.addBodyLine("return \"" + ConstKey.RESULT_ARRAY_NULL + "\";");
		}

		// method.addBodyLine("return \"" + ConstKey.RESULT_ARRAY_NULL + "\";");
		method.addBodyLine("}");

		topLevelClass.addImportedType(param);
		topLevelClass.addImportedType(list);

		topLevelClass.addMethod(method);
	}

}
package org.mybatis.generator.codegen.mybatis3.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.ConstKey;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.base.core.StringUtils;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.PropertyRegistry;

public class JavaControllerGenerator extends AbstractJavaGenerator {
	private static final String reference = "service";

	private static final String paramName = "data";

	private static Boolean isBaseControl = false;

	public void setContext(Context context) {
		this.context = context;
	}

	public void setContext(IntrospectedTable introspectedTable) {
		this.introspectedTable = introspectedTable;
	}

	public JavaControllerGenerator() {
		super();
	}

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		CommentGenerator commentGenerator = context.getCommentGenerator();
		String servicelocation = context.getJavaClientGeneratorConfiguration()
				.getProperty(PropertyRegistry.JAVA_TYPE_SERVICE_LOCATION);
		String baseContrller = context.getJavaClientGeneratorConfiguration()
				.getProperty(PropertyRegistry.JAVA_TYPE_SUPER_CONTROLLER);
		String localtion = context.getJavaClientGeneratorConfiguration()
				.getProperty(PropertyRegistry.JAVA_TYPE_CONTROLLER_LOCATION);

		if (StringUtils.isBlank(localtion)) {
			System.err.println("the generator.xml " + PropertyRegistry.JAVA_TYPE_SERVICE_LOCATION
					+ "tag is wrong or null , can not generate the code for [Controller] ");
			return null;
		}

		FullyQualifiedJavaType modal = introspectedTable.getRules().calculateAllFieldsClass();
		FullyQualifiedJavaType service = FullyQualifiedJavaType
				.getNewInterface(servicelocation + "." + modal.getBaseShortName() + "Service");

		service.setPackageName(servicelocation);

		FullyQualifiedJavaType thisType = new FullyQualifiedJavaType(modal + "Controller");
		thisType.setPackageName(localtion);
		TopLevelClass topLevelClass = new TopLevelClass(thisType);

		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		commentGenerator.addJavaFileComment(topLevelClass);

		if (!StringUtils.isBlank(baseContrller)) {
			FullyQualifiedJavaType basecontrller = FullyQualifiedJavaType.getNewInterface(baseContrller);
			FullyQualifiedJavaType genericType = introspectedTable.getRules().calculateAllFieldsClass();
			basecontrller.addTypeArgument(genericType);

			topLevelClass.addImportedType(genericType);
			topLevelClass.setSuperClass(basecontrller);
			topLevelClass.addImportedType(basecontrller);

			isBaseControl = true;
		}

		// add @Controller annotation
		FullyQualifiedJavaType annotationController = new FullyQualifiedJavaType(
				"org.springframework.stereotype.Controller");
		topLevelClass.addAnnotation("@Controller");
		topLevelClass.addImportedType(annotationController);
		// add @RequestMapping annotation
		String lowertable = modal.getBaseShortName();
		lowertable = lowertable.toLowerCase();
		FullyQualifiedJavaType annotationRequestMapping = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.RequestMapping");
		topLevelClass.addAnnotation("@RequestMapping(\"" + lowertable + "\")");
		topLevelClass.addImportedType(annotationRequestMapping);

		Field field = new Field();
		FullyQualifiedJavaType annotationType = new FullyQualifiedJavaType(
				"org.springframework.beans.factory.annotation.Autowired");

		field.addAnnotation("@Autowired");
		field.setType(service);
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setName(reference);

		topLevelClass.addField(field);
		topLevelClass.addImportedType(annotationType);
		topLevelClass.addImportedType(service);
		topLevelClass.addImportedType(FullyQualifiedJavaType.getStringInstance());

		addInsertMethod(topLevelClass);
		addInfoMethod(topLevelClass);
		addUpdateMethod(topLevelClass);
		addDeleteMethod(topLevelClass);
		addCommontListMethod(topLevelClass);

		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
		answer.add(topLevelClass);

		// print
		if (ConstKey.isPrint) {
			System.err.println("\n");
			System.err.println("Java controller start ...");
			System.err.println("\n");
			System.out.println(topLevelClass.getFormattedContent());
		}

		return answer;
	}

	public void addInsertMethod(TopLevelClass topLevelClass) {
		Method method = new Method();
		// add annotation
		FullyQualifiedJavaType annotationRequestMapping = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.RequestMapping");
		method.addAnnotation("@RequestMapping(\"" + ConstKey.PARAM_ADD + "\")");
		topLevelClass.addImportedType(annotationRequestMapping);
		// add annotation
		FullyQualifiedJavaType annotationResponseBody = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.ResponseBody");
		method.addAnnotation("@ResponseBody");
		topLevelClass.addImportedType(annotationResponseBody);
		// add name
		method.setName(ConstKey.SERVICE_ADD);
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getObjectInstance());
		// add parameter
		FullyQualifiedJavaType string = FullyQualifiedJavaType.getStringInstance();
		method.addParameter(new Parameter(string, paramName)); // $NON-NLS-1$
		// add body
		method.addBodyLine("try {");
		method.addBodyLine(reference + "." + ConstKey.SERVICE_ADD + "(" + paramName + ");");
		if (isBaseControl) {
			method.addBodyLine("return RESULT_OPER_SUCCESS;");
			method.addBodyLine("} catch(RuntimeException e) {");
			method.addBodyLine("e.printStackTrace();");
			method.addBodyLine("return RESULT_OPER_FAUILTRUE;");
			method.addBodyLine("}");
		} else {
			method.addBodyLine(ConstKey.returnSuccessData());
			method.addBodyLine("} catch(RuntimeException e) {");
			method.addBodyLine("e.printStackTrace();");
			method.addBodyLine(ConstKey.returnFauilreData());
			method.addBodyLine("}");
		}

		topLevelClass.addMethod(method);
	}

	public void addInfoMethod(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		// add annotation
		FullyQualifiedJavaType annotationRequestMapping = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.RequestMapping");
		method.addAnnotation("@RequestMapping(\"" + ConstKey.PARAM_INFO + "\")");
		topLevelClass.addImportedType(annotationRequestMapping);
		// add annotation
		FullyQualifiedJavaType annotationResponseBody = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.ResponseBody");
		method.addAnnotation("@ResponseBody");
		topLevelClass.addImportedType(annotationResponseBody);

		method.setReturnType(FullyQualifiedJavaType.getObjectInstance());
		method.setVisibility(JavaVisibility.PUBLIC);
		method.setName(ConstKey.SERVICE_INFO);

		List<IntrospectedColumn> introspectedColumns = introspectedTable.getPrimaryKeyColumns();
		Parameter parameter = null;
		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
			importedTypes.add(type);
			parameter = new Parameter(type, introspectedColumn.getJavaProperty());
			method.addParameter(parameter);
		}
		FullyQualifiedJavaType modal = introspectedTable.getRules().calculateAllFieldsClass();
		method.addBodyLine("try {");
		String modalName = modal.getBaseShortName();
		method.addBodyLine(
				modalName + " record = " + reference + "." + ConstKey.SERVICE_INFO + "(" + parameter.getName() + ");");

		method.addBodyLine("return record;");
		method.addBodyLine("} catch(RuntimeException e) {");
		method.addBodyLine("e.printStackTrace();");
		if (isBaseControl) {
			method.addBodyLine("return RESULT_OBJ_NULL;");
		} else {
			method.addBodyLine("return \"{}\";");
		}

		// method.addBodyLine(ConstKey.returnFauilreData("null"));
		method.addBodyLine("}");
		topLevelClass.addImportedType(modal);
		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	public void addDeleteMethod(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		// add annotation
		FullyQualifiedJavaType annotationRequestMapping = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.RequestMapping");
		method.addAnnotation("@RequestMapping(\"" + ConstKey.PARAM_DELETE + "\")");
		topLevelClass.addImportedType(annotationRequestMapping);
		// add annotation
		FullyQualifiedJavaType annotationResponseBody = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.ResponseBody");
		method.addAnnotation("@ResponseBody");
		topLevelClass.addImportedType(annotationResponseBody);

		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getObjectInstance());
		method.setName(ConstKey.SERVICE_DELETE);

		Parameter parameter = null;
		List<IntrospectedColumn> introspectedColumns = introspectedTable.getPrimaryKeyColumns();
		for (IntrospectedColumn introspectedColumn : introspectedColumns) {
			FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
			importedTypes.add(type);
			parameter = new Parameter(type, introspectedColumn.getJavaProperty());
			method.addParameter(parameter);
		}
		method.addBodyLine("try {");
		method.addBodyLine(reference + "." + ConstKey.SERVICE_DELETE + "(" + parameter.getName() + ");");
		if (isBaseControl) {
			method.addBodyLine("return RESULT_OPER_SUCCESS;");
		} else {
			method.addBodyLine(ConstKey.returnSuccessData());
		}
		method.addBodyLine("} catch(RuntimeException e) {");
		method.addBodyLine("e.printStackTrace();");
		if (isBaseControl) {
			method.addBodyLine("return RESULT_OPER_FAUILTRUE;");
		} else {
			method.addBodyLine(ConstKey.returnFauilreData());
		}
		method.addBodyLine("}");
		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	public void addUpdateMethod(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

		Method method = new Method();
		// add annotation
		FullyQualifiedJavaType annotationRequestMapping = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.RequestMapping");
		method.addAnnotation("@RequestMapping(\"" + ConstKey.PARAM_UPDATE + "\")");
		topLevelClass.addImportedType(annotationRequestMapping);
		// add annotation
		FullyQualifiedJavaType annotationResponseBody = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.ResponseBody");
		method.addAnnotation("@ResponseBody");
		topLevelClass.addImportedType(annotationResponseBody);

		method.setVisibility(JavaVisibility.PUBLIC);
		method.setReturnType(FullyQualifiedJavaType.getObjectInstance());
		method.setName(ConstKey.SERVICE_UPDATE);
		FullyQualifiedJavaType string = FullyQualifiedJavaType.getStringInstance();
		method.addParameter(new Parameter(string, paramName)); // $NON-NLS-1$
		// method.addParameter(new Parameter(parameterType, "record"));
		// //$NON-NLS-1$

		// context.getCommentGenerator().addGeneralMethodComment(method,
		// introspectedTable);
		method.addBodyLine("try {");
		method.addBodyLine(reference + "." + ConstKey.SERVICE_UPDATE + "(" + paramName + ");");
		if (isBaseControl) {
			method.addBodyLine("return RESULT_OPER_SUCCESS;");
		} else {
			method.addBodyLine(ConstKey.returnSuccessData());
		}
		// method.addBodyLine(ConstKey.returnSuccessData());
		method.addBodyLine("} catch(RuntimeException e) {");
		method.addBodyLine("e.printStackTrace();");
		if (isBaseControl) {
			method.addBodyLine("return RESULT_OPER_FAUILTRUE;");
		} else {
			method.addBodyLine(ConstKey.returnFauilreData());
		}
		method.addBodyLine("}");

		topLevelClass.addImportedTypes(importedTypes);
		topLevelClass.addMethod(method);
	}

	public void addCommontListMethod(TopLevelClass topLevelClass) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		Method method = new Method();
		// add annotation
		FullyQualifiedJavaType annotationRequestMapping = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.RequestMapping");
		method.addAnnotation("@RequestMapping(\"" + ConstKey.PARAM_LIST + "\")");
		topLevelClass.addImportedType(annotationRequestMapping);
		// add annotation
		FullyQualifiedJavaType annotationResponseBody = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.ResponseBody");
		method.addAnnotation("@ResponseBody");
		topLevelClass.addImportedType(annotationResponseBody);

		method.setVisibility(JavaVisibility.PUBLIC);
		FullyQualifiedJavaType string = FullyQualifiedJavaType.getStringInstance();
		method.setReturnType(FullyQualifiedJavaType.getObjectInstance());
		method.setName(ConstKey.SERVICE_LIST);

		FullyQualifiedJavaType ints = FullyQualifiedJavaType.getIntInstance();
		method.addParameter(0, new Parameter(ints, "pageIndex"));
		method.addParameter(1, new Parameter(ints, "pageSize"));
		method.addParameter(2, new Parameter(string, "data"));

		StringBuffer sb = new StringBuffer();
		List<Parameter> ps = method.getParameters();
		for (Parameter p : ps) {
			sb.append(p.getName());
			sb.append(",");
		}
		sb.setLength(sb.length() - 1);
		// add body
		method.addBodyLine("try {");
		method.addBodyLine("return " + reference + "." + ConstKey.SERVICE_LIST + "(" + sb + ");");
		method.addBodyLine("} catch(RuntimeException e) {");
		method.addBodyLine("e.printStackTrace();");

		if (isBaseControl) {
			method.addBodyLine("return RESULT_LIST_NULL;");
		} else {
			method.addBodyLine("return \"" + ConstKey.RESULT_LIST_NULL + "\";");
		}

		method.addBodyLine("}");

		importedTypes.add(string);

		topLevelClass.addMethod(method);
		topLevelClass.addImportedTypes(importedTypes);
	}

	public void addQueryMethod(TopLevelClass topLevelClass) {
		Method method = new Method();
		// add annotation
		FullyQualifiedJavaType annotationRequestMapping = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.RequestMapping");
		method.addAnnotation("@RequestMapping(\"" + ConstKey.PARAM_LIST_NO_PAGE + "\")");
		topLevelClass.addImportedType(annotationRequestMapping);
		// add annotation
		FullyQualifiedJavaType annotationResponseBody = new FullyQualifiedJavaType(
				"org.springframework.web.bind.annotation.ResponseBody");
		method.addAnnotation("@ResponseBody");
		topLevelClass.addImportedType(annotationResponseBody);

		method.setName(ConstKey.SERVICE_OLIST);
		method.setVisibility(JavaVisibility.PUBLIC);
		// add return type
		FullyQualifiedJavaType list = FullyQualifiedJavaType.getNewListInstance();
		list.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());
		method.setReturnType(FullyQualifiedJavaType.getObjectInstance());
		// add parameter
		FullyQualifiedJavaType param = FullyQualifiedJavaType.getNewMapInstance();
		param.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
		param.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
		Parameter parameter = new Parameter(param, "map");
		method.addParameter(parameter);
		// add body
		method.addBodyLine("try {");
		method.addBodyLine("return " + reference + "." + ConstKey.SERVICE_OLIST + "(" + parameter.getName() + ");");
		method.addBodyLine("} catch(RuntimeException e) {");
		method.addBodyLine("e.printStackTrace();");

		if (isBaseControl) {
			method.addBodyLine("return RESULT_ARRAY_NULL;");
		} else {
			method.addBodyLine("return \"" + ConstKey.RESULT_ARRAY_NULL + "\";");
		}

		// method.addBodyLine("return \"" + ConstKey.RESULT_ARRAY_NULL + "\";");
		method.addBodyLine("}");

		topLevelClass.addImportedType(param);
		topLevelClass.addImportedType(list);

		topLevelClass.addMethod(method);
	}

}

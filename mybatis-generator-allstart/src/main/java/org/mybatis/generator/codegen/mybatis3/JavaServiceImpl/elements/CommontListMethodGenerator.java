package org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.elements;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.ConstKey;
import org.mybatis.generator.api.ShellRunner;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.JavaServiceImpl.AbstractJavaMethodGenerator;

public class CommontListMethodGenerator extends AbstractJavaMethodGenerator {

	@Override
	public void addClassMethod(TopLevelClass topLevelClass) {
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

		addMethodBody(method);

		FullyQualifiedJavaType json = FullyQualifiedJavaType.getNewInterface("JSONObject");
		json.setPackageName("net.sf.json.JSONObject");
		importedTypes.add(json);

		topLevelClass.addMethod(method);
		topLevelClass.addImportedTypes(importedTypes);
	}

	@Override
	protected void addMethodBody(Method method) {
		List<Parameter> ps = method.getParameters();
		StringBuffer sb = new StringBuffer();
		for (Parameter p : ps) {
			sb.append(p.getName());
			sb.append(",");
		}
		// sb.setLength(sb.length() - 1);

		// method.addBodyLine("Map<String, Object> map = commonsAnalyze(" + sb +
		// ");");
		// method.addBodyLine("// net.sf.json.JSONObject");
		// method.addBodyLine("JSONObject jsonObject = new JSONObject();");
		// method.addBodyLine("jsonObject.put(\"total\",
		// mapper.countList(map));");
		// method.addBodyLine("jsonObject.put(\"data\",
		// mapper.queryList(map));");
		// method.addBodyLine("return jsonObject.toString();");
		StringBuilder sbs = new StringBuilder();
		sbs.append(RETURN);
		super.appendBlank(sbs);
		sbs.append(SUPER);
		super.appendSeparator(sbs);
		if (ShellRunner.isOracle) {
			sbs.append("pager4Oracle");
		} else {
			sbs.append("pager4Mysql");
		}
		super.appendLeftParentheis(sbs);

		sbs.append(sb);
		sbs.append("mapper");

		super.appendRightParentheis(sbs);
		super.appendSemicolon(sbs);

		method.addBodyLine(sbs.toString());

	}

}

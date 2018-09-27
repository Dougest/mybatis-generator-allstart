package org.mybatis.generator.codegen.mybatis3.JavaServiceImpl;

import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractGenerator;

public abstract class AbstractJavaMethodGenerator extends AbstractGenerator {

	public abstract void addClassMethod(TopLevelClass topLevelClass);

	protected abstract void addMethodBody(Method method);
}

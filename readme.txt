基于mybatis-generator-core.1.3.2的魔改版
在原先生成的mapper中加入分页查询,条件查询,条件统计.
生成spring标准的service源码,controller源码,其中service层自带分页代码
具体使用与原先版本大体类似

由于此版本为初步版本,代码注释以及使用体验还有待提高

核心部分:
Main 方法所在类 		org.mybatis.generator.api.ShellRunner
各层次代码代码生成 
	--org.mybatis.generator.codegen.mybatis3.controller
	--org.mybatis.generator.codegen.mybatis3.javamapper
	--org.mybatis.generator.codegen.mybatis3.javaservice
	--org.mybatis.generator.codegen.mybatis3.JavaServiceImpl
	--org.mybatis.generator.codegen.mybatis3.model
	--org.mybatis.generator.codegen.mybatis3.xmlmapper
主调用类
	--org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl
	
org.mybatis.generator
	--api 			== api
	--codegen 		== 代码生成
	--plugins		== 插件
	--config		== 配置
	--internal		== 内部使用的一些东西 
		-- rules	== 规则,有些匹配规则才会生成相应代码









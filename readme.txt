				********************************************************
				***	   mybatis-generator-allstart 分层代码生成器     ***
				********************************************************
				
基于mybatis-generator-core.1.3.2的魔改版

1.mapper文件:在原先生成的mapper文件中加入分页查询,条件查询,条件统计.并且自动生成oracle/mysql分页sql(仅支持)
2.dao: 自使用生成接口方法,若继承超类,则有超类抽象实现.
3.service:生成spring标准的service源码,包含增删改,分页,列表,统计方法.
4.service的实现类中,新增和更新操作已实现字符串解析功能.此类尤其建议使用超类(basic包下)自适应获取数据库的分页方法.
5.controller: 包含add,update,delete,info,list方法,包含try-catch与返回值.

注:超类使用方法可见:generator.xml中的<javaClientGenerator>标签.
使用步骤:
	1.在<javaClientGenerator>标签中,请指定 name 为baseMapper,baseService,baseServiceImpl,baseController 的value属性(超类位置).
	2.本项目中的basic包下可见超类的具体在实现代码.复制粘贴即可.
本应用鼓励使用超类,由JVM实现类型判断,方便我们后期的维护与控制.


核心部分:
Main 方法位置 		org.mybatis.generator.api.ShellRunner
各分层代码实现位置(主逻辑代码)
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

由于此版本为迭代版本,代码注释以及使用体验还有待提高.
如果你有建议或者疑问,请邮件:15050055260@163.com


DEMO:

****************************************
***     	controller				****
****************************************
package com.test.entity;

public class testUser {
    private Long id;

    private String name;

    private String age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
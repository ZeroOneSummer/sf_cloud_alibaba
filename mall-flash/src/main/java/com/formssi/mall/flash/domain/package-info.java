package com.formssi.mall.flash.domain;
/**
 * 主要负责表达业务概念,业务状态信息和业务规则，Domain层主要有
 * 1、实体(Entities): 具有唯一标识的对象，实体可以设计成充血模型。
 * 2、值对象(Value Objects): 无需唯一标识的对象，例如：一对一，一对多业务模型。
 * 3、领域服务(Domain Services): 一些行为无法归类到实体对象或值对象上，本质是一些操作，而非事物。
 * 4、仓储(Repository): 一些持久化接口，具体数据库操作在infrastructure层
 */
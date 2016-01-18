project背景:xiaoshuang.yi,20130514
1.spring+spring mvc+spring batch+mongodb
2.数据源连接池使用druid,阿里温少开发的,要在该项目中去了解掌握它。知道基本的原理大概。
3.要能深入理解mongodb,并最好了解其它类似的技术框架,如hadoop等。
4.spring batch在批处理方面的一个深入逻辑要去掌握
5.掌握spring mvc的处理流程以及核心。和struts2做一个参照对比
6.了解junit测试,以及spring-test的用法:
	@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration(locations = "classpath:applicationContext.xml")

7.增加google guava工具库

8.重写静态方法,得不到预期结果,因为：
    重写指的是根据运行时对象的类型决定调用那个方法,而不是根据编译时的类型。
   静态方法是在编译阶段使用了编译类型信息,进行静态绑定的。
   
9.Integer作为方法参数传递,不会改变参数的数值。
	class Integer{
	   final int value; //一旦赋值，就不能改变。
	}

10.测试 父类和子类的加载顺序：TestSubLoad
11. ||（短路或）和|（或）都是表示“或”，区别是||只要满足第一个条件，后面的条件就不再判断，而|要对所有的条件进行判断。
    "&&" 操作如果第一个条件不满足 那么后面的条件就不用再判断了,而 &要对所有的条件都进行判断。

12.冒泡排序的优化:增加标识判断是否不需要排序交互位置,减少循环次数
13:使用 正则表达式进行空格分隔, str.split("\\s+");

14.使用druid的连接池监控,了解怎么去实现
15.添加mysql建表脚本：201407/xxx.ddl

16.了解spring mvc,请求的处理逻辑,以及负责请求转发相关类
   SimpleUrlHandlerMapping:        通过配置文件,把一个URL映射到Controller类上.
   DefaultAnnotationHandlerMapping:通过注解,把一个URL映射到Controller类上.
        注意：是映射到类上.
   HandlerAdapter接口 -- 处理请求的映射
   AnnotationMethodHandlerAdapter类，通过注解，把一个URL映射到Controller类的方法上
   
17.添加 netty 和 Rabbitmq 的测试类,
18.添加Redis的测试类，并且集成spring,使用jedis java客户端。
19.学习实现双向链表,
         实现快速排序.

20.scanner接受键盘的输入
21.添加apollo的测试

22:学习disruptor框架,开源的 并发框架。
/*
 * @(#)ReflectionUtils.java  1.0 2009-1-27下午10:17:16
 *
 */
package com.xsyi.core.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.Assert;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.xsyi.core.IConstants;
import com.xsyi.core.annotation.MongodbFiledType;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	Reflection工具类
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author eric
 * @version 1.0, 2009-1-27
 * @since framework-mustang
 * 
 */
@SuppressWarnings("all")
public final class ReflectionUtils {
	
	private static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	private static Log _logger = LogFactory.getLog(ReflectionUtils.class);

	/**
	 * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
	 * @param object
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static final Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	/**
	 * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
	 * @param object
	 * @param Field field
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static final Object getFieldValue(Object object, Field field) throws NoSuchFieldException {
		Assert.notNull(field);
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
	 * @param object
	 * @param fieldName
	 * @param value
	 * @throws NoSuchFieldException
	 */
	public static final void setFieldValue(Object object, String fieldName, Object value) throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * @param object
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static final Field getDeclaredField(Object object, String fieldName) throws NoSuchFieldException {
		Assert.notNull(object);
		return getDeclaredField(object.getClass(), fieldName);
	}

	/**
	 * 循环向上转型,获取类的DeclaredField.
	 * @param clazz
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 */
	@SuppressWarnings("unchecked")
	public static final Field getDeclaredField(Class clazz, String fieldName) throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(fieldName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上寻找
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + fieldName);
	}
	
	/**
	 * 循环向上,获取类的DeclaredFields
	 * @param clazz
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static final Field[] getDeclaredFieldsArray(Class clazz) throws NoSuchFieldException {		
		Assert.notNull(clazz);
		Set<Field> tmp = new HashSet<Field>();
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				CollectionUtils.addAll(tmp, superClass.getDeclaredFields());
			} catch (Exception e) {
				// Field不在当前类定义,继续向上寻找
			}
		}
		return tmp.toArray(new Field[0]);
	}
	
	/**
	 * 
	 * 
	 * @param clazz
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static final List<Field> getDeclaredFieldsList(Class clazz) throws NoSuchFieldException {
	    Assert.notNull(clazz);
        List<Field> tmp = new ArrayList<Field>();
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field[] fields = superClass.getDeclaredFields();
                if( fields!=null && fields.length>0 ){
                    for(Field fiel:fields){
                        tmp.add(fiel);
                    }
                }
            } catch (Exception e) {
                // Field不在当前类定义,继续向上寻找
            }
        }
        return tmp;
	}
	
	/**
	 * 直接调用对象方法,无视private/protected修饰符
	 * @param object
	 * @param methodName
	 * @param parameters
	 * @return
	 * @throws NoSuchMethodException
	 */
	public static final Object callMethod(Object object, String methodName, Object... parameters) throws NoSuchMethodException {
		Method method = getDeclaredMethod(object,methodName,parameters);
		if (!method.isAccessible()){
			method.setAccessible(true);
		}
		try {
			return method.invoke(object, parameters);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	/**
	 * 循环向上转型,获取类的DeclaredMehtod.
	 * @param object
	 * @param methodName
	 * @param parameters
	 * @return
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public static final Method getDeclaredMethod(Object object, String methodName, Object... parameters) throws NoSuchMethodException {
		Assert.notNull(object);
		Class[] parameterTypes = null;
		if (!ArrayUtils.isEmpty(parameters)){
			Collection<Class> temp = new ArrayList<Class>();
			for (Object parameter : parameters) {
				Assert.notNull(parameter);
				temp.add(parameter.getClass());
			}
			parameterTypes = temp.toArray(new Class[temp.size()]);
		}
		return getDeclaredMethod(object.getClass(), methodName, parameterTypes);
	}
	
	/**
	 * 循环向上转型,获取类的DeclaredMehtod.
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public static final Method getDeclaredMethod(Object object, String methodName, Class... parameterTypes) throws NoSuchMethodException {
		Assert.notNull(object);
		return getDeclaredMethod(object.getClass(), methodName, parameterTypes);
	}
	
	/**
	 * 循环向上转型,获取类的DeclaredMehtod.
	 * @param clazz
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public static final Method getDeclaredMethod(Class clazz, String methodName, Class... parameterTypes) throws NoSuchMethodException{
		Assert.notNull(clazz);
		Assert.hasText(methodName);	
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {		
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
				// Field不在当前类定义,继续向上寻找
			}
		}	
		throw new NoSuchMethodException("No such Method: " + clazz.getName() + '.' + methodName);
	}

	/**
	 * 扫描classpath下的指定路径下所有指定类型的资源
	 * @param packagesToScan
	 * @param resourcePattern	
	 * @return
	 * @throws Exception
	 */
	
	public static final Resource[] scanClasspathResourcesByPackages(String[] packagesToScan,String resourceSuffix) throws Exception {
		Collection<Resource> resources = new HashSet<Resource>();
		Resource[] ret = new Resource[0];
		for (String pkg : packagesToScan) {
			String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
			org.springframework.util.ClassUtils.convertClassNameToResourcePath(pkg) + "/**/*"+resourceSuffix;
			CollectionUtils.addAll(resources, resourcePatternResolver.getResources(pattern));
		}	
		return resources.toArray(ret);
	}
	
	/**
	 * 扫描classpath下的指定路径下所有class,初始化的时候使用
	 * @param packagesToScan
	 * @return
	 * @throws Exception
	 */
	public static final List<Class<?>> scanClazzByPackages(String[] packagesToScan) throws Exception {		
		List<Class<?>> ret = new ArrayList<Class<?>>();
		try {
			Resource[] resources = scanClasspathResourcesByPackages(packagesToScan,IConstants.CLASS_SUFFIX);
			MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
			for (Resource resource : resources) {
				if (resource.isReadable()) {
					MetadataReader reader = readerFactory.getMetadataReader(resource);
					String className = reader.getClassMetadata().getClassName();
					ret.add(resourcePatternResolver.getClassLoader().loadClass(className));
				}
			}
			
		}catch (IOException ex) {
			throw new Exception ("Failed to scan classpath for unlisted classes", ex);
		}
		catch (ClassNotFoundException ex) {
			throw new Exception("Failed to load classes from classpath", ex);
		}		
		return ret;
	}
	
	
	/**
	 * 扫描所有指定名称指定类型的资源
	 * @param packagesToScan
	 * @param resourcePattern	
	 * @return
	 * @throws Exception
	 */
	
	public static final Resource[] scanClasspathResourcesByNames(String[] namesToScan,String resourceSuffix) throws Exception {
		Collection resources = new HashSet();
		Resource[] ret = new Resource[0];
		for (int i = 0; i < namesToScan.length; i++) {
			String pattern1 = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
				org.springframework.util.ClassUtils.convertClassNameToResourcePath(namesToScan[i]) + "*" + resourceSuffix;
			String pattern2 = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "/**/" +
				org.springframework.util.ClassUtils.convertClassNameToResourcePath(namesToScan[i]) + "*" + resourceSuffix;	
			CollectionUtils.addAll(resources, resourcePatternResolver.getResources(pattern1));
			CollectionUtils.addAll(resources, resourcePatternResolver.getResources(pattern2));
		}
		return (Resource[])resources.toArray(ret);
	}
	
	public static final Resource[] getResources(String pattern){
		try {
			return resourcePatternResolver.getResources(pattern);
		} catch (IOException e) {
			_logger.error("", e);
			return null;
		}
	}

	public static final Resource getResource(String pattern){
		try {
			Resource[] ret = resourcePatternResolver.getResources(pattern);
			Assert.notEmpty(ret);
			return ret[0];
		} catch (IOException e) {
			_logger.error("", e);
			return null;
		}
	}
	
	/**
	 * 扫描指定的properties存放路径，得到下面（包括子路径下）所有properties文件的basename
	 * @param propFilesPath
	 * @return
	 * @throws Exception
	 */
	public static final String[] calculatePropFilesBasenameByFilesPath(String[] propFilesPath)throws Exception {
		Collection baseNames = new HashSet();
		String[] ret=new String[0];
		Resource[] resources = scanClasspathResourcesByPackages(propFilesPath,IConstants.PROPERTIES_SUFFIX);
		for (int i = 0; i < resources.length; i++) {
			Assert.notNull(resources[i]);
			String aResPath = resources[i].getURL().getPath();
			Matcher m = Pattern.compile(
					//"(classes|file.*jar!/|file.*war!/)(.*)"+   //-- bugfix cannot resolve urlresource
					"(classes|.*jar!/|.*war!/)(.*)"+ // -- bugfix for respath in weblogic under windows not begin with 'file' 20101018
					IConstants.PROPERTIES_SUFFIX
			).matcher(aResPath); 
			while(m.find()){   
			    String temp = m.group(2);  
			    for (int j = 0; j < IConstants.ALL_LOCALES_STRING.length; j++) {
			    	String localeStr = IConstants.ALL_LOCALES_STRING[j];
			    	if (temp.endsWith(IConstants.UNDERLINE+localeStr)){
					  	  temp = temp.substring(0,temp.indexOf(IConstants.UNDERLINE+localeStr));//去掉国际化后缀部分
					  	  break;
					}
				}

			    baseNames.add(temp);
			}
		}

		return (String[])baseNames.toArray(ret);
	}
		
	
	/**
	 * 扫描指定的文件名的所有properties文件的basename
	 * @param propFilesPath
	 * @return
	 * @throws Exception
	 */
	public static final String[] calculatePropFilesBasenameByFilesName(String[] propFilesName)throws Exception {
		Collection baseNames = new HashSet();
		String[] ret=new String[0];
		Resource[] resources = scanClasspathResourcesByNames(propFilesName,IConstants.PROPERTIES_SUFFIX);
		for (int i = 0; i < resources.length; i++) {
			Assert.notNull(resources[i]);
			String aResPath = resources[i].getURL().getPath();
			Matcher m = Pattern.compile(
					"(classes|file.*jar!/|file.*war!/)(.*)"+   //-- bugfix cannot resolve urlresource				
					IConstants.PROPERTIES_SUFFIX
			).matcher(aResPath); 
			while(m.find()){   
			    String temp = m.group(2);  
			    for (int j = 0; j < IConstants.ALL_LOCALES_STRING.length; j++) {
			    	String localeStr = IConstants.ALL_LOCALES_STRING[j];
			    	if (temp.endsWith(IConstants.UNDERLINE+localeStr)){
					  	  temp = temp.substring(0,temp.indexOf(IConstants.UNDERLINE+localeStr));//去掉国际化后缀部分
					  	  break;
					}
				}

			    baseNames.add(temp);
			}
		}

		return (String[])baseNames.toArray(ret);
	}
	
	
	/**
	 * 扫描指定的properties存放路径，得到下面（包括子路径下）所有properties文件的basename
	 * @param propFilesPath
	 * @return
	 * @throws Exception
	 */
	public static final String[] caculatePropFilesBasename(String[] propFilesPath)throws Exception {
		Collection<String> baseNames = new HashSet<String>();
		String[] ret=new String[0];
		Resource[] resources = scanClasspathResourcesByPackages(propFilesPath,IConstants.PROPERTIES_SUFFIX);
		for (Resource r : resources) {
			String aResPath = r.getURL().getPath();
			Matcher m = Pattern.compile(
					"(classes|file.*jar!/|file.*war!/)(.*)"+   //-- bugfix cannot resolve urlresource				
					IConstants.PROPERTIES_SUFFIX
			).matcher(aResPath); 
			while(m.find()){   
			    String temp = m.group(2);  
			    for (String localeStr : IConstants.ALL_LOCALES_STRING) {
				    if (temp.endsWith(IConstants.UNDERLINE+localeStr)){
					  	  temp = temp.substring(0,temp.indexOf(IConstants.UNDERLINE+localeStr));//去掉国际化后缀部分
					  	  break;
					}
				}
			    baseNames.add(temp);
			}
		}
		return baseNames.toArray(ret);
	}
	

	/**
	 * 得到指定class实现的接口中指定位置的范型参数
	 * @param clazz
	 * @param i		第几个接口
	 * @param j		接口中第几个范型参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class getInterfaceGenricType(Class<?> clazz,int i, int j) {	
		Assert.notNull(clazz);
		Assert.isTrue(i>=0,"Index of interface out of bounds. The specified index:"+i+" must greater than or equal to zero.");
		Assert.isTrue(j>=0,"Index inside a interface which specify the genericType position is out of bounds. The specified index:"+j+" must greater than or equal to zero.");
		Type interfaceType = clazz.getGenericInterfaces()[i];
		if (!(interfaceType instanceof ParameterizedType)) {	
			//未指定范型参数，返回raw type
			//class SuperA<T>{}  
			//class A extends SuperA{}
			_logger.warn(clazz.getSimpleName() + "'s interface not ParameterizedType");
			return Object.class;
		}
		Type[] params = ((ParameterizedType) interfaceType).getActualTypeArguments();		
		Assert.isTrue(j < params.length,"Index Out Of Bounds. The specified index:"+j+" must less than "+params.length);
		if (!(params[j] instanceof Class)) {
			//未设置范型参数，返回raw type
			//class SuperA<T>{}  
			//class A<T> extends SuperA<T>{}			
			_logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		return (Class) params[j];
	}
	
	/**
	 * 得到指定class实现的第一个接口的第一个范型参数
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class getInterfaceGenricType(Class<?> clazz) {
		return getInterfaceGenricType(clazz,0,0);
	}
	
	
	/**
	 * 得到指定class指定位置的范型参数
	 * @param clazz
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(Class<?> clazz, int index) {	
		Assert.notNull(clazz);
		Assert.isTrue(index>=0,"Index Out Of Bounds. The specified index:"+index+" must greater than or equal to zero.");
		Type superClassType = clazz.getGenericSuperclass();
		if (!(superClassType instanceof ParameterizedType)) {	
			//未指定范型参数，返回raw type
			//class SuperA<T>{}  
			//class A extends SuperA{}
			_logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}
		Type[] params = ((ParameterizedType) superClassType).getActualTypeArguments();		
		Assert.isTrue(index < params.length,"Index Out Of Bounds. The specified index:"+index+" must less than "+params.length);
		if (!(params[index] instanceof Class)) {
			//未设置范型参数，返回raw type
			//class SuperA<T>{}  
			//class A<T> extends SuperA<T>{}			
			_logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}
		//正常情况返回父类范型参数的类型
		//class SuperA<T>{}  
		//class A extends SuperA<String>{}
		//返回 String
		return (Class) params[index];
	}
	
	/**
	 * 得到指定class的第一个范型参数
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(Class<?> clazz) {
		return getSuperClassGenricType(clazz,0);
	}
	
	/**
	 * 得到动态代理
	 * @param interfaces
	 * @param handler
	 * @return
	 */
	public static Object getProxyBean(Class<?>[] interfaces,InvocationHandler handler){
		return Proxy.newProxyInstance(ReflectionUtils.class.getClassLoader(), interfaces, handler);
	}
	
	private ReflectionUtils(){}


	/**
	 * 将业务对象采用反射机制产生相应的mongdb object
	 * 注： object对象必须按照mongdb的annotaions格式来定义，不然产生的结果达不到想要的结果
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static DBObject convertObjectToDBObject(Object object) throws Exception{
		if( object==null ) return null;
		DBObject dbo = new BasicDBObject();
		List<Field> fileds = ReflectionUtils.getDeclaredFieldsList(object.getClass());
		for (Field filed:fileds) {
			if( filed.isAnnotationPresent(org.springframework.data.annotation.Transient.class) ) continue;
			String mongdbFiledName = StringUtils.EMPTY;
			MongodbFiledType mongdbField = null;
            if (filed.isAnnotationPresent(org.springframework.data.mongodb.core.mapping.Field.class)  ){ 
            	Annotation[] annos = filed.getAnnotations();
            	for(Annotation anno:annos){
            	    if( anno instanceof org.springframework.data.mongodb.core.mapping.Field){
            	        mongdbFiledName = ((org.springframework.data.mongodb.core.mapping.Field)anno).value();
            	    }else if( anno instanceof MongodbFiledType){
            	        mongdbField = ((MongodbFiledType)anno);
            	    }
            	}
            }
            if( StringUtils.isEmpty(mongdbFiledName) ) continue;
            Object value = ReflectionUtils.getFieldValue(object, filed);
            if( filed.getType().isAssignableFrom(ObjectId.class) ){
                dbo.put(mongdbFiledName, value);
                continue;
            }
            if( mongdbField==null ){
                dbo.put(mongdbFiledName, value==null ? StringUtils.EMPTY:value);
            }else{
                if( mongdbField.type()==MongodbFiledType.Types.IGNORE ) continue;
                if( value==null ){
                    //设置默认的值
                    if( MongodbFiledType.Types.Boolean==mongdbField.type()){
                        dbo.put(mongdbFiledName, Boolean.FALSE.booleanValue());
                    }else if( MongodbFiledType.Types.String==mongdbField.type()){
                        dbo.put(mongdbFiledName,StringUtils.EMPTY);
                    }else if( MongodbFiledType.Types.INT==mongdbField.type()){
                        dbo.put(mongdbFiledName,0);
                    }else if( MongodbFiledType.Types.Double==mongdbField.type()){
                        dbo.put(mongdbFiledName,0.00);
                    }else if( MongodbFiledType.Types.Date==mongdbField.type()){
                        dbo.put(mongdbFiledName,StringUtils.EMPTY);
                    }
                }else{
                    if( MongodbFiledType.Types.Boolean==mongdbField.type()){
                        dbo.put(mongdbFiledName, Boolean.parseBoolean((String)value));
                    }else if( MongodbFiledType.Types.String==mongdbField.type()){
                        dbo.put(mongdbFiledName, value);
                    }else if( MongodbFiledType.Types.INT==mongdbField.type()){
                        if( value instanceof Integer){
                            dbo.put(mongdbFiledName, (Integer)value);
                        }else if( value instanceof BigInteger ){
                            dbo.put(mongdbFiledName, CurrencyUtils.convertBigIntegerToInt((BigInteger)value));
                        }else if( value instanceof String ){
                            dbo.put(mongdbFiledName, Integer.parseInt((String)value));
                        }
                    }else if( MongodbFiledType.Types.Double==mongdbField.type()){
                        if( value instanceof Double){
                            dbo.put(mongdbFiledName, (Double)value);
                        }else if( value instanceof BigDecimal ){
                            dbo.put(mongdbFiledName, CurrencyUtils.convertBigDecimalToDouble((BigDecimal)value,mongdbField.length()));
                        }else{
                            dbo.put(mongdbFiledName, value);
                        }
                    }else if( MongodbFiledType.Types.Date==mongdbField.type()){
                        if( value instanceof Date){
                            dbo.put(mongdbFiledName, (Date)value);
                        }else{
                            dbo.put(mongdbFiledName, value);
                        }
                    }
                }
            }
        }   
		return dbo;
	}
	
}


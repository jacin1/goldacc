/**
 * 
 *
 */
package com.xsyi.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import com.xsyi.core.validator.DefaultValidatorFactory;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p></dd>
 * </dl>
 * 
 * @author tangtao7481
 * @version , Mar 27, 2013 9:19:17 PM
 * 
 */
public class ScanUtils {

	// 日志记录对象
	private static final Log logger = LogFactory
			.getLog(DefaultValidatorFactory.class);

	/**
	 * 扫描指定报名下的所有类型
	 * 
	 * @param packageName
	 *            指定报名
	 * @param 要扫描的类实现的接口或继承的抽象类
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class> scanPackage(String[] packageName,Class targetClass) throws Exception {
		if (packageName == null || packageName.length == 0) return null;
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(Boolean.TRUE.booleanValue());
		provider.addIncludeFilter(new AssignableTypeFilter(targetClass));
		Set<BeanDefinition> components = null;
		List<Class> candidatesClasses = new ArrayList<Class>();
		if (packageName != null && packageName.length > 0) {
			for (int j = 0; j < packageName.length; j++) {
				logger.info("开始扫描包:" + packageName[j]);
				components = provider.findCandidateComponents(packageName[j].replaceAll("\\.", "/"));
				for (BeanDefinition component : components) {
					Class cls = Class.forName(component.getBeanClassName());
					logger.info("搜索到类::" + cls.getCanonicalName() + ".....");
					candidatesClasses.add(cls);
				}
			}
		}
		return candidatesClasses;
	}

	/**
	 * 
	 * @param packageName
	 * @param targetClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Class> scanPackage(String packageName, Class targetClass) throws Exception {
		return ScanUtils.scanPackage(new String[] { packageName }, targetClass);
	}

}

/**
 * 
 */
package com.xsyi.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xsyi.core.IConstants;
import com.xsyi.core.config.SystemConfigUtils;

/**
 * IP访问地址过滤
 * @author tangtao7481
 * @version $Id: IPAccessFilter.java, v 0.1 Mar 31, 2013 10:55:45 AM tangtao7481 Exp $
 */
public class IPAccessFilter implements Filter {

    private Log    logger   = LogFactory.getLog(IPAccessFilter.class);

    private static final String rulesKey = "draco.ip.access.rules";
    //IP 地址过滤规则
    private String rules    = StringUtils.EMPTY;
    //本机访问IP地址
    private static final String localAddress = "0:0:0:0:0:0:0:1";
    private static final String localIp = "127.0.0.1";
    

    /** 
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        //do nothing
        this.rules = null;
    }

    /** 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,ServletException {
        if (StringUtils.isEmpty(this.rules)) {
            logger.info("系统未设置IP访问控制规则...");
            filterChain.doFilter(request, response);
            return;
        }
        if( IConstants.STAR.equals(rules) ){
            filterChain.doFilter(request, response);
            return;
        }
        String ip = this.getRemoteAddress((HttpServletRequest)request);
        logger.info("远程访问客户端IP地址：" + ip);
        if( localAddress.equals(ip) || this.rules.contains(ip) || localIp.equals(ip) ){
            filterChain.doFilter(request, response);
        }else{
            //无权限访问系统服务
            logger.info(ip + ":无权限访问系统服务：");
            response.setContentType("text/html;charset=" + request.getCharacterEncoding());
            response.setCharacterEncoding(request.getCharacterEncoding());
            response.getWriter().print("无权限访问系统服务");
        }
    }

    /** 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.rules = SystemConfigUtils.getSysconfigProperty(rulesKey);
        logger.info("检查是否配置IP访问限制规则,Rules:" + rules);
    }

    /**
     * 获取客户端访问地址
     * @param request
     * @return
     */
    public String getRemoteAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;
    }
    

}

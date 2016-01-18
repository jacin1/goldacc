/**
 * 
 */
package com.xsyi.core.mybatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mybatis批处理更新、删除、插入动作
 * 对于批量删除动作，请使用传统的IN 语法
 * @author tangtao7481
 * @version $Id: MybatisBatchUtils.java, v 0.1 Apr 12, 2013 5:49:13 PM tangtao7481 Exp $
 */
@Component
public class MybatisBatchUtils {

    // 日志记录对象
    protected Log             logger = LogFactory.getLog(getClass());

    /**
     * SqlSessionFactory
     */
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 批量插入数据
     * @param records -- 需要插入的数据
     * @param sqlId -- mybatis中配置的ID
     */
    public void batchInsert(List<?> list,String insertSqlId) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            for (Object value : list) {
                sqlSession.insert(insertSqlId, value);
            }
            sqlSession.commit();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 批量更新操作对象
     * @param records
     * @param updateSqlId
     */
    public void batchUpdate(List<?> list,String updateSqlId) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            for (Object value: list) {
                session.update(updateSqlId, value);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            session.close();
        }
    }
    
}
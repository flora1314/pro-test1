
package com.flora.dao.common;

import com.wm.nb.exception.AppException;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MyBatisBaseSupport {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MyBatisBaseSupport.class);
    @Resource
    private SqlSessionTemplate sqlTemplate;
    @Resource
    private SqlSessionTemplate batchSqlTemplate;

    public MyBatisBaseSupport() {

    }

    public abstract String getNameSpace(String baseStatement);

    protected SqlSessionTemplate getSqlTemplate(boolean batch, boolean readonly) {
        if(readonly) {
            ;
        }

        return batch?this.batchSqlTemplate:this.sqlTemplate;
    }

    protected int insert(String statement, Object parameter) {
        int res = 0;

        try {
            if(parameter != null) {
                res = this.getSqlTemplate(false, false).insert(statement, parameter);
            }

            return res;
        } catch (Exception var5) {
            throw new AppException("Mybatis执行新增异常", var5);
        }
    }

    protected int delete(String statement, Object parameter) {
        boolean res = false;

        try {
            int res1 = this.getSqlTemplate(false, false).delete(statement, parameter);
            return res1;
        } catch (Exception var5) {
            throw new AppException("Mybatis执行删除异常", var5);
        }
    }

    protected int update(String statement, Object parameter) {
        int res = 0;

        try {
            if(parameter != null) {
                res = this.getSqlTemplate(false, false).update(statement, parameter);
            }

            return res;
        } catch (Exception var5) {
            throw new AppException("Mybatis执行更新异常", var5);
        }
    }

    protected <T> T select(String statement, Object parameter) {
        T obj = null;
        try {
            obj = this.getSqlTemplate(false, true).selectOne(statement, parameter);
            return obj;
        } catch (Exception var5) {
            throw new AppException("Mybatis执行单条查询异常", var5);
        }
    }

    protected <T> List<T> selectList(String statement, Object parameter) {
        List list = null;

        try {
            list = this.getSqlTemplate(false, true).selectList(statement, parameter);
            return list;
        } catch (Exception var5) {
            throw new AppException("Mybatis执行列表查询异常", var5);
        }
    }

    protected <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        Map map = null;

        try {
            map = this.getSqlTemplate(false, true).selectMap(statement, parameter, mapKey);
            return map;
        } catch (Exception var6) {
            throw new AppException("Mybatis执行Map查询异常", var6);
        }
    }
}

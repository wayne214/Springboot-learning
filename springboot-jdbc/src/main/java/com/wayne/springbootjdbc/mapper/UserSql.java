package com.wayne.springbootjdbc.mapper;

import com.wayne.springbootjdbc.param.UserParam;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class UserSql {
    private static final Logger log = LoggerFactory.getLogger(UserSql.class);
    public String getList(UserParam userParam) {
        StringBuffer sql = new StringBuffer("select id, userName, password, user_sex as userSex, nick_name as nickName");
        sql.append("from users where 1=1");
        if (userParam !=null) {
            if (!StringUtils.isEmpty(userParam.getUserName())) {
                sql.append(" and userName = #{userName}");
            }

            if (!StringUtils.isEmpty(userParam.getUserSex())) {
                sql.append(" and user_sex = #{userSex}");
            }
        }

        sql.append(" order by id desc");
        sql.append(" limit " + userParam.getBeginLine() + "," + userParam.getPageSize());
        log.info("getList sql is :" +sql.toString());

        return sql.toString();
    }

    /**
     * 结构化SQL
     * @param userParam
     * @return
     */
    public String getCount(UserParam userParam) {
        String sql= new SQL(){{
            SELECT("count(1)");
            FROM("users");
            if (!StringUtils.isEmpty(userParam.getUserName())) {
                WHERE("userName = #{userName}");
            }
            if (!StringUtils.isEmpty(userParam.getUserSex())) {
                WHERE("user_sex = #{userSex}");
            }
            //从这个 toString 可以看出，其内部使用高效的 StringBuilder 实现 SQL 拼接
        }}.toString();

        log.info("getCount sql is :" +sql);
        return sql;
    }
}

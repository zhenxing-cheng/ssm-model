package com.tasly.gxx.client;

import com.tasly.gxx.domain.UserRoleRelationExample;
import com.tasly.gxx.domain.UserRoleRelationKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IUserRoleRelationMapper {
    int countByExample(UserRoleRelationExample example);

    int deleteByExample(UserRoleRelationExample example);

    int deleteByPrimaryKey(UserRoleRelationKey key);

    int insert(UserRoleRelationKey record);

    int insertSelective(UserRoleRelationKey record);

    List<UserRoleRelationKey> selectByExample(UserRoleRelationExample example);

    int updateByExampleSelective(@Param("record") UserRoleRelationKey record, @Param("example") UserRoleRelationExample example);

    int updateByExample(@Param("record") UserRoleRelationKey record, @Param("example") UserRoleRelationExample example);
}
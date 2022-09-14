package core.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import core.entity.CoreTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoreTestMapper extends BaseMapper<CoreTest> {

    /**
     * sqlSession测试
     * @return
     */
    List<CoreTest> getById(@Param(value = "id") String id);
}

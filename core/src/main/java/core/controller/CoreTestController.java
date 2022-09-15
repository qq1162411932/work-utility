package core.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import core.entity.CoreTest;
import core.mapper.CoreTestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/15 10:24
 */

@RestController
@RequestMapping("/core")
public class CoreTestController {

    @Autowired
    private CoreTestMapper coreTestMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("selectAll")
    public List<Map<String,Object>> selectAll(){
        String sql = "select * from test";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }

    @GetMapping("/page")
    public Page<CoreTest> getCoreTestPage(Page page, CoreTest coreTest){
        coreTestMapper.getAllByEntity(coreTest);
        return coreTestMapper.getAllPage(page, coreTest);
    }
}

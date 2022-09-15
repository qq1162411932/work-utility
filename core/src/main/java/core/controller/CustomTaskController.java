package core.controller;

import core.entity.CustomTask;
import core.entity.dto.CustomTaskDTO;
import core.entity.vo.CommonCountVo;
import core.service.CustomTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/15 15:59
 */

@RestController
@RequestMapping("custom")
public class CustomTaskController {

    @Autowired
    private CustomTaskService customTaskService;

    @PutMapping("/insert")
    public Boolean insertCustomTask(@RequestBody CustomTask customTask) {
        //新增后对象 会自动附上id
        return customTaskService.save(customTask);
    }

    @GetMapping("/{id}")
    public CustomTask getById(@PathVariable("id") Long id) {
        return customTaskService.getById(id);
    }

    /**
     * 任务管理状态栏统计量
     *
     * @param
     * @param userName
     * @return
     */
    @GetMapping("/task/counts")
    public CommonCountVo getNumByMonth(@RequestParam(name = "userName") String userName) {
        return customTaskService.getTaskAllCount(userName);
    }

}

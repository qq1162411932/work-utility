package core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import core.entity.CustomTask;
import core.entity.dto.CustomTaskDTO;
import core.entity.vo.CommonCountVo;

import java.util.List;

/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/15 16:07
 */

public interface CustomTaskService extends IService<CustomTask> {

    List<CommonCountVo> getTaskAllCount(Long userName);
}

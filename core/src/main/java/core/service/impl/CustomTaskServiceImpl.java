package core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import core.entity.CustomTask;
import core.entity.emuns.CustomTaskHandleMark;
import core.entity.vo.CommonCountVo;
import core.mapper.CustomTaskMapper;
import core.service.CustomTaskService;
import core.utils.LocalDateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/15 16:07
 */

@Service
public class CustomTaskServiceImpl extends ServiceImpl<CustomTaskMapper, CustomTask> implements CustomTaskService {

    @Autowired
    private CustomTaskMapper customTaskMapper;


    /**
     * 关于
     * @param userName
     * @return
     */
    @Override
    public List<CommonCountVo> getTaskAllCount(Long userName) {
        Long count;
        List<CommonCountVo> CommonCountVoList = new ArrayList<>();
        List<CustomTask> customTasks = customTaskMapper.selectList(Wrappers.lambdaQuery(CustomTask.class)
                .eq(CustomTask::getReceiver, userName)
                .or()
                .eq(CustomTask::getPublisher, userName));

        //mine to do
        count = customTasks.stream().filter(customTask -> userName.equals(customTask.getReceiver())).count();
        CommonCountVoList.add(new CommonCountVo("任务总数", count));
        count = customTasks.stream().filter(customTask -> userName.equals(customTask.getReceiver()) && CustomTaskHandleMark.PASS.getKey().equals(customTask.getHandleMark())).count();
        CommonCountVoList.add(new CommonCountVo("完成任务总数", count));
        count = customTasks.stream().filter(customTask -> userName.equals(customTask.getReceiver()) && customTask.getPublishTime() != null && LocalDateTimeUtil.isThisWeek(customTask.getPublishTime())).count();
        CommonCountVoList.add(new CommonCountVo("本周任务总数", count));
        count = customTasks.stream().filter(customTask -> (userName.equals(customTask.getReceiver())
                && customTask.getPublishTime() != null && LocalDateTimeUtil.isThisWeek(customTask.getPublishTime())
                && CustomTaskHandleMark.PASS.getKey().equals(customTask.getHandleMark()))).count();
        CommonCountVoList.add(new CommonCountVo("本周完成任务总数", count));

        //others to do
        count = customTasks.stream().filter(customTask -> userName.equals(customTask.getPublisher())).count();
        CommonCountVoList.add(new CommonCountVo("发布总任务", count));
        count = customTasks.stream().filter(customTask -> userName.equals(customTask.getPublisher()) && CustomTaskHandleMark.PASS.getKey().equals(customTask.getHandleMark())).count();
        CommonCountVoList.add(new CommonCountVo("发布已完成任务", count));

        return CommonCountVoList;
    }
}

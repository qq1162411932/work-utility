package core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import core.entity.CoreTest;
import core.mapper.CoreTestMapper;
import core.service.CoreTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoreTestServiceImpl extends ServiceImpl<CoreTestMapper, CoreTest> implements CoreTestService {

    @Autowired
    private CoreTestMapper coreTestMapper;


}

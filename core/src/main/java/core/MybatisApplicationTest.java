package core;

import core.entity.CoreTest;
import core.mapper.CoreTestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/14 17:13
 */

@SpringBootTest
public class MybatisApplicationTest {

    @Autowired
    CoreTestMapper coreTestMapper;

    @Test
    public void likeTagTest() {
        coreTestMapper.getAllByNameAndValue("a", "a");
        CoreTest coreTest = new CoreTest();
        coreTest.setName("a");
        coreTest.setValue("a");
        coreTestMapper.getAllByEntity(coreTest);
    }

    @Test
    public void chooseTagTest() {
        //注意case 的先后顺序，小范围的放前面要不然不会执行到
        CoreTest coreTest = new CoreTest();
        coreTestMapper.getChooseAll(coreTest);
        coreTest.setName("a");
        coreTestMapper.getChooseAll(coreTest);
        coreTest.setValue("a");
        coreTestMapper.getChooseAll(coreTest);
    }
}

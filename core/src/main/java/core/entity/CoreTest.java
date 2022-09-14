package core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "core_test", autoResultMap = true)
public class CoreTest {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private String value;
}

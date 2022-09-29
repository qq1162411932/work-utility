package core.utils.others;

import lombok.Data;

/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/26 14:52
 */

@Data
public class DemoData {

    private String name;

    public DemoData(String name) {
        this.name = name;
    }
}

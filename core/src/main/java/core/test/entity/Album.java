package core.test.entity;

import lombok.Data;

/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/20 17:28
 */

@Data
public class Album {

    private Integer id;

    public Album(Integer id) {
        this.id = id;
    }
}

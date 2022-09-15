package core.entity.emuns;

/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/15 16:55
 */

public enum CustomTaskHandleMark {

    WAITING("WAITING","未完成"),
    PASS("PASS","已完成");

    private final String key;
    private final String value;

    CustomTaskHandleMark(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}

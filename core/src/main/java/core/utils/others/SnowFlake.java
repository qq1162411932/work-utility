package core.utils.others;

public class SnowFlake {

    /**
     * 起始的时间戳
     */
    private final static long START_STAMP = 1530374400000L;

    /**
     * 序列号占用的位数
     */
    private final static long SEQUENCE_BIT = 12;

    /**
     * 机器标识占用的位数
     */
    private final static long MACHINE_BIT = 5;

    /**
     * 数据中心占用的位数
     */
    private final static long DATACENTER_BIT = 5;

    /**
     * 数据中心ID最大值
     */
    private final static long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);

    /**
     * 机器ID最大值
     */
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);

    /**
     * 序列号最大值
     */
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    private final static long MACHINE_LEFT = SEQUENCE_BIT;

    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;

    private final static long TIMESTAMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    /**
     * 数据中心ID
     */
    private final long datacenterId;

    /**
     * 机器ID
     */
    private final long machineId;

    /**
     * 序列号ID
     */
    private long sequence = 0L;

    /**
     * 上一次时间戳
     */
    private long lastStamp = -1L;

    /**
     * 构造方法，初始化数据中心ID和机器ID
     * @param datacenterId 数据中心ID
     * @param machineId    机器ID
     */
    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0)
            throw new IllegalArgumentException("DatacenterId can't be greater than " + MAX_DATACENTER_NUM + " or less than 0 !");
        if (machineId > MAX_MACHINE_NUM || machineId < 0)
            throw new IllegalArgumentException("MachineId can't be greater than " + MAX_MACHINE_NUM + " or less than 0 !");
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * 使用雪花算法生成ID
     * @return ID
     */
    public synchronized long nextId() {
        long currStamp = System.currentTimeMillis();
        if (currStamp < lastStamp)
            throw new RuntimeException("The system clock rolls back, refuses to generate ID !");
        if (currStamp == lastStamp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                while (currStamp <= lastStamp) currStamp = System.currentTimeMillis();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }
        lastStamp = currStamp;
        return (currStamp - START_STAMP) << TIMESTAMP_LEFT | datacenterId << DATACENTER_LEFT | machineId << MACHINE_LEFT | sequence;
    }

    public static void main(String[] args) {
        SnowFlake snowFlake = new SnowFlake(1, 1);
        System.out.println(snowFlake.nextId());
    }

}
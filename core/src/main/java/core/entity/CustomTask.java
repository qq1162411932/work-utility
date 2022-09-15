package core.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/15 15:48
 */

@Data
@TableName(value = "custom_task")
public class CustomTask extends Model<CustomTask> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 任务主题
     */
    private String taskName;

    /**
     * 发布人
     */
    private Long publisher;

    /**
     * 接受人
     */
    private Long receiver;

    /**
     * 发布内容
     */
    private String content;

    /**
     * 处理内容
     */
    private String handleContent;

    /**
     * 发布时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;

    /**
     * 处理情况 0 已发布，1已处理
     */
    private String handleMark;

    /**
     * 处理时间
     * JSON parse error: Cannot deserialize value of type `java.time.LocalDateTime` from String "2022-09-15 03:12:12":
     * Failed to deserialize java.time.LocalDateTime: (java.time.format.DateTimeParseException) Text '2022-09-15 03:12:12'
     * could not be parsed at index 10; nested exception is com.fasterxml.jackson.databind.exc.InvalidFormatException:
     * Cannot deserialize value of type `java.time.LocalDateTime` from String "2022-09-15 03:12:12":
     * Failed to deserialize java.time.LocalDateTime: (java.time.format.DateTimeParseException) Text '2022-09-15 03:12:12' could not be parsed at index 10
     *  at [Source: (PushbackInputStream); line: 1, column: 16] (through reference chain: core.entity.CustomTask["publishTime"])]
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dealTime;

    /**
     * delFlag
     */
    @TableLogic
    private String delFlag;
}

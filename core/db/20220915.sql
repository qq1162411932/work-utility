CREATE TABLE `custom_task` (
       `id` bigint NOT NULL,
       `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '任务主题',
       `publisher` bigint DEFAULT NULL COMMENT '发布人',
       `receiver` bigint DEFAULT NULL COMMENT '接受人',
       `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '发布内容',
       `handle_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '处理内容',
       `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
       `handle_mark` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0' COMMENT '处理情况 0 已发布，1已处理',
       `deal_time` datetime DEFAULT NULL COMMENT '处理时间',
       `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '0',
       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='任务管理';

ALTER TABLE `test`.`custom_task`
    MODIFY COLUMN `handle_mark` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '处理情况 0 已发布，1已处理' AFTER `publish_time`
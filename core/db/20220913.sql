CREATE TABLE `test` (
    `name`  varchar(255) DEFAULT NULL,
    `value` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `test`.`test`(`name`, `value`) VALUES ('1', '1');

RENAME TABLE `test`.`test` TO `test`.`core_test`;

ALTER TABLE `test`.`core_test` ADD COLUMN `id` bigint NOT NULL AFTER `value`,
ADD PRIMARY KEY (`id`)
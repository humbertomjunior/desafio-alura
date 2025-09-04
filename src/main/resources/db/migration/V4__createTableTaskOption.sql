CREATE TABLE TaskOption (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    task_id bigint(20) NOT NULL,
    option_description varchar(80),
    is_correct tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_Task FOREIGN KEY (task_id) REFERENCES Task(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;
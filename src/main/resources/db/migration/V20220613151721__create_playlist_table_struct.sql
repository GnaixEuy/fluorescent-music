CREATE TABLE playlist
(
    `id`           VARCHAR(32)           NOT NULL
        PRIMARY KEY COMMENT '歌单ID',
    `name`         VARCHAR(64)           NOT NULL COMMENT '歌单名字',
    `description`  TEXT                  NULL COMMENT '歌单简介',
    `cover_url`    VARCHAR(256)          NULL COMMENT '歌单封面ID',
    `status`       INTEGER(32) DEFAULT 1 NOT NULL COMMENT '歌单上架状态，1-DRAFT-草稿，2-PUBLISHED-已上架，3-CLOSED-已下架',
    `creator_id`   VARCHAR(32)           NOT NULL COMMENT '创建人ID',
    `created_time` datetime(6)           NOT NULL COMMENT '创建时间',
    `updated_time` datetime(6)           NOT NULL COMMENT '更新时间',
    CONSTRAINT c_creator_id
        FOREIGN KEY (creator_id) REFERENCES `user` (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '歌单表';
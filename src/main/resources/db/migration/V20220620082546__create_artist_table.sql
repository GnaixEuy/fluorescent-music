CREATE TABLE artist
(
    `id`                 VARCHAR(32)          NOT NULL
        PRIMARY KEY COMMENT '歌手ID',
    `name`               VARCHAR(64)          NOT NULL COMMENT '歌手名字',
    `remark`             VARCHAR(128)         NULL COMMENT '歌手备注',
    `photo_url`          VARCHAR(256)         NULL COMMENT '歌手照片url',
    `status`             INTEGER(2) DEFAULT 0 NOT NULL COMMENT '歌手上架状态，0-DRAFT-草稿，1-PUBLISHED-已上架，2-BLOCKED-已封禁',
    `created_by_user_id` VARCHAR(32)          NOT NULL COMMENT '创建人ID',
    `updated_by_user_id` VARCHAR(32)          NOT NULL COMMENT '最后更新人ID',
    `created_time`       datetime(6)          NOT NULL COMMENT '创建时间',
    `updated_time`       datetime(6)          NOT NULL COMMENT '更新时间',
    CONSTRAINT artist_created_by_user_id
        FOREIGN KEY (created_by_user_id) REFERENCES `user` (id),
    CONSTRAINT artist_updated_by_user_id
        FOREIGN KEY (updated_by_user_id) REFERENCES `user` (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT '歌手表';
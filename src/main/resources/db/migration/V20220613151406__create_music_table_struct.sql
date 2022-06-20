CREATE TABLE music
(
    `id`           VARCHAR(32)  NOT NULL
        PRIMARY KEY COMMENT '歌曲ID',
    `name`         VARCHAR(64)  NOT NULL COMMENT '歌曲名',
    `type`         VARCHAR(64)  NULL DEFAULT '默认' COMMENT '歌曲种类',
    `image_url`    VARCHAR(256) NULL COMMENT '歌曲图片',
    `description`  TEXT         NULL COMMENT '歌曲简介',
    `status`       INTEGER(2)        DEFAULT 1 NOT NULL COMMENT '歌曲上架状态，1-DRAFT-草稿，2-PUBLISHED-已上架，3-CLOSED-已下架',
    `created_time` datetime(6)  NOT NULL COMMENT '创建时间',
    `updated_time` datetime(6)  NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin COMMENT '歌曲表';
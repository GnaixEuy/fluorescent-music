CREATE TABLE file
(
    `id` VARCHAR(32) NOT NULL
        PRIMARY KEY COMMENT '文件ID',
    `name` VARCHAR(64) NOT NULL COMMENT '文件名',
    `file_key` VARCHAR(64) NOT NULL COMMENT '文件hash值，即ObjectKey',
    `ext` VARCHAR(12) NOT NULL COMMENT '文件后缀名',
    `size` INT DEFAULT 0 NOT NULL COMMENT '文件大小；单位byte',
    `type` INTEGER(2) DEFAULT 0 NOT NULL COMMENT '文件类型，1-AUDIO-音频，2-IMAGE-图片，3-VIDEO-视频，0-OTHER-其他',
    `storage` VARCHAR(16) DEFAULT 1 NOT NULL COMMENT '存储供应商，1-COS-腾讯云存储，2-OSS-阿里云存储',
    `status` INTEGER(2) DEFAULT 1 NOT NULL COMMENT '文件状态，1-UPLOADING-上传中，2-UPLOADED-已上传，3-CANCEL-已取消',
    `created_time` datetime(6) NOT NULL COMMENT '创建时间',
    `updated_time` datetime(6) NOT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '文件表';
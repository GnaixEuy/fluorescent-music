BEGIN;
INSERT INTO `file` (`id`, `name`, `file_key`, `ext`, `size`, `type`, `storage`, `status`, `created_by_user_id`,
                    `updated_by_user_id`, `created_time`, `updated_time`)
VALUES ('2Aeozyetw0aw54r1dLls1kqVtnt', '2Aeozyetw0aw54r1dLls1kqVtnt', 'attachment/2Aeozyetw0aw54r1dLls1kqVtnt.mp3',
        'mp3', 177408, 1, '1', 2, '2', '2', '2022-06-16 19:41:18.969000', '2022-06-16 19:41:18.969000');
INSERT INTO `music` (`id`, `name`, `image_url`, `description`, `file_id`, `status`, `created_time`, `updated_time`)
VALUES ('1', '回忆中拥抱',
        'https://fluorescentmusic-1301661174.cos.ap-shanghai.myqcloud.com/attachment/2AeqUU1Uqi0jjSABuQNxMELyViJ.jpeg',
        '吴克群-回忆中拥抱', '2Aeozyetw0aw54r1dLls1kqVtnt', 1, '2022-06-16 19:44:58.000000', '2022-06-16 19:45:14.000000');
INSERT INTO `music` (`id`, `name`, `image_url`, `description`, `file_id`, `status`, `created_time`, `updated_time`)
VALUES ('2', '回忆中乱抱',
        'https://fluorescentmusic-1301661174.cos.ap-shanghai.myqcloud.com/attachment/2AeqUU1Uqi0jjSABuQNxMELyViJ.jpeg',
        '吴克群-回忆中拥抱', '2Aeozyetw0aw54r1dLls1kqVtnt', 1, '2022-06-16 19:44:58.000000', '2022-06-16 19:45:14.000000');

COMMIT;
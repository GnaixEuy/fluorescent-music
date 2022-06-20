BEGIN;
INSERT INTO `file` (`id`, `name`, `file_key`, `ext`, `size`, `type`, `storage`, `status`, `created_by_user_id`,
                    `updated_by_user_id`, `created_time`, `updated_time`)
VALUES ('2Aeozyetw0aw54r1dLls1kqVtnt', '2Aeozyetw0aw54r1dLls1kqVtnt', 'attachment/2Aeozyetw0aw54r1dLls1kqVtnt.mp3',
        'mp3', 177408, 1, '1', 2, '2', '2', '2022-06-16 19:41:18.969000', '2022-06-16 19:41:18.969000');
INSERT INTO `file` (`id`, `name`, `file_key`, `ext`, `size`, `type`, `storage`, `status`, `created_by_user_id`,
                    `updated_by_user_id`, `created_time`, `updated_time`)
VALUES ('2AptzWzYfkl8e0sHKmuLrL0Y8a1', '2AptzWzYfkl8e0sHKmuLrL0Y8a1', 'attachment/2AptzWzYfkl8e0sHKmuLrL0Y8a1.jpeg',
        'jpeg', 117666, 2, '1', 1, '2', '2', '2022-06-20 17:50:17.122000', '2022-06-20 17:50:17.122000');
INSERT INTO `file` (`id`, `name`, `file_key`, `ext`, `size`, `type`, `storage`, `status`, `created_by_user_id`,
                    `updated_by_user_id`, `created_time`, `updated_time`)
VALUES ('2Apu5A6IGneEKX1GxBk5e7LQV3Y', '2Apu5A6IGneEKX1GxBk5e7LQV3Y', 'attachment/2Apu5A6IGneEKX1GxBk5e7LQV3Y.jpeg',
        'jpeg', 49214, 2, '1', 1, '2', '2', '2022-06-20 17:51:01.947000', '2022-06-20 17:51:01.947000');
INSERT INTO `file` (`id`, `name`, `file_key`, `ext`, `size`, `type`, `storage`, `status`, `created_by_user_id`,
                    `updated_by_user_id`, `created_time`, `updated_time`)
VALUES ('2Apu8eFt5xqVPe8e39hQeDJoeAv', '2Apu8eFt5xqVPe8e39hQeDJoeAv', 'attachment/2Apu8eFt5xqVPe8e39hQeDJoeAv.jpeg',
        'jpeg', 276228, 2, '1', 1, '2', '2', '2022-06-20 17:51:30.044000', '2022-06-20 17:51:30.044000');
INSERT INTO `music` (`id`, `name`, `type`, `image_url`, `description`, `file_id`, `status`, `created_time`,
                     `updated_time`)
VALUES ('1', '回忆中拥抱', '流行乐',
        'https://fluorescentmusic-1301661174.cos.ap-shanghai.myqcloud.com/attachment/2AeqUU1Uqi0jjSABuQNxMELyViJ.jpeg',
        '吴克群-回忆中拥抱', '2Aeozyetw0aw54r1dLls1kqVtnt', 1, '2022-06-16 19:44:58.000000', '2022-06-16 19:45:14.000000');
INSERT INTO `music` (`id`, `name`, `type`, `image_url`, `description`, `file_id`, `status`, `created_time`,
                     `updated_time`)
VALUES ('2', '回忆中乱抱', '流行乐',
        'https://fluorescentmusic-1301661174.cos.ap-shanghai.myqcloud.com/attachment/2AeqUU1Uqi0jjSABuQNxMELyViJ.jpeg',
        '吴克群-回忆中拥抱', '2Aeozyetw0aw54r1dLls1kqVtnt', 1, '2022-06-16 19:44:58.000000', '2022-06-16 19:45:14.000000');

COMMIT;
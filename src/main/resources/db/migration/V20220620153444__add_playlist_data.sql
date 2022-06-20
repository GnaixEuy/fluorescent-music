BEGIN;
INSERT INTO `playlist` (`id`, `name`, `description`, `cover_url`, `status`, `recommended`, `special`,
                        `recommend_factor`, `creator_id`, `created_time`, `updated_time`)
VALUES ('2ApcWNEFCSCBwQNlgThhGR0tFvL', '测试歌单', '测试歌单详情信息',
        'https://fluorescentmusic-1301661174.cos.ap-shanghai.myqcloud.com/attachment/2ApvkSrbLair9Yllz9JzJMCojBr.jpeg',
        1, 0, 0, 0, '2', '2022-06-20 15:26:37.827000', '2022-06-20 15:26:37.827000');
INSERT INTO `playlist` (`id`, `name`, `description`, `cover_url`, `status`, `recommended`, `special`,
                        `recommend_factor`, `creator_id`, `created_time`, `updated_time`)
VALUES ('2ApccuQqKpt7kbFl46SVEgNjMQJ', '测试歌单1', '测试歌单详情信息',
        'https://fluorescentmusic-1301661174.cos.ap-shanghai.myqcloud.com/attachment/2AptzWzYfkl8e0sHKmuLrL0Y8a1.jpeg',
        1, 0, 0, 0, '2', '2022-06-20 15:27:29.359000', '2022-06-20 15:27:29.359000');
INSERT INTO `playlist` (`id`, `name`, `description`, `cover_url`, `status`, `recommended`, `special`,
                        `recommend_factor`, `creator_id`, `created_time`, `updated_time`)
VALUES ('2ApcdJJ6K6QtbCO2bwyPN7YhlMA', '测试歌单2', '测试歌单详情信息',
        'https://fluorescentmusic-1301661174.cos.ap-shanghai.myqcloud.com/attachment/2Apu8eFt5xqVPe8e39hQeDJoeAv.jpeg',
        1, 0, 0, 0, '2', '2022-06-20 15:27:32.058000', '2022-06-20 15:27:32.058000');
INSERT INTO `playlist` (`id`, `name`, `description`, `cover_url`, `status`, `recommended`, `special`,
                        `recommend_factor`, `creator_id`, `created_time`, `updated_time`)
VALUES ('2ApcdW2zUdhfcUrFX0rItzuzSyg', '测试歌单3', '测试歌单详情信息',
        'https://fluorescentmusic-1301661174.cos.ap-shanghai.myqcloud.com/attachment/2Apu5A6IGneEKX1GxBk5e7LQV3Y.jpeg',
        1, 0, 0, 0, '2', '2022-06-20 15:27:34.689000', '2022-06-20 15:27:34.689000');
INSERT INTO `playlist` (`id`, `name`, `description`, `cover_url`, `status`, `recommended`, `special`,
                        `recommend_factor`, `creator_id`, `created_time`, `updated_time`)
VALUES ('2Apcdo1nxQolk5d7hShaoCGLcTj', '测试歌单4', '测试歌单详情信息',
        'https://fluorescentmusic-1301661174.cos.ap-shanghai.myqcloud.com/attachment/2ApfqXEdZb1G1jzijByvog1bdCp.png',
        1, 0, 0, 0, '2', '2022-06-20 15:27:36.879000', '2022-06-20 15:27:36.879000');
INSERT INTO `playlist_music` (`playlist_id`, `music_id`)
VALUES ('2ApcWNEFCSCBwQNlgThhGR0tFvL', '2');
INSERT INTO `playlist_music` (`playlist_id`, `music_id`)
VALUES ('2ApcWNEFCSCBwQNlgThhGR0tFvL', '1');
INSERT INTO `playlist_music` (`playlist_id`, `music_id`)
VALUES ('2ApcdJJ6K6QtbCO2bwyPN7YhlMA', '1');
COMMIT;
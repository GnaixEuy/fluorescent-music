INSERT INTO `user` (id, username, nickname, password, gender, created_time, updated_time)
VALUES ('1', 'admin', 'GnaixEuy', '$2a$10$tLL4xDMkoWZN98GKxueh5uvUzT2jwjXTn7I5uDx95pWzC2PWar5bS',
        2,
        '2021-07-21 09:27:12.260000',
        '2021-07-21 09:27:12.260000');
INSERT INTO `user` (id, username, nickname, password, gender, created_time, updated_time, open_id, avatar_url)
VALUES ('2', 'admin22', 'music', '$2a$10$tLL4xDMkoWZN98GKxueh5uvUzT2jwjXTn7I5uDx95pWzC2PWar5bS',
        1,
        '2021-07-21 09:27:12.260000',
        '2021-07-21 09:27:12.260000',
        'oeEFD5JurMef-pOoDxDxNKowC84o',
        'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKRz0YhYGB6yDoicvcbsUDRsVYcB97aarmJfApricmtlKdTIV797FwahCDJibBaMftl6iciczUOvwdBxaA/132');
INSERT INTO `user` (id, username, nickname, password, created_time, updated_time)
VALUES ('3', 'admin23', '测试1111', '$2a$10$tLL4xDMkoWZN98GKxueh5uvUzT2jwjXTn7I5uDx95pWzC2PWar5bS',
        '2021-07-21 09:27:12.260000',
        '2021-07-21 09:27:12.260000');
INSERT INTO `role` (id, name, title, created_time, updated_time)
VALUES ('1', 'ROLE_USER', '普通用户', '2021-07-21 09:27:12.260000', '2021-07-21 09:27:12.260000');
INSERT INTO `role` (id, name, title, created_time, updated_time)
VALUES ('2', 'ROLE_ADMIN', '超级管理员', '2021-07-21 09:27:12.260000', '2021-07-21 09:27:12.260000');
INSERT INTO `user_role` (user_id, role_id)
VALUES ('1', '1');
INSERT INTO `user_role` (user_id, role_id)
VALUES ('1', '2');
INSERT INTO `user_role` (user_id, role_id)
VALUES ('2', '1');
INSERT INTO `user_role` (user_id, role_id)
VALUES ('2', '2');


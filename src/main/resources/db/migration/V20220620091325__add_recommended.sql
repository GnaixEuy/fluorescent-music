ALTER TABLE `artist`
    ADD COLUMN `recommended` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否推荐：推荐：1； 不推荐：0；默认：0' AFTER `status`;
ALTER TABLE `artist`
    ADD COLUMN `recommend_factor` INT NOT NULL DEFAULT 0 COMMENT '推荐因数：越高越在上面' AFTER `recommended`;
ALTER TABLE `playlist`
    ADD COLUMN `recommended`      TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否推荐：推荐：1； 不推荐：0；默认：0' AFTER `status`,
    ADD COLUMN `recommend_factor` INT        NOT NULL DEFAULT 0 COMMENT '推荐因数：越高越在上面' AFTER `recommended`;
ALTER TABLE `playlist`
    ADD COLUMN `special` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否特色歌单：特色歌单：1； 否则：0；默认：0' AFTER `recommended`;
ALTER TABLE `album`
    ADD COLUMN `recommended` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否推荐：推荐：1； 不推荐：0；默认：0' AFTER `status`;
ALTER TABLE `album`
    ADD COLUMN `recommend_factor` INT NOT NULL DEFAULT 0 COMMENT '推荐因数：越高越在上面' AFTER `recommended`;
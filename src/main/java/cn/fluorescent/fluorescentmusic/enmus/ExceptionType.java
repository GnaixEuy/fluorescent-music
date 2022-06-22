package cn.fluorescent.fluorescentmusic.enmus;

import lombok.Getter;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/6
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Getter
public enum ExceptionType {

    /**
     * 枚举业务异常信息
     */
    INNER_ERROR(500, "系统内部错误,联系支持"),
    UNAUTHORIZED(401, "未登录"),
    BAD_REQUEST(400, "请求错误"),
    FORBIDDEN(403, "无权操作"),
    NOT_FOUND(404, "未找到"),
    USER_NAME_DUPLICATE(40001001, "用户名重复"),
    USER_NOT_FOUND(40401002, "用户不存在,请注册"),
    USER_PASSWORD_NOT_MATCH(40001003, "用户名或密码错误"),
    USER_NOT_ENABLED(50001001, "用户未启用"),
    USER_LOCKED(50001002, "用户被锁定"),
    USER_INSERT_ERROR(40001077, "创建用户信息失败"),
    USER_UPDATE_ERROR(50001040, "用户更新失败"),
    USER_DELETE_ERROR(50001070, "用户删除失败"),
    ROLE_DELETE_ERROR(50001080, "角色信息删除失败"),
    USER_ROLE_BIND_ERROR(50001081, "用户角色信息绑定失败"),

    MUSIC_NOT_FOUND(40402002, "音乐信息不存在"),
    MUSIC_INSERT_ERROR(40002077, "创建音乐信息失败"),
    MUSIC_UPDATE_ERROR(50002040, "音乐更新失败"),
    MUSIC_DELETE_ERROR(50002070, "音乐删除失败"),
    FILE_NOT_FOUND(40403001, "文件不存在"),
    FILE_UPLOAD_ERROR(40403002, "文件上传失败"),
    FILE_EMPTY(40403003, "文件为空"),

    FILE_NOT_PERMISSION(40303002, "当前用户无权限修改文件"),
    PLAYLIST_NOT_FOUND(40404001, "歌单不存在"),
    PLAYLIST_DELETE_ERROR(40404003, "歌单删除失败"),
    PLAYLIST_CREATE_ERROR(40404002, "歌单创建失败"),
    PLAYLIST_UPDATE_ERROR(40404004, "歌单更新失败"),
    ARTIST_INSERT_ERROR(40405003, "歌手创建失败"),
    ARTIST_DELETE_ERROR(40405005, "歌手移除失败"),
    ARTIST_UPDATE_ERROR(40405006, "歌手信息更新失败"),
    ARTIST_ALREADY_REGISTERED(40405007, "歌手已注册"),
    ARTIST_NOT_FOUND(40405001, "歌手不存在");


    private final Integer code;
    private final String message;


    ExceptionType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
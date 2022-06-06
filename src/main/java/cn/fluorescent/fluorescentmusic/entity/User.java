package cn.fluorescent.fluorescentmusic.entity;

import cn.fluorescent.fluorescentmusic.enmu.Gender;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/6
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString(callSuper = true)
@TableName(value = "user", resultMap = "userResultMap")
public class User extends BaseEntity implements UserDetails {

    @TableField
    private String username;
    @TableField
    private String nickname;

    @TableField
    private String password;

    private Gender gender;

    private Boolean locked = false;

    private Boolean enabled = true;

    private String lastLoginIp;

    private Date lastLoginTime;

    @TableField(exist = false)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : this.roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !getLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Gender getGender() {
        return gender;
    }

    public Boolean getLocked() {
        return locked;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public List<Role> getRoles() {
        return roles;
    }

}
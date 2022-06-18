package cn.fluorescent.fluorescentmusic.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <img src="http://blog.gnaixeuy.cn/wp-content/uploads/2022/06/bug.png"/>
 *
 * <p>项目： fluorescent-music </p>
 *
 * @author GnaixEuy
 * @date 2022/6/13
 * @see <a href="https://github.com/GnaixEuy"> GnaixEuy的GitHub </a>
 */
@Configuration

public class  WeChatConfig {

    @Value(value = "${weixin.gzh.app.id}")
    private String id;

    @Value(value = "${weixin.gzh.app.secret}")
    private String secret;

    @Bean
    public WxMpService wxMpService() {
        final WxMpService wxMpService = new WxMpServiceImpl();
        final WxMpDefaultConfigImpl wxMpDefaultConfig = new WxMpDefaultConfigImpl();
        wxMpDefaultConfig.setAppId(this.id);
        wxMpDefaultConfig.setSecret(this.secret);
        wxMpService.setWxMpConfigStorage(wxMpDefaultConfig);
        return wxMpService;
    }



}
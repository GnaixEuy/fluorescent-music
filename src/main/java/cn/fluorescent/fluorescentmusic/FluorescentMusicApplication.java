package cn.fluorescent.fluorescentmusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FluorescentMusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(FluorescentMusicApplication.class, args);
    }

}

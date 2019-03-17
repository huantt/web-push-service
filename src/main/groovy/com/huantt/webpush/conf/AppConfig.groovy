package com.huantt.webpush.conf

import external.service.Save2GoogleSheet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

/**
 * @author huantt on 11/5/18
 */

@Configuration
class AppConfig {

    @Autowired
    Environment env

    @Bean
    Save2GoogleSheet clientSheet() {
        return new Save2GoogleSheet(env.getProperty('googlesheet.clients.uri'))
    }
}

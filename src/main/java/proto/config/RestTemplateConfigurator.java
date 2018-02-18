package proto.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplateの設定
 */
@Configuration
public class RestTemplateConfigurator {
    /**
     * トークン取得用 URL
     */
    private final String TOKEN_URL   = "https://api.recaius.jp/auth/v2/tokens";

    /**
     * 口語翻訳実行用 URL
     */
    private final String REQUEST_URL = "https://api.recaius.jp/mt/v2/translate";

    /**
     * 接続タイムアウト
     */
    private final int CONNECT_TIMEOUT = 3000;

    /**
     * 読み取りタイムアウト
     */
    private final int READ_TIMEOUT = 5000;

    /**
     * トークン取得用 RestTemplate
     * @param builder
     * @return
     */
    @Bean
    public RestTemplate requestToken(RestTemplateBuilder builder) {
        return builder
                .rootUri(TOKEN_URL)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setReadTimeout(READ_TIMEOUT)
                .build();
    }

    /**
     * 口語翻訳実行用 RestTemplate
     * @param builder
     * @return
     */
    @Bean
    public RestTemplate requestTranslate(RestTemplateBuilder builder) {
        return builder
                .rootUri(REQUEST_URL)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setReadTimeout(READ_TIMEOUT)
                .build();
    }
}

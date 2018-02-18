package proto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.web.client.RestTemplate;
import proto.dataset.*;

@Slf4j
@SpringBootApplication
public class ProtoApplication {
    /**
     * サービス利用ID
     */
    private static final String SERVICE_ID       = "";

    /**
     * サービス利用パスワード
     */
    private static final String SERVICE_PASSWORD = "";

    /**
     * Springコンテキストの初期化
     * @param args
     */
	public static void main(String[] args) {
		SpringApplication.run(ProtoApplication.class, args);
	}

    /**
     * RestTemplateのヘッダーにトークンを追加
     * @param restTemplate
     * @param token
     * @return
     */
    private RestTemplate setToken(RestTemplate restTemplate, Token token){
        restTemplate.getInterceptors().add((HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) -> {
            request.getHeaders().set("X-Token", token.getToken());
            return execution.execute(request, body);
        });
        return restTemplate;
    }

    /**
     * 口語翻訳を実行
     * @param requestToken
     * @param requestTranslate
     * @return
     * @throws Exception
     */
    @Bean
    public CommandLineRunner run(RestTemplate requestToken, RestTemplate requestTranslate) throws Exception {
        return args -> {
            // トークンの取得
            RecaiusService service = new RecaiusService(new User(SERVICE_ID, SERVICE_PASSWORD));
            Token token = requestToken.postForObject("/", service, Token.class);
            log.info(token.toString());

            // 口語翻訳の実行
            Query query = new Query("えー、東京タワーはどこですか？");
            Translated result = setToken(requestTranslate, token).postForObject("/", query, Translated.class);
            log.info( "口語翻訳: " + query.getQuery() + " -> "
                    + result.getData().getTranslations().get(0).getTranslatedText() );

            // トークンの破棄
            setToken(requestToken, token).delete("/");
        };
    }
}

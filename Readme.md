Spring Javaによる口語翻訳のサンプル
====

実行方法
----

コンパイルと実行には Java 8以降の Javaを利用してください。

`ProtoApplication.java` の `サービス利用ID` と `サービス利用パスワード` を
お使いのものを設定してください。

翻訳したい文章は、 `ProtoApplication.java` の 65行目の様に指定してください。

### Windowsの場合

コマンドプロンプトで以下を実行します。

```
> gradlew bootRun
```

### Mac または Linux の場合

ターミナルで以下を実行します。

```
$ ./gradlew bootRun
```

実行結果例
----

```
:compileJava
:processResources
:classes
:findMainClass
:bootRun

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::       (v1.5.10.RELEASE)

2018-02-18 23:40:35.466  INFO 12226 --- [           main] proto.ProtoApplication                   : Starting ProtoApplication on xx with PID 12226 (spring-java-translate/build/classes/java/main started by xx in spring-java-translate)
2018-02-18 23:40:35.470  INFO 12226 --- [           main] proto.ProtoApplication                   : No active profile set, falling back to default profiles: default
2018-02-18 23:40:35.555  INFO 12226 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@73d4cc9e: startup date [Sun Feb 18 23:40:35 JST 2018]; root of context hierarchy
2018-02-18 23:40:36.939  INFO 12226 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
2018-02-18 23:40:37.712  INFO 12226 --- [           main] proto.ProtoApplication                   : Token(expiry_sec=600, token=4c2a079e-d0b7-4cd4-8123-xx)
2018-02-18 23:40:37.902  INFO 12226 --- [           main] proto.ProtoApplication                   : 口語翻訳: えー、東京タワーはどこですか？ -> Where is Tokyo Tower?
2018-02-18 23:40:37.942  INFO 12226 --- [           main] proto.ProtoApplication                   : Started ProtoApplication in 3.216 seconds (JVM running for 3.782)
2018-02-18 23:40:37.943  INFO 12226 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@73d4cc9e: startup date [Sun Feb 18 23:40:35 JST 2018]; root of context hierarchy
2018-02-18 23:40:37.945  INFO 12226 --- [       Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown

BUILD SUCCESSFUL in 13s
4 actionable tasks: 4 executed

```

連続で翻訳する場合の記述例
----

```java
            // トークンの取得
            RecaiusService service = new RecaiusService(new User(SERVICE_ID, SERVICE_PASSWORD));
            Token token = requestToken.postForObject("/", service, Token.class);
            log.info(token.toString());

            // 翻訳のリクエストにトークンを設定
            RestTemplate requestTranslateWithToken = setToken(requestTranslate, token);

            // 口語翻訳の実行
            // デフォルトでは日英翻訳を行います
            Query query = new Query("えー、東京タワーはどこですか？");
            Translated result = requestTranslateWithToken.postForObject("/", query, Translated.class);
            log.info( query.getQuery() + " -> " + result.getData().getTranslations().get(0).getTranslatedText() );

            query = new Query("お好み焼きはどこで買えますか？");
            result = requestTranslateWithToken.postForObject("/", query, Translated.class);
            log.info( query.getQuery() + " -> " + result.getData().getTranslations().get(0).getTranslatedText() );

            query = new Query("もうこれ以上食べれません。");
            result = requestTranslateWithToken.postForObject("/", query, Translated.class);
            log.info( query.getQuery() + " -> " + result.getData().getTranslations().get(0).getTranslatedText() );

            // 翻訳後の言語の設定は以下のように行ないます
            query.setTgt_lang("zh");
            result = requestTranslateWithToken.postForObject("/", query, Translated.class);
            log.info( query.getQuery() + " -> " + result.getData().getTranslations().get(0).getTranslatedText() );

            query.setTgt_lang("ko");
            result = requestTranslateWithToken.postForObject("/", query, Translated.class);
            log.info( query.getQuery() + " -> " + result.getData().getTranslations().get(0).getTranslatedText() );

            // 英日翻訳を行う場合は以下のように行ないます
            query = new Query("Really?");
            query.setSrc_lang("en");
            query.setTgt_lang("ja");
            result = requestTranslateWithToken.postForObject("/", query, Translated.class);
            log.info( query.getQuery() + " -> " + result.getData().getTranslations().get(0).getTranslatedText() );

            // トークンの破棄
            setToken(requestToken, token).delete("/");
```

以上。

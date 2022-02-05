import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpClient;

import java.util.*;

public class Main {
    public static final String LINK = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent("Рандомный текст")
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30_000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        //TODO чекнуть дефолтное установление.

        HttpGet request = new HttpGet(LINK);/*создаем объект запроса*/
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        CloseableHttpResponse response = httpClient.execute(request);

        System.out.println(response.getStatusLine()+"\n\n");
        Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

        List<Cat> cats = mapper.readValue(
                response.getEntity().getContent(),
                new TypeReference<>() {
                }
        );
        cats.stream()
                .filter(value -> value.getUpvotes() != null)
                .forEach(System.out::println);

        response.close();
        httpClient.close();
    }
}

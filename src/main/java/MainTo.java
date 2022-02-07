import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class MainTo {

    public static final String LINK = "https://api.nasa.gov/planetary/apod?api_key=kQledRwJWIiKdv8buA45tWpvcNVHMPn4JDpwKhJF";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent("text")
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();

        //создаем запрос
        HttpGet request = new HttpGet(LINK);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        CloseableHttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine()+"\n");
        Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
       //String test = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
        AnswerNasa nasa = mapper.readValue(
                response.getEntity().getContent(), new TypeReference<AnswerNasa>() {
                }
        );
        System.out.println(nasa);
        saveFile(nasa.getUrl(), "photo.jpg");

        response.close();
        httpClient.close();
    }

    public static void saveFile(String url, String path){
      try(BufferedInputStream inputStream = new BufferedInputStream(new URL(url).openStream())){
          FileOutputStream fileOutputStream = new FileOutputStream(path,false);
          int count;
          while ((count = inputStream.read()) != -1) {
               fileOutputStream.write(count);
               fileOutputStream.flush();}
      }catch (MalformedURLException e){
          System.out.println(e.getMessage());
      }catch (IOException e){
          System.out.println(e.getMessage());
      }
    }
}

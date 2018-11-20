package com.yufan;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Ignore
public class TestHttpClient {


    //不带参数的get请求
    @Test
    public void doGet() throws IOException {

        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建get请求
        //相当于在浏览器输入的地址
        HttpGet httpGet=new HttpGet("http://admin.yufan.com/rest/item/list");
        //发送get请求获取响应
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        if(httpResponse.getStatusLine().getStatusCode()==200){
            //请求成功
            HttpEntity entity = httpResponse.getEntity();
            System.out.println(EntityUtils.toString(entity));
        }

    }


    //模拟带参数的get请求
    @Test
    public void doGet2() throws IOException, URISyntaxException {
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //创建get请求
        //相当于在浏览器输入的地址

        URI uri=new URIBuilder("http://admin.yufan.com/rest/item/list")
                .setParameter("page","1").setParameter("rows","1").build();

        HttpGet httpGet=new HttpGet(uri);

        //发送get请求获取响应
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        if(httpResponse.getStatusLine().getStatusCode()==200){
            //请求成功
            HttpEntity entity = httpResponse.getEntity();
            System.out.println(EntityUtils.toString(entity));
        }

    }


    @Test
    public void testPost() throws IOException {

        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //构建post请求
        HttpPost httpPost = new HttpPost("http://admin.yufan.com/rest/item/list");

        //构建请求体参数
        List<NameValuePair> pairList=new ArrayList<NameValuePair>();
        pairList.add(new BasicNameValuePair("page","1"));
        pairList.add(new BasicNameValuePair("rows","1"));
        //构建请求体
        UrlEncodedFormEntity formEntity=new UrlEncodedFormEntity(pairList);
        httpPost.setEntity(formEntity);

        CloseableHttpResponse httpResponse =httpClient.execute(httpPost);
        if(httpResponse.getStatusLine().getStatusCode()==200){
            //请求成功
            HttpEntity entity = httpResponse.getEntity();
            System.out.println(EntityUtils.toString(entity));
        }
    }

}

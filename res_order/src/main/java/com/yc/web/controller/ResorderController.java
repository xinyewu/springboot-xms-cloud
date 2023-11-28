package com.yc.web.controller;

import com.yc.api.ResfoodApi;
import com.yc.bean.Resfood;
import feign.Client;
import feign.Feign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("resorder")
@Slf4j
public class ResorderController {
    @Autowired
    private RestTemplate restTemplate;//spring的东西，不是cloud的东西
    @Autowired
    private ResfoodApi resfoodApi;
//    public ResorderController(){
//        this.resfoodApi = Feign.builder()
//                .client(new OkHttpClient())
//                .decoder(new GsonDecoder())
//                .contract(new JAXRSContract())
//                .target(GitHub2_javax.class, "https://api.github.com");
//    }


    //@RolesAllowed(value = "r1")
    @RequestMapping(value = "addCart", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> addCart(@RequestParam Integer fid, @RequestParam Integer num, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        //方案一:直接使用服务ip：端口，访问固定的一个服务结点
        //Map<String,Object> result=this.restTemplate.getForObject("http://localhost:9000/resfood/findById/"+fid,Map.class);
        //方案二：利用@LoadBalanced
        //String url = "http://resfood/resfood/findById/" + fid;
        //Map<String, Object> result = this.restTemplate.getForObject(url, Map.class);
        //方案三：利用openfeign发送请求
        Map<String, Object> result = this.resfoodApi.findById(fid);
        log.info("发送请求后得到商品信息：" + result);
        return map;
    }
}

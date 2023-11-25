package com.yc.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("resorder")
@Slf4j
public class ResorderController {
    @Autowired
    private RestTemplate restTemplate;//spring的东西，不是cloud的东西

    @RequestMapping(value = "addCart", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> addCart(@RequestParam Integer fid,@RequestParam Integer num, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        //方案一:直接使用服务ip：端口，访问固定的一个服务结点
        //Map<String,Object> result=this.restTemplate.getForObject("http://localhost:9000/resfood/findById/"+fid,Map.class);
        //方案二：利用@LoadBalanced
        String url="http://resfood/resfood/findById/"+fid;
        Map<String,Object> result=this.restTemplate.getForObject(url,Map.class);
        log.info("发送请求后得到商品信息："+result);
        return map;
    }
}

package com.yc.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yc.api.ResfoodApi;
import com.yc.bean.Resfood;
import com.yc.biz.GoodsBiz;
import com.yc.web.model.CartItem;
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
import java.util.Random;


@RestController
@RequestMapping("resorder")
@Slf4j
public class ResorderController {
    @Autowired
    private RestTemplate restTemplate;//spring的东西，不是cloud的东西
    @Autowired
    private ResfoodApi resfoodApi;
    @Autowired
    private GoodsBiz goodsBiz;

//    public ResorderController() {
//        this.resfoodApi = Feign.builder()
//                .client(new OkHttpClient())
//                .decoder(new GsonDecoder())
//                .contract(new JAXRSContract())
//                .target(GitHub2_javax.class, "https://api.github.com");
//    }
    //链路测试
    @RequestMapping(value = "serviceA", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> serviceA() {
        Map<String, Object> map = new HashMap<>();
        goodsBiz.goodsInfo();
        map.put("code", 1);
        return map;
    }

    @RequestMapping(value = "serviceB", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> serviceB() {
        Map<String, Object> map = new HashMap<>();
        goodsBiz.goodsInfo();
        map.put("code", 1);
        return map;
    }

    @GetMapping("payAction")
    public Map<String, Object> payAction(Integer flag) throws InterruptedException {
        //Thread.sleep(1000);  慢调用
        Random r = new Random();//异常数
        int a = r.nextInt(5);
        if (a == 0 || a == 1) {
            throw new RuntimeException("异常了");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        return map;
    }

    @RequestMapping(value = "getCartInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> getCartInfo(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (session.getAttribute("cart") == null || ((Map<Integer, CartItem>) session.getAttribute("cart")).size() <= 0) {
            map.put("code", 0);
            return map;
        }
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        map.put("code", 1);
        map.put("obj", cart.values());//返回的是map的值的set
        return map;
    }

    @RequestMapping(value = "clearAll", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> clearAll(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        session.removeAttribute("cart");
        map.put("code", 1);
        return map;
    }

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
        if (result == null) {
            map.put("code", -1);
            map.put("msg", "查无此商品");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Resfood food = objectMapper.convertValue(result.get("obj"), Resfood.class);

        //从session取出Cart(map)
        Map<Integer, CartItem> cart = new HashMap<Integer, CartItem>();
        if (session.getAttribute("cart") != null) {
            cart = (Map<Integer, CartItem>) session.getAttribute("cart");//引用对象
        } else {
            session.setAttribute("cart", cart);
        }
        CartItem ci;
        //  判断这个商品在map中是否有
        if (cart.containsKey(fid)) {
            //有，加数量，
            ci = cart.get(fid);
            ci.setNum(ci.getNum() + num);
            cart.put(fid, ci);
        } else {
            //没有，则创建一个CartItem,存到map中
            ci = new CartItem();
            ci.setNum(num);
            ci.setFood(food);
            cart.put(fid, ci);
        }
        //处理数量
        if (ci.getNum() <= 0) {
            cart.remove(fid);
        }
        session.setAttribute("cart", cart);//->spring httpSession redis->  监听器监听值的变化
        map.put("code", 1);
        map.put("obj", cart.values());
        return map;
    }
}

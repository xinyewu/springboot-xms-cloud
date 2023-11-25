package com.yc;

import feign.Feign;
import feign.gson.GsonDecoder;

import java.util.List;

public class MyApp {
    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        GitHub github = Feign.builder()
                .decoder(new GsonDecoder())//解码器
                .target(GitHub.class, "https://api.github.com");
        List<Contributor> contributors = github.contributors("OpenFeign", "feign");
        for (Contributor contributor : contributors) {
            System.out.println(contributor);
        }
    }
}
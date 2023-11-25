package com.yc;

import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface GitHub {
    // 传入的参数后可以拼接成url: https://api.github.com/repos/OpenFeign/feign/contributors
    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);

    //@RequestMapping(value="/repos/{owner}/{repo}/contributors",method={GET})
    //List<Contributor> contributors(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
}

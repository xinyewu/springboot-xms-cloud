package com.yc.api;

import com.yc.api.configuration.ResorderApiConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
@FeignClient(value = "resorder",configuration = ResorderApiConfiguration.class)
public interface ResorderApi {
    @RequestMapping(value = "addCart", method = {RequestMethod.GET})
    public Map<String, Object> addCart(@RequestParam Integer fid, @RequestParam Integer num);
}

package com.yc.api;

import com.yc.bean.Resfood;
import com.yc.web.model.MyPageBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@FeignClient("resfood")
public interface ResfoodApi {
    @RequestMapping(value = "resfood/detailCountAdd",method = {RequestMethod.GET})
    public Map<String, Object> detailCountAdd(Integer fid);

    @RequestMapping(value = "resfood/findById/{fid}",method = {RequestMethod.GET})//{fid}路径参数
    public Map<String, Object> findById(@PathVariable Integer fid);

    @GetMapping(value = "resfood/findAll")
    public Map<String, Object> findAll();

    @RequestMapping(value = "resfood/findPage", method = {RequestMethod.GET})
    public Map<String, Object> findByPage(@RequestParam("pageno") int pageno, @RequestParam("pagesize") int pagesize, @RequestParam(required = false) String sortby, @RequestParam(required = false) String sort);
}

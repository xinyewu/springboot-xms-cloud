package com.yc.web.controller;

import com.yc.bean.Resfood;
import com.yc.biz.ResfoodBizImpl;
import com.yc.config.RedisKeys;
import com.yc.web.model.MyPageBean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resfood")//localhost:9000/resfood/+xxx
@Slf4j
//@Api(tags = "菜品管理")
public class ResfoodController {

    @Autowired
    private ResfoodBizImpl resfoodBiz;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/detailCountAdd",method = {RequestMethod.GET,RequestMethod.POST})
   // @ApiOperation(value = "查看详情次数增加")
   // @ApiImplicitParams({@ApiImplicitParam(name = "fid", value = "菜品号", required = true)})
    public Map<String, Object> detailCountAdd(Integer fid){
        Map<String,Object>map=new HashMap<>();
        Long count=1L;
        if (redisTemplate.hasKey(RedisKeys.RESFOOD_DETAIL_COUNT_FID_+fid)==false){
            redisTemplate.opsForValue().set(RedisKeys.RESFOOD_DETAIL_COUNT_FID_+fid,1);
        }else {
            count=redisTemplate.opsForValue().increment(RedisKeys.RESFOOD_DETAIL_COUNT_FID_+fid,1);
        }
        map.put("code",1);
        map.put("obj",count);
        return map;
    }

    @RequestMapping(value = "findAll", method = {RequestMethod.GET, RequestMethod.POST})
   // @ApiOperation(value = "查询所有菜品")
    public List<Resfood> findAll() {
        return resfoodBiz.findAll();
    }

    //@RequestMapping(value = "findById/{fid}",method = {RequestMethod.GET,RequestMethod.POST})//localhost:9000/resfood/findById/1
    @GetMapping("findById/{fid}")//{fid}路径参数
   // @ApiOperation(value = "根据菜品编号查询操作")
   // @ApiImplicitParams({@ApiImplicitParam(name = "fid", value = "菜品号", required = true)})
    public Resfood findById(@PathVariable Integer fid) {
        return resfoodBiz.findById(fid);
    }


    @RequestMapping(value = "findPage", method = {RequestMethod.GET, RequestMethod.POST})
   // @ApiOperation(value = "分页查询操作")
    //@ApiImplicitParams({
    //        @ApiImplicitParam(name = "pageno", value = "页号", required = true),
    //        @ApiImplicitParam(name = "pagesize", value = "每页记录数", required = true),
     //       @ApiImplicitParam(name = "sortby", value = "排序列", required = false),
      //      @ApiImplicitParam(name = "sort", value = "排序方式", required = false)
  //  })
    public MyPageBean findByPage(@RequestParam("pageno") int pageno, @RequestParam("pagesize") int pagesize, @RequestParam(required = false) String sortby, @RequestParam(required = false) String sort) {
//        Map<String,Object>map=new HashMap<>();
//        MyPageBean page=null;
//        try {
//            page=this.resfoodBiz.findByPage(pageno, pagesize, sortby, sort);
//        }catch (Exception e){
//            map.put("code",0);
//            map.put("msg",e.getCause());
//            e.printStackTrace();
//            return map;
//        }
//        map.put("code",1);
//        map.put("data",page);
//        return map;
        return resfoodBiz.findByPage(pageno, pagesize, sortby, sort);
    }
}

package com.egbert.apitest.system.controller;

import com.egbert.apitest.common.exception.ApitestException;
import com.egbert.apitest.common.result.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.egbert.apitest.model.sys.SystemSet;
import com.egbert.apitest.system.service.SystemSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Api(tags="系统设置管理")
@RestController
@RequestMapping("/api/sys/systemSet")
public class SystemSetController {

    //注入service
    @Autowired
    private SystemSetService systemSetService;

    // http://localhost:8201/sys/systemSet/findAll

    //1 查询系统设置表所有信息
    @ApiOperation(value = "获取所有系统设置")
    @GetMapping("findAll")
    public Result findAllSystemSet() {
        //调用service的方法
        List<SystemSet> list = systemSetService.list();
        return Result.ok(list);
    }

    //2 逻辑删除医院设置
    @ApiOperation(value = "逻辑删除系统设置")
    @DeleteMapping("delete/{id}")
    public Result removeSystemSet(@PathVariable Long id) {
        boolean flag = systemSetService.removeById(id);
        if(flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //3 条件查询带分页
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("findPageSystemSet/{current}/{limit}")
    public Result findPageSystemSet(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) com.egbert.apitest.vo.sys.SystemSetQueryVo systemSetQueryVo) {
        //创建page对象，传递当前页，每页记录数
        Page<SystemSet> page = new Page<>(current,limit);
        //构建条件
        QueryWrapper<SystemSet> wrapper = new QueryWrapper<>();
        String sysname = systemSetQueryVo.getSysname();//x系统名称
        String sysdescription = systemSetQueryVo.getSysdescription();//系统编号
        if(!StringUtils.isEmpty(sysname)) {
            wrapper.like("sysname",systemSetQueryVo.getSysname());
        }
        if(!StringUtils.isEmpty(sysdescription)) {
            wrapper.like("description",systemSetQueryVo.getSysdescription());
        }

        //调用方法实现分页查询
        IPage<SystemSet> pageSystemSet = systemSetService.page(page, wrapper);

        //返回结果
        return Result.ok(pageSystemSet);
    }

    //4 添加系统设置
    @ApiOperation(value = "添加系统设置")
    @PostMapping("addSystemSet")
    public Result saveSystemSet(@RequestBody SystemSet systemSet) {
        //设置状态 1 使用 0 不能使用
        systemSet.setStatus(1);
        //调用service
        boolean save = systemSetService.save(systemSet);
        if(save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //5 根据id获取系统设置
    @ApiOperation(value = "根据id获取系统设置")
    @GetMapping("getSysSet/{id}")
    public Result getSysSet(@PathVariable Long id) {
//        try {
//            //模拟异常
//            int a = 1/0;
//        }catch (Exception e) {
//            throw new ApitestException("失败",201);
//        }

        SystemSet systemSet = systemSetService.getById(id);
        return Result.ok(systemSet);
    }

    //6 修改系统设置
    @ApiOperation(value = "修改系统设置")
    @PostMapping("updateSystemSet")
    public Result updateSystemSet(@RequestBody SystemSet systemSet) {
        boolean flag = systemSetService.updateById(systemSet);
        if(flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //7 批量删除系统设置
    @ApiOperation(value = "批量删除系统设置")
    @DeleteMapping("batchRemove")
    public Result batchRemoveSystemSet(@RequestBody List<Long> idList) {
        systemSetService.removeByIds(idList);
        return Result.ok();
    }

    //8 系统设置锁定和解锁
    @ApiOperation(value = "系统设置锁定和解锁")
    @PutMapping("lockSystemSet/{id}/{status}")
    public Result lockSystemSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        //根据id查询医院设置信息
        SystemSet systemSet = systemSetService.getById(id);
        //设置状态
        systemSet.setStatus(status);
        //调用方法
        systemSetService.updateById(systemSet);
        return Result.ok();
    }

}

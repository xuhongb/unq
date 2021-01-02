package com.annual.controller;


import com.annual.entity.Prize;
import com.annual.entity.PrizeList;
import com.annual.entity.User;
import com.annual.entity.UserList;
import com.annual.service.IPrizeListService;
import com.annual.service.IPrizeService;
import com.annual.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xhb
 * @since 2020-12-14
 */
@RestController
@RequestMapping("/prize")
public class PrizeController {
    @Autowired(required = false)
    private IPrizeService iPrizeService;
    @Autowired(required = false)
    private IPrizeListService iPrizeListService;
    @Autowired(required = false)
    private IUserService iUserService;
    @GetMapping("/status")
    public Prize getStatus(){
        return  iPrizeService.getById(1);
    }

    @PostMapping("/updateStatus")
    public String updateStatus(Prize prize){
        if (prize.getStatus() == 5){
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("status1",66).set("status1",1).
                    set("status1",1);
            iUserService.update(updateWrapper);


        }
        iPrizeService.updateById(prize);

        return  "success";
    }
    @GetMapping("/listLast/{lastId}")
    public UserList listLast(@PathVariable Integer lastId){

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("status1",99).ne("status1",66).
                isNotNull("last_name");
        if (lastId > 0){
            queryWrapper.gt("id",lastId);
        }
        List<User> list = iUserService.list(queryWrapper);
        if(list !=null && list.size()>0){
            for (User user:list){
                if (user.getId()>lastId){
                    lastId = user.getId();
                }
                user.setStatus(1);
            }
            iUserService.saveOrUpdateBatch(list);
        }
        UserList userList = new UserList();
        userList.setLastId(lastId);
        userList.setList(list);
        return userList;
    }
    @GetMapping("/user/{phone}")
    public User getPrizeUser(@PathVariable String phone){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        User user = iUserService.getOne(queryWrapper);
        if(user !=null ){
            //user.setStatus(66);//中奖
            user.setStatus1(66);//中奖
            iUserService.updateById(user);
            PrizeList prizeList = new PrizeList();
            prizeList.setUserId(user.getId());
            prizeList.setName(user.getLastName()+user.getFirstName());
            prizeList.setPhone(phone);
            prizeList.setEntryTime(user.getEntryTime());
            prizeList.setInsertTime(new Date());
            iPrizeListService.save(prizeList);
        }else{
            System.out.println("没有此人");
        }
        return  user;
    }
    @GetMapping("/prizeList")
    public List<PrizeList> getPrizeList(){
        return iPrizeListService.list();
    }
}

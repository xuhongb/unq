package com.annual.controller;


import com.annual.entity.EventList;
import com.annual.entity.User;
import com.annual.entity.UserEvent;
import com.annual.entity.UserList;
import com.annual.service.IEventListService;
import com.annual.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xhb
 * @since 2020-12-10
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired(required = false)
    private IUserService iUserService;
    @Autowired(required = false)
    private IEventListService iEventListService;
    @PostMapping("/user")
    public String saveUser(User user){
        logger.info(user.toString());
        if (user!=null && !user.getPhone().isEmpty()){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone",user.getPhone()).ne("status",99);
            User user1 = iUserService.getOne(queryWrapper);
            if (user1!= null){
                user.setStatus(user1.getStatus());
                user.setStatus1(user1.getStatus1());
                UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("phone",user.getPhone());
                user.setInsertDate(new Date());
                iUserService.update(user,updateWrapper);
            }else{
                user.setStatus(0);
                user.setStatus1(0);
                user.setInsertDate(new Date());
                iUserService.save(user);
            }
            return "success";

        }
        return  "error";
    }

    @GetMapping("/clear")
    public String deleteUser(){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status",99).set("status1",99);
        if (iUserService.update(updateWrapper))
            return "success";
        return "error";
    }

    @GetMapping("/phone/{phone}")
    public User getId(@PathVariable String phone){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone).ne("status",99);

        return iUserService.getOne(queryWrapper);
    }

    @GetMapping("/listLast/{lastId}")
    public UserList listLast(@PathVariable Integer lastId){

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("status",99).isNotNull("last_name");
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

    @GetMapping("/list/{status}")
    public List<User> getUserList(@PathVariable String status){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",status).isNotNull("last_name");
        List<User> list = iUserService.list(queryWrapper);
        if (!status.isEmpty()&& status.equals("0")){
            if(list !=null && list.size()>0){
                for (User user:list){
                    user.setStatus(1);
                }
                iUserService.saveOrUpdateBatch(list);
            }
        }
        return list;
    }
    @GetMapping("/listPrize/{status}")
    public List<User> getUserPrizeList(@PathVariable String status){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status1",status).isNotNull("last_name");
        List<User> list = iUserService.list(queryWrapper);
        if (!status.isEmpty()&& status.equals("0")){
            if(list !=null && list.size()>0){
                for (User user:list){
                    //抽奖界面的状态
                    user.setStatus1(1);
                }
                iUserService.saveOrUpdateBatch(list);
            }
        }
        return list;
    }
    @GetMapping("/event")
    public UserEvent getUserInfo(String year, String phone) throws ParseException {
        UserEvent event = new UserEvent();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",phone);
        User user = iUserService.getOne(queryWrapper);
       /* SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startStr = user.getEntryTime()+"-01-01 00:00:00";
        Date startDate = format.parse(startStr);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(startDate);
        int startYear = cal1.get(Calendar.YEAR);
        event.setPhone(phone);
        event.setYear(year);
        int dayNum = (int)((new Date().getTime()-startDate.getTime())/(1000*3600*24));*/
        if (user.getEntryTime().equals(year)){
            event.setFlag(true);
            event.setName(user.getLastName()+user.getFirstName());
            /*event.setMonth((cal1.get(Calendar.MONTH) +1)+"");
            event.setDay(dayNum +"");*/
        }
        else {
            event.setFlag(false);
        }

        QueryWrapper<EventList> eventWrapper = new QueryWrapper<>();
        eventWrapper.eq("year",year);
        EventList  lists = iEventListService.getOne(eventWrapper);
        event.setLists(lists.getContent());
        return event;
    }

}

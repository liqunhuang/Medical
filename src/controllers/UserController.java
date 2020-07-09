package controllers;

import com.google.gson.Gson;
import dao.DBHelper;
import models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jenifer
 * Date: 7/4/2020
 * Time: 8:59 PM
 * Project: Medical
 **/
@Controller
@RequestMapping(value ={"/user"})
public class UserController {

    @RequestMapping(value = {"/login"})
    public void login(User user, HttpSession session, HttpServletResponse response)
    {
        Hashtable ht = new Hashtable();
        ht.put("success",false);
        ht.put("msg","Login failed!");
        Gson gson = new Gson();

        String username = user.getUsername();
        String password = user.getPassword();
        if(username==null || password==null)
        {
            ht.replace("success",false);
            ht.replace("msg","参数错误");
        }
        else {
            if (username.isEmpty() || password.isEmpty()) {
                ht.replace("success",false);
                ht.replace("msg","用户名和密码不能为空");
            }
            else
            {
                try {
                    //查数据 库
                    User userDb =  DBHelper.getUser(username);
                    if(userDb.getUsername()==null || userDb.getUsername().isEmpty())
                    {
                        ht.replace("success",false);
                        ht.replace("msg","User not exist");
                    }
                    else
                    {
                        if(userDb.getPassword().equals(password))
                        {
                            ht.replace("success",true);
                            ht.replace("msg","Login OK");
                            //  Set Session
                            session.setAttribute("user",userDb);
                        }
                        else
                        {
                            ht.replace("success",false);
                            ht.replace("msg","Password Error");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            String result = gson.toJson(ht);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(result);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = {"/adduser"})
    public void addUser(User userinfo, HttpSession session, HttpServletResponse response)
    {
        Hashtable ht = new Hashtable();
        ht.put("success",false);
        ht.put("msg","");
        Gson gson = new Gson();
        response.setContentType("text/html;charset=utf-8");
        try
        {
            if(null == session.getAttribute("user"))   //未登录，
            {
                ht.replace("success",false);
                ht.replace("msg","用户未登录，请先登录。");
                response.getWriter().write(gson.toJson(ht));
                return;
            }
            else
            {
                //插入用户到数据库，先判断新插入的用户是否已存在。
                if(DBHelper.isUserExist(userinfo.getUsername()))
                {
                    ht.replace("success",false);
                    ht.replace("msg","该用户已存在，请重试。");
                    response.getWriter().write(gson.toJson(ht));
                    return;
                }
                if(DBHelper.addUser(userinfo))
                {
                    ht.replace("success",true);
                    ht.replace("msg","添加用户成功。");
                    response.getWriter().write(gson.toJson(ht));
                    return;
                }
                else
                {
                    ht.replace("success",false);
                    ht.replace("msg","添加用户失败，请重试。");
                    response.getWriter().write(gson.toJson(ht));
                    return;
                }
            }
        }
        catch (Exception e)
        {
            ht.replace("success",false);
            ht.replace("msg","添加用户失败，请重试。");
            try {
                response.getWriter().write(gson.toJson(ht));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;
        }
    }

    @RequestMapping(value={"/usermgr"})
    @ResponseBody
    public ModelAndView userMgr(User userinfo,HttpSession session)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("msg","");
        MappingJackson2JsonView retJson = new MappingJackson2JsonView();
        try
        {
            if (userinfo == null) {
                map.replace("success",false);
                map.replace("msg","User information parameters error.");
            } else {
                //assert userinfo != null;
                String operation = userinfo.getOpt();
                if(operation.isEmpty())
                {
                    map.replace("success",false);
                    map.replace("msg","Opt parameters error.");
                }
                else
                {
                    switch (operation)
                    {
                        case "add":
                            // do add user
                            if(DBHelper.addUser(userinfo))
                            {
                                map.replace("success",true);
                                map.replace("msg","Add userinfo successfully.");
                            }
                            else
                            {
                                map.replace("success",false);
                                map.replace("msg","Add userinfo failed.");
                            }
                            break;
                        case "del":
                            //do delete user
                            if(DBHelper.deleteUser(userinfo))
                            {
                                map.replace("success",true);
                                map.replace("msg","Delete userinfo successfully.");
                            }
                            else
                            {
                                map.replace("success",false);
                                map.replace("msg","Delete userinfo failed.");
                            }
                            break;
                        case "update":
                            //do update user
                            if(DBHelper.updateUser(userinfo))
                            {
                                map.replace("success",true);
                                map.replace("msg","Update userinfo successfully.");
                            }
                            else
                            {
                                map.replace("success",false);
                                map.replace("msg","Update userinfo failed.");
                            }
                            break;
                        default:
                            map.replace("success",false);
                            map.replace("msg","Opt parameters invalid.");
                    }
                }


            }
        }catch (Exception e)
        {
            map.replace("success",false);
            map.replace("msg","internal error.");
        }
        ModelAndView retView = new ModelAndView(retJson,map);
        return retView;
    }


    @RequestMapping("/getusers")
    @ResponseBody
    public ModelAndView getUsers(User user,HttpSession session)
    {

        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("msg","");
        try
        {
            map.replace("success",true);
            map.replace("msg","Get users successfully");
            map.put("data",DBHelper.getUsers());
        }
        catch (Exception e)
        {
            map.replace("success",false);
            map.replace("msg","internal error.");
        }
        ModelAndView retView = new ModelAndView(new MappingJackson2JsonView(),map);
        return retView;
    }

//old version
/*
    @RequestMapping(value={"/usermgr"})
    public void userMgr(User userinfo,HttpSession session,HttpServletResponse response)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("msg","");
        response.setContentType("text/html;charset=utf-8");
        try
        {
            Gson gson = new Gson();
            PrintWriter pw = response.getWriter();

            if(null == session.getAttribute("user"))   //未登录，
            {
                map.replace("success",false);
                map.replace("msg","用户未登录，请先登录。");
                response.getWriter().write(new Gson().toJson(map));
                return;
            }
            if (userinfo != null) {
                map.replace("success",false);
                map.replace("msg","User information parameters error.");
                pw.write(gson.toJson(map));
            }
            else {
                //assert userinfo != null;
                String operation = userinfo.getOpt();
                if(operation.isEmpty())
                {
                    map.replace("success",false);
                    map.replace("msg","Opt parameters error.");
                    pw.write(gson.toJson(map));
                    return;
                }
                else
                {
                    switch (operation)
                    {
                        case "add":
                            // do add user
                            if(DBHelper.addUser(userinfo))
                            {
                                map.replace("success",true);
                                map.replace("msg","Add userinfo successfully.");

                            }
                            else
                            {
                                map.replace("success",false);
                                map.replace("msg","Add userinfo failed.");
                            }
                            break;
                        case "del":
                            //do delete user
                            if(DBHelper.deleteUser(userinfo))
                            {
                                map.replace("success",true);
                                map.replace("msg","Delete userinfo successfully.");
                            }
                            else
                            {
                                map.replace("success",false);
                                map.replace("msg","Delete userinfo failed.");
                            }
                            break;
                        case "update":
                            //do update user
                            if(DBHelper.updateUser(userinfo))
                            {
                                map.replace("success",true);
                                map.replace("msg","Update userinfo successfully.");
                            }
                            else
                            {
                                map.replace("success",false);
                                map.replace("msg","Update userinfo failed.");
                            }
                            break;
                        default:
                            map.replace("success",false);
                            map.replace("msg","Opt parameters invalid.");
                    }
                    pw.write(gson.toJson(map));
                }


            }
        }catch (Exception e)
        {
            map.replace("success",false);
            map.replace("msg","internal error.");
            try {
                response.getWriter().write(new Gson().toJson(map));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }


    @RequestMapping("/getusers")
    public void getUsers(HttpSession session,HttpServletResponse response)
    {

        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("msg","");
        response.setContentType("text/html;charset=utf-8");
        try
        {
            if(null == session.getAttribute("user"))   //未登录，
            {
                map.replace("success",false);
                map.replace("msg","用户未登录，请先登录。");
                response.getWriter().write(new Gson().toJson(map));
                return;
            }
            map.replace("success",true);
            map.replace("msg","Get users successfully");
            map.put("data",DBHelper.getUsers());
            response.getWriter().write(new Gson().toJson(map));
        }
        catch (Exception e)
        {
            map.replace("success",false);
            map.replace("msg","internal error.");
            try {
                response.getWriter().write(new Gson().toJson(map));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

 */

}

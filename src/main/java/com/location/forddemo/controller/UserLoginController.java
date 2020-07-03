package com.location.forddemo.controller;


import com.location.forddemo.dao.UserDao;
import com.location.forddemo.entity.User;
import com.location.forddemo.util.HttpClientUtils;
import com.location.forddemo.util.HttpUtil;
import com.location.forddemo.util.ParamUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/account/github")
public class UserLoginController {
    @Autowired
    private UserDao userDao;




    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/githubLogin", method = RequestMethod.GET)
    public String githubLogin() {
        String githubState = "fordTest";
        return "redirect:https://github.com/login/oauth/authorize?client_id="+ParamUtil.client_id+"&redirect_uri="+ParamUtil.callback_uri+"&state=" ;
    }

    @RequestMapping(value = "/callback")
    public String githubCallback(String code, String state,HttpSession session) throws Exception{
        System.out.println("==>state:" + state);
        System.out.println("==>code:" + code);

        // Step2：通过 Authorization Code 获取 AccessToken
        String githubAccessTokenResult = HttpUtil.sendGet("https://github.com/login/oauth/access_token",
                "client_id="+ParamUtil.client_id+"&client_secret="+ParamUtil.client_secret+"&code=" + code +
                        "&redirect_uri="+ParamUtil.callback_uri);

        System.out.println("==>githubAccessTokenResult: " + githubAccessTokenResult);

        /**
         * 以 & 为分割字符分割 result
         */
        String[] githubResultList = githubAccessTokenResult.split("&");
        List<String> params = new ArrayList<>();

        // paramMap 是分割后得到的参数对, 比说 access_token=ad5f4as6gfads4as98fg
        for (String paramMap : githubResultList) {
            if (!"scope".equals(paramMap.split("=")[0])){
                // 再以 = 为分割字符分割, 并加到 params 中
                params.add(paramMap.split("=")[1]);
            }
        }
        //此时 params.get(0) 为 access_token;  params.get(1) 为 token_type
        // Step2：通过 access_token 获取用户信息
        String githubInfoResult = HttpClientUtils.doGet("https://api.github.com/user?access_token="+params.get(0));
        JSONObject jsonStr= JSONObject.fromObject(githubInfoResult);
        User user=new User();
       // if(userDao.findById(Integer.parseInt(jsonStr.get("id").toString()))==null){
            user.setUserId(Integer.parseInt(jsonStr.getString("id").toString()));
            user.setUserName(jsonStr.getString("login").toString());
            user.setEmail(jsonStr.getString("email").toString());
            userDao.save(user);
       // }
        session.setAttribute("user",user);
        return "retrieval-new";
    }
    @RequestMapping(value = "/touristLogin")
    public String test2(String code, String state,HttpSession session) throws Exception{
        User user=new User();
        // if(userDao.findById(Integer.parseInt(jsonStr.get("id").toString()))==null){
        user.setUserId(111);
        user.setUserName("lrl");
        user.setEmail("2225257276@qq.com");
        userDao.save(user);
        // }
        session.setAttribute("user",user);
        return "retrieval-new";
    }



}

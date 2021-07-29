package com.ouc.study.controller;/*
 *文件名: UserController
 *创建者: zdx
 *创建时间:2021/7/21 14:45
 *描述: TODO
 */

import com.ouc.study.annotation.LoginRequired;
import com.ouc.study.entity.User;
import com.ouc.study.service.FollowService;
import com.ouc.study.service.LikeService;
import com.ouc.study.service.UserService;
import com.ouc.study.util.HostHolder;
import com.ouc.study.util.StudyConstant;
import com.ouc.study.util.StudyUtil;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/user")
public class UserController implements StudyConstant {

    private static  final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${study.path.upload}")
    private String uploadPath;

    @Value("${study.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    @Value("${qiniu.key.access}")
    private String accessKey;

    @Value("${qiniu.key.secret}")
    private String secretkey;

    @Value("${qiniu.bucket.header.name}")
    private String headerBucketName;

    @Value("${qiniu.bucket.header.url}")
    private String headerBucketUrl;

    @LoginRequired
    @RequestMapping(path = "/setting",method = RequestMethod.GET)
    public String getSettingPage(Model model){
        //生成上传文件名称
        String fileNme = StudyUtil.generateUUID();
        //设置响应信息
        StringMap policy = new StringMap();
        policy.put("returnBody",StudyUtil.getJSONString(0));
        //生成上传凭证
        Auth auth = Auth.create(accessKey,secretkey);
        String uploadToken = auth.uploadToken(headerBucketName,fileNme,3600,policy);

        model.addAttribute("uploadToken",uploadToken);
        model.addAttribute("fileName" ,fileNme);

        return "/site/setting";
    }

    //更新头像路径
    @RequestMapping(path = "/header/url",method = RequestMethod.POST)
    @ResponseBody
    public String updateHeaderUrl(String fileName){
        if(StringUtils.isBlank(fileName)){
            return StudyUtil.getJSONString(1,"文件名不能为空！");
        }
        String url = headerBucketUrl + "/" + fileName;
        userService.updateHeader(hostHolder.getUser().getId(),url);

        return StudyUtil.getJSONString(0);
    }


    //废弃，上传至云服务器
    @LoginRequired
    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model){
        if(headerImage == null){
            model.addAttribute("error","您还没选择图片！");
            return "/site/setting";
        }

        String filename = headerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf(".")+1);
        if(StringUtils.isBlank(suffix)){
            model.addAttribute("error","文件格式不正确");
            return "/site/setting";
        }

        //生成随机文件名
        filename = StudyUtil.generateUUID() +"."+ suffix;
        // 确定文件存放路径
        File dest  = new File(uploadPath+"/"+filename);
        try {
            //存储文件
            headerImage.transferTo(dest);
        } catch (IOException e){
            logger.error("上传文件失败:"+e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常！",e);
        }

        // 更新当前用户的头像的路径（web访问路径）
        // http://localhost:8080/study/user/header/xxx.png
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath +"/user/header/"+filename;
        userService.updateHeader(user.getId(),headerUrl);

        return "redirect:/index";
    }

    // 废弃
    @RequestMapping(path = "/header/{filename}",method = RequestMethod.GET)
    public void getHeader(@PathVariable("filename" )String filename, HttpServletResponse response){
        //服务器上存放的路径
        filename = uploadPath + "/" + filename;
        //文件后缀
        String suffix = filename.substring(filename.lastIndexOf(".")+1);
        //响应图片
        response.setContentType("image/"+suffix);

        try(
                FileInputStream fis = new FileInputStream(filename);
                OutputStream os = response.getOutputStream();
                ) {

            byte[] buffer = new byte[1024];
            int b = 0;
            while((b = fis.read(buffer))!= -1){
                os.write(buffer,0,b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败",e.getMessage());
        }

    }

    //个人主页
    @RequestMapping(path = "/profile/{userId}",method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId,Model model){
        User user = userService.findUserById(userId);
        if(user == null){
            throw new RuntimeException("该用户不存在！");
        }

        //用户
        model.addAttribute("user",user);
        //点赞数量
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount",likeCount);

        //查询关注数量
         long followeeCount = followService.findFolloweeCount(userId,ENTITY_TYPE_USER);
         model.addAttribute("followeeCount",followeeCount);
        //查询粉丝数量
        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER,userId);
        model.addAttribute("followerCount",followerCount);
        //查询是否已关注
        boolean hasFollowed = false;
        if(hostHolder.getUser() != null){
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(),ENTITY_TYPE_USER,userId);
        }
        model.addAttribute("hasFollowed",hasFollowed);

        return "/site/profile";
    }

}

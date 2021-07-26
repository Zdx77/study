package com.ouc.study.service;/*
 *文件名: AlphaService
 *创建者: zdx
 *创建时间:2021/7/19 10:30
 *描述: TODO
 */

import com.ouc.study.dao.AlphaDao;
import com.ouc.study.dao.DiscussPostMapper;
import com.ouc.study.dao.UserMapper;
import com.ouc.study.entity.DiscussPost;
import com.ouc.study.entity.User;
import com.ouc.study.util.StudyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

@Service
//@Scope("prototype")
public class AlphaService {

    @Autowired
    private AlphaDao alphaDao;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;

    public AlphaService(){
        System.out.println("实例化AlphaService");
    }

    @PostConstruct
    public void init(){
        System.out.println("初始化AlphaService");
    }

    @PreDestroy
    public void destory(){
        System.out.println("销毁AlphaService");
    }

    public String find(){
        return alphaDao.select();
    }

    //REQUIRED:支持当前事务（外部事物），如果不存在则创建新事物
    //REQUIRES_NEW：创建一个新事物，并暂停当前事务（外部事物）
    //NESTED：如果当前存在事务（外部事物），则嵌套在该事务中执行（独立的提交和回滚），否则就和REQUIRED一样
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public Object savel(){
        //新增用户
        User user = new User();
        user.setUsername("alpha");
        user.setSalt(StudyUtil.generateUUID().substring(0,5));
        user.setPassword(StudyUtil.md5("123" + user.getSalt()));
        user.setEmail("alpha@qq.com");
        user.setHeaderUrl("http://image.nowcoder.com/head/99t.png");
        user.setCreateTime(new Date());
        userMapper.insertUser(user);
        //新增帖子
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle("hello");
        post.setContent("新人报道");
        post.setCreateTime(new Date());
        discussPostMapper.insertDiscussPost(post);

//       Integer.valueOf("abc");
        return "ok";
    }

    public Object save2(){
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        
        return transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                //新增用户
                User user = new User();
                user.setUsername("beta");
                user.setSalt(StudyUtil.generateUUID().substring(0,5));
                user.setPassword(StudyUtil.md5("123" + user.getSalt()));
                user.setEmail("alpha@qq.com");
                user.setHeaderUrl("http://image.nowcoder.com/head/99t.png");
                user.setCreateTime(new Date());
                userMapper.insertUser(user);
                //新增帖子
                DiscussPost post = new DiscussPost();
                post.setUserId(user.getId());
                post.setTitle("你好");
                post.setContent("我是新人");
                post.setCreateTime(new Date());
                discussPostMapper.insertDiscussPost(post);

//                Integer.valueOf("abc");
                return "ok";
            }
        });
    }
}

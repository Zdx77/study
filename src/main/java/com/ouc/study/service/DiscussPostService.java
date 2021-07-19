package com.ouc.study.service;/*
 *文件名: DiscussPostService
 *创建者: zdx
 *创建时间:2021/7/19 20:00
 *描述: TODO
 */

import com.ouc.study.dao.DiscussPostMapper;
import com.ouc.study.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}

package com.ouc.study.dao;/*
 *文件名: CommentMapper
 *创建者: zdx
 *创建时间:2021/7/22 11:00
 *描述: TODO
 */

import com.ouc.study.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<Comment> selectCommentsByEntity(int entityType,int entityId,int offset,int limit);

    int selectCountByEntity(int entityType,int entityId);

    int insertComment(Comment comment);

    Comment selectCommentById(int id);

}

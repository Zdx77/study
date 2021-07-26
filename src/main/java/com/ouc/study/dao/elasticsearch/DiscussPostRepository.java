package com.ouc.study.dao.elasticsearch;/*
 *文件名: DiscussPostRepository
 *创建者: zdx
 *创建时间:2021/7/26 19:25
 *描述: TODO
 */

import com.ouc.study.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost,Integer> {

}

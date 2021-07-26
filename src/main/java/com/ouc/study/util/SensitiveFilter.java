package com.ouc.study.util;/*
 *文件名: SensitiveFilter
 *创建者: zdx
 *创建时间:2021/7/21 18:33
 *描述: TODO
 */

import org.apache.commons.lang3.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    private  static  final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    //替换符
    private  static  final String RePLACEMENT = "***";

    //根节点
    private TrieNode rootNode = new TrieNode();

    @PostConstruct
    public void  init(){
        try(
                InputStream is =this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while((keyword = reader.readLine()) !=null){
                //添加到前缀树
                this.addKeyWord(keyword);
            }
        } catch (Exception e) {
            logger.error("加载敏感词文件失败：" + e.getMessage());
        }
    }

    //将一个敏感词添加到前缀树
    private  void addKeyWord(String keyword){
        TrieNode tempnode = rootNode;
        for(int i = 0;i < keyword.length();i++){
            char c = keyword.charAt(i);
            TrieNode subNode = tempnode.getSubNode(c);
            if(subNode == null){
                //初始化子节点
                subNode = new TrieNode();
                tempnode.addSubNode(c,subNode);
            }
            //指向子节点，进入下一轮循环
            tempnode = subNode;
            //设置结束标识
            if(i == keyword.length()-1){
                tempnode.setKeywordEnd(true);
            }

        }
    }

    /**
     * 过滤敏感词
     * @param text 待过滤的文本
     * @return 过滤后的文本
     */
    public String filter(String text){
        if (text == null) return  null;

        //指针1
        TrieNode tempnode = rootNode;
        //指针2
        int begin = 0;
        //指针3
        int position = 0;
        //结果
        StringBuilder sb = new StringBuilder();

        while(position < text.length()){
            char c = text.charAt(position);
            //跳过符号
            if(isSymbol(c)){
                //若指针1处于根节点，将此符号计入结果，让指针2向下走一步
                if(tempnode == rootNode){
                    sb.append(c);
                    begin++;
                }
                // 无论符号在开头还是中间，指针三都向下走一步
                position++;
                continue;
            }
            //检查下节点
            tempnode = tempnode.getSubNode(c);
            if(tempnode == null){
                //以begin开头的字符串不是敏感符号
                sb.append(text.charAt(begin));
                //进入下一个位置
                position = ++begin;
                //重新指向根节点
                tempnode = rootNode;
            }else if(tempnode.isKeywordEnd()){
                //发现敏感词，将begin~position字符串替换掉
                sb.append(RePLACEMENT);
                //进入下一位置
                begin = ++position;
                //重新指向根节点
                tempnode = rootNode;
            }else {
                //检查下一个字符
                if(position < text.length() -1){
                    position++;
                }
            }
        }
        //将最后一批字符计入结果
        sb.append(text.substring(begin));
        return sb.toString();
    }

    //判断是否为符号
    private boolean isSymbol(Character c){
        //  0x2E80 ~ 0X9FFF是东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0X9FFF);
    }

    //前缀树
    private class TrieNode{
        //关键词结束标志
        public boolean isKeywordEnd = false;

        //子节点(key是下级字符,value是下级节点)
        private Map<Character,TrieNode> subnodes = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }
        //添加子节点方法
        public void addSubNode(Character c,TrieNode node){
            subnodes.put(c,node);
        }

        //获取子节点
        public TrieNode getSubNode(Character c){
            return subnodes.get(c);
        }
    }
}

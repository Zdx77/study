package com.ouc.study.quartz;/*
 *文件名: Alphajob
 *创建者: zdx
 *创建时间:2021/7/28 14:32
 *描述: TODO
 */

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Alphajob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(Thread.currentThread().getName() + ": execute a quartz job.");
    }
}

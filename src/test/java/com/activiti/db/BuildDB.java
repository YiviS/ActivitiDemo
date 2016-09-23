package com.activiti.db;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/**
 * @Author Xg
 * @Date 2016-09-22 16:25
 * @Desc 使用activiti.cfg.xml建立表
 */
public class BuildDB {
    /**
     *  建立avtiviti所需要的表
     */
    @Test
    public void testDBByProperties() throws Exception {
        // 加载classpath下名为activiti.cfg.xml文件，创建核心流程引擎对象
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
        System.out.println("------- 创建表成功 ---------\nprocessEngine是："+processEngine);
    }
}

package com.alibaba.dubbo.remoting.zookeeper;

import java.util.List;

import com.alibaba.dubbo.common.URL;

public interface ZookeeperClient {

    /**
     * 创建路径 持久化或者临时
     *
     * @param path
     * @param ephemeral
     */
    void create(String path, boolean ephemeral);

    /**
     * 删除路径
     *
     * @param path
     */
    void delete(String path);

    /**
     * 获取某个路径子节点（路径）集合
     *
     * @param path
     * @return
     */
    List<String> getChildren(String path);

    /**
     * 添加路径监听器
     *
     * @param path
     * @param listener
     * @return
     */
    List<String> addChildListener(String path, ChildListener listener);

    /**
     * 删除路径监听器
     *
     * @param path
     * @param listener
     */
    void removeChildListener(String path, ChildListener listener);

    /**
     * 添加状态监听器
     *
     * @param listener
     */
    void addStateListener(StateListener listener);

    /**
     * 删除状态监听器
     *
     * @param listener
     */
    void removeStateListener(StateListener listener);

    /**
     * 是否已连接
     *
     * @return
     */
    boolean isConnected();

    /**
     * 关闭连接
     */
    void close();

    /**
     * 获取连接地址
     *
     * @return
     */
    URL getUrl();

}

package com.alibaba.dubbo.remoting.zookeeper;

import java.util.List;

public interface ChildListener {
    /**
     * 节点事件
     *
     * @param path     父路径
     * @param children 子路径
     */
    void childChanged(String path, List<String> children);

}

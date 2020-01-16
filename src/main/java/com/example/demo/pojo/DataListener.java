package com.example.demo.pojo;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 有个很重要的点 dataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
 *
 * @author yk
 * @date 2020/1/15
 */
public class DataListener<T> extends AnalysisEventListener<T> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 50;

    List<T> list = Lists.newArrayList();

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context 上下文
     */
    @Override
    public void invoke(T data, AnalysisContext context) {

        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        /*if (list.size() >= BATCH_COUNT) {
        saveData();
        // 存储完成清理 list
        list.clear();
        }*/
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context 上下文
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
    }

    public List<T> getList() {
        return list;
    }
}

package com.genersoft.iot.vmp.common;

import com.genersoft.iot.vmp.gb28181.bean.RecordItem;

import java.util.*;

public class DataCatch {

    HashMap<String, DataCatchForRecordItemList> data = new HashMap<>();

    private boolean job = false;
    private DataCatch() {
    }

    private static volatile DataCatch instance = null;

    public static DataCatch getInstance() {
        if (instance == null) {
            synchronized (DataCatch.class) {
                if (instance == null) {
                    instance = new DataCatch();
                }
            }
        }
        return instance;
    }

    public void dataCheck() {
        while (job) {
            try {
                for (String key : data.keySet()) {
                    Date now = new Date();
                    DataCatchForRecordItemList dl = data.get(key);
                    if (now.getTime() - dl.getCreateTime().getTime() > dl.getTimeout()) {
                        data.remove(key);
                        if (data.size() == 0) job = false;
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setInstance(DataCatch instance) {
        DataCatch.instance = instance;
    }

    public boolean hasKey(String key) {
        return data.containsKey(key);
    }

    public void del(String key) {
        data.remove(key);
        if (data.size() == 0) job = false;
    }

    public void set(String key, List<RecordItem> recordItems, int timeout) {
        DataCatchForRecordItemList dataCatchForRecordItemList = new DataCatchForRecordItemList();
        dataCatchForRecordItemList.setCreateTime(new Date());
        dataCatchForRecordItemList.setRecordItems(recordItems);
        dataCatchForRecordItemList.setTimeout(timeout);
        data.put(key, dataCatchForRecordItemList);
        if (!job){
            // 启动定时任务
            job = true;
            dataCheck();
        }
    }

    public List<RecordItem> get(String key) {
        return data.get(key).getRecordItems();
    }

    class DataCatchForRecordItemList{
        private List<RecordItem> recordItems;
        private Date createTime;
        private int timeout;

        public List<RecordItem> getRecordItems() {
            return recordItems;
        }

        public void setRecordItems(List<RecordItem> recordItems) {
            this.recordItems = recordItems;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }
    }
}

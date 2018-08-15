package com.zk.wanandroidtodo.bean;

import java.util.List;

/**
 * @description: 事项清单列表实体类
 * @author: zhukai
 * @date: 2018/8/15 11:44
 */
public class TodoListBean {

    /**
     * curPage : 1
     * datas : [{"completeDate":null,"completeDateStr":"","content":"测试","date":1534262400000,"dateStr":"2018-08-15","id":847,"status":0,"title":"测试","type":0,"userId":3753},{"completeDate":null,"completeDateStr":"","content":"测试","date":1534176000000,"dateStr":"2018-08-14","id":845,"status":0,"title":"测试","type":0,"userId":3753},{"completeDate":null,"completeDateStr":"","content":"测试","date":1534176000000,"dateStr":"2018-08-14","id":898,"status":0,"title":"测试","type":0,"userId":3753}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 3
     */
    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<DatasBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * completeDate : null
         * completeDateStr :
         * content : 测试
         * date : 1534262400000
         * dateStr : 2018-08-15
         * id : 847
         * status : 0
         * title : 测试
         * type : 0
         * userId : 3753
         */

        private Object completeDate;
        private String completeDateStr;
        private String content;
        private long date;
        private String dateStr;
        private int id;
        private int status;
        private String title;
        private int type;
        private int userId;

        public Object getCompleteDate() {
            return completeDate;
        }

        public void setCompleteDate(Object completeDate) {
            this.completeDate = completeDate;
        }

        public String getCompleteDateStr() {
            return completeDateStr;
        }

        public void setCompleteDateStr(String completeDateStr) {
            this.completeDateStr = completeDateStr;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getDateStr() {
            return dateStr;
        }

        public void setDateStr(String dateStr) {
            this.dateStr = dateStr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}

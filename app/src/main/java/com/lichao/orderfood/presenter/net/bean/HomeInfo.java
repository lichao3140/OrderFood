package com.lichao.orderfood.presenter.net.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-11-16.
 */

public class HomeInfo {
    private Head head;
    private List<HomeItem> body;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public List<HomeItem> getBody() {
        return body;
    }

    public void setBody(List<HomeItem> body) {
        this.body = body;
    }
}

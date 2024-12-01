package com.http.post.controller.listener;

import com.http.post.service.DBType;
import com.http.post.view.ViewManager;

public class DiskOptionListener extends AbstractPersistListener {


    public DiskOptionListener(ViewManager viewManager) {
        super(viewManager, viewManager.getDiskOption(), "Disk", "Cannot use DB SQL", DBType.JSON);
    }
}

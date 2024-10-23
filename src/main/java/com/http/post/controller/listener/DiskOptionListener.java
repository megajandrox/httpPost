package com.http.post.controller.listener;

import com.http.post.repository.DAOType;
import com.http.post.view.ViewManager;

public class DiskOptionListener extends AbstractPersistListener {


    public DiskOptionListener(ViewManager viewManager) {
        super(viewManager, viewManager.getDiskOption(), "Disk", "Cannot use DB SQL", DAOType.FILE);
    }
}

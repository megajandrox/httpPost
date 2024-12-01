package com.http.post.controller.listener;

import com.http.post.service.DBType;
import com.http.post.view.ViewManager;


public class DbSQLOptionListener extends AbstractPersistListener {

    public DbSQLOptionListener(ViewManager viewManager) {
        super(viewManager, viewManager.getDbSQLOption(), "DB SQL", "Cannot use DB SQL", DBType.RELATIONAL);
    }
}

package com.http.post.controller.listener;

import com.http.post.repository.DAOType;
import com.http.post.view.ViewManager;


public class DbSQLOptionListener extends AbstractPersistListener {

    public DbSQLOptionListener(ViewManager viewManager) {
        super(viewManager, viewManager.getDbSQLOption(), "DB SQL", "Cannot use DB SQL", DAOType.H2);
    }
}

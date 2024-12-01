package com.http.post.controller.worker;

import javax.swing.*;
import java.awt.event.ActionListener;

public interface SingleExecutor extends ActionListener {

    void actionPerform() throws Exception;

    default void execute() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                actionPerform();
                return null;
            }
        };
        worker.execute();
    }
}

package com.http.post.controller.worker;

import javax.swing.*;
import java.awt.event.ActionListener;

public interface JobExecutor extends ActionListener {

    void enableButton();

    void disableButton();

    void actionPerform() throws Exception;

    default void execute() {
        disableButton();
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                actionPerform();
                return null;
            }

            @Override
            protected void done() {
                enableButton();
            }
        };
        worker.execute();
    }
}

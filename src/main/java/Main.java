import com.http.post.controller.RequestController;
import com.http.post.view.ViewManager;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewManager viewManager = new ViewManager();
            new RequestController(viewManager);
            viewManager.setVisible(true);
        });
    }
}

import com.http.post.controller.RequestController;
import com.http.post.view.ViewManager;
import org.h2.tools.Server;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ViewManager viewManager = new ViewManager();
            new RequestController(viewManager);
            viewManager.setVisible(true);
            //Server.createTcpServer().start();
        });
    }
}

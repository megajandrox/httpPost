import com.http.post.view.HttpRequestManager;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HttpRequestManager().setVisible(true));
    }
}

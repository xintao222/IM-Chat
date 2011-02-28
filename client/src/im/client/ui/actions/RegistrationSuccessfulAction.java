package im.client.ui.actions;

import im.client.Frames;
import im.client.ui.LoginFrame;
import im.client.ui.RegistrationFrame;
import im.common.Client;
import im.common.Message;
import javax.swing.JOptionPane;

/**
 *
 * @author Stanislav Peshterliev
 */
public class RegistrationSuccessfulAction implements Action {

    @Override
    public void execute(Message message, Client client) {
        Frames.get(RegistrationFrame.class).setVisible(false);

        LoginFrame loginFrame = Frames.get(LoginFrame.class);
        
        JOptionPane.showMessageDialog(loginFrame, "Registration Sucessful. You can login.");
        
        loginFrame.setVisible(true);
    }
}

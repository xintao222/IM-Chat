package com.stanthinking.im.client.ui.actions;

import com.stanthinking.im.client.Frames;
import com.stanthinking.im.client.ui.LoginFrame;
import com.stanthinking.im.client.ui.RegistrationFrame;
import com.stanthinking.im.common.Client;
import com.stanthinking.im.common.Message;
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

package experimental;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import experimental.DeveloperInfo;


public class DeveloperInfoPanel extends JPanel {

    public DeveloperInfoPanel(DeveloperInfo info) {
        mInfo = info;
        add(new JLabel("Name: " + mInfo.getName()));
        add(new JLabel("About Me: " + mInfo.getAboutMe()));
        Image image = mInfo.getImage();
        image = image.getScaledInstance(256,256, Image.SCALE_DEFAULT);
        if(image != null) {
            JLabel label = new JLabel(new ImageIcon(image));
            add(label);
        }
    }
    
    private DeveloperInfo mInfo;
}

//package experimental;
//
//import java.awt.BorderLayout;
//import java.awt.CardLayout;
//import java.awt.FlowLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;
//import java.net.URL;
//import javax.swing.JButton;
//import javax.swing.JDialog;
//import javax.swing.JPanel;
//import org.jdesktop.swingx.JXCollapsiblePane;
//import runtime.Boot;
//import experimental.DeveloperInfoPanel;
//
//
//public class AboutDeveloperDialog extends JDialog {
//
//    public AboutDeveloperDialog() {
//        setTitle("Meet the Developers");
//        mDeveloperPanel = new JXCollapsiblePane();
//        add(mDeveloperPanel, BorderLayout.CENTER);
//        mLayout = new CardLayout();
//        mDeveloperPanel.setLayout(mLayout);
//        
//        URL info1Url = Boot.class.getResource("resources/about/developer/CliffBratonInfo.txt");
//        if(info1Url != null) {
//            
//            File file1 = new File(info1Url.getPath());
//            DeveloperInfo info1 = DeveloperInfo.load(file1);
//            DeveloperInfoPanel infoPanel1 = new DeveloperInfoPanel(info1);
//            mDeveloperPanel.add(infoPanel1, info1.getName());
//                
//        }
//        
//        URL info2Url = Boot.class.getResource("resources/about/developer/BrandonRuschillInfo.txt");
//        if(info2Url != null) {
//            File file2 = new File(info2Url.getPath());
//            DeveloperInfo info2 = DeveloperInfo.load(file2);
//            DeveloperInfoPanel infoPanel2 = new DeveloperInfoPanel(info2);
//            mDeveloperPanel.add(infoPanel2, info2.getName());
//                
//        }
//        
//        mNextButton = new JButton("Next");
//        mNextButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                mNextButton.setEnabled(false);
//                mPreviousButton.setEnabled(true);
//                mLayout.next(mDeveloperPanel.getContentPane());
//            }
//        });
//        mToggleButton = new JButton("Toggle");
//        mToggleButton.addActionListener(mDeveloperPanel.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
//        mPreviousButton = new JButton("Previous");
//        mPreviousButton.setEnabled(false);
//        mPreviousButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                mPreviousButton.setEnabled(false);
//                mNextButton.setEnabled(true);
//                mLayout.previous(mDeveloperPanel.getContentPane());
//            }
//        });
//        
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        buttonPanel.add(mPreviousButton);
//        buttonPanel.add(mToggleButton);
//        buttonPanel.add(mNextButton);
//        add(buttonPanel, BorderLayout.SOUTH);
//        
//        setDefaultCloseOperation(HIDE_ON_CLOSE);
//        setSize(500,500);
//    }
// 
//    private CardLayout mLayout;
//    private JXCollapsiblePane mDeveloperPanel;
//    private JButton mNextButton;
//    private JButton mToggleButton;
//    private JButton mPreviousButton;
//    
//}

package experimental;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.AlbumModel;

public class DialogViews {

    public static AlbumModel openAddAlbumDialog() {
        final AlbumModel album = new AlbumModel("");
        final JDialog dialog = new JDialog();
        
        dialog.setTitle("Add Album");
        dialog.setSize(300, 80);
        dialog.setLayout(new GridLayout(3,2));
        
        JLabel albumLabel = new JLabel("Album Name:");
        final JTextField albumNameTextBox = new JTextField(10);
        albumLabel.setLabelFor(albumNameTextBox);
        dialog.add(albumLabel);
        dialog.add(albumNameTextBox);
        
        JLabel descriptionLabel = new JLabel("Description:");
        final JTextArea albumDescription = new JTextArea();
        descriptionLabel.setLabelFor(albumDescription);
        dialog.add(descriptionLabel);
        dialog.add(albumDescription);
 
        JButton createAlbumButton = new JButton("Create");
        createAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JDialog dialog = (JDialog)arg0.getSource();
                album.setName(albumNameTextBox.getText());
                album.setDescription(albumDescription.getText());
                
                dialog.setVisible(false);
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                dialog.setVisible(false);
            }
        });
        
        dialog.add(createAlbumButton);
        dialog.add(cancelButton);
        
        dialog.setVisible(true);
        while(dialog.isVisible());
        return album;
    }
    
}
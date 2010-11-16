package experimental;

import io.XmlSettingsReader;
import io.XmlSettingsWriter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import runtime.Application;
import runtime.UserSettings;


public class SimpleSettingsDialog extends JDialog {

    public SimpleSettingsDialog() {
        mCurrentFile = Application.Runtime.settingsFile();
        mSettingsTable = new JTable();
        mSettingsTable.getColumnModel().setColumnSelectionAllowed(true);
        mTableModel = new DefaultTableModel(getSettingsData(mCurrentFile), new String[] { "Key", "Value" });
        mSettingsTable.setModel(mTableModel);
        
        mOpenSettingsButton = new JButton("Open");
        mOpenSettingsButton.setSize(200, 50);
        mOpenSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileFilter() {
                    
                    @Override
                    public String getDescription() {
                        return "*.xml | Xml Settings File";
                    }
                    
                    @Override
                    public boolean accept(File f) {
                        if(f.isDirectory()) {
                            return true;
                        }
                        String name = f.getName();
                        int i = name.lastIndexOf('.');
                        return (name.substring(i+1).toLowerCase().compareTo("xml") == 0);
                    }
                });
                
                int res = chooser.showOpenDialog(mSettingsTable);
                if(res == JFileChooser.APPROVE_OPTION) {
                    mCurrentFile = chooser.getSelectedFile();
                    mTableModel = new DefaultTableModel(getSettingsData(mCurrentFile), new String[] {"Key", "Value" });
                    mSettingsTable.setModel(mTableModel);
                    repaint();
                }
            }
        });
        mSaveSettingsButton = new JButton("Save");
        mSaveSettingsButton.setSize(200, 50);
        mSaveSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserSettings newModel = new UserSettings();
                for(int i = 0; i < mTableModel.getRowCount(); i++) {
                    Object key = mTableModel.getValueAt(i, 0);
                    Object value = mTableModel.getValueAt(i, 1);
                    newModel.putValue(key, value);
                }
                new XmlSettingsWriter(newModel).write(mCurrentFile);
            }
        });
        
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(Box.createGlue());
        buttonBox.add(mOpenSettingsButton);
        buttonBox.add(Box.createGlue());
        buttonBox.add(mSaveSettingsButton);
        buttonBox.add(Box.createGlue());
        
        setLayout(new BorderLayout());
        getContentPane().add(new JScrollPane(mSettingsTable), BorderLayout.CENTER);
        getContentPane().add(buttonBox, BorderLayout.SOUTH);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setSize(new Dimension(650, 800));
    }
    
    private Object[][] getSettingsData(File f) {
//        List<Field> allFields = new LinkedList<Field>();
//        
//        Class settings = runtime.Constants.class;
//        
//        for(Field field : settings.getFields()) {
//            allFields.add(field);
//        }
//        for(Class innerClass : settings.getClasses()) {
//            for(Field innerClassField : innerClass.getFields()) {
//                allFields.add(innerClassField);
//            }
//        }
//        
//        Object[][] data = new Object[allFields.size()][3];
//        int i = 0;
//        for(Field f : allFields) {
//            
//            String fname = f.getName();
//            String fvalue = f.toString();
//            String settingValue = Application.Settings.getString(fname);
//            data[i++] = new String[] { fname, fvalue, settingValue };
//        }
//        
//        return data;
        
     
        HashMap<Object,Object> settings = new XmlSettingsReader(f).getSettings();
        Object[][] data = new Object[settings.keySet().size()][2];
        int i =0;
        for(Object key : settings.keySet()) {
            data[i++] = new Object[] { key, settings.get(key) };
        }
        return data;
    }

    private JTable mSettingsTable;
    private DefaultTableModel mTableModel;
    private JButton mOpenSettingsButton;
    private JButton mSaveSettingsButton;
    private File mCurrentFile;
}

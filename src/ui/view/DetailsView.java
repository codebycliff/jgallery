// DetailsView.java
package ui.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.EventObject;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;
import common.IItemModel;
import common.IPhotoModel;
import common.ISelectionObserver;


/**
 * Class representing a view providing details on the currently selected item
 * in the gallery. The view is implemented as a table with two columns. The
 * first column contains and key and the second column contains the associated
 * value for that row's key.
 */
public class DetailsView extends JTable implements ISelectionObserver {
    
    /** Constant containing the value of the first column's  header. */
    public static final String KEY_HEADER = "Property";
    
    /** Constant containing the value of the second column's header. */
    public static final String VALUE_HEADER = "Value";
    
    /** Constant representing the first column model's identifier. */
    public static final Integer KEY_IDENTIFIER = 0;
    
    /** Constant representing the second column model's identifer. */
    public static final Integer VALUE_IDENTIFIER = 1;
    
    /**
     * Default constructor that instantiates a new details view with it's model
     * set to null.
     */
    public DetailsView() {
        super();
        mModel = null;
        mMaxKeyColumnWidth = 15;
        mCellRenderer = new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setBackground(Color.WHITE);
                label.setForeground(Color.DARK_GRAY);
                String fontName = "Droid Sans";
                int fontStyle = Font.PLAIN;
                int fontSize = 12;
                if(column == 0) {;
                    fontStyle = Font.BOLD;
                } 
                
                label.setFont(new Font(fontName, fontStyle, fontSize));
                return label;
            }
        };
        resetProperties();
        updateWidth();
    }
   
    /**
     * Constructor that instantiates a new details view with it's model set to
     * the model passed in.
     * 
     * @param model: the model to initialize this view with
     */
    public DetailsView(IItemModel model) {
        super();
        mModel = model;
        mMaxKeyColumnWidth = 15;
        mCellRenderer = new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                if(column == KEY_IDENTIFIER) {
                    label.setBackground(Color.DARK_GRAY);
                    label.setForeground(Color.WHITE);
                }
                return label;
            }
        };
        resetProperties();
        updateWidth();
    }
    
    /**
     * Adds a property to the details of the currently selected item. In other
     * words, adds a key/value pair represented a specific detail about the 
     * currently selected item in the gallery.
     * 
     * @param name: the key of the added property
     * @param value the value of the added property 
     */
    public void addProperty(Object name, Object value) {
        mTableModel.addRow(new Object[] { name, value });
        if(name.toString().length() > mMaxKeyColumnWidth) {
            mMaxKeyColumnWidth = name.toString().length();
            updateWidth();
        }
    }
    
    /**
     * Wipes out the current table model and re-initializes it with an empty
     * table model. If this view's model is not null, this method will parse
     * the current model and fill back in the rows of the table using the
     * current mode's details. 
     */
    @SuppressWarnings("rawtypes")
    public void resetProperties() {
        
        // Start with a fresh table containing no properties
//        mPropertiesModel = new DefaultTableModel(new Object[][] {}, new Object[] { "Property", "Value" });
        mTableModel = new DefaultTableModel(new Object[][] {}, new String[] {KEY_HEADER, VALUE_HEADER});
        setModel(mTableModel);
        updateWidth();
        TableColumn keyColumn = getColumnModel().getColumn(KEY_IDENTIFIER);
        keyColumn.setCellEditor(new NonEditableTreeCellEditor());
        keyColumn.setCellRenderer(mCellRenderer);
        keyColumn.setHeaderRenderer(new EmptyTableCellRenderer());
        TableColumn valColumn = getColumnModel().getColumn(VALUE_IDENTIFIER);
        valColumn.setCellEditor(new NonEditableTreeCellEditor());
        valColumn.setCellRenderer(mCellRenderer);
        valColumn.setHeaderRenderer(new EmptyTableCellRenderer());
        
        // Return from method if the photo model has not been set
        if(mModel == null) {
            return;
        }
        
        // Add the basic properties for the photo model
        addProperty("Name", mModel.getName());
        addProperty("Description", mModel.getDescription());
        addProperty("Size", mModel.getCount());
        
        // Parse the metadata from the photo model and add the properties
        
        if(mModel instanceof IPhotoModel) {
            IPhotoModel photoModel = (IPhotoModel)mModel;
            Iterator directoryIterator = photoModel.getMetadata().getDirectoryIterator();
            while(directoryIterator.hasNext()) {
                Directory directory = (Directory)directoryIterator.next();
                Iterator tagIterator = directory.getTagIterator();
                while(tagIterator.hasNext()) {
                    Tag tag = (Tag)tagIterator.next();
                    String name = tag.getTagName();
                    String value = tag.toString().replaceAll("^\\[.*\\].*-\\s", "");
                    addProperty(name, value);
                }
            }            
        }
        
    }
    
    /**
     * Gets the item model associated with this view.
     * 
     * @return IItemModel: the item model associated with this view
     */
    public IItemModel getItemModel() {
        return mModel;
    }

    /**
     * Sets the item model associated with this view.
     * 
     * @param model: the new item model to be associated with this view
     */
    public void setItemModel(IItemModel model) {
        mModel = model;
        resetProperties();
    }

    // ----------------------------------------------------- ISelectionObserver 
    
    /*
     * @see common.ISelectionObserver#update(common.IItemModel)
     */
    @Override
    public void update(IItemModel itemSelected) {
        mModel = itemSelected;
        resetProperties();
    }
    
    // ---------------------------------------------------------- Inner Classes
    
    /**
     * Class providing a cell editor that overrides the isCellEditable() to
     * return false no matter what. This makes it so that any cell using this
     * editor can't be edited or have it's value changed.
     */
    class NonEditableTreeCellEditor implements TableCellEditor {

        /*
         * @see javax.swing.CellEditor#getCellEditorValue()
         */
        @Override
        public Object getCellEditorValue() {return null;}
        
        /*
         * @see javax.swing.CellEditor#isCellEditable(java.util.EventObject)
         */
        @Override
        public boolean isCellEditable(EventObject anEvent) {return false;}
        
        /*
         * @see javax.swing.CellEditor#shouldSelectCell(java.util.EventObject)
         */
        @Override
        public boolean shouldSelectCell(EventObject anEvent) {return false;}
        
        /*
         * @see javax.swing.CellEditor#stopCellEditing()
         */
        @Override
        public boolean stopCellEditing() {return false;}
        
        /*
         * @see javax.swing.CellEditor#cancelCellEditing()
         */
        @Override
        public void cancelCellEditing() {}
        
        /*
         * @see javax.swing.CellEditor#addCellEditorListener(javax.swing.event.CellEditorListener)
         */
        @Override
        public void addCellEditorListener(CellEditorListener l) {}
        
        /*
         * @see javax.swing.CellEditor#removeCellEditorListener(javax.swing.event.CellEditorListener)
         */
        @Override
        public void removeCellEditorListener(CellEditorListener l) {}
        
        /*
         * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
         */
        @Override
        public Component getTableCellEditorComponent(JTable table,
                Object value, boolean isSelected, int row, int column) {
            // TODO Auto-Generated Method Stub
            return null;
        }        
    }
    
    /**
     * Class that renders the table as an empty table. Each cell in the table
     * is represented by a new JLabel with no properties. The effect of this
     * renderer is a table where no cells or headers can be seen.
     */
    class EmptyTableCellRenderer implements TableCellRenderer {

        /*
         * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
         */
        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            return new JLabel();
        }
        
    }
    
    // -------------------------------------------------------- Private Methods

    /**
     * Private method that adjusts the first column of the table to the width
     * of the largest displayed key, plus a little breathing room.
     */
    private void updateWidth() {
        int width = (int)(mMaxKeyColumnWidth * 6.667 + 10);
        TableColumn keyColumn = getColumnModel().getColumn(0);
        keyColumn.setMaxWidth(width);
        keyColumn.setPreferredWidth(width);
        keyColumn.setMinWidth(width);
        keyColumn.setWidth(width);
        keyColumn.setResizable(false);
    }

    // --------------------------------------------------------- Private Fields
    
    private IItemModel mModel;
    private int mMaxKeyColumnWidth;
    private TableCellRenderer mCellRenderer;
    private DefaultTableModel mTableModel;
}

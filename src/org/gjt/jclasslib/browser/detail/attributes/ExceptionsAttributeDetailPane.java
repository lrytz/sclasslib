/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.browser.detail.attributes;

import org.gjt.jclasslib.browser.*;
import org.gjt.jclasslib.structures.*;
import org.gjt.jclasslib.structures.attributes.*;

/**
    Detail pane showing an <tt>Exceptions</tt> attribute.

    @author <a href="mailto:jclasslib@gmx.net">Ingo Kegel</a>
    @version $Revision: 1.2 $ $Date: 2001/05/31 13:15:25 $
*/
public class ExceptionsAttributeDetailPane extends AbstractAttributeListDetailPane {

    public ExceptionsAttributeDetailPane(BrowserServices services) {
        super(services);
    }
    
    protected AbstractAttributeTableModel createTableModel(AttributeInfo attribute) {
        return new AttributeTableModel(attribute);
    }
    
    private class AttributeTableModel extends AbstractAttributeTableModel {
        
        private static final int COLUMN_COUNT = BASE_COLUMN_COUNT + 2;
        
        private static final int EXCEPTION_INDEX_COLUMN_INDEX = BASE_COLUMN_COUNT;
        private static final int EXCEPTION_VERBOSE_COLUMN_INDEX = BASE_COLUMN_COUNT + 1;
        
        private int[] exceptionIndexTable;
        
        public AttributeTableModel(AttributeInfo attribute) {
            super(attribute);
            exceptionIndexTable = ((ExceptionsAttribute)attribute).getExceptionIndexTable();
            initRowNumberStrings();
        }

        public int getColumnWidth(int column) {
            switch (column) {
                case EXCEPTION_INDEX_COLUMN_INDEX:
                   return LINK_COLUMN_WIDTH;
                   
                case EXCEPTION_VERBOSE_COLUMN_INDEX:
                   return VERBOSE_COLUMN_WIDTH;
                    
                default:
                   return LINK_COLUMN_WIDTH;
            }
        }
        
        public void link(int row, int column) {
            
            if (column == EXCEPTION_INDEX_COLUMN_INDEX) {
                int constantPoolIndex = exceptionIndexTable[row];
                ConstantPoolHyperlinkListener.link(services, constantPoolIndex);
            }
        }
        
        public int getRowCount() {
            return exceptionIndexTable.length;
        }
        
        public int getColumnCount() {
            return COLUMN_COUNT;
        }
        
        protected String doGetColumnName(int column) {
            switch (column) {
                case EXCEPTION_INDEX_COLUMN_INDEX:
                   return "exception";
                case EXCEPTION_VERBOSE_COLUMN_INDEX:
                   return "verbose";
                default:
                   return "";
            }
        }
        
        protected Class doGetColumnClass(int column) {
            switch (column) {
                case EXCEPTION_INDEX_COLUMN_INDEX:
                   return Link.class;
                case EXCEPTION_VERBOSE_COLUMN_INDEX:
                default:
                   return String.class;
            }
        }
        
        protected Object doGetValueAt(int row, int column) {

            int exceptionIndex = exceptionIndexTable[row];
            
            switch (column) {
                case EXCEPTION_INDEX_COLUMN_INDEX:
                    return CPINFO_LINK_TEXT + String.valueOf(exceptionIndex);
                case EXCEPTION_VERBOSE_COLUMN_INDEX:
                    return getConstantPoolEntryName(exceptionIndex);
                default:
                    return "";
            }
        }
    }
}

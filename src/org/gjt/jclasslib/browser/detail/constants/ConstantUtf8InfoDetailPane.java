/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.browser.detail.constants;

import org.gjt.jclasslib.browser.*;
import org.gjt.jclasslib.structures.*;
import org.gjt.jclasslib.structures.constants.*;
import org.gjt.jclasslib.util.*;

import javax.swing.tree.*;

/**
    Detail pane showing a <tt>CONSTANT_Utf8</tt> constant pool entry.

    @author <a href="mailto:jclasslib@gmx.net">Ingo Kegel</a>
    @version $Revision: 1.1 $ $Date: 2001/05/14 16:49:23 $
*/
public class ConstantUtf8InfoDetailPane extends AbstractConstantInfoDetailPane {

    // Visual components
    
    private ExtendedJLabel lblByteLength;
    private ExtendedJLabel lblByteLengthComment;
    private ExtendedJLabel lblStringLength;
    private ExtendedJLabel lblString;
    
    public ConstantUtf8InfoDetailPane(BrowserInternalFrame parentFrame) {
        super(parentFrame);
    }
    
    protected void setupLabels() {
        
        addDetailPaneEntry(normalLabel("Length of byte array:"),
                           lblByteLength = highlightLabel(),
                           lblByteLengthComment = highlightLabel());

        addDetailPaneEntry(normalLabel("Length of string:"),
                           lblStringLength = highlightLabel());
        
        addDetailPaneEntry(normalLabel("String:"),
                           null,
                           lblString = highlightLabel());

    }

    public void show(TreePath treePath) {
        
        int constantPoolIndex = constantPoolIndex(treePath);

        try {
            ConstantUtf8Info entry = parentFrame.getClassFile().getConstantPoolUtf8Entry(constantPoolIndex);
            lblByteLength.setText(entry.getBytes().length);
            lblStringLength.setText(entry.getString().length());
            lblString.setText(getConstantPoolEntryName(constantPoolIndex));
        } catch (InvalidByteCodeException ex) {
            lblByteLength.setText(-1);
            lblStringLength.setText(-1);
            lblByteLengthComment.setText(MESSAGE_INVALID_CONSTANT_POOL_ENTRY);
        }
        
        super.show(treePath);
    }
    
}


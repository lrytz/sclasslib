/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.browser.detail.attributes;

import org.gjt.jclasslib.browser.AbstractDetailPane;
import org.gjt.jclasslib.browser.BrowserServices;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;

/**
    Detail pane showing a <tt>Code</tt> attribute. Contains three other detail
    panes in its tabbed pane.

    @author <a href="mailto:jclasslib@ej-technologies.com">Ingo Kegel</a>
    @version $Revision: 1.4 $ $Date: 2003/07/08 14:04:28 $
*/
public class CodeAttributeDetailPane extends AbstractDetailPane {

    private JTabbedPane tabbedPane;
    
    private CodeAttributeExceptionTableDetailPane exceptionTablePane;
    private CodeAttributeByteCodeDetailPane byteCodePane;
    private CodeAttributeMiscDetailPane miscPane;
    
    public CodeAttributeDetailPane(BrowserServices services) {
        super(services);
    }

    protected void setupComponent() {
        setLayout(new BorderLayout());
        
        add(buildTabbedPane(), BorderLayout.CENTER);
    }
    
    /**
        Get the <tt>CodeAttributeByteCodeDetailPane</tt> showing the bytecode
        of this <tt>Code</tt> attribute.
        @return the <tt>CodeAttributeByteCodeDetailPane</tt>
     */
    public CodeAttributeByteCodeDetailPane getCodeAttributeByteCodeDetailPane() {
        return byteCodePane;
    }
    
    /**
        Select the <tt>CodeAttributeByteCodeDetailPane</tt> showing the bytecode
        of this <tt>Code</tt> attribute.
     */
    public void selectByteCodeDetailPane() {
        tabbedPane.setSelectedIndex(0);
    }
    
    private JTabbedPane buildTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Bytecode", buildByteCodePane());
        tabbedPane.addTab("Exception table", buildExceptionTablePane());
        tabbedPane.addTab("Misc", buildMiscPane());
        
        return tabbedPane;
    }

    private JPanel buildByteCodePane() {
        byteCodePane = new CodeAttributeByteCodeDetailPane(services);
        return byteCodePane;
    }

    private JPanel buildExceptionTablePane() {
        exceptionTablePane = new CodeAttributeExceptionTableDetailPane(services);
        return exceptionTablePane;
    }

    private JPanel buildMiscPane() {
        miscPane = new CodeAttributeMiscDetailPane(services);
        return miscPane;
    }
    
    public void show(TreePath treePath) {

        exceptionTablePane.show(treePath);
        byteCodePane.show(treePath);
        miscPane.show(treePath);
    }
    
}


/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.browser.detail.attributes.code;

import org.gjt.jclasslib.browser.*;
import org.gjt.jclasslib.structures.attributes.CodeAttribute;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;

/**
    Detail pane showing the code of a <tt>Code</tt> attribute.

    @author <a href="mailto:jclasslib@ej-technologies.com">Ingo Kegel</a>
    @version $Revision: 1.1 $ $Date: 2003/08/18 08:19:37 $
*/
public class ByteCodeDetailPane extends AbstractDetailPane {

    private static final Rectangle RECT_ORIGIN = new Rectangle(0, 0, 0, 0);

    // Visual components

    private ByteCodeDisplay byteCodeDisplay;
    private CounterDisplay counterDisplay;
    private JScrollPane scrollPane;
    private JButton btnCopy;

    /**
        Constructor.
        @param services the associated browser services.
     */
    public ByteCodeDetailPane(BrowserServices services) {
        super(services);
    }

    protected void setupComponent() {

        setLayout(new BorderLayout());
        btnCopy = new JButton("Copy to clipboard");
        btnCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                byteCodeDisplay.copyToClipboard();
            }
        });
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalGlue());
        box.add(btnCopy);

        add(box, BorderLayout.SOUTH);
        add(buildByteCodeScrollPane(), BorderLayout.CENTER);

        DocumentLinkListener listener = new DocumentLinkListener(byteCodeDisplay);
        byteCodeDisplay.addMouseListener(listener);
        byteCodeDisplay.addMouseMotionListener(listener);
    }
    
    public void show(TreePath treePath) {

        CodeAttribute attribute = (CodeAttribute)findAttribute(treePath);
        if (byteCodeDisplay.getCodeAttribute() != attribute) {

            BrowserComponent browserComponent = services.getBrowserComponent();
            browserComponent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            byteCodeDisplay.setCodeAttribute(attribute, services.getClassFile());
            counterDisplay.init(byteCodeDisplay);
            
            byteCodeDisplay.scrollRectToVisible(RECT_ORIGIN);

            scrollPane.validate();
            scrollPane.repaint();
            browserComponent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    /**
        Scroll the code to a specified code offset.
        @param offset the offset
     */
    public void scrollToOffset(int offset) {

        byteCodeDisplay.scrollToOffset(offset);
    }
    
    private JScrollPane buildByteCodeScrollPane() {

        byteCodeDisplay = new ByteCodeDisplay(this);
        scrollPane = new JScrollPane(byteCodeDisplay);
        scrollPane.getViewport().setBackground(Color.WHITE);
        counterDisplay = new CounterDisplay();
        scrollPane.setRowHeaderView(counterDisplay);

        MouseAdapter mouseListener = new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                scrollPane.requestFocus();
            }
        };
        byteCodeDisplay.addMouseListener(mouseListener);
        scrollPane.getHorizontalScrollBar().addMouseListener(mouseListener);
        scrollPane.getVerticalScrollBar().addMouseListener(mouseListener);
        scrollPane.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent event) {
                scrollPane.requestFocus();
            }
        });

        return scrollPane;
    }

    private class DocumentLinkListener extends MouseAdapter
                                       implements MouseMotionListener
    {

        private ByteCodeDisplay byteCodeDisplay;
        
        private Cursor defaultCursor;
        private int defaultCursorType;
        private Cursor handCursor;
        
        private DocumentLinkListener(ByteCodeDisplay byteCodeDisplay) {
            this.byteCodeDisplay = byteCodeDisplay;

            defaultCursor = Cursor.getDefaultCursor();
            defaultCursorType = defaultCursor.getType();
            handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        }
        
        public void mouseClicked(MouseEvent event) {

            byteCodeDisplay.link(event.getPoint());
        }

        public void mouseDragged(MouseEvent event) {
        }

        public void mouseMoved(MouseEvent event) {
            
            boolean link = byteCodeDisplay.isLink(event.getPoint());
            if (byteCodeDisplay.getCursor().getType() == defaultCursorType && link) {
                byteCodeDisplay.setCursor(handCursor);
            } else if (!link) {
                byteCodeDisplay.setCursor(defaultCursor);
            }
        }
        
    }
    

}


/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.util;

import javax.swing.*;
import java.awt.*;

/**
    Collection of GUI utility methods.

    @author <a href="mailto:jclasslib@ej-technologies.com">Ingo Kegel</a>
    @version $Revision: 1.1 $ $Date: 2003/08/18 07:46:43 $
*/
public class GUIHelper {

    /** The title for message boxes. */
    public static final String MESSAGE_TITLE = "jclasslib";

    /** "Yes" and "No" Options for showOptionDialog. */
    public static final String[] YES_NO_OPTIONS = new String[] {"Yes", "No"};
    /** Empty icon 16x16. */
    public static final Icon ICON_EMPTY = new EmptyIcon(16, 16);

    /**
        Show a <tt>JOptionPane</tt> option dialog.
        @param parent parent component
        @param message the message string
        @param options the array of option strings
        @param messageType the message type as defined in <tt>JOptionPane</tt>
        @return the result code of the dialog
     */
    public static int showOptionDialog(Component parent, String message, String[] options, int messageType) {
        return JOptionPane.showOptionDialog(parent,
                                            message,
                                            MESSAGE_TITLE,
                                            0,
                                            messageType,
                                            null,
                                            options,
                                            options[0]);
    }

    /**
        Show a <tt>JOptionPane</tt> message dialog.
        @param parent parent component
        @param message the message string
        @param messageType the message type as defined in <tt>JOptionPane</tt>
     */
    public static void showMessage(Component parent, String message, int messageType) {
        JOptionPane.showMessageDialog(parent,
                                      message,
                                      MESSAGE_TITLE,
                                      messageType,
                                      null);
    }

    /**
        Center a window on another window.
        @param window the window to be centered.
        @param parentWindow the parent window on which the window is to be centered.
     */
    public static void centerOnParentWindow(Window window, Window parentWindow) {
        window.setLocation(
                parentWindow.getX() + (parentWindow.getWidth() - window.getWidth()) / 2,
                parentWindow.getY() + (parentWindow.getHeight() - window.getHeight()) / 2
        );
    }

    /**
        Set reasonable unit increments for a scroll pane that does not contain a
        <tt>Scrollable</tt>.
        @param scrollPane
     */
    public static void setDefaultScrollbarUnits(JScrollPane scrollPane) {

        int unit = new JLabel().getFont().getSize() * 2;
        scrollPane.getHorizontalScrollBar().setUnitIncrement(unit);
        scrollPane.getVerticalScrollBar().setUnitIncrement(unit);
    }

}

/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.structures;

import java.io.*;
import java.lang.reflect.Array;
import org.gjt.jclasslib.io.Log;

/**
    Base class for all structures defined in the class file format. <p>
    Provides common services such as reading, writing and debugging.

    @author <a href="mailto:jclasslib@gmx.net">Ingo Kegel</a>
    @version $Revision: 1.1 $ $Date: 2001/05/14 16:49:18 $
 */
public abstract class AbstractStructure {

    /**
        Set this JVM System property to true to switch on debugging for
        reading and writing class files
     */
    public static final String SYSTEM_PROPERTY_DEBUG = "classlib.io.debug";

    /**
        Parent class file for this structure. <tt>Null</tt> for a <tt>ClassFile</tt>
        structure.
     */
    protected ClassFile classFile;
    
    /** Flag for debugging while reading and writing class files */
    protected boolean debug;
    
    public AbstractStructure() {
        debug = Boolean.getBoolean(SYSTEM_PROPERTY_DEBUG);
    }

    /**
        Get parent class file
        @return the class file
     */
    public ClassFile getClassFile() {
        return classFile;
    }

    /**
        Set parent class file. <p>
        
        Has to be called at least once on a structure.
        @param classFile the new parent class file
     */
    public void setClassFile(ClassFile classFile) {
        this.classFile = classFile;
    }

    /**
        Read this structure from the given <tt>DataInput</tt>. <p>
     
        Excpects <tt>DataInput</tt> to be in JVM class file format and just
        before a structure of this kind. No look ahead parsing since
        the class file format is deterministic.
        @param in the <tt>DataInput</tt> from which to read
        @throws InvalidByteCodeException if the byte code is invalid
        @throws IOException if an exception occurs with the <tt>DataInput</tt>
     */
    public void read(DataInput in)
        throws InvalidByteCodeException, IOException {
    }
    
    /**
        Write this structure to the given <tt>DataOutput</tt>. <p>
     
        The written bytes are in JVM class file format.
        @param out the <tt>DataOutput</tt> to which to write
        @throws InvalidByteCodeException if the structure is internally inconsistent
        @throws IOException if an exception occurs with the <tt>DataOutput</tt>
     */
    public void write(DataOutput out)
        throws InvalidByteCodeException, IOException {
    }
   
    /**
        Get the debug mode for this structure.
        @return whether debug is on or not
     */
    public boolean getDebug() {
        return debug;
    }

    /**
        Set the debug mode for this structure.
        @param debug the new debug mode
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
        Utility method for derived structures. Get the 
        length of an arbitrary array which may hold
        primitive types or reference types or may be null.
        @param array the array for which the length is requested
        @return the length
     */
    protected int getLength(Object array) {
        if (array == null || !array.getClass().isArray()) {
            return (int)0;
        } else {
            return (int)Array.getLength(array);
        }
    }
    
    /**
        Utility method for derived structures. Dump a specific debug message.
        @param message the debug message
     */
    protected void debug(String message) {
        if (debug) {
            Log.debug(message);
        }
    }
    
    /**
        Utility method for derived structures. Print an int value as a hex string.
        @param bytes the int value to print as a hex string
        @return the hex string
     */
    protected String printBytes(int bytes) {
        return padHexString(Integer.toHexString(bytes), 8);
    }

    /**
        Utility method for derived structures. Print an access flag or an
        unsigned short value as a hex string.
        @param accessFlags the unsigned short value to print as a hex string
        @return the hex string
     */
    protected String printAccessFlags(int accessFlags) {
        return padHexString(Integer.toHexString(accessFlags), 4);
    }
    
    private String padHexString(String hexString, int length) {
        StringBuffer buffer = new StringBuffer("0x");

        for (int i = hexString.length(); i < length; i++) {
            buffer.append('0');
        }
        buffer.append(hexString);
        
        return buffer.toString();
    }
    
    /**
        Utility method for derived structures. Print an access flag as
        a space separated list of verbose java access modifiers.
        @param accessFlags the unsigned short value to print as a hex string
        @return the hex string
     */
    protected String printAccessFlagsVerbose(int accessFlags) {
        StringBuffer accessFlagsVerbose = new StringBuffer();
        
        boolean isClassFile = this instanceof ClassFile;
        
        for (int i = 0; i < AccessFlags.allAccessFlags.length; i++) {
            if (isClassFile && AccessFlags.allAccessFlags[i] == AccessFlags.ACC_SUPER) {
                continue;
            }
            if ((accessFlags & AccessFlags.allAccessFlags[i]) != 0) {
                if (accessFlagsVerbose.length() > 0) {
                    accessFlagsVerbose.append(" ");
                }
                accessFlagsVerbose.append(AccessFlags.allAccessFlagsVerbose[i]);
            }
        }

        return accessFlagsVerbose.toString();
    }
}

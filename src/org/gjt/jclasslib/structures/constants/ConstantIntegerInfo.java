/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.structures.constants;

import org.gjt.jclasslib.structures.InvalidByteCodeException;

import java.io.*;

/**
    Describes a <tt>CONSTANT_Integer_info</tt> constant pool data structure.

    @author <a href="mailto:jclasslib@ej-technologies.com">Ingo Kegel</a>
    @version $Revision: 1.3 $ $Date: 2003/07/08 14:04:29 $
*/
public class ConstantIntegerInfo extends ConstantNumeric {

    public byte getTag() {
        return CONSTANT_INTEGER;
    }

    public String getTagVerbose() {
        return CONSTANT_INTEGER_VERBOSE;
    }

    public String getVerbose() throws InvalidByteCodeException {
        return String.valueOf(getInt());
    }

    /**
        Get the int value of this constant pool entry.
        @return the value
     */
    public int getInt() {
        return bytes;
    }

    /**
        Set the int value of this constant pool entry.
        @param number the value
     */
    public void setInt(int number) {
        bytes = number;
    }

    public void read(DataInput in)
        throws InvalidByteCodeException, IOException {
        
        super.read(in);
        if (debug) debug("read ");
    }
    
    public void write(DataOutput out)
        throws InvalidByteCodeException, IOException {
        
        out.writeByte(CONSTANT_INTEGER);
        super.write(out);
        if (debug) debug("wrote ");
    }
    
    protected void debug(String message) {
        super.debug(message + getTagVerbose() + " with bytes " + bytes);
    }

}

/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.bytecode;

import org.gjt.jclasslib.io.*;
import java.io.*;

/**
    Describes the <tt>invokeinterface</tt> instruction.
 
    @author <a href="mailto:jclasslib@ej-technologies.com">Ingo Kegel</a>
    @version $Revision: 1.3 $ $Date: 2002/02/27 16:47:43 $
*/
public class InvokeInterfaceInstruction extends ImmediateShortInstruction {

    private int count;
    
    public InvokeInterfaceInstruction(int opcode) {
        super(opcode); 
    }
    
    public InvokeInterfaceInstruction(int opcode, int immediateShort, int count) {
        super(opcode, immediateShort); 
        this.count = count;
    }
    
    
    public int getSize() {
        return super.getSize() + 2;
    }

    /**
        Get the argument count of this instruction 
        @return the argument count
     */
    public int getCount() {
        return count;
    }

    /**
        Set the argument count of this instruction 
        @param count the argument count
     */
    public void setCount(int count) {
        this.count = count;
    }

    public void read(ByteCodeInput in) throws IOException {
        super.read(in);

        count = in.readUnsignedByte();
        // Next byte is always 0 and thus discarded
        in.readByte();
    }

    public void write(ByteCodeOutput out) throws IOException {
        super.write(out);

        out.writeByte(count);
        out.writeByte(0);
    }
    
}

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
    Describes an instruction that is followed by an immediate unsigned short.
 
    @author <a href="mailto:jclasslib@gmx.net">Ingo Kegel</a>
    @version $Revision: 1.1 $ $Date: 2001/05/14 16:49:17 $
*/
public class ImmediateShortInstruction extends AbstractInstruction {

    private int immediateShort;
   
    public ImmediateShortInstruction(int opcode) {
        super(opcode); 
    }
    
    /**
        Get the immediate unsigned short of this instruction.
        @return the short
     */
    public int getImmediateShort() {
        return immediateShort;
    }

    /**
        Set the immediate unsigned short of this instruction.
        @param immediateShort the short
     */
    public void setImmediateShort(int immediateShort) {
        this.immediateShort = immediateShort;
    }
    
    public void read(ByteCodeInput in) throws IOException {
        super.read(in);

        immediateShort = in.readUnsignedShort();
    }

    public void write(ByteCodeOutput out) throws IOException {
        super.write(out);

        out.writeShort(immediateShort);
    }
    
}

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
    Describes an instruction that is followed by an immediate int.
 
    @author <a href="mailto:jclasslib@gmx.net">Ingo Kegel</a>
    @version $Revision: 1.1 $ $Date: 2001/05/14 16:49:17 $
*/
public class ImmediateIntInstruction extends AbstractInstruction {

    private int immediateInt;
   
    public ImmediateIntInstruction(int opcode) {
        super(opcode); 
    }
    
    /**
        Get the immediate int of this instruction.
        @return the int
     */
    public int getImmediateInt() {
        return immediateInt;
    }

    /**
        Set the immediate int of this instruction.
        @param immediateInt the int
     */
    public void setImmediateInt(int immediateInt) {
        this.immediateInt = immediateInt;
    }
    
    public void read(ByteCodeInput in) throws IOException {
        super.read(in);

        immediateInt = in.readInt();
    }

    public void write(ByteCodeOutput out) throws IOException {
        super.write(out);

        out.writeInt(immediateInt);
    }
    
}

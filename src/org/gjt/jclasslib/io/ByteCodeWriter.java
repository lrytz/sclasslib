/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.io;

import org.gjt.jclasslib.bytecode.*;
import java.io.*;
import java.util.*;

/**
    Converts a list of instructions as defined in the package
    <tt>org.gjt.jclasslib.bytecode</tt> to bytecode.
 
    @author <a href="mailto:jclasslib@ej-technologies.com">Ingo Kegel</a>
    @version $Revision: 1.2 $ $Date: 2002/02/27 16:47:43 $
*/
public class ByteCodeWriter {

    private ByteCodeWriter() {
    }
    
    /**
        Converts a list of instructions to bytecode.
        @param instructions the <tt>java.util.List</tt> with the instructions
        @return the bytecode as an array of bytes
        @throws IOException if an exception occurs with the bytecode
     */
    public static byte[] writeByteCode(List instructions) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteCodeOutputStream bcos = new ByteCodeOutputStream(baos);
        
        Iterator it = instructions.iterator();
        while (it.hasNext()) {
            writeNextInstruction(bcos, (AbstractInstruction)it.next());
        }
        bcos.close();
        return baos.toByteArray();
    }
    
    private static void writeNextInstruction(ByteCodeOutputStream bcos,
                                             AbstractInstruction instruction)
        throws IOException
    {
        instruction.write(bcos);
        
    }
    
}

/*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the license, or (at your option) any later version.
*/

package org.gjt.jclasslib.io;

import org.gjt.jclasslib.structures.*;

import java.io.*;

/**
    Converts class files to a class file structure <tt>ClassFile</tt> as defined in
    <tt>org.gjt.jclasslib.structures</tt>.

    @author <a href="mailto:jclasslib@gmx.net">Ingo Kegel</a>
    @version $Revision: 1.2 $ $Date: 2001/05/31 13:17:26 $
*/
public class ClassFileReader {

    private ClassFileReader() {
    }

    /**
        Converts a class file to a <tt>ClassFile</tt> structure.
        @param file the file from which to read the <tt>ClassFile</tt> structure
        @return the new <tt>ClassFile</tt> structure
        @throws InvalidByteCodeException if the bytecode is invalid
        @throws IOException if an exception occurs while reading the file
     */
    public static ClassFile readFromFile(File file)
        throws InvalidByteCodeException, IOException
    {
            
        return readFromInputStream(new FileInputStream(file));
    }

    /**
        Converts a class file to a <tt>ClassFile</tt> structure.
        @param is the input stream from which to read the
                  <tt>ClassFile</tt> structure
        @return the new <tt>ClassFile</tt> structure
        @throws InvalidByteCodeException if the bytecode is invalid
        @throws IOException if an exception occurs while reading from
                            the input stream
     */
    public static ClassFile readFromInputStream(InputStream is)
        throws InvalidByteCodeException, IOException
    {
            
        DataInputStream in = new DataInputStream(
                                new BufferedInputStream(is));
        
        ClassFile classFile = new ClassFile();
        classFile.read(in);
        in.close();
        return classFile;
    }
    
}

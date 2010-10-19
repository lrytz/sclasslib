package org.gjt.jclasslib.structures.elementvalues

import java.lang.String
import org.gjt.jclasslib.structures.ClassFile
import java.io. {PrintStream, ByteArrayOutputStream, DataOutput, DataInput}
import reflect.generic.ByteCodecs
import tools.nsc.util.ShowPickled
import org.gjt.jclasslib.io.Log
import java.util.Arrays

/**
 * Special ElementValue handling the Scala signature structure.
 * This class follows the <tt>ConstElementValue</tt> in the structure description.
 * @author Lomig Mégard
 * @Date 18.10.10
 */

class ScalaSigElementValue(tag: Int) extends ElementValue(tag) {

  private var constValueIndex = -1

  private var formattedSig = "not yet extracted !"


  def getConstValueIndex = constValueIndex

  def getFormattedSig = formattedSig

  protected def getSpecificLength = ScalaSigElementValue.LENGTH

  def getEntryName = ScalaSigElementValue.ENTRY_NAME


  private def extractSig() {
    val enc = classFile.getConstantPoolUtf8Entry(constValueIndex).getBytes()

    Log.debug("enc  = " + Arrays.toString(enc))


    val newSize = ByteCodecs.decode(enc)
    Log.debug("enc2 = " + Arrays.toString(enc))

    val dec = new Array[Byte](newSize)

    Array.copy(enc, 0, dec, 0, newSize)

    Log.debug("dec  = " + Arrays.toString(dec))


    ShowPickled.fromBytes(dec) match {
      case Some(buffer) =>
        val baos = new ByteArrayOutputStream()
        ShowPickled.printFile(buffer, new PrintStream(baos))
        formattedSig = baos.toString("UTF8")
      case None =>
        //TODO how handles this error ?
        Log.error("error occurs during scala sig parsing !")
        formattedSig = "error occurs during scala sig parsing !"
    }
  }

  override def read(in: DataInput) = {
    super.read(in)

    constValueIndex = in.readUnsignedShort()

    //TODO uncomment
    extractSig

    if (debug) debug("read ")
  }

  override def write(out: DataOutput) = {
    super.write(out)

    out.writeShort(constValueIndex)

    if (debug) debug("wrote ")
  }

  protected override def debug(message: String) = {
    super.debug(message + "ScalaSig with const_value_index " + constValueIndex)
  }
}

object ScalaSigElementValue {
  val ENTRY_NAME = "ScalaSig"
  val LENGTH = 2

  def apply(in: DataInput, classFile: ClassFile): ScalaSigElementValue = {
    val tag = in.readUnsignedByte();
    val elem = new ScalaSigElementValue(tag)
    elem.setClassFile(classFile)
    elem.read(in)
    elem
  }
  
}

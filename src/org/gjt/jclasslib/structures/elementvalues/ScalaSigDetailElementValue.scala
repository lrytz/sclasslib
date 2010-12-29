package org.gjt.jclasslib.structures.elementvalues

import tools.nsc.symtab.Flags
import org.gjt.jclasslib.structures.AbstractStructure

/**
 * Abstract structure that doesn't match any real bytecode structure but instead with
 * the Scala signature.
 * @author Lomig Mégard
 */

class ScalaSigDetailElementValue(sym: scala.tools.nsc.symtab.Symbols#Symbol) extends AbstractStructure {

  protected def printAccessFlagsVerbose(accessFlags: Int) = {
    if (accessFlags != 0)
      throw new RuntimeException("Access flags should be zero: " + Integer.toHexString(accessFlags));
    ""
  }

  /* get the symbol attached to this Element Value */
  def getSymbol = sym

  def fullName = sym.fullName
  def name = sym.nameString
  def ownerName = sym.owner.nameString
  def ownerFullName = sym.owner.fullName
  def privateWithin = sym.privateWithin.nameString
  def tpe = sym.info.toString
  def flags = Flags.flagsToString(sym.flags)
  def annotations = sym.annotations.map(a => a.toString)

}

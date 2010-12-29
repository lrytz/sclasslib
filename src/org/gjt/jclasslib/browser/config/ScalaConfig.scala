package org.gjt.jclasslib.browser.config

import classpath.{ClasspathEntry, ClasspathChangeEvent, ClasspathChangeListener}
import org.gjt.jclasslib.browser._
import tools.nsc.{Settings, Global}
import org.gjt.jclasslib.structures.elementvalues.ScalaSigDetailElementValue
import tools.nsc.symtab.Symbols
import java.io.File
import org.gjt.jclasslib.io.Log

/**
 * 
 * @author Lomig Mégard
 */

object ScalaConfig extends ClasspathChangeListener {

  // hack to get this instance from Java
  def instance = this

  var global: Global = _
  var config: BrowserConfig = _

  /*
   * map of nodes that are waiting for an updated classpath
   */
  private var markedNodes: Map[String, BrowserTreeNode] = Map()
  
  def setConfig(c: BrowserConfig): Unit = config = c
  
  def classpathChanged(event: ClasspathChangeEvent) = updateGlobal()

  def updateGlobal() {
    val cp = "-cp " + config.getClasspath.toArray.toList.asInstanceOf[List[ClasspathEntry]].map{cpe => cpe.getFileName}.mkString(":")
    val settings = new Settings()
    settings.processArgumentString(cp)

    val g = new Global(settings)
    val run = new g.Run()
    global = g

  }

  def markNode(className: String, parent: BrowserTreeNode): Unit = {
    markedNodes += ((className, parent))
  }

  def insertSignatureDetails(className: String): Unit = {
    markedNodes.get(className) match {
      case Some(parent) =>
        fillTree(className.replace(File.separatorChar, '.'), parent)
        markedNodes -= className
      case None =>
        Log.error("Try to insert nodes in " + className + ". But this class had not been marked.")
    }
  }

  def getClassSymbol(name: String) = {
    val g = global
    val typeName = g.newTermName(name)
    val sym = g.definitions.getClass(typeName)

    sym
  }

  def fillTree(className: String, parent: BrowserTreeNode): Unit = {
    val classSym = getClassSymbol(className)
    fillTree(classSym, 0, parent)
  }

  def fillTree(sym: Symbols#Symbol, index: Int, parent: BrowserTreeNode): Unit = {
    val entryNode =
      new BrowserTreeNode("[" + index + "] " + sym.nameString,
        BrowserTreeNode.NODE_SCALASIG,
        index,
        new ScalaSigDetailElementValue(sym))
    parent.add(entryNode)

    if (sym.isClass || sym.isModule || sym.isModuleClass) {
      val members = sym.info.decls.toList
      for ((memberSym, i) <- members.zipWithIndex) {
        fillTree(memberSym, i, entryNode)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val cp = ".:/Users/lomig/Documents/EPFL/3eme_annee/sclasslib/scala/target/pack/lib/scala-library.jar"

    val settings = new Settings()
    settings.processArguments(List("-cp", cp,
                                   "-verbose"), false)
    println("settings=" + settings)

    val g = new Global(settings)
    val run = new g.Run()

    val name = g.newTermName("scala.Array")

    val sym = g.definitions.getClass(name)
    println("sym=" + sym)

    val t = sym.info
    val symbols = t.decls.toList

    println(symbols)

    symbols foreach { s =>
      println(s.nameString)
    }


  }

}
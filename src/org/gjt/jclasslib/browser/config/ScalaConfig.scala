package org.gjt.jclasslib.browser.config

import classpath.{ClasspathEntry, ClasspathChangeEvent, ClasspathChangeListener}
import org.gjt.jclasslib.browser._
import tools.nsc.{Settings, Global}
import org.gjt.jclasslib.structures.elementvalues.ScalaSigDetailElementValue
import java.io.File
import org.gjt.jclasslib.io.Log
/**
 * 
 * @author Lomig Mégard
 */

object ScalaConfig extends ClasspathChangeListener {

  // hack to get this instance from Java
  def instance = this

  private var global: SclasslibGlobal = _
  private var config: BrowserConfig = _

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

    val g = new SclasslibGlobal(settings)
    val run = new g.Run()
    global = g

  }

  def markNode(className: String, parent: BrowserTreeNode): Unit = {
    markedNodes += ((className, parent))
  }

  def insertSignatureDetails(className: String): Unit = {
    markedNodes.get(className) match {
      case Some(parent) =>
        global.fillTree(className.replace(File.separatorChar, '.'), parent)
        markedNodes -= className
      case None =>
        Log.error("Try to insert nodes in " + className + ", but this class had not been marked.")
    }
  }



  private class SclasslibGlobal(settings: Settings) extends Global(settings) {
    object typeInitializer extends TypeTraverser {
      def traverse(tp: Type) {
        mapOver(tp)
      }
      override def mapOver(origSyms: List[Symbol]): List[Symbol] = {
        origSyms.foreach(_.initialize)
        origSyms
      }
    }

    def getClassSymbol(name: String) = {
      val typeName = newTermName(name)
      val sym = definitions.getClass(typeName)
      sym
    }

    def fillTree(className: String, parent: BrowserTreeNode): Unit = {
      val classSym = getClassSymbol(className)
      fillTree(classSym, 1, parent)
    }

    def fillTree(sym: Symbol, index: Int, parent: BrowserTreeNode): Unit = {
      typeInitializer(sym.info)
      val entryNode =
        new BrowserTreeNode("[" + index + "] " + sym.nameString,
          BrowserTreeNode.NODE_SCALASIG,
          index,
          new ScalaSigDetailElementValue(sym))
      parent.add(entryNode)

      if (sym.isClass || sym.isModule || sym.isModuleClass) {
        val members = sym.info.decls.toList
        for ((memberSym, i) <- members.zipWithIndex) {
          //g.typeInitializer(memberSym.info)
          fillTree(memberSym, i, entryNode)
        }
      }
    }

  }


}
package org.gjt.jclasslib.browser.config

import classpath.{ClasspathEntry, ClasspathChangeEvent, ClasspathChangeListener}
import org.gjt.jclasslib.browser._
import org.gjt.jclasslib.structures.elementvalues.ScalaSigDetailElementValue
import java.io.File
import tools.nsc.{Settings, Global}
import org.gjt.jclasslib.util.GUIHelper
import javax.swing.JOptionPane

/**
 * 
 * @author Lomig Mégard
 */

object ScalaConfig extends ClasspathChangeListener {

  // hack to get this instance from Java
  def instance = this

  private var global: Option[SclasslibGlobal] = None
  private var config: BrowserConfig = _

  /*
   * map of nodes that are waiting for an updated classpath
   */
  private var markedNodes: Map[String, BrowserTreeNode] = Map()

  def setConfig(c: BrowserConfig): Unit = config = c

  def classpathChanged(event: ClasspathChangeEvent) = updateGlobal()

  def updateGlobal() {
    try {
      val cp = "-cp " + config.getClasspath.toArray.toList.asInstanceOf[List[ClasspathEntry]].map{cpe => cpe.getFileName}.mkString(":")
      val settings = new Settings()
      settings.processArgumentString(cp)
      val g = new SclasslibGlobal(settings)
      val run = new g.Run()
      global = Some(g)
    } catch { case e =>
      GUIHelper.showMessage(null,
        "The Scala library is not in the classpath. The Scala signatures details will not be displayed.",
        JOptionPane.WARNING_MESSAGE
      )
      global = None
    }
  }

  def markNode(className: String, parent: BrowserTreeNode): Unit = {
    markedNodes += ((className, parent))
  }

  def insertSignatureDetails(className: String): Unit = {
    if(global.isEmpty)
      return; // There were an error, don't try to add the signature.

    markedNodes.get(className) match {
      case Some(parent) =>
        global.get.fillTree(className.replace(File.separatorChar, '.'), parent)
        markedNodes -= className
      case None =>
        // Do nothing: this class has not be marked. Could be a Java class.
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
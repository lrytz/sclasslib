package org.gjt.jclasslib.browser.detail.elementvalues

import org.gjt.jclasslib.browser.detail.FixedListDetailPane
import org.gjt.jclasslib.util.ExtendedJLabel
import javax.swing.tree.TreePath
import org.gjt.jclasslib.browser. {BrowserTreeNode, BrowserServices}
import org.gjt.jclasslib.structures.elementvalues.ScalaSigElementValue
/**
 * Class for showing the Scala signature.
 * @author Lomig Mégard
 */

class ScalaSigElementValueEntryDetailPane(services: BrowserServices) extends FixedListDetailPane(services) {

  lazy private val lblIndex: ExtendedJLabel = linkLabel()
  lazy private val lblIndexVerbose: ExtendedJLabel = highlightLabel()
  lazy private val lblSignature: ExtendedJLabel = highlightLabel()


  protected def setupLabels = {

    addDetailPaneEntry(normalLabel("Constant value:"), lblIndex, lblIndexVerbose)

    addDetailPaneEntry(normalLabel("Extracted signature:"), lblSignature, lblSignature)


  }


  override def show(treePath: TreePath) = {
    val ssev = treePath.getLastPathComponent().asInstanceOf[BrowserTreeNode].getElement().asInstanceOf[ScalaSigElementValue]

    constantPoolHyperlink(lblIndex, lblIndexVerbose, ssev.getConstValueIndex)

    lblSignature.setText(ssev.getFormattedSig)

    super.show(treePath)
  }

}
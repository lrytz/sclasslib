package org.gjt.jclasslib.browser.detail

import javax.swing.tree.TreePath
import org.gjt.jclasslib.util.{GUIHelper, ExtendedJLabel}
import org.gjt.jclasslib.browser.{BrowserTreeNode, BrowserServices, AbstractDetailPane}
import javax.swing._
import org.gjt.jclasslib.structures.elementvalues.ScalaSigDetailElementValue
import collection.mutable.ListBuffer
import java.awt._

/**
 * 
 * @author Lomig Mégard
 */

class ScalaSigDetailPane(services: BrowserServices) extends AbstractDetailPane(services) {

  // visual components
  lazy val scrollPane: JScrollPane = new JScrollPane()
  lazy val entries: ListBuffer[(ExtendedJLabel, ExtendedJLabel)] = new ListBuffer()

  // labels
  lazy private val lblName: ExtendedJLabel = highlightLabel()
  lazy private val lblPrivateWithin: ExtendedJLabel = highlightLabel()
  lazy private val lblOwner: ExtendedJLabel = highlightLabel()
  lazy private val lblType: ExtendedJLabel = highlightLabel()
  lazy private val lblFlags: ExtendedJLabel = highlightLabel()
  lazy private val lblAnno: ExtendedJLabel = highlightLabel()


  protected def setupLabels(): Unit = {
    addDetailPaneEntry(normalLabel("Name:"), lblName)
    addDetailPaneEntry(normalLabel("Private within:"), lblPrivateWithin)
    addDetailPaneEntry(normalLabel("Owner:"), lblOwner)
    addDetailPaneEntry(normalLabel("Type:"), lblType)
    addDetailPaneEntry(normalLabel("Flags:"), lblFlags)
    addDetailPaneEntry(normalLabel("Annotations:"), lblAnno)
  }


  protected def addDetailPaneEntry(key: ExtendedJLabel, value: ExtendedJLabel): Unit = {
    entries.append((key, value))
  }

  protected def setupComponent: Unit = {
    setupLabels()

    this.setLayout(new BorderLayout())

    val panel = new JPanel()

    panel.setLayout(new GridBagLayout())

    val gKey = new GridBagConstraints()
    gKey.anchor = GridBagConstraints.NORTHWEST
    gKey.insets = new Insets(1,10,0,10)

    val gValue = new GridBagConstraints()
    gValue.gridx = 1
    gValue.anchor = GridBagConstraints.NORTHWEST
    gValue.insets = new Insets(1,0,0,5)

    val gRemainder = new GridBagConstraints()
    gRemainder.gridx = 2
    gRemainder.weighty = 1
    gRemainder.weightx = 1
    gRemainder.fill = GridBagConstraints.BOTH

    for ((key, value) <- entries) {
      gKey.gridy += 1
      gValue.gridy = gKey.gridy
      panel.add(key, gKey)
      panel.add(value, gValue)
    }

    gRemainder.gridy = gKey.gridy + 1

    panel.add(new JPanel(), gRemainder)

    scrollPane.setViewportView(panel)
    GUIHelper.setDefaultScrollbarUnits(scrollPane)
    scrollPane.setBorder(null)

    this.add(scrollPane, BorderLayout.CENTER)
    this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Specific info:"))
  }

  def show(treePath: TreePath): Unit = {
    val ssdev = treePath.getLastPathComponent().asInstanceOf[BrowserTreeNode].getElement().asInstanceOf[ScalaSigDetailElementValue]

    lblName.setText(ssdev.name)
    lblPrivateWithin.setText(ssdev.privateWithin)
    lblOwner.setText(ssdev.ownerName)
    lblType.setText(ssdev.tpe)
    lblFlags.setText(ssdev.flags)
    lblAnno.setText(ssdev.annotations.foldLeft("")(_ + ";\n" + _))

    scrollPane.getViewport().setViewPosition(new Point(0, 0))
  }

}
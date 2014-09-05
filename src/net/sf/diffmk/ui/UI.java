// UI.java
//
// This code is derived from the examples in _Java Swing_
// by Eckstein, Loy, and Wood. Published by O'Reilly & Associates, Inc.
// ISBN 1-56592-455-X
//
package net.sf.diffmk.ui;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import net.sf.diffmk.DiffMk;

public class UI {
  public String oldFile = "";
  public String newFile = "";
  public String diffFile = "";
  public boolean diffTypeElement = false;
  public boolean diffTypeText = false;
  public boolean diffTypeBoth = true;
  public boolean wordOpt = false;
  public boolean ignOpt = true;
  public boolean valOpt = false;
  public boolean verbOpt = false;
  private DiffMk theDiffMk = null;

  public void show(DiffMk diffmk) {
      theDiffMk = diffmk;

      // The frame we'll put all this stuff in ...
      final JFrame f = new JFrame(diffmk.version + " Options");

      // Create labels and text fields
      JLabel oldLabel = new JLabel("\"Old\" XML Version:", JLabel.RIGHT);
      JLabel newLabel = new JLabel("\"New\" XML Version:", JLabel.RIGHT);
      JLabel diffLabel = new JLabel("Output XML diff:", JLabel.RIGHT);

      final JTextField oldField = new JTextField(40);
      oldField.setText(oldFile);

      final JTextField newField = new JTextField(40);
      newField.setText(newFile);

      final JTextField diffField = new JTextField(40);
      diffField.setText(diffFile);

      JButton oldBrowse = new JButton("Browse");
      JButton newBrowse = new JButton("Browse");
      JButton diffBrowse = new JButton("Browse");

      oldBrowse.addActionListener(new FilenameListener(f,oldField));
      newBrowse.addActionListener(new FilenameListener(f,newField));
      diffBrowse.addActionListener(new FilenameListener(f,diffField));

      JPanel filenamePanel = new JPanel();
      GridBagLayout gb = new GridBagLayout();
      filenamePanel.setLayout(gb);

      try {
          addComponent(filenamePanel,oldLabel,0,0,1,1,
                  GridBagConstraints.NONE,GridBagConstraints.CENTER);
          addComponent(filenamePanel,oldField,0,1,3,1,
                  GridBagConstraints.NONE,GridBagConstraints.CENTER);
          addComponent(filenamePanel,oldBrowse,0,4,1,1,
                  GridBagConstraints.NONE,GridBagConstraints.CENTER);

          addComponent(filenamePanel,newLabel,1,0,1,1,
                  GridBagConstraints.NONE,GridBagConstraints.CENTER);
          addComponent(filenamePanel,newField,1,1,3,1,
                  GridBagConstraints.NONE,GridBagConstraints.CENTER);
          addComponent(filenamePanel,newBrowse,1,4,1,1,
                  GridBagConstraints.NONE,GridBagConstraints.CENTER);

          addComponent(filenamePanel,diffLabel,2,0,1,1,
                  GridBagConstraints.NONE,GridBagConstraints.CENTER);
          addComponent(filenamePanel,diffField,2,1,3,1,
                  GridBagConstraints.NONE,GridBagConstraints.CENTER);
          addComponent(filenamePanel,diffBrowse,2,4,1,1,
                  GridBagConstraints.NONE,GridBagConstraints.CENTER);
      } catch (Exception e) {
          e.printStackTrace();
      }


      // What kind of diff?
      JRadioButton elementDiff = new JRadioButton("Elements only", diffTypeElement);
      elementDiff.setActionCommand("element");

      JRadioButton textDiff = new JRadioButton("Text only", diffTypeText);
      textDiff.setActionCommand("text");

      JRadioButton bothDiff = new JRadioButton("Both elements and text", diffTypeBoth);
      bothDiff.setActionCommand("both");

      final ButtonGroup diffGroup = new ButtonGroup();
      diffGroup.add(elementDiff);
      diffGroup.add(textDiff);
      diffGroup.add(bothDiff);

      JPanel diffTypePanel = new JPanel();
      diffTypePanel.setLayout(new GridLayout(1,4));
      diffTypePanel.add(elementDiff);
      diffTypePanel.add(textDiff);
      diffTypePanel.add(bothDiff);

      // Diff Options
      final JCheckBox wordOptBox = new JCheckBox("Word-level diff", wordOpt);
      final JCheckBox ignOptBox = new JCheckBox("Ignore whitespace", ignOpt);
      final JCheckBox valOptBox = new JCheckBox("Validating", valOpt);
      final JCheckBox verbOptBox = new JCheckBox("Verbose", verbOpt);

      JPanel optionPanel = new JPanel();
      optionPanel.setLayout(new GridLayout(2,2));
      optionPanel.add(wordOptBox);
      optionPanel.add(ignOptBox);
      optionPanel.add(valOptBox);
      optionPanel.add(verbOptBox);

      // Ok or Cancel
      JButton ok = new JButton("OK");
      JButton cancel = new JButton("Cancel");

      JPanel buttonPanel = new JPanel();
      buttonPanel.add(ok);
      buttonPanel.add(cancel);

      ok.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent ev) {
              String diffType = diffType = diffGroup.getSelection().getActionCommand();

              f.setVisible(false);

              theDiffMk.setOriginalInput(oldField.getText());
              theDiffMk.setChangedInput(newField.getText());
              theDiffMk.setOutput(diffField.getText());
              theDiffMk.setDiffType(diffType);
              theDiffMk.setDiffWords(wordOptBox.isSelected());
              theDiffMk.setIgnoreWhitespace(ignOptBox.isSelected());
              theDiffMk.setValidating(valOptBox.isSelected());
              theDiffMk.setVerbosity(verbOptBox.isSelected() ? 1 : 0);

	  f.dispose();
	}
    });

    cancel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ev) {
            f.setVisible(false);
            f.dispose();
            System.exit(1);
        }
    });

    // Layout and Display
    JPanel p = new JPanel();
    gb = new GridBagLayout();
    p.setLayout(gb);

    try {
        addComponent(p,filenamePanel,0,0,4,3,
                GridBagConstraints.NONE,GridBagConstraints.CENTER);
        addComponent(p,diffTypePanel,3,0,4,1,
                GridBagConstraints.NONE,GridBagConstraints.CENTER);
        addComponent(p,optionPanel,4,0,4,2,
                GridBagConstraints.NONE,GridBagConstraints.CENTER);
        addComponent(p,buttonPanel,7,0,4,1,
                GridBagConstraints.NONE,GridBagConstraints.CENTER);
    } catch (Exception e) {
        e.printStackTrace();
    }

    f.addWindowListener(new BasicWindowMonitor());
    f.setContentPane(p);
    f.pack();
    f.setVisible(true);
    
    while (f.isVisible()) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ie) {
            // nop;
        }
    }
  }

  private void addComponent(Container container,
                            Component component,
                            int gridy, int gridx,
			    int gridWidth, int gridHeight,
			    int fill, int anchor)
                            throws AWTException {
      LayoutManager lm = container.getLayout();
      if (!(lm instanceof GridBagLayout)) {
          throw new AWTException("Invalid layout");
      } else {
          GridBagConstraints gbc = new GridBagConstraints();
          gbc.gridx = gridx;
          gbc.gridy = gridy;
          gbc.gridwidth = gridWidth;
          gbc.gridheight = gridHeight;
          gbc.fill = fill;
          gbc.anchor = anchor;
          ((GridBagLayout)lm).setConstraints(component, gbc);
          container.add(component);
      }
  }
}

package csiifinal;

import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;

public class MainPanel extends javax.swing.JPanel {

    File file;
    ArrayList<Compressor> compressors;
    static final String suffix = ".mycomp";
    JFileChooser fc;

    public MainPanel() {
        initComponents();
        compressButton.setEnabled(false);
        text.setEditable(false);
        compressors = new ArrayList<>();
        //Add the compressors to this array
        //compressors.add(new TestCompressor());
        compressors.add(new Huffman());
        compressors.add(new ArithmeticCoding());

        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (Compressor c : compressors) {
            model.addElement(new BoxItem(c.getName()));
        }
        comboBox.setModel(model);
        fc = new JFileChooser();
        fc.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                boolean test = false;
                for (Compressor c : compressors) {
                    if (pathname.getPath().endsWith(c.getSuffix())) {
                        test = true;
                        break;
                    }
                }
                return (test || pathname.getPath().endsWith(".txt") || pathname.isDirectory());
            }

            @Override
            public String getDescription() {
                return ".txt and compressed files";
            }

        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Compression Pro 2.0");
        frame.add(new MainPanel());
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        openButton = new javax.swing.JButton();
        createButton = new javax.swing.JButton();
        fileLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        text = new javax.swing.JTextPane();
        comboBox = new javax.swing.JComboBox();
        compressButton = new javax.swing.JButton();
        allowEditingBox = new javax.swing.JCheckBox();

        openButton.setText("Open File");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        createButton.setText("Create File");
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        fileLabel.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        fileLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fileLabel.setText("No File Selected");

        text.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(text);

        comboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Compresson 1", "Compression 2", "Compression 3" }));

        compressButton.setText("Compress");
        compressButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compressButtonActionPerformed(evt);
            }
        });

        allowEditingBox.setText("Allow Editing");
        allowEditingBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allowEditingBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fileLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(comboBox, 0, 267, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(compressButton, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(allowEditingBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(createButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fileLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(allowEditingBox)
                    .addComponent(openButton)
                    .addComponent(createButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(compressButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        int choice = fc.showOpenDialog(this);
        if (choice != JFileChooser.APPROVE_OPTION) {
            return;
        }
        file = fc.getSelectedFile();//Use file chooser to get a user chosen file
        text.setText("");//reset the text
        text.setEditable(allowEditingBox.isSelected());
        Document doc = text.getDocument();
        fileLabel.setText(file.getName());
        if (file.getPath().endsWith(".txt")) {//Do a regular load of the text file
            BufferedInputStream out;
            EditorKit kit = text.getEditorKit();
            try {
                out = new BufferedInputStream(new FileInputStream(file));
                kit.read(out, doc, 0);
            } catch (FileNotFoundException e) {
            } catch (IOException | BadLocationException e) {
            }
            compressButton.setEnabled(true);
        } else {//Do the custom load of the compression algorythm
            compressButton.setEnabled(false);
            //Find which compressor to use
            Compressor comp = null;
            for (Compressor c : compressors) {
                if (file.getPath().endsWith(c.getSuffix())) {
                    comp = c;
                    break;
                }
            }
            String txt = (comp.loadFile(file));
//            System.out.println("Loaded file: "+txt);
            //Decompress
            try {
                text.setText(comp.decompress(txt));
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(this, "File type not supported.");
            }
        }

    }//GEN-LAST:event_openButtonActionPerformed

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
        text.setEditable(true);
        text.setText("");
        text.requestFocus();
        compressButton.setEnabled(false);
        fileLabel.setText("New File");
    }//GEN-LAST:event_createButtonActionPerformed

    private void allowEditingBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allowEditingBoxActionPerformed
        if (file != null) {
            text.setEditable(allowEditingBox.isSelected());
        }
    }//GEN-LAST:event_allowEditingBoxActionPerformed

    private void compressButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compressButtonActionPerformed
        //get the corresponding compressor
        Compressor c = compressors.get(comboBox.getSelectedIndex());

        if (file != null) {
            fc.setSelectedFile(new File(file.getPath().substring(0, file.getPath().indexOf(".txt")) + c.getSuffix()));
        } else {
            fc.setSelectedFile(new File(fc.getCurrentDirectory().getPath() + "/" + "NewFile" + c.getSuffix()));
        }
        int choice = fc.showSaveDialog(this);
        if (choice != JFileChooser.APPROVE_OPTION) {
            return;
        }
        fileLabel.setText("No File Selected");
        compressButton.setEnabled(false);
        file = fc.getSelectedFile();                    //Use file chooser to get a user chosen file'
        long time = System.currentTimeMillis();         //Record the time before         
        String result = c.compress(text.getText());     //Decompress the file
        time = System.currentTimeMillis()-time;         //Fine the elapsed time
        c.saveFile(result, file);                       //Save the file
        JFrame frame;
        JTextPane pane;
        
        int size = c.getSize(result);
        double comp = ((double)size/((double)text.getText().length()));
        //System.out.println(size+" "+text.getText().length());
        
        JOptionPane.showMessageDialog(this.getRootPane(), "Compression Time: "+(time)+" millis\nCompression File Size: "+size+" bytes\nCompression Rate: "+comp);
        int user = JOptionPane.showOptionDialog(this.getRootPane(), "Would you like to decompress?", "Decompress", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (user == JOptionPane.YES_OPTION) {
            //Open the new window
            frame = new JFrame("Decompressed File");
            frame.setLayout(new BorderLayout());
            pane = new JTextPane();
            pane.setText(c.decompress(result));
            frame.add(new JScrollPane(pane));

            frame.setSize(this.getSize());
            frame.setLocation(this.getLocation());
            frame.setVisible(true);
        }
        
        //Open the new window
        frame = new JFrame("Exported Document (String Version)");
        frame.setLayout(new BorderLayout());
        pane = new JTextPane();
        pane.setText(result);
        frame.add(new JScrollPane(pane));

        frame.setSize(this.getSize());
        frame.setLocation(this.getLocation());
        frame.setVisible(true);
        
    }//GEN-LAST:event_compressButtonActionPerformed

    private void textKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textKeyPressed
        if (!text.getText().isEmpty()) {
            compressButton.setEnabled(true);
        }
    }//GEN-LAST:event_textKeyPressed

    class BoxItem {

        String name;

        public BoxItem(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox allowEditingBox;
    private javax.swing.JComboBox comboBox;
    private javax.swing.JButton compressButton;
    private javax.swing.JButton createButton;
    private javax.swing.JLabel fileLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton openButton;
    private javax.swing.JTextPane text;
    // End of variables declaration//GEN-END:variables
}

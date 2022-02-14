package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormEscolher {
    private JPanel panelEscolher;
    private JButton ModeloButton;
    private JButton MarcasButton;

    public FormEscolher() {
        ModeloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormModelos().setVisible(false);
            }
        });

        MarcasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FormMarca().setVisible(true);
            }
        });


    }

    public void setVisible(boolean b)
    {
        JFrame frame=new JFrame("Escolher");
        frame.setContentPane(new FormEscolher().panelEscolher);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900,900);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
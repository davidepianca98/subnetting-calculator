package gui;

import ip.Ip;
import ip.IpCalc;
import ip.SubnetMask;

import javax.swing.*;
import java.awt.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class Gui {

    private JTextField ip1;
    private JTextField ip2;
    private JTextField ip3;
    private JTextField ip4;
    private JTextField sm;
    private Label netsLabel;
    private Label hostsLabel;
    private Label classLabel;
    private JButton buttonOpen;

    public Gui() {
        JFrame mainFrame = new JFrame("Subnetting");
        mainFrame.setSize(350, 200);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Label l1 = new Label("IP:");
        Label l2 = new Label("/");
        classLabel = new Label();
        hostsLabel = new Label();
        netsLabel = new Label();

        JPanel p1 = new JPanel();

        ip1 = new JTextField(4);
        ip2 = new JTextField(4);
        ip3 = new JTextField(4);
        ip4 = new JTextField(4);
        sm = new JTextField(4);

        p1.add(l1);
        p1.add(ip1);
        p1.add(ip2);
        p1.add(ip3);
        p1.add(ip4);
        p1.add(l2);
        p1.add(sm);

        JPanel p2 = new JPanel(new GridLayout(4, 1));

        buttonOpen = new JButton("Show subnet IPs");
        buttonOpen.setVisible(false);
        p2.add(classLabel);
        p2.add(netsLabel);
        p2.add(hostsLabel);
        p2.add(buttonOpen);

        JButton buttonSet = new JButton("Calculate");
        buttonSet.addActionListener(e -> doAll());

        mainFrame.add(p1);
        mainFrame.add(buttonSet);
        mainFrame.add(p2);
        mainFrame.setVisible(true);
    }

    private void doAll() {
        try {
            InetAddress address = Inet4Address.getByName(ip1.getText() + "." + ip2.getText() + "." + ip3.getText() + "." + ip4.getText());
            classLabel.setText("Class " + Ip.getIpClass(address));
            SubnetMask subnetMask = new SubnetMask(Integer.parseInt(sm.getText()));
            if ((subnetMask.getBits() < 16 && Ip.getIpClass(address) == Ip.CLASS_B) ||
                    subnetMask.getBits() < 24 && Ip.getIpClass(address) == Ip.CLASS_C) {
                throw new Exception();
            }
            netsLabel.setText("Net number: " + IpCalc.netNumber(address, subnetMask));
            hostsLabel.setText("Host number: " + IpCalc.hostNumber(subnetMask));
            buttonOpen.setVisible(true);
            if (buttonOpen.getActionListeners().length > 0)
                buttonOpen.removeActionListener(buttonOpen.getActionListeners()[0]);
            buttonOpen.addActionListener(e -> new ShowIpGui(address, subnetMask));
        } catch (UnknownHostException e) {
            classLabel.setText("Incorrect IP address");
            netsLabel.setText("");
            hostsLabel.setText("");
            buttonOpen.setVisible(false);
        } catch (Exception e) {
            classLabel.setText("Incorrect Subnet Mask");
            netsLabel.setText("");
            hostsLabel.setText("");
            buttonOpen.setVisible(false);
        }
    }
}

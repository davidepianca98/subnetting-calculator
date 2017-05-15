package gui;

import ip.Ip;
import ip.IpCalc;
import ip.SubnetMask;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.util.List;

public class ShowIpGui {

    public ShowIpGui(InetAddress ip, SubnetMask sm) {
        if (Ip.isAddressPure(ip, sm)) {
            showError();
        } else {
            start(ip, sm);
        }
    }

    private void start(InetAddress ip, SubnetMask sm) {
        JFrame mainFrame = new JFrame("Available IPs");
        mainFrame.setSize(300, 300);
        mainFrame.setLayout(new GridLayout(1, 1));
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel p = new JPanel();
        JScrollPane scrollBar = new JScrollPane(p, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        p.add(new JLabel("Subnet IPs"));
        List<InetAddress> addresses = IpCalc.netIpAddress(ip, sm);

        if (addresses != null) {
            for (InetAddress address : addresses) {
                p.add(new JLabel(address.getHostAddress()));
            }
        }

        mainFrame.add(scrollBar);
        mainFrame.setVisible(true);
    }

    private void showError() {
        JFrame mainFrame = new JFrame("Pure IP");
        mainFrame.setSize(350, 100);
        mainFrame.setLayout(new GridLayout(1, 1));
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(new JLabel("The IP is pure, Subnetting not possible"));
        mainFrame.add(p);
        mainFrame.setVisible(true);
    }
}

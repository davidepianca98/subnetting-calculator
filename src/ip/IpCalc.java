package ip;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains static methods to calculate hosts and nets numbers
 *
 * @author davide
 */
public class IpCalc {

    /**
     * Calculates the number of possible subnets with the given IP address and subnet mask
     *
     * @param ip IP address
     * @param sm Subnet Mask
     * @return number of possible subnets
     */
    public static int netNumber(InetAddress ip, SubnetMask sm) {
        int netBits = sm.getBits();
        switch (Ip.getIpClass(ip)) {
            case Ip.CLASS_A:
                netBits -= 8;
                break;
            case Ip.CLASS_B:
                netBits -= 16;
                break;
            case Ip.CLASS_C:
                netBits -= 24;
                break;
        }
        return (int) Math.pow(2, netBits);
    }

    /**
     * Calculates the number of possible hosts with an IP
     *
     * @param sm Subnet Mask
     * @return number of possible hosts
     */
    public static int hostNumber(SubnetMask sm) {
        return (int) Math.pow(2, 32 - sm.getBits()) - 2;
    }

    /**
     * Builds an array of subnet IPs with the provided IP and subnet mask
     *
     * @param ip IP address
     * @param sm Subnet Mask
     * @return list with possible IPs
     */
    public static List<InetAddress> netIpAddress(InetAddress ip, SubnetMask sm) {
        switch (Ip.getIpClass(ip)) {
            case Ip.CLASS_A:
                return netIpAddressA(ip, sm);
            case Ip.CLASS_B:
                return netIpAddressB(ip, sm);
            case Ip.CLASS_C:
                return netIpAddressC(ip, sm);
            default:
                return null;
        }
    }

    /**
     * Builds an array of subnet IPs for class A with the provided IP and subnet mask
     *
     * @param ip IP address
     * @param sm Subnet Mask
     * @return list with possible IPs
     */
    private static List<InetAddress> netIpAddressA(InetAddress ip, SubnetMask sm) {
        int netBits = sm.getBits() - 8;
        int netn = netNumber(ip, sm);
        List<InetAddress> list = new ArrayList<>();

        if (netBits < 8) {
            for (int i = 0; i < netn; i++) {
                try {
                    String host = (ip.getAddress()[0] & 0xFF) + "." + (int) Math.pow(2, 8 - netBits) * i + ".0.0";
                    list.add(InetAddress.getByName(host));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (netBits < 16) {
            for (int i = 0; i < 256; i++) {
                for (int j = 0; j < (netBits - 8) + 1; j++) {
                    try {
                        String host = (ip.getAddress()[0] & 0xFF) + "." + i + "." + (int) Math.pow(2, 8 - (netBits - 8)) * j + ".0";
                        list.add(InetAddress.getByName(host));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            for (int i = 0; i < 256; i++) {
                for (int j = 0; j < 256; j++) {
                    for (int k = 0; k < (netBits - 16) + 1; k++) {
                        try {
                            String host = (ip.getAddress()[0] & 0xFF) + "." + i + "." + j + "." + (int) Math.pow(2, 8 - (netBits - 16)) * k;
                            list.add(InetAddress.getByName(host));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * Builds an array of subnet IPs for class B with the provided IP and subnet mask
     *
     * @param ip IP address
     * @param sm Subnet Mask
     * @return list with possible IPs
     */
    private static List<InetAddress> netIpAddressB(InetAddress ip, SubnetMask sm) {
        int netBits = sm.getBits() - 16;
        int netn = netNumber(ip, sm);
        List<InetAddress> list = new ArrayList<>();

        if (netBits < 8) {
            for (int i = 0; i < netn; i++) {
                try {
                    String host = (ip.getAddress()[0] & 0xFF) + "." + (ip.getAddress()[1] & 0xFF) + "." + (int) Math.pow(2, 8 - netBits) * i + ".0";
                    list.add(InetAddress.getByName(host));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (int i = 0; i < 256; i++) {
                for (int j = 0; j < (netBits - 8) + 1; j++) {
                    try {
                        String host = (ip.getAddress()[0] & 0xFF) + "." + (ip.getAddress()[1] & 0xFF) + "." + i + "." + (int) Math.pow(2, 8 - (netBits - 8)) * j;
                        list.add(InetAddress.getByName(host));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return list;
    }

    /**
     * Builds an array of subnet IPs for class C with the provided IP and subnet mask
     *
     * @param ip IP address
     * @param sm Subnet Mask
     * @return list with possible IPs
     */
    private static List<InetAddress> netIpAddressC(InetAddress ip, SubnetMask sm) {
        int netBits = sm.getBits() - 24;
        int netn = netNumber(ip, sm);
        List<InetAddress> list = new ArrayList<>();

        for (int i = 0; i < netn; i++) {
            try {
                String host = (ip.getAddress()[0] & 0xFF) + "." + (ip.getAddress()[1] & 0xFF) + "." + (ip.getAddress()[2] & 0xFF) + "." + (int) Math.pow(2, 8 - netBits) * i;
                list.add(InetAddress.getByName(host));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}

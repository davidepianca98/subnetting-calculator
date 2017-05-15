package ip;


import java.net.InetAddress;

/**
 * IP utils
 */
public class Ip {

    public static final char CLASS_A = 'A';
    public static final char CLASS_B = 'B';
    public static final char CLASS_C = 'C';
    private static final int CLASS_A_START = 0; // First byte class A limit
    private static final int CLASS_A_END = 126; // First byte class A limit
    private static final int CLASS_B_START = 128; // First byte class B limit
    private static final int CLASS_B_END = 191; // First byte class B limit
    private static final int CLASS_C_START = 192; // First byte class C limit
    private static final int CLASS_C_END = 223; // First byte class C limit

    /**
     * Checks the class of the given IP
     *
     * @return the class
     */
    public static char getIpClass(InetAddress address) {
        int firstByte = address.getAddress()[0] & 0xFF;
        if ((firstByte >= CLASS_A_START) && (firstByte <= CLASS_A_END)) {
            return CLASS_A;
        } else if ((firstByte >= CLASS_B_START) && (firstByte <= CLASS_B_END)) {
            return CLASS_B;
        } else if ((firstByte >= CLASS_C_START) && (firstByte <= CLASS_C_END)) {
            return CLASS_C;
        }
        return 0;
    }

    /**
     * Checks if the address and subnet mask match as pure
     *
     * @param address    IP address
     * @param subnetMask IP's subnet mask
     * @return true if pure
     */
    public static boolean isAddressPure(InetAddress address, SubnetMask subnetMask) {
        return (subnetMask.getBits() == SubnetMask.PURE_CLASS_A && Ip.getIpClass(address) == Ip.CLASS_A) ||
                (subnetMask.getBits() == SubnetMask.PURE_CLASS_B && Ip.getIpClass(address) == Ip.CLASS_B) ||
                (subnetMask.getBits() == SubnetMask.PURE_CLASS_C && Ip.getIpClass(address) == Ip.CLASS_C);
    }
}

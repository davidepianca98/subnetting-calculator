package ip;

/**
 * Represents an IPv4 Subnet Mask
 *
 * @author davide
 */
public class SubnetMask {

    static final int PURE_CLASS_A = 8;
    static final int PURE_CLASS_B = 16;
    static final int PURE_CLASS_C = 24;

    private int bits;

    /**
     * Builds the subnet mask taking the bits number
     *
     * @param n number of bits set to 1
     * @throws Exception on error
     */
    public SubnetMask(int n) throws Exception {
        if (n > 30)
            throw new Exception("Subnet Mask too big");
        else if (n < 8)
            throw new Exception("Subnet Mask too small");

        bits = n;
    }

    public int getBits() {
        return bits;
    }
}

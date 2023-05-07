import java.util.*;

/**
 * Prefix codes and Huffman tree.
 * Tree depends on source data.
 */
public class Huffman {

    private Node[] leaves;
    private Node root;
    private int hCodeLength;

    private int originalLength;

    class Node {
        private byte[] code;
        private int codeLength;
        private int frequency;
        private Node left;
        private Node right;
        private byte symbol;

    }


    /**
     * Constructor to build the Huffman code for a given bytearray.
     * Builds the Huffman coding tree based on the byte array
     *
     * @param original source data
     */
    Huffman(byte[] original) {
        leaves = new Node[256];
        for (int i = 0; i < 256; i++) {
            leaves[i] = new Node();
            leaves[i].left = null;
            leaves[i].right = null;
            leaves[i].symbol = (byte) (i - 128);
            leaves[i].frequency = 0;
        }

        if (original.length == 0) {
            root = null;
            return;
        }

        for (int i = 0; i < original.length; i++) {
            byte b = original[i];
            leaves[b + 128].frequency++; // byte varies from -128 to 127, by adding 128 we make the range 0 to 255
            leaves[b + 128].symbol = b;
        }

        LinkedList<Node> roots = new LinkedList<Node>();
        for (int i = 0; i < 256; i++) {
            if (leaves[i].frequency > 0) {
                roots.add((leaves[i]));
            }
        }
        while (roots.size() > 1) {
            Node least1 = removeSmallest(roots);
            Node least2 = removeSmallest(roots);
            Node newRoot = new Node();
            newRoot.left = least1;
            newRoot.right = least2;
            newRoot.frequency = least1.frequency + least2.frequency;
            roots.addLast(newRoot);
        }
        root = (Node) roots.remove(0);

        setCodes(root, new byte[]{});
        originalLength = original.length;

    }

    private void setCodes(Node root, byte[] code) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            if (code.length == 0) code = addBit(code, (byte)0);
            root.code = code;
            root.codeLength = code.length;
            hCodeLength += root.frequency * code.length;
            return;
        }
        setCodes(root.left, addBit(code, (byte) 0));
        setCodes(root.right, addBit(code, (byte) 1));
    }

    /**
     * @param code  - input byte array
     * @param value - value to append (either 1 or 0)
     * @return new byte array with appended value
     */
    private byte[] addBit(byte[] code, byte value) {
        if (code.length == 0) {
            return new byte[]{value};
        }
        byte[] newCode = new byte[code.length + 1];
        System.arraycopy(code, 0, newCode, 0, code.length);
        newCode[code.length] = value;
        return newCode;
    }

    // gets the node with the smallest frequency and removes it from linked list
    private Node removeSmallest(LinkedList<Node> roots) {
        Node smallest = roots.get(0);
        for (int i = 1; i < roots.size(); i++) {
            if (roots.get(i).frequency < smallest.frequency) {
                smallest = roots.get(i);
            }
        }
        roots.remove(smallest);
        return smallest;
    }

    /**
     * Length of encoded data in bits.
     *
     * @return number of bits
     */
    public int bitLength() {
        return hCodeLength;
    }

    /**
     * Encoding the byte array using this prefixcode.
     * Encodes the given byte array using the calculated coding tree
     *
     * @param origData original data
     * @return encoded data
     */
    public byte[] encode(byte[] origData) {
        byte[] code = new byte[originalLength];
        int index = 0;
        for (byte input : origData) {
            byte[] inputCode = leaves[input + 128].code;
            byte res = 0;
            for (byte b : inputCode) {
                res = (byte) ((res << 1) | b);
            }
            code[index] = res;
            index++;
        }
        return code;
    }

    // convert the encoded array which has the decimal representation of the input to the byte array
    // which has binary representation of the data
    public byte[] getByteArray(byte[] encodedData) {
        byte[] output = new byte[bitLength()];
        int index = 0;
        for (byte b: encodedData) {
            String binaryString = Integer.toBinaryString(b);
            for (Character c: binaryString.toCharArray()) {
                output[index] = Byte.parseByte(c.toString());
                index++;
            }
        }
        return output;
    }

    /**
     * Decoding the byte array using this prefixcode.
     *
     * @param encodedData encoded data
     * @return decoded data (hopefully identical to original)
     */
    public byte[] decode(byte[] encodedData) {
        Node curr = root;
        byte [] output = new byte[originalLength];

        // only root node available
        if (curr.left == null && curr.right == null) {
            for (int i = 0; i < originalLength; i++) {
                output[i] = curr.symbol;
            }
            return output;
        }

        encodedData = getByteArray(encodedData);
        int index = 0;
        for (int i = 0; i < encodedData.length; i++) {
            if (encodedData[i] == 0) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
            if (curr.left == null && curr.right == null) {
                output[index] = curr.symbol;
                index++;
                curr = root;
            }
        }
        return output; // TODO!!!
    }

    /**
     * Main method.
     */
    public static void main(String[] params) {
        String input = "AAABBCF";
        byte[] orig = input.getBytes();
        Huffman huf = new Huffman(orig);
        byte[] code = huf.encode(orig);
        byte[] orig2 = huf.decode(code);
        // must be equal: orig, orig2
        System.out.println(Arrays.equals(orig, orig2));
        int length = huf.bitLength();
        System.out.println("Length of encoded data in bits: " + length);
        // TODO!!! Your tests here!
    }
}


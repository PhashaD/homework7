
import java.util.*;

/**
 * Prefix codes and Huffman tree.
 * Tree depends on source data.
 */
public class Huffman {

   // TODO!!! Your instance variables here!

   /** Constructor to build the Huffman code for a given bytearray.
    * @param original source data
    */
   Huffman (byte[] original) {
      // TODO!!! Your constructor here!
   }

   /** Length of encoded data in bits. 
    * @return number of bits
    */
   public int bitLength() {
      return 0; // TODO!!!
   }


   /** Encoding the byte array using this prefixcode.
    * @param origData original data
    * @return encoded data
    */
   public byte[] encode (byte [] origData) {
      return null; // TODO!!!
   }

   /** Decoding the byte array using this prefixcode.
    * @param encodedData encoded data
    * @return decoded data (hopefully identical to original)
    */
   public byte[] decode (byte[] encodedData) {
      return null; // TODO!!!
   }

   /** Main method. */
   public static void main (String[] params) {
      String tekst = "AAAAAAAAAAAAABBBBBBCCCDDEEF";
      byte[] orig = tekst.getBytes();
      Huffman huf = new Huffman (orig);
      byte[] kood = huf.encode (orig);
      byte[] orig2 = huf.decode (kood);
      // must be equal: orig, orig2
      System.out.println (Arrays.equals (orig, orig2));
      int lngth = huf.bitLength();
      System.out.println ("Length of encoded data in bits: " + lngth);
      // TODO!!! Your tests here!
   }
}


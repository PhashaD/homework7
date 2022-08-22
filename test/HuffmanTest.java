
import static org.junit.Assert.*;
import org.junit.Test;
// import java.util.*;

/** Test class.
 * @author jaanus
 */
public class HuffmanTest {

   @Test (timeout=1000)
   public void testCodeDecode() { 
      String text = "AAAAAAABBBBBCCCDE";
      byte[] orig = text.getBytes();
      // Huffman code constructor
      Huffman huf = new Huffman (orig);
      // encoding
      byte[] code = huf.encode (orig);
      // decoding
      byte[] orig2 = huf.decode (code);
      assertArrayEquals ("Not the same text " + text, orig, orig2);
      assertTrue ("Code cannot be longer than original",  
         code.length <= orig.length);
      text = "AAA";
      orig = text.getBytes();
      huf = new Huffman (orig);
      code = huf.encode (orig);
      orig2 = huf.decode (code);
      assertArrayEquals ("Not the same text " + text, orig, orig2);
   }

   @Test (timeout=1000)
   public void testLength() {
      String text = "ABCDEFAAABBC";
      Huffman huf = new Huffman (text.getBytes());
      huf.encode (text.getBytes());
      assertEquals (text + " number of bits in optimal encoding", 29,
         huf.bitLength());
      text = "A";
      huf = new Huffman (text.getBytes());
      huf.encode (text.getBytes());
      assertEquals (text + " number of bits in optimal encoding", 1,
         huf.bitLength());
      text = "ABCD";
      huf = new Huffman (text.getBytes());
      huf.encode (text.getBytes());
      assertEquals (text + " number of bits in optimal encoding", 8,
         huf.bitLength());
      text = "AAAABC";
      huf = new Huffman (text.getBytes());
      huf.encode (text.getBytes());
      assertEquals (text + " number of bits in optimal encoding", 8,
         huf.bitLength());
      text = "1234567890ABCDEF";
      huf = new Huffman (text.getBytes());
      huf.encode (text.getBytes());
      assertEquals (text + " number of bits in optimal encoding", 64,
         huf.bitLength());
   }

   @Test (expected=RuntimeException.class)
   public void testNullObject() {
      new Huffman (null);
   }

}


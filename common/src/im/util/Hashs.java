package im.util;

import java.io.ByteArrayInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Formatter;

/**
 *
 * @author Stanislav Peshterliev
 */
final public class Hashs {

    public static String sh1(String text) {
        try {
            return calculateHash(MessageDigest.getInstance("SHA1"), text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String calculateHash(MessageDigest algorithm,
            String text) throws Exception {

        DigestInputStream dis = new DigestInputStream(new ByteArrayInputStream(text.getBytes("UTF-8")), algorithm);

        // read the file and update the hash calculation
        while (dis.read() != -1) ;

        // get the hash value as byte array
        byte[] hash = algorithm.digest();

        return byteArray2Hex(hash);
    }

    private static String byteArray2Hex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}

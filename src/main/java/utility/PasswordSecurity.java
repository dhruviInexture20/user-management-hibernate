package utility;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class PasswordSecurity {
	
	
	private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = "1234567891234567".getBytes();
    private static Key securityKey;
    
    
    public PasswordSecurity() throws Exception{
//    	securityKey = generateKey();
    	securityKey = new SecretKeySpec(keyValue, ALGORITHM);
    }
	
//    private static Key generateKey() throws Exception {
//        Key key = new SecretKeySpec(keyValue, ALGORITHM);
//        return key;
//    }
//	
	public String encrypt(String valueToEnc) throws Exception {
	       Cipher cipher = Cipher.getInstance(ALGORITHM);
	       cipher.init(Cipher.ENCRYPT_MODE, securityKey);
	 
	       byte[] encValue = cipher.doFinal(valueToEnc.getBytes());
	       byte[] encryptedByteValue = new Base64().encode(encValue);
	 
	       return new String(encryptedByteValue);
	   }
	
	public String decrypt(String encryptedValue) throws Exception {
	        //Key key = generateKey();
	        Cipher cipher = Cipher.getInstance(ALGORITHM);
	        cipher.init(Cipher.DECRYPT_MODE, securityKey);
	        
	        byte[] decodedBytes = new Base64().decode(encryptedValue.getBytes());
	 
	        byte[] enctVal = cipher.doFinal(decodedBytes);
	        
	        return new String(enctVal);
	    }
	
}

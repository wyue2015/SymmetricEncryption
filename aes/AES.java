package aes;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.encoders.Hex;


public class AES {
	
	public static byte[] generKey(){
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyGenerator.init(128);
		SecretKey secretKey = keyGenerator.generateKey();
		byte[] binary_key = secretKey.getEncoded();
		
		return binary_key;
	}
	
	
	public static Key convertKey(byte[] binary_key){
		SecretKey secretKey = new SecretKeySpec(binary_key, "AES");
		
		return secretKey;
	}
	
	public static byte[] jdkAESEncryption(byte[] unencrypted_data, byte[] binary_key){
		byte[] encrypted_data = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, AES.convertKey(binary_key));
			encrypted_data = cipher.doFinal(unencrypted_data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encrypted_data;
	}
	
	public static byte[] jdkAESDecryption(byte[] encrypted_data, byte[] binary_key){
		byte[] decrypted_data = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, AES.convertKey(binary_key));
			decrypted_data = cipher.doFinal(encrypted_data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return decrypted_data;
	}
	
	public static void main(String[] args) {
		byte[] binary_key = AES.generKey();
		byte[] encrypted_data = AES.jdkAESEncryption("asÄãºÃ".getBytes(), binary_key);
		System.out.println(Hex.toHexString(encrypted_data));
		byte[] decrypted_data = AES.jdkAESDecryption(encrypted_data, binary_key);
		System.out.println(new String(decrypted_data));
		
		
	}
	
	
}

package pbe;

import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.bouncycastle.util.encoders.Hex;

/**
 * 
 * @author wy
 *	基于JDK方式实现PBE加解密
 */
public class PBE {
	
	/**
	 * 生成“盐”
	 * “盐”的长度必须8字节
	 * @return
	 */
	public static byte[] salt(){
		SecureRandom secureRandom = new SecureRandom();//实例化安全随机数
		byte[] salt_bytes = secureRandom.generateSeed(8);//产出“盐”
		
		return salt_bytes;
	}
	
	/**
	 * 将自定义口令password转成PBE算法的密钥
	 * @param password
	 * @return
	 */
	public static Key convertKey(String password){
		PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
		SecretKey secretKey = null;
		try {
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
			secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return secretKey;
	}
	
	/**
	 * 根据“盐”+口令实现对数据的加密
	 * @param unencrypted_data
	 * @param password
	 * @param salt
	 * @return
	 */
	public static byte[] jdkPBEEncryption(byte[] unencrypted_data, String password, byte[] salt){
		PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);//注意指定迭代次数
		byte[] encryptied_data = null;
		try {
			Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
			cipher.init(Cipher.ENCRYPT_MODE, PBE.convertKey(password), pbeParameterSpec);
			encryptied_data = cipher.doFinal(unencrypted_data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encryptied_data;
	}
	
	/**
	 * 根据“盐”+口令实现对数据的解密
	 * @param encrypted_data
	 * @param password
	 * @param salt
	 * @return
	 */
	public static byte[] jdkPBEDencryption(byte[] encrypted_data, String password, byte[] salt){
		PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
		byte[] encryptied_data = null;
		try {
			Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
			cipher.init(Cipher.DECRYPT_MODE, PBE.convertKey(password), pbeParameterSpec);
			encryptied_data = cipher.doFinal(encrypted_data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return encryptied_data;
	}
	
	//测试
	public static void main(String[] args) {
		byte[] salt = PBE.salt();
		String password = "123456";
		String origin_data = "hello 你好";
		byte[] encrypted_data = PBE.jdkPBEEncryption(origin_data.getBytes(), password, salt);
		System.out.println(Hex.toHexString(encrypted_data));
		byte[] unencrypted_data = PBE.jdkPBEDencryption(encrypted_data, password, salt);
		System.out.println(new String(unencrypted_data));
	}
	
}

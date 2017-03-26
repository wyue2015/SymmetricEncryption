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
 *	����JDK��ʽʵ��PBE�ӽ���
 */
public class PBE {
	
	/**
	 * ���ɡ��Ρ�
	 * ���Ρ��ĳ��ȱ���8�ֽ�
	 * @return
	 */
	public static byte[] salt(){
		SecureRandom secureRandom = new SecureRandom();//ʵ������ȫ�����
		byte[] salt_bytes = secureRandom.generateSeed(8);//�������Ρ�
		
		return salt_bytes;
	}
	
	/**
	 * ���Զ������passwordת��PBE�㷨����Կ
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
	 * ���ݡ��Ρ�+����ʵ�ֶ����ݵļ���
	 * @param unencrypted_data
	 * @param password
	 * @param salt
	 * @return
	 */
	public static byte[] jdkPBEEncryption(byte[] unencrypted_data, String password, byte[] salt){
		PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);//ע��ָ����������
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
	 * ���ݡ��Ρ�+����ʵ�ֶ����ݵĽ���
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
	
	//����
	public static void main(String[] args) {
		byte[] salt = PBE.salt();
		String password = "123456";
		String origin_data = "hello ���";
		byte[] encrypted_data = PBE.jdkPBEEncryption(origin_data.getBytes(), password, salt);
		System.out.println(Hex.toHexString(encrypted_data));
		byte[] unencrypted_data = PBE.jdkPBEDencryption(encrypted_data, password, salt);
		System.out.println(new String(unencrypted_data));
	}
	
}

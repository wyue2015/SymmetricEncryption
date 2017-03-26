package des;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.bouncycastle.util.encoders.Hex;


/**
 * ����JDK�ṩ�ķ�ʽ��ʵ��DES�ӽ���
 * @author wy
 *
 */
public class DES {
	
	/**
	 * ������Կ��������ɣ�--��ʵ��ʹ���У���Կ����������ɣ�����˫��Լ������Կ
	 * @return
	 */
	public static byte[] generKey(){
		//1.������Կ��������ɣ�--��ʵ��ʹ���У���Կ����������ɣ�����˫��Լ������Կ
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance("DES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}//��ȡ��Կ������
		keyGenerator.init(56);//��ʼ����Կ��������ָ����Կ��bit����
		SecretKey secretKkey = keyGenerator.generateKey();//������Կ
		byte[] bytesKey = secretKkey.getEncoded();//��ȡ��Կ�Ķ�������ʽ
		
		return bytesKey;
	}
	
	/**
	 * ת����Կ����������ɵ���Կת��DES����Կ
	 * @param bytesKey
	 * @return
	 */
	public static Key convertKey(byte[] bytesKey){
		
		//2.ת����Կ--����������ɵ���Կ���õ�DES�㷨����Կ
		DESKeySpec desKeySpec=null;
		Key convertSecretKey=null;
		try {
			desKeySpec = new DESKeySpec(bytesKey);//ʵ����DES��Կ���˴�Ҳ���Բ�ʹ�����ɵ������Կ������ָ��˫���Ͽɵ���Կ��8bytes��
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");//��ȡDES��Կ����
			convertSecretKey = factory.generateSecret(desKeySpec);//����DES�㷨����Կ
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return convertSecretKey;
	}
	
	public static byte[] jdkDESEncode(String str,byte[] bytesKey){
		byte[] result = null;
		try {
			//3.��������
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");//���üӽ����㷨/����ģʽ/��䷽ʽ
			cipher.init(Cipher.ENCRYPT_MODE, DES.convertKey(bytesKey));
			result = cipher.doFinal(str.getBytes());//��str���ܣ��õ����ܺ�Ķ���result
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return result;
	}
	
	public static String jdkDESDecode(byte[] encrypted_data,byte[] bytesKey){
		
		//4.����
		Cipher cipher=null;
		byte[] result2=null;
		try {
			cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, DES.convertKey(bytesKey));
			result2 = cipher.doFinal(encrypted_data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new String(result2);
		
	}
	
	public static void main(String[] args) {
		byte[] key = DES.generKey();
		byte[] encrption = DES.jdkDESEncode("as���",key);
		System.out.println(Hex.toHexString(encrption));
		System.out.println(DES.jdkDESDecode(encrption, key));
	}
	
}

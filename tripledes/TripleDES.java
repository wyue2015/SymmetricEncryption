package tripledes;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.bouncycastle.util.encoders.Hex;



/**
 * ����JDK�Ļ���JDK�ṩ�ķ�ʽ��ʵ��3DES�ӽ���
 * @author wy
 *
 */
public class TripleDES {

	/**
	 * ������Կ��������ɣ�--��ʵ��ʹ���У���Կ����������ɣ�����˫��Լ������Կ
	 * @return
	 */
	public static byte[] generKey(){
		//1.������Կ��������ɣ�--��ʵ��ʹ���У���Կ����������ɣ�����˫��Լ������Կ
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance("DESede");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}//��ȡ��Կ������
		keyGenerator.init(168);//��ʼ����Կ��������ָ����Կ��bit����
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
		DESedeKeySpec desKeySpec=null;
		Key convertSecretKey=null;
		try {
			desKeySpec = new DESedeKeySpec(bytesKey);//ʵ����DESede��Կ���˴�Ҳ���Բ�ʹ�����ɵ������Կ������ָ��˫���Ͽɵ���Կ(�����㷨ȷ����������Կ)
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");//��ȡDES��Կ����
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
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");//���üӽ����㷨/����ģʽ/��䷽ʽ
			cipher.init(Cipher.ENCRYPT_MODE, TripleDES.convertKey(bytesKey));
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
			cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, TripleDES.convertKey(bytesKey));
			result2 = cipher.doFinal(encrypted_data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new String(result2);
		
	}
	
	public static void main(String[] args) {
		byte[] key = TripleDES.generKey();
		System.out.println(Hex.toHexString(key));
		byte[] encrption = TripleDES.jdkDESEncode("as���", key);//������ʹ��Base64�Լ��ܺ�����ݱ��봦��
		System.out.println(Hex.toHexString(encrption));
		System.out.println(TripleDES.jdkDESDecode(encrption, key));
	}
}

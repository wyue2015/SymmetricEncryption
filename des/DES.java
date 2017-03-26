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
 * 基于JDK提供的方式，实现DES加解密
 * @author wy
 *
 */
public class DES {
	
	/**
	 * 生成密钥（随机生成）--在实际使用中，密钥不会随机生成，都是双方约定好密钥
	 * @return
	 */
	public static byte[] generKey(){
		//1.生成密钥（随机生成）--在实际使用中，密钥不会随机生成，都是双方约定好密钥
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance("DES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}//获取密钥生成器
		keyGenerator.init(56);//初始化密钥生成器，指定密钥的bit长度
		SecretKey secretKkey = keyGenerator.generateKey();//生成密钥
		byte[] bytesKey = secretKkey.getEncoded();//获取密钥的二进制形式
		
		return bytesKey;
	}
	
	/**
	 * 转换密钥，将随机生成的密钥转成DES的密钥
	 * @param bytesKey
	 * @return
	 */
	public static Key convertKey(byte[] bytesKey){
		
		//2.转换密钥--根据随机生成的密钥，得到DES算法的密钥
		DESKeySpec desKeySpec=null;
		Key convertSecretKey=null;
		try {
			desKeySpec = new DESKeySpec(bytesKey);//实例化DES密钥，此处也可以不使用生成的随机密钥，可以指定双方认可的密钥（8bytes）
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");//获取DES密钥工厂
			convertSecretKey = factory.generateSecret(desKeySpec);//生成DES算法的密钥
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return convertSecretKey;
	}
	
	public static byte[] jdkDESEncode(String str,byte[] bytesKey){
		byte[] result = null;
		try {
			//3.加密数据
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");//设置加解密算法/工作模式/填充方式
			cipher.init(Cipher.ENCRYPT_MODE, DES.convertKey(bytesKey));
			result = cipher.doFinal(str.getBytes());//对str加密，得到加密后的对象result
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return result;
	}
	
	public static String jdkDESDecode(byte[] encrypted_data,byte[] bytesKey){
		
		//4.解密
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
		byte[] encrption = DES.jdkDESEncode("as你好",key);
		System.out.println(Hex.toHexString(encrption));
		System.out.println(DES.jdkDESDecode(encrption, key));
	}
	
}

package cbb.mystyle.com.myapp.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Utils {
	public static String digesPassword(String password){
		try {
			//创建加密
			MessageDigest digest = MessageDigest.getInstance("MD5");
//			digest.update();//把文件的一部分预加密
//			digest.digest();//把预加密的文件 统一加密
			
			//把传过来的字符串明文 变成字节明文
			byte[] b = digest.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			//循环遍历字节明文
			for (int i = 0; i < b.length; i++) {
				//把负数提升成正数
				int result = b[i]&0xff;
				//转换成16进制
				String hexString = Integer.toHexString(result);
				//循环加入缓冲区
				if (hexString.length()<2) {
					sb.append("0");
				}
				sb.append(hexString);
			}
			String string  = sb.toString();
			return string;
		
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}

package com.sitech.sessionshare.redis3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * <p>Title: 		对象序列化工具类</p>
 * <p>Description: 	对象序列化成二进制流 ， 二进制流反序列化成对象</p>
 * <p>Copyright: 	Copyright (c) 2017</p>
 * <p>Company: 		SI-TECH </p>
 * @date 2017-4-12 上午11:31:35 
 * @author 		gt
 * @version 		1.0
 */
public class SerializeUtils {
	
	

	/**
	 * <p>Description: 	二进制流反序列化成对象</p>
	 * @param bytes
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {
		Object result = null;

		if (isEmpty(bytes)) {
			return null;
		}
		try {
			ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(
						byteStream);
				try {
					result = objectInputStream.readObject();
				} catch (ClassNotFoundException ex) {
					throw new Exception("Failed to deserialize object type", ex);
				}
			} catch (Throwable ex) {
				throw new Exception("Failed to deserialize", ex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	/**
	 * <p>Description: 对象序列化成二进制流</p>
	 * @param bytes
	 * @return
	 */
	public static byte[] serialize(Object object) {
		byte[] result = null;

		if (object == null)
			return new byte[0];
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
			try {
				if (!(object instanceof Serializable)) {
					throw new IllegalArgumentException(
							SerializeUtils.class.getSimpleName()
									+ " requires a Serializable payload "
									+ "but received an object of type ["
									+ object.getClass().getName() + "]");
				}
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
						byteStream);
				objectOutputStream.writeObject(object);
				objectOutputStream.flush();
				result = byteStream.toByteArray();
			} catch (Throwable ex) {
				throw new Exception("Failed to serialize", ex);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	public static boolean isEmpty(byte[] data) {
		return (data == null) || (data.length == 0);
	}
}

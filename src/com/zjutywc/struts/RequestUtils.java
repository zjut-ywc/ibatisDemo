package com.zjutywc.struts;



import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RequestUtils {

	public static Class applicationClass(String className)
			throws ClassNotFoundException {

		ClassLoader classLoader = getClassLoader();
		return (classLoader.loadClass(className));
	}
	/*
	 * ʵ������Ķ���
	 */
	public static Object applicationInstance(String className)
			throws ClassNotFoundException, IllegalAccessException,
			InstantiationException {
         try {
        	 return (applicationClass(className).newInstance());
		  } catch (Exception e) {
			 return null;
		}
	}
	/*
	 * ȡ��ָ��·���µ��ļ���
	 */
	public static InputStream getResourceAsStream(String path)
			throws IOException {

		ClassLoader loader = getClassLoader();

		InputStream in = null;
		if (loader != null)
			in = loader.getResourceAsStream(path);
		if (in == null)
			in = ClassLoader.getSystemResourceAsStream(path);
		if (in == null)
			throw new IOException("Could not find resource " + path);
		return in;
	}
	/*
	 * ȡ��*.properties�ļ�����Դ
	 */
	public static Properties getResourceAsProperties(String path)
			throws IOException {
		Properties props = new Properties();
		InputStream in = null;
		String propfile = path;
		in = getResourceAsStream(propfile);
		props.load(in);
		in.close();
		return props;
	}

	private static ClassLoader getClassLoader() {

		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		if (classLoader == null) {
			classLoader = RequestUtils.class.getClassLoader();
		}
		return classLoader;
	}
}

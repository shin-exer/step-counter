package jp.gr.java_conf.shinexer.stepcounter.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeanUtil {
	public static String toJSONString(List<?> beanList) {
		StringBuilder buf = new StringBuilder(1024);

		if (beanList.isEmpty()) {
			return buf.toString();
		}

		Class<?> beanClass = beanList.get(0).getClass();
		for (Object bean : beanList) {
			StringBuilder item = new StringBuilder(256);

			Arrays.asList(beanClass.getDeclaredFields()).forEach(field -> {
				try {
					String fieldName = field.getName();
					PropertyDescriptor pd = new PropertyDescriptor(fieldName, beanClass);
					Method getter = pd.getReadMethod();
					Object val = getter.invoke(bean, (Object[]) null);
					if (val instanceof Integer) {
						item.append(", \"").append(fieldName).append("\": ").append(val);
					} else {
						item.append(", \"").append(fieldName).append("\": \"").append(val).append('"');
					}

				} catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
					e.printStackTrace();
				}
			});

			item.replace(0, 1, "{");
			item.append("}");
			buf.append(',').append(item);
		}
		buf.replace(0, 1, "[").append(']');
		return buf.toString();
	}

	public static String toCSVString(List<?> beanList) {
		StringBuilder buf = new StringBuilder(1024);

		if (beanList.isEmpty()) {
			return buf.toString();
		}

		Class<?> beanClass = beanList.get(0).getClass();

		Field[] fields = beanClass.getDeclaredFields();
		List<String> fieldNames = new ArrayList<String>(fields.length);
		Arrays.asList(fields).forEach(field -> {
			String fieldName = field.getName();
			buf.append(',').append(fieldName);
			fieldNames.add(fieldName);
		});

		buf.append(System.lineSeparator());
		for (Object bean : beanList) {
			StringBuilder line = new StringBuilder(256);
			fieldNames.stream().forEach(fieldName -> {
				try {
					PropertyDescriptor pd = new PropertyDescriptor(fieldName, beanClass);
					Method getter = pd.getReadMethod();
					Object val = getter.invoke(bean, (Object[]) null);
					line.append(',').append(val == null ? "" : val);
				} catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
					e.printStackTrace();
				}
			});
			buf.append(line.substring(1)).append(System.lineSeparator());
		}

		return buf.substring(1);
	}
}

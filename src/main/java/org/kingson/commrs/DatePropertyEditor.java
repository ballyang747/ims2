package org.kingson.commrs;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// PropertyEditor不是单例，也不是线程安全的。
// 所以需要该对象的时候，总是new一个的！
public class DatePropertyEditor extends PropertyEditorSupport {

	// 简单日期格式化工具
	private SimpleDateFormat sdf;

	public DatePropertyEditor(String pattern) {
		super();
		this.sdf = new SimpleDateFormat(pattern);
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			// 把字符串转换为自己需要的格式的对象
			Date d = sdf.parse(text);
			// 把转换得到的对象，设置给目标
			super.setValue(d);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
}

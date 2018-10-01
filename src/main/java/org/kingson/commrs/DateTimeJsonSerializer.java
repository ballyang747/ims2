package org.kingson.commrs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

// 把Date转换为yyyy-MM-dd HH:mm:ss格式字符串
// 在需要使用该转换器的地方，直接加上@JsonSerialize(using = DateTimeJsonSerializer.class)
public class DateTimeJsonSerializer extends JsonSerializer<Date> {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		String text = sdf.format(value);
		gen.writeString(text);
	}
}

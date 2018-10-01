package org.kingson.Ims.workflow.vo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProcessImage {

	private String name;
	private byte[] data;

	public ProcessImage(String name, InputStream in) {
		this.name = name;

		// 把数据输出到一个字节数组的流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			byte[] buf = new byte[1024];
			for (int len = in.read(buf); //
					len != -1; //
					len = in.read(buf)//
					) {
				out.write(buf, 0, len);
			}

			// 获取字节数组输出流里面的所有字节
			this.data = out.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("无法把输入流转换为字节数组，因为:" + e.getMessage(), e);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}

package com.potter;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class HEncoder implements ProtocolEncoder {
	private final Charset charset;

	public HEncoder(Charset charset) {
		this.charset = charset;
	}

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		CharsetEncoder ce = charset.newEncoder();
		// PlayerAccount_Entity paEntity = (PlayerAccount_Entity) message;
		// IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
		// buffer.putInt(paEntity.getId());
		// buffer.putInt(paEntity.getSex());
		// buffer.putString(paEntity.getName(), 10, ce);
		// buffer.putString(paEntity.getEmailAdress(), ce);
		// buffer.flip();
		// out.write(buffer);
		//
		HData data = (HData) message;
		int length = data.getLength(charset);
		IoBuffer buffer = IoBuffer.allocate(length).setAutoExpand(false);
		buffer.putInt(length);
		buffer.putShort(data.getType());
		buffer.putString(data.getData(), ce);
		buffer.flip();
		out.write(buffer);
	}

	@Override
	public void dispose(IoSession session) throws Exception {
	}
}

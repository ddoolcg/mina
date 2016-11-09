package com.potter;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class HDecoder extends CumulativeProtocolDecoder {
	private final Charset charset;

	public HDecoder(Charset charset) {
		this.charset = charset;
	}

	@Override
	protected boolean doDecode(IoSession arg0, IoBuffer message,
			ProtocolDecoderOutput arg2) throws Exception {
		CharsetDecoder cd = charset.newDecoder();
		// int id = 0;
		// if (message.remaining() >= 4) {
		// id = message.getInt();
		// }
		// int sex = 0;
		// if (message.remaining() >= 8) {
		// sex = message.getInt();
		// }
		// String name = message.getString(10, cd);
		// String emailAdress = message.getString(cd);
		// PlayerAccount_Entity paEntity = new PlayerAccount_Entity(name, id,
		// emailAdress, sex);
		//
		// arg2.write(paEntity);
		//
		int length = message.getInt();
		short type = message.getShort();
		String data = message.getString(length - 4 - 2, cd);
		HData hData = new HData(type, data);
		arg2.write(hData);
		return true;
	}
}

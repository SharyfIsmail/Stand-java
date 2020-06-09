package stand.semikron.sdo;

import stand.util.LilEndByteParser;

public class ActualUq extends ServiceDataObject<Float> {

	private float value;

	public ActualUq() {
		super(8325, (byte) 0);
		setCommand((byte) 64);
	}

	@Override
	public Float getValue() {
		return value;
	}

	@Override
	public void setValue(byte[] data) {
		value = LilEndByteParser.byteArrayToFloat(data);
	}

}

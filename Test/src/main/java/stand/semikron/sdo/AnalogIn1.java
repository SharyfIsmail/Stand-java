package stand.semikron.sdo;

import stand.util.LilEndByteParser;

public class AnalogIn1 extends ServiceDataObject<Float> {

	private float value;

	public AnalogIn1() {
		super(8304, (byte) 0);
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

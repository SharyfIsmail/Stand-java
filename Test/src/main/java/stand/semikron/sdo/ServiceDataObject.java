package stand.semikron.sdo;

import stand.util.LilEndByteParser;

public abstract class ServiceDataObject<T> implements SDO<T> {

	private byte command;
	private int index;
	private byte subindex;

	public ServiceDataObject(int index, byte subindex) {
		super();
		this.index = index;
		this.subindex = subindex;
	}

	@Override
	public byte getCommand() {
		return command;
	}

	@Override
	public int getIndex() {
		return index;
	}

	@Override
	public byte getSubindex() {
		return subindex;
	}

	@Override
	public void setCommand(byte command) {
		this.command = command;
	}

	@Override
	public byte[] request() {
		byte[] b2 = LilEndByteParser.intToUnsignedInt(index);
		byte[] data = { command, b2[0], b2[1], subindex };
		return data;
	}

}

package stand.pcm.tx;

import stand.can.CanCdr;
import stand.can.candata.DataFromCan;
import stand.util.BigEndByteParser;

public class CurrentVoltageSensor extends CanCdr implements DataFromCan {

	private int current;
	private int voltage;
	private String error; // 65535(current) - Current_Voltage_Sensor not connected

	private byte contactorsManagement; // 0 - Contractor OFF
										// 1 - Contractor ON
										// 3 - without changes

	private byte powerDriverStatus; // 0 - Error
									// 1 - Normal operation

	@Override
	public void parseDataFromCan(byte[] data) {
		if (data.length == 8) {

			byte[] b2 = new byte[2];
			System.arraycopy(data, 0, b2, 0, b2.length);
			int current = BigEndByteParser.unsignedIntToInt(b2);
			if (current == 65535) {
				error = "Current_Sensor not connected";
			} else {
				this.current = current;
				error = null;
			}

			System.arraycopy(data, 2, b2, 0, b2.length);
			voltage = BigEndByteParser.unsignedIntToInt(b2);
			contactorsManagement = data[4];
			powerDriverStatus = data[5];
		} else {
			throw new IndexOutOfBoundsException("длинна data не равна 8 байтам. data.length=" + data.length);
		}
	}

	/**
	 * Get value in A
	 */
	public float getCurrent() {
		return (float) ((current - 5000) * 0.1);
	}

	/**
	 * Get value in V
	 */
	public float getVoltage() {
		return (float) (voltage * 0.1);
	}

	/**
	 * Get "Contractor OFF" if value 0 "Contractor ON" if value 1 "without changes"
	 * if value 3 "wrong value" if value is not 0,1,3
	 */
	public String getContactorsManagement() {
		if (contactorsManagement == 0) {
			return "contactor OFF";
		} else if (contactorsManagement == 1) {
			return "contactor ON";
		} else if (contactorsManagement == 3) {
			return "without changes";
		} else {
			return "wrong value: " + contactorsManagement;
		}
	}

	/**
	 * Get "Normal operation" if value 1 "Error" if value 0 "wrong value" if value
	 * is not 0,1
	 */
	public String getPowerDriverStatus() {
		if (powerDriverStatus == 1) {
			return "Normal operation";
		} else if (powerDriverStatus == 0) {
			return "Error";
		} else {
			return "wrong value: " + powerDriverStatus;
		}
	}

	public String getError() {
		return error;
	}
}

package stand.pcm.tx;

import stand.can.CanCdr;
import stand.can.candata.DataFromCan;
import stand.util.BigEndByteParser;

public class TurnoverSensor extends CanCdr implements DataFromCan {

	private String car_State;
	private int turnover;
	private int Torque;
	private String error;

	@Override
	public void parseDataFromCan(byte[] data) {
		if (data.length == 8) {
			if (data[0] == 0) {
				error = "Car State: Error";
			}
			if (data[0] == 1) {
				car_State = "Moving Forward";
			}
			if (data[0] == 2) {
				car_State = "Moving Backward";
			}
			if (data[0] == 3) {
				car_State = "Not Moving";
			}

			byte[] b2 = new byte[2];
			System.arraycopy(data, 1, b2, 0, b2.length);
			int turnover = BigEndByteParser.unsignedIntToInt(b2);
			
			if (turnover == 65535) {
				error = "Turnover_Sensor not connected";
			} else {
				this.turnover = turnover;
				error = null;
			}
			byte[] b3 = new byte[2];
			System.arraycopy(data, 3, b3, 0, b3.length);
			int Torque = BigEndByteParser.unsignedIntToInt(b3);
			this.Torque = Torque;
		} else {
			throw new IndexOutOfBoundsException("длинна data не равна 8 байтам. data.length=" + data.length);
		}
	}

	public String getCarState() {
		return car_State;
	}

	/**
	 * TO/min
	 */
	public int getTurnover() {
		return turnover;
	}

	public String getError() {
		return error;
	}
	public int getTorque()
	{
		return Torque;
	}
}

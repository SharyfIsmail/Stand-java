package stand.pcm.tx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import stand.can.CanCdr;
import stand.can.candata.DataFromCan;
import stand.t_45.data.DataFromT_45;
import stand.util.BigEndByteParser;

public class TurnoverSensor extends CanCdr implements DataFromCan, DataFromT_45 {

	private String car_State;
	private int turnover;
	private int Torque;
	private float turnOverT_45;
	private float TorqueT_45;
	private float tempT_45;
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
		return turnover * 60;
	}

	public String getError() {
		return error;
	}
	public int getTorque()
	{
		return (Torque - 10000) / 10;
	}

	public float getTurnoverT_45()
	{
		return turnOverT_45;
	}
	public float getTorqueT_45()
	{
		return	TorqueT_45;
	}
	public float getTempT_45()
	{
		return tempT_45;
	}
	
	@Override
	public void parseDataFromT_45(byte[] data) 
	{
		//System.out.println(ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getLong(0));
		TorqueT_45 = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getFloat(8);
		tempT_45 = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getFloat(12);
		turnOverT_45 = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getFloat(16);
		//System.out.println("Torque: "+ TorqueT_45 +" , "+"Turnover: " +turnOverT_45);
	}
}

package stand.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import com.sun.jna.Pointer;

import stand.t_45.SpecialParametrs;
import stand.t_45.T_45;

public class WinUsbDataReceiver implements DataReceiver 
{
	private Pointer pointer = null;
	private SpecialParametrs specialParametrs = new SpecialParametrs();
	private ByteBuffer bytebuffer = ByteBuffer.allocate(24);
	private IntBuffer intBuffer = IntBuffer.allocate(24);
	 public WinUsbDataReceiver() 
	 {
		 super();
		 specialParametrs.AveragingFactor = 1;
		 specialParametrs.SpeedMeasurementPeriod = 100;
	 }

	@Override
	public byte[] receive() throws IOException 
	{
		int status = T_45.INSTANCE.DecoderReadComplex(pointer, bytebuffer, intBuffer);
		if(status != 0)
			throw new T_45Exception();
		byte []arr = new byte[bytebuffer.remaining()];
		bytebuffer.get(arr);
		return arr;
	}

	@Override
	public void close() throws IOException
	{
		T_45.INSTANCE.DecoderClose(pointer);
		pointer = null;
	}
	public void createCommunication() throws NullPointerException
	{
		this.pointer = T_45.INSTANCE.DecoderCreate(1, (byte)T_45.USB_DECODER_T45, null , null,specialParametrs);
		if(pointer == null)
		{
			throw new NullPointerException();
		}
	}
	public int openCommunication() throws T_45Exception
	{
		int isOpen = 0;
		isOpen = T_45.INSTANCE.DecoderOpen(pointer, (byte)0);
		if(isOpen != 0)
			throw new T_45Exception("Check the connection, decoder is not connected");
		return isOpen; 
	}

	public Pointer getPointer() {
		return pointer;
	}

	public void setPointer(Pointer pointer) {
		this.pointer = pointer;
	}
	
	
}

package stand.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.atomic.AtomicReference;

import com.sun.jna.Pointer;

import stand.t_45.SpecialParametrs;
import stand.t_45.T_45;

public class WinUsbDataReceiver implements DataReceiver 
{
	private AtomicReference <Pointer> pointerTest = new AtomicReference<>(null);
	
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
		int status = T_45.INSTANCE.DecoderReadComplex(pointerTest.get(), bytebuffer, intBuffer);
		if(status != 0)
		{
			throw new T_45Exception("Decoder may not be connected!!!");
		}
		byte []arr = bytebuffer.array();
		return arr;
	}

	@Override
	public void close() 
	{
		if(pointerTest.get() != null)
		{
			T_45.INSTANCE.DecoderClose(pointerTest.get());
			pointerTest.set(null);
		}
	}
	public void createCommunication()
	{
		pointerTest.set( T_45.INSTANCE.DecoderCreate(1, (byte)T_45.USB_DECODER_T45, null , null,specialParametrs));
	}
	public void openCommunication() throws T_45Exception
	{
		int isOpen = T_45.INSTANCE.DecoderOpen(pointerTest.get(), (byte)0);
		if(isOpen != 0)
		{
			throw new T_45Exception("Decoder is not connected, check the connection");
		}
	}
	public AtomicReference<Pointer>  getPointerTest()
	{
		return pointerTest;
	}
}

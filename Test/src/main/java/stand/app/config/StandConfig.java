package stand.app.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import stand.app.module.battery.model.BatteryDataMonitor;
import stand.app.module.pcm.PcmDataMonitor;
import stand.app.module.pcm.model.CurrentVoltageSensorModel;
import stand.app.module.pcm.model.TurnoverSensorModel;
import stand.app.module.semikron.model.SemikronDataMonitor;
import stand.app.thread.ReceiveThread;
import stand.app.thread.SensorComunicationThread;
import stand.battery.Battery;
import stand.battery.BatteryService;
import stand.can.candata.DataFromCanModel;
import stand.charger.Charger;
import stand.charger.ChargerService;
import stand.ethernet.EthCanCdr;
import stand.ethernet.EthernetCan;
import stand.semikron.Semikron;
import stand.semikron.SemikronService;
import stand.util.DataSender;
import stand.util.TcpDataSender;
import stand.util.UdpDataReceiver;
import stand.util.WinUsbDataReceiver;

@Configuration
@PropertySource(value = "classpath:application.properties")
@ComponentScan("stand.app")
public class StandConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Value("${server.port}")
	public int SEND_PORT ;// = 32000;

	@Value("${server.ip}")
	public String INET_ADDRESS= "169.254.123.123";// = "169.254.123.123";

	@Value("${receive.port}")
	private int RECEIVE_PORT;// = 31000;

	@Bean
	public DataSender dataSender() {
		return new TcpDataSender(SEND_PORT, INET_ADDRESS);
	}

	@Bean
	public EthernetCan ethernetCan() {
		return new EthCanCdr();
	}

	@Bean
	public SemikronService service() {
		return new Semikron(ethernetCan(), dataSender());
	}

	@Bean
	public SemikronDataMonitor semikronDataMonitor() {
		return new SemikronDataMonitor();
	}

	@Bean
	public BatteryDataMonitor batteryDataMonitor() {
		return new BatteryDataMonitor();
	}

	@Bean
	public BatteryService battery() {
		return new Battery(ethernetCan(), dataSender());
	}

	@Bean
	public ChargerService charger() {
		return new Charger(ethernetCan(), dataSender());
	}

	@Bean
	public ConcurrentLinkedQueue<byte[]> ethCanQueue() {
		return new ConcurrentLinkedQueue<>();
	}
	@Bean
	public CurrentVoltageSensorModel currentVoltageSensorModel()
	{
		return new CurrentVoltageSensorModel();
	}
	@Bean
	public TurnoverSensorModel turnoverSensorModel()
	{
		return new TurnoverSensorModel();
	}
	@Bean
	public PcmDataMonitor pcmDataMonitor()
	{
		return new PcmDataMonitor(currentVoltageSensorModel(), turnoverSensorModel());
	}

	@Autowired
	@Bean
	public Map<Integer, DataFromCanModel> canId(BatteryDataMonitor batteryDataMonitor,
			SemikronDataMonitor semikronDataMonitor, PcmDataMonitor pcmDataMonitor) {
		Map<Integer, DataFromCanModel> canId = new HashMap<>();
		canId.put(1914, semikronDataMonitor.getNodeGuardingSlaveModel());
		canId.put(506, semikronDataMonitor.getTxPDO1());
		canId.put(762, semikronDataMonitor.getTxPDO2());
		canId.put(1018, semikronDataMonitor.getTxPDO3());
		canId.put(1274, semikronDataMonitor.getTxPDO4());
		canId.put(442, semikronDataMonitor.getTxPDO5());
		canId.put(1530, semikronDataMonitor.getTxSDO());

		canId.put(486486368, batteryDataMonitor.getActiveFaultDataModel());
		canId.put(486486880, batteryDataMonitor.getData00Model());
		canId.put(486487136, batteryDataMonitor.getData01Model());
		canId.put(486487392, batteryDataMonitor.getData02Model());
		//canId.put(201981789, pcmDataMonitor.getTurnoverSensorModel());

		return canId;
	}

	@Bean
	public ReceiveThread receiveThread() {
		ReceiveThread receiveThread = new ReceiveThread();
		receiveThread.setUnitIdMapper(canId(batteryDataMonitor(), semikronDataMonitor(),pcmDataMonitor()));
		receiveThread.setReceiveData(new UdpDataReceiver(RECEIVE_PORT, EthCanCdr.ETH_CAN_SIZE));
		return receiveThread;
	}
	@Bean
	public SensorComunicationThread getSensorComunicationThread()
	{
		SensorComunicationThread sensorComunicationThread = new SensorComunicationThread();
		sensorComunicationThread.setTurnOverSensorModel(pcmDataMonitor());
		sensorComunicationThread.setWinUsbDataReceiver(new WinUsbDataReceiver());
		return sensorComunicationThread;
		
	}
}

package stand.battery;

import java.io.IOException;

public interface BatteryService {
	void turnOn() throws IOException;

	void turnOff() throws IOException;
}

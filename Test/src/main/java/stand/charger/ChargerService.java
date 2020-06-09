package stand.charger;

import java.io.IOException;

public interface ChargerService {
	void turnOn() throws IOException;

	void turnOff() throws IOException;
}

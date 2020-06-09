package stand.ethernet;

public interface Ethernet {

	byte[] collectEthernetPacket();

	void parseEthernetPacket(byte[] ethernetPacket);
}

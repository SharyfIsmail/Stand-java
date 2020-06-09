package stand.ethernet;

import stand.can.Can;

public interface EthernetCan extends Ethernet {
	/**
	 * remove Can from cans
	 */
	public void removeCan(Can removingCan);

	/**
	 * if EthernetCan is not contains addingCAN the method inserts new Can and
	 * return true else return false
	 */
	public boolean addCan(Can addingCan);

	/**
	 * return array of Can
	 */
	public Can[] getAllCan();

	/**
	 * 
	 * @return count of Can (max : 30)
	 */
	public int getNz();
}

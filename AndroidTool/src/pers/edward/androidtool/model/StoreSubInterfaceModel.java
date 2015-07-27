package pers.edward.androidtool.model;

import java.util.List;

public class StoreSubInterfaceModel {
	private String interfaceUrl;
	private String interfaceTitle;
	private List<NetworkUrlModel> networkUrlList;

	public String getInterfaceUrl() {
		return interfaceUrl;
	}

	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}

	public String getInterfaceTitle() {
		return interfaceTitle;
	}

	public void setInterfaceTitle(String interfaceTitle) {
		this.interfaceTitle = interfaceTitle;
	}

	public List<NetworkUrlModel> getNetworkUrlList() {
		return networkUrlList;
	}

	public void setNetworkUrlList(List<NetworkUrlModel> networkUrlList) {
		this.networkUrlList = networkUrlList;
	}

}

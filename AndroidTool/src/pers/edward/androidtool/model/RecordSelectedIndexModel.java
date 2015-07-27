package pers.edward.androidtool.model;

import java.util.List;

/**
 * 此Model用来标记用户已经选择的复选框（打钩）
 * 
 * @author Edward
 * 
 */
public class RecordSelectedIndexModel {
	private int index;

	private List<Integer> subListIndex;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<Integer> getSubListIndex() {
		return subListIndex;
	}

	public void setSubListIndex(List<Integer> subListIndex) {
		this.subListIndex = subListIndex;
	}

}

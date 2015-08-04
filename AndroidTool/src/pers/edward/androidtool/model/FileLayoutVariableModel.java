package pers.edward.androidtool.model;

import java.util.List;

/**
 * 此model用来存放变量
 * 
 * @author Edward
 * 
 */
public class FileLayoutVariableModel
{
	private String fileName;
	private List<VariableDataModel> variableList;

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public List<VariableDataModel> getVariableList()
	{
		return variableList;
	}

	public void setVariableList(List<VariableDataModel> variableList)
	{
		this.variableList = variableList;
	}

}

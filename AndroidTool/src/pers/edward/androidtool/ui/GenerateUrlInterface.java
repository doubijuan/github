package pers.edward.androidtool.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pers.edward.androidtool.function.getUrlInterface;
import pers.edward.androidtool.model.NetworkUrlModel;
import pers.edward.androidtool.model.RecordSelectedIndexModel;
import pers.edward.androidtool.model.StoreSubInterfaceModel;
import pers.edward.androidtool.tool.CommonMethod;

/**
 * 生成网络请求地址页面
 * 
 * @author Edward
 * 
 */
public class GenerateUrlInterface
{
	private JPanel jpanel;
	private JLabel label13, label14, label15;
	private JTextField field7, field8, field9;
	private JButton button6;
	private CommonMethod common;
	private String codingType;
	private static GenerateUrlInterface instance = null;

	public static GenerateUrlInterface getInstanceInterface(JPanel jpanel, Container container, String codingType)
	{

		if (instance == null)
		{
			instance = new GenerateUrlInterface(jpanel, container, codingType);
		}

		return instance;
	}

	private GenerateUrlInterface(JPanel jpanel, Container container, String codingType)
	{
		this.jpanel = jpanel;
		this.codingType = codingType;
		common = new CommonMethod(container);
		draw();
	}

	public void draw()
	{
		label13 = new JLabel("输入Url接口地址：");
		label13.setBounds(10, 10, 150, 30);
		jpanel.add(label13);

		label14 = new JLabel("存储Url接口文件：");
		label14.setBounds(10, 50, 150, 30);
		jpanel.add(label14);

		label15 = new JLabel("替换IP变量：");
		label15.setBounds(10, 90, 150, 30);
		jpanel.add(label15);

		field7 = new JTextField("http://120.25.218.242:9012/");
		field7.setBounds(170, 10, 600, 30);
		jpanel.add(field7);

		field8 = new JTextField("C:\\MyWorkspace\\JAVASE\\github\\AndroidTool\\src\\pers\\edward\\androidtool\\function\\TestUrl.java");
		field8.setBounds(170, 50, 600, 30);
		jpanel.add(field8);

		field9 = new JTextField("INTERNET_URL");
		field9.setBounds(170, 90, 200, 30);
		jpanel.add(field9);

		button6 = new JButton("生成Url接口");
		button6.setBounds(10, 250, 150, 30);
		jpanel.add(button6);

		button6.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				try
				{
					SelectUrlInterface.getInstance(field7.getText().toString(), field8.getText().toString(), field9.getText().toString(), codingType);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

}

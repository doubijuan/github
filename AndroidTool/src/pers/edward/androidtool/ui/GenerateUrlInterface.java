package pers.edward.androidtool.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

	public JTextField getField7()
	{
		return field7;
	}

	public void setField7(JTextField field7)
	{
		this.field7 = field7;
	}

	public JTextField getField8()
	{
		return field8;
	}

	public void setField8(JTextField field8)
	{
		this.field8 = field8;
	}

	public JTextField getField9()
	{
		return field9;
	}

	public void setField9(JTextField field9)
	{
		this.field9 = field9;
	}

	public GenerateUrlInterface(JPanel jpanel, Container container, String codingType)
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
		// http://120.25.218.242:9012/
		field7 = new JTextField("");
		field7.setBounds(170, 10, 600, 30);
		jpanel.add(field7);
		// C:\\MyWorkspace\\JAVASE\\github\\AndroidTool\\src\\pers\\edward\\androidtool\\function\\TestUrl.java
		// C:\\MyWorkspace\\Android\\YunYiPurchase\\YunYiGou\\src\\com\\zhanyun\\yunyigou\\network\\ConnectUrl.java
		field8 = new JTextField("");
		field8.setBounds(170, 50, 600, 30);
		jpanel.add(field8);
		// INTERFACE_URL
		field9 = new JTextField("");
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
					new SelectUrlInterface(field7.getText().toString(), field8.getText().toString(), field9.getText().toString(), codingType);
					// SelectUrlInterface.getInstance(field7.getText().toString(),
					// field8.getText().toString(), field9.getText().toString(),
					// codingType);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

}

package pers.edward.androidtool.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pers.edward.androidtool.function.GetWidgetByXmlParser;
import pers.edward.androidtool.model.FileLayoutVariableModel;
import pers.edward.androidtool.model.VariableDataModel;
import pers.edward.androidtool.tool.CommonMethod;

/**
 * 生成控件界面
 * 
 * @author Edward
 * 
 */
public class GenerateWidgetInterface
{
	private JPanel jpanel;
	private Container container;
	private JLabel label4, label5, label6, label7, label8;
	private JTextField field4;
	private JButton button3;
	private String activityPathStrStr = "", xmlPathStr = "";
	private String codingType;
	private CommonMethod common;
	private Main main;

	public JLabel getLabel6()
	{
		return label6;
	}

	public void setLabel6(JLabel label6)
	{
		this.label6 = label6;
	}

	public JLabel getLabel7()
	{
		return label7;
	}

	public void setLabel7(JLabel label7)
	{
		this.label7 = label7;
	}

	public String getActivityPathStrStr()
	{
		return activityPathStrStr;
	}

	public void setActivityPathStrStr(String activityPathStrStr)
	{
		this.activityPathStrStr = activityPathStrStr;
	}

	public String getXmlPathStr()
	{
		return xmlPathStr;
	}

	public void setXmlPathStr(String xmlPathStr)
	{
		this.xmlPathStr = xmlPathStr;
	}

	public GenerateWidgetInterface(JPanel jpanel, Container container, Main main)
	{
		this.main = main;
		this.jpanel = jpanel;
		this.container = container;
		common = new CommonMethod(container);
		draw();
	}

	public void draw()
	{
		label4 = new JLabel("当前Activity文件路径：");
		label4.setBounds(10, 0, 200, 50);
		jpanel.add(label4);

		label5 = new JLabel("当前xml文件路径：");
		label5.setBounds(10, 40, 200, 50);
		jpanel.add(label5);

		label6 = new JLabel("暂无");
		label6.setFont(new Font("Dialog", 1, 20));
		label6.setForeground(Color.red);
		label6.setBounds(150, 0, 600, 50);
		jpanel.add(label6);

		label7 = new JLabel("暂无");
		label7.setFont(new Font("Dialog", 1, 20));
		label7.setForeground(Color.red);
		label7.setBounds(150, 40, 600, 50);
		jpanel.add(label7);

		label8 = new JLabel("输入方法名：");
		label8.setBounds(10, 80, 200, 50);
		jpanel.add(label8);

		field4 = new JTextField("protected void bindView()");
		field4.setBounds(150, 90, 300, 30);
		jpanel.add(field4);

		button3 = new JButton("确定");
		button3.setBounds(10, 180, 150, 30);
		button3.setActionCommand("3");
		button3.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub

				try
				{

					new SelectWidgetVariableInterface(main.getActivityPathStrStr(), main.getXmlPathStr(), main.getField5().getText().toString());

					// SelectWidgetVariableInterface
					// instance=SelectWidgetVariableInterface.getInstance(main.getActivityPathStrStr(),
					// main.getXmlPathStr(), main.getField5().getText()
					// .toString());
					// instance.init();

				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		jpanel.add(button3);
	}

}

package pers.edward.androidtool.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pers.edward.androidtool.function.GetWidgetByXmlParser;
import pers.edward.androidtool.tool.CommonMethod;

/**
 * ���ɿؼ�����
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

	public GenerateWidgetInterface(JPanel jpanel, Container container, String codingType)
	{
		this.codingType = codingType;
		this.jpanel = jpanel;
		this.container = container;
		common = new CommonMethod(container);
		draw();
	}

	public void draw()
	{
		label4 = new JLabel("��ǰActivity�ļ�·����");
		label4.setBounds(10, 0, 200, 50);
		jpanel.add(label4);

		label5 = new JLabel("��ǰxml�ļ�·����");
		label5.setBounds(10, 40, 200, 50);
		jpanel.add(label5);

		label6 = new JLabel("����");
		label6.setFont(new Font("Dialog", 1, 20));
		label6.setForeground(Color.red);
		label6.setBounds(150, 0, 600, 50);
		jpanel.add(label6);

		label7 = new JLabel("����");
		label7.setFont(new Font("Dialog", 1, 20));
		label7.setForeground(Color.red);
		label7.setBounds(150, 40, 600, 50);
		jpanel.add(label7);

		label8 = new JLabel("���뷽������");
		label8.setBounds(10, 80, 200, 50);
		jpanel.add(label8);

		field4 = new JTextField("protected void bindView()");
		field4.setBounds(150, 90, 300, 30);
		jpanel.add(field4);

		button3 = new JButton("���ɴ���");
		button3.setBounds(10, 180, 150, 30);
		button3.setActionCommand("3");
		button3.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				generateWidget();
			}
		});
		jpanel.add(button3);
	}

	/**
	 * ���ɿؼ������¼�
	 */
	public void generateWidget()
	{
		String xmlPath1 = xmlPathStr;
		String targetPath = activityPathStrStr;
		String methodNmae = field4.getText().toString();

		try
		{
			if (!xmlPath1.isEmpty() && !targetPath.isEmpty() && !methodNmae.isEmpty() && !codingType.isEmpty())
			{
				GetWidgetByXmlParser getWidgetByXmlParser = new GetWidgetByXmlParser();
				getWidgetByXmlParser.generateWidget(xmlPath1, targetPath, methodNmae, codingType);

				common.showMessage("���������ɣ�");
			} else
			{
				common.showErrorMessage("���ɴ���ʧ�ܣ�");
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			common.showErrorMessage("���ɴ���ʧ�ܣ�");
			e.printStackTrace();
		}
	}
}

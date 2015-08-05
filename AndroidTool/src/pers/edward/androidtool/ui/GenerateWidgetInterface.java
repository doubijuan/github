package pers.edward.androidtool.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import pers.edward.androidtool.tool.CommonMethod;
import sun.awt.FontDescriptor;

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
	private JLabel label1, label2, label3, label4, label5, label6, label7;
	// private JTextField field4;
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

		label1 = new JLabel("指定控件修饰符：");
		label1.setBounds(10, 80, 200, 50);
		jpanel.add(label1);

		label2 = new JLabel("是否生成监听器：");
		label2.setBounds(10, 120, 200, 50);
		jpanel.add(label2);

		label6 = new JLabel("暂无");
		label6.setFont(new Font("Dialog", 1, 16));
		label6.setForeground(Color.red);
		label6.setBounds(150, 0, 600, 50);
		jpanel.add(label6);

		label7 = new JLabel("暂无");
		label7.setFont(new Font("Dialog", 1, 16));
		label7.setForeground(Color.red);
		label7.setBounds(150, 40, 600, 50);
		jpanel.add(label7);

		ButtonGroup gb = new ButtonGroup();
		final String[] modifiers = { "private", "public", "protected" };
		final JRadioButton[] modifierRadioButton = new JRadioButton[modifiers.length];
		// 设置修饰符
		for (int i = 0; i < modifiers.length; i++)
		{
			JRadioButton jr = new JRadioButton();
			jr.setText(modifiers[i]);
			jr.setBounds(120 + i * 80, 95, 80, 20);
			jpanel.add(jr);

			if (i == 0)
				jr.setSelected(true);
			modifierRadioButton[i] = jr;
			gb.add(jr);
		}

		gb = new ButtonGroup();
		final String[] modifiers1 = { "是", "否" };
		final JRadioButton[] isListenerRadioButton = new JRadioButton[modifiers1.length];
		// 设置修饰符
		for (int i = 0; i < modifiers1.length; i++)
		{
			JRadioButton jr = new JRadioButton();
			jr.setText(modifiers1[i]);
			jr.setBounds(120 + i * 80, 135, 80, 20);
			jpanel.add(jr);

			if (i == 0)
				jr.setSelected(true);
			isListenerRadioButton[i] = jr;
			gb.add(jr);
		}

		main.getField1().getDocument().addDocumentListener(new TextFiledChangeListener());
		// 显示所有的文件
		// File file = new File(main.getField1().getText());
		// File[] files = file.listFiles();
		//
		// JTextArea area = new JTextArea();
		// // 设置了才会显示滚动条
		// area.setPreferredSize(new Dimension(350, files.length * 25));
		// ButtonGroup bg = new ButtonGroup();
		// for (int i = 0; i < files.length; i++)
		// {
		// JRadioButton tempRadioButton = new JRadioButton();
		// tempRadioButton.setBounds(0, 25 * i, 300, 30);
		// tempRadioButton.setText(files[i].getName());
		// tempRadioButton.setFont(new Font("", 1, 12));
		// tempRadioButton.setBackground(Color.white);
		// if (i == 0)
		// {
		// tempRadioButton.setSelected(true);
		// }
		//
		// area.add(tempRadioButton);
		// bg.add(tempRadioButton);
		// }
		//
		// JScrollPane scroll = new JScrollPane(area);
		// scroll.setEnabled(false);
		// scroll.setBounds(470, 10, 320, 320);
		// scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		// jpanel.add(scroll);

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
					new SelectWidgetVariableInterface(main.getActivityPathStrStr(), main.getXmlPathStr(), main.getField5().getText().toString(),
							modifierRadioButton, isListenerRadioButton);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		jpanel.add(button3);
	}

	/**
	 * 监听文本变化
	 * 
	 * @author Edward
	 * 
	 */
	public class TextFiledChangeListener implements DocumentListener
	{
		@Override
		public void changedUpdate(DocumentEvent arg0)
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void insertUpdate(DocumentEvent arg0)
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void removeUpdate(DocumentEvent arg0)
		{
			// TODO Auto-generated method stub
		}
	}

}

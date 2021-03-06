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

import pers.edward.androidtool.function.getActivity;
import pers.edward.androidtool.tool.CommonMethod;

/**
 * 生成Activity页面
 * 
 * @author Edward
 * 
 */
public class GenerateActivityInterface
{
	private JLabel label16, label17, label18;
	private JTextField field10;
	private JButton button7, button8, button9;
	private JPanel jpanel;
	private Container container;
	private CommonMethod common;
	private String activityFolderPath;
	private static GenerateActivityInterface instance = null;

	public static GenerateActivityInterface getInstance(JPanel jpanel, Container container, String activityFolderPath)
	{
		if (instance == null)
		{
			instance = new GenerateActivityInterface(jpanel, container, activityFolderPath);
		}
		return instance;
	}

	private GenerateActivityInterface(JPanel jpanel, Container container, String activityFolderPath)
	{
		this.activityFolderPath = activityFolderPath;
		this.jpanel = jpanel;
		this.container = container;
		common = new CommonMethod(container);
		draw();
	}

	public void draw()
	{
		label16 = new JLabel();
		label16.setFont(new Font("Dialog", 1, 15));
		label16.setForeground(Color.red);
		label16.setBounds(170, 10, 500, 30);
		jpanel.add(label16);

		label18 = new JLabel();
		label18.setFont(new Font("Dialog", 1, 15));
		label18.setForeground(Color.red);
		label18.setBounds(170, 50, 500, 30);
		jpanel.add(label18);

		label17 = new JLabel("继承类：");
		label17.setBounds(10, 100, 120, 30);
		jpanel.add(label17);

		button7 = new JButton("选择xml布局文件路径");
		button7.setBounds(10, 10, 150, 30);
		button7.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				common.fileChooice(label16, ".xml");
			}
		});
		jpanel.add(button7);

		button8 = new JButton("选择配置文件路径");
		button8.setBounds(10, 50, 150, 30);
		button8.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				common.fileChooice(label18, ".xml");
			}
		});
		jpanel.add(button8);

		field10 = new JTextField("Activity");
		field10.setBounds(100, 100, 200, 30);
		jpanel.add(field10);

		button9 = new JButton("生成Activity");
		button9.setBounds(10, 200, 150, 30);
		button9.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub

				getActivity test = new getActivity();
				test.test(field10.getText().toString(), activityFolderPath, label16.getText().toString(), label18.getText().toString());
			}
		});
		jpanel.add(button9);
	}

}

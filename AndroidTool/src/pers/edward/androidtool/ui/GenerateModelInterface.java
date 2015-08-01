package pers.edward.androidtool.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.Context;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pers.edward.androidtool.function.GetGenerateModel;
import pers.edward.androidtool.tool.CommonMethod;

/**
 * 生成model界面
 * 
 * @author Edward
 * 
 */
public class GenerateModelInterface
{
	private JLabel label11;
	private JLabel label12;
	private JTextField field2, field5, field6;
	private JButton button4, button5;
	private JPanel jpanel;
	private CommonMethod common;
	private Main main;
	private static GenerateModelInterface instance = null;

	public static GenerateModelInterface getInstanceModel(JPanel jpanel, Container container, Main main)
	{
		if (instance == null)
		{
			instance = new GenerateModelInterface(jpanel, container, main);
		}
		return instance;

	}

	private GenerateModelInterface(JPanel jpanel, Container container, Main main)
	{
		this.main = main;
		this.jpanel = jpanel;
		common = new CommonMethod(container);
		draw();
	}

	public void draw()
	{
		button4 = new JButton("选择文件");
		button4.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub

				common.fileChooice(label11, ".json");
			}
		});
		button4.setBounds(10, 10, 120, 30);
		jpanel.add(button4);

		label11 = new JLabel();
		label11.setFont(new Font("Dialog", 1, 20));
		label11.setForeground(Color.red);
		label11.setBounds(150, 10, 500, 30);
		jpanel.add(label11);

		label12 = new JLabel("Model文件名称：");
		label12.setBounds(10, 50, 500, 30);
		jpanel.add(label12);

		field6 = new JTextField();
		field6.setBounds(150, 50, 300, 30);
		jpanel.add(field6);

		button5 = new JButton("生成model");
		button5.setBounds(10, 180, 150, 30);
		button5.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub
				GetGenerateModel model = new GetGenerateModel();
				try
				{
					model.test(label11.getText().toString(), main.getField2().getText().toString(), field6.getText().toString(), main.getField5()
							.getText().toString());
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		jpanel.add(button5);

	}

}

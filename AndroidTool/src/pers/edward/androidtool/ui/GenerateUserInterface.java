package pers.edward.androidtool.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import pers.edward.androidtool.function.getUserInterface;
import pers.edward.androidtool.tool.CommonMethod;

/**
 * 生成界面UI
 * 
 * @author Edward
 * 
 */
public class GenerateUserInterface
{
	private JLabel label16, label17, label18;
	private JTextField field10;
	private JButton button7, button8, button9;
	private JPanel jpanel;
	private Container container;
	private CommonMethod common;
	private String activityFolderPath;
	private JComboBox box;
	private static GenerateUserInterface instance = null;
	private Main main;
	private JCheckBox[] checkBoxs;

	public JButton getButton9()
	{
		return button9;
	}

	public void setButton9(JButton button9)
	{
		this.button9 = button9;
	}

	public GenerateUserInterface(JPanel jpanel, Container container, Main main)
	{
		this.main = main;
		this.jpanel = jpanel;
		this.container = container;
		common = new CommonMethod(container);
		draw();
	}

	/**
	 * 加载XML布局文件列表
	 * 
	 * @param xmlFolderPath
	 */
	public void loadXMLFileList(String xmlFolderPath)
	{
		if (xmlFolderPath == null)
		{
			return;
		}

		JTextArea area = new JTextArea();

		JScrollPane scroll = new JScrollPane(area);
		scroll.setEnabled(false);
		scroll.setBounds(470, 10, 320, 320);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// 设置了才会显示滚动条
		File file = new File(xmlFolderPath);
		File[] files = file.listFiles();
		if (files.length == 0)
		{
			return;
		}
		checkBoxs = new JCheckBox[files.length];
		area.setPreferredSize(new Dimension(350, files.length * 25));
		for (int i = 0; i < files.length; i++)
		{
			// System.out.println(files[i].getName());
			// 显示所有的文件
			JCheckBox checkBox = new JCheckBox(files[i].getName());
			checkBox.setBounds(0, 25 * i, 300, 30);
			checkBox.setText(files[i].getName());
			checkBox.setFont(new Font("", 1, 12));
			checkBox.setSelected(false);
			checkBox.setBackground(Color.white);
			checkBoxs[i] = checkBox;
			area.add(checkBox);
		}

		button9.setEnabled(true);
		jpanel.add(scroll);

	}

	public void draw()
	{
		// main.getLayoutPath()C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\res\\layout
		System.out.println("asdasdasd         " + main.getLayoutPath());
		loadXMLFileList(main.getLayoutPath());

		box = new JComboBox();
		box.addItem("Activity");
		box.addItem("Fragment");
		box.addItem("FragmentActivity");
		box.setBounds(120, 10, 150, 30);
		jpanel.add(box);

		label17 = new JLabel("继承类：");
		label17.setBounds(10, 10, 150, 30);
		jpanel.add(label17);

		button9 = new JButton("生成界面UI");
		button9.setBounds(10, 200, 150, 30);
		button9.setEnabled(false);
		button9.setEnabled(false);
		button9.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				// TODO Auto-generated method stub

				File file = new File(main.getLayoutPath());
				if (!file.exists())
				{
					common.showErrorMessage("找不到xml根目录，请检查项目文件夹是否设置正确！");
					return;
				}

				file = new File(main.getAndroidManifestPath());
				if (!file.exists())
				{
					common.showErrorMessage("找不到AndroidManifest文件，请检查项目文件夹是否设置正确！");
					return;
				}

				List<String> list = new ArrayList<String>();
				// list.add(label16.getText());

				for (int i = 0; i < checkBoxs.length; i++)
				{
					if (checkBoxs[i].isSelected())
					{
						System.out.println("文件名称：" + checkBoxs[i].getText());
						String selectedFilePath = main.getLayoutPath() + "\\" + checkBoxs[i].getText();
						System.out.println("文件路径：" + selectedFilePath);
						File file1 = new File(selectedFilePath);
						if (file1.exists())
						{
							list.add(selectedFilePath);
						} else
						{
							common.showErrorMessage("所选的文件路径不存在！");
							break;
						}
					}
				}

				for (int i = 0; i < list.size(); i++)
				{
					System.err.println("所选的文件路径：" + list.get(i));

				}

				getUserInterface ui = new getUserInterface();
				ui.test(box.getSelectedItem().toString(), main.getField().getText(), list, main.getAndroidManifestPath(), main.getBox()
						.getSelectedItem().toString());
			}
		});
		jpanel.add(button9);
	}

}

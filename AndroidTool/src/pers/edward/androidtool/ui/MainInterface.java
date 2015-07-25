package pers.edward.androidtool.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.io.monitor.FileAlterationMonitor;

import pers.edward.androidtool.interfaces.FileChangeListener;
import pers.edward.androidtool.interfaces.FileChangeListener.setChangeFilePath;
import pers.edward.androidtool.tool.CommonMethod;

/**
 * 主界面
 * 
 * @author Edward
 * 
 */
public class MainInterface implements ActionListener {
	private JPanel jpanel;
	private JLabel label, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11;
	private JLabel label12, label13, label14, label15, label16, label17, label18;
	private JTextField field, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11;
	private JButton button1, button2, button3, button4, button5, button6, button7, button8, button9;
	private CommonMethod common;
	private Container container;
	private FileAlterationMonitor monitor = null;

	public MainInterface(JPanel jpanel, Container container) {
		this.jpanel = jpanel;
		common = new CommonMethod(container);
		draw();
	}

	public void draw() {
		label = new JLabel("输入Activity文件夹路径：");
		label.setBounds(10, 0, 200, 50);
		jpanel.add(label);

		label1 = new JLabel("输入xml文件夹路径：");
		label1.setBounds(10, 40, 200, 50);
		jpanel.add(label1);

		label2 = new JLabel("输入model文件夹路径：");
		label2.setBounds(10, 80, 200, 50);
		jpanel.add(label2);

		label3 = new JLabel("监听时间间隔设置：");
		label3.setBounds(10, 120, 200, 50);
		jpanel.add(label3);

		label9 = new JLabel("输入编码方式：");
		label9.setBounds(350, 120, 200, 50);
		jpanel.add(label9);

		label10 = new JLabel("作者：Edward");
		label10.setBounds(10, 290, 100, 50);
		jpanel.add(label10);

		field5 = new JTextField("gbk");
		field5.setBounds(450, 130, 100, 30);
		jpanel.add(field5);

		field = new JTextField("C:\\MyWorkspace\\Android\\YiHuaHotel\\YiHuaHotel\\src\\com\\zhanyun\\yihuahotel\\activities");
		field.setBounds(170, 10, 600, 30);
		jpanel.add(field);

		field1 = new JTextField("C:\\MyWorkspace\\Android\\YiHuaHotel\\YiHuaHotel\\res\\layout");
		field1.setBounds(170, 50, 600, 30);
		jpanel.add(field1);

		field2 = new JTextField("C:\\MyWorkspace\\Android\\YiHuaHotel\\YiHuaHotel\\src\\com\\zhanyun\\yihuahotel\\model");
		field2.setBounds(170, 90, 600, 30);
		jpanel.add(field2);

		field3 = new JTextField("1000");
		field3.setBounds(170, 130, 100, 30);
		jpanel.add(field3);

		button1 = new JButton("开始监听");
		button1.setBounds(10, 180, 150, 30);
		button1.setActionCommand("1");
		button1.addActionListener(this);
		jpanel.add(button1);

		button2 = new JButton("停止监听");
		button2.setBounds(210, 180, 150, 30);
		button2.setActionCommand("2");
		button2.addActionListener(this);
		button2.setEnabled(false);
		jpanel.add(button2);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Integer integer = Integer.valueOf(arg0.getActionCommand());
		switch (integer) {
		// 开始监听
		case 1:
			try {
				File file = new File(field.getText().toString().trim());
				if (!file.isDirectory()) {
					common.showErrorMessage("输入的路径目录无效！");
					return;
				}

				file = new File(field1.getText().toString().trim());
				if (!file.isDirectory()) {
					common.showErrorMessage("输入的路径目录无效！");
					return;
				}
//				banTextInput();
//				setListener();
//				start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		// 停止监听
		case 2:
			try {
				cancelTextInput();
//				stop();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

//	/**
//	 * 设置监听事件
//	 */
//	public void setListener() {
//		monitor = new FileAlterationMonitor(Integer.valueOf(field3.getText().toString()));
//		monitor(field.getText().toString().trim(), new FileChangeListener(new setChangeFilePath() {
//
//			@Override
//			public void alreadyChangeFilePath(File file) {
//				// TODO Auto-generated method stub
//				activityPathStrStr = file.getPath();
//				label6.setText(activityPathStrStr.substring(activityPathStrStr.lastIndexOf("\\") + 1, activityPathStrStr.length()));
//			}
//		}));
//
//		monitor(field1.getText().toString().trim(), new FileChangeListener(new setChangeFilePath() {
//
//			@Override
//			public void alreadyChangeFilePath(File file) {
//				// TODO Auto-generated method stub
//				xmlPathStr = file.getPath();
//				label7.setText(xmlPathStr.substring(xmlPathStr.lastIndexOf("\\") + 1, xmlPathStr.length()));
//			}
//		}));
//	}

	/**
	 * 禁止文本输入
	 */
	public void banTextInput() throws Exception {
		field.setEditable(false);
		field1.setEditable(false);
		field2.setEditable(false);
		field3.setEditable(false);
		field5.setEditable(false);
		button1.setEnabled(false);
		button2.setEnabled(true);
	}

	/**
	 * 取消文本输入
	 */
	public void cancelTextInput() {
		field.setEditable(true);
		field1.setEditable(true);
		field2.setEditable(true);
		field3.setEditable(true);
		field5.setEditable(true);
		button1.setEnabled(true);
		button2.setEnabled(false);
	}
}

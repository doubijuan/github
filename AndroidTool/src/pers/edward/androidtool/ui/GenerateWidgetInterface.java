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
 * 生成控件界面
 * 
 * @author Edward
 * 
 */
public class GenerateWidgetInterface implements ActionListener {
	private JPanel jpanel;
	private Container container;
	private JLabel label, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11;
	private JLabel label12, label13, label14, label15, label16, label17, label18;
	private JTextField field, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11;
	private JButton button1, button2, button3, button4, button5, button6, button7, button8, button9;
	private String activityPathStrStr = "", xmlPathStr = "";
	private CommonMethod common;

	public GenerateWidgetInterface(JPanel jpanel, Container container) {
		this.jpanel = jpanel;
		this.container = container;
		common = new CommonMethod(container);
		draw();
	}

	public void draw() {
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

		button3 = new JButton("生成代码");
		button3.setBounds(10, 180, 150, 30);
		button3.setActionCommand("3");
		button3.addActionListener(this);
		jpanel.add(button3);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Integer integer = Integer.valueOf(arg0.getActionCommand());
		switch (integer) {
		case 3:
			generateCode();
			break;
		}
	}

	/**
	 * 生成控件代码事件
	 */
	public void generateCode() {
		String xmlPath1 = xmlPathStr;
		String targetPath = activityPathStrStr;
		String methodNmae = field4.getText().toString();
		String codingType = field5.getText().toString();
		try {
			if (!xmlPath1.isEmpty() && !targetPath.isEmpty() && !methodNmae.isEmpty() && !codingType.isEmpty()) {
				GetWidgetByXmlParser getWidgetByXmlParser = new GetWidgetByXmlParser();
				getWidgetByXmlParser.generateWidget(xmlPath1, targetPath, methodNmae, codingType);
				// new GetWidgetByXmlParser(xmlPath1, targetPath, methodNmae,
				// codingType);
				common.showMessage("代码已生成！");
			} else {
				common.showErrorMessage("生成代码失败！");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			common.showErrorMessage("生成代码失败！");
			e.printStackTrace();
		}
	}
}

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
import pers.edward.androidtool.model.RecordSelectedIndexModel;
import pers.edward.androidtool.tool.CommonMethod;

/**
 * 生成网络请求地址页面
 * 
 * @author Edward
 * 
 */
public class GenerateUrlInterface {
	private JPanel jpanel;
	private JLabel label13, label14, label15;
	private JTextField field7, field8, field9;
	private JButton button6;
	private CommonMethod common;
	private String codingFormat;

	public GenerateUrlInterface(JPanel jpanel, Container container, String codingFormat) {
		this.jpanel = jpanel;
		this.codingFormat = codingFormat;
		common = new CommonMethod(container);
		draw();
	}

	public void draw() {
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

		field8 = new JTextField("C:\\MyWorkspace\\JAVASE\\MyExercise\\AndroidTool\\src\\pers\\edward\\androidtool\\function\\TestUrl.java");
		field8.setBounds(170, 50, 600, 30);
		jpanel.add(field8);

		field9 = new JTextField("INTERNET_URL");
		field9.setBounds(170, 90, 200, 30);
		jpanel.add(field9);

		button6 = new JButton("生成Url接口");
		button6.setBounds(10, 250, 150, 30);
		jpanel.add(button6);

		button6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				getUrlInterface getInterface = new getUrlInterface();
				try {
					getInterface.test("http://120.24.62.95:9202",
							"C:\\MyWorkspace\\JAVASE\\MyExercise\\AndroidTool\\src\\pers\\edward\\androidtool\\function\\TestUrl.java",
							"INTERNET_URL", "gbk");

					List<RecordSelectedIndexModel> list = new ArrayList<RecordSelectedIndexModel>();

					for (int i = 1; i <= 3; i++) {
						RecordSelectedIndexModel model = new RecordSelectedIndexModel();
						model.setIndex(i);
						List<Integer> tempList = new ArrayList<Integer>();
						tempList.add(1);
						tempList.add(3);
						tempList.add(2);
						model.setSubListIndex(tempList);

						list.add(model);
					}
					
					getInterface.outputUrlToTargetFile(list);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// try {
				// getInterface.test(field7.getText().toString(),
				// field8.getText().toString(), field9.getText().toString(),
				// codingFormat);
				// common.showMessage("生成接口成功！");
				// } catch (Exception e) {
				// // TODO Auto-generated catch block
				// common.showErrorMessage("生成接口失败！");
				// e.printStackTrace();
				// }

			}
		});
	}

}

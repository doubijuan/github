package pers.edward.androidtool.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.naming.Context;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import pers.edward.androidtool.function.GetGenerateModel;
import pers.edward.androidtool.interfaces.FileChangeListener;
import pers.edward.androidtool.interfaces.FileChangeListener.setChangeFilePath;
import pers.edward.androidtool.tool.CommonMethod;

public class Main extends JFrame implements ActionListener, ItemListener
{
	private static Main main;
	private GenerateWidgetInterface generateWidgetInterface;
	private Container container = null;
	private JTabbedPane tabbedPane = null;
	private String[] tabName = { "������", "���ɿؼ�", "����Model", "����URL�ӿ�", "����Activity" };
	private JPanel[] mJpanel;
	private JLabel label, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11;
	private JTextField field, field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11;
	private JButton button1, button2, button3, button4, button5, button6, button7, button8, button9;
	private String activityPathStrStr = "", xmlPathStr = "";
	private FileAlterationMonitor monitor = null;
	private CommonMethod common = new CommonMethod(getContentPane());

	public Main() throws Exception
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 400);
		// �̶������С
		setResizable(false);
		// ����Swing����UI����ϵͳ�仯
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setTitle("Android����������");
		setLayout(new GridLayout(1, 1));
		CommonMethod.setLayoutCenter(this);
		container = getContentPane();
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		mJpanel = new JPanel[tabName.length];

		for (int i = 0; i < tabName.length; i++)
		{
			mJpanel[i] = new JPanel();
			// ���óɾ��Բ���
			mJpanel[i].setLayout(null);
			tabbedPane.addTab(tabName[i], mJpanel[i]);
		}

		// �������һ���沼��
		setJPanelOneLayout(mJpanel[0]);
		generateWidgetInterface = new GenerateWidgetInterface(mJpanel[1], getContentPane(), this);
		GenerateModelInterface.getInstanceModel(mJpanel[2], getContentPane(), this);
		GenerateUrlInterface.getInstanceInterface(mJpanel[3], getContentPane(), field5.getText().toString());
		GenerateActivityInterface.getInstance(mJpanel[4], getContentPane(), field.getText().toString());

		container.add(tabbedPane, BorderLayout.CENTER);
	}

	public JTextField getField1()
	{
		return field1;
	}

	public void setField1(JTextField field1)
	{
		this.field1 = field1;
	}

	public JTextField getField2()
	{
		return field2;
	}

	public JTextField getField5()
	{
		return field5;
	}

	public void setField5(JTextField field5)
	{
		this.field5 = field5;
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

	@Override
	public void itemStateChanged(ItemEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * �������һ�Ĳ���
	 * 
	 * @param jpanel
	 */
	public void setJPanelOneLayout(JPanel jpanel)
	{
		label = new JLabel("����Activity�ļ���·����");
		label.setBounds(10, 0, 200, 50);
		jpanel.add(label);

		label1 = new JLabel("����xml�ļ���·����");
		label1.setBounds(10, 40, 200, 50);
		jpanel.add(label1);

		label2 = new JLabel("����model�ļ���·����");
		label2.setBounds(10, 80, 200, 50);
		jpanel.add(label2);

		label3 = new JLabel("����ʱ�������ã�");
		label3.setBounds(10, 120, 200, 50);
		jpanel.add(label3);

		label9 = new JLabel("������뷽ʽ��");
		label9.setBounds(350, 120, 200, 50);
		jpanel.add(label9);

		label10 = new JLabel("���ߣ�Edward");
		label10.setBounds(10, 290, 100, 50);
		jpanel.add(label10);

		field5 = new JTextField("utf-8");
		field5.setBounds(450, 130, 100, 30);
		jpanel.add(field5);

		field = new JTextField("C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\src\\cn\\zhanyun\\n18client");
		field.setBounds(170, 10, 600, 30);
		jpanel.add(field);

		field1 = new JTextField("C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\res\\layout");
		field1.setBounds(170, 50, 600, 30);
		jpanel.add(field1);

		field2 = new JTextField("C:\\MyWorkspace\\JAVASE\\github\\AndroidTool\\src\\pers\\edward\\androidtool\\model");
		field2.setBounds(170, 90, 600, 30);
		jpanel.add(field2);

		field3 = new JTextField("1000");
		field3.setBounds(170, 130, 100, 30);
		jpanel.add(field3);

		button1 = new JButton("��ʼ����");
		button1.setBounds(10, 180, 150, 30);
		button1.setActionCommand("1");
		button1.addActionListener(this);
		jpanel.add(button1);

		button2 = new JButton("ֹͣ����");
		button2.setBounds(210, 180, 150, 30);
		button2.setActionCommand("2");
		button2.addActionListener(this);
		button2.setEnabled(false);
		jpanel.add(button2);
	}

	public void stop() throws Exception
	{
		monitor.stop();
	}

	public void start() throws Exception
	{
		monitor.start();
	}

	public static void main(String[] args) throws Exception
	{
		main = new Main();
		main.setVisible(true);
	}

	public void monitor(String path, FileAlterationListener listener)
	{
		FileAlterationObserver observer = new FileAlterationObserver(new File(path));
		monitor.addObserver(observer);
		observer.addListener(listener);
	}

	/**
	 * ��ť�����¼�
	 */
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		Integer integer = Integer.valueOf(arg0.getActionCommand());
		switch (integer)
		{
		// ��ʼ����
		case 1:
			try
			{
				File file = new File(field.getText().toString().trim());
				if (!file.isDirectory())
				{
					common.showErrorMessage("�����·��Ŀ¼��Ч��");
					return;
				}

				file = new File(field1.getText().toString().trim());
				if (!file.isDirectory())
				{
					common.showErrorMessage("�����·��Ŀ¼��Ч��");
					return;
				}
				banTextInput();
				setListener();
				start();
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		// ֹͣ����
		case 2:
			try
			{
				cancelTextInput();
				stop();
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case 4:

			common.fileChooice(label11, ".json");
			break;
		// ����Model����¼�
		case 5:
			try
			{
				GetGenerateModel model = new GetGenerateModel();
				model.test(label11.getText().toString(), field2.getText().toString(), field6.getText().toString(), field5.getText().toString());

				common.showMessage("�����ļ��ɹ���");
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				common.showErrorMessage("����Model�ļ�ʧ��");
				e.printStackTrace();
			}
			break;
		}
	}

	/**
	 * ���ü����¼�
	 */
	public void setListener()
	{
		monitor = new FileAlterationMonitor(Integer.valueOf(field3.getText().toString()));
		monitor(field.getText().toString().trim(), new FileChangeListener(new setChangeFilePath()
		{

			@Override
			public void alreadyChangeFilePath(File file)
			{
				// TODO Auto-generated method stub
				activityPathStrStr = file.getPath();
				generateWidgetInterface.setActivityPathStrStr(activityPathStrStr);
				generateWidgetInterface.getLabel6().setText(
						activityPathStrStr.substring(activityPathStrStr.lastIndexOf("\\") + 1, activityPathStrStr.length()));
			}
		}));

		monitor(field1.getText().toString().trim(), new FileChangeListener(new setChangeFilePath()
		{

			@Override
			public void alreadyChangeFilePath(File file)
			{
				// TODO Auto-generated method stub
				xmlPathStr = file.getPath();
				generateWidgetInterface.setXmlPathStr(xmlPathStr);
				generateWidgetInterface.getLabel7().setText(xmlPathStr.substring(xmlPathStr.lastIndexOf("\\") + 1, xmlPathStr.length()));
			}
		}));
	}

	/**
	 * ��ֹ�ı�����
	 */
	public void banTextInput() throws Exception
	{
		field.setEditable(false);
		field1.setEditable(false);
		field2.setEditable(false);
		field3.setEditable(false);
		field5.setEditable(false);
		button1.setEnabled(false);
		button2.setEnabled(true);
	}

	/**
	 * ȡ���ı�����
	 */
	public void cancelTextInput()
	{
		field.setEditable(true);
		field1.setEditable(true);
		field2.setEditable(true);
		field3.setEditable(true);
		field5.setEditable(true);
		button1.setEnabled(true);
		button2.setEnabled(false);
	}

}

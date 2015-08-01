package pers.edward.androidtool.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

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
import pers.edward.androidtool.function.getActivity;
import pers.edward.androidtool.interfaces.FileChangeListener;
import pers.edward.androidtool.interfaces.FileChangeListener.setChangeFilePath;
import pers.edward.androidtool.tool.CommonMethod;

public class Main extends JFrame implements ActionListener, ItemListener
{
	private GenerateWidgetInterface generateWidgetInterface;
	private Container container = null;
	private JTabbedPane tabbedPane = null;
	private String[] tabName = { "������", "���ɿؼ�", "����Model", "����URL�ӿ�", "����Activity" };
	private JPanel[] mJpanel;
	private JLabel label, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11;
	private JLabel label12, label13, label14, label15, label16, label17, label18;
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
		setLayoutCenter();
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
		setJPanelTwoLayout(mJpanel[1]);
		setJPanelThreeLayout(mJpanel[2]);
		setJPanelFourLayout(mJpanel[3]);
		setJPanelFiveLayout(mJpanel[4]);
		container.add(tabbedPane, BorderLayout.CENTER);
	}

	/**
	 * ���������Ĳ���
	 * 
	 * @param jpanel
	 */
	public void setJPanelFiveLayout(JPanel jpanel)
	{
		GenerateActivityInterface activityInterface = new GenerateActivityInterface(jpanel, getContentPane());

//		label16 = new JLabel();
//		label16.setFont(new Font("Dialog", 1, 15));
//		label16.setForeground(Color.red);
//		label16.setBounds(170, 10, 500, 30);
//		jpanel.add(label16);
//
//		label18 = new JLabel();
//		label18.setFont(new Font("Dialog", 1, 15));
//		label18.setForeground(Color.red);
//		label18.setBounds(170, 50, 500, 30);
//		jpanel.add(label18);
//
//		label17 = new JLabel("�̳��ࣺ");
//		label17.setBounds(10, 100, 120, 30);
//		jpanel.add(label17);
//
//		button7 = new JButton("ѡ��xml�����ļ�·��");
//		button7.setBounds(10, 10, 150, 30);
//		button7.addActionListener(this);
//		button7.setActionCommand("6");
//		jpanel.add(button7);
//
//		button8 = new JButton("ѡ�������ļ�·��");
//		button8.setBounds(10, 50, 150, 30);
//		button8.addActionListener(this);
//		button8.setActionCommand("10");
//		jpanel.add(button8);
//
//		field10 = new JTextField("Activity");
//		field10.setBounds(100, 100, 200, 30);
//		jpanel.add(field10);
//
//		button9 = new JButton("����Activity");
//		button9.setBounds(10, 200, 150, 30);
//		button9.addActionListener(this);
//		button9.setActionCommand("8");
//		jpanel.add(button9);
	}

	/**
	 * ��������ĵĲ���
	 * 
	 * @param jpanel
	 */
	public void setJPanelFourLayout(JPanel jpanel)
	{
		// C:\MyWorkspace\JAVASE\MyExercise\AndroidTool\src\pers\edward\androidtool\function\TestUrl.java

		GenerateUrlInterface.getInstanceInterface(jpanel, getContentPane(), field5.getText().toString());
	}

	@Override
	public void itemStateChanged(ItemEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * ����������Ĳ���
	 * 
	 * @param jpanel
	 */
	public void setJPanelThreeLayout(JPanel jpanel)
	{
		button4 = new JButton("ѡ���ļ�");
		button4.setActionCommand("4");
		button4.addActionListener(this);
		button4.setBounds(10, 10, 120, 30);
		jpanel.add(button4);

		label11 = new JLabel();
		label11.setFont(new Font("Dialog", 1, 20));
		label11.setForeground(Color.red);
		label11.setBounds(150, 10, 500, 30);
		jpanel.add(label11);

		label12 = new JLabel("Model�ļ����ƣ�");
		label12.setBounds(10, 50, 500, 30);
		jpanel.add(label12);

		field6 = new JTextField();
		field6.setBounds(150, 50, 300, 30);
		jpanel.add(field6);

		button5 = new JButton("����model");
		button5.setBounds(10, 180, 150, 30);
		button5.setActionCommand("5");
		button5.addActionListener(this);
		jpanel.add(button5);
	}

	/**
	 * ���������Ĳ���
	 * 
	 * @param jpanel
	 */
	public void setJPanelTwoLayout(JPanel jpanel)
	{
		generateWidgetInterface = new GenerateWidgetInterface(jpanel, getContentPane(), field5.getText().toString());
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

		field5 = new JTextField("gbk");
		field5.setBounds(450, 130, 100, 30);
		jpanel.add(field5);

		field = new JTextField("C:\\MyWorkspace\\Android\\YunYiPurchase\\YunYiGou\\src\\com\\zhanyun\\yunyigou\\activities");
		field.setBounds(170, 10, 600, 30);
		jpanel.add(field);

		field1 = new JTextField("C:\\MyWorkspace\\Android\\YunYiPurchase\\YunYiGou\\res\\layout");
		field1.setBounds(170, 50, 600, 30);
		jpanel.add(field1);

		field2 = new JTextField("C:\\MyWorkspace\\Android\\YiHuaHotel\\YiHuaHotel\\src\\com\\zhanyun\\yihuahotel\\model");
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

	/**
	 * ���ò��־���
	 */
	public void setLayoutCenter()
	{
		int windowWidth = getWidth(); // ��ô��ڿ�
		int windowHeight = getHeight(); // ��ô��ڸ�
		Toolkit kit = Toolkit.getDefaultToolkit(); // ���幤�߰�
		Dimension screenSize = kit.getScreenSize(); // ��ȡ��Ļ�ĳߴ�
		int screenWidth = screenSize.width; // ��ȡ��Ļ�Ŀ�
		int screenHeight = screenSize.height; // ��ȡ��Ļ�ĸ�
		setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);
	}

	public static void main(String[] args) throws Exception
	{
		final Main main = new Main();
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
				// new GetGenerateModel(label11.getText().toString(),
				// field2.getText().toString(), field6.getText().toString(),
				// field5.getText()
				// .toString());
				common.showMessage("�����ļ��ɹ���");
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				common.showErrorMessage("����Model�ļ�ʧ��");
				e.printStackTrace();
			}
			break;

		case 6:
			common.fileChooice(label16, ".xml");
			break;

		case 8:
			getActivity test = new getActivity();
			test.test(field10.getText().toString(), field.getText().toString(), label16.getText().toString(), label18.getText().toString());
			break;

		case 10:
			common.fileChooice(label18, ".xml");
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

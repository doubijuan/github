package pers.edward.androidtool.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
	private JLabel label, label1, label2, label3, label9, label10, label11, label4;
	private JTextField field, field1, field2, field6;
	private JButton button1, button2;
	private String activityPathStrStr = "", xmlPathStr = "";
	private FileAlterationMonitor monitor = null;
	private JComboBox codingTypeComboBox, intervalComboBox;
	private CommonMethod common = new CommonMethod(getContentPane());
	private GenerateUserInterface userInterface;
	private GenerateUrlInterface urlInterface;
	
	// xml�ļ��и�Ŀ¼
	private String layoutPath = null;
	// �����ļ�Ŀ¼
	private String androidManifestPath = null;

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
		// ��ȡ��ǰ��Ŀ·��
		CommonMethod.configFilePath = System.getProperty("user.dir") + "\\META-INF\\" + "android config.txt";

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
		urlInterface = new GenerateUrlInterface(mJpanel[3], getContentPane(), this);
		userInterface = new GenerateUserInterface(mJpanel[4], getContentPane(), this);

		// ��ȡ������Ϣ
		readConfigInfo(CommonMethod.configFilePath);

		// �˴���Ҫ�ع�����������������Activityҳ���xml�ļ��б�
		if (layoutPath != null || "".equals(layoutPath))
		{
			userInterface.getButton9().setEnabled(true);
			userInterface.loadXMLFileList(layoutPath);
		}

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

	public JComboBox getBox()
	{
		return codingTypeComboBox;
	}

	public void setBox(JComboBox box)
	{
		this.codingTypeComboBox = box;
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

	public JTextField getField()
	{
		return field;
	}

	public void setField(JTextField field)
	{
		this.field = field;
	}

	public String getLayoutPath()
	{
		return layoutPath;
	}

	public void setLayoutPath(String layoutPath)
	{
		this.layoutPath = layoutPath;
	}

	public String getAndroidManifestPath()
	{
		return androidManifestPath;
	}

	public void setAndroidManifestPath(String androidManifestPath)
	{
		this.androidManifestPath = androidManifestPath;
	}

	@Override
	public void itemStateChanged(ItemEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	private JButton btnSavedConfigInfo;

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

		JButton button = new JButton("ѡ����Ŀ�ļ���");
		button.setBounds(10, 180, 150, 30);
		button.addActionListener(new SelectProjectPath());
		jpanel.add(button);

		label4 = new JLabel("��ѡ����Ŀ·��");
		label4.setFont(new Font("Dialog", 1, 14));
		label4.setForeground(Color.red);
		label4.setBounds(170, 180, 600, 30);
		jpanel.add(label4);

		label3 = new JLabel("����ʱ�������ã�");
		label3.setBounds(10, 120, 200, 50);
		jpanel.add(label3);

		label9 = new JLabel("ѡ��������ͣ�");
		label9.setBounds(350, 120, 200, 50);
		jpanel.add(label9);

		btnSavedConfigInfo = new JButton("����������Ϣ");
		btnSavedConfigInfo.setBounds(10, 300, 150, 30);
		btnSavedConfigInfo.addActionListener(new SavedInfoClickListener(CommonMethod.configFilePath));
		jpanel.add(btnSavedConfigInfo);

		label10 = new JLabel("���ߣ�Edward");
		label10.setBounds(700, 290, 100, 50);
		jpanel.add(label10);

		codingTypeComboBox = new JComboBox();
		codingTypeComboBox.addItem("gbk");
		codingTypeComboBox.addItem("utf-8");
		codingTypeComboBox.addItem("ISO-8859-1");
		codingTypeComboBox.setBackground(Color.white);
		codingTypeComboBox.setBounds(450, 130, 100, 30);
		jpanel.add(codingTypeComboBox);

		// C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\src\\cn\\zhanyun\\n18client
		field = new JTextField("");
		field.setBounds(170, 10, 600, 30);
		jpanel.add(field);
		// C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\res\\layout
		field1 = new JTextField("");
		field1.setBounds(170, 50, 600, 30);
		jpanel.add(field1);
		// C:\\MyWorkspace\\JAVASE\\github\\AndroidTool\\src\\pers\\edward\\androidtool\\model
		field2 = new JTextField("");
		field2.setBounds(170, 90, 600, 30);
		jpanel.add(field2);

		intervalComboBox = new JComboBox();
		intervalComboBox.addItem("1");
		intervalComboBox.addItem("2");
		intervalComboBox.addItem("3");
		intervalComboBox.addItem("5");
		intervalComboBox.addItem("10");
		intervalComboBox.setBounds(170, 130, 40, 30);
		jpanel.add(intervalComboBox);

		JLabel seconds = new JLabel("��");
		seconds.setBounds(220, 130, 50, 30);
		jpanel.add(seconds);

		button1 = new JButton("��ʼ����");
		button1.setBounds(10, 250, 150, 30);
		button1.setActionCommand("1");
		button1.addActionListener(this);
		jpanel.add(button1);

		button2 = new JButton("ֹͣ����");
		button2.setBounds(210, 250, 150, 30);
		button2.setActionCommand("2");
		button2.addActionListener(this);
		button2.setEnabled(false);
		jpanel.add(button2);
	}

	/**
	 * ѡ����Ŀ·��
	 * 
	 * @author Edward
	 * 
	 */
	public class SelectProjectPath implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			// TODO Auto-generated method stub
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (JFileChooser.APPROVE_OPTION == jFileChooser.showOpenDialog(null))
			{

				System.out.println("��Ŀ·��" + jFileChooser.getSelectedFile().getPath());

				String rootPath = jFileChooser.getSelectedFile().getPath();

				if (userInterface != null)
				{
					File file = new File(rootPath);
					if (file.isDirectory())
					{
						layoutPath = rootPath + "\\res\\layout";
						androidManifestPath = rootPath + "\\AndroidManifest.xml";
						System.out.println("xml���ָ�Ŀ¼��" + layoutPath);
						System.out.println("�����ļ�Ŀ¼��" + androidManifestPath);

						userInterface.getButton9().setEnabled(true);

						userInterface.loadXMLFileList(layoutPath);
						label4.setText(jFileChooser.getSelectedFile().getPath());
					} else
					{
						common.showErrorMessage("�Ҳ���xml��Ŀ¼����ȷ����Ŀ·���Ƿ���ȷ��");
					}
				}
			}
		}
	}

	/**
	 * ����������Ϣ����¼�
	 * 
	 * @author Edward
	 * 
	 */
	public class SavedInfoClickListener implements ActionListener
	{
		private String path;

		public SavedInfoClickListener(String path)
		{
			this.path = path;
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			// TODO Auto-generated method stub

			File file = new File(path);

			if (file.getParentFile().mkdirs())
			{
				System.out.println("�����ļ��гɹ���");
			}

			try
			{
				if (!file.exists())
				{
					if (file.createNewFile())
					{
						System.out.println("���������ļ��ɹ�");
						// д��������Ϣ
						writeConfigFile(file);
					} else
					{
						System.out.println("���������ļ�ʧ��");
					}
				} else
				{
					CommonMethod.reSetUpFile(file.getPath());
					writeConfigFile(file);
				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				common.showErrorMessage("��������ʧ�ܣ�");
				e.printStackTrace();
			}

		}
	}

	/**
	 * ��ȡ������Ϣ
	 */
	public void readConfigInfo(String path)
	{
		File file = new File(path);
		if (file.exists())
		{
			String result = CommonMethod.fileToString(file.getPath(), "utf-8");
			System.out.println("��ȡ�����ļ���Ϣ����:");
			String[] strings = result.split(",");

			for (int i = 0; i < strings.length; i++)
			{
				System.out.println("-------->" + strings[i]);
			}

			try
			{
				field.setText(strings[0]);
				field1.setText(strings[1]);
				field2.setText(strings[2]);
				intervalComboBox.setSelectedItem(strings[3]);
				codingTypeComboBox.setSelectedItem(strings[4]);
				label4.setText(strings[5]);
				layoutPath = strings[6];
				System.err.println("hello        " + strings[6]);
				androidManifestPath = strings[7];
				urlInterface.getField7().setText(strings[8]);
				urlInterface.getField8().setText(strings[9]);
				urlInterface.getField9().setText(strings[10]);
			} catch (Exception e)
			{
				System.err.println("��ȡ������Ϣ����");
			}

		} else
		{
			System.out.println("�Ҳ��������ļ���");
		}
	}

	/**
	 * ����Ϣд�������ļ�
	 * 
	 * @param file
	 */
	public void writeConfigFile(File file)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(field.getText() + ",");
		sb.append(field1.getText() + ",");
		sb.append(field2.getText() + ",");
		sb.append(intervalComboBox.getSelectedItem().toString() + ",");
		sb.append(codingTypeComboBox.getSelectedItem().toString() + ",");
		sb.append(label4.getText() + ",");
		sb.append(layoutPath + ",");
		sb.append(androidManifestPath + ",");
		sb.append(urlInterface.getField7().getText() + ",");
		sb.append(urlInterface.getField8().getText() + ",");
		sb.append(urlInterface.getField9().getText() + ",");
		sb.append("null");
		CommonMethod.inputDataToTargetFile(file.getPath(), sb.toString(), "utf-8");
		common.showMessage("�������óɹ���");
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
				model.test(label11.getText().toString(), field2.getText().toString(), field6.getText().toString(), codingTypeComboBox
						.getSelectedItem().toString());

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
		monitor = new FileAlterationMonitor(Integer.valueOf(intervalComboBox.getSelectedItem().toString()) * 1000);
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
		button1.setEnabled(false);
		generateWidgetInterface.getButton3().setEnabled(true);
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
		button1.setEnabled(true);
		generateWidgetInterface.getButton3().setEnabled(false);
		button2.setEnabled(false);
	}

}

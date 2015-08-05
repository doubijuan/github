package pers.edward.androidtool.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultTreeModel;

import pers.edward.androidtool.function.GetWidgetByXmlParser;
import pers.edward.androidtool.model.RecordSelectedIndexModel;
import pers.edward.androidtool.tool.CommonMethod;

/**
 * 选择方法列表
 * 
 * @author Edward
 * 
 */
public class SelectMethodListInterface extends JFrame
{
	private CommonMethod common;
	private static SelectMethodListInterface instance = null;
	private List<RecordSelectedIndexModel> list;
	private GetWidgetByXmlParser getWidgetByXmlParser;
	private String activityPath, codingType;
	private JRadioButton[] modifierRadioButton;
	private JRadioButton[] isListenerRadioButton;
	private List<String> listMethod;

	public SelectMethodListInterface(String activityPath, String codingType, List<RecordSelectedIndexModel> list,
			GetWidgetByXmlParser getWidgetByXmlParser, JRadioButton[] modifierRadioButton, JRadioButton[] isListenerRadioButton)
	{
		this.codingType = codingType;
		this.activityPath = activityPath;
		this.getWidgetByXmlParser = getWidgetByXmlParser;
		this.list = list;
		this.modifierRadioButton = modifierRadioButton;
		this.isListenerRadioButton = isListenerRadioButton;
		listMethod = new ArrayList<String>();

		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 600);
		CommonMethod.setLayoutCenter(this);
		// 固定窗体大小
		setResizable(false);
		// 设置Swing界面UI跟随系统变化
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setTitle("选择需要插入的方法");
		common = new CommonMethod(getContentPane());
		try
		{
			init();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void init() throws Exception
	{
		listMethod = getWidgetByXmlParser.getTargetFileMethodList(activityPath, codingType);

		generateSelectMethod(listMethod);
	}

	/**
	 * 生成选择树
	 * 
	 * @param listMethod
	 */
	public void generateSelectMethod(List<String> listMethod)
	{
		JTextArea area = new JTextArea();
		ButtonGroup bg = new ButtonGroup();

		JRadioButton[] methodRadioButton = new JRadioButton[listMethod.size()];

		area.setPreferredSize(new Dimension(350, listMethod.size() * 25 + 10));
		for (int i = 0; i < listMethod.size(); i++)
		{
			JRadioButton jb = new JRadioButton();
			String methodName = listMethod.get(i);
			jb.setText(methodName.substring(0, methodName.length() - 1));
			jb.setFont(new Font("", 1, 16));
			jb.setBackground(Color.white);
			jb.setBounds(10, 10 + i * 25, 500, 30);
			methodRadioButton[i] = jb;

			if (i == 0)
			{
				jb.setSelected(true);
			}

			area.add(jb);
			bg.add(jb);
		}
		JScrollPane scroll = new JScrollPane(area);
		scroll.setEnabled(false);
		scroll.setBounds(0, 0, 700, 500);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scroll);

		JButton button = new JButton("确定");
		button.setBounds(10, 520, 150, 30);
		button.addActionListener(new ClickListener(listMethod, methodRadioButton));
		add(button);

	}

	/**
	 * 点击确定事件
	 * 
	 * @author Edward
	 * 
	 */
	public class ClickListener implements ActionListener
	{
		private List<String> listMethod;
		private JRadioButton[] methodRdaioButton;

		public ClickListener(List<String> listMethod, JRadioButton[] methodRdaioButton)
		{
			this.methodRdaioButton = methodRdaioButton;
			this.listMethod = listMethod;
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			// TODO Auto-generated method stub
			String selectedMethodName = null;

			for (int i = 0; i < methodRdaioButton.length; i++)
			{
				if (methodRdaioButton[i].isSelected())
				{
					selectedMethodName = listMethod.get(i);
				}

			}

			// // 根据前一个页面选中的值
			try
			{
				String modifierVariable = "";
				for (int i = 0; i < modifierRadioButton.length; i++)
				{
					if (modifierRadioButton[i].isSelected())
					{
						modifierVariable = modifierRadioButton[i].getText();
					}
				}

				// 默认为添加监听事件
				boolean isListener = true;
				for (int i = 0; i < isListenerRadioButton.length; i++)
				{
					if (isListenerRadioButton[i].isSelected())
						if (isListenerRadioButton[i].getText().equals("是"))
						{
							isListener = true;

						} else
						{
							isListener = false;
						}

				}
				getWidgetByXmlParser.generateStatementAndBindviewVariable(activityPath, selectedMethodName, list, codingType, modifierVariable,
						isListener);
				common.showMessage("生成控件成功！");
				// 关闭当前界面
				dispose();
			} catch (Exception e)
			{
				common.showErrorMessage("生成控件失败！");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	// public static SelectMethodListInterface getInstance(String activityPath,
	// String codingType, List<RecordSelectedIndexModel> list,
	// GetWidgetByXmlParser getWidgetByXmlParser)
	// {
	//
	// if (instance == null)
	// {
	// instance = new SelectMethodListInterface(activityPath, codingType, list,
	// getWidgetByXmlParser);
	// }
	//
	// return instance;
	// }

}

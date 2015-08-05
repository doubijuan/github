package pers.edward.androidtool.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeModel;

import pers.edward.androidtool.function.GetWidgetByXmlParser;
import pers.edward.androidtool.model.FileLayoutVariableModel;
import pers.edward.androidtool.model.RecordSelectedIndexModel;
import pers.edward.androidtool.model.VariableDataModel;
import pers.edward.androidtool.tool.CommonMethod;

/**
 * 选择控件页面
 * 
 * @author Edward
 * 
 */
public class SelectWidgetVariableInterface extends JFrame
{
	private List<RecordSelectedIndexModel> list;
	private CommonMethod common;
	private static SelectWidgetVariableInterface instance = null;
	private GetWidgetByXmlParser getWidgetByXmlParser;
	private String activityPath, xmlPath, codingType;
	private JRadioButton[] modifierRadioButton;
	private JRadioButton[] isListenerRadioButton;

	public SelectWidgetVariableInterface(String activityPath, String xmlPath, String codingType, JRadioButton[] modifierRadioButton,
			JRadioButton[] isListenerRadioButton) throws Exception
	{
		this.activityPath = activityPath;
		this.xmlPath = xmlPath;
		this.codingType = codingType;
		this.modifierRadioButton = modifierRadioButton;
		this.isListenerRadioButton = isListenerRadioButton;

		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 600);
		CommonMethod.setLayoutCenter(this);
		// 固定窗体大小
		setResizable(false);
		// 设置Swing界面UI跟随系统变化
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setTitle("选择XML控件");
		common = new CommonMethod(getContentPane());

		init();
	}

	public void init() throws Exception
	{
		getWidgetByXmlParser = new GetWidgetByXmlParser();
		getWidgetByXmlParser.generateWidget(xmlPath, activityPath, codingType);

		List<FileLayoutVariableModel> list = getWidgetByXmlParser.getFileLayoutVariableList();
		generateTree(list);
	}

	/**
	 * 生成树形结构
	 */
	public void generateTree(List<FileLayoutVariableModel> getTreeList)
	{
		JTree tree = new JTree();
		CheckBoxTreeNode rootNode = new CheckBoxTreeNode("全选");
		for (int i = 0; i < getTreeList.size(); i++)
		{
			File file = new File(getTreeList.get(i).getFileName());
			CheckBoxTreeNode subTree = new CheckBoxTreeNode(file.getName() + "   路径：" + file.getPath());
			List<VariableDataModel> subList = getTreeList.get(i).getVariableList();
			for (int j = 0; j < subList.size(); j++)
			{
				CheckBoxTreeNode subTreeList = new CheckBoxTreeNode(subList.get(j).getVariableType() + "    " + subList.get(j).getVariableName());
				subTree.add(subTreeList);
			}
			rootNode.add(subTree);
		}
		DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
		tree.addMouseListener(new CheckBoxTreeNodeSelectionListener());
		tree.setModel(treeModel);
		// 用来绘制checkbox
		tree.setCellRenderer(new CheckBoxTreeCellRenderer());

		JScrollPane scroll = new JScrollPane(tree);
		scroll.setBounds(0, 0, 700, 500);
		add(scroll);

		JButton button = new JButton("确定");
		button.setBounds(10, 520, 150, 30);
		button.addActionListener(new ClickListener(treeModel));
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
		private DefaultTreeModel model;

		public ClickListener(DefaultTreeModel model)
		{
			this.model = model;
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			// TODO Auto-generated method stub
			list = new ArrayList<RecordSelectedIndexModel>();
			// 判断是否选中状态
			boolean isChoose = false;

			CheckBoxTreeNode root = (CheckBoxTreeNode) model.getRoot();
			for (int i = 0; i < root.getChildCount(); i++)
			{
				CheckBoxTreeNode second = (CheckBoxTreeNode) root.getChildAt(i);
				RecordSelectedIndexModel indexModel = new RecordSelectedIndexModel();
				indexModel.setIndex(i);
				List<Integer> indexList = new ArrayList<Integer>();
				for (int j = 0; j < second.getChildCount(); j++)
				{
					CheckBoxTreeNode third = (CheckBoxTreeNode) second.getChildAt(j);
					if (third.isSelected)
					{
						isChoose = true;
						indexList.add(j);
					}
				}
				indexModel.setSubListIndex(indexList);
				list.add(indexModel);
			}

			// 将数据输出到目标文件中
			try
			{
				if (isChoose)
				{
					new SelectMethodListInterface(activityPath, codingType, list, getWidgetByXmlParser, modifierRadioButton, isListenerRadioButton);
				
				} else
				{
					common.showErrorMessage("请选择数据！");
				}
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 获取实例
	 * 
	 * @param domainNameAndPortConstant
	 * 
	 * @param targetFilePath
	 * 
	 * @param domainNameAndPort
	 * 
	 * @param codingType
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	// public static SelectWidgetVariableInterface getInstance(String
	// activityPath, String xmlPath, String codingType) throws Exception
	// {
	//
	// if (instance == null)
	// {
	// instance = new SelectWidgetVariableInterface(activityPath, xmlPath,
	// codingType);
	// }
	//
	// return instance;
	// }

}

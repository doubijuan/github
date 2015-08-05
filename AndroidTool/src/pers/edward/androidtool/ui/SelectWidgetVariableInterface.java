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
 * ѡ��ؼ�ҳ��
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
		// �̶������С
		setResizable(false);
		// ����Swing����UI����ϵͳ�仯
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setTitle("ѡ��XML�ؼ�");
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
	 * �������νṹ
	 */
	public void generateTree(List<FileLayoutVariableModel> getTreeList)
	{
		JTree tree = new JTree();
		CheckBoxTreeNode rootNode = new CheckBoxTreeNode("ȫѡ");
		for (int i = 0; i < getTreeList.size(); i++)
		{
			File file = new File(getTreeList.get(i).getFileName());
			CheckBoxTreeNode subTree = new CheckBoxTreeNode(file.getName() + "   ·����" + file.getPath());
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
		// ��������checkbox
		tree.setCellRenderer(new CheckBoxTreeCellRenderer());

		JScrollPane scroll = new JScrollPane(tree);
		scroll.setBounds(0, 0, 700, 500);
		add(scroll);

		JButton button = new JButton("ȷ��");
		button.setBounds(10, 520, 150, 30);
		button.addActionListener(new ClickListener(treeModel));
		add(button);
	}

	/**
	 * ���ȷ���¼�
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
			// �ж��Ƿ�ѡ��״̬
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

			// �����������Ŀ���ļ���
			try
			{
				if (isChoose)
				{
					new SelectMethodListInterface(activityPath, codingType, list, getWidgetByXmlParser, modifierRadioButton, isListenerRadioButton);
				
				} else
				{
					common.showErrorMessage("��ѡ�����ݣ�");
				}
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * ��ȡʵ��
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

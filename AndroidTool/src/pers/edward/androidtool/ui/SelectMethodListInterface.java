package pers.edward.androidtool.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultTreeModel;

import pers.edward.androidtool.function.GetWidgetByXmlParser;
import pers.edward.androidtool.model.RecordSelectedIndexModel;
import pers.edward.androidtool.tool.CommonMethod;

/**
 * ѡ�񷽷��б�
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

	public SelectMethodListInterface(String activityPath, String codingType, List<RecordSelectedIndexModel> list,
			GetWidgetByXmlParser getWidgetByXmlParser)
	{
		this.codingType = codingType;
		this.activityPath = activityPath;
		this.getWidgetByXmlParser = getWidgetByXmlParser;
		this.list = list;

		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 600);
		CommonMethod.setLayoutCenter(this);
		// �̶������С
		setResizable(false);
		// ����Swing����UI����ϵͳ�仯
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
		setTitle("ѡ����Ҫ����ķ���");
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
		List<String> listMethod = getWidgetByXmlParser.getTargetFileMethodList(activityPath, codingType);
		generateTree(listMethod);
	}

	/**
	 * ����ѡ����
	 * 
	 * @param listMethod
	 */
	public void generateTree(List<String> listMethod)
	{

		JTree tree = new JTree();
		CheckBoxTreeNode rootNode = new CheckBoxTreeNode("ȫѡ");
		for (int i = 0; i < listMethod.size(); i++)
		{
			String methodName = listMethod.get(i);
			CheckBoxTreeNode childNode = new CheckBoxTreeNode(methodName.substring(0, methodName.length() - 1));
			rootNode.add(childNode);
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
		button.addActionListener(new ClickListener(treeModel, listMethod));
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
		private List<String> listMethod;
		private DefaultTreeModel model;

		public ClickListener(DefaultTreeModel model, List<String> listMethod)
		{
			this.listMethod = listMethod;
			this.model = model;
		}

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			// TODO Auto-generated method stub

			String selectedMethodName = null;

			CheckBoxTreeNode node = (CheckBoxTreeNode) model.getRoot();
			for (int i = 0; i < node.getChildCount(); i++)
			{
				CheckBoxTreeNode childNode = (CheckBoxTreeNode) node.getChildAt(i);
				if (childNode.isSelected)
				{
					selectedMethodName = listMethod.get(i);
				}
			}

			// ����ǰһ��ҳ��ѡ�е�ֵ
			try
			{
				getWidgetByXmlParser.generateStatementAndBindviewVariable(activityPath, selectedMethodName, list, codingType, "private", true);
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static SelectMethodListInterface getInstance(String activityPath, String codingType, List<RecordSelectedIndexModel> list,
			GetWidgetByXmlParser getWidgetByXmlParser)
	{

		if (instance == null)
		{
			instance = new SelectMethodListInterface(activityPath, codingType, list, getWidgetByXmlParser);
		}

		return instance;
	}

}

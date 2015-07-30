package pers.edward.androidtool.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeModel;

import pers.edward.androidtool.function.getUrlInterface;
import pers.edward.androidtool.model.NetworkUrlModel;
import pers.edward.androidtool.model.RecordSelectedIndexModel;
import pers.edward.androidtool.model.StoreSubInterfaceModel;

/**
 * ѡ��ӿڽ���
 * 
 * @author Edward
 * 
 */
public class SelectUrlInterface extends JFrame
{
	private List<RecordSelectedIndexModel> list;
	private getUrlInterface getInterface;

	public SelectUrlInterface() throws Exception
	{
		setLayout(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 600);
		setLayoutCenter();
		// �̶������С
		setResizable(false);
		// ����Swing����UI����ϵͳ�仯
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setTitle("ѡ��ӿڵ�ַ����");

		init();
	}

	public void init() throws Exception
	{
		getInterface = new getUrlInterface();
		getInterface.test("http://120.24.62.95:9202",
				"C:\\MyWorkspace\\JAVASE\\MyExercise\\AndroidTool\\src\\pers\\edward\\androidtool\\function\\TestUrl.java", "INTERNET_URL", "gbk");

		List<StoreSubInterfaceModel> getTreeList = getInterface.getStoreSubInterfaceList();
		// �������νṹ
		generateTreeList(getTreeList);

	}

	/**
	 * ���������б�
	 * 
	 * @param getTreeList
	 */
	public void generateTreeList(List<StoreSubInterfaceModel> getTreeList)
	{
		JTree tree = new JTree();
		CheckBoxTreeNode rootNode = new CheckBoxTreeNode("ȫѡ");
		for (int i = 0; i < getTreeList.size(); i++)
		{
			CheckBoxTreeNode subTree = new CheckBoxTreeNode(getTreeList.get(i).getInterfaceTitle());
			List<NetworkUrlModel> subList = getTreeList.get(i).getNetworkUrlList();
			for (int j = 0; j < subList.size(); j++)
			{
				CheckBoxTreeNode subTreeList = new CheckBoxTreeNode(subList.get(j).getUrlDescribe());
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
						indexList.add(j);
						System.out.println(third.isSelected);

					}
				}
				indexModel.setSubListIndex(indexList);
				list.add(indexModel);
			}

			getInterface.outputUrlToTargetFile(list);

		}
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
}

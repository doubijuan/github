package pers.edward.androidtool.function;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pers.edward.androidtool.model.FileLayoutVariableModel;
import pers.edward.androidtool.model.RecordSelectedIndexModel;
import pers.edward.androidtool.model.VariableDataModel;
import pers.edward.androidtool.tool.CommonMethod;

/**
 * Android�ؼ��Զ�������
 * 
 * @author Edward
 * 
 */
public class GetWidgetByXmlParser
{
	// store current file method list(�洢��ǰ�ļ��б�)
	private List<String> listMethod;

	// �洢���ֿؼ�
	private List<FileLayoutVariableModel> fileLayoutVariableList = new ArrayList<FileLayoutVariableModel>();

	public List<FileLayoutVariableModel> getFileLayoutVariableList()
	{
		return fileLayoutVariableList;
	}

	public void setFileLayoutVariableList(List<FileLayoutVariableModel> fileLayoutVariableList)
	{
		this.fileLayoutVariableList = fileLayoutVariableList;
	}

	public static void main(String[] args)
	{
		GetWidgetByXmlParser getWidgetByXmlParser = new GetWidgetByXmlParser();
		try
		{
			// C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\res\\layout\\fragment_home.xml
			// C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\src\\cn\\zhanyun\\n18client\\ApplicationActivity.java
			getWidgetByXmlParser.generateWidget("C:\\MyWorkspace\\Android\\YiHuaHotel\\MyTest\\res\\layout\\one.xml",
					"C:\\MyWorkspace\\Android\\YiHuaHotel\\MyTest\\src\\com\\example\\mytest\\_Main1.java", "utf-8");
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param xmlPath
	 *            xml�ļ�·��
	 * @param targetPath
	 *            Ŀ���ļ�·��
	 * @param methodName
	 *            ���뷽����
	 * @param codingType
	 *            ��������
	 * @throws Exception
	 */
	public void generateWidget(String xmlPath, String targetPath, String codingType) throws Exception
	{
		// �����ļ�
		parserEmbeddedXmlFile(xmlPath, codingType);
	}

	/**
	 * ���Ĵ��룬���������ؼ�������ʵ�����ؼ�����
	 * 
	 * @param result
	 * 
	 * @param targetString
	 * 
	 * @throws Exception
	 */
	public void generateStatementAndBindviewVariable(String targetFilePath, String selectedMethodName, List<RecordSelectedIndexModel> list,
			String codeType, String modifierType, boolean isAddListener) throws Exception
	{
		selectedMethodName = selectedMethodName.replace("(", "\\(*");
		selectedMethodName = selectedMethodName.replace(")", "\\)*");
		selectedMethodName = selectedMethodName.replace("{", "\\{*");

		String targetFileString = CommonMethod.fileToString(targetFilePath, codeType);

		// System.out.println(targetFileString);
		// ����ƥ����򣬸���public class XXXXX extends XXXXX {�ʹ�������ķ�������public void
		// methodTest(String a) {�������������ļ������ݷָ������
		Pattern pattern = Pattern.compile("([\\s\\S]*public\\s*class\\s*[\\w]*\\s*extends\\s*[^{]*\\{)([\\s\\S]*\\s*" + selectedMethodName
				+ ")([\\s\\S]*)");
		Matcher matcher = pattern.matcher(targetFileString);

		StringBuffer sb = new StringBuffer();

		if (matcher.find())
		{

			System.out.println(matcher.group(1));
			sb.append(matcher.group(1) + "\n");
			System.err.println("-------------------------------------------------------------------------->");
			// �����ؼ�
			generateStatementVariable(list, modifierType, sb);
			System.out.println(matcher.group(2));
			sb.append(matcher.group(2) + "\n");
			System.err.println("-------------------------------------------------------------------------->");
			// �󶨿ؼ�
			generateBindViewVariable(list, sb);
			sb.append("\n");
			// �ж��Ƿ�Ӧ����ӵ���¼�
			if (isAddListener)
			{
				addListener(list, sb);
			}
			System.out.println(matcher.group(3));
			sb.append(matcher.group(3));
		} else
		{
			System.out.println("not find for example public class XXXXX extends XXXXX {");
		}
		
		// ������д��ָ���ļ���
		CommonMethod.inputDataToTargetFile(targetFilePath, sb.toString(), codeType);
	}

	/**
	 * �󶨿ؼ�����
	 * 
	 * @param list
	 * 
	 * @param sb
	 */
	public void generateStatementVariable(List<RecordSelectedIndexModel> list, String modifierType, StringBuffer sb)
	{
		// ���� �����ؼ����롣
		for (int i = 0; i < list.size(); i++)
		{
			List<Integer> tempList = list.get(i).getSubListIndex();
			List<VariableDataModel> tempVariable = fileLayoutVariableList.get(i).getVariableList();
			for (int j = 0; j < tempList.size(); j++)
			{
				String variableTypeString = tempVariable.get(j).getVariableType();
				int result = variableTypeString.lastIndexOf(".");
				if (result != -1)
				{
					System.out.println(modifierType + " " + variableTypeString.substring(result + 1, variableTypeString.length()) + " m" + i
							+ tempVariable.get(j).getVariableName() + ";");
					sb.append(modifierType + " " + variableTypeString.substring(result + 1, variableTypeString.length()) + " m" + i
							+ tempVariable.get(j).getVariableName() + ";\n");
				} else
				{
					System.out.println(modifierType + " " + variableTypeString + " m" + i + tempVariable.get(j).getVariableName() + ";");
					sb.append(modifierType + " " + variableTypeString + " m" + i + tempVariable.get(j).getVariableName() + ";\n");
				}
			}
		}
	}

	/**
	 * ���������ؼ�����
	 * 
	 * @param list
	 * 
	 * @param sb
	 */
	public void generateBindViewVariable(List<RecordSelectedIndexModel> list, StringBuffer sb)
	{
		// ����ʵ��������
		for (int i = 0; i < list.size(); i++)
		{
			List<Integer> tempList = list.get(i).getSubListIndex();
			List<VariableDataModel> tempVariable = fileLayoutVariableList.get(i).getVariableList();
			for (int j = 0; j < tempList.size(); j++)
			{
				String variableTypeString = tempVariable.get(j).getVariableType();

				String tempType = null, tempName = null;
				// �жϱ��������Ƿ��Զ���ؼ���������Զ���ؼ����� xxx.xxx�Ĺ��򣬸��ݹ����ȡ.���һ����Ϊ�˱�����
				int result = variableTypeString.lastIndexOf(".");
				if (result != -1)
				{
					tempType = variableTypeString.substring(result + 1, variableTypeString.length());
					tempName = tempVariable.get(j).getVariableName();
				} else
				{
					tempType = variableTypeString;
					tempName = tempVariable.get(j).getVariableName();
				}
				System.out.println("m" + i + tempName + " = (" + tempType + ") findViewById(R.id." + tempName + ");");
				sb.append("m" + i + tempName + " = (" + tempType + ") findViewById(R.id." + tempName + ");\n");
			}
		}
	}

	/**
	 * Ϊ�ؼ���Ӽ����¼�
	 * 
	 * @param list
	 * 
	 * @param sb
	 */
	public void addListener(List<RecordSelectedIndexModel> list, StringBuffer sb)
	{
		for (int i = 0; i < list.size(); i++)
		{
			List<Integer> tempList = list.get(i).getSubListIndex();
			List<VariableDataModel> tempVariable = fileLayoutVariableList.get(i).getVariableList();
			for (int j = 0; j < tempList.size(); j++)
			{
				String variableTypeString = tempVariable.get(j).getVariableType();

				if (variableTypeString.equals("TextView") || variableTypeString.equals("ImageView") || variableTypeString.equals("LinearLayout")
						|| variableTypeString.equals("RelativeLayout") || variableTypeString.equals("ImageButton"))
				{
					System.out.println("m" + i + tempVariable.get(j).getVariableName() + ".setOnClickListener(this);");
					sb.append("m" + i + tempVariable.get(j).getVariableName() + ".setOnClickListener(this);\n");
				}
			}
		}
	}

	/**
	 * ��ȡָ���ļ�·���ķ����б�
	 * 
	 * @param targetFilepPath
	 */
	public List<String> getTargetFileMethodList(String targetFilepPath, String codingType) throws Exception
	{
		String result = CommonMethod.fileToString(targetFilepPath, codingType);
		listMethod = new ArrayList<String>();

		// ����ָ��������ȡĿ���ļ������б�
		Pattern pattern = Pattern.compile("([public|private|protected]*\\s+[\\w]+\\s+[\\w]+\\([^)]*\\)\\s*\\{)");
		Matcher matcher = pattern.matcher(result);

		while (matcher.find())
		{
			listMethod.add(matcher.group(1).trim());
			// String temp=matcher.group(1).trim();
		}

		return listMethod;
	}

	/**
	 * ��ȡ�Ӳ����ļ���
	 * 
	 * @param regex
	 *            ƥ�����
	 * @param result
	 *            ��Ҫƥ����ַ���
	 * @param suffix
	 *            ƴ���ļ���׺
	 * @param isDeleRepet
	 *            �Ƿ��޳��ظ�
	 * @return
	 */
	public List<String> getChildLayout(String regex, String result, String suffix, boolean isDeleRepet)
	{
		List<String> list = new ArrayList<String>();

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(result);

		while (matcher.find())
		{
			System.err.println("�Ӳ����ļ���" + matcher.group(1) + suffix);
			list.add(matcher.group(1) + suffix);
		}

		if (isDeleRepet)
		{
			// �޳��ظ�xml�ļ�
			Set<String> set = new HashSet<String>(list);
			list.clear();
			list.addAll(set);
		}

		return list;

	}

	/**
	 * ���Ĵ��룬�Ȱ���ǶXML�ļ���ȡ������
	 * 
	 * @param result
	 */
	/**
	 * @param path
	 */
	public void parserEmbeddedXmlFile(String path, String codingType)
	{
		if (path.isEmpty())
		{
			System.out.println("xml file path is null!!!(xml�ļ�Ϊ��)");
			return;
		}

		Queue<String> queue = new LinkedList<String>();
		File file = new File(path);
		if (file.exists())
		{
			// ����һ���ļ���ջ
			queue.add(path);
		} else
		{
			System.err.println("input file path error!(�����ļ�·������)");
			return;
		}

		// ������в�Ϊ��
		while (!queue.isEmpty())
		{
			// �����ǰXML�ļ�·��
			String currentPath = queue.poll();

			// ���ڻ�ȡ��ǰ·���ĸ�·��
			file = new File(currentPath);

			// ����·���ļ�ת��ΪString
			String fileResultString = CommonMethod.fileToString(currentPath, codingType);
			// System.err.println(fileResultString);

			// �������ļ�
			searchChildFileLayout(queue, file, fileResultString);

			System.out.println(currentPath);

			// �洢�����ļ���·��
			FileLayoutVariableModel model = new FileLayoutVariableModel();
			model.setFileName(currentPath);
			// ���洢�������б�洢����
			model.setVariableList(getVariableTypeAndNameList(fileResultString));

			fileLayoutVariableList.add(model);
		}

	}

	/**
	 * �˺��������������ļ�����
	 * 
	 * @param queue
	 * 
	 * @param file
	 * 
	 * @param fileResultString
	 */
	public void searchChildFileLayout(Queue<String> queue, File file, String fileResultString)
	{
		// �˹���������ȡ�Ӳ����ļ�
		Pattern pattern = Pattern.compile("layout=\"@layout/([^\"]*)");
		Matcher matcher = pattern.matcher(fileResultString);

		// Ѱ�Ҵ�XML�ļ����ж��ٸ����ļ�
		while (matcher.find())
		{
			String childFilePath = file.getParent() + "\\" + matcher.group(1) + ".xml";

			file = new File(childFilePath);
			// ����ļ��Ѵ��ڣ�������������
			if (file.exists())
			{
				queue.add(childFilePath);
			} else
			{
				System.err.println("subfile inexistence!!!(���ļ�������)");
			}
			// System.out.println(file.getParent() + "\\" + matcher.group(1)
			// + ".xml");
		}
	}

	/**
	 * ��ȡ�������ͺ������б�
	 */
	public List<VariableDataModel> getVariableTypeAndNameList(String fileResultString)
	{
		// <([\\w|.]*)\\s*.*\\s*android:id=\"@\\+id/([^\"]*)\"
		// �˴��㷨��Ҫ�Ż�-------------------------------------------------------------------------------------------------------------->
		Pattern pattern = Pattern.compile("<([\\w|.]*)\\s*.*\\s*android:id=\"@\\+id/([^\"]*)\"");
		Matcher matcher = pattern.matcher(fileResultString);

		List<VariableDataModel> tempList = new ArrayList<VariableDataModel>();
		while (matcher.find())
		{
			VariableDataModel variableDataModel = new VariableDataModel();
			variableDataModel.setVariableType(matcher.group(1));
			variableDataModel.setVariableName(matcher.group(2));
			tempList.add(variableDataModel);
			System.err.println(matcher.group(1) + "       " + matcher.group(2));
		}

		return tempList;
	}

}

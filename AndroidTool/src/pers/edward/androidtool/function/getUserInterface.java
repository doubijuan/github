package pers.edward.androidtool.function;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pers.edward.androidtool.tool.CommonMethod;

/**
 * ����Activity�ļ�
 * 
 * @author Edward
 * 
 */
public class getUserInterface
{

	public static void main(String[] args) throws Exception
	{

		getUserInterface main = new getUserInterface();

		List<String> list = new ArrayList<String>();
		list.add("C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\res\\layout\\activity_guide.xml");
		list.add("C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\res\\layout\\activity_ordersure.xml");
		list.add("C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\res\\layout\\activity_product.xml");
		// C:\\MyWorkspace\\Android\\YiHuaHotel\\YiHuaHotel\\AndroidManifest.xml
		main.test("Fragment", "C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\src\\cn\\zhanyun\\n18client", list,
				"C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\AndroidManifest.xml", "utf-8");
	}

	/**
	 * @param extendsClassStr
	 *            ���û�ָ���̳���
	 * @param activityFolderPath
	 *            activity�ļ���·��
	 * @param xmlPathStr
	 *            ���û�ָ��xml·��
	 * @param configFilePath
	 *            ���û�ָ�������ļ�·��
	 */
	public void test(String extendsClassStr, String activityFolderPath, List<String> xmlPathStrList, String configFilePath, String codingType)
	{
		// System.out.println(extendsClassStr + "    " + activityFolderPath +
		// "     " + xmlPathStrList.get(0) + "        " + configFilePath +
		// "       "
		// + configFilePath);

		// �����ļ�
		List<String> uiName = writeDataToFile(activityFolderPath, xmlPathStrList, extendsClassStr, codingType);

		// ����̳�����Fragment������������Ϣ
		if (!extendsClassStr.equals("Fragment"))
		{
			// ƴ��������Ϣ
			jointConfigInfo(activityFolderPath, uiName, configFilePath, codingType);
		}
	}

	/**
	 * �����ļ�
	 * 
	 * @param activityFolderPath
	 * 
	 * @param xmlPathStrList
	 * 
	 * @return
	 */
	public List<String> writeDataToFile(String activityFolderPath, List<String> xmlPathStrList, String extendsClassStr, String codingType)
	{
		List<String> uiNameList = new ArrayList<String>();

		for (int i = 0; i < xmlPathStrList.size(); i++)
		{
			String getXmlPath = xmlPathStrList.get(i);
			System.out.println("xml·����" + getXmlPath);
			String uiName = getXmlPath.substring(getXmlPath.lastIndexOf("\\") + 1, getXmlPath.lastIndexOf("."));
			uiNameList.add(uiName);
			System.out.println("�������ƣ�" + uiName);

			String createFilePath = activityFolderPath + "\\" + uiName + ".java";
			System.err.println("�����ļ�·����" + createFilePath);
			// �����ļ�
			if (!createFile(createFilePath, activityFolderPath, extendsClassStr, uiName, codingType))
			{
				System.err.println("����ʧ�ܣ�");
				return null;
			}
		}

		return uiNameList;
	}

	/**
	 * ƴ��������Ϣ
	 * 
	 * @param activityFolderPath
	 * 
	 * @param uiName
	 * 
	 * @param configFilePath
	 * 
	 * @param codingType
	 */
	public void jointConfigInfo(String activityFolderPath, List<String> uiName, String configFilePath, String codingType)
	{
		// ��ȡ����·��
		activityFolderPath = getPackageNamePath(activityFolderPath);

		String result = CommonMethod.fileToString(configFilePath, codingType);
		Pattern pattern = Pattern.compile("([\\s\\S]*<application\\s*[^>]*>)([\\s\\S]*)");

		Matcher matcher = pattern.matcher(result);

		StringBuffer sb = new StringBuffer();
		if (matcher.find())
		{
			sb.append(matcher.group(1) + "\n");
			sb.append("<!-- android����������  -->\n");

			for (int i = 0; i < uiName.size(); i++)
			{
				sb.append("<activity android:name=\"" + activityFolderPath + "." + uiName.get(i) + "\" />\n");
			}
			sb.append(matcher.group(2));
		}

		CommonMethod.inputDataToTargetFile(configFilePath, sb.toString(), codingType);
		System.out.println(sb.toString());
	}

	/**
	 * ��ȡ����·��
	 * 
	 * @param activityFolderPath
	 * 
	 * @return
	 */
	public String getPackageNamePath(String activityFolderPath)
	{
		// ��ȡsrcĿ¼���·��
		activityFolderPath = activityFolderPath.substring(activityFolderPath.indexOf("src") + 4, activityFolderPath.length());
		// ����б���滻Ϊ.
		activityFolderPath = activityFolderPath.replace("\\", ".");
		System.err.println("activity����·��" + activityFolderPath);

		return activityFolderPath;
	}

	/**
	 * �����ļ�����������ɹ�����true
	 * 
	 * @param filePath
	 * 
	 * @return
	 */
	public boolean createFile(String filePath, String activityFolderPath, String extendsClassStr, String uiName, String codingType)
	{
		File file = new File(filePath);
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
				if (extendsClassStr.equals("Activity") || extendsClassStr.equals("FragmentActivity") || extendsClassStr.equals("Fragment"))
				{
					jointData(file, activityFolderPath, extendsClassStr, uiName, codingType);
				} else
				{

				}
				return true;
			} catch (IOException e)
			{

				// TODO Auto-generated catch block
				System.err.println("�ļ�����ʧ�ܣ�");
				e.printStackTrace();
				return false;
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				System.err.println("�ļ�д��ʧ�ܣ�");
				e.printStackTrace();
				return false;
			}
		} else
		{
			System.err.println("���ļ��Ѵ��ڣ�" + file.getName());
		}
		return false;
	}

	/**
	 * ƴ������
	 * 
	 * @param file
	 * 
	 * @param activityFolderPath
	 * 
	 * @param extendsClassStr
	 * 
	 * @param uiName
	 * 
	 * @param codingType
	 * 
	 * @throws Exception
	 */
	public void jointData(File file, String activityFolderPath, String extendsClassStr, String uiName, String codingType) throws Exception
	{
		String packageNamePath = getPackageNamePath(activityFolderPath);

		StringBuffer content = new StringBuffer();
		// �������
		content.append("package " + packageNamePath + ";\n\n");

		// �����
		content.append("import android.os.Bundle;\n");
		if (extendsClassStr.equals("Activity"))
			content.append("import android.app.Activity;\n\n");

		if (extendsClassStr.equals("FragmentActivity"))
			content.append("import android.support.v4.app.FragmentActivity;\n\n");

		if (extendsClassStr.equals("Fragment"))
		{
			content.append("import android.support.v4.app.Fragment;\n");
			content.append("import android.view.LayoutInflater;\n");
			content.append("import android.view.View;\n");
			content.append("import android.view.ViewGroup;\n\n");
		}

		content.append("public class " + uiName + " extends " + extendsClassStr + "{\n");
		content.append("@Override\n");
		content.append("public void onCreate(Bundle savedInstanceState) {\n");
		content.append("// TODO Auto-generated method stub\n");
		content.append("super.onCreate(savedInstanceState);\n");

		if (!extendsClassStr.equals("Fragment"))
			content.append("setContentView(R.layout." + uiName + ");");
		content.append("\n}\n");

		if (extendsClassStr.equals("Fragment"))
		{
			content.append("@Override\n");
			content.append("public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {\n");
			content.append("// TODO Auto-generated method stub\n");
			content.append("return super.onCreateView(inflater, container, savedInstanceState);\n");
			content.append("}\n");
		}

		content.append("}\n");

		System.out.println("д�����ݣ�" + content.toString());
		CommonMethod.inputDataToTargetFile(file.getPath(), content.toString(), codingType);
	}

}

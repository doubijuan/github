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
	// ����·������������·��
	private String packageNamePath;
	// ʹ��XML�ļ�����Ϊjava�ļ���
	private String activityName;
	// ����java�ļ�·��
	private String generateActivityPath;
	// д���ļ��ı�������
	private String encodingType = "gbk";
	// �����ļ���·����ƴ�������ļ�·��
	private String configFileNamePath;

	public static void main(String[] args) throws Exception
	{

		getUserInterface main = new getUserInterface();

		List<String> list = new ArrayList<String>();
		list.add("C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\res\\layout\\activity_guide.xml");
		list.add("C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\res\\layout\\activity_ordersure.xml");
		list.add("C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\res\\layout\\activity_product.xml");
		// C:\\MyWorkspace\\Android\\YiHuaHotel\\YiHuaHotel\\AndroidManifest.xml
		main.test("Activity", "C:\\MyWorkspace\\Android\\YiHuaHotel\\N18Client\\src\\cn\\zhanyun\\n18client", list,
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
		// �����ļ�
		List<String> uiName = writeDataToFile(activityFolderPath, xmlPathStrList, extendsClassStr, codingType);

		// ƴ��������Ϣ
		jointConfigInfo(activityFolderPath, uiName, configFilePath, codingType);

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
				jointData(file, activityFolderPath, extendsClassStr, uiName, codingType);
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
		content.append("package " + packageNamePath + ";\n\n");
		content.append("import android.app.Activity;\n");
		content.append("import android.os.Bundle;\n\n");
		content.append("public class " + uiName + " extends " + extendsClassStr + "{\n");
		content.append("@Override\n");
		content.append("protected void onCreate(Bundle savedInstanceState) {\n");
		content.append("// TODO Auto-generated method stub\n");
		content.append("super.onCreate(savedInstanceState);\n setContentView(R.layout." + uiName + ");\n}");
		content.append("}\n");

		System.out.println("д�����ݣ�" + content.toString());
		CommonMethod.inputDataToTargetFile(file.getPath(), content.toString(), codingType);
	}

}

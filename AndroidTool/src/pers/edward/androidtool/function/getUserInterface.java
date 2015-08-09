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
 * 生成Activity文件
 * 
 * @author Edward
 * 
 */
public class getUserInterface
{
	// 根据路径解析出包名路径
	private String packageNamePath;
	// 使用XML文件名作为java文件名
	private String activityName;
	// 生成java文件路径
	private String generateActivityPath;
	// 写入文件的编码类型
	private String encodingType = "gbk";
	// 根据文件夹路径，拼接配置文件路径
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
	 *            由用户指定继承类
	 * @param activityFolderPath
	 *            activity文件夹路径
	 * @param xmlPathStr
	 *            由用户指定xml路径
	 * @param configFilePath
	 *            由用户指定配置文件路径
	 */
	public void test(String extendsClassStr, String activityFolderPath, List<String> xmlPathStrList, String configFilePath, String codingType)
	{
		// 创建文件
		List<String> uiName = writeDataToFile(activityFolderPath, xmlPathStrList, extendsClassStr, codingType);

		// 拼接配置信息
		jointConfigInfo(activityFolderPath, uiName, configFilePath, codingType);

	}

	/**
	 * 创建文件
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
			System.out.println("xml路径：" + getXmlPath);
			String uiName = getXmlPath.substring(getXmlPath.lastIndexOf("\\") + 1, getXmlPath.lastIndexOf("."));
			uiNameList.add(uiName);
			System.out.println("界面名称：" + uiName);

			String createFilePath = activityFolderPath + "\\" + uiName + ".java";
			System.err.println("创建文件路径：" + createFilePath);
			// 创建文件
			if (!createFile(createFilePath, activityFolderPath, extendsClassStr, uiName, codingType))
			{
				System.err.println("创建失败！");
				return null;
			}
		}

		return uiNameList;
	}

	/**
	 * 拼接配置信息
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
		// 获取包名路径
		activityFolderPath = getPackageNamePath(activityFolderPath);

		String result = CommonMethod.fileToString(configFilePath, codingType);
		Pattern pattern = Pattern.compile("([\\s\\S]*<application\\s*[^>]*>)([\\s\\S]*)");

		Matcher matcher = pattern.matcher(result);

		StringBuffer sb = new StringBuffer();
		if (matcher.find())
		{
			sb.append(matcher.group(1) + "\n");
			sb.append("<!-- android代码生成器  -->\n");

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
	 * 获取包名路径
	 * 
	 * @param activityFolderPath
	 * 
	 * @return
	 */
	public String getPackageNamePath(String activityFolderPath)
	{
		// 提取src目录后的路径
		activityFolderPath = activityFolderPath.substring(activityFolderPath.indexOf("src") + 4, activityFolderPath.length());
		// 将反斜杠替换为.
		activityFolderPath = activityFolderPath.replace("\\", ".");
		System.err.println("activity包的路径" + activityFolderPath);

		return activityFolderPath;
	}

	/**
	 * 创建文件，如果创建成功返回true
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
				System.err.println("文件创建失败！");
				e.printStackTrace();
				return false;
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				System.err.println("文件写入失败！");
				e.printStackTrace();
				return false;
			}
		} else
		{
			System.err.println("此文件已存在：" + file.getName());
		}
		return false;
	}

	/**
	 * 拼接数据
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

		System.out.println("写入数据：" + content.toString());
		CommonMethod.inputDataToTargetFile(file.getPath(), content.toString(), codingType);
	}

}

package pers.edward.androidtool.function;

import java.io.File;
import java.io.IOException;

import pers.edward.androidtool.tool.CommonMethod;

/**
 * 生成Activity文件
 * 
 * @author Edward
 * 
 */
public class getActivity {
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

	public static void main(String[] args) throws Exception {

		getActivity main = new getActivity();

		main.test("Activity", "C:\\MyWorkspace\\Android\\YiHuaHotel\\MyTest\\src\\com\\example\\mytest",
				"C:\\MyWorkspace\\Android\\YiHuaHotel\\MyTest\\res\\layout\\item.xml",
				"C:\\MyWorkspace\\Android\\YiHuaHotel\\MyTest\\AndroidManifest.xml");
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
	public void test(String extendsClassStr, String activityFolderPath, String xmlPathStr, String configFilePath) {
		int isRes = activityFolderPath.indexOf("\\src\\");
		if (isRes == -1) {
			System.err.println("解析包名路径出错！");
			return;
		}
		activityName = xmlPathStr.substring(xmlPathStr.lastIndexOf("\\") + 1, xmlPathStr.length());
		activityName = activityName.substring(0, activityName.lastIndexOf("."));
		System.out.println("生成Activity的名称：" + activityName);

		packageNamePath = activityFolderPath.substring(activityFolderPath.indexOf("\\src\\") + 5, activityFolderPath.length());

		configFileNamePath = packageNamePath.replace("\\", ".") + "." + activityName;
		System.out.println("配置文件name的路径：" + configFileNamePath);

		packageNamePath = "package " + packageNamePath.replace("\\", ".") + ";";
		System.out.println("类的包名：" + packageNamePath);

		generateActivityPath = activityFolderPath + "\\" + activityName + ".java";
		System.out.println("生成java文件路径：" + generateActivityPath);

		File file = new File(generateActivityPath);
		if (!file.exists()) {
			try {
				// 创建一个新的java文件
				file.createNewFile();
				insertData(extendsClassStr, file.getPath(), xmlPathStr, configFilePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("生成文件失败！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.err.println("文件已存在");
			return;
		}
	}

	/**
	 * 将需要生成的数据写入文件中
	 * 
	 * @param extendsClassStr
	 * @param newFilePath
	 */
	public void insertData(String extendsClassStr, String newFilePath, String xmlPathStr, String configFilePath) throws Exception {

		StringBuffer insertContent = new StringBuffer();
		insertContent.append(packageNamePath);
		insertContent.append("public class " + activityName + " extends " + extendsClassStr + "{\n");
		insertContent.append("@Override\n");
		insertContent.append("protected void onCreate(Bundle savedInstanceState) {\n");
		insertContent.append("// TODO Auto-generated method stub\n");
		insertContent.append("super.onCreate(savedInstanceState);\n setContentView(R.layout." + activityName + ");\n}");
		insertContent.append("}");

		CommonMethod.insertContentToFile(newFilePath, insertContent.toString(), encodingType, 0);

		generateConfigFile(configFilePath);
	}

	/**
	 * 生成配置文件
	 */
	public void generateConfigFile(String configFilePath) throws Exception {
		// 获取需要插入点
		long temp = CommonMethod.seekInsertLocation(configFilePath, "</application>");

		String insertContent = "<activity\n" + "android:name=\"" + configFileNamePath + "\"/>";
		CommonMethod.insertContentToFile(configFilePath, insertContent, encodingType, temp);
	}
}

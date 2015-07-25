package pers.edward.androidtool.function;

import java.io.File;
import java.io.IOException;

import pers.edward.androidtool.tool.CommonMethod;

/**
 * ����Activity�ļ�
 * 
 * @author Edward
 * 
 */
public class getActivity {
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

	public static void main(String[] args) throws Exception {

		getActivity main = new getActivity();

		main.test("Activity", "C:\\MyWorkspace\\Android\\YiHuaHotel\\MyTest\\src\\com\\example\\mytest",
				"C:\\MyWorkspace\\Android\\YiHuaHotel\\MyTest\\res\\layout\\item.xml",
				"C:\\MyWorkspace\\Android\\YiHuaHotel\\MyTest\\AndroidManifest.xml");
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
	public void test(String extendsClassStr, String activityFolderPath, String xmlPathStr, String configFilePath) {
		int isRes = activityFolderPath.indexOf("\\src\\");
		if (isRes == -1) {
			System.err.println("��������·������");
			return;
		}
		activityName = xmlPathStr.substring(xmlPathStr.lastIndexOf("\\") + 1, xmlPathStr.length());
		activityName = activityName.substring(0, activityName.lastIndexOf("."));
		System.out.println("����Activity�����ƣ�" + activityName);

		packageNamePath = activityFolderPath.substring(activityFolderPath.indexOf("\\src\\") + 5, activityFolderPath.length());

		configFileNamePath = packageNamePath.replace("\\", ".") + "." + activityName;
		System.out.println("�����ļ�name��·����" + configFileNamePath);

		packageNamePath = "package " + packageNamePath.replace("\\", ".") + ";";
		System.out.println("��İ�����" + packageNamePath);

		generateActivityPath = activityFolderPath + "\\" + activityName + ".java";
		System.out.println("����java�ļ�·����" + generateActivityPath);

		File file = new File(generateActivityPath);
		if (!file.exists()) {
			try {
				// ����һ���µ�java�ļ�
				file.createNewFile();
				insertData(extendsClassStr, file.getPath(), xmlPathStr, configFilePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("�����ļ�ʧ�ܣ�");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.err.println("�ļ��Ѵ���");
			return;
		}
	}

	/**
	 * ����Ҫ���ɵ�����д���ļ���
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
	 * ���������ļ�
	 */
	public void generateConfigFile(String configFilePath) throws Exception {
		// ��ȡ��Ҫ�����
		long temp = CommonMethod.seekInsertLocation(configFilePath, "</application>");

		String insertContent = "<activity\n" + "android:name=\"" + configFileNamePath + "\"/>";
		CommonMethod.insertContentToFile(configFilePath, insertContent, encodingType, temp);
	}
}

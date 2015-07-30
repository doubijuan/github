package pers.edward.androidtool.function;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pers.edward.androidtool.tool.CommonMethod;

/**
 * �Զ������ֶ�
 * 
 * @author Edward DisplaySearchHotelModel
 * 
 *         ���´�������� "_imageurl":
 *         "/Upload/Shop/Images/Product/20150723/201507231458423648003.jpg,/Upload/Shop/Images/Product/20150723/201507231612457422028.jpg"
 *         , "_addeddate": "/Date(1437633478000+0800)/",
 */
public class GetGenerateModel {
	// �Ƿ�ѭ������
	private boolean isBracket = false;

	public static void main(String[] args) throws Exception {

		GetGenerateModel model = new GetGenerateModel();
		model.test("F:\\GetAllOrderByUserID.json", "C:\\MyWorkspace\\Android\\YunYiPurchase\\YunYiGou\\src\\com\\zhanyun\\yunyigou\\model", "Test2",
				"utf-8");
	}

	public void test(String parseFileName, String genertaeFilePath, String fileName, String encodingType) throws Exception {
		String temp = parseFileName.substring(parseFileName.length() - 4, parseFileName.length());
		// �ж�������ļ��Ƿ���.json��׺
		if (!temp.equals("json")) {
			parseFileName += ".json";
		}
		System.err.println("�����ļ�����" + parseFileName);

		// �����ļ�·��
		String packageName = genertaeFilePath.substring(genertaeFilePath.lastIndexOf("\\src\\") + 5, genertaeFilePath.length());
		// ���ɰ���
		packageName = packageName.replace("\\", ".");
		System.err.println("���������" + packageName);

		// �ж��Ƿ��к�׺Model
		if (fileName.length() <= 4) {
			fileName += "Model";
		} else {
			temp = fileName.substring(fileName.length() - 5, fileName.length());
			if (!temp.equals("Model")) {
				fileName += "Model";
			}
		}
		System.err.println("Model�ļ�����" + fileName);

		String string = CommonMethod.fileToString(parseFileName);
		// System.out.println(string);
		String modelFilePath = genertaeFilePath + "\\" + fileName + ".java";
		System.err.println("Model�ļ�·����" + modelFilePath);

		List<String> list = parserJsonFile(string);

		// geterateDomainNameData(list, modelFilePath, fileName, packageName,
		// encodingType);
	}

	/**
	 * ������������
	 * 
	 * @param modelFilePath
	 * @throws Exception
	 */
	public void geterateDomainNameData(List<String> list, String modelFilePath, String fileName, String packageName, String encodingType)
			throws Exception {
		// �Զ������ļ�
		File file = new File(modelFilePath);
		if (!file.exists()) {
			file.createNewFile();
			RandomAccessFile rf = new RandomAccessFile(file.getPath().toString(), "rw");
			// д�����
			rf.write(("package " + packageName + ";").getBytes(encodingType));
			// �����
			rf.write("import java.io.Serializable;".getBytes(encodingType));
			// ���JSON�ļ��������������飬��ִ�д˲���
			if (isBracket) {
				rf.write("import java.util.ArrayList;".getBytes(encodingType));
			}
			// д���������Ҽ̳�Serializable�ӿ�
			rf.write(("public class " + fileName + " implements Serializable{").getBytes(encodingType));
			// �����ֶ��б�
			for (int i = 0; i < list.size(); i++) {
				String temp5 = "private String " + list.get(i) + ";";
				rf.write(temp5.getBytes(encodingType));
			}

			// д��get��set����
			for (int i = 0; i < list.size(); i++) {
				rf.write(("public String get" + list.get(i) + "(){").getBytes(encodingType));
				rf.write(("return " + list.get(i) + ";}").getBytes(encodingType));

				rf.write(("public void set" + list.get(i) + "(String " + list.get(i) + "){").getBytes(encodingType));
				rf.write(("this." + list.get(i) + "=" + list.get(i) + ";}").getBytes(encodingType));
			}

			// ��ȡ����ResultModel
			String resultModel = fileName.substring(0, fileName.indexOf("Model"));
			rf.write(("public class " + resultModel + "ResultModel {").getBytes(encodingType));
			if (isBracket) {
				rf.write(("private ArrayList<" + fileName + "> Result;").getBytes(encodingType));
				rf.write(("public ArrayList<" + fileName + "> getResult(){").getBytes(encodingType));
				rf.write("return Result;}".getBytes(encodingType));

				rf.write(("public void setResult(ArrayList<" + fileName + "> result){").getBytes(encodingType));
				rf.write("Result=result;}}".getBytes(encodingType));
			} else {
				rf.write(("private " + fileName + " Result;").getBytes(encodingType));
				rf.write(("public " + fileName + " getResult(){").getBytes(encodingType));
				rf.write("return Result;}".getBytes(encodingType));

				rf.write(("public void setResult(" + fileName + " result){").getBytes(encodingType));
				rf.write("Result=result;}}".getBytes(encodingType));
			}
			rf.write("}".getBytes(encodingType));
			rf.close();
		} else
			System.err.println("����ʧ�ܣ��ļ��Ѵ���");
	}

	/**
	 * ����json�ļ�
	 * 
	 * @param result
	 * @return
	 */
	public List<String> parserJsonFile(String result) {
		System.out.println(result);
		
		
		
		
		
		return null;
		
		
		
//		// �ж�JSON�Ƿ�һ������
//		if (result.indexOf("[") != -1) {
//			isBracket = true;
//		} else
//			isBracket = false;
//
//		result = result.substring(result.indexOf("Result"), result.length());
//		result = result.substring(result.indexOf("{"), result.indexOf("}") + 1);
//		System.err.println("�ļ����ݣ�" + result);

//		Pattern patten = Pattern.compile("\"([^\":]*)\":");
//
//		Matcher matcher = patten.matcher(result);
//
//		List<String> fieldNameList = new ArrayList<String>();
//		while (matcher.find()) {
//			System.out.println(matcher.group(1));
//			fieldNameList.add(matcher.group(1));
//		}

//		return fieldNameList;
	}

}

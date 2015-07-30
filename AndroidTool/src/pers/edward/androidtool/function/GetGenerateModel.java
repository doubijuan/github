package pers.edward.androidtool.function;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pers.edward.androidtool.tool.CommonMethod;

/**
 * 自动生成字段
 * 
 * @author Edward DisplaySearchHotelModel
 * 
 *         导致错误的数据 "_imageurl":
 *         "/Upload/Shop/Images/Product/20150723/201507231458423648003.jpg,/Upload/Shop/Images/Product/20150723/201507231612457422028.jpg"
 *         , "_addeddate": "/Date(1437633478000+0800)/",
 */
public class GetGenerateModel {
	// 是否循环数组
	private boolean isBracket = false;

	public static void main(String[] args) throws Exception {

		GetGenerateModel model = new GetGenerateModel();
		model.test("F:\\GetAllOrderByUserID.json", "C:\\MyWorkspace\\Android\\YunYiPurchase\\YunYiGou\\src\\com\\zhanyun\\yunyigou\\model", "Test2",
				"utf-8");
	}

	public void test(String parseFileName, String genertaeFilePath, String fileName, String encodingType) throws Exception {
		String temp = parseFileName.substring(parseFileName.length() - 4, parseFileName.length());
		// 判断输入的文件是否有.json后缀
		if (!temp.equals("json")) {
			parseFileName += ".json";
		}
		System.err.println("解析文件名：" + parseFileName);

		// 生成文件路径
		String packageName = genertaeFilePath.substring(genertaeFilePath.lastIndexOf("\\src\\") + 5, genertaeFilePath.length());
		// 生成包名
		packageName = packageName.replace("\\", ".");
		System.err.println("程序包名：" + packageName);

		// 判断是否有后缀Model
		if (fileName.length() <= 4) {
			fileName += "Model";
		} else {
			temp = fileName.substring(fileName.length() - 5, fileName.length());
			if (!temp.equals("Model")) {
				fileName += "Model";
			}
		}
		System.err.println("Model文件名：" + fileName);

		String string = CommonMethod.fileToString(parseFileName);
		// System.out.println(string);
		String modelFilePath = genertaeFilePath + "\\" + fileName + ".java";
		System.err.println("Model文件路径：" + modelFilePath);

		List<String> list = parserJsonFile(string);

		// geterateDomainNameData(list, modelFilePath, fileName, packageName,
		// encodingType);
	}

	/**
	 * 生成域名数据
	 * 
	 * @param modelFilePath
	 * @throws Exception
	 */
	public void geterateDomainNameData(List<String> list, String modelFilePath, String fileName, String packageName, String encodingType)
			throws Exception {
		// 自动创建文件
		File file = new File(modelFilePath);
		if (!file.exists()) {
			file.createNewFile();
			RandomAccessFile rf = new RandomAccessFile(file.getPath().toString(), "rw");
			// 写入包名
			rf.write(("package " + packageName + ";").getBytes(encodingType));
			// 导入包
			rf.write("import java.io.Serializable;".getBytes(encodingType));
			// 如果JSON文件传过来的是数组，则执行此步骤
			if (isBracket) {
				rf.write("import java.util.ArrayList;".getBytes(encodingType));
			}
			// 写入类名并且继承Serializable接口
			rf.write(("public class " + fileName + " implements Serializable{").getBytes(encodingType));
			// 声明字段列表
			for (int i = 0; i < list.size(); i++) {
				String temp5 = "private String " + list.get(i) + ";";
				rf.write(temp5.getBytes(encodingType));
			}

			// 写入get和set方法
			for (int i = 0; i < list.size(); i++) {
				rf.write(("public String get" + list.get(i) + "(){").getBytes(encodingType));
				rf.write(("return " + list.get(i) + ";}").getBytes(encodingType));

				rf.write(("public void set" + list.get(i) + "(String " + list.get(i) + "){").getBytes(encodingType));
				rf.write(("this." + list.get(i) + "=" + list.get(i) + ";}").getBytes(encodingType));
			}

			// 截取子类ResultModel
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
			System.err.println("生成失败，文件已存在");
	}

	/**
	 * 解析json文件
	 * 
	 * @param result
	 * @return
	 */
	public List<String> parserJsonFile(String result) {
		System.out.println(result);
		
		
		
		
		
		return null;
		
		
		
//		// 判断JSON是否一个数组
//		if (result.indexOf("[") != -1) {
//			isBracket = true;
//		} else
//			isBracket = false;
//
//		result = result.substring(result.indexOf("Result"), result.length());
//		result = result.substring(result.indexOf("{"), result.indexOf("}") + 1);
//		System.err.println("文件内容：" + result);

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

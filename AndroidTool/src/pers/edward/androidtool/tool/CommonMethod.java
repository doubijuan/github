package pers.edward.androidtool.tool;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import cpdetector.io.ASCIIDetector;
import cpdetector.io.CodepageDetectorProxy;
import cpdetector.io.JChardetFacade;
import cpdetector.io.ParsingDetector;
import cpdetector.io.UnicodeDetector;

/**
 * 公共方法类
 * 
 * @author Edward
 * 
 */
public class CommonMethod
{
	private Container container;

	public CommonMethod()
	{

	}

	public CommonMethod(Container container)
	{
		this.container = container;
	}

	/**
	 * 文件选择器
	 * 
	 * @param container
	 *            容器
	 * @param label
	 *            显示文件路径
	 * @param fileterType
	 *            过滤的文件类型
	 */
	public void fileChooice(JLabel label, String fileterType)
	{
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setAcceptAllFileFilterUsed(false);
		FileFilterChooser fileFilter = new FileFilterChooser(fileterType);
		jfc.setFileFilter(fileFilter);
		int value = jfc.showDialog(new JLabel(), "选择文件");
		// 如果点击了选择文件，则继续进行处理
		if (value == JFileChooser.APPROVE_OPTION)
		{
			File file = jfc.getSelectedFile();
			if (file.exists())
			{
				System.out.println(file.getPath());
				label.setText(file.getPath());
			} else
			{
				JOptionPane.showMessageDialog(container, "选择文件无效！", "系统信息", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * 文件过滤器
	 * 
	 * @author Edward
	 * 
	 */
	public class FileFilterChooser extends FileFilter
	{
		private String fileterType;

		public FileFilterChooser(String filterType)
		{
			this.fileterType = filterType;
		}

		@Override
		public boolean accept(File arg0)
		{
			// TODO Auto-generated method stub
			if (arg0.isDirectory())
				return true;
			return arg0.getName().endsWith(fileterType);
		}

		@Override
		public String getDescription()
		{
			// TODO Auto-generated method stub
			return fileterType;
		}

	}

	/**
	 * 显示信息
	 * 
	 * @param string
	 */
	public void showMessage(String string)
	{
		JOptionPane.showMessageDialog(container, string, "系统信息", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * 错误显示信息
	 * 
	 * @param string
	 */
	public void showErrorMessage(String string)
	{
		JOptionPane.showMessageDialog(container, string, "系统信息", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * 将文件转换为字符串
	 * 
	 * @param path
	 *            文件路径
	 * @return 字符串
	 */
	public static String fileToString(String path)
	{
		return fileToString(path, "utf-8");
	}

	public static String fileToString(String path, String codingType)
	{
		FileInputStream fis;
		BufferedInputStream bis;
		ByteArrayOutputStream bos;
		try
		{
			fis = new FileInputStream(path);
			bis = new BufferedInputStream(fis);
			bos = new ByteArrayOutputStream();

			int length = 0;
			byte[] by = new byte[1024];
			while ((length = bis.read(by)) != -1)
			{
				bos.write(by, 0, length);
			}
			bos.flush();
			String result = new String(bos.toByteArray(), codingType);

			bos.close();
			bis.close();
			fis.close();

			return result;
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			System.err.println("没有找到此文件！");
			return null;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			System.err.println("文件转换字符串失败！");
			return null;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			System.err.println("获取文件编码失败！");
			return null;
		}
	}

	/**
	 * 获取字符串插入点坐标
	 * 
	 * @param path
	 *            寻找插入位置的文件路径
	 * @param insertStr
	 *            插入点字符串
	 * @return 返回插入点的坐标
	 */
	public static long seekInsertLocation(String path, String insertStr)
	{

		String result = fileToString(path);
		long index = result.indexOf(insertStr);

		return index;

	}

	/**
	 * 获取字符串插入点坐标
	 * 
	 * @param path
	 *            寻找插入位置的文件路径
	 * @param insertStr
	 *            插入点字符串
	 * @param index
	 *            往插入点前进或后退index
	 * @return
	 */
	public static long seekInsertLocation(String path, String insertStr, int index)
	{

		String result = fileToString(path);
		long temp = result.indexOf(insertStr) + index;

		return temp;

	}

	/**
	 * 插入内容
	 * 
	 * @param path
	 *            插入文件路径
	 * @param insertContent
	 *            插入内容
	 * @param codeType
	 *            插入文件编码类型
	 * @param position
	 *            插入文件坐标点
	 */
	public static boolean insertContentToFile(String path, String insertContent, String codeType, long position)
	{
		try
		{
			File file = File.createTempFile("tempFile", null);
			file.deleteOnExit();
			RandomAccessFile rf = new RandomAccessFile(path, "rw");
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(file);

			rf.seek(position);

			byte[] by = new byte[1024];
			int length = 0;
			// 将插入点之后的数据写入缓存中
			while ((length = rf.read(by)) > 0)
			{
				fos.write(by);
			}

			// 将内容插入指定坐标之中
			rf.seek(position);
			rf.write(insertContent.getBytes(codeType));

			// 将缓存中的数据追加到插入数据的后面
			while ((length = fis.read(by)) > 0)
			{
				rf.write(by, 0, length);
			}

			rf.close();
			fis.close();
			fos.close();

			return true;

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			System.err.println("插入内容失败！");
			return false;
		}

	}

	public static void main(String[] args)
	{
		CommonMethod method = new CommonMethod();
		// String result = method.fileToString("F:\\HealthShopList2.json");
		// System.out.println(result);

		// try {
		// System.err.println(method.getCodeType("F:\\新建文本文档.txt"));
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// try {
		// System.out.println(method.codeString("F:\\HealthShopList2.json"));
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	/**
	 * 获取编码类型
	 * 
	 * @param path
	 * @return
	 */
	public static String getCodeType(String path) throws Exception
	{
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		// detector.add(new ParsingDetector(false));
		detector.add(JChardetFacade.getInstance());
		// ASCIIDetector用于ASCII编码测定
		detector.add(ASCIIDetector.getInstance());
		// UnicodeDetector用于Unicode家族编码的测定
		detector.add(UnicodeDetector.getInstance());
		File f = new File(path);

		Charset charset = null;
		charset = detector.detectCodepage(f.toURL());

		if (charset != null)
		{
			// System.out.println(f.getName() + "编码是：" + charset.name());
			return charset.name();
		} else
		{
			// System.out.println(f.getName() + "未知");
			return null;
		}

	}

	public static Matcher PatternAndMatcher(String rule, String result)
	{
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(result);

		return matcher;
	}

	/**
	 * 设置布局居中
	 */
	public static void setLayoutCenter(JFrame frame)
	{
		int windowWidth = frame.getWidth(); // 获得窗口宽
		int windowHeight = frame.getHeight(); // 获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width; // 获取屏幕的宽
		int screenHeight = screenSize.height; // 获取屏幕的高
		frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);
	}
}

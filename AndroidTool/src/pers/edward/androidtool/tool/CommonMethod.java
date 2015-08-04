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
 * ����������
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
	 * �ļ�ѡ����
	 * 
	 * @param container
	 *            ����
	 * @param label
	 *            ��ʾ�ļ�·��
	 * @param fileterType
	 *            ���˵��ļ�����
	 */
	public void fileChooice(JLabel label, String fileterType)
	{
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.setAcceptAllFileFilterUsed(false);
		FileFilterChooser fileFilter = new FileFilterChooser(fileterType);
		jfc.setFileFilter(fileFilter);
		int value = jfc.showDialog(new JLabel(), "ѡ���ļ�");
		// ��������ѡ���ļ�����������д���
		if (value == JFileChooser.APPROVE_OPTION)
		{
			File file = jfc.getSelectedFile();
			if (file.exists())
			{
				System.out.println(file.getPath());
				label.setText(file.getPath());
			} else
			{
				JOptionPane.showMessageDialog(container, "ѡ���ļ���Ч��", "ϵͳ��Ϣ", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * �ļ�������
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
	 * ��ʾ��Ϣ
	 * 
	 * @param string
	 */
	public void showMessage(String string)
	{
		JOptionPane.showMessageDialog(container, string, "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * ������ʾ��Ϣ
	 * 
	 * @param string
	 */
	public void showErrorMessage(String string)
	{
		JOptionPane.showMessageDialog(container, string, "ϵͳ��Ϣ", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * ���ļ�ת��Ϊ�ַ���
	 * 
	 * @param path
	 *            �ļ�·��
	 * @return �ַ���
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
			System.err.println("û���ҵ����ļ���");
			return null;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			System.err.println("�ļ�ת���ַ���ʧ�ܣ�");
			return null;
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			System.err.println("��ȡ�ļ�����ʧ�ܣ�");
			return null;
		}
	}

	/**
	 * ��ȡ�ַ������������
	 * 
	 * @param path
	 *            Ѱ�Ҳ���λ�õ��ļ�·��
	 * @param insertStr
	 *            ������ַ���
	 * @return ���ز���������
	 */
	public static long seekInsertLocation(String path, String insertStr)
	{

		String result = fileToString(path);
		long index = result.indexOf(insertStr);

		return index;

	}

	/**
	 * ��ȡ�ַ������������
	 * 
	 * @param path
	 *            Ѱ�Ҳ���λ�õ��ļ�·��
	 * @param insertStr
	 *            ������ַ���
	 * @param index
	 *            �������ǰ�������index
	 * @return
	 */
	public static long seekInsertLocation(String path, String insertStr, int index)
	{

		String result = fileToString(path);
		long temp = result.indexOf(insertStr) + index;

		return temp;

	}

	/**
	 * ��������
	 * 
	 * @param path
	 *            �����ļ�·��
	 * @param insertContent
	 *            ��������
	 * @param codeType
	 *            �����ļ���������
	 * @param position
	 *            �����ļ������
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
			// �������֮�������д�뻺����
			while ((length = rf.read(by)) > 0)
			{
				fos.write(by);
			}

			// �����ݲ���ָ������֮��
			rf.seek(position);
			rf.write(insertContent.getBytes(codeType));

			// �������е�����׷�ӵ��������ݵĺ���
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
			System.err.println("��������ʧ�ܣ�");
			return false;
		}

	}

	public static void main(String[] args)
	{
		CommonMethod method = new CommonMethod();
		// String result = method.fileToString("F:\\HealthShopList2.json");
		// System.out.println(result);

		// try {
		// System.err.println(method.getCodeType("F:\\�½��ı��ĵ�.txt"));
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
	 * ��ȡ��������
	 * 
	 * @param path
	 * @return
	 */
	public static String getCodeType(String path) throws Exception
	{
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		// detector.add(new ParsingDetector(false));
		detector.add(JChardetFacade.getInstance());
		// ASCIIDetector����ASCII����ⶨ
		detector.add(ASCIIDetector.getInstance());
		// UnicodeDetector����Unicode�������Ĳⶨ
		detector.add(UnicodeDetector.getInstance());
		File f = new File(path);

		Charset charset = null;
		charset = detector.detectCodepage(f.toURL());

		if (charset != null)
		{
			// System.out.println(f.getName() + "�����ǣ�" + charset.name());
			return charset.name();
		} else
		{
			// System.out.println(f.getName() + "δ֪");
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
	 * ���ò��־���
	 */
	public static void setLayoutCenter(JFrame frame)
	{
		int windowWidth = frame.getWidth(); // ��ô��ڿ�
		int windowHeight = frame.getHeight(); // ��ô��ڸ�
		Toolkit kit = Toolkit.getDefaultToolkit(); // ���幤�߰�
		Dimension screenSize = kit.getScreenSize(); // ��ȡ��Ļ�ĳߴ�
		int screenWidth = screenSize.width; // ��ȡ��Ļ�Ŀ�
		int screenHeight = screenSize.height; // ��ȡ��Ļ�ĸ�
		frame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);
	}
}

package org.zzmfish.TVBrowser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.util.Log;

public class Bookmarks {
	private final String FILE_NAME = "bookmarks.xml";
	private static Bookmarks mInstance = new Bookmarks();
	private List<Bookmark> mBookmarkList = new ArrayList<Bookmark>();
	
	public static Bookmarks getInstance() 	{
		return mInstance;
	}
	
	public class Bookmark {
		String mName;
		String mUrl;
		
		public Bookmark(String name, String url) {
			mName = name;
			mUrl = url;
		}
		public String getName() {
			return mName;
		}
		public String getUrl() {
			return mUrl;
		}
	}

	
	public void add(String name, String url) {
		mBookmarkList.add(new Bookmark(name, url));
	}
	
	public void save(Context context) {
		try {
			//创建文档
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			//创建根节点
			Element root = document.createElement("Bookmarks");
			//创建书签节点
			for (int i = 0; i < mBookmarkList.size(); i ++) {
				Bookmark bookmark = mBookmarkList.get(i);
				Element node = document.createElement("Bookmark");
				node.setAttribute("name", bookmark.getName());
				node.setAttribute("url", bookmark.getUrl());
				root.appendChild(node);
			}
			//添加节点到文档
			document.appendChild(root);
			//保存到文件
			TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            FileOutputStream file = context.openFileOutput(FILE_NAME, 0);
            PrintWriter pw = new PrintWriter(file);
            StreamResult result = new StreamResult(pw);
            transformer.transform(source, result);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load(Context context) {
		try {
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
			FileInputStream file;
			file = context.openFileInput(FILE_NAME);
			Document document = db.parse(file);
			NodeList nodeList = document.getElementsByTagName("Bookmark");
			mBookmarkList.clear();
			for (int i = 0; i < nodeList.getLength(); i ++) {
				Node node = nodeList.item(i);
				try {
					String name = node.getAttributes().getNamedItem("name").getNodeValue();
					String url = node.getAttributes().getNamedItem("url").getNodeValue();
					add(name, url);
					//Log.d("zhouzm", "name=" + name + ", url=" + url);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Bookmark get(int i) {
		return mBookmarkList.get(i);
	}
	
	public int count() {
		return mBookmarkList.size();
	}
}

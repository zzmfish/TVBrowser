package org.zzmfish.TVBrowser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import android.content.Context;

public class Bookmarks {
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
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Element root = document.createElement("Bookmarks");
			for (int i = 0; i < mBookmarkList.size(); i ++) {
				Bookmark bookmark = mBookmarkList.get(i);
				Element node = document.createElement("Bookmark");
				node.setAttribute("name", bookmark.getName());
				node.setAttribute("url", bookmark.getUrl());
				root.appendChild(node);
			}
			document.appendChild(root);
			TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            FileOutputStream file;
			file = context.openFileOutput("bookmarks.xml", 0);
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
}

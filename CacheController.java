package application;

import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.util.Callback;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
public class CacheController implements Initializable {
	    
		@FXML
		Button get;
		@FXML
		Button clear;
		@FXML
		Button save;
		@FXML
		TextArea text;
		
		public String myDocPath;
		public Clipboard c;
		
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			 myDocPath=getDocPath();
			 c=Toolkit.getDefaultToolkit().getSystemClipboard();		//clipboard reference
			 text.setText("");
			 try {
				 
				    File file = new File(myDocPath+"\\javatext");
				    boolean dirCreated = file.mkdir();
				    int i;
				    for(i=1;i<6;i++) {
				    	File fileTXT = new File(myDocPath+"\\javatext\\cache_"+i+".txt");
				    	fileTXT.createNewFile();//initialize files
				    }
				    updateText(getCache());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			 Timer timer = new Timer();
			 timer.schedule(new Demo(), 0, 200);//0.02 Min 1.2sec
		}
		
		class Demo extends TimerTask {
		      public void run() {

					try {
						updateText(getCache());
					} catch (Exception e) {
						e.printStackTrace();
					}
			
		       }
		  }
		
		@FXML
		public void save(ActionEvent e)throws Exception {//clipboard to cache
    
		    String fileContent = ""+c.getData(DataFlavor.stringFlavor);
		    BufferedWriter writer = new BufferedWriter(new FileWriter(myDocPath+"\\javatext\\cache_"+Controller.acviteCache+".txt"));
		    writer.write(fileContent);
		    writer.close();
		    
		    updateText(getCache());
		    //put
		    
		    URL url = new URL("http://margasiewicz.pythonanywhere.com/jazda/"+Controller.acviteCache);
		    HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		    httpCon.setDoOutput(true);
		    httpCon.setRequestMethod("PUT");
		    httpCon.setRequestProperty("Content-Type","application/json; charset-UTF-8");
		    OutputStreamWriter out = new OutputStreamWriter(
		    httpCon.getOutputStream());
		    
		    
		    Gson gson = new Gson();
		    msg obj = new msg();
		    obj.msg = fileContent;
		    String json = gson.toJson(obj);
		    out.write(json);
		    
		    
		    out.close();
		    httpCon.getInputStream();
		}
		
		@FXML
		public void setClipboard(ActionEvent e)throws Exception {//cache to clipboard
		    
			  String data=getCache();
			  StringSelection selection = new StringSelection(data);
			  c.setContents(selection, selection);
		}
		
		@FXML
		public String getCache() throws Exception{//get cache
		    
			  File file = new File(myDocPath+"\\javatext\\cache_"+Controller.acviteCache+".txt"); 
			  BufferedReader br = new BufferedReader(new FileReader(file)); 
			  String st;
			  String data="";
			  while ((st = br.readLine()) != null) 
			    data=""+data+st+"\n"; 
			  
return data;
		}
		
		@FXML
		public void clear(ActionEvent e)throws Exception {//cache clear

			File file = new File(myDocPath+"\\javatext");
		    boolean dirCreated = file.mkdir();
		    String fileContent = "";
		    BufferedWriter writer = new BufferedWriter(new FileWriter(myDocPath+"\\javatext\\cache_"+Controller.acviteCache+".txt"));
		    writer.write(fileContent);
		    writer.close();
		    	updateText(getCache());
		}
		
		public String getDocPath() {//myDocuments - contains path to documents on windows
			String myDocuments=null;
		    try {
		        Process p =  Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
		        p.waitFor();

		        InputStream in = p.getInputStream();
		        byte[] b = new byte[in.available()];
		        in.read(b);
		        in.close();

		        myDocuments = new String(b);
		        myDocuments = myDocuments.split("\\s\\s+")[4];

		    } catch(Throwable t) {
		        t.printStackTrace();
		    }
		    return myDocuments;
		}
		
		public void updateText(String data) {
		    	text.setText(""+data);
		}
		
		
		}

class msg {
	String msg;
	
}


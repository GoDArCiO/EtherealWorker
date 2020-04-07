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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import javafx.util.Callback;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.omg.CORBA.portable.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Controller implements Initializable {
    
	@FXML
	Button c1;
	@FXML
	Button c2;
	@FXML
	Button c3;
	@FXML
	Button c4;
	@FXML
	Button c5;
	@FXML
	Button log;
	@FXML
	Button sig;
	@FXML AnchorPane holderPane;
	AnchorPane home;
	public static String myDocPath;
	public static int acviteCache=1;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		createPage();
		myDocPath=getDocPath();
	}
	
	private void setNode(Node node) {
		holderPane.getChildren().clear();
		holderPane.getChildren().add((Node) node);
	}
	
	private void createPage() {
		try {
			home = FXMLLoader.load(getClass().getResource("scene.fxml"));
			setNode(home);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void loadCache1(ActionEvent e)throws Exception {
		acviteCache=1;
		createPage();
	}
	@FXML
	public void loadCache2(ActionEvent e)throws Exception {
		acviteCache=2;
		createPage();
	}
	@FXML
	public void loadCache3(ActionEvent e)throws Exception {
		acviteCache=3;
		createPage();
	}
	@FXML
	public void loadCache4(ActionEvent e)throws Exception {
		acviteCache=4;
		createPage();
	}
	@FXML
	public void loadCache5(ActionEvent e)throws Exception {
		acviteCache=5;
		createPage();
	}
	
	@FXML
	public void openLogin(ActionEvent e)throws Exception {
		Parent root;
        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = new Stage();
        stage.setTitle("LoginBox");
        stage.setScene(new Scene(root, 200, 80));
        stage.show();
        stage.setAlwaysOnTop(true);
	}
	@FXML
	public void sync(ActionEvent e)throws Exception {
		syncdown(null);
	}
	

	public static void syncdown(String[] args) throws IOException {
		sendGET();
	}

	private static void sendGET() throws IOException {
		URL obj = new URL("http://margasiewicz.pythonanywhere.com/jazda/"+acviteCache);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		//System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			Gson gson = new Gson();
			msg mytext = gson.fromJson(response.toString(), msg.class);
	        
		    BufferedWriter writer = new BufferedWriter(new FileWriter(myDocPath+"\\javatext\\cache_"+acviteCache+".txt"));
		    writer.write(mytext.getMsg());
		    writer.close();

		    //CacheController.updateText(mytext.getMsg());

		} else {
			System.out.println("GET request not worked");
		}

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
	
	
	class msg {
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		int id;
		String msg;
		
	}
	
	}










	 



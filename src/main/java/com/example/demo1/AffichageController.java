package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AffichageController implements Initializable {

    @FXML
    private TableView<Vehicule> tv_categorie;
    @FXML
    private TableColumn<Vehicule, String> col_name;
    @FXML
    private TableColumn<Vehicule, String> col_model;
    @FXML
    private TableColumn<Vehicule, String> col_manuf;
    @FXML
    private TextField tf_recherche;
    @FXML
    private Text txt_name,txt_model,txt_manufact,txt_passengers,txt_vehicle_class;

    private String base_url = "https://swapi.dev/api/";
    @FXML
    private Button close;
    @FXML
    private void onClose(){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showResultat();
    }
    @FXML
    private void ActionKeyPressed(KeyEvent event){
        if (event.getSource()==tf_recherche){
            showRecherche(tf_recherche.getText());
        }else if (tf_recherche.getText()==""){
            showResultat();
        }
    }
    //Affichage izy rehetra
    private void showResultat() {
        ObservableList<Vehicule> list = getListe();

        col_name.setCellValueFactory(new PropertyValueFactory<Vehicule, String>("name"));
        col_model.setCellValueFactory(new PropertyValueFactory<Vehicule, String>("model"));
        col_manuf.setCellValueFactory(new PropertyValueFactory<Vehicule, String>("manufacturer"));

        tv_categorie.setItems(list);
    }
    private ObservableList<Vehicule> getListe(){
        ObservableList<Vehicule> etList = FXCollections.observableArrayList();
        try{
            URL url = new URL(base_url+"vehicles/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode !=200){
                throw new RuntimeException("HttpResponseCode: "+responseCode);
            }else {
                StringBuilder stringBuilder = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()){
                    stringBuilder.append(scanner.nextLine());
                }
                scanner.close();
                String result = stringBuilder.toString();
                Vehicule cl_et;
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject)parser.parse(result);
                JSONArray jarr = (JSONArray)obj.get("results");
                for(int i = 0;i<jarr.size();i++){
                    cl_et = new Vehicule(((JSONObject)jarr.get(i)).get("name").toString(),((JSONObject)jarr.get(i)).get("model").toString(),((JSONObject)jarr.get(i)).get("manufacturer").toString(),((JSONObject)jarr.get(i)).get("passengers").toString(),((JSONObject)jarr.get(i)).get("vehicle_class").toString());
                    etList.add(cl_et);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return etList;
    }

    //Affichage recherche
    private ObservableList<Vehicule> getRecherche(String search){
        ObservableList<Vehicule> etList = FXCollections.observableArrayList();
        try{
            URL url = new URL(base_url+"vehicles/?search="+search);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            if (responseCode !=200){
                throw new RuntimeException("HttpResponseCode: "+responseCode);
            }else {
                StringBuilder stringBuilder = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()){
                    stringBuilder.append(scanner.nextLine());
                }
                scanner.close();
                String result = stringBuilder.toString();
                Vehicule cl_et;
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject)parser.parse(result);
                JSONArray jarr = (JSONArray)obj.get("results");
                if (jarr == null){
                    cl_et = new Vehicule("","","","","");
                    etList.add(cl_et);
                }else {
                    for(int i = 0;i<jarr.size();i++){
                        cl_et = new Vehicule(((JSONObject)jarr.get(i)).get("name").toString(),((JSONObject)jarr.get(i)).get("model").toString(),((JSONObject)jarr.get(i)).get("manufacturer").toString(),((JSONObject)jarr.get(i)).get("passengers").toString(),((JSONObject)jarr.get(i)).get("vehicle_class").toString());
                        etList.add(cl_et);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return etList;
    }
    private void showRecherche(String search){
        ObservableList<Vehicule> list = getRecherche(search);

        col_name.setCellValueFactory(new PropertyValueFactory<Vehicule, String>("name"));
        col_model.setCellValueFactory(new PropertyValueFactory<Vehicule, String>("model"));
        col_manuf.setCellValueFactory(new PropertyValueFactory<Vehicule, String>("manufacturer"));

        tv_categorie.setItems(list);
    }

    @FXML
    private void ItemClicked(MouseEvent event) {
        Vehicule vehicule = tv_categorie.getSelectionModel().getSelectedItem();
        txt_name.setText("Name : "+vehicule.getName());
        txt_model.setText("Model : "+vehicule.getModel());
        txt_manufact.setText("Manufacturer : "+vehicule.getManufacturer());
        txt_passengers.setText("Passengers : "+vehicule.getPassengers());
        txt_vehicle_class.setText("Vehicle_Class : "+vehicule.getVehicle_class());
    }
}

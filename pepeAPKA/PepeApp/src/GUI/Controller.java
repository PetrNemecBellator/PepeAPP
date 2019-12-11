package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //about jmeno prijmeni
    //barevny obrazec
    private boolean wasUsed = false;
    @FXML
    private ImageView pictureN2;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView picture;

    final FileChooser fileChooser = new FileChooser();
    @FXML
    private RadioButton originalImageTogle;
    @FXML
    private  RadioButton modifiedImageTogle;

    private Image originalImage;
    private Image modifiedImage;

    private ToggleGroup tougleGroupImge = new ToggleGroup();
    public void tougleGroupInit(){
        if (!wasUsed ){
            wasUsed= !wasUsed;
            tougleGroupImge.getToggles().add(originalImageTogle);
        tougleGroupImge.getToggles().add(modifiedImageTogle);
        originalImageTogle.setDisable(false);
        modifiedImageTogle.setDisable(false);
            tougleGroupImge.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
                {
                    public void changed(ObservableValue<? extends Toggle> ob, Toggle o, Toggle n) {
                        RadioButton rb = (RadioButton)tougleGroupImge.getSelectedToggle();
                        if (rb != null) {
                            if(rb.getText().equals(originalImageTogle.getText())){
                                setBouthImages(originalImage);
                            }else {
                                setBouthImages(modifiedImage);

                            }
                        }
                }
            });
        }
    }

    @FXML
    public void exit() {
        System.exit(0);
    }
    //original image non original

    @FXML
    public void loadImage() throws Exception {
        tougleGroupInit();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Images", "*.jpeg", "*.jpg", "*.png");
        //fileChooser.setSelectedExtensionFilter(extFilter);
        originalImageTogle.setSelected(true);


        File file = fileChooser.showOpenDialog(new Stage());
        Image image = new Image(new FileInputStream(file));
        originalImage = image;

        setBouthImages(image);


    }

    private void setBouthImages(Image image){
        picture.setImage(image);
        pictureN2.setImage(image);
    }

    @FXML
    public void saveImage() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.png");
        //fileChooser.setSelectedExtensionFilter(extFilter);
        Image image = picture.getImage();
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void generateImage(){
        tougleGroupInit();
        BufferedImage buffImg =  makeColoredImage();
        picture.setImage(null);
        pictureN2.setImage(null);
        picture.setImage(SwingFXUtils.toFXImage(buffImg , null ));
        pictureN2.setImage(picture.getImage());
        modifiedImage = picture.getImage();
    }

    @FXML
    public void menuAboutAction(){
        try{
            about();
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }
    }
    public void about() throws Exception {
        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
        s.setScene(new Scene(root, 800, 700));
        s.show();
    }

    public BufferedImage makeColoredImage(){
        BufferedImage bImage = new BufferedImage(600, 600, BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 1; x < bImage.getWidth(); x++){
            for (int y = 1; y < bImage.getHeight(); y++){
                bImage.setRGB(x, y, (new Color((((y/x) + x*3) %255) ,(y*y)%255,(x+y)%(255)).getRGB()));
            }
        }
        return bImage;
    }
    /*private BufferedImage getInvertedImage(){
        try {

          BufferedImage  filteredImage = new BufferedImage(originalImage.getWidth(),
                    originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int x = 0; x < originalImage.getWidth(); x++){
                for (int y = 0; y < originalImage.getHeight(); y++){
                    int rgbOrig = originalImage.getRGB(x, y);
                    Color c = new Color(rgbOrig);
                    int r = 255 - c.getRed();
                    int g = 255 - c.getGreen();
                    int b = 255 - c.getBlue();
                    Color nc = new Color(r,g,b);
                    filteredImage.setRGB(x,y,nc.getRGB());
                }
            }
            return filteredImage;
        } catch (Exception e){
               }

    }*/
    private void setOfactionsForImageChange()
    {
        tougleGroupInit();
        picture.setImage(null);
        pictureN2.setImage(null);
    }

    private void imageInversion(){
       setOfactionsForImageChange();
        BufferedImage buffImg =  makeColoredImage();
        picture.setImage(null);
        pictureN2.setImage(null);
        picture.setImage(SwingFXUtils.toFXImage(buffImg , null ));
        pictureN2.setImage(picture.getImage());
        modifiedImage = picture.getImage();
    }
    @FXML
    private void inversion(ActionEvent actionEvent) {
        System.out.print("vykresluji");
    }
}

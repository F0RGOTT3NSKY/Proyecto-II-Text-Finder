package Application;

import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Clase para establecer y obtener las imagenes
 */
public class Images {
	private ImageView ImageView;
	private Image Image;
	/**
	 * Metodo para obtener una Image ya establecida
	 * @return Image
	 */
	public Image getImage() {
		return Image;
	}
	/**
	 * Metodo para establecer una Image 
	 * @param Image
	 * @param Type
	 * @throws FileNotFoundException
	 */
	public void setImage(Images Image, ImageType Type) throws FileNotFoundException {
		if(Type == ImageType.Icon) {
			Image imagenN = new Image("/Images/ICONimage.png");       
			this.Image = imagenN;
		}else if(Type == ImageType.And) {
			Image imagenN = new Image("/Images/ANDimage.png");       
			this.Image = imagenN;
		}else if(Type == ImageType.Exnor) {
			Image imagenN = new Image("/Images/EXNORimage.png");       
			this.Image = imagenN;
		}else if(Type == ImageType.Exor) {
			Image imagenN = new Image("/Images/EXORimage.png");       
			this.Image = imagenN;
		}else if(Type == ImageType.Nand) {
			Image imagenN = new Image("/Images/NANDimage.png");       
			this.Image = imagenN;
		}else if(Type == ImageType.Nor) {
			Image imagenN = new Image("/Images/NORimage.png");       
			this.Image = imagenN;
		}else if(Type == ImageType.Not) {
			Image imagenN = new Image("/Images/NOTimage.png");       
			this.Image = imagenN;
		}else if(Type == ImageType.Or) {
			Image imagenN = new Image("/Images/ORimage.png");       
			this.Image = imagenN;
		}
	}
	/**
	 * Metodo para obtener una ImageView ya establecida
	 * @return ImageView
	 */
	public ImageView getImageView() {
		return ImageView;
	}
	/**
	 * Metodo para establecer una ImageView
	 * @param ImageView
	 * @param Type
	 * @throws FileNotFoundException
	 */
	public void setImageView(Images ImageView, ImageType Type) throws FileNotFoundException {
		if(Type == ImageType.About) {
			Image imagenN = new Image("/Images/ABOUTimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.Add) {
			Image imagenN = new Image("/Images/ADDimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.And) {
			Image imagenN = new Image("/Images/ANDimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.Clear) {
			Image imagenN = new Image("/Images/CLEARimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.Delete) {
			Image imagenN = new Image("/Images/DELETEimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.ExitSave) {
			Image imagenN = new Image("/Images/EXITSAVEimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.Exnor) {
			Image imagenN = new Image("/Images/EXNORimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.Exor) {
			Image imagenN = new Image("/Images/EXORimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.File) {
			Image imagenN = new Image("/Images/FILEimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.Help) {
			Image imagenN = new Image("/Images/HELPimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.Nand) {
			Image imagenN = new Image("/Images/NANDimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.Nor) {
			Image imagenN = new Image("/Images/NORimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.Not) {
			Image imagenN = new Image("/Images/NOTimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.Or) {
			Image imagenN = new Image("/Images/ORimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.Run) {
			Image imagenN = new Image("/Images/RUNimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.Save) {
			Image imagenN = new Image("/Images/SAVEimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}else if(Type == ImageType.Wiki) {
			Image imagenN = new Image("/Images/WIKIimage.png");       
			ImageView imageViewN = new ImageView(imagenN);
			this.ImageView = imageViewN;
		}
	}
}
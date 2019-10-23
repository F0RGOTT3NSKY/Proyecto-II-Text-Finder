package Application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BImageView {
    private ImageView ImageView;
    private String ImageDirectory;

    public BImageView(String ImageDirectory) {
    	Image ImageN = new Image(ImageDirectory);
		ImageView ImageViewN = new ImageView(ImageN);
		this.ImageView = ImageViewN;
        this.ImageDirectory = ImageDirectory;
    }
	public ImageView getImageView() {
		return ImageView;
	}
}

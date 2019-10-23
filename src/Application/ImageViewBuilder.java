package Application;
/**
 * Builder para ImageView
 */
public class ImageViewBuilder {
	private String ImageDirectory = "";
    public ImageViewBuilder() {
    }
	public ImageViewBuilder setImageDirectory(String ImageDirectory) {
		if(ImageDirectory == ImageType.pdf.toString()) {
			ImageDirectory = ("/Images/PDFimage.png");
			System.out.println("set ImageDirectory to:"+ImageDirectory);
			this.ImageDirectory = ImageDirectory;
		}else if(ImageDirectory == ImageType.txt.toString()) {
			ImageDirectory = ("/Images/TXTimage.png");
			System.out.println("set ImageDirectory to:"+ImageDirectory);
			this.ImageDirectory = ImageDirectory;
		}else if(ImageDirectory == ImageType.docx.toString()) {
			ImageDirectory = ("/Images/DOCXimage.png");
			System.out.println("set ImageDirectory to:"+ImageDirectory);
			this.ImageDirectory = ImageDirectory;
		}
		return this;
	}
	public BImageView build() {
        return new BImageView(ImageDirectory);
    }
}
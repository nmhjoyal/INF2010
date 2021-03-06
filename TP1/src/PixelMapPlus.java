/**
 * Classe PixelMapPlus
 * Image de type noir et blanc, tons de gris ou couleurs
 * Peut lire et ecrire des fichiers PNM
 * Implemente les methodes de ImageOperations
 * @author : 
 * @date   : 
 */

public class PixelMapPlus extends PixelMap implements ImageOperations 
{
	/**
	 * Constructeur creant l'image a partir d'un fichier
	 * @param fileName : Nom du fichier image
	 */
	PixelMapPlus(String fileName)
	{
		super( fileName );
	}
	
	/**
	 * Constructeur copie
	 * @param image : source
	 */
	PixelMapPlus(PixelMap image)
	{
		super(image); 
	}
	
	/**
	 * Constructeur copie (sert a changer de format)
	 * @param type : type de l'image a creer (BW/Gray/Color)
	 * @param image : source
	 */
	PixelMapPlus(ImageType type, PixelMap image)
	{
		super(type, image); 
	}
	
	/**
	 * Constructeur servant a allouer la memoire de l'image
	 * @param type : type d'image (BW/Gray/Color)
	 * @param h : hauteur (height) de l'image 
	 * @param w : largeur (width) de l'image
	 */
	PixelMapPlus(ImageType type, int h, int w)
	{
		super(type, h, w);
	}
	
	/**
	 * Genere le negatif d'une image
	 */
	public void negate()
	{
		// compl�ter
		for(int i = 0; i < this.height; i++) {
			for(int j = 0 ; j < this.width; j++) {
				imageData[i][j]=imageData[i][j].Negative();
			}
		}
	}
	
	/**
	 * Convertit l'image vers une image en noir et blanc
	 */
	public void convertToBWImage()
	{
		// compl�ter
		this.imageData=this.toBWImage().imageData;
	}
	
	/**
	 * Convertit l'image vers un format de tons de gris
	 */
	public void convertToGrayImage()
	{
		// compl�ter
		this.imageData=this.toGrayImage().imageData;
	}
	
	/**
	 * Convertit l'image vers une image en couleurs
	 */
	public void convertToColorImage()
	{
		// compl�ter
		this.imageData=this.toColorImage().imageData;
	}
	
	public void convertToTransparentImage()
	{
		// compl�ter
		this.imageData=this.toTransparentImage().imageData;
	}
	
	
	/**
	 * Modifie la longueur et la largeur de l'image 
	 * @param w : nouvelle largeur
	 * @param h : nouvelle hauteur
	 */
	public void resize(int w, int h) throws IllegalArgumentException
	{
		if(w < 0 || h < 0)
			throw new IllegalArgumentException();
		
		// compl�ter
		if(w>this.width){
			// n est le facteur d'aggrandissement de l'image
			int n = w/this.width;
			PixelMapPlus test = new PixelMapPlus(imageType,h,w);
			 
			//Copier les valeurs 
			for (int i=0;i<h;i++){
				for(int y=0;y<n;y++){
					for (int j=0;j<w;j++){
						for (int x=0;x<n;x++){
							test.imageData[i+y][j+x]=imageData[i][j];
						}
					}
				}
			}
			height=h;
			width=w;
			imageData=test.imageData;
		}
		if(w<this.width){
			// n est le facteur de retressisement de l'image
			int n = this.width/w;
			PixelMapPlus test = new PixelMapPlus(imageType,h,w);
			for (int i=0;i<h;i++){
				for (int j=0;j<w;j++){
					test.imageData[i][j]=imageData[n*i][n*j];
				}
			}
		height = h;
		width = w;
		imageData = test.imageData;
		}
	}
	
	/**
	 * Insert pm dans l'image a la position row0 col0
	 */
	public void insert(PixelMap pm, int row0, int col0)
	{
		// compl�ter
		int indexRow = 0;
		int indexCol = 0;
		for(int i = row0; i < (pm.height + row0); i++) {
			indexCol=0;
			for(int j = col0; j < (pm.width + col0); j++) {
				if(i < this.height && j < this.width && indexRow < pm.height && indexCol < pm.width) {
					this.imageData[i][j] = pm.imageData[indexRow][indexCol++];
				}
			}
			indexRow++;
		}
	}
	
	/**
	 * Decoupe l'image 
	 */
	public void crop(int h, int w)
	{
		// compl�ter
		PixelMap pm = new PixelMap(this.imageType, h, w);
		
		for(int i = 0; i < h; i++) {
			for(int j = 0 ; j < w; j++) {
				if(j > this.width || i > this.height) {
					switch(this.imageType) {
					case BW:
						pm.imageData[i][j] = new BWPixel();
						break;
					case Color:
						pm.imageData[i][j] = new ColorPixel();
						break;
					case Gray:
						pm.imageData[i][j] = new GrayPixel();
						break;
					case Transparent:
						pm.imageData[i][j] = new TransparentPixel();
						break;
					default:
						break;
					} 
				}else {
					pm.imageData[i][j] = this.imageData[i][j];
				}
			}
		}
		this.height = h;
		this.width = w;
		this.imageData = pm.imageData;
	}
	
	/**
	 * Effectue une translation de l'image 
	 */
	public void translate(int rowOffset, int colOffset)
	{
		// compl�ter
		for(int i = (rowOffset*-1); i < (this.height -  rowOffset); i++) {
			for(int j = (colOffset*-1) ; j < (this.width - colOffset); j++) {
				if (j > this.width || i > this.height || i < 0 || j < 0) {
					switch (this.imageType) {
						case BW:
							this.imageData[i][j] = new BWPixel();
							break;
						case Color:
							this.imageData[i][j] = new ColorPixel();
							break;
						case Gray:
							this.imageData[i][j] = new GrayPixel();
							break;
						case Transparent:
							this.imageData[i][j] = new TransparentPixel();
							break;
						default:
							break;
					}
				} else {
					this.imageData[i][j] = this.imageData[i][j];
				}
			}
		}
	}
}

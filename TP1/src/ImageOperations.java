/**
 * Interface des operations sur PixelMap
 * @author Tarek Ould Bachir, Wail Khemir
 * @date : 2015-09-06
 */

public interface ImageOperations 
{
	public void negate();
	public void convertToBWImage();
	public void convertToGrayImage();
	public void convertToColorImage();
	public void resize(int x, int y);
	public void insert(PixelMap pm, int row0, int col0);
	//Suppression de meth. rotate()
	public void crop(int h, int w);
	public void translate(int colOffset, int rowOffset);
}

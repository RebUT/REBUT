package vision;

import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2HSV;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_imgproc.*;

/**
 * Detection de jaune dans une image/à la caméra
 * 
 * @author dyna
 * 
 */

public class Color {
	// Code hsv de la couleur qu'on cherche
	int h = 0, s = 0, v = 0, tolerance = 10;

	// Notre image
	IplImage image;

	// Image dans un autre repere couleur
	IplImage hsv;

	// Masque
	IplImage mask;

	public Color(String filename) {
		this.image = cvLoadImage(filename);
		this.hsv = this.image.clone();
	//	mask = cvCreateImage(this.image.cvSize(), image.depth(), 1);
		cvCvtColor(this.image, this.hsv, CV_BGR2HSV);

	}

	public void binairisation() {

	}

}

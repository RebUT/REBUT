package vision;

import static com.googlecode.javacv.cpp.opencv_core.cvGet2D;
import static com.googlecode.javacv.cpp.opencv_core.cvInRangeS;
import static com.googlecode.javacv.cpp.opencv_core.cvOr;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2HSV;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_SHAPE_ELLIPSE;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCreateStructuringElementEx;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvDilate;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvErode;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvReleaseStructuringElement;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_imgproc.IplConvKernel;

/**
 * Detection de jaune dans une image/à la caméra
 * 
 * @author dyna
 * 
 */

public class Color {
	// Code hsv de la couleur qu'on cherche
	int h = 30, s = 252, v = 0, tolerance = 20, sommeX = 0, sommeY = 0, x, y,
			nbPixels;

	// Notre image
	IplImage image;

	// Image dans un autre repere couleur
	IplImage hsv;

	// Masque
	IplImage mask;

	// Noyeau
	IplConvKernel kernel;

	// Fenetre de depart
	CanvasFrame fenetreImage;

	public void binairisation(String filename) {
		this.image = cvLoadImage(filename);
		this.hsv = this.image.clone();
		this.mask = IplImage.create(this.image.cvSize(), image.depth(), 1);

		// On cree l'image hsv
		cvCvtColor(this.image, this.hsv, CV_BGR2HSV);
		
		
		CvScalar scalar = new CvScalar();
		scalar.setVal(0, h - tolerance);
		scalar.setVal(1, s - tolerance);
		scalar.setVal(2, 0);

		CvScalar scalar2 = new CvScalar();
		scalar2.setVal(0, h + tolerance);
		scalar2.setVal(1, s + tolerance);
		scalar2.setVal(2, 255);

		// hsv_frame = cvCreateImage(size, IPL_DEPTH_8U, 3)
		// cvCvtColor(frame, hsv_frame, CV_BGR2HSV)
		CvScalar limite1 = new CvScalar();
		limite1.setVal(0, 20);
		limite1.setVal(1, 60);
		limite1.setVal(2, 60);
		//limite1.setVal(3, 0);
		CvScalar limite2 = new CvScalar();
		limite2.setVal(0, 30);
		limite2.setVal(1, 255);
		limite2.setVal(2, 255);
		//limite2.setVal(3, 255);
		cvInRangeS(hsv, limite1,limite2, mask);

//		cvInRangeS(hsv, hsv_min, hsv_max, image1);
//		cvInRangeS(hsv, hsv_min2, hsv_max2, image2);
//		cvOr(image1, image2, mask, null);

		// cvInRangeS(this.hsv, scalar, scalar2, this.mask);

		kernel = cvCreateStructuringElementEx(5, 5, 3, 3, CV_SHAPE_ELLIPSE,
				null);

		cvDilate(mask, mask, kernel, 1);
		cvErode(mask, mask, kernel, 1);

		for (x = 0; x < mask.width(); x++) {
			for (y = 0; y < mask.height(); y++) {
				if ((y * mask.widthStep() + mask.nChannels() * x) == 255) {
					sommeX += sommeX;
					sommeY += sommeY;
					nbPixels++;
				}

			}
		}

		fenetreImage = new CanvasFrame("In");
		fenetreImage.showImage(image);
		fenetreImage.getCanvas().addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("On clique sur la sourie !");
				CvScalar pixel;
				pixel = cvGet2D(hsv, e.getY(), e.getX());

				// Change the value of the tracked color with the color of the
				// selected pixel
				h = (int) pixel.getVal(0);
				s = (int) pixel.getVal(1);
				v = (int) pixel.getVal(2);

				System.out.println(h);
				System.out.println(s);

				// Release the memory of the hsv image
				// cvReleaseImage(hsv);

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});

		// On affiche le resultat
		CanvasFrame fenetreMask = new CanvasFrame("Out");
		fenetreMask.showImage(mask);

		CanvasFrame fenetreHsv = new CanvasFrame("Out");
		fenetreHsv.showImage(hsv);

		cvReleaseStructuringElement(kernel);

		// if(nbPixels > 0){
		// return cvPoint((int)(sommeX / (nbPixels)), (int)(sommeY /
		// (nbPixels)));
		// }else{
		// return cvPoint(-1, -1);
		// }

		// cvReleaseImage((Object)image.pointerByReference());
	}

	public IplImage getImage() {
		return image;
	}

	public void setImage(IplImage image) {
		this.image = image;
	}

	public IplImage getHsv() {
		return hsv;
	}

	public void setHsv(IplImage hsv) {
		this.hsv = hsv;
	}

	public IplImage getMask() {
		return mask;
	}

	public void setMask(IplImage mask) {
		this.mask = mask;
	}

	public static void main(String args[]) {
		Color c = new Color();

		// cvSetMouseCallback("GeckoGeek Color Tracking", getObjectColor());
		c.binairisation("/Users/gallaz/Documents/workspace/REBUT/src/vision/essai.jpg");
	}

}

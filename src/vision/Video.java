package vision;

import static com.googlecode.javacv.cpp.opencv_core.cvCloneImage;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImage;
import static com.googlecode.javacv.cpp.opencv_core.cvDrawCircle;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSize;
import static com.googlecode.javacv.cpp.opencv_core.cvInRangeS;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import static com.googlecode.javacv.cpp.opencv_core.cvReleaseImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvShowImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvWaitKey;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2HSV;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_SHAPE_ELLIPSE;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCreateStructuringElementEx;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvDilate;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvErode;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvReleaseStructuringElement;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.CvPoint;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_imgproc.IplConvKernel;

/*
 * Code written by Lya (GeckoGeek.fr)
 */

// Maths methods
//#define max(a, b) ((a) > (b) ? (a) : (b))
//#define min(a, b) ((a) < (b) ? (a) : (b))  
//#define abs(x) ((x) > 0 ? (x) : -(x))
//#define sign(x) ((x) > 0 ? 1 : -1)

// Step mooving for object min & max

//#define STEP_MIN 5
//#define STEP_MAX 100 
public class Video {

	final static int STEP_MIN = 5;
	final static int STEP_MAX = 100;
	static IplImage image;
	static CvPoint objectPos = cvPoint(-1, -1);
	static CanvasFrame fenetre = new CanvasFrame("Out");

	// Position of the object we overlay
	// CvPoint objectPos = cvPoint(-1, -1);
	// Color tracked and our tolerance towards it
	static int h = 0;
	static int s = 0;
	int v = 0;
	static int tolerance = 10;

	/*
	 * Transform the image into a two colored image, one color for the color we
	 * want to track, another color for the others colors From this image, we
	 * get two datas : the number of pixel detected, and the center of gravity
	 * of these pixel
	 */
	static CvPoint binarisation(IplImage image, int nbPixels) {

		int x, y;
		IplImage hsv, mask;
		IplConvKernel kernel;
		int sommeX = 0, sommeY = 0;
		nbPixels = 0;

		// Create the mask &initialize it to white (no color detected)
		mask = cvCreateImage(cvGetSize(image), image.depth(), 1);

		// Create the hsv image
		hsv = cvCloneImage(image);
		cvCvtColor(image, hsv, CV_BGR2HSV);

		// cvCvtColor(frame, hsv_frame, CV_BGR2HSV)
		CvScalar limite1 = new CvScalar();
		limite1.setVal(0, 20);
		limite1.setVal(1, 60);
		limite1.setVal(2, 60);
		// limite1.setVal(3, 0);
		CvScalar limite2 = new CvScalar();
		limite2.setVal(0, 30);
		limite2.setVal(1, 255);
		limite2.setVal(2, 255);
		// limite2.setVal(3, 255);
		cvInRangeS(hsv, limite1, limite2, mask);
		// Show the result of the mask image
		cvShowImage("GeckoGeek Mask", mask);

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

		// We release the memory of kernels
		cvReleaseStructuringElement(kernel);

		// We release the memory of the mask
		cvReleaseImage(mask);
		// We release the memory of the hsv image
		cvReleaseImage(hsv);

		// If there is no pixel, we return a center outside the image, else we
		// return the center of gravity
		if (nbPixels > 0)
			return cvPoint((int) (sommeX / (nbPixels)),
					(int) (sommeY / (nbPixels)));
		else
			return cvPoint(-1, -1);
	}

	/*
	 * Add a circle on the video that fellow your colored object
	 */
	static void addObjectToVideo(IplImage image, CvPoint objectNextPos,
			int nbPixels) {

		int objectNextStepX, objectNextStepY;
		int objectPosX = 0;
		int objectPosY = 0;

		// Calculate circle next position (if there is enough pixels)
		if (nbPixels > 10) {

			// Reset position if no pixel were found
			if (objectPos.x() == -1 || objectPos.y() == -1) {
				objectPos.set(objectNextPos.x(), objectNextPos.y());
			}

			// Move step by step the object position to the desired position
			if (Math.abs(objectPos.x() - objectNextPos.x()) > STEP_MIN) {
				objectNextStepX = Math.max(STEP_MIN, Math.min(STEP_MAX,
						Math.abs(objectPos.x() - objectNextPos.x()) / 2));
				objectPosX += (-1)
						* Math.signum(objectPos.x() - objectNextPos.x())
						* objectNextStepX;
				objectPos.x(objectPosX);
			}

			if (Math.abs(objectPos.y() - objectNextPos.y()) > STEP_MIN) {
				objectNextStepY = Math.max(STEP_MIN, Math.min(STEP_MAX,
						Math.abs(objectPos.y() - objectNextPos.y()) / 2));
				objectPosY += (-1)
						* Math.signum(objectPos.y() - objectNextPos.y())
						* objectNextStepY;
				objectPos.y(objectPosY);
			}

			// -1 = object isn't within the camera range
		} else {

			objectPos.x(-1);
			objectPos.y(-1);

		}

		// Draw an object (circle) centered on the calculated center of gravity
		if (nbPixels > 10)
			cvDrawCircle(image, objectPos, 15, CvScalar.RED, 1, 8, 0);

		// We show the image on the window
		// cvShowImage("GeckoGeek Color Tracking", image);
		fenetre.showImage(image);

	}

	/*
	 * Get the color of the pixel where the mouse has clicked We put this color
	 * as model color (the color we want to tracked)
	 */

	public static void main(String args[]) throws Exception {

		// Key for keyboard event
		char key = 0;

		// Number of tracked pixels
		int nbPixels = 0;
		// Next position of the object we overlay
		CvPoint objectNextPos;

		// Initialize the video Capture (200 => CV_CAP_V4L2)
		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
		OpenCVFrameGrabber grabber2 = new OpenCVFrameGrabber(-1);

		try {
			grabber.start();
			grabber2.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Check if the capture is ok
		// if (!capture) {
		// System.out.println("Can't initialize the video capture.\n");
		// return -1;
		// }

		// Create the windows
		// cvNamedWindow("GeckoGeek Color Tracking", CV_WINDOW_AUTOSIZE);
		// cvNamedWindow("GeckoGeek Mask", CV_WINDOW_AUTOSIZE);
		// cvMoveWindow("GeckoGeek Color Tracking", 0, 100);
		// cvMoveWindow("GeckoGeek Mask", 650, 100);

		// Mouse event to select the tracked color on the original image
		// cvSetMouseCallback("GeckoGeek Color Tracking", getObjectColor);

		// While we don't want to quit
		while (key != 'Q' && key != 'q') {

			// We get the current image
			// image = grabber.grab();
			image = grabber2.grab();

			// On affiche l'image dans une fenêtre
			// fenetre.showImage(image);

			// On attend 10ms
			key = (char) cvWaitKey(10);

			// If there is no image, we exit the loop
			// if(!image)
			// break ;

			objectNextPos = binarisation(image, nbPixels);
			addObjectToVideo(image, objectNextPos, nbPixels);
			fenetre.showImage(image);
			// We wait 10 ms
			// key = cvWaitKey(10);

		}

		// Destroy the windows we have created
		// cvDestroyWindow("GeckoGeek Color Tracking");
		// cvDestroyWindow("GeckoGeek Mask");

		// Destroy the capture
		grabber.stop();
		grabber2.stop();
	}
}
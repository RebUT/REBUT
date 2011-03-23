package vision;

import static com.googlecode.javacv.cpp.opencv_core.cvReleaseImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import static com.googlecode.javacv.cpp.opencv_highgui.cvWaitKey;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_GAUSSIAN;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvSmooth;

import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_objdetect;

public class Test {

	public static void smooth(String filename) {
		IplImage image = cvLoadImage(filename);
		cvSmooth(image, image, CV_GAUSSIAN, 3);
		cvSaveImage(filename, image);
	}

	public static void charger(String filename) {
		Loader.load(opencv_objdetect.class);

		// On d�clare "un pointeur vers une structure IplImage" :
		// En gros, on "d�clare une image".
		IplImage img;

		// On charge notre image depuis un fichier.
		img = cvLoadImage(filename);

		// On cr�e une fen�tre intitul�e "Hello World",
		// La taille de cette fen�tre s'adapte � ce qu'elle contient.
		// cvNamedWindow("Hello World", CV_WINDOW_AUTOSIZE);
		CanvasFrame canvas = new CanvasFrame("Out");

		// On affiche l'image dans la fen�tre "Hello World".
		// cvShowImage("Hello World", img);
		canvas.showImage(img);

		// On attend que l'utilisateur appuie sur une touche (0 = ind�finiment).
		cvWaitKey(0);

		// Destruction de la fen�tre.
		// cvDestroyWindow("Hello World");
		canvas.dispose();

		// Lib�ration de l'IplImage (on lui passe un IplImage**).
		cvReleaseImage(img);

	}

	public static void cam() throws Exception {
		// Touche clavier
		char key = 0;
		// Image
		IplImage image;

		// Ouvrir le flux vid�o
		// capture = cvCreateFileCapture("/path/to/your/video/test.avi"); //
		// chemin pour un fichier
		// capture = cvCreateCameraCapture(0);
		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
		grabber.start();
		// V�rifier si l'ouverture du flux est ok

		// D�finition de la fen�tre
		// oGeek Window", CV_WINDOW_AUTOSIZE);
		CanvasFrame fenetre = new CanvasFrame("Out");

		// Boucle tant que l'utilisateur n'appuie pas sur la touche q (ou Q)
		while (key != 'q' && key != 'Q') {

			// On r�cup�re une image
			image = grabber.grab();

			// On affiche l'image dans une fen�tre
			fenetre.showImage(image);

			// On attend 10ms
			key = (char) cvWaitKey(10);

		}

		fenetre.dispose();
		// cvDestroyWindow("GeckoGeek Window");

		// return 0;

	}

	public static void main(String args[]) throws Exception {
		// charger("/Users/gallaz/Documents/workspace/REBUT/resource/lib/javacv-bin/samples/image0.png");
		cam();
	}
}

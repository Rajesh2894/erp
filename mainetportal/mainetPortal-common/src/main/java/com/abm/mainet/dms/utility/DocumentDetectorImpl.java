package com.abm.mainet.dms.utility;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageParser;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.formats.bmp.BmpImageParser;
import org.apache.commons.imaging.formats.dcx.DcxImageParser;
import org.apache.commons.imaging.formats.gif.GifImageParser;
import org.apache.commons.imaging.formats.pcx.PcxImageParser;
import org.apache.commons.imaging.formats.png.PngImageParser;
import org.apache.commons.imaging.formats.tiff.TiffImageParser;
import org.apache.commons.imaging.formats.wbmp.WbmpImageParser;
import org.apache.commons.imaging.formats.xbm.XbmImageParser;
import org.apache.commons.imaging.formats.xpm.XpmImageParser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.slides.IOleObjectFrame;
import com.aspose.slides.IShape;
import com.aspose.slides.ISlide;
import com.aspose.slides.Presentation;
import com.aspose.words.Document;
import com.aspose.words.FileFormatInfo;
import com.aspose.words.FileFormatUtil;
import com.aspose.words.NodeCollection;
import com.aspose.words.NodeType;
import com.aspose.words.Shape;
import com.lowagie.text.pdf.PdfArray;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfReader;

/**
 * Implementation of the detector for Document,Image,pdf File.
 *
 *
 */
@Service
public class DocumentDetectorImpl implements IDocumentDetector {

    /**
     * 
     */
    private static final long serialVersionUID = -4110370541309258147L;
    /**
     * LOGGER
     */
    private static final Logger LOG = Logger.getLogger(DocumentDetectorImpl.class);

    /**
     * {@inheritDoc}
     *
     * @see eu.righettod.poc.detector.DocumentDetector#isSafe(java.io.File)
     */
    @Override
    public boolean isPowerpointSafe(File f, MultipartFile multiFile) {
        boolean safeState = false;
        ByteArrayInputStream inputStream = null;
        try {
            if (f != null) {
                // Load the file into the Powerpoint document parser
                inputStream = new ByteArrayInputStream(multiFile.getBytes());

                Presentation presentation = new Presentation(inputStream);
                // First check on Powerpoint format skipped because:
                // FileFormatInfo class is not provided for Aspose Slides API
                // PresentationFactory.getInstance().getPresentationInfo() can be used but the LoadFormat class miss format like
                // POT or PPT XML
                // Aspose API do not support PPT XML format
                // Get safe state from presence of a VBA project in the presentation
                safeState = (presentation.getVbaProject() == null);
                // If presentation is safe then we pass to OLE objects analysis
                if (safeState) {
                    // Parse all slides of the presentation
                    int totalOLEObjectCount = 0;
                    for (ISlide slide : presentation.getSlides()) {
                        for (IShape shape : slide.getShapes()) {
                            // Check if the current shape is an OLE object
                            if (shape instanceof IOleObjectFrame) {
                                totalOLEObjectCount++;
                            }
                        }
                    }
                    // Update safe status flag according to number of OLE object found
                    if (totalOLEObjectCount != 0) {
                        safeState = false;
                    }
                }

            }
        } catch (Exception e) {
            safeState = false;
            LOG.warn("Error during Powerpoint file analysis !", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LOG.warn("Error during close inputStream", e);
            }
        }
        return safeState;
    }

    /**
     * List of allowed Word format (WML = Word ML (Word 2003 XML)).<br>
     * Allow also DOCM because it can exists without macro inside.<br>
     * Allow also DOT/DOTM because both can exists without macro inside.<br>
     * We reject MHTML file because:<br>
     * <ul>
     * <li>API cannot detect macro into this format</li>
     * <li>Is not normal to use this format to represent a Word file (there plenty of others supported format)</li>
     * </ul>
     */
    private static final List<String> ALLOWED_FORMAT = Arrays
            .asList(new String[] { "doc", "docx", "docm", "wml", "dot", "dotm" });

    /**
     * {@inheritDoc}
     *
     * @see eu.righettod.poc.detector.DocumentDetector#isSafe(java.io.File)
     */
    @Override
    @SuppressWarnings("rawtypes")
    public boolean isWordDocumentSafe(File f, MultipartFile multiFile) {
        boolean safeState = false;
        ByteArrayInputStream inputStream = null;
        try {
            if ((f != null)) {
                // File file = new File(f.getAbsolutePath());
                // file.createNewFile();
                // InputStream initialStream = new FileInputStream(file);
                // byte[] buffer = new byte[initialStream.available()];
                inputStream = new ByteArrayInputStream(multiFile.getBytes());

                // initialStream.read(buffer);
                // Perform a first check on Word document format
                FileFormatInfo formatInfo = FileFormatUtil.detectFileFormat(inputStream);
                String formatExtension = FileFormatUtil.loadFormatToExtension(formatInfo.getLoadFormat());
                if ((formatExtension != null)
                        && ALLOWED_FORMAT.contains(formatExtension.toLowerCase(Locale.US).replaceAll("\\.", ""))) {
                    // Load the file into the Word document parser
                    Document document = new Document(inputStream);
                    // Get safe state from Macro presence
                    safeState = !document.hasMacros();
                    // If document is safe then we pass to OLE objects analysis
                    if (safeState) {
                        // Get all shapes of the document
                        NodeCollection shapes = document.getChildNodes(NodeType.SHAPE, true);
                        Shape shape = null;
                        // Search OLE objects in all shapes
                        int totalOLEObjectCount = 0;
                        for (int i = 0; i < shapes.getCount(); i++) {
                            shape = (Shape) shapes.get(i);
                            // Check if the current shape has OLE object
                            if (shape.getOleFormat() != null) {
                                totalOLEObjectCount++;
                            }
                        }
                        // Update safe status flag according to number of OLE object found
                        if (totalOLEObjectCount != 0) {
                            safeState = false;
                        }

                    }
                }
            }
        } catch (Exception e) {
            safeState = false;
            LOG.warn("Error during Word file analysis !", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LOG.warn("Error during close inputStream", e);
            }
        }
        return safeState;
    }

    /**
     * {@inheritDoc}
     *
     * @see eu.righettod.poc.detector.DocumentDetector#isSafe(java.io.File)
     */
    @Override
    public boolean isPdfSafe(MultipartFile f) {
        boolean safeState = false;
        try {
            if (f != null) {

                // Load stream in PDF parser
                // If the stream is not a PDF then exception will be throwed
                // here and safe state will be set to FALSE
                PdfReader reader = new PdfReader(f.getBytes());

                // Check 1:
                // Detect if the document contains any JavaScript code
                String jsCode = reader.getJavaScript();
                if (jsCode == null) {
                    // OK no JS code then when pass to check 2:
                    // Detect if the document has any embedded files
                    PdfDictionary root = reader.getCatalog();
                    PdfDictionary names = root.getAsDict(PdfName.NAMES);
                    PdfArray namesArray = null;
                    if (names != null) {
                        PdfDictionary embeddedFiles = names.getAsDict(PdfName.EMBEDDEDFILES);
                        namesArray = embeddedFiles.getAsArray(PdfName.NAMES);
                    }
                    // Get safe state from number of embedded files
                    safeState = ((namesArray == null) || namesArray.isEmpty());
                }
            }
        } catch (Exception e) {
            safeState = false;
            LOG.warn("Error during Pdf file analysis !", e);
        }
        return safeState;
    }

    /**
     * Implementation of the sanitizer for Image file.
     * <p>
     * Use Java built-in API in complement of Apache Commons Imaging for format not supported by the built-in API.
     *
     * @see "http://commons.apache.org/proper/commons-imaging/"
     * @see "http://commons.apache.org/proper/commons-imaging/formatsupport.html" {@inheritDoc}
     *
     * @see eu.righettod.poc.sanitizer.DocumentSanitizer#madeSafe(java.io.File)
     */
    @Override
    public boolean isImageSafe(File f, MultipartFile multiFile) {
        boolean safeState = false;
        boolean fallbackOnApacheCommonsImaging;
        OutputStream outputStream = null;
        ByteArrayInputStream inputStream = null;
        try {
            if (f != null) {
                // Get the image format
                String formatName;
                inputStream = new ByteArrayInputStream(multiFile.getBytes());
                outputStream = new FileOutputStream(f);
                outputStream.write(multiFile.getBytes());

                try (ImageInputStream iis = ImageIO.createImageInputStream(f)) {
                    Iterator<ImageReader> imageReaderIterator = ImageIO.getImageReaders(iis);
                    // If there not ImageReader instance found so it's means that the current format is not supported by the Java
                    // built-in API
                    if (!imageReaderIterator.hasNext()) {
                        ImageInfo imageInfo = Imaging.getImageInfo(multiFile.getBytes());
                        if (imageInfo != null && imageInfo.getFormat() != null && imageInfo.getFormat().getName() != null) {
                            formatName = imageInfo.getFormat().getName();
                            fallbackOnApacheCommonsImaging = true;
                        } else {
                            throw new IOException("Format of the original image is not supported for read operation !");
                        }
                    } else {
                        ImageReader reader = imageReaderIterator.next();
                        formatName = reader.getFormatName();
                        fallbackOnApacheCommonsImaging = false;
                    }
                }

                // Load the image
                BufferedImage originalImage;
                if (!fallbackOnApacheCommonsImaging) {
                    originalImage = ImageIO.read(inputStream);
                } else {
                    originalImage = Imaging.getBufferedImage(inputStream);
                }

                // Check that image has been successfully loaded
                if (originalImage == null) {
                    throw new IOException("Cannot load the original image !");
                }

                // Get current Width and Height of the image
                int originalWidth = originalImage.getWidth(null);
                int originalHeight = originalImage.getHeight(null);

                // Resize the image by removing 1px on Width and Height
                Image resizedImage = originalImage.getScaledInstance(originalWidth - 1, originalHeight - 1, Image.SCALE_SMOOTH);

                // Resize the resized image by adding 1px on Width and Height - In fact set image to is initial size
                Image initialSizedImage = resizedImage.getScaledInstance(originalWidth, originalHeight, Image.SCALE_SMOOTH);

                // Save image by overwriting the provided source file content
                BufferedImage sanitizedImage = new BufferedImage(initialSizedImage.getWidth(null),
                        initialSizedImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
                Graphics bg = sanitizedImage.getGraphics();
                bg.drawImage(initialSizedImage, 0, 0, null);
                bg.dispose();
                try (OutputStream fos = outputStream) {
                    if (!fallbackOnApacheCommonsImaging) {
                        ImageIO.write(sanitizedImage, formatName, fos);
                    } else {
                        ImageParser imageParser;
                        // Handle only formats for which Apache Commons Imaging can successfully write (YES in Write column of the
                        // reference link) the image format
                        // See reference link in the class header
                        switch (formatName) {
                        case "TIFF": {
                            imageParser = new TiffImageParser();
                            break;
                        }
                        case "PCX": {
                            imageParser = new PcxImageParser();
                            break;
                        }
                        case "DCX": {
                            imageParser = new DcxImageParser();
                            break;
                        }
                        case "BMP": {
                            imageParser = new BmpImageParser();
                            break;
                        }
                        case "GIF": {
                            imageParser = new GifImageParser();
                            break;
                        }
                        case "PNG": {
                            imageParser = new PngImageParser();
                            break;
                        }
                        case "WBMP": {
                            imageParser = new WbmpImageParser();
                            break;
                        }
                        case "XBM": {
                            imageParser = new XbmImageParser();
                            break;
                        }
                        case "XPM": {
                            imageParser = new XpmImageParser();
                            break;
                        }
                        default: {
                            throw new IOException("Format of the original image is not supported for write operation !");
                        }

                        }
                        imageParser.writeImage(sanitizedImage, fos, new HashMap<>());
                    }

                }

                // Set state flag
                safeState = true;
            }

        } catch (Exception e) {
            safeState = false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LOG.warn("Error during close stream", e);
            }
        }
        return safeState;
    }

}

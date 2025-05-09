package com.phonestore.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Utility class for handling file uploads
 */
public class FileUploadUtil {

    /**
     * Process a multipart request and extract form fields and files
     * @param request The HTTP request
     * @param uploadDir The directory to upload files to
     * @return A map of form fields and file paths
     */
    public static Map<String, String> processRequest(HttpServletRequest request, String uploadDir) {
        Map<String, String> formFields = new HashMap<>();

        try {
            // Create the upload directory if it doesn't exist
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            // Process all parts of the request
            for (Part part : request.getParts()) {
                String fieldName = part.getName();
                String contentDisposition = part.getHeader("content-disposition");

                // Check if this part is a file
                if (contentDisposition.contains("filename=")) {
                    // Get the filename from the content-disposition header
                    String fileName = getFileName(part);

                    if (fileName != null && !fileName.isEmpty()) {
                        // Generate a unique file name to prevent overwriting
                        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

                        // Write the file
                        part.write(uploadDir + File.separator + uniqueFileName);

                        // Add the file path to the form fields
                        formFields.put(fieldName, uniqueFileName);
                    }
                } else {
                    // Process regular form field
                    String fieldValue = request.getParameter(fieldName);
                    formFields.put(fieldName, fieldValue);
                }
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }

        return formFields;
    }

    /**
     * Extract the filename from a Part
     * @param part The Part to extract the filename from
     * @return The filename or null if not found
     */
    private static String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");

        for (String item : items) {
            if (item.trim().startsWith("filename=")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }

        return null;
    }

    /**
     * Delete a file
     * @param filePath The path of the file to delete
     * @param uploadDir The directory where the file is located
     * @return true if deleted, false otherwise
     */
    public static boolean deleteFile(String filePath, String uploadDir) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }

        File file = new File(uploadDir, filePath);
        return file.exists() && file.delete();
    }
}

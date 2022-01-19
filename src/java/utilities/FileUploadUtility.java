package utilities;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.logging.Level;
import javax.servlet.http.Part;

/**
 * Utility class for uploading files to a remote directory.
 * This class utilizes <code> Part </code> objects, and requires
 * that any calling servlet gets it's data from a form that is
 * multi-part.
 * 
 * Multi-Part servlets are easily configured by importing
 * the <code> @MultiPartConfig </code> tag and getting part 
 * objects from a form that defines the attribute enctype='multipart/form-data'.
 * 
 * @author Jacob Swineford (2021)
 */
public class FileUploadUtility {
    
    /**
     * Method that writes a file to a remote directory. Note that this method
     * has no way of knowing that a file location is remote. 
     * 
     * The file extension that comes with file part is automatically appended
     * to the given saveAs <code> String </code> when saving.
     * Ex: partName = sample.png, saveAs = foo, result = foo.png 
     * 
     * This method, from what I've seen, seems to overwrite files of the same name
     * with new part data. Thus, for replacing files in a directory
     * <code> deleteFromRemote() </code> may not be needed.
     * 
     * @param directory directory to upload to
     * @param saveAs name to save the file under
     * @param filePart given <code> Part </code> object from a multi-part form,
     *                 implicitly trusted to be a file from a form
     * 
     * @return true if the file was uploaded, false otherwise
     */
    public static boolean uploadToRemote(String directory, String saveAs, Part filePart) {
        String fileName = getFileName(filePart);
        if (fileName.equals("")) return false;
        String fileExtension = getFileNameExtension(filePart);
        File saveFile = new File(directory + File.separator + saveAs + '.' + fileExtension);
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                 // nothing to really be done here. This catch statement does not mean that the file cannot
                 // be uploaded, just that a separate storage file has not been created.
                System.out.println("The file " + directory + File.separator + saveAs + " was not created!");
            }
        }
        
        String savePath = directory + File.separator + saveAs + '.' + fileExtension;
        try {
            filePart.write(savePath); // overwrites files with the same name (no .extension)
            return true;
        } catch (IOException e) {
            WebErrorLogger.log(Level.SEVERE, "Something went wrong with trying to upload the file "
                    + saveAs + " to " + directory, e);
        }
        return false;
    }
    
    /**
     * Uploads all of the files defined in fileParts, corresponding to the name
     * contained in the same index of saveAsNames. Note that this method has no way of knowing
     * that a file location is remote.
     * 
     * This method is generally not recommended because it might get hard to debug, but
     * it is here if you would like to use it.
     * 
     * If saveAsNames and fileParts do not have the same length, this method returns false.
     * 
     * @param directory directory to upload to
     * @param saveAsNames corresponding name to save the file as
     * @param fileParts corresponding <code> Part </code> object written to the directory
     * 
     * @return true if the files were uploaded, false otherwise.
     */
    public static boolean uploadAllToRemote(String directory, String[] saveAsNames, Part[] fileParts) {
        if (saveAsNames.length != fileParts.length) return false;
        for (int i = 0; i < fileParts.length; i++) {
            boolean uploaded = uploadToRemote(directory, saveAsNames[i], fileParts[i]);
            if (!uploaded) return false;
        }
        return true;
    }
    
    /**
     * Makes a remote directory. Note that this method has no way of knowing
     * that a file location is remote.
     * 
     * @param directory directory to upload. This method expects the directory
     *                  path to be absolute.
     * 
     * @return true if the directory was created, false otherwise.
     */
    public static boolean makeRemoteDirectory(String directory) {
        File dir = new File(directory);
        boolean made = dir.mkdirs();
        if (!made) {
            WebErrorLogger.log(Level.SEVERE, "Something went wrong when trying to create remote directory " + directory + "!");
            return false;
        }
        return true;
    }
    
    /**
     * Deletes a file from the given remote directory. Note that this method
     * has no way of knowing that a file location is remote. Also note that if the caller
     * wishes to delete a directory and all of it's contents
     * then the <code> deleteRemoteDirectory() </code> method must be used.
     * 
     * This method expects a the given file name to be without an extension.
     * The first encountered file that contains the file name will be deleted.
     * Ex: [file1.txt, file2.txt], fileName = file1, file1.txt will be deleted.
     * 
     * @param directory directory to delete from
     * @param fileName name of the file to delete
     * 
     * @return true if the file was successfully deleted, false otherwise
     */
    public static boolean deleteFromRemote(String directory, String fileName) {
        File dir = new File(directory);
        if (!dir.exists()) return false;
        String[] list = dir.list();
        String fileNameWithExtension = null;
        for (String str : list) {
            if (str.contains(fileName)) {
                fileNameWithExtension = str;
                break;
            }
        }
        if (fileNameWithExtension == null) return false;
        File file = new File(directory + File.separator + fileNameWithExtension);
        if (file.delete()) return true;
        WebErrorLogger.log(Level.SEVERE, "Something went wrong when trying to delete " + fileName + " from " + directory + "!");
        return false;
    } 
     
    /**
     * Deletes a remote directory and all of it's contents. Note that this method has no way of knowing
     * that a file location is remote. This method deletes a directory and all
     * of it's contents.
     * 
     * @param directory directory to be deleted
     * @return true if the directory was successful
     */
    public static boolean deleteRemoteDirectory(String directory) {
        boolean cleared = deleteAllFromRemote(directory);
        if (!cleared) return false;
        File dir = new File(directory);
        boolean deleted = dir.delete();
        if (!deleted) {
            WebErrorLogger.log(Level.SEVERE, "Something went wrong with trying to delete the directory " + directory + "!");
            return false;
        }
        return true;
    }
    
    /**
     * Deletes all the files in a remote directory. Note that this method has no way of knowing
     * that a file location is remote.
     * 
     * This method will return true if the given remote directory is already empty.
     * Also, if this method returns false then some files may be deleted while others
     * would have remained intact which may cause some issues.
     * 
     * @param directory given directory to delete from
     * 
     * @return true if all files were deleted, false otherwise.
     */
    public static boolean deleteAllFromRemote(String directory) {
        String[] fileNames = new File(directory).list();
        for (String fileName : fileNames) {
            boolean deleted = deleteFromRemote(directory, fileName);
            if (!deleted) return false;
        }
        return true;
    }
    
    /**
     * Gets the uploaded file name. Note that if the part is empty
     * (no file was uploaded) then the string returned is "".
     * 
     * @param part given part
     * @return file name of the given part
     */
    public static String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim()
                        .replace("\"", "");
            }
        }
        return null;
    }
    
    /**
     * Gets the uploaded file name extension. Note that if a string is
     * (no file was uploaded) then the string returned is "";
     * 
     * @param part given part
     * @return file name extension of the given part
     */
    public static String getFileNameExtension(Part part) {
        String name = getFileName(part);
        return name.substring(name.lastIndexOf('.') + 1);
    }
    
    /**
     * Gets the given file name's extension. Note that if the given file isn't
     * found, then the string returned is "".
     * 
     * This method does not necessarily expect that the given file name
     * is appended with it's extension.
     * Ex. real = sample.png expected = sample
     * 
     * Either way, this method will return the extension.
     * 
     * @param directory given remote directory
     * @param fileName file name to search for in directory
     * 
     * @return the file extension of the given file, if it exists.
     *         returns <code> null </code> otherwise
     */
    public static String getFileNameExtension(String directory, String fileName) {
        int fileIndex = remoteDirectoryIndexOf(directory, fileName);
        if (fileIndex != -1) {
            File dir = new File(directory); 
            String[] list = dir.list();
            return list[fileIndex].substring(list[fileIndex].lastIndexOf('.') + 1);
        } else {
            return null;
        }
    }
    
    /**
     * Returns whether this part is empty or not. An empty part in this context
     * means that no file was uploaded through an input tag in a form.
     * 
     * @param part given part
     * @return true if the part is empty, false otherwise
     */
    public static boolean isEmptyPart(Part part) {
        return getFileName(part).equals("");
    }
    
    /**
     * Returns whether the given remote directory contains the given file.
     * If the directory doesn't exist, this method returns false.
     * 
     * This method checks whether any particular file in the directory
     * contains the given file name.
     * 
     * @param directory given remote directory
     * @param fileName file name to check for
     * 
     * @return true if the file was found, false otherwise
     */
    public static boolean remoteDirectoryContains(String directory, String fileName) {
        File dir = new File(directory);
        if (dir.exists()) {
            String[] list = dir.list();
            for (String str : list) {
                if (str.contains(fileName)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }
    
    /**
     * Returns an array of strings for all files in a given directory.
     * If the directory doesn't exist, this method returns null.
     * 
     * @param directory given remote directory
     * 
     * @return an array of file names from the directory, null if the directory does not exist
     */
    public static String[] getAllFileNames(String directory){
        File dir = new File(directory);
        if(dir.exists()){
            return dir.list();
        }
        return null;
    }
    
    /**
     * Returns all files in a given directory of given type.
     * If the directory doesn't exist, this method returns null.
     * 
     * @param directory given remote directory
     * 
     * @return an array of File objects from the directory, null if the directory does not exist
     */
    public static File[] getAllFiles(String directory) {
        File dir = new File(directory);
        if(dir.exists()){
            try{
                File[] files = dir.listFiles();
                return files;

            }catch(Exception e){
            WebErrorLogger.log(Level.SEVERE, "Something went wrong when retrieving "
                    + "files in FileUploadUtility.getAllFiles(String directory) "
                    + "directory= " + directory + " error: " + e);
            }
        }
        return null;
    }
    
    /**
     * Returns all files in a given directory.
     * If the directory doesn't exist, this method returns null.
     * 
     * This method checks whether any particular file in the directory
     * contains the given file name.
     * 
     * @param directory given remote directory
     * @param type file extention for desired file type 
     *             ex: ".txt" or "txt" can be used to search for text files
     * 
     * @return true if the file was found, false otherwise
     */
    public static File[] getAllFilesOfType(String directory, String type){
        
        File dir = new File(directory);
            if(dir.exists()){
                try{
                    FileFilter extention = (File dir1) -> dir1.getName().endsWith(type);
                    File files[] = dir.listFiles(extention);
                    return files;
                }catch(Exception e){
                    WebErrorLogger.log(Level.SEVERE, "Something went wrong when retrieving "
                    + "files in FileUploadUtility.getAllFilesByType(String directory) "
                    + "directory= " + directory + " error: " + e);
                }
            }
            return null;
    }
    
    /**
     * Returns whether the given fileName's location based on the directory's 
     * list() method.
     * If the directory doesn't exist, this method returns false.
     * 
     * This method checks whether any particular file in the directory
     * contains the given file name.
     * 
     * @param directory given remote directory
     * @param fileName file name to check for
     * 
     * @return true if the file was found, false otherwise
     */
    public static int remoteDirectoryIndexOf(String directory, String fileName) {
        File dir = new File(directory);
        if (dir.exists()) {
            String[] list = dir.list();
            int count = 0;
            for (String str : list) {
                if (str.contains(fileName)) {
                    return count;
                }
                count++;
            }
        }
        return -1;
    }

}

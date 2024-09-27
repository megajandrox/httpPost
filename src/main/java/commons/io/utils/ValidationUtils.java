package commons.io.utils;

import commons.io.utils.exceptions.FileIOException;

import java.io.File;

import static java.lang.String.format;

/**
 *  Created by alejandro larosa.
 *  This class is a utility class to validate if the file can read and write
 *  It uses the FileIOException class to throw exceptions
 *  It uses the File class to get the absolute path of the file
 */
public class ValidationUtils {

    public static void validateIfCanRead(File file) throws FileIOException {
        if (!file.canRead()) {
            throw new FileIOException(format("Permission Denied to read file %s", file.getAbsolutePath()));
        }
    }

    public static void validateIfCanWrite(File file) throws FileIOException {
        if (!file.canWrite()) {
            throw new FileIOException(format("Permission Denied to write file %s", file.getAbsolutePath()));
        }
    }

    public static void validateIfNotExists(File file) throws FileIOException {
        if (file.exists()) {
            throw new FileIOException(format("File %s exist", file.getAbsolutePath()));
        }
    }
    public static void validateIfExists(File file) throws FileIOException {
        if (!file.exists()) {
            throw new FileIOException(format("File %s does not exist", file.getAbsolutePath()));
        }
    }
}

package com.wevioo.pi.utility;

import java.io.File;

import org.springframework.stereotype.Component;

import com.wevioo.pi.common.ApplicationConstants;
import com.wevioo.pi.exception.DataNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileUtils {

	/**
	 * Delete file from path
	 * 
	 * @param path filePath
	 */
	public void deleteFile(String path) {
		if (path != null) {
			// Create a File object representing the file at the given path
			File file = new File(path);

			// Check if the file exists
			if (file.exists()) {
				// Attempt to delete the file
				if (file.delete()) {
						// If the file is successfully deleted, print a success message
					log.info("File deleted successfully.");
				} else {
					// If the file deletion fails, throw a custom exception indicating failure
					throw new DataNotFoundException(ApplicationConstants.ERROR_FAILED_TO_DELETE_FILE,
							ApplicationConstants.FAILED_TO_DELETE_FILE);
				}
			} else {
				// If the file does not exist, throw a custom exception indicating that no file
				// was found
				throw new DataNotFoundException(ApplicationConstants.ERROR_NO_FILE_FOUND,
						ApplicationConstants.NO_FILE_FOUND_WITH_PATH + path);
			}
		}

	}

}

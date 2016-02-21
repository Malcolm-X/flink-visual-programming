package org.tuberlin.de.common.model.interfaces.datasources.file.read;

import org.tuberlin.de.common.model.Constants;
import org.tuberlin.de.common.model.interfaces.datasources.file.DataSourceFile;

/**
 * Created by oxid on 1/9/16.
 *
 * readTextFile(path) / TextInputFormat - Reads files line wise and returns them as Strings.
 *
 */
public interface DataSourceFileReadTextFile extends DataSourceFile{

    public static final String TYPE = Constants.TYPE_DATA_SOURCE_FILE_READ_TEXT_FILE;

}

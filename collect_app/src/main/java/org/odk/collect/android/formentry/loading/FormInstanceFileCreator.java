package org.odk.collect.android.formentry.loading;

import org.odk.collect.android.storage.StoragePathProvider;
import org.odk.collect.android.storage.StorageSubdirectory;
import org.odk.collect.android.utilities.FileUtils;
import org.odk.collect.utilities.Clock;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class FormInstanceFileCreator {

    private final StoragePathProvider storagePathProvider;
    private final Clock clock;

    public FormInstanceFileCreator(StoragePathProvider storagePathProvider, Clock clock) {
        this.storagePathProvider = storagePathProvider;
        this.clock = clock;
    }

    public File createInstanceFile(String formDefinitionPath) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS", Locale.ENGLISH)
                .format(new Date(clock.getCurrentTime()));
        String formFileName = formDefinitionPath.substring(formDefinitionPath.lastIndexOf('/') + 1,
                formDefinitionPath.lastIndexOf('.'));
        String instancesDir = storagePathProvider.getDirPath(StorageSubdirectory.INSTANCES);
        String instanceDir = instancesDir + File.separator + formFileName + "_" + timestamp;

        if (FileUtils.createFolder(instanceDir)) {
            return new File(instanceDir + File.separator + formFileName + "_" + timestamp + ".xml");
        } else {
            Timber.e("Error creating form instance file");
            return null;
        }
    }
}

package prefs;

import util.Version;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains configuration information for updating
 */
public class UpdateConfig {

    private boolean lastUpdateDownloadStatus;
    private List<Version> listOfVersionsPreviouslyDownloaded; // NOPMD

    public UpdateConfig() {
        listOfVersionsPreviouslyDownloaded = new ArrayList<>();
    }

    public void setLastUpdateDownloadStatus(boolean status) {
        this.lastUpdateDownloadStatus = status;
    }

    public boolean getLastUpdateDownloadStatus() {
        return this.lastUpdateDownloadStatus;
    }

    public void addToVersionPreviouslyDownloaded(Version downloadedVersion) {
        listOfVersionsPreviouslyDownloaded.add(downloadedVersion);
    }

    public boolean checkIfVersionWasPreviouslyDownloaded(Version version) {
        return listOfVersionsPreviouslyDownloaded.contains(version);
    }
}
